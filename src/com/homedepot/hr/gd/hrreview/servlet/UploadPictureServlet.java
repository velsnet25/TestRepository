/**
 * 
 * This program is proprietary to The Home Depot and is not to be 
 * reproduced,
 *  used, or disclosed without permission of:
 *     The Home Depot
 *     2455 Paces Ferry Road, N.W.
 *     Atlanta, GA 30339-4024
 *  
 *  File Name: UploadPictureServlet.java  
 *  Application: Succession Planning
 * 
 */

package com.homedepot.hr.gd.hrreview.servlet;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.homedepot.hr.gd.hrreview.interfaces.Constants;
import com.homedepot.hr.gd.hrreview.request.MultipartRequest;
import com.homedepot.ta.aa.catalina.realm.Profile;
import com.homedepot.ta.aa.util.TAAAResourceManager;

import org.apache.log4j.Logger;

/**
 * <P>This is the servlet for uploading pictures. This servlet is 
 * invoked by clicking on the Upload Picture button on the Basic Information screen
 *
 * <P><B>Class Name: UploadPictureServlet </B><BR>
 * <P><B>Change Log: </B><BR>
 * 01/13/2014 - gxn5764 - modified for new HR Review
 * 09/04/2001 - Wipro - Creation<BR>
 * 01/08/2001 - Wipro - modified for DR-01 <BR>
 * 02/08/2001 - Wipro - modified for DR-03 <BR>
 *
 * @author Wipro
 * @version 3.0
 *
 */
public class UploadPictureServlet extends HttpServlet implements Constants
{  	
  /**
	 * 
	 */
	private static final long serialVersionUID = -8200547800356231012L;

private static final Logger logger = Logger
			.getLogger(UploadPictureServlet.class);

  /** This method is called when the first time the servlet is invoked
  *   
  *   <P><B>Method Name:init</B><BR>
  *   <P><B>Change Log: </B><BR>
  *      09/04/2001 - Wipro - Creation<BR>
  *     
  *   @param ascConfig ServletConfig
  * 
  * */
  public void init (ServletConfig aservletConfig) throws ServletException
  {
    if (logger.isDebugEnabled()) {
		logger.debug("start init(ServletConfig)");
		logger.debug("aservletConfig: " + aservletConfig);
	}
	super.init(aservletConfig);

	if (logger.isDebugEnabled()) {
		logger.debug("finish init()");
	}
  }//end of init method																
  
  /** This method is called by a HTTP GET method
  * This method calls the displayPage method which paints the page
  *   Tests profile object
  
  *     
  * @param ahsrRequest - the HttpServletRequest
  *	@param ahsrResponse - the HttpServletResponse
  * 
  * */ 
  public void doGet(HttpServletRequest ahsrRequest,
    HttpServletResponse ahsrResponse)
    throws ServletException
  {
    if (logger.isDebugEnabled()) {
		logger.debug("start doGet(HttpServletRequest,HttpServletResponse)");
		logger.debug("ahsrRequest: " + ahsrRequest);
		logger.debug("ahsrResponse: " + ahsrResponse);
	}
	PrintWriter printWriter;
  
    try
    {
      ahsrResponse.setContentType("text/html");
      //get a PrintWriter from the Response object
      printWriter=ahsrResponse.getWriter();
      
      
      Profile profile = Profile.getCurrent();
      logger.info(profile.getProperty(Profile.FIRST_NAME));
      logger.info(profile.getProperty(Profile.USER_ID)) ;
      logger.info(profile.getProperty(Profile.SYS_USER_ID));
     
 	
      
      printWriter.println(profile.getProperty(Profile.FIRST_NAME)) ;
      printWriter.println(profile.getProperty(Profile.USER_ID)) ;
      printWriter.println(profile.getProperty(Profile.SYS_USER_ID));
      printWriter.flush();
      printWriter.close();
			//call the displayPage method to paint the page
  		
    }
    catch(Exception e)
    {
    	logger.error("Error occurred in get UploadPicture Servlet", e);
      
    }
	if (logger.isDebugEnabled()) {
		logger.debug("finish doGet()");
	}
    
    
  }
  
  
  /** This method is called by a HTTP POST method
  * An instance of the MultipartRequest is created which reads the file
  * from the input stream and then names it to the required name in the 
  * required directory
  *   
  *   <P><B>Method Name: doPost</B><BR>
  *   <P><B>Change Log: </B><BR>
  *      09/04/2001 - Wipro - Creation<BR>
  *	   02/08/2001 - Wipro - modified for DR-03 <BR>
  *     
  * @param ahsrRequest - the HttpServletRequest
  *	@param ahsrResponse - the HttpServletResponse
  * 
  * */  
	protected void doPost(HttpServletRequest ahsrRequest,
			HttpServletResponse ahsrResponse) throws ServletException,
			IOException {

		if (logger.isDebugEnabled()) {
					logger.debug("start doPost(HttpServletRequest,HttpServletResponse)");
					logger.debug("ahsrRequest: " + ahsrRequest);
					logger.debug("ahsrResponse: " + ahsrResponse);
				}
		String associateID = ahsrRequest.getParameter("ID");
		// get the PrintWriter from the Response object
		PrintWriter printWriter = ahsrResponse.getWriter();
		String strError = null;

		try {
			// get the real path corresponding to the virtual path given

			String strPath = null;
			String lcp = TAAAResourceManager.getProperty("host.LCP");
			lcp = lcp.trim().toUpperCase();

			strPath = "/var/opt/tomcat/tmp/gd";

			try {

				MultipartRequest multipartRequest = new MultipartRequest(
						ahsrRequest.getContentType(),
						ahsrRequest.getContentLength(),
						ahsrRequest.getInputStream(), strPath, MAX_FILE_SIZE,
						ALLOWED_FILE_TYPES);

				// call the fileRename method on it
				// multipartRequest.fileRename(associateID);

				// call upload to upload image to final permanent store,				
				String userID = ahsrRequest.getUserPrincipal().getName();
				multipartRequest.uploadImage(associateID, userID);

				displayPage("msg.txt.picture.upload.success", printWriter,
						ahsrRequest);
				ahsrResponse.setStatus(HttpServletResponse.SC_OK);

			} catch (IOException ie) {
				
				//Must set to 200/OK for ie8
				ahsrResponse.setStatus(HttpServletResponse.SC_OK);
				displayPage(ie.getMessage(), printWriter, ahsrRequest);

			}

		} catch (Exception e) {
			// if some error has occurred then get the error message and pass it
			// to the displayPage method for painting
			strError = e.getMessage();
			logger.error("Error occurred in doPost()", e);

			ahsrResponse.setContentType(TEXT_HTML);
			ahsrResponse.setStatus(HttpServletResponse.SC_OK);
			// DR-03
			displayPage(strError, printWriter, ahsrRequest);
		}

		// set the response content type
		ahsrResponse.setContentType(TEXT_HTML);
		if (logger.isDebugEnabled()) {
			
			logger.debug("finish doPost()");
		}

	}// end of do post
  
  /** This method is called by both the doPost and doGet methods. This is the method 
  * actually doing the painting of the screen
  *   
  *     
  *   
  *	@param astrError - the string representing the error message
  *   @param aprintWriter the printWriter
  * @param ahsrRequest - the HttpServletRequest
  * */
 // private void displayPage(String astrSessionId,String astrError,PrintWriter aprintWriter, HttpServletRequest ahsrRequest)
  private void displayPage(String astrError,PrintWriter aprintWriter, HttpServletRequest ahsrRequest)
	{	

		if (logger.isDebugEnabled()) {
			logger.debug("start displayPage(String,PrintWriter,HttpServletRequest)");
			logger.debug("astrError: \"" + astrError + "\"");
			logger.debug("aprintWriter: " + aprintWriter);
			logger.debug("ahsrRequest: " + ahsrRequest);
		}
		String assocId = null;
		String htmlResponseText = new String() ;
   
		assocId = ahsrRequest.getParameter("ID").toUpperCase(); 
		

		if(logger.isDebugEnabled())
		{
			logger.debug("ID: " + assocId);
		}
		

			
				
	    if (astrError!=null && astrError.equals("msg.error.choose.valid.picture"))
	    	htmlResponseText=encodeHtml("Invalid Image format. Acceptable formats are: gif,bmp,avi,dib,fits,jpg,jpeg,pgm,ppm,psd,sun,wpg,pict,mtv");
	    else if (astrError != null && astrError.equals("msg.error.picture.size"))
	    	htmlResponseText=encodeHtml("The picture is too large to upload, please use an image file less than of equal to 1MB.");
			else if (astrError != null && astrError.equals("msg.txt.picture.upload.success"))
				htmlResponseText=encodeHtml("Picture has been successfully uploaded.");
	    else if (astrError!=null && astrError.trim().length()>0)
	    	htmlResponseText=encodeHtml("Some error occurred while uploading, please try again");
	    
	    
	    
	    //paint the page
	    aprintWriter.println("<html><head>");
	
	    aprintWriter.println("<script type = \"text/javascript\">");
	    aprintWriter.println("alert(\""+htmlResponseText+"\");") ;
	    if((astrError != null) && (astrError.equals("msg.error.picture.size") || astrError.equals("msg.error.choose.valid.picture")))
	    {
	    	aprintWriter.println("parent.notDone();") ;
	    	logger.debug("trying to set firstload to false");
	    }
	    aprintWriter.println("</script>") ;
	    aprintWriter.println("</head>");
	    aprintWriter.println("<body></body>") ;
	    aprintWriter.println("</html>") ;
	    
	    
	    aprintWriter.flush();
	    aprintWriter.close();
		if (logger.isDebugEnabled()) {
			logger.debug("finish displayPage()");
		}
  }//end of displayPage method
  
  /**
	 * This method is Html Encoder. converts the text into html equivelent text
	 *
	 * <P><B>Method Name: encodeHtml</B><BR>
	 * <P><B>Change Log: </B><BR>
	 *
	 * @param String to be encoded
	 * @return HTML encoded string
	 * */
	public String encodeHtml(String astrHtmlText)
	{
		if (logger.isDebugEnabled()) {
			logger.debug("start encodeHtml(String)");
			logger.debug("astrHtmlText: \"" + astrHtmlText + "\"");
		}
		if(astrHtmlText == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("astrHtmlText == null");
				logger.debug("finish encodeHtml()");
				logger.debug("returning: " + null);
			}
			return null;
		}
		// check for exsistance of the special characters ( ASCII values ) in the string
		if(astrHtmlText.indexOf(34, 0) < 0 && astrHtmlText.indexOf(38, 0) < 0 && astrHtmlText.indexOf(60, 0) < 0
			&& astrHtmlText.indexOf(62, 0) < 0 && astrHtmlText.indexOf(39, 0) < 0 && astrHtmlText.indexOf(92, 0) < 0) {
			if (logger.isDebugEnabled()) {
				logger.debug("astrHtmlText.indexOf(34,0) < 0 && astrHtmlText.indexOf(38,0) < 0 && astrHtmlText.indexOf(60,0) < 0 && astrHtmlText.indexOf(62,0) < 0 && astrHtmlText.indexOf(39,0) < 0 && astrHtmlText.indexOf(92,0) < 0");
				logger.debug("finish encodeHtml()");
				logger.debug("returning: " + astrHtmlText);
			}
			return astrHtmlText;
		}
		StringBuffer strbfHtmlText = new StringBuffer(astrHtmlText);
		String strSpecialChar = "\"&<>'\\";
		for(int j = 0; j < strbfHtmlText.length(); j++)
		{
			int iLoopCounter = strSpecialChar.indexOf(strbfHtmlText.charAt(j));
			if(iLoopCounter >= 0)
			{
				strbfHtmlText.setCharAt(j,("&").charAt(0));
				strbfHtmlText.insert(j+1,arrstrHtmlEquivelent[iLoopCounter]);
				j += arrstrHtmlEquivelent[iLoopCounter].length() ;
			}
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("finish encodeHtml()");
			logger.debug("returning: " + new String(strbfHtmlText));
		}
		return new String(strbfHtmlText);
	} // end of method encodeHTML()
	
	/**
	 * This method is Java Script encoder. converts the special character to Java Script
	 * equivalent
	 *
	 * <P><B>Method Name: encodeJavaScript</B><BR>
	 * <P><B>Change Log: </B><BR>
	 *
	 * @param String to be encoded
	 * @return Java Script encoded string
	 * */
	public static String encodeJavaScript(String astrHtmlText)
	{
		if (logger.isDebugEnabled()) {
			logger.debug("start encodeJavaScript(String)");
			logger.debug("astrHtmlText: \"" + astrHtmlText + "\"");
		}
		if(astrHtmlText == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("astrHtmlText == null");
				logger.debug("finish encodeJavaScript()");
				logger.debug("returning: " + null);
			}
			return null;
		}
		// check for existence of the special characters ( ASCII values ) in the string
		if(astrHtmlText.indexOf(34, 0) < 0 && astrHtmlText.indexOf(39, 0) < 0 && astrHtmlText.indexOf(92, 0) < 0) {
			if (logger.isDebugEnabled()) {
				logger.debug("astrHtmlText.indexOf(34,0) < 0 && astrHtmlText.indexOf(39,0) < 0 && astrHtmlText.indexOf(92,0) < 0");
				logger.debug("finish encodeJavaScript()");
				logger.debug("returning: " + astrHtmlText);
			}
			return astrHtmlText;
		}
		StringBuffer strbfHtmlText = new StringBuffer(astrHtmlText);
		String strSpecialChar = "\"'\\";
		
		for(int j = 0; j < strbfHtmlText.length(); j++)
		{
			int iLoopCounter = strSpecialChar.indexOf(strbfHtmlText.charAt(j));
			if(iLoopCounter >= 0)
			{
				strbfHtmlText.setCharAt(j,("\\").charAt(0));
				strbfHtmlText.insert(j+1,arrstrJavaScriptEquivelent[iLoopCounter]);
				j += arrstrHtmlEquivelent[iLoopCounter].length() ;
			}
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("finish encodeJavaScript()");
			logger.debug("returning: " + new String(strbfHtmlText));
		}
		return new String(strbfHtmlText);
	} // end of method encodeJavaScript
	
	private static final String arrstrHtmlEquivelent[] =
		{
			"quot;", "amp;", "lt;", "gt;", "#39;", "#92;"
		};
	
	/**
	 * This are the Java Script equivelent of the special character like .....quote ("), greater than sign ( > ), etc.
	 */
	private static final String arrstrJavaScriptEquivelent[] =
	{
		"\"", "'", "\\"
	};
}//end of UploadPictureServlet

package com.homedepot.hr.gd.hrreview.servlet;

/*
 *
 * This program is proprietary to The Home Depot and is not to be reproduced,
 * used, or disclosed without permission of:
 * The Home Depot
 * 2455 Paces Ferry Road, N.W.
 * Atlanta, GA 30339-4024
 * 
 * File Name: ImageTransmissionServlet.java
 * Application: <ApplicationName>
 *
 * 
 */

//Import Statements

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Blob;

import org.apache.log4j.Logger;

import com.homedepot.hr.gd.hrreview.dao.PhotoDAO;
import com.homedepot.ta.aa.util.TAAAResourceManager;



/**
 * <filename> ImageTransmissionServlet
 * This servlet is accessed from BasicInfo.jsp and is responsible for
 * transmission of images from the appserver to the browser
 * Created: 08/24/01
 *
 * @author Robert Baker
 * @version $Revision:   
 * @see <relatedclassname>
 * 
 * $Log:   V:/vcs/java/hr/gd/SuccessionPlanning/sh/util/ImageTransmissionServlet.java_v  $
 * 
 *    Rev 1.6   15 Sep 2004 07:21:30   nav01
 * Changed URL call to assetLocator
 * 
 *    Rev 1.5   02 Apr 2004 15:48:22   pxd02
 * Retrieved imageCatalog from Database instead of runtime properties
 * 
 *    Rev 1.4   04 Sep 2002 17:02:52   wlc01
 * Removed RTWatchDog Calls
 * 
 *    Rev 1.3   15 Jul 2002 07:36:22   rwb04
 * arch changes
 * 
 *    Rev 1.2   12 Jul 2002 17:56:24   ckwp01
 * Changed image file read logic
 * 
 *    Rev 1.1   08 Jul 2002 11:35:14   ckwp01
 * Image upload/download to MVS
 * 
 *    Rev 1.0   05 Mar 2002 08:13:08   nav01
 * Initial Check In
 * 
 * 
 */

public class ImageTransmissionServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8010913923684210474L;
	private static final Logger logger = Logger
			.getLogger(ImageTransmissionServlet.class);

	
	
		
	public ImageTransmissionServlet(){
		super();
	}
	
	public void doGet(HttpServletRequest req, 
		HttpServletResponse res) throws IOException{
		doPost(req,res);
		
	}//doGet
	
	private String getURLHost() 
	{
		String LCP = TAAAResourceManager.getProperty("host.LCP");
		
		if (LCP.equalsIgnoreCase("PR"))
		{
			return "https://webapps.homedepot.com/";
		}
		else if(LCP.equalsIgnoreCase("QP"))
		{
			return "https://webapps-qp.homedepot.com/";
		}
		else if(LCP.equalsIgnoreCase("QA"))
		{	
			return "https://webapps-qa.homedepot.com/";
		}else if (LCP.equalsIgnoreCase("AD"))
		{
			logger.info("TEST");
			return "http://localhost:8085/" ;
			
		}
		
		return "" ;
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
		throws IOException{
		
		
		
		try {
			 StringBuffer sbString = new StringBuffer();
	         String lcp = TAAAResourceManager.getProperty("host.LCP");
	         lcp = lcp.trim().toUpperCase();
	         String associateID = req.getParameter("ID") ;
	         
	         if (logger.isDebugEnabled())
	         {
	        	 logger.debug("Parameter ID: "+ associateID);
	         }
			
			
			 
			
			URL url = null ;
			HttpURLConnection urlc = null ;
			InputStream dis = null ;
			ServletOutputStream out = null ;
			String urlString = null ;
			boolean noPhoto = false ;
			
			try
			{
				Blob photoBlob = PhotoDAO.getPhoto(associateID);
				
				dis = photoBlob.getBinaryStream();//new DataInputStream(new BufferedInputStream(urlc.getInputStream(),2048));
				out = res.getOutputStream();
				
				
			}catch (Exception e)
			{
				
				logger.info("No Photo for " +associateID+".") ;
				urlString = getURLHost()+ req.getContextPath().substring(1) +"/Images/nophotofound.gif" ;
				noPhoto = true ;
				url = new URL(urlString);
				urlc = (HttpURLConnection)url.openConnection();
				urlc.connect();
				dis = new DataInputStream(new BufferedInputStream(urlc.getInputStream(),2048));
				out = res.getOutputStream();
				
				
			}
			
			
	
									
			int bytesRead = 0;
			byte buffer[] = new byte[2048];
			try
			{
				while( (bytesRead = dis.read(buffer)) > 0 )
					out.write(buffer,0,bytesRead);
				
				
											
			}
			catch(Exception e)
			{
				logger.error(e.toString());
				e.printStackTrace();
				
			}
			
			//if no photo disconnect the upload of the no photo found photo
			if(noPhoto)
			{
				urlc.disconnect();
			}
			
			out.flush();
			out.close();
		}
		
		catch(Exception e) {
			e.printStackTrace();
			throw new IOException(e.getMessage());	
		}
	}//doPost
	
	
} // ImageTransmissionServlet


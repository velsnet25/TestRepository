package com.homedepot.hr.gd.hrreview.bl;

/*
 *
 * This program is proprietary to The Home Depot and is not to be reproduced,
 * used, or disclosed without permission of:
 *    The Home Depot
 *    2455 Paces Ferry Road, N.W.
 *    Atlanta, GA 30339-4024
 * 
 * File Name: $Workfile:   FOPReport.java  $
 * Application: <ApplicationName>
 *
 * 
 */

//Import Statements
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.Hashtable;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.Driver;
import org.apache.log4j.Logger;

import com.homedepot.hr.gd.hrreview.dao.ExcelReportingDAO;
import com.homedepot.hr.gd.hrreview.exceptions.HrReviewApplLogMessage;
import com.homedepot.hr.gd.hrreview.exceptions.HrReviewException;
import com.homedepot.hr.gd.hrreview.interfaces.Constants;
import com.homedepot.hr.gd.hrreview.util.ApplicationAids;
import com.homedepot.hr.gd.hrreview.util.AssocIDConverter;
import com.homedepot.ta.aa.util.TAAAResourceManager;

public class FOPReport extends HttpServlet implements Constants {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ExcelReportingDAO.class);	

	public static byte[] generateAssocProfileReport(String xml,HttpServletResponse response) throws IOException, HrReviewException {

		String assocxml=null;
		byte content[]=null;
		try {
			
			assocxml = xml;

		} catch (NullPointerException ne) {
			ne.printStackTrace();
		}
		
		if (assocxml == null)
			errorDisplay(response);
		else {
			try {
				content=createPdf(assocxml, response);
			} 
			catch (Exception e) {
				
			logger.error(HrReviewApplLogMessage.create(HrReviewApplLogMessage.FATAL_ERROR,
			Constants.ERROR_GENERATE_ASSOCIATEPROFILE,e));
			throw new HrReviewException(Constants.BAD_REQUEST,Constants.ERROR_GENERATE_ASSOCIATEPROFILE, e);
			}
		}
		return content;

	}

	
	public static byte[] createPdf(String xmlS,
			HttpServletResponse response) throws TransformerException,
			IOException {
		
		
		// setup FOP
		Driver driver = new Driver();
		driver.setRenderer(Driver.RENDER_PDF);
		
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		// set the driver outputStream
		driver.setOutputStream(outStream);

		// Make sure the XSL transformation's result is piped through to FOP
		Result res = new SAXResult(driver.getContentHandler());

		// Setup XML input
		Source src = new StreamSource(new StringReader(xmlS));

		// Setup Transformer
		
		ClassLoader classLoader = FOPReport.class.getClassLoader();
        File xslFileName = new File(classLoader.getResource("com/homedepot/hr/gd/hrreview/resources/assocrpt.xsl").getFile());
		Source xsltSrc = new StreamSource(xslFileName);
		
		Transformer transformer = TransformerFactory.newInstance().newTransformer(xsltSrc);
		
		// Start the transformation and rendering process
		transformer.transform(src, res);

		byte[] content = outStream.toByteArray();

		response.setContentLength(content.length);
		// set the MIME-TYPE
		response.setContentType("application/pdf");
		// write the content
		response.getOutputStream().write(content);

		response.getOutputStream().flush();
		// close the pdf file
		// fos.close();
		return content;
	}

	/**
	 * This method returns an error verbage to browser
	 * 
	 *<P>
	 * <B>Method Name:errorDisplay<B><BR>
	 *<P>
	 * <B>Change Log:<B><BR>
	 * 
	 * @param: <{HttpServletResponse}>
	 * @exception: IOException, ServletException
	 */
	private static void errorDisplay(HttpServletResponse response) throws IOException {
		PrintWriter outWriter = null;
		try {
			
			if (outWriter == null)
				outWriter = response.getWriter();
			// build the Error message.
			outWriter.println("<HTML>");
			outWriter
					.println("<P><CENTER><FONT FACE=\"Times New Roman, Times, serif\" SIZE=\"4\">");
			outWriter
					.println("An error occurred while generating your report<P>");
			outWriter.println("Please try again<BR></FONT>");
			outWriter.println("<P><P>");
			outWriter.println("</CENTER>");
			outWriter.println("</HTML>");

			outWriter.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw ioe;
		}
	}
	
} // FOPReport

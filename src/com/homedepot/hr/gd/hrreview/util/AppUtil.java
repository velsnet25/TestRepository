/* 
 * This program is proprietary to The Home Depot and is not to be
 * reproduced, used, or disclosed without permission of:
 *    
 *  The Home Depot
 *  2455 Paces Ferry Road, N.W.
 *  Atlanta, GA 30339-4053
 *
 * File Name: util.java
 * Application: RetailStaffing
 *
 */
package com.homedepot.hr.gd.hrreview.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;

import com.homedepot.hr.gd.hrreview.dto.DateTO;
import com.homedepot.hr.gd.hrreview.exceptions.HrReviewException;
import com.homedepot.hr.gd.hrreview.interfaces.Constants;
import com.homedepot.ta.aa.catalina.realm.Profile;

/**
 * This class consists f methods which will be used commonly
 * 
 * @author 209546
 * 
 */
public class AppUtil implements Constants
{
	private final static Logger logger = Logger.getLogger(AppUtil.class);
	public static final String DATE_FORMAT = "yyyy-MM-dd";

	public static DateTO converTimeStampTOtoDateTO(java.sql.Timestamp timestamp)
	{
		DateTO dateTO = null;
		String msgDataVal = null;
		try
		{
			if(timestamp != null)
			{
				dateTO = new DateTO();
				logger.info("The timestamp obtained is:" + timestamp.toString());
				String timeStampString = timestamp.toString();
				String[] timeStampToks = timeStampString.split(" ");
				logger.info("The token length is" + timeStampToks.length);

				if(timeStampToks != null && timeStampToks.length >= 2)
				{
					String datePart = timeStampToks[0];
					// String timePart = timeStampToks[1];
					if(null!=datePart)
					{
						String[] dateToks = datePart.split("-");
						if(dateToks != null && dateToks.length >= 3)
						{
							dateTO.setYear(dateToks[0]);
							dateTO.setMonth(dateToks[1]);
							dateTO.setDay(dateToks[2]);
						}
					}

				}

				logger.info("The converted timestampTo is:" + dateTO.toString());

			}
		}
		catch (Exception e)
		{
			msgDataVal = "Exception : Error occured in Util.converTimeStampTOtoDateTO()";
			logger.error((Constants.ERROR_PARSE_TIMESTAMP),e);
		}
		return dateTO;

	}

    public static DateTO converDatetoDateTO(java.sql.Date date)
	{
		DateTO dateTO = null;

		if(date != null)
		{
			dateTO = new DateTO();

			String[] dateToks = date.toString().split("-");
			if(dateToks != null && dateToks.length >= 3)
			{
				dateTO.setYear(dateToks[0]);
				dateTO.setMonth(dateToks[1]);
				dateTO.setDay(dateToks[2]);
			}
			logger.info("The converted dateTo is:" + dateTO.toString());
		}

		return dateTO;

	}
    
    public static String convertDateToString(java.sql.Date date)
	{
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	String dateFormatted = null;           
    	
		if (date != null)
			dateFormatted = format.format(date);
		
    	return dateFormatted;
	}
    
    public static String convertTimestampToString(java.sql.Timestamp timestamp)
	{
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    	String dateFormatted = null;           

    	if (timestamp != null)
    			dateFormatted = format.format(timestamp);
    	
    	return dateFormatted;
	}
	
	/**
	 * The method will be used for creating a date object for the given date
	 * string.
	 * 
	 * @param DateTO
	 *            - the given object containing the date details. format.
	 * @return
	 */
	public static java.sql.Date convertDateTO(DateTO dateTO)
	{
		java.sql.Date date = null;
		StringBuilder dateStringBuf = null;
		String msgDataVal = null;
		try
		{
			if(dateTO != null && dateTO.getYear() != null && dateTO.getMonth() != null && dateTO.getDay() != null)
			{
				SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
				dateStringBuf = new StringBuilder();
				dateStringBuf.append(dateTO.getYear());
				dateStringBuf.append("-");
				dateStringBuf.append(dateTO.getMonth());
				dateStringBuf.append("-");
				dateStringBuf.append(dateTO.getDay());
				logger.info("The date string obtained is:" + dateStringBuf.toString());
				java.util.Date parsedDate = dateFormat.parse(dateStringBuf.toString());
				date = new java.sql.Date(parsedDate.getTime());
				logger.info("The converted date is:" + date);
			}
		}
		catch (Exception e)
		{
			msgDataVal = "Exception : Error occured in Util.convertDateTO()";
			//TODO
			//logError(msgDataVal, new RetailStaffingException(RetailStaffingConstants.ERROR_PARSE_DATE));
		}
		return date;
	}
	
	/**
	 * This method checks for the empty string
	 * 
	 * @param string
	 * @return boolean
	 */
	public static boolean isEmptyString(String string) {
		boolean isEmptyString = false;

		if (string == null || "".equals(string.trim())) {
			isEmptyString = true;
		}
		return isEmptyString;
	}
	
	// W2T PDF Report File Name Fix START
    public static String generateRandomInt(){
	    //note a single Random object is reused here
	    Random randomGenerator = new Random();
	    int randomInt = randomGenerator.nextInt(100000);
	    return new Integer(randomInt).toString();
    }
    
    @SuppressWarnings("unused")
	public static String getDuration(Date sDate, Date eDate) {    	
    	String returnValue = null;
		if (sDate != null && eDate != null) {			
			int yearAge = (eDate.getYear() - sDate.getYear());
			int monthAge = eDate.getMonth() - sDate.getMonth();
			if (monthAge < 0)
			{
				yearAge -= 1;
				monthAge = 12 - sDate.getMonth() + eDate.getMonth();
			}
			returnValue = yearAge + " years, " + monthAge + " months";
		}
		return returnValue;
    }
    
    public static String getLdapId(){
    	String userId = null;
    	try {
    		userId = Profile.getCurrent().getProperty(Profile.USER_ID).toUpperCase();    		
    		if (userId == null)
    			userId = "QAT2701";
    	} catch (Exception e) {
    		userId = "QAT2701";
    	}    	
    	return userId; 
    }
    
    public static String convertUtilDateToString(java.util.Date date)
   	{
       	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
       	String dateFormatted = null;           
       	
   		if (date != null)
   			dateFormatted = format.format(date);
   		
       	return dateFormatted;
   	}

}

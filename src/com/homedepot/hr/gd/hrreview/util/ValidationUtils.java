package com.homedepot.hr.gd.hrreview.util;
/* 
 * This program is proprietary to The Home Depot and is not to be
 * reproduced, used, or disclosed without permission of:
 *    
 *  The Home Depot
 *  2455 Paces Ferry Road, N.W.
 *  Atlanta, GA 30339-4053
 *
 * File Name: ValidationUtils.java
 * Application: RetailStaffing
 */

import java.util.Collection;

import org.apache.log4j.Logger;

import com.homedepot.hr.gd.hrreview.enumerations.InputField;
import com.homedepot.hr.gd.hrreview.exceptions.HrReviewApplLogMessage;
import com.homedepot.hr.gd.hrreview.exceptions.HrReviewException;
import com.homedepot.hr.gd.hrreview.interfaces.Constants;
import com.homedepot.hr.gd.hrreview.service.AssociateService;
import com.homedepot.ta.aa.dao.exceptions.ValidationException;


/**
 * This class contains convenience methods used for validation throughout
 * the application
 */
public class ValidationUtils
{
	/* threshold value calendar id's must exceed */
	private static final int CALENDAR_ID_THREHSOLD = 0;
	
	/* threshold value hiring event id's must exceed */
	private static final int HIRE_EVENT_ID_THREHSOLD = 0;
	
	/* threshold value Phone Screen Number must exceed */
	private static final int PHONE_SCREEN_THREHSOLD = 0;
	
	/* threshold value for requisition number */
	private static final int VALID_THRESHOLD_REQUISITION_NUMBER = 0;
	
	/* Regular expression used to validate store number */
	private static final String VALID_REGEX_STORE_NBR = "[0-9a-zA-Z]{4}";
	
	/* language code validation regular expression pattern */
	public static final String VALID_REGEX_LANG_CD = "[a-zA-Z]{2}_[a-zA-Z]{2}";	
	
	private static final Logger mLogger = Logger.getLogger(AssociateService.class);
		
	/**
	 * Convenience method used to validate a version number is supported
	 * 
	 * @param version the version to validate
	 * @param supportedVersions the version(s) supported
	 * 
	 * @throws IllegalArgumentException thrown if no supported versions were provided
	 * @throws ValidationException thrown if the version provided is not supported (a.k.a.
	 * 							   is not one of the supported versions provided)
	 */
	public static void validateVersion(int version, int ... supportedVersions) throws HrReviewException
	{
		try
		{
		// validate that at least one supported version was provided
		if(supportedVersions.length == 0)
		{
			throw new IllegalArgumentException("At least one supported version must be provided");
		} // end if(supportedVersions.length == 0)
		
		boolean supported = false;
		int versionCounter = 0;
		
		do
		{
			/*
			 * Compare the version provided to each version in the supported 
			 * versions array UNTIL the version provided is found to be supported
			 * or we have iterated through each supported version in the array
			 */
			supported = (version == supportedVersions[versionCounter++]);
		} // end do
		while(!supported && (versionCounter < supportedVersions.length));
		
		// if the version provided is not supported, throw an exception
		if(!supported)
		{		
			throw new HrReviewException(Constants.BAD_REQUEST,Constants.ERROR_GET_VERSION_VALIDATION_ERROR_DESC+ String.format("%1$s %2$s is not supported", InputField.VERSION_NUMBER.getFieldLabel(), version));
		} // end if(!supported)
		}
		catch(IllegalArgumentException e)
		{
			throw new HrReviewException(Constants.BAD_REQUEST,Constants.ERROR_GET_ILLEGALARGUMENT_VALIDATION_ERROR_DESC, e);
		}
	} // end function validateVersion()	
	
	/**
	 * Convenience method to validate a store number
	 * 
	 * @param toValidate the store number to validate
	 * 
	 * @throws ValidationException throw if the store number is null, empty, or not exactly four digit characters
	 */
	public static void validateStoreNumber(String toValidate) throws HrReviewException
	{
		try
		{
		validateUsingRegex(InputField.STORE_NUMBER, toValidate, VALID_REGEX_STORE_NBR, false);
		}
		
		catch(IllegalArgumentException e)
		{
			throw new HrReviewException(Constants.BAD_REQUEST,Constants.ERROR_GET_STORENUMBER_VALIDATION_ERROR_DESC+"-"+e.getMessage(), e);
		}
		
	} // end function validateStoreNumber()	
	

	/**
	 * Convenience method to validate a String object using a regular expression pattern
	 * 
	 * @param field							Info about the field being validated
	 * @param toValidate					String object to evaluate
	 * @param regexPattern					Regular Expression pattern to apply
	 * @param maskValue						true if the value provided should be masked in the Exception stack
	 * 										that may be generated, false otherwise
	 * 
	 * @throws IllegalArgumentException		Thrown if the regular expression pattern provided is null or empty
	 * 										of if the InputField provided is null
	 * 
	 * @throws ValidationException			Thrown if the String object provided is null, empty, or does not
	 * 										match the pattern provided
	 */
	public static void validateUsingRegex(InputField field, String toValidate, String regexPattern, boolean maskValue) throws HrReviewException
	{
		// validate the InputField provided is not null
		if(field == null)
		{
			throw new IllegalArgumentException("An InputField must be provided");
		} // end if(field == null)
		
		// validate the regular expression pattern was provided
		if(regexPattern == null || regexPattern.trim().length() == 0)
		{
			throw new IllegalArgumentException("A regular expression pattern must be provided");
		} // end if(regexPattern == null || regexPattern.trim().length() == 0)
		
		// next validate the input field provided is not null or empty
		validateNotNullOrEmpty(field, toValidate);
		
		// finally validate the input field using the pattern provided
		if(!toValidate.trim().matches(regexPattern))
		{
			if(maskValue)
			{
				
				throw new HrReviewException(Constants.BAD_REQUEST,String.format("Invalid %1$s %2$s provided, %1$s must match pattern %3$s",
					field.getFieldLabel(), StringUtils.mask(toValidate), regexPattern));
			} // end if(maskValue)
			else
			{
				
				throw new HrReviewException(Constants.BAD_REQUEST,String.format("Invalid %1$s %2$s provided, %1$s must match pattern %3$s",
					field.getFieldLabel(), toValidate, regexPattern));				
			} // end else
		} // end if(!toValidate.trim().matches(regexPattern))		
		
		
	} // end function validateUsingRegex()	
	
	/**
	 * Convenience method to validate a String object is not null or empty
	 * 
	 * @param field							Info about the field being validated
	 * @param toValidate					String object to evaluate
	 * 
	 * @throws IllegalArgumentException 	Thrown if the InputField provided is null
	 * @throws ValidationException			Thrown if the String object provided is null or empty
	 */
	public static void validateNotNullOrEmpty(InputField field, String toValidate) throws HrReviewException
	{
		// validate the InputField provided is not null
		if(field == null)
		{
			throw new IllegalArgumentException("An InputField must be provided");
		} // end if(field == null)
		
		// validate the String object provided is not null
		validateNotNull(field, toValidate);
		// validate the String object provided is not empty
		if(toValidate.trim().length() == 0)
		{
			throw new HrReviewException(Constants.BAD_REQUEST, String.format("Empty %1$s provided", field.getFieldLabel()));
		} // end if(toValidate.trim().length() == 0)
	} // end function validateNotNullOrEmpty()	
	
	/**
	 * Convenience method to validate a collection object is not null or empty
	 * 
	 * @param field info about the field being validated
	 * @param toValidate collection object to validate
	 * 
	 * @throws IllegalArgumentException thrown if the InputField provided is null
	 * @throws ValidationException Thrown if the collection object provided is null or empty
	 */
	public static void validateNotNullOrEmpty(InputField field, Collection<?> toValidate) throws HrReviewException
	{
		// validate the InputField provided is not null
		if(field == null)
		{
			throw new IllegalArgumentException("An InputField must be provided");
		} // end if(field == null)
		
		// validate the list provided is not null
		validateNotNull(field, toValidate);
		// validate the list provided is not empty
		if(toValidate.isEmpty())
		{
			throw new HrReviewException(Constants.BAD_REQUEST, String.format("Empty %1$s provided", field.getFieldLabel()));
		} // end if(toValidate.isEmpty())
	} // end function validateNotNullOrEmpty()
	
	/**
	 * Convenience method to validate a String object is not null
	 * 
	 * @param field							Info about the field being validated
	 * @param toValidate					Value to evaluate
	 * 
	 * @throws IllegalArgumentException 	Thrown if the InputField provided is null
	 * @throws ValidationException			Thrown if the String object provided is null
	 */
	public static void validateNotNull(InputField field, Object toValidate) throws HrReviewException
	{
		// validate the InputField provided is not null
		if(field == null)
		{
			throw new IllegalArgumentException("An InputField must be provided");
		} // end if(field == null)
		
		// validate the Object provided is not null
		if(toValidate == null)
		{
			throw new HrReviewException(Constants.BAD_REQUEST, String.format("Null %1$s provided", field.getFieldLabel()));
		} // end if(toValidate == null)
	} // end function validateNotNull()	
	
	
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
	
	/**
	 * Convenience method to validate a requisition number
	 * 
	 * @param toValidate the requisition number to validate
	 * 
	 * @throws ValidationException thrown if the requisition number is &lt;= 0
	 */
	public static void validateRequisitionNumber(int toValidate) throws HrReviewException
	{
		try {
			validateGreaterThan(InputField.REQUISITION_NUMBER, toValidate, VALID_THRESHOLD_REQUISITION_NUMBER);
		} catch (ValidationException e) {
			
			mLogger.info("validateRequisitionNumber(): " + Constants.INVALID_INPUT_DESC);
			throw new HrReviewException(Constants.BAD_REQUEST, Constants.INVALID_INPUT_DESC);
			
		}
	} // end function validateRequisitionNumber()
	
	/**
	 * Convenience method to validate a short field value is greater than some threshold
	 * 
	 * @param field							Info about the field being validated
	 * @param toCompare						Value to compare
	 * @param threshold						Threshold the value provided must be greater than
	 * 
	 * @throws IllegalArgumentException 	Thrown if the InputField provided is null
	 * @throws ValidationException			Thrown if the value provided is <= the threshold value provided
	 */
	public static void validateGreaterThan(InputField field, int toCompare, int threshold) throws ValidationException
	{
		// validate the InputField provided is not null
		if(field == null)
		{
			throw new IllegalArgumentException("An InputField must be provided");
		} // end if(field == null)
				
		// if the field to compare is <= threshold, it's an error
		if(toCompare <= threshold)
		{
			throw new ValidationException(field.toString(), String.format("Invalid %1$s provided. %1$s must be greater than %2$d", field.getFieldLabel(), threshold));
		} // end if(toCompare <= threshold)
	} // end function validateGreaterThan()	
	
	
	/**
	 * This method checks for the numeric values
	 * 
	 * @param string
	 * @return boolean
	 * @throws IVRServiceException
	 */

	public static boolean isNumeric(String aString) throws HrReviewException {

		boolean numericValue = true;
		try {

			Integer.parseInt(aString);

		} catch (NumberFormatException nfe) {
			throw new HrReviewException(Constants.BAD_REQUEST,"NumberFormatException occured while parsing the Integer");

          }

		return numericValue;
	}

	
} // end class ValidationUtils
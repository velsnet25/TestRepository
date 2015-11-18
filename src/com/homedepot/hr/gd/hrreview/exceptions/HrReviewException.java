package com.homedepot.hr.gd.hrreview.exceptions;

import org.apache.log4j.Logger;

public class HrReviewException extends Exception {

	private int code;	
	private String status;
	
	/**
	 * Logger instance
	 */
	private static final Logger loggerInstance = Logger.getLogger(HrReviewException.class);
	/**
	 * Serial Id
	 */
	private static final long serialVersionUID = -3103857515154217668L;

	/**
	 * Constructor
	 * 
	 * @param message
	 *            The exception message
	 */

	public HrReviewException(int code, String message) {
		super(message);
		this.code = code;		
		
	}
	
	/**
	 * Constructor
	 * 
	 * @param message
	 *            The exception message
	 */

	public HrReviewException(int code, String message,String status) {
		super(message);
		this.code = code;
		this.setStatus(status);
		
	}

	/**
	 * Constructor
	 * 
	 * @param message
	 *            The exception message
	 * @param cause
	 *            The root cause of the exception
	 */
	public HrReviewException(int code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;	
		
	}

	public HrReviewException(int code,String message,Exception exception) {
		super(message, exception);
		this.code = code;
		
		
	}
	public HrReviewException(int code,String message,Exception exception,boolean isApplLogRequired) {
		super(message, exception);
		this.code = code;	
		
		/**
		 * Insert message into APPL_LOG table 
		 */
		if(isApplLogRequired)
		{
			loggerInstance.error(HrReviewApplLogMessage.create(code, message));
		}
		
	}

	/**
	 * Gets the response code
	 * 
	 * @return short The response code
	 */
	public int getcode() {
		return code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}	

	

}

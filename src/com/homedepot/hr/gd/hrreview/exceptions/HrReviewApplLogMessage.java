package com.homedepot.hr.gd.hrreview.exceptions;

import com.homedepot.hr.gd.hrreview.interfaces.Constants;
import com.homedepot.ta.aa.log4j.ApplLogMessage;
import com.homedepot.ta.aa.util.TAAAResourceManager;

/**
 * The ApplLog class is for writing messages to the APPL_LOG table using the
 * Log4J and THD's ApplLogAppender.
 * <P/>
 * Usage:
 * 
 * <pre>
 * <code>
 *   private static final Logger LOGGER = Logger.getLogger(getClass());
 *   
 *   public void myMethod(String arg0, int arg1 ) {
 *   
 *     try {
 *    
 *       // your code here
 *      
 *     } catch ( Exception e ) {
 *    
 *       // Log to appl log
 *       ApplLogMessage appllog = ApplLogMessage.create(16805, "An error occurred", arg0, arg1);
 *       LOGGER.error(appllog, e);
 *      
 *     }  // end catch
 *   
 *   } // end myMethod
 *      
 * </code>
 * </pre>
 * 
 * 
 */
public final class HrReviewApplLogMessage extends ApplLogMessage {

	// ----------------------------------------------------------------------
	// APPL_LOG Message Numbers
	// ----------------------------------------------------------------------

	/**
	 * APPL_LOG message number for 'No error has occurred'
	 */
	public static final int NO_ERROR = 0;

	/**
	 * APPL_LOG message number for 'A fatal error has occurred'
	 */
	public static final int FATAL_ERROR = 10183;

	/**
	 * APPL_LOG message number for 'Network communication error'
	 */
	public static final int NETWORK_ERROR = 10109;

	/**
	 * APPL_LOG message number for 'SOAP Exception'
	 */
	public static final int SOAP_ERROR = 16764;

	/**
	 * APPL_LOG message number for 'A database error has occurred'
	 */
	public static final int DATABASE_ERROR = 16805;
	
	/**
	 * Create a new instance of an ApplLog message
	 * 
	 * @param messageNumber
	 *            The message number to write to APPL_LOG
	 * @param messageText
	 *            The message text to write to APPL_LOG. The arguments will be
	 *            appended to this message.
	 * @param args
	 *            The arguments passed into the method that is logging the error
	 * @return ApplLog The ApplLog Message to log with Log4j
	 */
	public static HrReviewApplLogMessage create(int messageNumber, String messageText,
			Object... args) {

		HrReviewApplLogMessage appllog = null;
		StringBuilder s = new StringBuilder(messageText);

		try {

			if (args != null && args.length > 0) {

				s.append(": args[");
				boolean first = true;
				for (Object arg : args) {
					if (first) {
						s.append(arg);
						first = false;
					} else {
						s.append(',').append(arg);
					}
				}
				s.append(']');

			}

		} finally {

			appllog = new HrReviewApplLogMessage(messageNumber, s.toString());

			/*
			 * A central appl_log table should have a field for the server
			 * reporting the error, but it does not - so abuse an existing field
			 * (TBL_NM)
			 */
			appllog.setTableName(TAAAResourceManager
					.getProperty("host.HOSTNAME"));
		}

		return appllog;

	}

	/**
	 * Private Constructor - use 'create' method
	 * 
	 * @param messageNumber
	 *            The message number to write to APPL_LOG
	 * @param messageText
	 *            A textual description of the error
	 * 
	 * @see #create(int, String, Object...)
	 */
	private HrReviewApplLogMessage(int messageNumber, String messageText) {
		super(messageNumber, messageText);
	}

	@Override
	public String getProgramID() {
		return Constants.PGM_ID;
	}

	@Override
	public String getSubsystemCode() {
		return Constants.SUB_SYS_CD;
	}

}

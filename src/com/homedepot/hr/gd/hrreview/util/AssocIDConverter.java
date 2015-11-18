package com.homedepot.hr.gd.hrreview.util;

import com.homedepot.hr.gd.hrreview.exceptions.HrReviewException;
import com.homedepot.hr.gd.hrreview.interfaces.Constants;

/*
 *
 * This program is proprietary to The Home Depot and is not to be reproduced,
 * used, or disclosed without permission of:
 *    The Home Depot
 *    2455 Paces Ferry Road, N.W.
 *    Atlanta, GA 30339-4024
 * 
 * File Name: $Workfile:   AssocIDConverter.java  $
 * Application: <ApplicationName>
 *
 * $Header:   V:/vcs/java/hr/gd/SuccessionPlanning/sh/util/AssocIDConverter.java_v   1.2   04 Sep 2002 17:02:46   wlc01  $
 */

//Import Statements

/**
 * <filename>
 *
 * Created: <date>
 *
 * @author <authorname>
 * @version $Revision:   1.2  $    Last Updated: $Date:   04 Sep 2002 17:02:46  $ 
 * @see <relatedclassname>
 * 
 * $Log:   V:/vcs/java/hr/gd/SuccessionPlanning/sh/util/AssocIDConverter.java_v  $
 * 
 *    Rev 1.2   04 Sep 2002 17:02:46   wlc01
 * Removed RTWatchDog Calls
 * 
 *    Rev 1.1   20 Jun 2002 13:57:18   ckwp01
 * Set DEV flag
 * 
 *    Rev 1.0   20 Jun 2002 13:55:22   ckwp01
 * Initial check-in
 * 
 * 
 */

public class AssocIDConverter implements Constants
{
	////Class (static) variables
	public static final String RCS_HEADER = "$Header:   V:/vcs/java/hr/gd/SuccessionPlanning/sh/util/AssocIDConverter.java_v   1.2   04 Sep 2002 17:02:46   wlc01  $";
	public static final String RCS_REVISION = "$Revision:   1.2  $";
  public static final int MAX_ID_LENGTH = 9;
	public static final int MAX_ENCODED_LENGTH = 7;
	public static final int BASE = 16;
  public static final String BASE_VALUES = "0123456789ABCDEF";	
	
	////Instance Variables

	
	public static String encode(String id) throws HrReviewException {
					
	  double curr = 0.0;
		double quotient = 0.0;
		double rounded = 0.0;
		StringBuffer sbuf = new StringBuffer();
		char[] chars = {
    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		'A', 'B', 'C', 'D', 'E', 'F' };
		
		if (id==null)
			return id;
		
		try {
			curr = new Double(id).doubleValue();
		} 
		catch (NumberFormatException nfe) {
//         throw new HrReviewException (FATAL,FATAL_MSG_NBR,"Error: An error occurred while converting ID "
//                     + id + ", ID contains at least one non-numeric character ", nfe);

		}
			
		for (int i=MAX_ENCODED_LENGTH;i>=0;i--) {
			quotient = curr / Math.pow(BASE,i);
			rounded = new Float(Math.rint(quotient)).floatValue();
			if (quotient < rounded)
				rounded = rounded - 1;
			if (rounded >= 1.0) {

				// Since MVS requires first char of all filenames to be
				// alpha, and first digit here will always be between 0
				// and 3, convert 0=A,1=B,2=C,3=D
				if (i==MAX_ENCODED_LENGTH)
				  sbuf.append(chars[new Float(rounded).intValue() + 10]);					
				else
				  sbuf.append(chars[new Float(rounded).intValue()]);
				curr %= Math.pow(BASE,i);
			}
			else {
				if (i==MAX_ENCODED_LENGTH)
					sbuf.append("A");
				else
					sbuf.append("0");  // zero
			}	
		}
		
		return sbuf.toString();
		
	}
	
	public static String decode(String encoded) {
		
		int id = 0;
//		String base16Values = "0123456789ABCDEF";
		StringBuffer sbuf = new StringBuffer();
		String decoded = null;
		
		if (encoded == null)
			return encoded;
		for(int i=0,j=MAX_ENCODED_LENGTH;i<encoded.length();i++,j--) {
			if (i==0)
			  id += ((BASE_VALUES.indexOf(encoded.substring(i,i+1)) - 10) * Math.pow(BASE,j));
			else
				id += ((BASE_VALUES.indexOf(encoded.substring(i,i+1))) * Math.pow(BASE,j));
		}
		
		decoded = Integer.toString(id);
		for (int i= MAX_ID_LENGTH - decoded.length();i>0;i--) {
			sbuf.append("0");  // zero
		}
		sbuf.append(decoded);
		//return Integer.toString(id);
		return sbuf.toString();
		
	}

}

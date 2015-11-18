package com.homedepot.hr.gd.hrreview.util;

import java.util.ResourceBundle;

public class MessageUtil {
	  private static ResourceBundle myResources =
	    ResourceBundle.getBundle("com.homedepot.hr.et.ram.resources.ErrorMessages");
	  
	  public static String getMessageString(String messageKey) {
	    return myResources.getString(messageKey);
	  }
	  
}

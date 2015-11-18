
package com.homedepot.hr.gd.hrreview.util;

//Import Statements

import java.util.Properties;
import java.util.Locale;
import java.io.InputStream;
import java.io.File;

/**
 * The class used for the reading the properties file. All the environment related 
 * attributes are provided by this class, either through the properties file or maybe
 * later through the Resource Bundles.
 * @author Wipro
 * @version 1.0 
 */
public class ApplicationAids
{
    /**
     * Source Control Header 
     */
	public static final String RCS_HEADER = "$Header:   V:/vcs/java/hr/gd/SuccessionPlanning/sh/util/ApplicationAids.java_v   1.2   12 Jun 2002 10:07:52   rwb04  $";

    /**
     * Source Control Revision History 
     */
	public static final String RCS_REVISION = "$Revision:   1.2  $";
	
    /**
     * The synthetic attribute for the Class variable. 
     */
	//private static Class class$com$homedepot$hr$gd$sh$util$ApplicationAids; /* synthetic field */

    /**
     * The Runtime Properties attribute. This is loaded whenever any method is called for the first time 
     */
	private static Properties mPropertiesRunTime = null;

    /**
     * The image location, read from the properties file 
     */
	private static String mImageLocation;

    /**
     * The Javascript location, read from the properties file 
     */
	private static String mJSLocation;

    /**
     * The JSP location, read from the properties file 
     */
	private static String mJspLocation;

	/**
	 * Gets the RunTime properties file for the application.
	 * 
	 * @return   RunTime Properties.    
	 */
	public static Properties getRunTimeProperties()
	{
		//commented out the following block to force RunTime.properties to be loaded
		//on every call
		//if (mPropertiesRunTime != null)
		//	return mPropertiesRunTime;
			
		mPropertiesRunTime = new Properties();
		try
		{
			//gets runtime properties
      InputStream inputstream = ApplicationAids.class.getResourceAsStream("Runtime.properties");
			
      if(inputstream != null)
      {
          mPropertiesRunTime.load(inputstream);
      }
			
			//gets the image location from the runtime properties file
			String value = mPropertiesRunTime.getProperty("sp.image.location");
			if (value != null)
				mImageLocation = value;
			
			value = mPropertiesRunTime.getProperty("sp.js.location");
			if (value != null)
				mJSLocation = value;
			
			//gets the Training Tracking jsp location from the runtime properties file
			value = mPropertiesRunTime.getProperty("sp.jsp.location");
			if (value != null)
				mJspLocation = value;
			
		}
		catch(Exception e)
		{
			fatalError("Error getting run time properties: " + e);
			return null;
		}
		return mPropertiesRunTime;
	}
	
	/**
	 * Gets a specific property from the runtime properties file.
	 * 
	 * @see      ApplicationAids#getRuntimeProperties.
	 * @param    property  runtime property.
	 * @return   requested runtime property value.    
	 */
	public static String getRunTimeProperty(String property)
	{
		getRunTimeProperties();
		if (mPropertiesRunTime == null)
			return "";
		String value = mPropertiesRunTime.getProperty(property);
		if (value == null)
			return "";
		return value;			
	}
	
	/**
	 * Gets the Succesion Planning JSP file.
	 * 
	 * @see      ApplicationAids#getRuntimeProperties.
	 * @param    uri  name of the JSP file
	 * @return   full path of the JSP location.    
	 */
	public static String getJspUri(String uri)
	{
		getRunTimeProperties();
		//appends the JSP directory and the name of the file
		return mJspLocation + uri;
	}
	
	/**
	 * Gets the location of all images for the application.
	 * 
	 * @see      ApplicationAids#getRunTimeProperties.
	 * @return   String directory location of the images.
	 * @param String anstrImageFile
	 */
	public static String getImagePath(String anstrImageFile)
	{
		getRunTimeProperties();
		return mImageLocation + anstrImageFile;
	}
	
    /**
     * Gets the location of all images for the application.
     * @return String directory location of the images. 
     */
	public static String getImagePath()
	{
		getRunTimeProperties();
		return mImageLocation;
	}
	
    /**
     * returns the Javascript path for the js files.
     * @param String astrJSFile
     * @return String returns the Javascript path 
     */
	public static String getJSPath(String astrJSFile)
	{
		getRunTimeProperties();
		return mJSLocation + astrJSFile;
	}
	
    /**
     * Gets the Javascript file location path.
     * @return String returns the javascript path 
     */
	public static String getJSPath()
	{
		getRunTimeProperties();
		return mJSLocation;
	}
	
    /**
     * This method is called if the properties file is not been able to be read.
     * @return void 
     */
	protected static void fatalError(String error)
	{
		System.err.println("Fatal error in system operation.");
		System.err.println("Current directory:" + new File(".").getAbsolutePath());
		System.err.println("Error is: " + error);	
	}
		
	/**
	 * method for loading the class
	 * @return Class 
	 */
		/*
    static Class class$(String s)
    {
        try
        {
            return Class.forName(s);
        }
        catch(ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }
*/

} // ApplicationConstants



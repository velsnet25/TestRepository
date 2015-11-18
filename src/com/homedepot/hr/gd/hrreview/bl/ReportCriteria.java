package com.homedepot.hr.gd.hrreview.bl;

/**
 * @author pxd02
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

import java.util.Locale;

public class ReportCriteria {

	private Locale mLocale = null;
	private String lang_cd = null;
	private String user_ID = null;
	private String access_type = null; // default to admin
	private String criteria = null; // A-all, S-current search, C-current
									// associate
	private String empl_ID = null;
	private String asset_path = null; // image catalog only
	
	/**
	 * This variable holds the Report Code. The following are supported report codes:
	 * 
	 * SPConstants.OP_LOAD_ASSOC_TBL_PROFILE = 7
	 * SPConstants.OP_LOAD_PERF_DEV_SUMMARY  = 23
	 * SPConstants.OP_LOAD_PERF_SCREEN_ELONG = 27
	 * 
	 */
	private int report_code = 0; // Assoc profile = 7

	public ReportCriteria() {
	}

	public ReportCriteria(Locale locale, String user_ID, String access_type,
			String criteria, String empl_ID, String asset_path, int report_code) {
		this.mLocale = locale;
		this.user_ID = user_ID;
		this.access_type = access_type; // default to admin
		this.criteria = criteria; // A-all, S-current search, C-current
									// associate
		this.empl_ID = empl_ID;
		this.asset_path = asset_path; // image catalog only
		this.report_code = report_code; // Assoc profile = 7
	}

	/**
	 * @return
	 */
	public String getAsset_path() {
		return asset_path;
	}

	/**
	 * @return
	 */
	public String getCriteria() {
		return criteria;
	}

	/**
	 * @return
	 */
	public String getEmpl_ID() {
		return empl_ID;
	}

	/**
	 * @return
	 */
	public Locale getLocale() {
		return mLocale;
	}

	/**
	 * @return
	 */
	public String getLang_cd() {
		return lang_cd;
	}

	/**
	 * @return
	 */
	public String getUser_ID() {
		return user_ID;
	}

	/**
	 * @return
	 */
	public int getReport_Code() {
		return report_code;
	}

	/**
	 * @return
	 */
	public String getAccess_Type() {
		return access_type;
	}

	/**
	 * @param string
	 */
	public void setAsset_path(String string) {
		asset_path = string;
	}

	/**
	 * @param string
	 */
	public void setCriteria(String string) {
		criteria = string;
	}

	/**
	 * @param string
	 */
	public void setEmpl_ID(String string) {
		empl_ID = string;
	}

	/**
	 * @param string
	 */
	public void setLocale(Locale locale) {
		mLocale = locale;
	}

	/**
	 * @param string
	 */
	public void setLang_Cd(String string) {
		lang_cd = string;
	}

	/**
	 * @param string
	 */
	public void setUser_ID(String string) {
		user_ID = string;
	}

	/**
	 * @param string
	 */
	public void setReport_Code(int iNum) {
		report_code = iNum;
	}

	/**
	 * @param string
	 */
	public void setAccess_Type(String string) {
		access_type = string;
	}
}

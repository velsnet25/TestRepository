package com.homedepot.hr.gd.hrreview.dto;

import java.sql.Date;

public class ApplyAllAssociateDTO {

	private String zeroEmployeeId;
	private Date effectiveBeginDate;
	private Date effectiveEndDate;
	private String languageCode;
	private String jobTitleDescription;
	private String jobTitleCode;
	private int successionPlanSecurityGroupId;
	private String userId;

	public String getZeroEmployeeId() {
		return zeroEmployeeId;
	}

	public void setZeroEmployeeId(String aValue) {
		zeroEmployeeId = aValue;
	}

	public Date getEffectiveBeginDate() {
		return effectiveBeginDate;
	}

	public void setEffectiveBeginDate(Date aValue) {
		effectiveBeginDate = aValue;
	}

	public Date getEffectiveEndDate() {
		return effectiveEndDate;
	}

	public void setEffectiveEndDate(Date aValue) {
		effectiveEndDate = aValue;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String aValue) {
		languageCode = aValue;
	}

	public String getJobTitleDescription() {
		return jobTitleDescription;
	}

	public void setJobTitleDescription(String aValue) {
		jobTitleDescription = aValue;
	}

	public String getJobTitleCode() {
		return jobTitleCode;
	}

	public void setJobTitleCode(String aValue) {
		jobTitleCode = aValue;
	}

	public int getSuccessionPlanSecurityGroupId() {
		return successionPlanSecurityGroupId;
	}

	public void setSuccessionPlanSecurityGroupId(int aValue) {
		successionPlanSecurityGroupId = aValue;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String aValue) {
		userId = aValue;
	}
}

package com.homedepot.hr.gd.hrreview.dto;

import java.sql.Date;

public class WorkHistoryHomeDepotPre95DTO {

	private String humanResourcesSystemEmployeeId;
	private short sequenceNumber;
	private Date workBeginDate;
	private Date workEndDate;
	private String employmentHistoryTypeIndicator;
	private String employerName;
	private Short successionPlanIndustryTypeCode;
	private String jobTitleCode;
	private String overrideJobTitleDescription;
	private String divisionName;
	private String actionDescription;
	private String zeroEmplid;
	private String duration;
	

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getHumanResourcesSystemEmployeeId() {
		return humanResourcesSystemEmployeeId;
	}

	public void setHumanResourcesSystemEmployeeId(String aValue) {
		humanResourcesSystemEmployeeId = aValue;
	}

	public short getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(short aValue) {
		sequenceNumber = aValue;
	}

	public Date getWorkBeginDate() {
		return workBeginDate;
	}

	public void setWorkBeginDate(Date aValue) {
		workBeginDate = aValue;
	}

	public Date getWorkEndDate() {
		return workEndDate;
	}

	public void setWorkEndDate(Date aValue) {
		workEndDate = aValue;
	}

	public String getEmploymentHistoryTypeIndicator() {
		return employmentHistoryTypeIndicator;
	}

	public void setEmploymentHistoryTypeIndicator(String aValue) {
		employmentHistoryTypeIndicator = aValue;
	}

	public String getEmployerName() {
		return employerName;
	}

	public void setEmployerName(String aValue) {
		employerName = aValue;
	}

	public Short getSuccessionPlanIndustryTypeCode() {
		return successionPlanIndustryTypeCode;
	}

	public void setSuccessionPlanIndustryTypeCode(Short aValue) {
		successionPlanIndustryTypeCode = aValue;
	}

	public String getJobTitleCode() {
		return jobTitleCode;
	}

	public void setJobTitleCode(String aValue) {
		jobTitleCode = aValue;
	}

	public String getOverrideJobTitleDescription() {
		return overrideJobTitleDescription;
	}

	public void setOverrideJobTitleDescription(String aValue) {
		overrideJobTitleDescription = aValue;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String aValue) {
		divisionName = aValue;
	}

	public String getActionDescription() {
		return actionDescription;
	}

	public void setActionDescription(String aValue) {
		actionDescription = aValue;
	}

	public String getZeroEmplid() {
		return zeroEmplid;
	}

	public void setZeroEmplid(String aValue) {
		zeroEmplid = aValue;
	}
}

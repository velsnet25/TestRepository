package com.homedepot.hr.gd.hrreview.dto;

import java.sql.Date;

public class CourseDTO {

	private short sequenceNumber;
	private String successionPlanCourseDescription;
	private String overrideSchoolName;
	private Date beginDate;
	private Date endDate;
	private String zeroEmployeeId;

	public short getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(short aValue) {
		sequenceNumber = aValue;
	}

	public String getSuccessionPlanCourseDescription() {
		return successionPlanCourseDescription;
	}

	public void setSuccessionPlanCourseDescription(String aValue) {
		successionPlanCourseDescription = aValue;
	}

	public String getOverrideSchoolName() {
		return overrideSchoolName;
	}

	public void setOverrideSchoolName(String aValue) {
		overrideSchoolName = aValue;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date aValue) {
		beginDate = aValue;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date aValue) {
		endDate = aValue;
	}

	public String getZeroEmployeeId() {
		return zeroEmployeeId;
	}

	public void setZeroEmployeeId(String aValue) {
		zeroEmployeeId = aValue;
	}
}

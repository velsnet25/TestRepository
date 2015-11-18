package com.homedepot.hr.gd.hrreview.dto;

public class SchoolDTO {

	private short sequenceNumber;
	private String stateCode;
	private String countryCode;
	private String overrideFacilityLocationText;
	private String overrideSchoolTypeCode;
	private short educationFacilityTypeCode;
	private String educationFacilityId;
	private String facilityName;
	private String overrideSchoolName;
	private short completedYearCount;
	private String degreeTypeCode;
	private String overrideDegreeDescription;
	private String collegeMajorCode;
	private String overrideCollegeMajorDescription;
	private Short schoolGraduateYear;
	private Boolean degreeCompletedFlag;
	private String zeroEmployeeId;

	public short getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(short aValue) {
		sequenceNumber = aValue;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String aValue) {
		stateCode = aValue;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String aValue) {
		countryCode = aValue;
	}

	public String getOverrideFacilityLocationText() {
		return overrideFacilityLocationText;
	}

	public void setOverrideFacilityLocationText(String aValue) {
		overrideFacilityLocationText = aValue;
	}

	public String getOverrideSchoolTypeCode() {
		return overrideSchoolTypeCode;
	}

	public void setOverrideSchoolTypeCode(String aValue) {
		if (aValue != null)
			overrideSchoolTypeCode = aValue.trim();
	}

	public short getEducationFacilityTypeCode() {
		return educationFacilityTypeCode;
	}

	public void setEducationFacilityTypeCode(short aValue) {
		educationFacilityTypeCode = aValue;
	}

	public String getEducationFacilityId() {
		return educationFacilityId;
	}

	public void setEducationFacilityId(String aValue) {
		educationFacilityId = aValue;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String aValue) {
		facilityName = aValue;
	}

	public String getOverrideSchoolName() {
		return overrideSchoolName;
	}

	public void setOverrideSchoolName(String aValue) {
		overrideSchoolName = aValue;
	}

	public short getCompletedYearCount() {
		return completedYearCount;
	}

	public void setCompletedYearCount(short aValue) {
		completedYearCount = aValue;
	}

	public String getDegreeTypeCode() {
		return degreeTypeCode;
	}

	public void setDegreeTypeCode(String aValue) {
		degreeTypeCode = aValue;
	}

	public String getOverrideDegreeDescription() {
		return overrideDegreeDescription;
	}

	public void setOverrideDegreeDescription(String aValue) {
		overrideDegreeDescription = aValue;
	}

	public String getCollegeMajorCode() {
		return collegeMajorCode;
	}

	public void setCollegeMajorCode(String aValue) {
		collegeMajorCode = aValue;
	}

	public String getOverrideCollegeMajorDescription() {
		return overrideCollegeMajorDescription;
	}

	public void setOverrideCollegeMajorDescription(String aValue) {
		overrideCollegeMajorDescription = aValue;
	}

	public Short getSchoolGraduateYear() {
		return schoolGraduateYear;
	}

	public void setSchoolGraduateYear(Short aValue) {
		schoolGraduateYear = aValue;
	}

	public Boolean getDegreeCompletedFlag() {
		return degreeCompletedFlag;
	}

	public void setDegreeCompletedFlag(Boolean aValue) {
		degreeCompletedFlag = aValue;
	}

	public String getZeroEmployeeId() {
		return zeroEmployeeId;
	}

	public void setZeroEmployeeId(String aValue) {
		zeroEmployeeId = aValue;
	}
}

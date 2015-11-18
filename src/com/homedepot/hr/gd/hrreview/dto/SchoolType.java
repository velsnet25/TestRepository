package com.homedepot.hr.gd.hrreview.dto;

import java.sql.Timestamp;

public class SchoolType {

	private short schoolTypeCode;
	private String languageCode;
	private String lastUpdateUserId;
	private Timestamp lastUpdateTimestamp;
	private String dSchoolTypeCode;
	private String shortSchoolTypeDescription;
	private String schoolTypeDescription;

	public short getSchoolTypeCode() {
		return schoolTypeCode;
	}

	public void setSchoolTypeCode(short aValue) {
		schoolTypeCode = aValue;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String aValue) {
		languageCode = aValue;
	}

	public String getLastUpdateUserId() {
		return lastUpdateUserId;
	}

	public void setLastUpdateUserId(String aValue) {
		lastUpdateUserId = aValue;
	}

	public Timestamp getLastUpdateTimestamp() {
		return lastUpdateTimestamp;
	}

	public void setLastUpdateTimestamp(Timestamp aValue) {
		lastUpdateTimestamp = aValue;
	}

	public String getDSchoolTypeCode() {
		return dSchoolTypeCode;
	}

	public void setDSchoolTypeCode(String aValue) {
		dSchoolTypeCode = aValue;
	}

	public String getShortSchoolTypeDescription() {
		return shortSchoolTypeDescription;
	}

	public void setShortSchoolTypeDescription(String aValue) {
		shortSchoolTypeDescription = aValue;
	}

	public String getSchoolTypeDescription() {
		return schoolTypeDescription;
	}

	public void setSchoolTypeDescription(String aValue) {
		schoolTypeDescription = aValue;
	}
}

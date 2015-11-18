package com.homedepot.hr.gd.hrreview.dto;

import java.sql.Timestamp;

public class LanguageTypeDTO {

	private short languageUseTypeCode;
	private String languageCode;
	private String lastUpdateUserId;
	private Timestamp lastUpdateTimestamp;
	private String dLanguageUseTypeCode;
	private String languageUseTypeDescription;

	public short getLanguageUseTypeCode() {
		return languageUseTypeCode;
	}

	public void setLanguageUseTypeCode(short aValue) {
		languageUseTypeCode = aValue;
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

	public String getDLanguageUseTypeCode() {
		return dLanguageUseTypeCode;
	}

	public void setDLanguageUseTypeCode(String aValue) {
		dLanguageUseTypeCode = aValue;
	}

	public String getLanguageUseTypeDescription() {
		return languageUseTypeDescription;
	}

	public void setLanguageUseTypeDescription(String aValue) {
		languageUseTypeDescription = aValue;
	}
}

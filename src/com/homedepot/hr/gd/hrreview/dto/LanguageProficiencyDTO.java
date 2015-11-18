package com.homedepot.hr.gd.hrreview.dto;

import java.sql.Timestamp;

public class LanguageProficiencyDTO {

	private String languageProficiencyCode;
	private String languageCode;
	private String lastUpdateUserId;
	private Timestamp lastUpdateTimestamp;
	private String dLanguageProficiencyCode;
	private String shortLanguageProficiencyDescription;
	private String languageProficiencyDescription;

	public String getLanguageProficiencyCode() {
		return languageProficiencyCode;
	}

	public void setLanguageProficiencyCode(String aValue) {
		languageProficiencyCode = aValue;
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

	public String getDLanguageProficiencyCode() {
		return dLanguageProficiencyCode;
	}

	public void setDLanguageProficiencyCode(String aValue) {
		dLanguageProficiencyCode = aValue;
	}

	public String getShortLanguageProficiencyDescription() {
		return shortLanguageProficiencyDescription;
	}

	public void setShortLanguageProficiencyDescription(String aValue) {
		shortLanguageProficiencyDescription = aValue;
	}

	public String getLanguageProficiencyDescription() {
		return languageProficiencyDescription;
	}

	public void setLanguageProficiencyDescription(String aValue) {
		languageProficiencyDescription = aValue;
	}
}

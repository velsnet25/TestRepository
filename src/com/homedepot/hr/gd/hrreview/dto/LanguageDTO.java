package com.homedepot.hr.gd.hrreview.dto;

public class LanguageDTO {

	private short languageUseTypeCode;
	private String internationalOrganizationForStandardsLanguageCode;
	private String overrideInternationalOrganizationForStandardsLanguageName;
	private String languageProficiencyCode;
	private short languageSequenceNumber;
	private boolean preferenceLanguageFlag;
	private String zeroEmployeeId;

	public short getLanguageUseTypeCode() {
		return languageUseTypeCode;
	}

	public void setLanguageUseTypeCode(short aValue) {
		languageUseTypeCode = aValue;
	}

	public String getInternationalOrganizationForStandardsLanguageCode() {
		return internationalOrganizationForStandardsLanguageCode;
	}

	public void setInternationalOrganizationForStandardsLanguageCode(
			String aValue) {
		internationalOrganizationForStandardsLanguageCode = aValue;
	}

	public String getOverrideInternationalOrganizationForStandardsLanguageName() {
		return overrideInternationalOrganizationForStandardsLanguageName;
	}

	public void setOverrideInternationalOrganizationForStandardsLanguageName(
			String aValue) {
		overrideInternationalOrganizationForStandardsLanguageName = aValue;
	}

	public String getLanguageProficiencyCode() {
		return languageProficiencyCode;
	}

	public void setLanguageProficiencyCode(String aValue) {
		languageProficiencyCode = aValue;
	}

	public short getLanguageSequenceNumber() {
		return languageSequenceNumber;
	}

	public void setLanguageSequenceNumber(short aValue) {
		languageSequenceNumber = aValue;
	}

	public boolean getPreferenceLanguageFlag() {
		return preferenceLanguageFlag;
	}

	public void setPreferenceLanguageFlag(boolean aValue) {
		preferenceLanguageFlag = aValue;
	}

	public String getZeroEmployeeId() {
		return zeroEmployeeId;
	}

	public void setZeroEmployeeId(String aValue) {
		zeroEmployeeId = aValue;
	}
}

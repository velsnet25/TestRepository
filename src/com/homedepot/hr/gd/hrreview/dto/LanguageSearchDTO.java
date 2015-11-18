package com.homedepot.hr.gd.hrreview.dto;

public class LanguageSearchDTO {

	private String internationalOrganizationForStandardsLanguageCode;
	private String internationalOrganizationForStandardsLanguageName;

	public String getInternationalOrganizationForStandardsLanguageCode() {
		return internationalOrganizationForStandardsLanguageCode;
	}

	public void setInternationalOrganizationForStandardsLanguageCode(
			String aValue) {
		internationalOrganizationForStandardsLanguageCode = aValue.trim();
	}

	public String getInternationalOrganizationForStandardsLanguageName() {
		return internationalOrganizationForStandardsLanguageName;
	}

	public void setInternationalOrganizationForStandardsLanguageName(
			String aValue) {
		internationalOrganizationForStandardsLanguageName = aValue.trim();
		internationalOrganizationForStandardsLanguageName = internationalOrganizationForStandardsLanguageName.replaceAll("\"", "");		
	}

}

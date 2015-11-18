package com.homedepot.hr.gd.hrreview.dto;

public class IndustryDetailsDTO {

	private short successionPlanIndustryTypeCode;
	private String successionPlanIndustryTypeDescription;
	private String languageCode;

	public short getSuccessionPlanIndustryTypeCode() {
		return successionPlanIndustryTypeCode;
	}

	public void setSuccessionPlanIndustryTypeCode(short aValue) {
		successionPlanIndustryTypeCode = aValue;
	}

	public String getSuccessionPlanIndustryTypeDescription() {
		return successionPlanIndustryTypeDescription;
	}

	public void setSuccessionPlanIndustryTypeDescription(String aValue) {
		successionPlanIndustryTypeDescription = aValue;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String aValue) {
		languageCode = aValue;
	}
}

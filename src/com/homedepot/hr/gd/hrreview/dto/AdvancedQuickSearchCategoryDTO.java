package com.homedepot.hr.gd.hrreview.dto;

public class AdvancedQuickSearchCategoryDTO {

	private int successionPlanQueryId;
	private String languageCode;
	private String successionPlanQueryDescription;
	private boolean publicFlag;

	public int getSuccessionPlanQueryId() {
		return successionPlanQueryId;
	}

	public void setSuccessionPlanQueryId(int aValue) {
		successionPlanQueryId = aValue;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String aValue) {
		languageCode = aValue;
	}

	public String getSuccessionPlanQueryDescription() {
		return successionPlanQueryDescription;
	}

	public void setSuccessionPlanQueryDescription(String aValue) {
		successionPlanQueryDescription = aValue.trim();
	}

	public boolean getPublicFlag() {
		return publicFlag;
	}

	public void setPublicFlag(boolean aValue) {
		publicFlag = aValue;
	}
}

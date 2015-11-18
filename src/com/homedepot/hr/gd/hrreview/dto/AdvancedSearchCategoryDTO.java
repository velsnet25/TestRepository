package com.homedepot.hr.gd.hrreview.dto;

public class AdvancedSearchCategoryDTO {

	private int successionPlanColumnId;
	private String dataTypeIndicator;
	private boolean viewAllowFlag;
	private boolean criteriaAllowFlag;
	private String columnName;
	private String tableName;
	private String opticalSourceText;
	private String languageCode;
	private String promptText;

	public int getSuccessionPlanColumnId() {
		return successionPlanColumnId;
	}

	public void setSuccessionPlanColumnId(int aValue) {
		successionPlanColumnId = aValue;
	}

	public String getDataTypeIndicator() {
		return dataTypeIndicator;
	}

	public void setDataTypeIndicator(String aValue) {
		dataTypeIndicator = aValue;
	}

	public boolean getViewAllowFlag() {
		return viewAllowFlag;
	}

	public void setViewAllowFlag(boolean aValue) {
		viewAllowFlag = aValue;
	}

	public boolean getCriteriaAllowFlag() {
		return criteriaAllowFlag;
	}

	public void setCriteriaAllowFlag(boolean aValue) {
		criteriaAllowFlag = aValue;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String aValue) {
		columnName = aValue;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String aValue) {
		tableName = aValue;
	}

	public String getOpticalSourceText() {
		return opticalSourceText;
	}

	public void setOpticalSourceText(String aValue) {
		opticalSourceText = aValue;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String aValue) {
		languageCode = aValue;
	}

	public String getPromptText() {
		return promptText;
	}

	public void setPromptText(String aValue) {
		promptText = aValue;
	}
}

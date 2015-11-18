package com.homedepot.hr.gd.hrreview.dto;

public class LoadQueryDTO {

	private int successionPlanQueryId;
	private String successionPlanQueryName;
	private int successionPlanColumnId;
	private String openParenthesesValue;
	private String operatorText;
	private String parameterValue;
	private String connectOperatorText;
	private String closeParenthesesValue;
	private String createUserId;
	private boolean publicFlag;

	public int getSuccessionPlanQueryId() {
		return successionPlanQueryId;
	}

	public void setSuccessionPlanQueryId(int aValue) {
		successionPlanQueryId = aValue;
	}

	public String getSuccessionPlanQueryName() {
		return successionPlanQueryName;
	}

	public void setSuccessionPlanQueryName(String aValue) {
		successionPlanQueryName = aValue;
	}

	public int getSuccessionPlanColumnId() {
		return successionPlanColumnId;
	}

	public void setSuccessionPlanColumnId(int aValue) {
		successionPlanColumnId = aValue;
	}

	public String getOpenParenthesesValue() {
		return openParenthesesValue;
	}

	public void setOpenParenthesesValue(String aValue) {
		openParenthesesValue = aValue;
	}

	public String getOperatorText() {
		return operatorText;
	}

	public void setOperatorText(String aValue) {
		operatorText = aValue;
	}

	public String getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(String aValue) {
		parameterValue = aValue;
	}

	public String getConnectOperatorText() {
		return connectOperatorText;
	}

	public void setConnectOperatorText(String aValue) {
		connectOperatorText = aValue;
	}

	public String getCloseParenthesesValue() {
		return closeParenthesesValue;
	}

	public void setCloseParenthesesValue(String aValue) {
		closeParenthesesValue = aValue;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String aValue) {
		createUserId = aValue;
	}

	public boolean getPublicFlag() {
		return publicFlag;
	}

	public void setPublicFlag(boolean aValue) {
		publicFlag = aValue;
	}
}

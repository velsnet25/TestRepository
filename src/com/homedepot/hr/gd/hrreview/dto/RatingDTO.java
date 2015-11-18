package com.homedepot.hr.gd.hrreview.dto;

import java.sql.Date;

public class RatingDTO {

	private Date evaluateDate;
	private String evaluateCategoryDescription;
	private String successionPlanNineBoxGridDescription;
	private String evaluateRatingDescription;
	private short evaluateCategoryCode;
	private String languageCode;
	private String zeroEmplid;

	public Date getEvaluateDate() {
		return evaluateDate;
	}

	public void setEvaluateDate(Date aValue) {
		evaluateDate = aValue;
	}

	public String getEvaluateCategoryDescription() {
		return evaluateCategoryDescription;
	}

	public void setEvaluateCategoryDescription(String aValue) {
		evaluateCategoryDescription = aValue;
	}

	public String getSuccessionPlanNineBoxGridDescription() {
		return successionPlanNineBoxGridDescription;
	}

	public void setSuccessionPlanNineBoxGridDescription(String aValue) {
		successionPlanNineBoxGridDescription = aValue;
	}

	public String getEvaluateRatingDescription() {
		return evaluateRatingDescription;
	}

	public void setEvaluateRatingDescription(String aValue) {
		evaluateRatingDescription = aValue;
	}

	public short getEvaluateCategoryCode() {
		return evaluateCategoryCode;
	}

	public void setEvaluateCategoryCode(short aValue) {
		evaluateCategoryCode = aValue;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String aValue) {
		languageCode = aValue;
	}

	public String getZeroEmplid() {
		return zeroEmplid;
	}

	public void setZeroEmplid(String aValue) {
		zeroEmplid = aValue;
	}
}

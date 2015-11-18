package com.homedepot.hr.gd.hrreview.dto;

public class PotentialPerformanceSearchDTO {

	private String id;
	private short categoryCode;
	private String description;
	private short evaluateRatingCode;
	
	

	public short getEvaluateRatingCode() {
		return evaluateRatingCode;
	}

	public void setEvaluateRatingCode(short evaluateRatingCode) {
		this.evaluateRatingCode = evaluateRatingCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String aValue) {
		if (aValue != null)
			id = aValue.trim();
	}

	public short getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(short aValue) {
		categoryCode = aValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String aValue) {
		description = aValue;
	}
}

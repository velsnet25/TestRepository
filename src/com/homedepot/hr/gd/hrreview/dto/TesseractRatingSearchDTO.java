package com.homedepot.hr.gd.hrreview.dto;

public class TesseractRatingSearchDTO {

	private String effectiveDate;
	//private short sequence;
	private String performance;
	private String performanceDescription;
	private String potential;
	private String potentialDescription;
	private String leadership;
	private String leadershipDescription;
	
	private String nineBoxGridDescription;
	private String reviewRate;

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String aValue) {
		effectiveDate = aValue;
	}

//	public short getSequence() {
//		return sequence;
//	}
//
//	public void setSequence(short aValue) {
//		sequence = aValue;
//	}

	public String getPerformance() {
		return performance;
	}

	public void setPerformance(String aValue) {
		performance = aValue;
	}

	public String getPerformanceDescription() {
		return performanceDescription;
	}

	public void setPerformanceDescription(String aValue) {
		performanceDescription = aValue;
	}

	public String getPotential() {
		return potential;
	}

	public void setPotential(String aValue) {
		potential = aValue;
	}

	public String getPotentialDescription() {
		return potentialDescription;
	}

	public void setPotentialDescription(String aValue) {
		potentialDescription = aValue;
	}

	public String getLeadership() {
		return leadership;
	}

	public void setLeadership(String aValue) {
		leadership = aValue;
	}

	public String getLeadershipDescription() {
		return leadershipDescription;
	}

	public void setLeadershipDescription(String aValue) {
		leadershipDescription = aValue;
	}

	public String getNineBoxGridDescription() {
		return nineBoxGridDescription;
	}

	public void setNineBoxGridDescription(String nineBoxGridDescription) {
		this.nineBoxGridDescription = nineBoxGridDescription;
	}

	public String getReviewRate() {
		return reviewRate;
	}

	public void setReviewRate(String reviewRate) {
		this.reviewRate = reviewRate;
	}
}

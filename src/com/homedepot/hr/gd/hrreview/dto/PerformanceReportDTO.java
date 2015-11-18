package com.homedepot.hr.gd.hrreview.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class PerformanceReportDTO {
	@XStreamAlias("PerformanceReportDTO")
	private String associateID; 
	private String firstName;
	private String lastName; 
	private String businessName;
	private String jobTtlDesc;
	private String evalCatgryCode;
	private String reviewDtlTxt;
	private String assocRevID;
	private String revTypDesc;
	private String splnGstatDesc;
	private String perfEvalRtgCode;
	private String leadershipEvalRtgCode;
	private String potentialEvalRtgCode;
	private String revSectCode;
	private String dispEvalRtgCd;
	//private String nEvalRtgCd;
	private String evalRtgCd;
	
	
		
	public String getAssociateID() {
		return associateID;
	}
	public void setAssociateID(String associateID) {
		this.associateID = associateID;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getJobTtlDesc() {
		return jobTtlDesc;
	}
	public void setJobTtlDesc(String jobTtlDesc) {
		this.jobTtlDesc = jobTtlDesc;
	}
	public String getEvalCatgryCode() {
		return evalCatgryCode;
	}
	public void setEvalCatgryCode(String evalCatgryCode) {
		this.evalCatgryCode = evalCatgryCode;
	}
	public String getReviewDtlTxt() {
		return reviewDtlTxt;
	}
	public void setReviewDtlTxt(String reviewDtlTxt) {
		this.reviewDtlTxt = reviewDtlTxt;
	}
	public String getAssocRevID() {
		return assocRevID;
	}
	public void setAssocRevID(String assocRevID) {
		this.assocRevID = assocRevID;
	}
	public String getRevTypDesc() {
		return revTypDesc;
	}
	public void setRevTypDesc(String revTypDesc) {
		this.revTypDesc = revTypDesc;
	}
	public String getSplnGstatDesc() {
		return splnGstatDesc;
	}
	public void setSplnGstatDesc(String splnGstatDesc) {
		this.splnGstatDesc = splnGstatDesc;
	}
	public String getPerfEvalRtgCode() {
		return perfEvalRtgCode;
	}
	public void setPerfEvalRtgCode(String perfEvalRtgCode) {
		this.perfEvalRtgCode = perfEvalRtgCode;
	}
	public String getLeadershipEvalRtgCode() {
		return leadershipEvalRtgCode;
	}
	public void setLeadershipEvalRtgCode(String leadershipEvalRtgCode) {
		this.leadershipEvalRtgCode = leadershipEvalRtgCode;
	}
	public String getPotentialEvalRtgCode() {
		return potentialEvalRtgCode;
	}
	public void setPotentialEvalRtgCode(String potentialEvalRtgCode) {
		this.potentialEvalRtgCode = potentialEvalRtgCode;
	}
	public String getRevSectCode() {
		return revSectCode;
	}
	public void setRevSectCode(String revSectCode) {
		this.revSectCode = revSectCode;
	}
	public String getDispEvalRtgCd() {
		return dispEvalRtgCd;
	}
	public void setDispEvalRtgCd(String dispEvalRtgCd) {
		this.dispEvalRtgCd = dispEvalRtgCd;
	}
	public String getnEvalRtgCd() {
		return evalRtgCd;
	}
	public void setnEvalRtgCd(String nEvalRtgCd) {
		this.evalRtgCd = nEvalRtgCd;
	}
	
	

}

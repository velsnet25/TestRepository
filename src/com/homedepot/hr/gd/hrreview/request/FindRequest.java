package com.homedepot.hr.gd.hrreview.request;

import javax.ws.rs.QueryParam;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("FindRequest")
public class FindRequest {

	private String nameLast;
	private String associateId;
	private String nameContain;
	private String firstNameStartsWith;
	private String preferredNameStartsWith;
	private String jobTitle;
	private String storeNo;
	private String lastfirstName;
	
	public String getNameLast() {
		return nameLast;
	}
	public void setNameLast(String nameLast) {
		this.nameLast = nameLast;
	}
	public String getAssociateId() {
		return associateId;
	}
	public void setAssociateId(String associateId) {
		this.associateId = associateId;
	}
	public String getNameContain() {
		return nameContain;
	}
	public void setNameContain(String nameContain) {
		this.nameContain = nameContain;
	}
	public String getFirstNameStartsWith() {
		return firstNameStartsWith;
	}
	public void setFirstNameStartsWith(String firstNameStartsWith) {
		this.firstNameStartsWith = firstNameStartsWith;
	}
	public String getPreferredNameStartsWith() {
		return preferredNameStartsWith;
	}
	public void setPreferredNameStartsWith(String preferredNameStartsWith) {
		this.preferredNameStartsWith = preferredNameStartsWith;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getStoreNo() {
		return storeNo;
	}
	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}
	public String getLastfirstName() {
		return lastfirstName;
	}
	public void setLastfirstName(String lastfirstName) {
		this.lastfirstName = lastfirstName;
	}
	
}

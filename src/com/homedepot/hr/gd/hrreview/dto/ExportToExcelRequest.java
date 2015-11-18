package com.homedepot.hr.gd.hrreview.dto;

import java.io.Serializable;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ExportToExcelRequest")
public class ExportToExcelRequest 
{
	private static final long serialVersionUID = 362498820763181265L;
	
	private String reportCategory;
	private String ratingsFlag;
	private List<AssociateList> associateList;
	
	public String getReportCategory() {
		return reportCategory;
	}
	public void setReportCategory(String reportCategory) {
		this.reportCategory = reportCategory;
	}
	public String getRatingsFlag() {
		return ratingsFlag;
	}
	public void setRatingsFlag(String ratingsFlag) {
		this.ratingsFlag = ratingsFlag;
	}
	public List<AssociateList> getAssociateList() {
		return associateList;
	}
	public void setAssociateList(List<AssociateList> associateList) {
		this.associateList = associateList;
	}
	
	

}

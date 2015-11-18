package com.homedepot.hr.gd.hrreview.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class AssociateProfileDTO {
	@XStreamAlias("AssociateProfileDTO")
	
	private String associateID; 
	private String firstName;
	private String lastName; 
	private String jobTitleDesc;
	private String divisonName;
	private String region;
	private String location;
	private String deptName;
	private String dateHired;
	private String effDate;
	private String strNbr;
	private String ctznCntryCode;
	
	//Education
	private String schoolGradYr;
	private String facName;
	private String locText;
	private String degreeDesc;
	private String collegeMjr;
	private String gradeYesNo;
	private String school;
	
	//Course
	private String lastUpdTs;
	private String splnCourseDesc;
	private String ovrdSchoolName;
	private String courseEndDate;
	
	//EE_POSITIONS_T
	private String jobTitleCode;
	private String divisionCode;
	//private String strNbr;
	private String jobTitle;
	//private String divisonName;
	private String startDate;
	private String endDate;
	
	//SPEMPL_EMPLT_HIST 
	private String ovrdJobTtlDesc;
	private String emplyrName;
	private String wrkBgnDate;
	private String wrkEndDate;
	
	//SPEMPL_INDV_PLN
	private String plnLastUpdTs;
	//private String grpSplJtgrpDesc;
	private String position;
	private String splnGstatDesc;
	
	//
	private String typeCd;
	private String reloLastUpdTs;
	private String reloDesription;
	private String dependson;
	
	//SPEMPL_LANG 
	
    private String langUseTypCd;
    private String langLastUpdTs;
    private String language;
    private String prefLangFlg;
    private String langUseTypDesc;
    private String langPrfncyDesc;
    
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
	public String getJobTitleDesc() {
		return jobTitleDesc;
	}
	public void setJobTitleDesc(String jobTitleDesc) {
		this.jobTitleDesc = jobTitleDesc;
	}
	public String getDivisonName() {
		return divisonName;
	}
	public void setDivisonName(String divisonName) {
		this.divisonName = divisonName;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getDateHired() {
		return dateHired;
	}
	public void setDateHired(String dateHired) {
		this.dateHired = dateHired;
	}
	public String getStrNbr() {
		return strNbr;
	}
	public void setStrNbr(String strNbr) {
		this.strNbr = strNbr;
	}
	public String getCtznCntryCode() {
		return ctznCntryCode;
	}
	public void setCtznCntryCode(String ctznCntryCode) {
		this.ctznCntryCode = ctznCntryCode;
	}
	public String getSchoolGradYr() {
		return schoolGradYr;
	}
	public void setSchoolGradYr(String schoolGradYr) {
		this.schoolGradYr = schoolGradYr;
	}
	public String getFacName() {
		return facName;
	}
	public void setFacName(String facName) {
		this.facName = facName;
	}
	public String getLocText() {
		return locText;
	}
	public void setLocText(String locText) {
		this.locText = locText;
	}
	public String getDegreeDesc() {
		return degreeDesc;
	}
	public void setDegreeDesc(String degreeDesc) {
		this.degreeDesc = degreeDesc;
	}
	public String getCollegeMjr() {
		return collegeMjr;
	}
	public void setCollegeMjr(String collegeMjr) {
		this.collegeMjr = collegeMjr;
	}
	public String getGradeYesNo() {
		return gradeYesNo;
	}
	public void setGradeYesNo(String gradeYesNo) {
		this.gradeYesNo = gradeYesNo;
	}
	public String getLastUpdTs() {
		return lastUpdTs;
	}
	public void setLastUpdTs(String lastUpdTs) {
		this.lastUpdTs = lastUpdTs;
	}
	public String getSplnCourseDesc() {
		return splnCourseDesc;
	}
	public void setSplnCourseDesc(String splnCourseDesc) {
		this.splnCourseDesc = splnCourseDesc;
	}
	public String getOvrdSchoolName() {
		return ovrdSchoolName;
	}
	public void setOvrdSchoolName(String ovrdSchoolName) {
		this.ovrdSchoolName = ovrdSchoolName;
	}
	public String getCourseEndDate() {
		return courseEndDate;
	}
	public void setCourseEndDate(String courseEndDate) {
		this.courseEndDate = courseEndDate;
	}
	public String getJobTitleCode() {
		return jobTitleCode;
	}
	public void setJobTitleCode(String jobTitleCode) {
		this.jobTitleCode = jobTitleCode;
	}
	public String getDivisionCode() {
		return divisionCode;
	}
	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getOvrdJobTtlDesc() {
		return ovrdJobTtlDesc;
	}
	public void setOvrdJobTtlDesc(String ovrdJobTtlDesc) {
		this.ovrdJobTtlDesc = ovrdJobTtlDesc;
	}
	public String getEmplyrName() {
		return emplyrName;
	}
	public void setEmplyrName(String emplyrName) {
		this.emplyrName = emplyrName;
	}
	public String getWrkBgnDate() {
		return wrkBgnDate;
	}
	public void setWrkBgnDate(String wrkBgnDate) {
		this.wrkBgnDate = wrkBgnDate;
	}
	public String getWrkEndDate() {
		return wrkEndDate;
	}
	public void setWrkEndDate(String wrkEndDate) {
		this.wrkEndDate = wrkEndDate;
	}
	public String getPlnLastUpdTs() {
		return plnLastUpdTs;
	}
	public void setPlnLastUpdTs(String plnLastUpdTs) {
		this.plnLastUpdTs = plnLastUpdTs;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getSplnGstatDesc() {
		return splnGstatDesc;
	}
	public void setSplnGstatDesc(String splnGstatDesc) {
		this.splnGstatDesc = splnGstatDesc;
	}
	public String getTypeCd() {
		return typeCd;
	}
	public void setTypeCd(String typeCd) {
		this.typeCd = typeCd;
	}
	public String getReloLastUpdTs() {
		return reloLastUpdTs;
	}
	public void setReloLastUpdTs(String reloLastUpdTs) {
		this.reloLastUpdTs = reloLastUpdTs;
	}
	public String getReloDesription() {
		return reloDesription;
	}
	public void setReloDesription(String reloDesription) {
		this.reloDesription = reloDesription;
	}
	public String getDependson() {
		return dependson;
	}
	public void setDependson(String dependson) {
		this.dependson = dependson;
	}
	public String getLangUseTypCd() {
		return langUseTypCd;
	}
	public void setLangUseTypCd(String langUseTypCd) {
		this.langUseTypCd = langUseTypCd;
	}
	public String getLangLastUpdTs() {
		return langLastUpdTs;
	}
	public void setLangLastUpdTs(String langLastUpdTs) {
		this.langLastUpdTs = langLastUpdTs;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getPrefLangFlg() {
		return prefLangFlg;
	}
	public void setPrefLangFlg(String prefLangFlg) {
		this.prefLangFlg = prefLangFlg;
	}
	public String getLangUseTypDesc() {
		return langUseTypDesc;
	}
	public void setLangUseTypDesc(String langUseTypDesc) {
		this.langUseTypDesc = langUseTypDesc;
	}
	public String getLangPrfncyDesc() {
		return langPrfncyDesc;
	}
	public void setLangPrfncyDesc(String langPrfncyDesc) {
		this.langPrfncyDesc = langPrfncyDesc;
	}
	public String getEffDate() {
		return effDate;
	}
	public void setEffDate(String effDate) {
		this.effDate = effDate;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
    
	
    	
}

package com.homedepot.hr.gd.hrreview.dto;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class Associate {
	
	@XStreamAsAttribute
	@XStreamAlias("printed-on")
	private String printedDate;
	
	@XStreamAlias("personel")
	private AssocProfilePersonal assocProfilePersonal;
	@XStreamAlias("education")
	private List<EducationList> education;
	@XStreamAlias("key")
	//private KeyCourses keyCourses;
	private List<KeyCoursesList> keyCourses;
	@XStreamAlias("ext-work")
	private List<ExternalWorkHistoryList> externalWorkHistory;
	@XStreamAlias("hd-work")
	private List<THDWorkHistoryList> thdWorkHistory;
	@XStreamAlias("planning")
	private List<PlanningList> planning;
	@XStreamAlias("mobility-lang")
	private MobilityLang mobilityLang ;

	public AssocProfilePersonal getAssocProfilePersonal() {
		return assocProfilePersonal;
	}

	public void setAssocProfilePersonal(AssocProfilePersonal assocProfilePersonal) {
		this.assocProfilePersonal = assocProfilePersonal;
	}
	
	public List<EducationList> getEducation() {
		return education;
	}

	public void setEducation(List<EducationList> education) {
		this.education = education;
	}

//	public Education getEducation() {
//		return education;
//	}
//
//	public void setEducation(Education education) {
//		this.education = education;
//	}

	
public MobilityLang getMobilityLang() {
		return mobilityLang;
	}

	public void setMobilityLang(MobilityLang mobilityLang) {
		this.mobilityLang = mobilityLang;
	}

	public List<KeyCoursesList> getKeyCourses() {
		return keyCourses;
	}

	public void setKeyCourses(List<KeyCoursesList> keyCourses) {
		this.keyCourses = keyCourses;
	}

	public List<ExternalWorkHistoryList> getExternalWorkHistory() {
		return externalWorkHistory;
	}

	public void setExternalWorkHistory(
			List<ExternalWorkHistoryList> externalWorkHistory) {
		this.externalWorkHistory = externalWorkHistory;
	}

	public List<THDWorkHistoryList> getThdWorkHistory() {
		return thdWorkHistory;
	}

	public void setThdWorkHistory(List<THDWorkHistoryList> thdWorkHistory) {
		this.thdWorkHistory = thdWorkHistory;
	}

	public List<PlanningList> getPlanning() {
		return planning;
	}

	public void setPlanning(List<PlanningList> planning) {
		this.planning = planning;
	}

	
public String getPrintedDate() {
		return printedDate;
	}

	public void setPrintedDate(String printedDate) {
		this.printedDate = printedDate;
	}
	

}

package com.homedepot.hr.gd.hrreview.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

	@XStreamAlias("ExcelReportDTO")
	public class ExcelReportDTO {
		private String associateID; 
		private String firstName;
		private String lastName; 
		private String jobTitle; 
		private String divisionName;
		private String departmentName;
		private String location;
		private String effectiveBgnDate;
		private String divisionCode;
        private String storeNumber;
        //workHistory External with Ratings
        private String performanceCode;
        private String leadershipCode;
        private String potentialCode;
        private String company;
        private String overrideJobTitleDesc;
        private String startDate;
        private String endDate;
        //InternaloverrideDivisionName
        private String overrideDivisionName;
        private String overrideDeptName;
        
        //Education
        private String gradeYear;
        private String schoolName;
        private String degree;
        //KeyCourse
        private String course;
        private String organization;
        private String courseCompletionDate;
        //CareerPlanning
        private String jtGrpDesc;
        private String title; //same jobtitle but fetched based on different condition in query.	
        private String potentialTiming;
        //Mobility
        private String mobilityLocation;
        private String relocationTypeDesc;
        //Language
        private String language;
        //Departmental Experience
        private String departmentExperience;
        private String experienceYrs;
        
        
        
        
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
		public String getJobTitle() {
			return jobTitle;
		}
		public void setJobTitle(String jobTitle) {
			this.jobTitle = jobTitle;
		}
		public String getDivisionName() {
			return divisionName;
		}
		public void setDivisionName(String divisionName) {
			this.divisionName = divisionName;
		}
		public String getDepartmentName() {
			return departmentName;
		}
		public void setDepartmentName(String departmentName) {
			this.departmentName = departmentName;
		}
		public String getLocation() {
			return location;
		}
		public void setLocation(String location) {
			this.location = location;
		}
		public String getEffectiveBgnDate() {
			return effectiveBgnDate;
		}
		public void setEffectiveBgnDate(String effectiveBgnDate) {
			this.effectiveBgnDate = effectiveBgnDate;
		}
		public String getDivisionCode() {
			return divisionCode;
		}
		public void setDivisionCode(String divisionCode) {
			this.divisionCode = divisionCode;
		}
		public String getStoreNumber() {
			return storeNumber;
		}
		public void setStoreNumber(String storeNumber) {
			this.storeNumber = storeNumber;
		}
		public String getPerformanceCode() {
			return performanceCode;
		}
		public void setPerformanceCode(String performanceCode) {
			this.performanceCode = performanceCode;
		}
		public String getLeadershipCode() {
			return leadershipCode;
		}
		public void setLeadershipCode(String leadershipCode) {
			this.leadershipCode = leadershipCode;
		}
		public String getPotentialCode() {
			return potentialCode;
		}
		public void setPotentialCode(String potentialCode) {
			this.potentialCode = potentialCode;
		}
		public String getCompany() {
			return company;
		}
		public void setCompany(String company) {
			this.company = company;
		}
		public String getOverrideJobTitleDesc() {
			return overrideJobTitleDesc;
		}
		public void setOverrideJobTitleDesc(String overrideJobTitleDesc) {
			this.overrideJobTitleDesc = overrideJobTitleDesc;
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
		public String getGradeYear() {
			return gradeYear;
		}
		public void setGradeYear(String gradeYear) {
			this.gradeYear = gradeYear;
		}
		public String getSchoolName() {
			return schoolName;
		}
		public void setSchoolName(String schoolName) {
			this.schoolName = schoolName;
		}
		public String getDegree() {
			return degree;
		}
		public void setDegree(String degree) {
			this.degree = degree;
		}
		public String getCourse() {
			return course;
		}
		public void setCourse(String course) {
			this.course = course;
		}
		public String getOrganization() {
			return organization;
		}
		public void setOrganization(String organization) {
			this.organization = organization;
		}
		public String getCourseCompletionDate() {
			return courseCompletionDate;
		}
		public void setCourseCompletionDate(String courseCompletionDate) {
			this.courseCompletionDate = courseCompletionDate;
		}
		public String getJtGrpDesc() {
			return jtGrpDesc;
		}
		public void setJtGrpDesc(String jtGrpDesc) {
			this.jtGrpDesc = jtGrpDesc;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getPotentialTiming() {
			return potentialTiming;
		}
		public void setPotentialTiming(String potentialTiming) {
			this.potentialTiming = potentialTiming;
		}
		public String getMobilityLocation() {
			return mobilityLocation;
		}
		public void setMobilityLocation(String mobilityLocation) {
			this.mobilityLocation = mobilityLocation;
		}
		public String getRelocationTypeDesc() {
			return relocationTypeDesc;
		}
		public void setRelocationTypeDesc(String relocationTypeDesc) {
			this.relocationTypeDesc = relocationTypeDesc;
		}
		public String getLanguage() {
			return language;
		}
		public void setLanguage(String language) {
			this.language = language;
		}
		public String getDepartmentExperience() {
			return departmentExperience;
		}
		public void setDepartmentExperience(String departmentExperience) {
			this.departmentExperience = departmentExperience;
		}
		public String getExperienceYrs() {
			return experienceYrs;
		}
		public void setExperienceYrs(String experienceYrs) {
			this.experienceYrs = experienceYrs;
		}
		public String getOverrideDivisionName() {
			return overrideDivisionName;
		}
		public void setOverrideDivisionName(String overrideDivsionName) {
			this.overrideDivisionName = overrideDivsionName;
		}
		public String getOverrideDeptName() {
			return overrideDeptName;
		}
		public void setOverrideDeptName(String overrideDeptName) {
			this.overrideDeptName = overrideDeptName;
		}
		
		
	        
}




package com.homedepot.hr.gd.hrreview.dto;

public class AdvancedSearchDTO {
	public String zeroEmployeeId;
	public String associates;
	public String firstName;
	public String lastName;
	public String districtCode;
	public String humanResourcesSystemStoreNumber;
	public String humanResourcesSystemDepartmentNumber;
	public String jobTitleCode;
	public String jobTitleDescription;
	public String consentDecreeJobCategoryCode;
	public boolean selected;
	
	//Added for selection/apply logic.  Synonymous with SPLN_USER_EMPL_WRK.WRK_OK_FLG
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public String getZeroEmployeeId() {
		return zeroEmployeeId;
	}
	public void setZeroEmployeeId(String zeroEmployeeId) {
		this.zeroEmployeeId = zeroEmployeeId;
	}
	public String getAssociates() {
		return associates;
	}
	public void setAssociates(String associates) {
		this.associates = associates;
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
	public String getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
	public String getHumanResourcesSystemStoreNumber() {
		return humanResourcesSystemStoreNumber;
	}
	public void setHumanResourcesSystemStoreNumber(
			String humanResourcesSystemStoreNumber) {
		this.humanResourcesSystemStoreNumber = humanResourcesSystemStoreNumber;
	}
	public String getHumanResourcesSystemDepartmentNumber() {
		return humanResourcesSystemDepartmentNumber;
	}
	public void setHumanResourcesSystemDepartmentNumber(
			String humanResourcesSystemDepartmentNumber) {
		this.humanResourcesSystemDepartmentNumber = humanResourcesSystemDepartmentNumber;
	}
	public String getJobTitleCode() {
		return jobTitleCode;
	}
	public void setJobTitleCode(String jobTitleCode) {
		this.jobTitleCode = jobTitleCode;
	}
	public String getJobTitleDescription() {
		return jobTitleDescription;
	}
	public void setJobTitleDescription(String jobTitleDescription) {
		this.jobTitleDescription = jobTitleDescription;
	}
	public String getConsentDecreeJobCategoryCode() {
		return consentDecreeJobCategoryCode;
	}
	public void setConsentDecreeJobCategoryCode(String consentDecreeJobCategoryCode) {
		this.consentDecreeJobCategoryCode = consentDecreeJobCategoryCode;
	}
}

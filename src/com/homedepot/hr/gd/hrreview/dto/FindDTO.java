package com.homedepot.hr.gd.hrreview.dto;

public class FindDTO {

	private String zeroEmployeeId;
	private String firstName;
	private String lastName;
	private String middleName;
	private String humanResourcesSystemStoreNumber;
	private String jobTitleCode;
	private String preferenceName;
	private String userId;
	private String jobTitleDescription;

	public String getZeroEmployeeId() {
		return zeroEmployeeId;
	}

	public void setZeroEmployeeId(String aValue) {
		if (aValue != null)
			zeroEmployeeId = aValue.trim();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String aValue) {
		if (aValue != null)
			firstName = aValue.trim();
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String aValue) {
		if (aValue != null)
			lastName = aValue.trim();
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String aValue) {
		if (aValue != null)
			middleName = aValue.trim();
	}

	public String getHumanResourcesSystemStoreNumber() {
		return humanResourcesSystemStoreNumber;
	}

	public void setHumanResourcesSystemStoreNumber(String aValue) {
		if (aValue != null)
			humanResourcesSystemStoreNumber = aValue.trim();
	}

	public String getJobTitleCode() {
		return jobTitleCode;
	}

	public void setJobTitleCode(String aValue) {
		if (aValue != null)
			jobTitleCode = aValue.trim();
	}

	public String getPreferenceName() {
		return preferenceName;
	}

	public void setPreferenceName(String aValue) {
		if (aValue != null)
			preferenceName = aValue.trim();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String aValue) {
		if (aValue != null)
			userId = aValue.trim();
	}

	public String getJobTitleDescription() {
		return jobTitleDescription;
	}

	public void setJobTitleDescription(String aValue) {
		if (aValue != null)
			jobTitleDescription = aValue.trim();
	}
}

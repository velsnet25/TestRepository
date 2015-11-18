package com.homedepot.hr.gd.hrreview.dto;

public class CityInfoDTO {

	private String phoneAreaCityCode;
	private String phoneLocalNumber;
	private String phoneExtensionNumber;
	private String emailAddressText;
	private short contactMechanismRoleCode;
	private short contactMethodCode; //5 email 4 fax 2 phone
	private short contactMechanismPreferenceNumber;
	private String zeroEmplid;

	public String getPhoneAreaCityCode() {
		return phoneAreaCityCode;
	}

	public void setPhoneAreaCityCode(String aValue) {
		phoneAreaCityCode = aValue;
	}

	public String getPhoneLocalNumber() {
		return phoneLocalNumber;
	}

	public void setPhoneLocalNumber(String aValue) {
		phoneLocalNumber = aValue;
	}

	public String getPhoneExtensionNumber() {
		return phoneExtensionNumber;
	}

	public void setPhoneExtensionNumber(String aValue) {
		phoneExtensionNumber = aValue;
	}

	public String getEmailAddressText() {
		return emailAddressText;
	}

	public void setEmailAddressText(String aValue) {
		emailAddressText = aValue;
	}

	public short getContactMechanismRoleCode() {
		return contactMechanismRoleCode;
	}

	public void setContactMechanismRoleCode(short aValue) {
		contactMechanismRoleCode = aValue;
	}

	public short getContactMethodCode() {
		return contactMethodCode;
	}

	public void setContactMethodCode(short aValue) {
		contactMethodCode = aValue;
	}

	public short getContactMechanismPreferenceNumber() {
		return contactMechanismPreferenceNumber;
	}

	public void setContactMechanismPreferenceNumber(short aValue) {
		contactMechanismPreferenceNumber = aValue;
	}

	public String getZeroEmplid() {
		return zeroEmplid;
	}

	public void setZeroEmplid(String aValue) {
		zeroEmplid = aValue;
	}

	
}

package com.homedepot.hr.gd.hrreview.dto;

import java.sql.Date;

public class EnterpriseWorkAddressDTO {

	private String addressLineOneText;
	private String addressLineTwoText;
	private String addressLineThreeText;
	private String addressLineFourText;
	private String cityName;
	private String countryName;
	private String stateCode;
	private String postalCode;
	private String countryCode;
	private String humanResourcesSystemStoreNumber;
	private short contactMechanismRoleCode;
	private short contactMechanismPreferenceNumber;
	private short contactMethodCode;
	private short businessUnitTypeCode;
	private Date effectiveEndDate;

	public String getAddressLineOneText() {
		return addressLineOneText;
	}

	public void setAddressLineOneText(String aValue) {
		addressLineOneText = aValue;
	}

	public String getAddressLineTwoText() {
		return addressLineTwoText;
	}

	public void setAddressLineTwoText(String aValue) {
		addressLineTwoText = aValue;
	}

	public String getAddressLineThreeText() {
		return addressLineThreeText;
	}

	public void setAddressLineThreeText(String aValue) {
		addressLineThreeText = aValue;
	}

	public String getAddressLineFourText() {
		return addressLineFourText;
	}

	public void setAddressLineFourText(String aValue) {
		addressLineFourText = aValue;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String aValue) {
		cityName = aValue;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String aValue) {
		countryName = aValue;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String aValue) {
		stateCode = aValue;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String aValue) {
		postalCode = aValue;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String aValue) {
		countryCode = aValue;
	}

	public String getHumanResourcesSystemStoreNumber() {
		return humanResourcesSystemStoreNumber;
	}

	public void setHumanResourcesSystemStoreNumber(String aValue) {
		humanResourcesSystemStoreNumber = aValue;
	}

	public short getContactMechanismRoleCode() {
		return contactMechanismRoleCode;
	}

	public void setContactMechanismRoleCode(short aValue) {
		contactMechanismRoleCode = aValue;
	}

	public short getContactMechanismPreferenceNumber() {
		return contactMechanismPreferenceNumber;
	}

	public void setContactMechanismPreferenceNumber(short aValue) {
		contactMechanismPreferenceNumber = aValue;
	}

	public short getContactMethodCode() {
		return contactMethodCode;
	}

	public void setContactMethodCode(short aValue) {
		contactMethodCode = aValue;
	}

	public short getBusinessUnitTypeCode() {
		return businessUnitTypeCode;
	}

	public void setBusinessUnitTypeCode(short aValue) {
		businessUnitTypeCode = aValue;
	}

	public Date getEffectiveEndDate() {
		return effectiveEndDate;
	}

	public void setEffectiveEndDate(Date aValue) {
		effectiveEndDate = aValue;
	}
}

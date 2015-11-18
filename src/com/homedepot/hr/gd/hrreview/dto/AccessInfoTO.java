package com.homedepot.hr.gd.hrreview.dto;

import java.util.Map;

import com.google.gson.annotations.SerializedName;

/*
 * TO for group access information
 */
public class AccessInfoTO {
	
	private String lastName;
	private String commonName;
	
	private String firstName;
	private String location;
	
	
	@SerializedName("access")
	private Map<String, Boolean> accessMap ;
	
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @return the accessMap
	 */
	public Map<String, Boolean> getAccessMap() {
		return accessMap;
	}
	/**
	 * @param accessMap the accessMap to set
	 */
	public void setAccessMap(Map<String, Boolean> accessMap) {
		this.accessMap = accessMap;
	}
	
	/**
	 * @return the commonName
	 */
	public String getCommonName() {
		return commonName;
	}
	/**
	 * @param commonName the commonName to set
	 */
	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	
}

package com.homedepot.hr.gd.hrreview.dto;

import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;


public class AssocProfilePersonal {

private String associateID; 
private String name;
private String title; 
private String division;
private String region;
private String location;
private String department;
@XStreamAlias("original-hire-dt")
private String original_hire_dt;
private String time_w_co;
@XStreamAlias("store-num")
private String store_num;
private String citizenship;
private String time_in_pos;

public String getAssociateID() {
	return associateID;
}
public void setAssociateID(String associateID) {
	this.associateID = associateID;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getDivision() {
	return division;
}
public void setDivision(String division) {
	this.division = division;
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
public String getDepartment() {
	return department;
}
public void setDepartment(String department) {
	this.department = department;
}
public String getOriginal_hire_dt() {
	return original_hire_dt;
}
public void setOriginal_hire_dt(String original_hire_dt) {
	this.original_hire_dt = original_hire_dt;
}
public String getTime_w_co() {
	return time_w_co;
}
public void setTime_w_co(String time_w_co) {
	this.time_w_co = time_w_co;
}
public String getStore_num() {
	return store_num;
}
public void setStore_num(String store_num) {
	this.store_num = store_num;
}
public String getCitizenship() {
	return citizenship;
}
public void setCitizenship(String citizenship) {
	this.citizenship = citizenship;
}
public String getTime_in_pos() {
	return time_in_pos;
}
public void setTime_in_pos(String time_in_pos) {
	this.time_in_pos = time_in_pos;
}



}
package com.homedepot.hr.gd.hrreview.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias("ed-line")
public class EducationList {
	
	private String school;
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getGraduated() {
		return graduated;
	}
	public void setGraduated(String graduated) {
		this.graduated = graduated;
	}
	private String location;
	private String degree;
	private String major;
	private String graduated;
	

}

package com.homedepot.hr.gd.hrreview.dto;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class KeyCourses {
	
	@XStreamAlias("key-line")
	private List<KeyCoursesList> keyCoursesList;

	public List<KeyCoursesList> getKeyCoursesList() {
		return keyCoursesList;
	}

	public void setKeyCoursesList(List<KeyCoursesList> keyCoursesList) {
		this.keyCoursesList = keyCoursesList;
	}

}

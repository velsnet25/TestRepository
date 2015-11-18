package com.homedepot.hr.gd.hrreview.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class THDWorkHistory {

	@XStreamAlias("work-line")
	private List<THDWorkHistoryList> thdWorkHistoryList;

	public List<THDWorkHistoryList> getThdWorkHistoryList() {
		return thdWorkHistoryList;
	}

	public void setThdWorkHistoryList(List<THDWorkHistoryList> thdWorkHistoryList) {
		this.thdWorkHistoryList = thdWorkHistoryList;
	}

	
	
	
}

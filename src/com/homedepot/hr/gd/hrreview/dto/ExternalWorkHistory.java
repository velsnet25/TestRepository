package com.homedepot.hr.gd.hrreview.dto;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class ExternalWorkHistory {

	@XStreamAlias("work-line")
	private List<ExternalWorkHistoryList> externalWorkHistoryList;

	public List<ExternalWorkHistoryList> getExternalWorkHistoryList() {
		return externalWorkHistoryList;
	}

	public void setExternalWorkHistoryList(
			List<ExternalWorkHistoryList> externalWorkHistoryList) {
		this.externalWorkHistoryList = externalWorkHistoryList;
	}
	
	
}

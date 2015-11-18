package com.homedepot.hr.gd.hrreview.dto;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;


public class Planning {

	@XStreamAlias("plan-line")
	private List<PlanningList> planningList;

	public List<PlanningList> getPlanningList() {
		return planningList;
	}

	public void setPlanningList(List<PlanningList> planningList) {
		this.planningList = planningList;
	}

	
	
	
}

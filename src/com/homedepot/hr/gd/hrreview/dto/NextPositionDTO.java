package com.homedepot.hr.gd.hrreview.dto;

import java.sql.Timestamp;

public class NextPositionDTO {

	private short sequenceNumber;
	private String jobTitleCode;
	private String overridePositionDescription;
	private short successionPlanGoalStatusCode;
	private short successionPlanGoalSourceCode;
	private Short successionPlanGoalTypeCode;
	private Timestamp lastUpdateTimestamp;
	private String zeroEmplid;
	private String successionPlanGoalStatusDescription;

	public String getSuccessionPlanGoalStatusDescription() {
		return successionPlanGoalStatusDescription;
	}

	public void setSuccessionPlanGoalStatusDescription(
			String successionPlanGoalStatusDescription) {
		this.successionPlanGoalStatusDescription = successionPlanGoalStatusDescription;
	}

	public short getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(short aValue) {
		sequenceNumber = aValue;
	}

	public String getJobTitleCode() {
		return jobTitleCode;
	}

	public void setJobTitleCode(String aValue) {
		jobTitleCode = aValue;
	}

	public String getOverridePositionDescription() {
		return overridePositionDescription;
	}

	public void setOverridePositionDescription(String aValue) {
		overridePositionDescription = aValue;
	}

	public short getSuccessionPlanGoalStatusCode() {
		return successionPlanGoalStatusCode;
	}

	public void setSuccessionPlanGoalStatusCode(short aValue) {
		successionPlanGoalStatusCode = aValue;
	}

	public short getSuccessionPlanGoalSourceCode() {
		return successionPlanGoalSourceCode;
	}

	public void setSuccessionPlanGoalSourceCode(short aValue) {
		successionPlanGoalSourceCode = aValue;
	}

	public Short getSuccessionPlanGoalTypeCode() {
		return successionPlanGoalTypeCode;
	}

	public void setSuccessionPlanGoalTypeCode(Short aValue) {
		successionPlanGoalTypeCode = aValue;
	}

	public Timestamp getLastUpdateTimestamp() {
		return lastUpdateTimestamp;
	}

	public void setLastUpdateTimestamp(Timestamp aValue) {
		lastUpdateTimestamp = aValue;
	}

	public String getZeroEmplid() {
		return zeroEmplid;
	}

	public void setZeroEmplid(String aValue) {
		zeroEmplid = aValue;
	}
}

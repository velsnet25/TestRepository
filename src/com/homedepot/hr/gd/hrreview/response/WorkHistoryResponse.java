/* 
 * This program is proprietary to The Home Depot and is not to be
 * reproduced, used, or disclosed without permission of:
 *    
 *  The Home Depot
 *  2455 Paces Ferry Road, N.W.
 *  Atlanta, GA 30339-4053
 *
 * File Name: CandidateSearchResponse.java
 * Application: AIMs
 *
 */
package com.homedepot.hr.gd.hrreview.response;

import java.util.List;

import com.homedepot.hr.gd.hrreview.dto.WorkHistoryExternalDTO;
import com.homedepot.hr.gd.hrreview.dto.WorkHistoryHomeDepotDTO;
import com.homedepot.hr.gd.hrreview.dto.WorkHistoryHomeDepotPre95DTO;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("WorkHistoryList")
public class WorkHistoryResponse
{
	private boolean success;	
	private String message;
	private List<WorkHistoryHomeDepotDTO>  workHistoryHomeDepotDTO;
	private List<WorkHistoryHomeDepotPre95DTO>  workHistoryHomeDepotPre95DTO;
	private List<WorkHistoryExternalDTO>  workHistoryExternalDTO;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<WorkHistoryHomeDepotDTO> getWorkHistoryHomeDepotDTO() {
		return workHistoryHomeDepotDTO;
	}
	public void setWorkHistoryHomeDepotDTO(
			List<WorkHistoryHomeDepotDTO> workHistoryHomeDepotDTO) {
		this.workHistoryHomeDepotDTO = workHistoryHomeDepotDTO;
	}
	public List<WorkHistoryHomeDepotPre95DTO> getWorkHistoryHomeDepotPre95DTO() {
		return workHistoryHomeDepotPre95DTO;
	}
	public void setWorkHistoryHomeDepotPre95DTO(
			List<WorkHistoryHomeDepotPre95DTO> workHistoryHomeDepotPre95DTO) {
		this.workHistoryHomeDepotPre95DTO = workHistoryHomeDepotPre95DTO;
	}
	public List<WorkHistoryExternalDTO> getWorkHistoryExternalDTO() {
		return workHistoryExternalDTO;
	}
	public void setWorkHistoryExternalDTO(
			List<WorkHistoryExternalDTO> workHistoryExternalDTO) {
		this.workHistoryExternalDTO = workHistoryExternalDTO;
	}	
}

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

import com.homedepot.hr.gd.hrreview.dto.DistrictSearchDTO;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("DistrictSearch")
public class DistrictSearchResponse
{
	private boolean sucess;
	private String message;
	private List<DistrictSearchDTO> districtSearchDTO;
	
	public boolean isSucess() {
		return sucess;
	}
	public void setSucess(boolean sucess) {
		this.sucess = sucess;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<DistrictSearchDTO> getDistrictSearchDTO() {
		return districtSearchDTO;
	}
	public void setDistrictSearchDTO(List<DistrictSearchDTO> districtSearchDTO) {
		this.districtSearchDTO = districtSearchDTO;
	}	
}

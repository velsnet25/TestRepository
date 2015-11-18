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

import com.homedepot.hr.gd.hrreview.dto.HomeAddressDTO;
import com.homedepot.hr.gd.hrreview.dto.WorkAddressDTO;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("AddressList")
public class AddressResponse
{
	private boolean sucess;
	private String message;
	private HomeAddressDTO homeAddressDTO;
	private WorkAddressDTO workAddressDTO;
	//private CityInfoDTO cityInfoDTO;
	
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
	public HomeAddressDTO getHomeAddressDTO() {
		return homeAddressDTO;
	}
	public void setHomeAddressDTO(HomeAddressDTO homeAddressDTO) {
		this.homeAddressDTO = homeAddressDTO;
	}
	public WorkAddressDTO getWorkAddressDTO() {
		return workAddressDTO;
	}
	public void setWorkAddressDTO(WorkAddressDTO workAddressDTO) {
		this.workAddressDTO = workAddressDTO;
	}
//	public CityInfoDTO getCityInfoDTO() {
//		return cityInfoDTO;
//	}
//	public void setCityInfoDTO(CityInfoDTO cityInfoDTO) {
//		this.cityInfoDTO = cityInfoDTO;
//	}
//	
}

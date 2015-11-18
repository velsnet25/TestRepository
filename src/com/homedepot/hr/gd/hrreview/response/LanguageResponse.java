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

import com.homedepot.hr.gd.hrreview.dto.LanguageDetailsDTO;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("LanguageList")
public class LanguageResponse
{
	private boolean success;	
	private String message;
	private List<LanguageDetailsDTO> languageDetailsDTO;
	
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
	public List<LanguageDetailsDTO> getLanguageDetailsDTO() {
		return languageDetailsDTO;
	}
	public void setLanguageDetailsDTO(List<LanguageDetailsDTO> languageDetailsDTO) {
		this.languageDetailsDTO = languageDetailsDTO;
	}
	
	
}

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

import com.homedepot.hr.gd.hrreview.dto.ApplyAssociateDTO;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ApplyAssociateList")
public class ApplyAssociateResponse
{
	private boolean success;	
	private String message;
	private ApplyAssociateDTO applyAssociateDTO;
	
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
	public ApplyAssociateDTO getApplyAssociateDTO() {
		return applyAssociateDTO;
	}
	public void setApplyAssociateDTO(ApplyAssociateDTO applyAssociateDTO) {
		this.applyAssociateDTO = applyAssociateDTO;
	}
	
}

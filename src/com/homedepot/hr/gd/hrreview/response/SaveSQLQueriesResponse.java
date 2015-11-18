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

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("SaveSQLQueriesList")
public class SaveSQLQueriesResponse
{
	private boolean success;	
	private String message;
	private boolean successFromService;	
	private boolean successFromParameterService;	
	
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
	public boolean isSuccessFromService() {
		return successFromService;
	}
	public void setSuccessFromService(boolean successFromService) {
		this.successFromService = successFromService;
	}
	public boolean isSuccessFromParameterService() {
		return successFromParameterService;
	}
	public void setSuccessFromParameterService(boolean successFromParameterService) {
		this.successFromParameterService = successFromParameterService;
	}	
	
}

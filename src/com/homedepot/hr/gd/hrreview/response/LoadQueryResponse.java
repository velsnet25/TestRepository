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

import com.homedepot.hr.gd.hrreview.dto.LoadQueryDTO;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("LoadSQLQueriesList")
public class LoadQueryResponse
{
	private boolean success;	
	private String message;
	private List<LoadQueryDTO> loadQuery;
	
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
	public List<LoadQueryDTO> getLoadQuery() {
		return loadQuery;
	}
	public void setLoadQuery(List<LoadQueryDTO> loadQuery) {
		this.loadQuery = loadQuery;
	}
	
}
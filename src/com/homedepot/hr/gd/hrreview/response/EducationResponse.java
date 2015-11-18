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

import com.homedepot.hr.gd.hrreview.dto.CourseDTO;
import com.homedepot.hr.gd.hrreview.dto.SchoolDTO;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("EducationList")
public class EducationResponse
{
	private boolean success;	
	private String message;
	private List<SchoolDTO> educationDTO;
	private List<CourseDTO> courseDTO;
	
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
	public List<SchoolDTO> getEducationDTO() {
		return educationDTO;
	}
	public void setEducationDTO(List<SchoolDTO> educationDTO) {
		this.educationDTO = educationDTO;
	}
	public List<CourseDTO> getCourseDTO() {
		return courseDTO;
	}
	public void setCourseDTO(List<CourseDTO> courseDTO) {
		this.courseDTO = courseDTO;
	}
	
	
}

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

import com.homedepot.hr.gd.hrreview.dto.RatingDTO;
import com.homedepot.hr.gd.hrreview.dto.TesseractRatingSearchDTO;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("RatingList")
public class RatingResponse
{
	private boolean success;	
	private String message;
	private List<TesseractRatingSearchDTO> currentRatingDTO;
	private List<TesseractRatingSearchDTO> tesseractRatingDTO;
	private List<TesseractRatingSearchDTO> tesseractRatingDTOBefore2002;
	
	
	public List<TesseractRatingSearchDTO> getTesseractRatingDTOBefore2002() {
		return tesseractRatingDTOBefore2002;
	}
	public void setTesseractRatingDTOBefore2002(
			List<TesseractRatingSearchDTO> tesseractRatingDTOBefore2002) {
		this.tesseractRatingDTOBefore2002 = tesseractRatingDTOBefore2002;
	}
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
	public List<TesseractRatingSearchDTO> getCurrentRatingDTO() {
		return currentRatingDTO;
	}
	public void setCurrentRatingDTO(List<TesseractRatingSearchDTO> ratingDTO) {
		this.currentRatingDTO = ratingDTO;
	}
	public List<TesseractRatingSearchDTO> getTesseractRatingDTO() {
		return tesseractRatingDTO;
	}
	public void setTesseractRatingDTO(
			List<TesseractRatingSearchDTO> tesseractRatingDTO) {
		this.tesseractRatingDTO = tesseractRatingDTO;
	}	
	
}

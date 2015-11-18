package com.homedepot.hr.gd.hrreview.bl;


import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.TreeSet;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.homedepot.hr.gd.hrreview.dao.DataDAO;
import com.homedepot.hr.gd.hrreview.dao.PerformanceDevelopmentDAO;
import com.homedepot.hr.gd.hrreview.dto.JobTitleSearchDTO;
import com.homedepot.hr.gd.hrreview.dto.NextPositionDTO;
import com.homedepot.hr.gd.hrreview.dto.PerformanceRating;
import com.homedepot.hr.gd.hrreview.dto.RatingDTO;
import com.homedepot.hr.gd.hrreview.dto.ReadinessSearchDTO;
import com.homedepot.hr.gd.hrreview.dto.TesseractRatingSearchDTO;
import com.homedepot.hr.gd.hrreview.exceptions.HrReviewApplLogMessage;
import com.homedepot.hr.gd.hrreview.exceptions.HrReviewException;
import com.homedepot.hr.gd.hrreview.interfaces.Constants;
import com.homedepot.hr.gd.hrreview.response.NextPositionResponse;
import com.homedepot.hr.gd.hrreview.response.RatingResponse;
import com.homedepot.hr.gd.hrreview.util.AppUtil;
import com.homedepot.ta.aa.dao.exceptions.QueryException;

public class PerformanceDevelopmentManager implements Constants {

	private static final Logger mLogger = Logger.getLogger(PerformanceDevelopmentManager.class);
	private static final int NEW_RATINGS_YEAR = 102;
	
	public static RatingResponse getRatingDetails(String associateId) throws HrReviewException 
	{
		
		long startTime = 0;
		RatingResponse ratingRes = null;
		List<RatingDTO> ratingCurrentDTO = null;
		List<TesseractRatingSearchDTO> ratingTesseractDTO = null;
		List<TesseractRatingSearchDTO> ratingTesseractDTOPre2002 = null;
		List<TesseractRatingSearchDTO> ratingTesseractDTOFrom2002 = null;
		List<TesseractRatingSearchDTO> currentRatingDTO = null;
		
		String strEvalCtgry = null;
		String strEvalRating = null;
		int evalCtgryCode = 0;
		
		String strPerfCode = null;
		String strPotCode = null;
		String strLeadCode = null;
		String strEvalDate = null;
		String strPerfDesc = null;
		String strPotDesc = null;
		String strLeadDesc = null;
		String strNineBoxCode = null; 
		
		if (mLogger.isDebugEnabled()) {
			startTime = System.nanoTime();
		}

		try {
						
			ratingCurrentDTO = (List<RatingDTO>) PerformanceDevelopmentDAO.getRatingDetails(associateId);		
			ratingTesseractDTO = (List<TesseractRatingSearchDTO>) PerformanceDevelopmentDAO.getTesseractRatingDetails(associateId);
			if (ratingTesseractDTO != null && ratingTesseractDTO.size() > 0) {
				
				 ratingTesseractDTOPre2002 = new 	ArrayList<TesseractRatingSearchDTO>();
				 ratingTesseractDTOFrom2002 = new 	ArrayList<TesseractRatingSearchDTO> ();
				 currentRatingDTO= new ArrayList<TesseractRatingSearchDTO>();
				 TesseractRatingSearchDTO ratingHistoryDTO=null;
				 TesseractRatingSearchDTO rating=null;
//				for (int i=0;i<ratingTesseractDTO.size();i++) {
//					
//					rating=ratingTesseractDTO.get(i);
//					ratingHistoryDTO=new TesseractRatingSearchDTO();
//					ratingHistoryDTO.setEffectiveDate(rating.getEffectiveDate());
//					ratingHistoryDTO.setPerformance(rating.getPerformance());
//					if(!(AppUtil.isEmptyString(rating.getPerformanceDescription())))
//					{
//						ratingHistoryDTO.setPerformanceDescription(rating.getPerformanceDescription().trim());	
//					}
//					
//					ratingHistoryDTO.setPotential(rating.getPotentialDescription());
//					if(!(AppUtil.isEmptyString(rating.getPotentialDescription())))
//					{
//						ratingHistoryDTO.setPotentialDescription(rating.getPotentialDescription().trim());	
//					}
//					
//					if(!(AppUtil.isEmptyString(rating.getLeadershipDescription())))
//					{
//						ratingHistoryDTO.setLeadershipDescription(rating.getLeadershipDescription().trim());	
//					}
//					
//					ratingHistoryDTO.setLeadership(rating.getLeadership());
//					
//					ratingHistoryDTO.setSequence(rating.getSequence());
//					
//					if(i==0)
//					{
//						currentRatingDTO.add(ratingHistoryDTO);
//					}
//					else
//					{
//						ratingTesseractDTOFrom2002.add(ratingHistoryDTO);
//					}
//					
//				}
					
					// To get the records for pre 2002
					
					Vector hrzRatingsVect = new Vector(); //holds all reviews for pre-2002 																			
					
					HashMap oldTessMap = new HashMap();//holds year/review rating list from pre-2002 
					//performance reviews
					
					HashMap oldHrzMap = new HashMap(); //holds list of all years in which associates have 
					//reviews from the horizons system.  Used in building
					
					Date tempDate = null;	
					int tempYear = 0;
					PerformanceRating perfRating = null;
					Date evalDate = null;
					perfRating = new PerformanceRating ();
					Vector oldRatingsVect = new Vector();
					Vector vectRatings = new Vector();
					
					for(RatingDTO ratingDTO:ratingCurrentDTO)
					{
						strEvalCtgry = ratingDTO.getEvaluateCategoryDescription();
						strEvalRating = ratingDTO.getEvaluateRatingDescription();
						evalCtgryCode = ratingDTO.getEvaluateCategoryCode();
						strNineBoxCode =ratingDTO.getSuccessionPlanNineBoxGridDescription();
						tempDate = evalDate;
						evalDate = ((java.util.Date) ratingDTO.getEvaluateDate());
						if(evalDate.equals(tempDate)){ //add multiple Ctgry's for same review
							
							perfRating.addOldRatingsCodes(evalDate,strEvalRating,strEvalCtgry,evalCtgryCode,strNineBoxCode);
						}
						else{
							
							if(tempDate != null){ 
								
								hrzRatingsVect.addElement(perfRating); 
								
								oldHrzMap.put(perfRating.getEvalDate(),"OK");
								perfRating = new PerformanceRating();
								perfRating.addOldRatingsCodes(evalDate,strEvalRating,strEvalCtgry,evalCtgryCode,strNineBoxCode);
							}//if
							else{ //tempDate will be null on the first time thru loop
								perfRating.addOldRatingsCodes(evalDate,strEvalRating,strEvalCtgry,evalCtgryCode,strNineBoxCode);
							}									
						}//else
					}//for 
					
					if(perfRating.getEvalDate() != null){
						hrzRatingsVect.addElement(perfRating); //add final performance rating
						
						oldHrzMap.put(perfRating.getEvalDate(),"OK");	
					}
					SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd");
					HashMap checkDupReviewMap = new HashMap();
					
					//Iterating the second query results
					for (TesseractRatingSearchDTO tesseractDTO : ratingTesseractDTO) {
						strPerfCode = tesseractDTO.getPerformance();
						strPotCode =  tesseractDTO.getPotential();
						strLeadCode = tesseractDTO.getLeadership();
						strPerfDesc = tesseractDTO.getPerformanceDescription();
						strPotDesc =  tesseractDTO.getPotentialDescription();
						strLeadDesc = tesseractDTO.getLeadershipDescription();
						strEvalDate = tesseractDTO.getEffectiveDate();
						
						if(strPerfCode != null){
							strPerfCode = strPerfCode.trim();
						}
						if(strPotCode != null){
							strPotCode = strPotCode.trim();
						}
						if(strLeadCode != null){
							strLeadCode = strLeadCode.trim();
						}
						if(strPerfDesc != null){
							strPerfDesc = strPerfDesc.trim();
						}
						if(strPotDesc != null){
							strPotDesc = strPotDesc.trim();
						}
						if(strLeadDesc != null){
							strLeadDesc = strLeadDesc.trim();
						}
						
						if(!AppUtil.isEmptyString(strEvalDate))
						evalDate = dateFmt.parse(strEvalDate, new ParsePosition(0));
						
						if(checkDupReviewMap!=null && evalDate!=null)
						if(checkDupReviewMap.get(strPerfCode + strPotCode + strLeadCode + evalDate.getYear())  == null){
							
							//no previous PerformanceRating object with this information...
							//add it to checkDupReviewMap
							
							checkDupReviewMap.put(strPerfCode + strPotCode + strLeadCode + evalDate.getYear(),"OK");
						}
						else{
							continue;
							//duplicate review information found in checkDupReviewMap (non-null on get())
							//continue to top of loop...adding no review information for this row of the resultset
						}
						Date reviewChangeDate = new Date(NEW_RATINGS_YEAR,0,01); //Jan 1, 2002
						
						if(evalDate!=null)
						{
						if(evalDate.before(reviewChangeDate)){ //if the date is prior to 2002 add to oldVector
							if (strPerfCode == null || "".equals(strPerfCode)){ //no reviewRating information...continue
								continue; 
							}
							
							if(oldHrzMap.get(evalDate) == null){  //null indicates no horizons row for this year
								perfRating = new PerformanceRating();            //add a perfRating object with only reviewRating 
								perfRating.setEvalDate(evalDate);                //and evalDate infor from Teserract
								perfRating.setReviewRating(strPerfCode);
								oldRatingsVect.addElement(perfRating); 
							}
							else{
								
								oldTessMap.put(evalDate,strPerfCode); //add necessary Teserract info 
							}										  //for merg with Hrz pre 2002 data
							//PerformanceRating object for this 
							//review will be created below
						}//before new ratings year (2002)
						
						
						else{ //date is after 2002 add to regular ratings vector
							
							perfRating = new PerformanceRating();
							
							perfRating.setPerformance(strPerfDesc);
							perfRating.setPotential(strPotDesc);
							perfRating.setLeadership(strLeadDesc);
							perfRating.setEvalDate(evalDate);
							vectRatings.addElement(perfRating);					   
							
						}//else after 1/1/2002
					}
					}//for
					
					Comparator c = new Comparator(){
						public int compare(Object one, Object two){
							return ((PerformanceRating)one).compareTo((PerformanceRating)two);
						}
					};
					
					TreeSet ts = new TreeSet(c);
					
					//now move through Horizons review data merging with matching Tess data	
					for(int i=0;i<hrzRatingsVect.size();i++){
						
						perfRating = (PerformanceRating)hrzRatingsVect.elementAt(i);
						
						//get corresponding review rating from teserract for this year
						
						strPerfCode = (String)oldTessMap.get(perfRating.getEvalDate());
						if(strPerfCode != null){
							perfRating.setReviewRating(strPerfCode);
						}
						
						ts.add(perfRating); //add and sort by date
						
					}//for
					
					for(int i=0;i<oldRatingsVect.size();i++){ //get all tesseract entries with only evalDate
						//and review rating
						perfRating = (PerformanceRating)oldRatingsVect.elementAt(i);
						ts.add(perfRating);
					}		
					
					oldRatingsVect = new Vector(ts);
					//use .elementAt(i) to retreive the elements from vector and store it in ur final list
					vectRatings.add(0,oldRatingsVect); //add oldRatingsVect to front of vector
					PerformanceRating tempRating=null;
					for(int j=0;j<oldRatingsVect.size();j++)
					{
						tempRating=(PerformanceRating)oldRatingsVect.elementAt(j);
						ratingHistoryDTO=new TesseractRatingSearchDTO();
						ratingHistoryDTO.setEffectiveDate(AppUtil.convertUtilDateToString(tempRating.getEvalDate()));
						ratingHistoryDTO.setPerformance(tempRating.getPerformance());
						ratingHistoryDTO.setPotential(tempRating.getPotential());
						ratingHistoryDTO.setNineBoxGridDescription(tempRating.getNineBoxCode());
						ratingHistoryDTO.setReviewRate(tempRating.getReviewRating());
						ratingTesseractDTOPre2002.add(ratingHistoryDTO);
					}
					
					for(int j=1;j<vectRatings.size();j++)
					{
						tempRating=(PerformanceRating)vectRatings.elementAt(j);
						ratingHistoryDTO=new TesseractRatingSearchDTO();
						if(tempRating.getEvalDate()!=null)
						ratingHistoryDTO.setEffectiveDate(AppUtil.convertUtilDateToString(tempRating.getEvalDate()));
						ratingHistoryDTO.setPerformance(tempRating.getPerformance());
						ratingHistoryDTO.setPotential(tempRating.getPotential());
						ratingHistoryDTO.setLeadership(tempRating.getLeadership());
						if(j==1)
						{
								currentRatingDTO.add(ratingHistoryDTO);
						}
						else
						{
								ratingTesseractDTOFrom2002.add(ratingHistoryDTO);
						}
					}
					
					
				}
				ratingRes = new RatingResponse();
				ratingRes.setSuccess(true);
				ratingRes.setTesseractRatingDTO(ratingTesseractDTOFrom2002);
				ratingRes.setTesseractRatingDTOBefore2002(ratingTesseractDTOPre2002);
			    ratingRes.setCurrentRatingDTO(currentRatingDTO);
			

		} catch (QueryException e) {
			mLogger.error(HrReviewApplLogMessage.create(
					HrReviewApplLogMessage.FATAL_ERROR,
					ERROR_SEARCH_CANDIDATE_LIST_QUERY, e));
			throw new HrReviewException(HrReviewApplLogMessage.FATAL_ERROR,
					ERROR_SEARCH_CANDIDATE_LIST_QUERY, e);

		}

		if (mLogger.isDebugEnabled()) {
			long endTime = System.nanoTime();
			if (startTime == 0) {
				startTime = endTime;
			}
			mLogger.debug(String
					.format("Exiting geCandidateSearchList(). Total time to process request: %.4f seconds",
							(((float) endTime - startTime) / NANOS_IN_SECOND)));
		}
		return ratingRes;
	}

	public static NextPositionResponse getNextPositionDetails(String associateId) throws HrReviewException 
	{	
		long startTime = 0;
		NextPositionResponse nextPositionRes = null;
		List<NextPositionDTO> nextPositionCurrentDTO = null;

		if (mLogger.isDebugEnabled()) {
			startTime = System.nanoTime();
		}

		try {
						
			nextPositionCurrentDTO = (List<NextPositionDTO>) PerformanceDevelopmentDAO.getNextPositionDetails(associateId);		
			List<ReadinessSearchDTO> readinessDTO = (List<ReadinessSearchDTO>) DataDAO.getReadinessDetails();
			
			//Readiness
			Hashtable<Short,String> readinessDTOList = new Hashtable<Short,String>();
			for (ReadinessSearchDTO readiness :  readinessDTO) {
				if (!readinessDTOList.containsKey(readiness.getSuccessionPlanGoalStatusCode()))
					readinessDTOList.put(readiness.getSuccessionPlanGoalStatusCode(), readiness.getSuccessionPlanGoalStatusDescription());
			}
			
			//next position
			List<JobTitleSearchDTO> jobTitleDTO = (List<JobTitleSearchDTO>) DataDAO.getJobTitleDetails(false);
			Hashtable<String,String> jobTitleDTOList = new Hashtable<String,String>();
			for (JobTitleSearchDTO jobTitle : jobTitleDTO) {
				if (jobTitle.getJobTitleCode() != null && !jobTitleDTOList.containsKey(jobTitle.getJobTitleCode().trim()))
					jobTitleDTOList.put(jobTitle.getJobTitleCode().trim(), jobTitle.getJobTitleDescription());
			}
			
			for (NextPositionDTO nextPosition : nextPositionCurrentDTO) {
				
				if (readinessDTOList.containsKey(nextPosition.getSuccessionPlanGoalStatusCode())) {
					nextPosition.setSuccessionPlanGoalStatusDescription(readinessDTOList.get(nextPosition.getSuccessionPlanGoalStatusCode()));;
				}
				
				if (nextPosition.getJobTitleCode() != null && jobTitleDTOList.containsKey(nextPosition.getJobTitleCode().trim())) {
					nextPosition.setOverridePositionDescription(jobTitleDTOList.get(nextPosition.getJobTitleCode().trim()));
				}
			}
			
			nextPositionRes = new NextPositionResponse();
			nextPositionRes.setSuccess(true);
			nextPositionRes.setNextPositionDTO(nextPositionCurrentDTO);

		} catch (QueryException e) {
			mLogger.error(HrReviewApplLogMessage.create(
					HrReviewApplLogMessage.FATAL_ERROR,
					ERROR_SEARCH_CANDIDATE_LIST_QUERY, e));
			throw new HrReviewException(HrReviewApplLogMessage.FATAL_ERROR,
					ERROR_SEARCH_CANDIDATE_LIST_QUERY, e);

		}

		if (mLogger.isDebugEnabled()) {
			long endTime = System.nanoTime();
			if (startTime == 0) {
				startTime = endTime;
			}
			mLogger.debug(String
					.format("Exiting geCandidateSearchList(). Total time to process request: %.4f seconds",
							(((float) endTime - startTime) / NANOS_IN_SECOND)));
		}
		return nextPositionRes;
	}
	
}

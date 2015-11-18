package com.homedepot.hr.gd.hrreview.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.homedepot.hr.gd.hrreview.dto.PerformanceReportDTO;
import com.homedepot.hr.gd.hrreview.exceptions.HrReviewApplLogMessage;
import com.homedepot.hr.gd.hrreview.exceptions.HrReviewException;
import com.homedepot.hr.gd.hrreview.interfaces.Constants;
import com.homedepot.hr.gd.hrreview.interfaces.DAOConstants;
import com.homedepot.ta.aa.dao.Inputs;
import com.homedepot.ta.aa.dao.Query;
import com.homedepot.ta.aa.dao.Results;
import com.homedepot.ta.aa.dao.ResultsReader;
import com.homedepot.ta.aa.dao.basic.BasicDAO;
import com.homedepot.ta.aa.dao.exceptions.QueryException;
import com.homedepot.ta.aa.dao.stream.MapStream;

public class PerformanceReportDAO implements DAOConstants, Constants {

	private static final Logger logger = Logger.getLogger(PerformanceReportDAO.class);	

	public static List<PerformanceReportDTO> generateCurrentIndvdlPersonalPerformanceReport(String userID,String associateId) throws QueryException, HrReviewException 
	{
		final List<PerformanceReportDTO> performanceReportListDTO=new ArrayList<PerformanceReportDTO>();
		if (logger.isDebugEnabled()) {
			logger.debug("Start generateCurrentIndvdlPersonalPerformanceReport in Reporting DAO");
		}
		MapStream inputList= new MapStream("readSuccessionPlanUserEmployeeWorkAndEnterpriseBusinessUnitDetails");
		inputList.put("userId",userID);
		inputList.put("zeroEmployeeId",associateId);
		inputList.put("languageCode", LANGUAGE_CD);
		inputList.put("successionPlanSecurityGroupId",300);
		
		try
	      {
			BasicDAO.getResult(BUSINESSORGANIZATIONTHDORGANIZATIONSTRUCTURE_CONTRACT_NAME, BUSINESSORGANIZATIONTHDORGANIZATIONSTRUCTURE_BUID, BUSINESSORGANIZATIONTHDORGANIZATIONSTRUCTURE_VERSION,inputList,  new ResultsReader() {

				public void readResults(Results results, Query query, Inputs inputs) throws QueryException
				{
					PerformanceReportDTO performanceReportDTO= null;
					while(results.next())
					{
						performanceReportDTO=new PerformanceReportDTO();
						performanceReportDTO.setAssociateID(results.getString("zeroEmployeeId"));
						performanceReportDTO.setFirstName(results.getString("firstName"));
						performanceReportDTO.setLastName(results.getString("lastName"));
						performanceReportDTO.setBusinessName(results.getString("businessUnitName"));
						performanceReportDTO.setJobTtlDesc(results.getString("jobTitleDescription"));
						performanceReportListDTO.add(performanceReportDTO);
						
					}
		
				}
			});
		if (logger.isDebugEnabled()) {
			logger.debug("Finish generateCurrentIndividualPerformanceReport in Reporting DAO");
			
		}
		}
		catch(QueryException queryException){
				/**
						 * Insert message into APPL_LOG table 
						 */
			    logger.error(HrReviewApplLogMessage.create(HrReviewApplLogMessage.DATABASE_ERROR,
				DAOConstants.ERROR_QUERY_PERFORMANCE_CURRENTINDIVIDUAL_PERSONALREPORT,queryException));
				throw new HrReviewException(Constants.BAD_REQUEST,
				DAOConstants.ERROR_QUERY_PERFORMANCE_CURRENTINDIVIDUAL_PERSONALREPORT,queryException);
		}
		return performanceReportListDTO;
	}
	
	
	public static List<PerformanceReportDTO> generateCurrentIndvdlCareerPlnPerformanceReport(String userID,String associateId) throws QueryException, HrReviewException 
	{
		final List<PerformanceReportDTO> performanceReportListDTO=new ArrayList<PerformanceReportDTO>();
		if (logger.isDebugEnabled()) {
			logger.debug("Start generateCurrentIndvdlCareerPlnPerformanceReport in Reporting DAO");
		}
		short reviewTypeCode= 3;
		short reviewCycleTypeCode= 10;
		short geographicRegionCode=1;
		short reviewSectionCode=3;
		List<Short> reviewStatusCodeList= new ArrayList<Short>();
		reviewStatusCodeList.add(new Short("2"));
		reviewStatusCodeList.add(new Short("3"));
		MapStream inputList= new MapStream("readSuccessorPlanUserEmployeeWorkAndAssociateDetails");
		inputList.put("reviewTypeCode", reviewTypeCode);
    	inputList.put("languageCode", LANGUAGE_CD);
        inputList.put("displayFlag", true);
		inputList.put("reviewCycleTypeCode", reviewCycleTypeCode);
     	inputList.put("geographicRegionCode",geographicRegionCode);
		inputList.put("cycleDisplayFlag",true);
		inputList.put("cycleReviewCycleTypeCode",reviewCycleTypeCode);
		inputList.put("cycleGeographicRegionCode",geographicRegionCode);
		inputList.put("reviewSectionCode",reviewSectionCode);
		inputList.put("reviewStatusCodeList",reviewStatusCodeList);
		inputList.put("userId",userID);
		inputList.put("zeroEmployeeId",associateId);
		inputList.put("successionPlanSecurityGroupId",300);
		
		
		try
	      {
			BasicDAO.getResult(HRREVIEW_CONTRACT_NAME, HRREVIEW_BU_ID, HRREVIEW_VERSION,inputList,  new ResultsReader() {

				public void readResults(Results results, Query query, Inputs inputs) throws QueryException
				{
					PerformanceReportDTO performanceReportDTO= null;
					while(results.next())
					{
						performanceReportDTO=new PerformanceReportDTO();
						performanceReportDTO.setAssociateID(results.getString("zeroEmployeeId"));
						performanceReportDTO.setFirstName(results.getString("firstName"));
						performanceReportDTO.setLastName(results.getString("lastName"));
						performanceReportDTO.setJobTtlDesc(results.getString("jobTitleDescription"));
						performanceReportDTO.setSplnGstatDesc(results.getString("successionPlanGoalStatusDescription"));
						performanceReportListDTO.add(performanceReportDTO);
						
					}
		
				}
			});
		if (logger.isDebugEnabled()) {
			logger.debug("Finish generateCurrentIndvdlCareerPlnPerformanceReport in Reporting DAO");
			
		}
		}
		catch(QueryException queryException){
				/**
						 * Insert message into APPL_LOG table 
						 */
			    logger.error(HrReviewApplLogMessage.create(HrReviewApplLogMessage.DATABASE_ERROR,
				DAOConstants.ERROR_QUERY_PERFORMANCE_CURRENTINDIVIDUAL_CAREERREPORT,queryException));
				throw new HrReviewException(Constants.BAD_REQUEST,
				DAOConstants.ERROR_QUERY_PERFORMANCE_CURRENTINDIVIDUAL_CAREERREPORT,queryException);
		}
		return performanceReportListDTO;
	}
	
	public static List<PerformanceReportDTO> generateCurrentIndvdlDevProfilePerformanceReport(String userID,String associateId) throws QueryException, HrReviewException 
	{
		final List<PerformanceReportDTO> performanceReportListDTO=new ArrayList<PerformanceReportDTO>();
		if (logger.isDebugEnabled()) {
			logger.debug("Start generateCurrentIndvdlDevProfilePerformanceReport in Reporting DAO");
		}
		List<Integer> reviewStatusCodeList= new ArrayList<Integer>();
		reviewStatusCodeList.add(new Integer(2));
		reviewStatusCodeList.add(new Integer(3));
		short reviewTypeCode= 3;
		short reviewCycleTypeCode= 10;  
		short geographicRegionCode=1;
		short reviewSectionCode=3;
		short documentReviewTypeCode=3;
		short sectionReviewSectionCode=3;
		
		MapStream inputList= new MapStream("readHumanResourceAndAssociateReviewSectionDetails");
		inputList.put("languageCode", LANGUAGE_CD);
		inputList.put("typeLanguageCode",LANGUAGE_CD);	
		inputList.put("displayFlag", true);
		inputList.put("reviewCycleTypeCode", reviewCycleTypeCode);
		inputList.put("geographicRegionCode", geographicRegionCode);
		inputList.put("reviewSectionCode",reviewSectionCode);
		inputList.put("reviewStatusCodeList",reviewStatusCodeList);
		inputList.put("zeroEmployeeId", associateId);
		inputList.put("userID",userID);
		inputList.put("reviewTypeCode",reviewTypeCode);
		inputList.put("documentReviewTypeCode",documentReviewTypeCode);
		inputList.put("successionPlanSecurityGroupId",300);
		inputList.put("sectionReviewSectionCode",sectionReviewSectionCode);
		inputList.put("currentReviewIndicator","");
		
		
		try
	      {
			BasicDAO.getResult(HRREVIEW_CONTRACT_NAME, HRREVIEW_BU_ID, HRREVIEW_VERSION,inputList,  new ResultsReader() {

				public void readResults(Results results, Query query, Inputs inputs) throws QueryException
				{
					PerformanceReportDTO performanceReportDTO= null;
					while(results.next())
					{
						performanceReportDTO=new PerformanceReportDTO();
						performanceReportDTO.setAssociateID(results.getString("zeroEmployeeId"));
						performanceReportDTO.setEvalCatgryCode(results.getString("evaluateCategoryCode"));
						performanceReportDTO.setReviewDtlTxt(results.getString("reviewDetailText"));
						performanceReportDTO.setFirstName(results.getString("firstName"));
						performanceReportDTO.setLastName(results.getString("lastName"));
						performanceReportDTO.setAssocRevID(results.getString("associateReviewId"));
						performanceReportDTO.setRevTypDesc(results.getString("reviewTypeDescription"));
						performanceReportListDTO.add(performanceReportDTO);
					}
		
				}
			});
		if (logger.isDebugEnabled()) {
			logger.debug("Finish generateCurrentIndvdlDevProfilePerformanceReport in Reporting DAO");
			
		}
		}
		catch(QueryException queryException){
				/**
						 * Insert message into APPL_LOG table 
						 */
			    logger.error(HrReviewApplLogMessage.create(HrReviewApplLogMessage.DATABASE_ERROR,
				DAOConstants.ERROR_QUERY_PERFORMANCE_CURRENTINDIVIDUAL_DEVPROFILEREPORT,queryException));
				throw new HrReviewException(Constants.BAD_REQUEST,
				DAOConstants.ERROR_QUERY_PERFORMANCE_CURRENTINDIVIDUAL_DEVPROFILEREPORT,queryException);
		}
		return performanceReportListDTO;
	}
	
	public static List<PerformanceReportDTO> generateCurrentIndvdlRatingsReport(String userID,String associateId) throws QueryException, HrReviewException 
	{
		final List<PerformanceReportDTO> performanceReportListDTO=new ArrayList<PerformanceReportDTO>();
		if (logger.isDebugEnabled()) {
			logger.debug("Start generateCurrentIndvdlRatingsReport in Reporting DAO");
		}
		MapStream inputList= new MapStream("readSuccessionPlanningUserEmployeeWorkAndHumanResourcesTransferDetails");
		List<Integer> reviewStatusCodeList= new ArrayList<Integer>();
		reviewStatusCodeList.add(new Integer(2));
		reviewStatusCodeList.add(new Integer(3));
		short reviewTypeCode= 3;
		short reviewCycleTypeCode= 10;
		short geographicRegionCode=1;
		short reviewSectionCode=3;
		short performanceReviewSectionCode=4;
		inputList.put("reviewTypeCode",reviewTypeCode);
		inputList.put("performanceReviewSectionCode",performanceReviewSectionCode);
		inputList.put("performanceEvaluateCategoryCode",new Short("1"));
		inputList.put("potentialReviewSectionCode", new Short("4"));
		inputList.put("potentialEvaluateCategoryCode", new Short("2"));
		inputList.put("leadershipReviewSectionCode",new Short("4"));
		inputList.put("leadershipEvaluateCategoryCode",new Short("4"));
		inputList.put("displayFlag",true);
		inputList.put("reviewCycleTypeCode",reviewCycleTypeCode);
		inputList.put("geographicRegionCode",geographicRegionCode);
		inputList.put("reviewingCycleTypeCode",new Short("10"));
		inputList.put("currentReviewIndicator","");
		inputList.put("currentReviewingIndicator","");
		inputList.put("displayingFlag", true);
		inputList.put("geographicRegioningCode", new Short("1"));
		inputList.put("sectionReviewSectionCode",reviewSectionCode);
		inputList.put("reviewStatusCodeList",reviewStatusCodeList);
		inputList.put("zeroEmployeeId",associateId);
		inputList.put("userId",userID);
		inputList.put("successionPlanSecurityGroupId",300);
		
		
		
		try
	      {
			BasicDAO.getResult(HRREVIEW_CONTRACT_NAME, HRREVIEW_BU_ID, HRREVIEW_VERSION,inputList,  new ResultsReader() {

				public void readResults(Results results, Query query, Inputs inputs) throws QueryException
				{
					PerformanceReportDTO performanceReportDTO= null;
					while(results.next())
					{
						performanceReportDTO=new PerformanceReportDTO();
						performanceReportDTO.setAssociateID(results.getString("zeroEmployeeId"));
						performanceReportDTO.setFirstName(results.getString("firstName"));
						performanceReportDTO.setLastName(results.getString("lastName"));
						performanceReportDTO.setPerfEvalRtgCode(results.getString("performanceCode"));
						performanceReportDTO.setLeadershipEvalRtgCode(results.getString("leadershipCode"));
						performanceReportDTO.setPotentialEvalRtgCode(results.getString("potentialCode"));
						performanceReportListDTO.add(performanceReportDTO);
						
					}
		
				}
			});
		if (logger.isDebugEnabled()) {
			logger.debug("Finish generateCurrentIndvdlRatingsReport in Reporting DAO");
			
		}
		}
		catch(QueryException queryException){
				/**
						 * Insert message into APPL_LOG table 
						 */
			    logger.error(HrReviewApplLogMessage.create(HrReviewApplLogMessage.DATABASE_ERROR,
				DAOConstants.ERROR_QUERY_PERFORMANCE_CURRENTINDIVIDUAL_RATINGSREPORT,queryException));
				throw new HrReviewException(Constants.BAD_REQUEST,
				DAOConstants.ERROR_QUERY_PERFORMANCE_CURRENTINDIVIDUAL_RATINGSREPORT,queryException);
		}
		return performanceReportListDTO;
	}
	
	public static List<PerformanceReportDTO> generateCurrentIndvdlRatingsDisplyCd() throws QueryException, HrReviewException 
	{
		final List<PerformanceReportDTO> performanceReportListDTO=new ArrayList<PerformanceReportDTO>();
		if (logger.isDebugEnabled()) {
			logger.debug("Start generateCurrentIndvdlRatingsDisplyCd in Reporting DAO");
		}
		MapStream inputList= new MapStream("readNlsEvaluateRatingCodeByInputList");
		inputList.put("languageCode", LANGUAGE_CD);
		
		
		try
	      {
			BasicDAO.getResult(HRREVIEW_CONTRACT_NAME, HRREVIEW_BU_ID, HRREVIEW_VERSION,inputList,  new ResultsReader() {

				public void readResults(Results results, Query query, Inputs inputs) throws QueryException
				{
					PerformanceReportDTO performanceReportDTO= null;
					while(results.next())
					{
						performanceReportDTO=new PerformanceReportDTO();
						performanceReportDTO.setDispEvalRtgCd(results.getString("evaluateRatingCode"));
						performanceReportDTO.setEvalCatgryCode(results.getString("displayEvaluateRatingCode"));
						performanceReportListDTO.add(performanceReportDTO);
					}
		
				}
			});
		if (logger.isDebugEnabled()) {
			logger.debug("Finish generateCurrentIndvdlRatingsDisplyCd in Reporting DAO");
			
		}
		}
		catch(QueryException queryException){
				/**
						 * Insert message into APPL_LOG table 
						 */
			    logger.error(HrReviewApplLogMessage.create(HrReviewApplLogMessage.DATABASE_ERROR,
				DAOConstants.ERROR_QUERY_PERFORMANCE_CURRENTINDIVIDUAL_RATINGSDISPREPORT,queryException));
				throw new HrReviewException(Constants.BAD_REQUEST,
				DAOConstants.ERROR_QUERY_PERFORMANCE_CURRENTINDIVIDUAL_RATINGSDISPREPORT,queryException);
		}
		return performanceReportListDTO;
	}
	
	}

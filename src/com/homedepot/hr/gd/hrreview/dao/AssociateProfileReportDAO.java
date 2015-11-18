package com.homedepot.hr.gd.hrreview.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.homedepot.hr.gd.hrreview.dto.AssociateProfileDTO;
import com.homedepot.hr.gd.hrreview.exceptions.HrReviewApplLogMessage;
import com.homedepot.hr.gd.hrreview.exceptions.HrReviewException;
import com.homedepot.hr.gd.hrreview.interfaces.Constants;
import com.homedepot.hr.gd.hrreview.interfaces.DAOConstants;
import com.homedepot.hr.gd.hrreview.util.AppUtil;
import com.homedepot.ta.aa.dao.Inputs;
import com.homedepot.ta.aa.dao.Query;
import com.homedepot.ta.aa.dao.Results;
import com.homedepot.ta.aa.dao.ResultsReader;
import com.homedepot.ta.aa.dao.basic.BasicDAO;
import com.homedepot.ta.aa.dao.exceptions.QueryException;
import com.homedepot.ta.aa.dao.stream.MapStream;

public class AssociateProfileReportDAO implements DAOConstants, Constants {

	private static final Logger logger = Logger.getLogger(AssociateProfileReportDAO.class);	

	public static AssociateProfileDTO generatePersonalReport(String userID,String associateId) throws QueryException, HrReviewException 
	{
		if (logger.isDebugEnabled()) {
			logger.debug("Start generatePersonalReport in AssociateProfileReportDAO");
		}
		MapStream inputList= new MapStream("readSuccessionPlanUserEmployeeWorkAndEventPostionsDetails");
		short statusCode= 100;
		List<String>recordsStatusList= new ArrayList<String>();
		List<String>personRecordStatusList= new ArrayList<String>();
		recordsStatusList.add("A");
		personRecordStatusList.add("A");
		inputList.put("userId",userID);
		inputList.put("zeroEmployeeId",associateId);
		inputList.put("languageCode", LANGUAGE_CD);
		inputList.put("successionPlanEmployeeStatusCode", statusCode);
		inputList.put("successionPlanSecurityGroupId",300);
		inputList.putAllowNull("recordStatusList",recordsStatusList);
		inputList.putAllowNull("personRecordStatusList",recordsStatusList);
		inputList.put("humanResourcesStoreEffectiveBeginDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.put("humanResourcesStoreEffectiveEndDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("humanResourcesStoreEffectiveBeginDateLessThanEqualTo", true);
		inputList.addQualifier("humanResourcesStoreEffectiveEndDateGreaterThanEqualTo", true);
		inputList.put("effectiveDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("effectiveDateLessThanEqualTo", true);
		inputList.put("departmentEffectiveBeginDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("departmentEffectiveBeginDateLessThanEqualTo", true);
		inputList.put("divisionHumanResourcesStoreEffectiveBeginDate",new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("divisionHumanResourcesStoreEffectiveBeginDateLessThanEqualTo",true);
		inputList.put("personEffectiveDate",new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("personEffectiveDateLessThanEqualTo",true);
		inputList.put("effectiveBeginDate",new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("effectiveBeginDateLessThanEqualTo",true);
		
		final AssociateProfileDTO associateProfileDTO= new AssociateProfileDTO();
		
		try
	      {
			BasicDAO.getResult(HRREVIEW_CONTRACT_NAME, HRREVIEW_BU_ID, HRREVIEW_VERSION,inputList,  new ResultsReader() {

				public void readResults(Results results, Query query, Inputs inputs) throws QueryException
				{
					
					while(results.next())
					{
						
						associateProfileDTO.setAssociateID(results.getString("zeroEmployeeId"));
						associateProfileDTO.setFirstName(results.getString("firstName"));
						associateProfileDTO.setLastName(results.getString("lastName"));
						associateProfileDTO.setDivisonName(results.getString("divisionName"));
						associateProfileDTO.setRegion(results.getString("operationsGroupName"));
						associateProfileDTO.setLocation(results.getString("humanResourcesSystemStoreName"));
						associateProfileDTO.setDeptName(results.getString("humanResourcesSystemDepartmentName"));
						associateProfileDTO.setDateHired(AppUtil.convertDateToString(results.getDate("hireDateOrigin")));
						associateProfileDTO.setEffDate(AppUtil.convertDateToString(results.getDate("effectiveDate")));
						associateProfileDTO.setStrNbr(results.getString("humanResourcesSystemStoreNumber"));
						associateProfileDTO.setCtznCntryCode(results.getString("citizenCountryCode"));
						associateProfileDTO.setJobTitleDesc(results.getString("jobTitleDescription"));
					}
		
				}
			});
		if (logger.isDebugEnabled()) {
			logger.debug("Finish generatePersonalReport in AssociateProfileReportDAO");
			
		}
		}
		catch(QueryException queryException){
				/**
						 * Insert message into APPL_LOG table 
						 */
			    logger.error(HrReviewApplLogMessage.create(HrReviewApplLogMessage.DATABASE_ERROR,
				DAOConstants.ERROR_QUERY_ASSOCITEPROFILE_CURRINDVDL_PERSONAL,queryException));
				throw new HrReviewException(Constants.BAD_REQUEST,
				DAOConstants.ERROR_QUERY_ASSOCITEPROFILE_CURRINDVDL_PERSONAL,queryException);
		}
		return associateProfileDTO;
	}
	
	public static List<AssociateProfileDTO> generateEducationReport(String userID,String associateId) throws QueryException, HrReviewException 
	{
		if (logger.isDebugEnabled()) {
			logger.debug("Start generateEducationReport in AssociateProfileReportDAO");
		}
		MapStream inputList= new MapStream("readSuccessionPlanUserEmployeeWorkAndEducateDetails");
		inputList.put("userId",userID);
		inputList.put("zeroEmployeeId",associateId);
		inputList.put("languageCode", LANGUAGE_CD);
		inputList.put("successionPlanSecurityGroupId",300);
		
		final List<AssociateProfileDTO> associateProfileListDTO= new ArrayList<AssociateProfileDTO>();
				
		try
	      {
			BasicDAO.getResult(HRREVIEW_CONTRACT_NAME, HRREVIEW_BU_ID, HRREVIEW_VERSION,inputList,  new ResultsReader() {

				public void readResults(Results results, Query query, Inputs inputs) throws QueryException
				{
					AssociateProfileDTO associateProfileDTO= null;
					while(results.next())
					{
						associateProfileDTO=new AssociateProfileDTO();
						associateProfileDTO.setSchoolGradYr(results.getString("schoolGraduateYear"));
						associateProfileDTO.setLocText(results.getString("location"));
						associateProfileDTO.setDegreeDesc(results.getString("degreeDescription"));
						associateProfileDTO.setCollegeMjr(results.getString("collegeMajor"));
						associateProfileDTO.setGradeYesNo(results.getString("graduateYesNo"));
						associateProfileDTO.setFirstName(results.getString("firstName"));
						associateProfileDTO.setLastName(results.getString("lastName"));
						associateProfileDTO.setSchool(results.getString("schoolName"));
						associateProfileListDTO.add(associateProfileDTO);
					}
		
				}
			});
		if (logger.isDebugEnabled()) {
			logger.debug("Finish generateEducationReport in AssociateProfileReportDAO");
			
		}
		}
		catch(QueryException queryException){
				/**
						 * Insert message into APPL_LOG table 
						 */
			    logger.error(HrReviewApplLogMessage.create(HrReviewApplLogMessage.DATABASE_ERROR,
				DAOConstants.ERROR_QUERY_ASSOCITEPROFILE_CURRINDVDL_EDUCATION,queryException));
				throw new HrReviewException(Constants.BAD_REQUEST,
				DAOConstants.ERROR_QUERY_ASSOCITEPROFILE_CURRINDVDL_EDUCATION,queryException);
		}
		return associateProfileListDTO;
	}
	
	public static List<AssociateProfileDTO> generateCourseReport(String userID,String associateId) throws QueryException, HrReviewException 
	{
		if (logger.isDebugEnabled()) {
			logger.debug("Start generateCourseReport in AssociateProfileReportDAO");
		}
		MapStream inputList= new MapStream("readSuccessionPlanUserEmployeeAndWorkCourseDetails");
		inputList.put("userId",userID);
		inputList.put("zeroEmployeeId",associateId);
		inputList.put("languageCode", LANGUAGE_CD);
		inputList.put("successionPlanSecurityGroupId",300);
		final List<AssociateProfileDTO> associateProfileListDTO= new ArrayList<AssociateProfileDTO>();
						
		try
	      {
			BasicDAO.getResult(HRREVIEW_CONTRACT_NAME, HRREVIEW_BU_ID, HRREVIEW_VERSION,inputList,  new ResultsReader() {

				public void readResults(Results results, Query query, Inputs inputs) throws QueryException
				{
					AssociateProfileDTO associateProfileDTO= null;
					
					while(results.next())
					{
						associateProfileDTO=new AssociateProfileDTO();
						associateProfileDTO.setAssociateID(results.getString("zeroEmployeeId"));
						associateProfileDTO.setLastUpdTs(results.getString("lastUpdateTimestamp"));
						associateProfileDTO.setSplnCourseDesc(results.getString("successionPlanCourseDescription"));
						associateProfileDTO.setOvrdSchoolName(results.getString("schoolName"));
						associateProfileDTO.setCourseEndDate(AppUtil.convertDateToString(results.getDate("endDate")));
						associateProfileDTO.setFirstName(results.getString("firstName"));
						associateProfileDTO.setLastName(results.getString("lastName"));
						associateProfileListDTO.add(associateProfileDTO);
						
					}
		
				}
			});
		if (logger.isDebugEnabled()) {
			logger.debug("Finish generateCourseReport in AssociateProfileReportDAO");
			
		}
		}
		catch(QueryException queryException){
				/**
						 * Insert message into APPL_LOG table 
						 */
			    logger.error(HrReviewApplLogMessage.create(HrReviewApplLogMessage.DATABASE_ERROR,
				DAOConstants.ERROR_QUERY_ASSOCITEPROFILE_CURRINDVDL_COURSE,queryException));
				throw new HrReviewException(Constants.BAD_REQUEST,
				DAOConstants.ERROR_QUERY_ASSOCITEPROFILE_CURRINDVDL_COURSE,queryException);
		
		}
		return associateProfileListDTO;
	}
	
	public static List<AssociateProfileDTO> generateTHDWorkHistoryReport(String userID,String associateId) throws QueryException, HrReviewException 
	{
		if (logger.isDebugEnabled()) {
			logger.debug("Start generateTHDWorkHistoryReport in AssociateProfileReportDAO");
		}
		final ArrayList<AssociateProfileDTO> associateProfileListDTO= new ArrayList<AssociateProfileDTO>();
		List<String> zeroEmployeeIdList= new ArrayList<String>();
		zeroEmployeeIdList.add(associateId);
		List<String> recordStatusList= new ArrayList<String>();
		recordStatusList.add("A");
		
		MapStream inputList= new MapStream("readSuccessionPlanUserEmployeeAndWorkPositionsDetails");
		inputList.put("userId",userID);
		inputList.put("zeroEmployeeIdList",zeroEmployeeIdList);
		inputList.put("languageCode", LANGUAGE_CD);
		inputList.put("successionPlanSecurityGroupId",300);
		inputList.put("recordStatusList",recordStatusList );
		inputList.put("humanResourcesStoreEffectiveBeginDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.put("humanResourcesStoreEffectiveEndDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("humanResourcesStoreEffectiveBeginDateLessThanEqualTo", true);
		inputList.addQualifier("humanResourcesStoreEffectiveEndDateGreaterThanEqualTo", true);
		inputList.put("effectiveDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("effectiveDateLessThanEqualTo", true);
		inputList.put("divisionHumanResourcesStoreEffectiveBeginDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("divisionHumanResourcesStoreEffectiveBeginDateLessThanEqualTo", true);
		inputList.put("effectiveBeginDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("effectiveBeginDateLessThanEqualTo", true);
		 
						
		try
	      {
			BasicDAO.getResult(HRREVIEW_CONTRACT_NAME, HRREVIEW_BU_ID, HRREVIEW_VERSION,inputList,  new ResultsReader() {

				public void readResults(Results results, Query query, Inputs inputs) throws QueryException
				{
					AssociateProfileDTO associateProfileDTO= null;
					while(results.next())
					{
						associateProfileDTO=new AssociateProfileDTO();
						associateProfileDTO.setJobTitleCode(results.getString("jobTitleId"));
						associateProfileDTO.setJobTitle(results.getString("jobTitleDescription"));
						associateProfileDTO.setDivisonName(results.getString("divisionName"));
						associateProfileDTO.setStartDate(results.getString("effectiveDate"));
						associateProfileDTO.setEndDate(results.getString("relationEndDate"));
						associateProfileListDTO.add(associateProfileDTO);
					}
		
				}
			});
		if (logger.isDebugEnabled()) {
			logger.debug("Finish generateTHDWorkHistoryReport in AssociateProfileReportDAO");
			
		}
		}
		catch(QueryException queryException){
				/**
						 * Insert message into APPL_LOG table 
						 */
			    logger.error(HrReviewApplLogMessage.create(HrReviewApplLogMessage.DATABASE_ERROR,
				DAOConstants.ERROR_QUERY_ASSOCITEPROFILE_CURRINDVDL_THDWorkHistory,queryException));
				throw new HrReviewException(Constants.BAD_REQUEST,
				DAOConstants.ERROR_QUERY_ASSOCITEPROFILE_CURRINDVDL_THDWorkHistory,queryException);
		}
		return associateProfileListDTO;
	}	
	
	public static List<AssociateProfileDTO> generateExternalEmploymentReport(String userID,String associateId) throws QueryException, HrReviewException 
	{
		if (logger.isDebugEnabled()) {
			logger.debug("Start generateExternalEmploymentReport in AssociateProfileReportDAO");
		}
		MapStream inputList= new MapStream("readSuccessionPlanUserEmployeeWorkAndHistoryDetails");
		inputList.put("userId",userID);
		inputList.put("zeroEmployeeId",associateId);
		inputList.put("successionPlanSecurityGroupId",300);
		inputList.put("employmentHistoryTypeIndicator","E");
		
		
		final ArrayList<AssociateProfileDTO> associateProfileListDTO= new ArrayList<AssociateProfileDTO>();
		
		try
	      {
			BasicDAO.getResult(HRREVIEW_CONTRACT_NAME, HRREVIEW_BU_ID, HRREVIEW_VERSION,inputList,  new ResultsReader() {

				public void readResults(Results results, Query query, Inputs inputs) throws QueryException
				{
					AssociateProfileDTO associateProfileDTO= null;
					while(results.next())
					{
						associateProfileDTO=new AssociateProfileDTO();
						associateProfileDTO.setOvrdJobTtlDesc(results.getString("overrideJobTitleDescription"));
						associateProfileDTO.setEmplyrName(results.getString("employerName"));
						associateProfileDTO.setWrkBgnDate(results.getString("workBeginDate"));
						associateProfileDTO.setWrkEndDate(results.getString("workEndDate"));
						associateProfileListDTO.add(associateProfileDTO);
						
					}
		
				}
			});
		if (logger.isDebugEnabled()) {
			logger.debug("Finish generateExternalEmploymentReport in AssociateProfileReportDAO");
			
		}
		}
		catch(QueryException queryException){
				/**
						 * Insert message into APPL_LOG table 
						 */
			    logger.error(HrReviewApplLogMessage.create(HrReviewApplLogMessage.DATABASE_ERROR,
				DAOConstants.ERROR_QUERY_ASSOCITEPROFILE_CURRINDVDL_ExternalEmploymentHistory,queryException));
				throw new HrReviewException(Constants.BAD_REQUEST,
				DAOConstants.ERROR_QUERY_ASSOCITEPROFILE_CURRINDVDL_ExternalEmploymentHistory,queryException);
		}
		return associateProfileListDTO;
	}	
	
	public static List<AssociateProfileDTO> generateCareerInterestReport(String userID,String associateId) throws QueryException, HrReviewException 
	{
		if (logger.isDebugEnabled()) {
			logger.debug("Start generateCareerInterestReport in AssociateProfileReportDAO");
		}
		MapStream inputList= new MapStream("readSuccessionPlanUserEmployeeWorkAndIndividualPlanDetails");
		inputList.put("userId",userID);
		inputList.put("zeroEmployeeId",associateId);
		inputList.put("languageCode", LANGUAGE_CD);
		inputList.put("successionPlanSecurityGroupId",300);
		inputList.put("effectiveBeginDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("effectiveBeginDateLessThanEqualTo", true);
		final List<AssociateProfileDTO> associateProfileListDTO= new ArrayList<AssociateProfileDTO>();
		
		try
	      {
			BasicDAO.getResult(HRREVIEW_CONTRACT_NAME, HRREVIEW_BU_ID, HRREVIEW_VERSION,inputList,  new ResultsReader() {

				public void readResults(Results results, Query query, Inputs inputs) throws QueryException
				{
					AssociateProfileDTO associateProfileDTO= null;
					while(results.next())
					{
						associateProfileDTO=new AssociateProfileDTO();
						associateProfileDTO.setJobTitleDesc(results.getString("successionPlanJobTitleGroupDescription"));
						associateProfileDTO.setPosition(results.getString("position"));
						associateProfileDTO.setSplnGstatDesc(results.getString("successionPlanGoalStatusDescription"));
						associateProfileListDTO.add(associateProfileDTO);
					}
		
				}
			});
		if (logger.isDebugEnabled()) {
			logger.debug("Finish generateCareerInterestReport in AssociateProfileReportDAO");
			
		}
		}
		catch(QueryException queryException){
				/**
						 * Insert message into APPL_LOG table 
						 */
			    logger.error(HrReviewApplLogMessage.create(HrReviewApplLogMessage.DATABASE_ERROR,
				DAOConstants.ERROR_QUERY_ASSOCITEPROFILE_CURRINDVDL_CAREERINTEREST,queryException));
				throw new HrReviewException(Constants.BAD_REQUEST,
				DAOConstants.ERROR_QUERY_ASSOCITEPROFILE_CURRINDVDL_CAREERINTEREST,queryException);
		}
		return associateProfileListDTO;
	}
	
	public static AssociateProfileDTO generateMobileReport(String userID,String associateId) throws QueryException, HrReviewException 
	{
		if (logger.isDebugEnabled()) {
			logger.debug("Start generateMobileReport in AssociateProfileReportDAO");
		}
		MapStream inputList= new MapStream("readSuccessionPlanUserEmployeeWorkAndHumanResourceDetails");
		inputList.put("userId",userID);
		inputList.put("zeroEmployeeId",associateId);
		inputList.put("languageCode", LANGUAGE_CD);
		final AssociateProfileDTO associateProfileDTO= new AssociateProfileDTO();
		
		try
	      {
			BasicDAO.getResult(HRREVIEW_CONTRACT_NAME, HRREVIEW_BU_ID, HRREVIEW_VERSION,inputList,  new ResultsReader() {

				public void readResults(Results results, Query query, Inputs inputs) throws QueryException
				{
					
					while(results.next())
					{
						associateProfileDTO.setAssociateID(results.getString("zeroEmployeeId"));
						associateProfileDTO.setTypeCd(results.getString("typeCode"));
						associateProfileDTO.setReloLastUpdTs(results.getString("lastUpdateTimestamp"));
						associateProfileDTO.setReloDesription(results.getString("description"));
						associateProfileDTO.setDependson(results.getString("relocationCommentText"));
						associateProfileDTO.setFirstName(results.getString("firstName"));
						associateProfileDTO.setLastName(results.getString("lastName"));
					}
		
				}
			});
		if (logger.isDebugEnabled()) {
			logger.debug("Finish generateMobileReport in AssociateProfileReportDAO");
			
		}
		}
		catch(QueryException queryException){
				/**
						 * Insert message into APPL_LOG table 
						 */
			    logger.error(HrReviewApplLogMessage.create(HrReviewApplLogMessage.DATABASE_ERROR,
				DAOConstants.ERROR_QUERY_ASSOCITEPROFILE_CURRINDVDL_MOBILITY,queryException));
				throw new HrReviewException(Constants.BAD_REQUEST,
				DAOConstants.ERROR_QUERY_ASSOCITEPROFILE_CURRINDVDL_MOBILITY,queryException);
		}
		return associateProfileDTO;
	}
	
	public static List<AssociateProfileDTO> generateLanguageReport(String userID,String associateId) throws QueryException, HrReviewException 
	{
		if (logger.isDebugEnabled()) {
			logger.debug("Start generateLanguageReport in AssociateProfileReportDAO");
		}
		MapStream inputList= new MapStream("readSuccessionPlanUserEmployeeWorkAndLanguageDetails");
		inputList.put("userId",userID);
		inputList.put("zeroEmployeeId",associateId);
		inputList.put("languageCode", LANGUAGE_CD);
		final List<AssociateProfileDTO> associateProfileListDTO= new ArrayList<AssociateProfileDTO>();
		
		try
	      {
			BasicDAO.getResult(HRREVIEW_CONTRACT_NAME, HRREVIEW_BU_ID, HRREVIEW_VERSION,inputList,  new ResultsReader() {

				public void readResults(Results results, Query query, Inputs inputs) throws QueryException
				{
					AssociateProfileDTO associateProfileDTO= null;
					while(results.next())
					{
						associateProfileDTO=new AssociateProfileDTO();
						associateProfileDTO.setAssociateID(results.getString("zeroEmployeeId"));
						associateProfileDTO.setLangUseTypCd(results.getString("languageUseTypeCode"));
						associateProfileDTO.setLangLastUpdTs(results.getString("lastUpdateTimestamp"));
						associateProfileDTO.setLanguage(results.getString("language"));
						associateProfileDTO.setPrefLangFlg(results.getString("preferenceLanguageFlag"));
						associateProfileDTO.setLangUseTypDesc(results.getString("languageUseTypeDescription"));
						associateProfileDTO.setLangPrfncyDesc(results.getString("languageProficiencyDescription"));
						associateProfileListDTO.add(associateProfileDTO);
					}
		
				}
			});
		if (logger.isDebugEnabled()) {
			logger.debug("Finish generateLanguageReport in AssociateProfileReportDAO");
			
		}
		}
		catch(QueryException queryException){
				/**
						 * Insert message into APPL_LOG table 
						 */
			    logger.error(HrReviewApplLogMessage.create(HrReviewApplLogMessage.DATABASE_ERROR,
				DAOConstants.ERROR_QUERY_ASSOCITEPROFILE_CURRINDVDL_LANGUAGE,queryException));
				throw new HrReviewException(Constants.BAD_REQUEST,
				DAOConstants.ERROR_QUERY_ASSOCITEPROFILE_CURRINDVDL_LANGUAGE,queryException);
			
		}
		return associateProfileListDTO;
	}
	
	
	
	
	
}


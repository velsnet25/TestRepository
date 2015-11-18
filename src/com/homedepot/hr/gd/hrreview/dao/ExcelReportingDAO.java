package com.homedepot.hr.gd.hrreview.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.homedepot.hr.gd.hrreview.dto.ExcelReportDTO;
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

public class ExcelReportingDAO implements DAOConstants, Constants {

	private static final Logger logger = Logger.getLogger(ExcelReportingDAO.class);	
	static String date = "2099-12-31";
	static java.sql.Date divisionEffectiveEndDate = java.sql.Date.valueOf(date);

	public static List<ExcelReportDTO> generateExcelBasicReport(String userID,final boolean ratingsFlag,List<String> zeroEmployeeIdList) throws QueryException, HrReviewException 
	{
		final List<ExcelReportDTO> excelReportDTOList= new ArrayList<ExcelReportDTO>();

        if (logger.isDebugEnabled()) {
			logger.debug("Start generateExcelBasicReport in Reporting DAO");
		}
		 
		MapStream inputList= new MapStream("readSuccessionPlanUserEmployeeWorkAndActivityDetails");
		inputList.put("userId",userID);
		inputList.put("divisionEffectiveEndDate", divisionEffectiveEndDate);
		inputList.put("languageCode", LANGUAGE_CD);
		inputList.put("recordStatusIndicator", "A");
		inputList.put("successionPlanSecurityGroupId",300);
		inputList.put("departmentLanguageCode",LANGUAGE_CD);
		inputList.put("effectiveEndDate",new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("effectiveEndDateGreaterThanEqualTo",true);
		inputList.put("effectiveBeginDate",new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("effectiveBeginDateLessThanEqualTo",true);
		inputList.put("humanResourcesStoreEffectiveBeginDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.put("humanResourcesStoreEffectiveEndDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("humanResourcesStoreEffectiveBeginDateLessThanEqualTo", true);
		inputList.addQualifier("humanResourcesStoreEffectiveEndDateGreaterThanEqualTo", true);
		inputList.put("departmentEffectiveBeginDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("departmentEffectiveBeginDateLessThanEqualTo", true);
		inputList.put("jobEffectiveBeginDate",new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("jobEffectiveBeginDateLessThanEqualTo",true);
		inputList.put("zeroEmployeeIdList",zeroEmployeeIdList);  
		
			
		try
	      {
			BasicDAO.getResult(HRREVIEW_CONTRACT_NAME, HRREVIEW_BU_ID, HRREVIEW_VERSION,inputList,  new ResultsReader() {

				public void readResults(Results results, Query query, Inputs inputs) throws QueryException
				{
					ExcelReportDTO excelReportDTO=null;
					
					while(results.next())
					{
						excelReportDTO=new ExcelReportDTO();
						if(!(AppUtil.isEmptyString(results.getString("zeroEmployeeId"))))
						excelReportDTO.setAssociateID(results.getString("zeroEmployeeId"));
						else
						excelReportDTO.setAssociateID("-");	
						if(!(AppUtil.isEmptyString(results.getString("employeeFirstName"))))
						excelReportDTO.setFirstName(results.getString("employeeFirstName"));
						else
						excelReportDTO.setFirstName("-");
						if(!(AppUtil.isEmptyString(results.getString("employeeLastName"))))
						excelReportDTO.setLastName(results.getString("employeeLastName"));
						else
						excelReportDTO.setLastName("-");
						if(!(AppUtil.isEmptyString(results.getString("jobTitleDescription"))))
						excelReportDTO.setJobTitle(results.getString("jobTitleDescription"));
						else
						excelReportDTO.setJobTitle("-");
						if(!(AppUtil.isEmptyString(results.getString("currentDivisionName"))))
							excelReportDTO.setDivisionName(results.getString("currentDivisionName"));
							else
							excelReportDTO.setDivisionName("-");
						if(!(AppUtil.isEmptyString(results.getString("humanResourcesSystemDepartmentName"))))
						excelReportDTO.setDepartmentName(results.getString("humanResourcesSystemDepartmentName"));
						else
						excelReportDTO.setDepartmentName("-");
						if(!(AppUtil.isEmptyString(results.getString("humanResourcesSystemStoreName"))))
						excelReportDTO.setLocation(results.getString("humanResourcesSystemStoreName"));
						else
						excelReportDTO.setLocation("-");
						excelReportDTO.setEffectiveBgnDate(results.getString("effectiveBeginDate"));
						excelReportDTO.setDivisionCode(results.getString("humanResourcesSystemDivisionCode"));
						if(!(AppUtil.isEmptyString(results.getString("humanResourcesSystemStoreNumber"))))
						excelReportDTO.setStoreNumber(results.getString("humanResourcesSystemStoreNumber"));
						else
						excelReportDTO.setStoreNumber("-");
						
						if(ratingsFlag)
						{
						excelReportDTO.setPerformanceCode(results.getString("performanceReviewRatingCode"));
						excelReportDTO.setLeadershipCode(results.getString("leadershipRatingCode"));
						excelReportDTO.setPotentialCode(results.getString("potentialRatingCode"));
						}
						
						excelReportDTOList.add(excelReportDTO);
					}
					
		
				}
			});
		if (logger.isDebugEnabled()) {
			logger.debug("Finish generateExcelBasicReport in Reporting DAO");
			
		}
		}
		catch(QueryException queryException){
				/**
						 * Insert message into APPL_LOG table 
						 */
			    logger.error(HrReviewApplLogMessage.create(HrReviewApplLogMessage.DATABASE_ERROR,
				DAOConstants.ERROR_GET_QUERY_JOBREQUISITION_DETAILS_ERROR_DESC,queryException));
				throw new HrReviewException(Constants.BAD_REQUEST,
				DAOConstants.ERROR_GET_QUERY_EXCEL_BASIC_REPORT_DESC,queryException);
		}
		return excelReportDTOList;
	}
	
	public static List<ExcelReportDTO> generateExcelWorkHistoryExternalReport(String userID,final boolean ratingsFlag,List<String> zeroEmployeeIdList) throws QueryException, HrReviewException 
	{
		final List<ExcelReportDTO> excelReportDTOList= new ArrayList<ExcelReportDTO>();
		if (logger.isDebugEnabled()) {
			logger.debug("Start generateExcelWorkHistoryExternalReport in Reporting DAO");
		}
		MapStream inputList= new MapStream("readSuccessionPlanUserEmployeeWorkAndStoreOrganizationDetails");
		
		inputList.put("userId",userID);
		inputList.put("successionPlanSecurityGroupId",300);
		inputList.put("languageCode", LANGUAGE_CD);
		inputList.put("departmentLanguageCode", LANGUAGE_CD);
		inputList.put("humanResourcesStoreEffectiveBeginDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.put("humanResourcesStoreEffectiveEndDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("humanResourcesStoreEffectiveBeginDateLessThanEqualTo", true);
		inputList.addQualifier("humanResourcesStoreEffectiveEndDateGreaterThanEqualTo", true);
		inputList.put("departmentLanguageCode", LANGUAGE_CD);
		inputList.put("effectiveBeginDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.put("effectiveEndDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("effectiveBeginDateLessThanEqualTo", true);
		inputList.addQualifier("effectiveEndDateGreaterThanEqualTo", true);
		inputList.put("recordStatusIndicator", "A");
		inputList.put("employmentHistoryTypeIndicator","E");
		inputList.put("divisionEffectiveEndDate",divisionEffectiveEndDate);
		inputList.put("departmentEffectiveBeginDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("departmentEffectiveBeginDateLessThanEqualTo", true);
		inputList.put("jobEffectiveBeginDate",new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("jobEffectiveBeginDateLessThanEqualTo",true);
		inputList.put("zeroEmployeeIdList",zeroEmployeeIdList); 
		
		
		try
	      {
					
			BasicDAO.getResult(HRREVIEW_CONTRACT_NAME, HRREVIEW_BU_ID, HRREVIEW_VERSION,inputList,  new ResultsReader() {

				public void readResults(Results results, Query query, Inputs inputs) throws QueryException
				{
					
					ExcelReportDTO excelReportDTO= null;
					while(results.next())
					{
						excelReportDTO=new ExcelReportDTO();
						if(!(AppUtil.isEmptyString(results.getString("zeroEmployeeId"))))
							excelReportDTO.setAssociateID(results.getString("zeroEmployeeId"));
							else
							excelReportDTO.setAssociateID("-");	
							if(!(AppUtil.isEmptyString(results.getString("employeeFirstName"))))
							excelReportDTO.setFirstName(results.getString("employeeFirstName"));
							else
							excelReportDTO.setFirstName("-");
							if(!(AppUtil.isEmptyString(results.getString("employeeLastName"))))
							excelReportDTO.setLastName(results.getString("employeeLastName"));
							else
							excelReportDTO.setLastName("-");
							if(!(AppUtil.isEmptyString(results.getString("jobTitleDescription"))))
							excelReportDTO.setJobTitle(results.getString("jobTitleDescription"));
							else
							excelReportDTO.setJobTitle("-");
							if(!(AppUtil.isEmptyString(results.getString("currentDivisionName"))))
								excelReportDTO.setDivisionName(results.getString("currentDivisionName"));
								else
								excelReportDTO.setDivisionName("-");
							if(!(AppUtil.isEmptyString(results.getString("humanResourcesSystemDepartmentName"))))
							excelReportDTO.setDepartmentName(results.getString("humanResourcesSystemDepartmentName"));
							else
							excelReportDTO.setDepartmentName("-");
							if(!(AppUtil.isEmptyString(results.getString("humanResourcesSystemStoreName"))))
							excelReportDTO.setLocation(results.getString("humanResourcesSystemStoreName"));
							else
							excelReportDTO.setLocation("-");
							excelReportDTO.setEffectiveBgnDate(results.getString("effectiveBeginDate"));
							excelReportDTO.setDivisionCode(results.getString("humanResourcesSystemDivisionCode"));
							if(!(AppUtil.isEmptyString(results.getString("humanResourcesSystemStoreNumber"))))
							excelReportDTO.setStoreNumber(results.getString("humanResourcesSystemStoreNumber"));
							else
							excelReportDTO.setStoreNumber("-");
						//Ratings
						if(ratingsFlag)
						{						
						excelReportDTO.setPerformanceCode(results.getString("performanceReviewRatingCode"));
						excelReportDTO.setLeadershipCode(results.getString("leadershipRatingCode"));
						excelReportDTO.setPotentialCode(results.getString("potentialRatingCode"));
						}
						excelReportDTO.setCompany(results.getString("employerName"));
						excelReportDTO.setOverrideJobTitleDesc(results.getString("overrideJobTitleDescription"));
						excelReportDTO.setStartDate(results.getString("workBeginDate"));
						excelReportDTO.setEndDate(results.getString("workEndDate"));
						
						excelReportDTOList.add(excelReportDTO);
					
					}
		
				}
			});
		if (logger.isDebugEnabled()) {
			logger.debug("Finish generateExcelWorkHistoryExternalReport in Reporting DAO");
		}
		}
		catch(QueryException queryException){
				/**
						 * Insert message into APPL_LOG table 
						 */
			logger.error(HrReviewApplLogMessage.create(HrReviewApplLogMessage.DATABASE_ERROR,
				DAOConstants.ERROR_GET_QUERY_EXCEL_WORKHISTORY_EXTERNALREPORT_DESC,queryException));
				throw new HrReviewException(Constants.BAD_REQUEST,
				DAOConstants.ERROR_GET_QUERY_EXCEL_WORKHISTORY_EXTERNALREPORT_DESC,queryException);
			
		}
		return excelReportDTOList;
	}
	
	
		public static List<ExcelReportDTO> generateExcelWorkHistoryInternalReport(String userID,final boolean ratingsFlag,List<String> zeroEmployeeIdList) throws QueryException, HrReviewException 
	{
		final List<ExcelReportDTO> excelReportDTOList= new ArrayList<ExcelReportDTO>();

		List<String> recordStatusList= new ArrayList<String>();
		recordStatusList.add("A");
		if (logger.isDebugEnabled()) {
			logger.debug("Start generateExcelWorkHistoryInternalReport in Reporting DAO");
		}                                   
		MapStream inputList= new MapStream("readSuccessionPlanUserEmployeeAndWorkPositionsDetails");
		inputList.put("userId",userID);
		inputList.put("successionPlanSecurityGroupId",300);
		inputList.put("zeroEmployeeIdList", zeroEmployeeIdList);
		inputList.put("humanResourcesStoreEffectiveBeginDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.put("humanResourcesStoreEffectiveEndDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("humanResourcesStoreEffectiveBeginDateLessThanEqualTo", true);
		inputList.addQualifier("humanResourcesStoreEffectiveEndDateGreaterThanEqualTo", true);
		inputList.put("languageCode",LANGUAGE_CD);
		inputList.put("effectiveDate",new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("effectiveDateLessThanEqualTo", true);
		inputList.put("recordStatusList",recordStatusList );
		inputList.put("divisionHumanResourcesStoreEffectiveBeginDate",new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("divisionHumanResourcesStoreEffectiveBeginDateLessThanEqualTo",true);
		inputList.put("effectiveBeginDate",new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("effectiveBeginDateLessThanEqualTo",true);
		
		try
	      {
					
			BasicDAO.getResult(HRREVIEW_CONTRACT_NAME, HRREVIEW_BU_ID, HRREVIEW_VERSION,inputList,  new ResultsReader() {

				public void readResults(Results results, Query query, Inputs inputs) throws QueryException
				{
					
					ExcelReportDTO excelReportDTO= null;
					while(results.next())
					{
						excelReportDTO=new ExcelReportDTO();
						if(!(AppUtil.isEmptyString(results.getString("zeroEmployeeId"))))
						excelReportDTO.setAssociateID(results.getString("zeroEmployeeId"));
						else
						excelReportDTO.setAssociateID("-");
						if(!(AppUtil.isEmptyString(results.getString("firstName"))))
						excelReportDTO.setFirstName(results.getString("firstName"));
						else
						excelReportDTO.setFirstName("-");
						if(!(AppUtil.isEmptyString(results.getString("lastName"))))
						excelReportDTO.setLastName(results.getString("lastName"));
						else
						excelReportDTO.setLastName("-");
                        excelReportDTO.setDivisionCode(results.getString("humanResourcesSystemDivisionCode"));

						if(!(AppUtil.isEmptyString(results.getString("jobTitleDescription"))))
						excelReportDTO.setOverrideJobTitleDesc(results.getString("jobTitleDescription"));
						else
						excelReportDTO.setOverrideJobTitleDesc("-");
						if(!(AppUtil.isEmptyString(results.getString("divisionName"))))
						excelReportDTO.setOverrideDivisionName(results.getString("divisionName"));
						else
						excelReportDTO.setOverrideDivisionName("-");
						if(!(AppUtil.isEmptyString(results.getString("effectiveDate"))))
						excelReportDTO.setStartDate(results.getString("effectiveDate"));
						else
						excelReportDTO.setStartDate("-");
						if(!(AppUtil.isEmptyString(results.getString("relationEndDate"))))
						excelReportDTO.setEndDate(results.getString("relationEndDate"));
						else
						excelReportDTO.setEndDate("-");
						excelReportDTOList.add(excelReportDTO);
						
						if(ratingsFlag)
						{
						excelReportDTO.setPerformanceCode("-");
						excelReportDTO.setLeadershipCode("-");
						excelReportDTO.setPotentialCode("-");
						}
					
					}
		
				}
			});
		if (logger.isDebugEnabled()) {
			logger.debug("Finish generateExcelWorkHistoryInternalReport in Reporting DAO");
		}
		}
		catch(QueryException queryException){
				/**
						 * Insert message into APPL_LOG table 
						 */
			    logger.error(HrReviewApplLogMessage.create(HrReviewApplLogMessage.DATABASE_ERROR,
				DAOConstants.ERROR_GET_QUERY_EXCEL_WORKHISTORY_INERNALREPORT_DESC,queryException));
				throw new HrReviewException(Constants.BAD_REQUEST,
				DAOConstants.ERROR_GET_QUERY_EXCEL_WORKHISTORY_INERNALREPORT_DESC,queryException);
		}
		return excelReportDTOList;
	}
	
	public static List<ExcelReportDTO> generateExcelEducationReport(String userID,final boolean ratingsFlag,List<String> zeroEmployeeIdList) throws QueryException, HrReviewException 
	{
		final List<ExcelReportDTO> excelReportDTOList= new ArrayList<ExcelReportDTO>();

		if (logger.isDebugEnabled()) {
			logger.debug("Start generateExcelEducationReport in Reporting DAO");
		}
		MapStream inputList= new MapStream("readSuccessionPlanUserEmployeeWorkAndSystemDivisionDetails");
		inputList.put("userId",userID);
		inputList.put("successionPlanSecurityGroupId",300);
		inputList.put("humanResourcesStoreEffectiveBeginDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.put("humanResourcesStoreEffectiveEndDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("humanResourcesStoreEffectiveBeginDateLessThanEqualTo", true);
		inputList.addQualifier("humanResourcesStoreEffectiveEndDateGreaterThanEqualTo", true);
		inputList.put("divisionEffectiveEndDate",divisionEffectiveEndDate);
		inputList.put("languageCode", LANGUAGE_CD);
		inputList.put("departmentLanguageCode", LANGUAGE_CD);
		inputList.put("degreeLanguageCode",LANGUAGE_CD);
		inputList.put("effectiveBeginDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.put("effectiveEndDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("effectiveBeginDateLessThanEqualTo", true);
		inputList.addQualifier("effectiveEndDateGreaterThanEqualTo", true);
		inputList.put("recordStatusIndicator", "A");
		inputList.put("departmentEffectiveBeginDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("departmentEffectiveBeginDateLessThanEqualTo", true);
		inputList.put("jobEffectiveBeginDate",new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("jobEffectiveBeginDateLessThanEqualTo",true);
		inputList.put("zeroEmployeeIdList",zeroEmployeeIdList); 
		
		try
	      {
					
			BasicDAO.getResult(HRREVIEW_CONTRACT_NAME, HRREVIEW_BU_ID, HRREVIEW_VERSION,inputList,  new ResultsReader() {

									
					public void readResults(Results results, Query query, Inputs inputs) throws QueryException
					{
						ExcelReportDTO excelReportDTO= null;
						while(results.next())
						{
							excelReportDTO=new ExcelReportDTO();
							if(!(AppUtil.isEmptyString(results.getString("zeroEmployeeId"))))
								excelReportDTO.setAssociateID(results.getString("zeroEmployeeId"));
								else
								excelReportDTO.setAssociateID("-");	
								if(!(AppUtil.isEmptyString(results.getString("employeeFirstName"))))
								excelReportDTO.setFirstName(results.getString("employeeFirstName"));
								else
								excelReportDTO.setFirstName("-");
								if(!(AppUtil.isEmptyString(results.getString("employeeLastName"))))
								excelReportDTO.setLastName(results.getString("employeeLastName"));
								else
								excelReportDTO.setLastName("-");
								if(!(AppUtil.isEmptyString(results.getString("jobTitleDescription"))))
								excelReportDTO.setJobTitle(results.getString("jobTitleDescription"));
								else
								excelReportDTO.setJobTitle("-");
								if(!(AppUtil.isEmptyString(results.getString("currentDivisionName"))))
									excelReportDTO.setDivisionName(results.getString("currentDivisionName"));
									else
									excelReportDTO.setDivisionName("-");
								if(!(AppUtil.isEmptyString(results.getString("humanResourcesSystemDepartmentName"))))
								excelReportDTO.setDepartmentName(results.getString("humanResourcesSystemDepartmentName"));
								else
								excelReportDTO.setDepartmentName("-");
								if(!(AppUtil.isEmptyString(results.getString("humanResourcesSystemStoreName"))))
								excelReportDTO.setLocation(results.getString("humanResourcesSystemStoreName"));
								else
								excelReportDTO.setLocation("-");
								excelReportDTO.setEffectiveBgnDate(results.getString("effectiveBeginDate"));
								excelReportDTO.setDivisionCode(results.getString("humanResourcesSystemDivisionCode"));
								if(!(AppUtil.isEmptyString(results.getString("humanResourcesSystemStoreNumber"))))
								excelReportDTO.setStoreNumber(results.getString("humanResourcesSystemStoreNumber"));
								else
								excelReportDTO.setStoreNumber("-");
							
							if(!(AppUtil.isEmptyString(results.getString("schoolGraduateYear"))))	
						    excelReportDTO.setGradeYear(results.getString("schoolGraduateYear"));
							else
							excelReportDTO.setGradeYear("-");
							
							if(!(AppUtil.isEmptyString(results.getString("schoolName"))))
							excelReportDTO.setSchoolName(results.getString("schoolName"));
							else
							excelReportDTO.setSchoolName("-");
							if(!(AppUtil.isEmptyString(results.getString("degree"))))
							excelReportDTO.setDegree(results.getString("degree"));
							else
							excelReportDTO.setDegree("-");
							
							if(ratingsFlag)
							{
								excelReportDTO.setPerformanceCode(results.getString("performanceReviewRatingCode"));
								excelReportDTO.setLeadershipCode(results.getString("leadershipRatingCode"));
								excelReportDTO.setPotentialCode(results.getString("potentialRatingCode"));
							}
							excelReportDTOList.add(excelReportDTO);
							}
							
				}
			});
	      }
		
		catch(QueryException queryException){
				/**
						 * Insert message into APPL_LOG table 
						 */
			logger.error(HrReviewApplLogMessage.create(HrReviewApplLogMessage.DATABASE_ERROR,
		     DAOConstants.ERROR_GET_QUERY_EXCEL_EDUCATION_REPORT_DESC,queryException));
			throw new HrReviewException(Constants.BAD_REQUEST,
			DAOConstants.ERROR_GET_QUERY_EXCEL_EDUCATION_REPORT_DESC,queryException);
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("Finish generateExcelEducationReport in Reporting DAO");
		}
		return excelReportDTOList;
	}
	
	public static List<ExcelReportDTO> generateExcelKeyCourseReport(String userID,final boolean ratingsFlag,List<String> zeroEmployeeIdList) throws QueryException, HrReviewException 
	{
		final List<ExcelReportDTO> excelReportDTOList= new ArrayList<ExcelReportDTO>();
		if (logger.isDebugEnabled()) {
			logger.debug("Start generateExcelKeyCourseReport in Reporting DAO");
		}
		MapStream inputList= new MapStream("readSuccessionPlanUserEmployeeWorkAndCourseDetails");	
		inputList.put("userId",userID);
		inputList.put("successionPlanSecurityGroupId",300);
		inputList.put("humanResourcesStoreEffectiveBeginDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.put("humanResourcesStoreEffectiveEndDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("humanResourcesStoreEffectiveBeginDateLessThanEqualTo", true);
		inputList.addQualifier("humanResourcesStoreEffectiveEndDateGreaterThanEqualTo", true);
		inputList.put("divisionEffectiveEndDate",divisionEffectiveEndDate);
		inputList.put("languageCode", LANGUAGE_CD);
		inputList.put("departmentLanguageCode", LANGUAGE_CD);
		inputList.put("effectiveBeginDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.put("effectiveEndDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("effectiveBeginDateLessThanEqualTo", true);
		inputList.addQualifier("effectiveEndDateGreaterThanEqualTo", true);
		inputList.put("recordStatusIndicator", "A");
		inputList.put("departmentEffectiveBeginDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("departmentEffectiveBeginDateLessThanEqualTo", true);
		inputList.put("jobEffectiveBeginDate",new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("jobEffectiveBeginDateLessThanEqualTo",true);
		inputList.put("zeroEmployeeIdList",zeroEmployeeIdList); 
		
		try
	      {
					
			BasicDAO.getResult(HRREVIEW_CONTRACT_NAME, HRREVIEW_BU_ID, HRREVIEW_VERSION,inputList,  new ResultsReader() {

				public void readResults(Results results, Query query, Inputs inputs) throws QueryException
				{
					ExcelReportDTO excelReportDTO= null;
					while(results.next())
					{
						excelReportDTO=new ExcelReportDTO();
						if(!(AppUtil.isEmptyString(results.getString("zeroEmployeeId"))))
							excelReportDTO.setAssociateID(results.getString("zeroEmployeeId"));
							else
							excelReportDTO.setAssociateID("-");	
							if(!(AppUtil.isEmptyString(results.getString("employeeFirstName"))))
							excelReportDTO.setFirstName(results.getString("employeeFirstName"));
							else
							excelReportDTO.setFirstName("-");
							if(!(AppUtil.isEmptyString(results.getString("employeeLastName"))))
							excelReportDTO.setLastName(results.getString("employeeLastName"));
							else
							excelReportDTO.setLastName("-");
							if(!(AppUtil.isEmptyString(results.getString("jobTitleDescription"))))
							excelReportDTO.setJobTitle(results.getString("jobTitleDescription"));
							else
							excelReportDTO.setJobTitle("-");
							if(!(AppUtil.isEmptyString(results.getString("currentDivisionName"))))
								excelReportDTO.setDivisionName(results.getString("currentDivisionName"));
								else
								excelReportDTO.setDivisionName("-");
							if(!(AppUtil.isEmptyString(results.getString("humanResourcesSystemDepartmentName"))))
							excelReportDTO.setDepartmentName(results.getString("humanResourcesSystemDepartmentName"));
							else
							excelReportDTO.setDepartmentName("-");
							if(!(AppUtil.isEmptyString(results.getString("humanResourcesSystemStoreName"))))
							excelReportDTO.setLocation(results.getString("humanResourcesSystemStoreName"));
							else
							excelReportDTO.setLocation("-");
							excelReportDTO.setEffectiveBgnDate(results.getString("effectiveBeginDate"));
							excelReportDTO.setDivisionCode(results.getString("humanResourcesSystemDivisionCode"));
							if(!(AppUtil.isEmptyString(results.getString("humanResourcesSystemStoreNumber"))))
							excelReportDTO.setStoreNumber(results.getString("humanResourcesSystemStoreNumber"));
							else
							excelReportDTO.setStoreNumber("-");
						if(!(AppUtil.isEmptyString(results.getString("successionPlanCourseDescription"))))	
						excelReportDTO.setCourse(results.getString("successionPlanCourseDescription"));
						else
						excelReportDTO.setCourse("-");
						if(!(AppUtil.isEmptyString(results.getString("organization"))))
						excelReportDTO.setOrganization(results.getString("organization"));
						else
						excelReportDTO.setOrganization("-");
						if(!(AppUtil.isEmptyString(results.getString("completionDate"))))
						excelReportDTO.setCourseCompletionDate(results.getString("completionDate"));
						else
						excelReportDTO.setCourseCompletionDate("-");
						
						if(ratingsFlag)
						{
							excelReportDTO.setPerformanceCode(results.getString("performanceReviewRatingCode"));
							excelReportDTO.setLeadershipCode(results.getString("leadershipRatingCode"));
							excelReportDTO.setPotentialCode(results.getString("potentialRatingCode"));
						}
						excelReportDTOList.add(excelReportDTO);
					}
				}		
			});
		
		}
		catch(QueryException queryException){
				/**
						 * Insert message into APPL_LOG table 
						 */
			logger.error(HrReviewApplLogMessage.create(HrReviewApplLogMessage.DATABASE_ERROR,
		     DAOConstants.ERROR_GET_QUERY_EXCEL_KEYCOURSE_REPORT_DESC,queryException));
			 throw new HrReviewException(Constants.BAD_REQUEST,
			 DAOConstants.ERROR_GET_QUERY_EXCEL_KEYCOURSE_REPORT_DESC,queryException);
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("Finish generateExcelKeyCourseReport in Reporting DAO");
		}
		return excelReportDTOList;
	}
	
	public static List<ExcelReportDTO> generateCareerPlanningReport(String userID,final boolean ratingsFlag,List<String> zeroEmployeeIdList) throws QueryException, HrReviewException 
	{
		final List<ExcelReportDTO> excelReportDTOList= new ArrayList<ExcelReportDTO>();
		if (logger.isDebugEnabled()) {
			logger.debug("Start generateCareerPlanningReport in Reporting DAO");
		}

		MapStream inputList= new MapStream("readSuccessionPlanningUserEmploymentWorkHumanResourceSystemActivityDetails");
		inputList.put("userId",userID);
		inputList.put("successionPlanSecurityGroupId",300);
		inputList.put("humanResourcesStoreEffectiveBeginDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.put("humanResourcesStoreEffectiveEndDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("humanResourcesStoreEffectiveBeginDateLessThanEqualTo", true);
		inputList.addQualifier("humanResourcesStoreEffectiveEndDateGreaterThanEqualTo", true);
		inputList.put("divisionEffectiveEndDate",divisionEffectiveEndDate);
		inputList.put("languageCode", LANGUAGE_CD);
	    inputList.put("effectiveBeginDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.put("effectiveEndDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("effectiveBeginDateLessThanEqualTo", true);
		inputList.addQualifier("effectiveEndDateGreaterThanEqualTo", true);
		inputList.put("recordStatusIndicator", "A");
		inputList.put("departmentEffectiveBeginDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.put("jobEffectiveBeginDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.put("jobPlanningEffectiveBeginDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("departmentEffectiveBeginDateLessThanEqualTo", true);
		inputList.addQualifier("jobEffectiveBeginDateLessThanEqualTo", true);
		inputList.addQualifier("jobPlanningEffectiveBeginDateLessThanEqualTo", true);
		inputList.put("zeroEmployeeIdList",zeroEmployeeIdList); 
		
		try
	      {
					
			BasicDAO.getResult(HRREVIEW_CONTRACT_NAME, HRREVIEW_BU_ID, HRREVIEW_VERSION,inputList,  new ResultsReader() {

				public void readResults(Results results, Query query, Inputs inputs) throws QueryException
				{
					ExcelReportDTO excelReportDTO= null;
					while(results.next())
					{
						excelReportDTO=new ExcelReportDTO();
						if(!(AppUtil.isEmptyString(results.getString("zeroEmployeeId"))))
							excelReportDTO.setAssociateID(results.getString("zeroEmployeeId"));
							else
							excelReportDTO.setAssociateID("-");	
							if(!(AppUtil.isEmptyString(results.getString("employeeFirstName"))))
							excelReportDTO.setFirstName(results.getString("employeeFirstName"));
							else
							excelReportDTO.setFirstName("-");
							if(!(AppUtil.isEmptyString(results.getString("employeeLastName"))))
							excelReportDTO.setLastName(results.getString("employeeLastName"));
							else
							excelReportDTO.setLastName("-");
							if(!(AppUtil.isEmptyString(results.getString("jobTitleDescription"))))
							excelReportDTO.setJobTitle(results.getString("jobTitleDescription"));
							else
							excelReportDTO.setJobTitle("-");
							if(!(AppUtil.isEmptyString(results.getString("currentDivisionName"))))
								excelReportDTO.setDivisionName(results.getString("currentDivisionName"));
								else
								excelReportDTO.setDivisionName("-");
							if(!(AppUtil.isEmptyString(results.getString("humanResourcesSystemDepartmentName"))))
							excelReportDTO.setDepartmentName(results.getString("humanResourcesSystemDepartmentName"));
							else
							excelReportDTO.setDepartmentName("-");
							if(!(AppUtil.isEmptyString(results.getString("humanResourcesSystemStoreName"))))
							excelReportDTO.setLocation(results.getString("humanResourcesSystemStoreName"));
							else
							excelReportDTO.setLocation("-");
							excelReportDTO.setEffectiveBgnDate(results.getString("effectiveBeginDate"));
							if(!(AppUtil.isEmptyString(results.getString("humanResourcesSystemStoreNumber"))))
							excelReportDTO.setStoreNumber(results.getString("humanResourcesSystemStoreNumber"));
							else
							excelReportDTO.setStoreNumber("-");
						if(!(AppUtil.isEmptyString(results.getString("successionPlanJobTitleGroupDescription"))))	
						excelReportDTO.setJtGrpDesc(results.getString("successionPlanJobTitleGroupDescription"));
						else
						excelReportDTO.setJtGrpDesc("-");
						if(!(AppUtil.isEmptyString(results.getString("title"))))
						excelReportDTO.setTitle(results.getString("title"));
						else
						excelReportDTO.setTitle("-");
						if(!(AppUtil.isEmptyString(results.getString("successionPlanGoalStatusDescription"))))
						excelReportDTO.setPotentialTiming(results.getString("successionPlanGoalStatusDescription"));
						else
						excelReportDTO.setPotentialTiming("-");
						if(ratingsFlag)
						{
							excelReportDTO.setPerformanceCode(results.getString("performanceReviewRatingCode"));
							excelReportDTO.setLeadershipCode(results.getString("leadershipRatingCode"));
							excelReportDTO.setPotentialCode(results.getString("potentialRatingCode"));
						}
						excelReportDTOList.add(excelReportDTO);
					}
				}		
			});
		
		}
		catch(QueryException queryException){
				/**
						 * Insert message into APPL_LOG table 
						 */
			logger.error(HrReviewApplLogMessage.create(HrReviewApplLogMessage.DATABASE_ERROR,
		    DAOConstants.ERROR_GET_QUERY_EXCEL_CAREERPLANNING_REPORT_DESC,queryException));
		     throw new HrReviewException(Constants.BAD_REQUEST,
			DAOConstants.ERROR_GET_QUERY_EXCEL_CAREERPLANNING_REPORT_DESC,queryException);
			}
		
		if (logger.isDebugEnabled()) {
			logger.debug("Finish generateCareerPlanningReport in Reporting DAO");
		}
		
		return excelReportDTOList;
	}
	
	public static List<ExcelReportDTO> generateExcelMobilityReport(String userID,final boolean ratingsFlag,List<String> zeroEmployeeIdList) throws QueryException, HrReviewException 
	{
		final List<ExcelReportDTO> excelReportDTOList= new ArrayList<ExcelReportDTO>();
		if (logger.isDebugEnabled()) {
			logger.debug("Start generateExcelMobilityReport in Reporting DAO");
		}

		MapStream inputList= new MapStream("readSuccessionPlanningUserEmploymentWorkHumanResourceExternalReferenceDetails");
		inputList.put("userId",userID);
		inputList.put("successionPlanSecurityGroupId",300);
		inputList.put("humanResourcesStoreEffectiveBeginDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.put("humanResourcesStoreEffectiveEndDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("humanResourcesStoreEffectiveBeginDateLessThanEqualTo", true);
		inputList.addQualifier("humanResourcesStoreEffectiveEndDateGreaterThanEqualTo", true);
		inputList.put("effectiveBeginDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.put("effectiveEndDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("effectiveBeginDateLessThanEqualTo", true);
		inputList.addQualifier("effectiveEndDateGreaterThanEqualTo", true);
		inputList.put("divisionEffectiveEndDate",divisionEffectiveEndDate);
		inputList.put("languageCode", LANGUAGE_CD);
		inputList.put("recordStatusIndicator", "A");
		inputList.put("departmentEffectiveBeginDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.put("jobEffectiveBeginDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("departmentEffectiveBeginDateLessThanEqualTo",true);
		inputList.addQualifier("jobEffectiveBeginDateLessThanEqualTo",true);
		inputList.put("zeroEmployeeIdList",zeroEmployeeIdList); 
				
		try
	      {
					
			BasicDAO.getResult(HRREVIEW_CONTRACT_NAME, HRREVIEW_BU_ID, HRREVIEW_VERSION,inputList,  new ResultsReader() {

				public void readResults(Results results, Query query, Inputs inputs) throws QueryException
				{
					ExcelReportDTO excelReportDTO= null;
					while(results.next())
					{
						excelReportDTO=new ExcelReportDTO();
						if(!(AppUtil.isEmptyString(results.getString("zeroEmployeeId"))))
							excelReportDTO.setAssociateID(results.getString("zeroEmployeeId"));
							else
							excelReportDTO.setAssociateID("-");	
							if(!(AppUtil.isEmptyString(results.getString("employeeFirstName"))))
							excelReportDTO.setFirstName(results.getString("employeeFirstName"));
							else
							excelReportDTO.setFirstName("-");
							if(!(AppUtil.isEmptyString(results.getString("employeeLastName"))))
							excelReportDTO.setLastName(results.getString("employeeLastName"));
							else
							excelReportDTO.setLastName("-");
							if(!(AppUtil.isEmptyString(results.getString("jobTitleDescription"))))
							excelReportDTO.setJobTitle(results.getString("jobTitleDescription"));
							else
							excelReportDTO.setJobTitle("-");
							
							if(!(AppUtil.isEmptyString(results.getString("currentDivisionName"))))
								excelReportDTO.setDivisionName(results.getString("currentDivisionName"));
								else
								excelReportDTO.setDivisionName("-");
							if(!(AppUtil.isEmptyString(results.getString("humanResourcesSystemDepartmentName"))))
							excelReportDTO.setDepartmentName(results.getString("humanResourcesSystemDepartmentName"));
							else
							excelReportDTO.setDepartmentName("-");
							if(!(AppUtil.isEmptyString(results.getString("humanResourcesSystemStoreName"))))
							excelReportDTO.setLocation(results.getString("humanResourcesSystemStoreName"));
							else
							excelReportDTO.setLocation("-");
							excelReportDTO.setEffectiveBgnDate(results.getString("effectiveBeginDate"));
							
							if(!(AppUtil.isEmptyString(results.getString("humanResourcesSystemStoreNumber"))))
							excelReportDTO.setStoreNumber(results.getString("humanResourcesSystemStoreNumber"));
							else
							excelReportDTO.setStoreNumber("-");
						if(!(AppUtil.isEmptyString(results.getString("mobilityLocation"))))
						excelReportDTO.setMobilityLocation(results.getString("mobilityLocation"));
						else
						excelReportDTO.setMobilityLocation("-");
						if(!(AppUtil.isEmptyString(results.getString("successionPlanRelocationTypeDescription"))))
						excelReportDTO.setRelocationTypeDesc(results.getString("successionPlanRelocationTypeDescription"));
						else
						excelReportDTO.setRelocationTypeDesc("-");
						if(ratingsFlag)
						{
							excelReportDTO.setPerformanceCode(results.getString("performanceReviewRatingCode"));
							excelReportDTO.setLeadershipCode(results.getString("leadershipRatingCode"));
							excelReportDTO.setPotentialCode(results.getString("potentialRatingCode"));
						}
						excelReportDTOList.add(excelReportDTO);
						
					}
				}		
			});
		
		}
		catch(QueryException queryException){
				/**
						 * Insert message into APPL_LOG table 
						 */
			logger.error(HrReviewApplLogMessage.create(HrReviewApplLogMessage.DATABASE_ERROR,
			DAOConstants.ERROR_GET_QUERY_EXCEL_MOBILITY_REPORT_DESC,queryException));
			throw new HrReviewException(Constants.BAD_REQUEST,
		    DAOConstants.ERROR_GET_QUERY_EXCEL_MOBILITY_REPORT_DESC,queryException);
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("Finish generateExcelMobilityReport in Reporting DAO");
		}
		return excelReportDTOList;
	}
	
	public static List<ExcelReportDTO> generateExcelLanguageReport(String userID,final boolean ratingsFlag,List<String> zeroEmployeeIdList) throws QueryException, HrReviewException 
	{
		final List<ExcelReportDTO> excelReportDTOList= new ArrayList<ExcelReportDTO>();
		if (logger.isDebugEnabled()) {
			logger.debug("Start generateExcelLanguageReport in Reporting DAO");
		}
        MapStream inputList= new MapStream("readSuccessionPlanningUserEmploymentWorkHumanResourceExternalReferenceActivityDetails");
		inputList.put("userId",userID);
		inputList.put("successionPlanSecurityGroupId",300);
		inputList.put("humanResourcesStoreEffectiveBeginDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.put("humanResourcesStoreEffectiveEndDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("humanResourcesStoreEffectiveBeginDateLessThanEqualTo", true);
		inputList.addQualifier("humanResourcesStoreEffectiveEndDateGreaterThanEqualTo", true);
		inputList.put("divisionEffectiveEndDate",divisionEffectiveEndDate);
		inputList.put("languageCode", LANGUAGE_CD);
		inputList.put("effectiveBeginDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.put("effectiveEndDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("effectiveBeginDateLessThanEqualTo", true);
		inputList.addQualifier("effectiveEndDateGreaterThanEqualTo", true);
		inputList.put("recordStatusIndicator", "A");
		inputList.put("departmentEffectiveBeginDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.put("jobEffectiveBeginDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("departmentEffectiveBeginDateLessThanEqualTo",true);
		inputList.addQualifier("jobEffectiveBeginDateLessThanEqualTo",true);
		inputList.put("zeroEmployeeIdList",zeroEmployeeIdList); 
		
	  try
	      {
					
			BasicDAO.getResult(HRREVIEW_CONTRACT_NAME, HRREVIEW_BU_ID, HRREVIEW_VERSION,inputList,  new ResultsReader() {

				public void readResults(Results results, Query query, Inputs inputs) throws QueryException
				{
					ExcelReportDTO excelReportDTO= null;
					while(results.next())
					{
						excelReportDTO=new ExcelReportDTO();
						if(!(AppUtil.isEmptyString(results.getString("zeroEmployeeId"))))
							excelReportDTO.setAssociateID(results.getString("zeroEmployeeId"));
							else
							excelReportDTO.setAssociateID("-");	
							if(!(AppUtil.isEmptyString(results.getString("employeeFirstName"))))
							excelReportDTO.setFirstName(results.getString("employeeFirstName"));
							else
							excelReportDTO.setFirstName("-");
							if(!(AppUtil.isEmptyString(results.getString("employeeLastName"))))
							excelReportDTO.setLastName(results.getString("employeeLastName"));
							else
							excelReportDTO.setLastName("-");
							if(!(AppUtil.isEmptyString(results.getString("jobTitleDescription"))))
							excelReportDTO.setJobTitle(results.getString("jobTitleDescription"));
							else
							excelReportDTO.setJobTitle("-");
							
							if(!(AppUtil.isEmptyString(results.getString("currentDivisionName"))))
								excelReportDTO.setDivisionName(results.getString("currentDivisionName"));
								else
								excelReportDTO.setDivisionName("-");
							if(!(AppUtil.isEmptyString(results.getString("humanResourcesSystemDepartmentName"))))
							excelReportDTO.setDepartmentName(results.getString("humanResourcesSystemDepartmentName"));
							else
							excelReportDTO.setDepartmentName("-");
							if(!(AppUtil.isEmptyString(results.getString("humanResourcesSystemStoreName"))))
							excelReportDTO.setLocation(results.getString("humanResourcesSystemStoreName"));
							else
							excelReportDTO.setLocation("-");
							excelReportDTO.setEffectiveBgnDate(results.getString("effectiveBeginDate"));
							//excelReportDTO.setDivisionCode(results.getString("humanResourcesSystemDivisionCode"));
							if(!(AppUtil.isEmptyString(results.getString("humanResourcesSystemStoreNumber"))))
							excelReportDTO.setStoreNumber(results.getString("humanResourcesSystemStoreNumber"));
							else
							excelReportDTO.setStoreNumber("-");
							if(!(AppUtil.isEmptyString(results.getString("language"))))
						    excelReportDTO.setLanguage(results.getString("language"));
							else
							excelReportDTO.setLanguage("-");
							
						if(ratingsFlag)
						{
							excelReportDTO.setPerformanceCode(results.getString("performanceReviewRatingCode"));
							excelReportDTO.setLeadershipCode(results.getString("leadershipRatingCode"));
							excelReportDTO.setPotentialCode(results.getString("potentialRatingCode"));
						}
						excelReportDTOList.add(excelReportDTO);
						
					}
				}		
			});
		
		}
		
		catch(QueryException queryException){
				/**
						 * Insert message into APPL_LOG table 
						 */
			logger.error(HrReviewApplLogMessage.create(HrReviewApplLogMessage.DATABASE_ERROR,
			DAOConstants.ERROR_GET_QUERY_EXCEL_LANGUAGE_REPORT_DESC,queryException));
			throw new HrReviewException(Constants.BAD_REQUEST,
		    DAOConstants.ERROR_GET_QUERY_EXCEL_LANGUAGE_REPORT_DESC,queryException);
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("Finish generateExcelLanguageReport in Reporting DAO");
		}
		return excelReportDTOList;
	}
	
	public static List<ExcelReportDTO> generateExcelDepartmentExperienceReport(String userID,final boolean ratingsFlag,List<String> zeroEmployeeIdList) throws QueryException, HrReviewException 
	{
		final List<ExcelReportDTO> excelReportDTOList= new ArrayList<ExcelReportDTO>();
		if (logger.isDebugEnabled()) {
			logger.debug("Start generateExcelDepartmentExperienceReport in Reporting DAO");
		}

		MapStream inputList= new MapStream("readSuccessionPlanningUserEmploymentWorkHumanResourceExternalReferenceStoreDetails");
		inputList.put("userId",userID);
		inputList.put("successionPlanSecurityGroupId",300);
		inputList.put("humanResourcesStoreEffectiveBeginDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.put("humanResourcesStoreEffectiveEndDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("humanResourcesStoreEffectiveBeginDateLessThanEqualTo", true);
		inputList.addQualifier("humanResourcesStoreEffectiveEndDateGreaterThanEqualTo", true);
		inputList.put("divisionEffectiveEndDate",divisionEffectiveEndDate);
		inputList.put("languageCode", LANGUAGE_CD);
		inputList.put("effectiveBeginDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.put("effectiveEndDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("effectiveBeginDateLessThanEqualTo", true);
		inputList.addQualifier("effectiveEndDateGreaterThanEqualTo", true);
		inputList.put("recordStatusIndicator", "A");
		inputList.put("departmentEffectiveBeginDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.put("jobEffectiveBeginDate", new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("departmentEffectiveBeginDateLessThanEqualTo",true);
		inputList.put("jobEffectiveBeginDateLessThanEqualTo",true);
		inputList.put("skillDescriptionEffectiveBeginDate",new Date(Calendar.getInstance().getTimeInMillis()));
		inputList.addQualifier("skillDescriptionEffectiveBeginDateLessThanEqualTo",true);
		inputList.put("zeroEmployeeIdList",zeroEmployeeIdList); 
		inputList.putAllowNull("skillsDescriptionEffectiveBeginDate",null);
		
		try
	      {
					
			BasicDAO.getResult(HRREVIEW_CONTRACT_NAME, HRREVIEW_BU_ID, HRREVIEW_VERSION,inputList,  new ResultsReader() {

				public void readResults(Results results, Query query, Inputs inputs) throws QueryException
				{
					ExcelReportDTO excelReportDTO= null;
					while(results.next())
					{
						excelReportDTO=new ExcelReportDTO();
						if(!(AppUtil.isEmptyString(results.getString("zeroEmployeeId"))))
							excelReportDTO.setAssociateID(results.getString("zeroEmployeeId"));
							else
							excelReportDTO.setAssociateID("-");	
							if(!(AppUtil.isEmptyString(results.getString("employeeFirstName"))))
							excelReportDTO.setFirstName(results.getString("employeeFirstName"));
							else
							excelReportDTO.setFirstName("-");
							if(!(AppUtil.isEmptyString(results.getString("employeeLastName"))))
							excelReportDTO.setLastName(results.getString("employeeLastName"));
							else
							excelReportDTO.setLastName("-");
							if(!(AppUtil.isEmptyString(results.getString("jobTitleDescription"))))
							excelReportDTO.setJobTitle(results.getString("jobTitleDescription"));
							else
							excelReportDTO.setJobTitle("-");
							if(!(AppUtil.isEmptyString(results.getString("currentDivisionName"))))
							excelReportDTO.setDivisionName(results.getString("currentDivisionName"));
							else
							excelReportDTO.setDivisionName("-");
							if(!(AppUtil.isEmptyString(results.getString("humanResourcesSystemDepartmentName"))))
							excelReportDTO.setDepartmentName(results.getString("humanResourcesSystemDepartmentName"));
							else
							excelReportDTO.setDepartmentName("-");
							if(!(AppUtil.isEmptyString(results.getString("humanResourcesSystemStoreName"))))
							excelReportDTO.setLocation(results.getString("humanResourcesSystemStoreName"));
							else
							excelReportDTO.setLocation("-");
							excelReportDTO.setEffectiveBgnDate(results.getString("effectiveBeginDate"));
							//excelReportDTO.setDivisionCode(results.getString("humanResourcesSystemDivisionCode"));
							if(!(AppUtil.isEmptyString(results.getString("humanResourcesSystemStoreNumber"))))
							excelReportDTO.setStoreNumber(results.getString("humanResourcesSystemStoreNumber"));
							else
							excelReportDTO.setStoreNumber("-");
						
							if(!(AppUtil.isEmptyString(results.getString("departmentExpense"))))
						excelReportDTO.setDepartmentExperience(results.getString("departmentExpense"));
							else
								excelReportDTO.setDepartmentExperience("-");
							if(!(AppUtil.isEmptyString(results.getString("experienceYears"))))
						excelReportDTO.setExperienceYrs(results.getString("experienceYears"));
							else
								excelReportDTO.setExperienceYrs("-");
						if(ratingsFlag)
						{
							excelReportDTO.setPerformanceCode(results.getString("performanceReviewRatingCode"));
							excelReportDTO.setLeadershipCode(results.getString("leadershipRatingCode"));
							excelReportDTO.setPotentialCode(results.getString("potentialRatingCode"));
						}
						excelReportDTOList.add(excelReportDTO);
						
					}
				}		
			});
		
		}
		catch(QueryException queryException){
				/**
						 * Insert message into APPL_LOG table 
						 */
			logger.error(HrReviewApplLogMessage.create(HrReviewApplLogMessage.DATABASE_ERROR,
			DAOConstants.ERROR_GET_QUERY_EXCEL_DEPARTMENTEXP_REPORT_DESC,queryException));
			throw new HrReviewException(Constants.BAD_REQUEST,
			DAOConstants.ERROR_GET_QUERY_EXCEL_DEPARTMENTEXP_REPORT_DESC,queryException);
		}
		return excelReportDTOList;
	}
	
		

}

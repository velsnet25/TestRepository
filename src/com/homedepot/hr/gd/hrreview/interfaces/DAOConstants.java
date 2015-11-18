package com.homedepot.hr.gd.hrreview.interfaces;
/* 
 * This program is proprietary to The Home Depot and is not to be
 * reproduced, used, or disclosed without permission of:
 *    
 *  The Home Depot
 *  2455 Paces Ferry Road, N.W.
 *  Atlanta, GA 30339-4053
 *
 * File Name: DAOConstants.java
 * Application: RetailStaffing
 */
import com.homedepot.ta.aa.dao.Contract;
import com.homedepot.ta.aa.dao.basic.BasicContract;

/**
 * This interface contains SHARED constants used by different DAO components of the application 
 */
public interface DAOConstants
{
	public static final String lang_code = "en_US";
	
	// DAO constants for Contract WorkforceRecruitment
	public static final String WORKFORCERECRUITMENT_CONTRACT_NAME = "WorkforceRecruitment";
	public static final int WORKFORCERECRUITMENT_BUID = 55;
	public static final int WORKFORCERECRUITMENT_VERSION = 1;	
	public static final Contract WORKFORCERECRUITMENT_DAO_CONTRACT = new BasicContract(WORKFORCERECRUITMENT_CONTRACT_NAME, WORKFORCERECRUITMENT_BUID, WORKFORCERECRUITMENT_VERSION);	
	
	// DAOconstants for Contract WorkforceEmploymentQualification
	public static final String WORKFORCE_EMPLOYMENT_QUALIFICATION_CONTRACT_NAME = "WorkforceEmploymentQualifications";
	public static final int WORKFORCE_EMPLOYMENT_QUALIFICATION_BUID = 55;
	public static final int WORKFORCE_EMPLOYMENT_QUALIFICATION_VERSION = 1;	
	public static final Contract WORKFORCE_EMPLOYMENT_QUALIFICATION_DAO_CONTRACT = new BasicContract(WORKFORCE_EMPLOYMENT_QUALIFICATION_CONTRACT_NAME, WORKFORCE_EMPLOYMENT_QUALIFICATION_BUID, WORKFORCE_EMPLOYMENT_QUALIFICATION_VERSION);	
	
	
 //DAO Constants for Contract BusinessOrganizationThdOrganizationStructure
	public static final String BUSINESSORGANIZATIONTHDORGANIZATIONSTRUCTURE_CONTRACT_NAME = "BusinessOrganizationThdOrganization";
	public static final int BUSINESSORGANIZATIONTHDORGANIZATIONSTRUCTURE_BUID = 55;
	public static final int BUSINESSORGANIZATIONTHDORGANIZATIONSTRUCTURE_VERSION = 2;	
	public static final Contract BUSINESSORGANIZATIONTHDORGANIZATIONSTRUCTURE_DAO_CONTRACT = new BasicContract(BUSINESSORGANIZATIONTHDORGANIZATIONSTRUCTURE_CONTRACT_NAME, BUSINESSORGANIZATIONTHDORGANIZATIONSTRUCTURE_BUID, BUSINESSORGANIZATIONTHDORGANIZATIONSTRUCTURE_VERSION);
	
	// DAO Contract Constants for HRStaffing
				//==========
				public static final String HRSTAFFING_CONTRACT_NAME = "HrHrStaffing";
				public static final int HRSTAFFING_BU_ID = 10038;
				public static final int HRSTAFFING_VERSION = 1;	
				public static final Contract HRSTAFFING_DAO_CONTRACT = new BasicContract(HRSTAFFING_CONTRACT_NAME, HRSTAFFING_BU_ID, HRSTAFFING_VERSION);
				
				// DAO Contract Constants for HRReview
				//==========
				public static final String HRREVIEW_CONTRACT_NAME = "WorkforceSuccessionPlanning";
				public static final int HRREVIEW_BU_ID = 55;
				public static final int HRREVIEW_VERSION = 1;	
				public static final Contract HRREVIEW_DAO_CONTRACT = new BasicContract(HRREVIEW_CONTRACT_NAME, HRREVIEW_BU_ID, HRREVIEW_VERSION);
	
	//==========
	// DAO Selector Constants
	//==========
	public static final String READ_STORE_DETAILS = "readStoreDetails";	
	
	
	//==========
		// DAO Input/Output constants
		//==========
		public static final String HR_SYS_STR_NBR = "humanResourcesSystemStoreNumber";
		public static final String LOC_TYP_CODE_LIST = "locationTypeCodeList";
		public static final String HR_SYS_STR_NM = "humanResourcesSystemStoreName";
		public static final String ADDR_LINE1_TXT = "addressLineOneText";
		public static final String ADDR_LINE2_TXT = "addressLineTwoText";
		public static final String CITY_NM = "cityName";
		public static final String LONG_ZIP_CD = "longZipCodeCode";
		public static final String ST_CD = "stateCode";
		public static final String CNTRY_CD = "countryCode";
		public static final String PHN_NBR = "phoneNumber";
		public static final String HR_SYS_RGN_CD = "humanResourcesSystemRegionCode";
		public static final String HR_SYS_OGRP_NM = "humanResourcesSystemOperationsGroupName";
		public static final String HR_SYS_DIV_NM ="humanResourcesSystemDivisionName";
		
		//==========
		// TESERRACT table number constants
		//==========
		public static final String TABNO_DEPT_NO = "10069";
		
	//error code,desc constants
		public static final String ERROR_GET_QUERY_JOBREQUISITION_DETAILS_ERROR_DESC="Query Exception occured in getJobRequisitionDetailsDAO method in GetJobRequisitionDetailsDAO";
		public static final String ERROR_GET_QUERY_GETBACKGROUN_CRIMINALANDDMVRECORDS_DESC="Query Exception occured in getBackgroundCriminalAndDMVRecords in CandidateDAO";
		
		
		//Reporting Constants
		
		public static final String  LANGUAGE_CD="en_US";
		public static final String ERROR_GET_QUERY_EXCEL_BASIC_REPORT_DESC="Query Exception occured in generateExcelBasicReport method in ReportingDAO";
		public static final String ERROR_GET_QUERY_EXCEL_WORKHISTORY_EXTERNALREPORT_DESC="Query Exception occured in generateExcelWorkHistoryExternalReport method in ReportingDAO";
		public static final String ERROR_GET_QUERY_EXCEL_WORKHISTORY_INERNALREPORT_DESC="Query Exception occured in generateExcelWorkHistoryInternalReport method in ReportingDAO";
		public static final String ERROR_GET_QUERY_EXCEL_EDUCATION_REPORT_DESC="Query Exception occured in generateExcelEducationReport method in ReportingDAO";
		public static final String ERROR_GET_QUERY_EXCEL_KEYCOURSE_REPORT_DESC="Query Exception occured in generateExcelKeyCourseReport method in ReportingDAO";
		public static final String ERROR_GET_QUERY_EXCEL_CAREERPLANNING_REPORT_DESC="Query Exception occured in generateCareerPlanningReport method in ReportingDAO";
		public static final String ERROR_GET_QUERY_EXCEL_MOBILITY_REPORT_DESC="Query Exception occured in generateExcelMobilityReport method in ReportingDAO";
		public static final String ERROR_GET_QUERY_EXCEL_LANGUAGE_REPORT_DESC="Query Exception occured in generateExcelLanguageReport method in ReportingDAO";
		public static final String ERROR_GET_QUERY_EXCEL_DEPARTMENTEXP_REPORT_DESC="Query Exception occured in generateExcelDepartmentExperienceReport method in ReportingDAO";
		public static final String ERROR_QUERY_PERFORMANCE_CURRENTINDIVIDUAL_PERSONALREPORT="Query Exception occured in generateCurrentIndvdlPersonalPerformanceReport method in PerformanceReportDAO";
		public static final String ERROR_QUERY_PERFORMANCE_CURRENTINDIVIDUAL_CAREERREPORT="Query Exception occured in generateCurrentIndvdlCareerPlnPerformanceReport method in PerformanceReportDAO";
		public static final String ERROR_QUERY_PERFORMANCE_CURRENTINDIVIDUAL_DEVPROFILEREPORT="Query Exception occured in generateCurrentIndvdlDevProfilePerformanceReport method in PerformanceReportDAO";
		public static final String ERROR_QUERY_PERFORMANCE_CURRENTINDIVIDUAL_RATINGSREPORT="Query Exception occured in generateCurrentIndvdlRatingsReport method in PerformanceReportDAO";
		public static final String ERROR_QUERY_PERFORMANCE_CURRENTINDIVIDUAL_RATINGSDISPREPORT="Query Exception occured in generateCurrentIndvdlRatingsDisplyCd method in PerformanceReportDAO";
		public static final String ERROR_QUERY_PERFORMANCE_SEARCHCRITERIA_REPORT="Query Exception occured in generateCurrentSearchCriteriaPerformanceReport method in PerformanceReportDAO";
		public static final String ERROR_QUERY_ASSOCITEPROFILE_CURRINDVDL_PERSONAL="Query Exception occured in generatePersonalReport method in AssociateProfileReportDAO";
		public static final String ERROR_QUERY_ASSOCITEPROFILE_CURRINDVDL_EDUCATION="Query Exception occured in generateEducationReport method in AssociateProfileReportDAO";
		public static final String ERROR_QUERY_ASSOCITEPROFILE_CURRINDVDL_COURSE="Query Exception occured in generateCourseReport method in AssociateProfileReportDAO";
		public static final String ERROR_QUERY_ASSOCITEPROFILE_CURRINDVDL_THDWorkHistory="Query Exception occured in generateTHDWorkHistoryReport method in AssociateProfileReportDAO";
		public static final String ERROR_QUERY_ASSOCITEPROFILE_CURRINDVDL_ExternalEmploymentHistory="Query Exception occured in generateExternalEmploymentReport method in AssociateProfileReportDAO";
		public static final String ERROR_QUERY_ASSOCITEPROFILE_CURRINDVDL_CAREERINTEREST="Query Exception occured in generateCareerInterestReport method in AssociateProfileReportDAO";
		public static final String ERROR_QUERY_ASSOCITEPROFILE_CURRINDVDL_MOBILITY="Query Exception occured in generateMobileReport method in AssociateProfileReportDAO";
		public static final String ERROR_QUERY_ASSOCITEPROFILE_CURRINDVDL_LANGUAGE="Query Exception occured in generateLanguageReport method in AssociateProfileReportDAO";
		public static final String ERROR_QUERY_ASSOCITEPROFILE_CURRINDVDL_COUNT="Query Exception occured in getCount method in AssociateProfileReportDAO";
		
		//DAO jndi
		public static final String workForceSuccessionPlanning = "java:comp/env/jdbc/WorkforceSuccessionPlanning.1";
		
} // end interface Constants
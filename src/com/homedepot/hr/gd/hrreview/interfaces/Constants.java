package com.homedepot.hr.gd.hrreview.interfaces;


/**
 * Interface containing COMMON constants used throughout the 
 * application
 */
public interface Constants
{	
	/** application/XML MIME type */
	public static final String APPLICATION_XML = "application/xml";
	
	public static final String APPLICATION_JSON= "application/json";
	public static final String APPLICATION_XLS = "application/vnd.ms-excel";
	
	/** application/x-www-form-urlencoded MIME type */
	public static final String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded";
	
	public static final String APPLICATION_PDF = "application/pdf";
	public static final String TEXT_HTML = "text/html";
	
	public static final String FORM_PARAM_DATA = "data";
	
	public static final String REQ_DTL_LIST = "reqDetList";

	/** version 1 */
	public static final int VERSION1 = 1;
	
	/** Empty string */
	public static final String EMPTY_STRING = "";
	
	public static final float NANOS_IN_SECOND = 1000000000.0f;	
	
	public static final String PGM_ID = "HRReview";
	public static final String SUB_SYS_CD = "hr"; 
	
	public static final String usaStoresConstant="7";
	
	// The various error codes used in the application.
	public static final int INVALID_INPUT_CODE = 100;
	public static final String INVALID_INPUT_DESC ="Invalid Input Provided";
	public static final int APP_FATAL_ERROR_CODE = 1000;
	public static final int NO_RECORDS_FOUND_ERROR_CODE = 200;
		
	// FormName Constants
	public static final String ERROR_APP_STATUS = "ERROR";
	public static final String SUCCESS_APP_STATUS = "SUCCESS";
		
	// Boolean values
	public static final String TRUE = "Y";
	public static final String FALSE = "N";
	
	//Candidate List Values
	public static final String CAND_APPLICANT = "CA" ;
	
	//releaseCandidate Values 
	public static final int INTERVIEW_SCHEDULED_CODE = 11;
	
	//lifecycle instants
	String HOST_LCP = "host.LCP";
	String QA = "QA";
	String PR = "PR";
	String QP = "QP";
	String AD = "AD";
	
	
	/**
	 * The maximum size of the file that could be uploaded through the
	 * Upload picture screen
	 */
	public static int MAX_FILE_SIZE = 1024 * 1024;
	
	/**
	 * The list of file extensions allowed to be uploaded through
	 * upload picture screen
	 */
	public static String ALLOWED_FILE_TYPES = "gif,png,bmp,avi,dib,fits,jpg,jpeg,pgm,ppm,psd,sun,wpg,pict,mtv";
	
	
	//method level error codes and descriptions
	public static final int ERROR_GET_FOUNDATION_COUNT_ERROR_CODE = 301;
	public static final String ERROR_FROM_UI="Error Occured in UI";
	public static final String ERROR_GET_STOREDETAILS_ERROR_DESC="Query Exception occured in getStoreDetailsDAO method in StoreDetailsDAO";
	public static final String ERROR_GET_STORESLIST_ERROR_DESC="Query Exception occured in getStoresList method in StoreDetailsDAO";
	public static final String ERROR_GET_DEPT_DETAILS_DAO_ERROR_DESC="Query Exception occured in getDeptsByStr method in DepartmentDetailsDAO";
	public static final String ERROR_UPDATE_REQUISITION_DAO_ERROR_DESC="Query Exception occured in submitReqDtlsForStoreTable method in UpdateRequisitionDAO";
	public static final String ERROR_UPDATE_BACKGROUNDCHECKRESULTS_DAO_ERROR_DESC="Query Exception occured in updateBackgroundCheckResults method in CandidateDAO";
	public static final String ERROR_UPDATE_REQUISITION_THD_DAO_ERROR_DESC="Query Exception occured in submitReqDtlsForTHDTable method in UpdateRequisitionDAO";
	public static final String ERROR_GET_JOBTITLE_DETAILS_DAO_ERROR_DESC="Query Exception occured in getJobTtlsByDeptByStr method in JobTitleDescriptionDAO";
	public static final String ERROR_GET_JOBREQUISITION_DETAIL_DESC="Exception occured in getJobRequisitionDetails method in GetJobRequisitionManager";
	public static final String ERROR_GET_STOREDETAILS_MGR_ERROR_DESC="Query Exception occured in getStoreDetails method in StoreManager";
	public static final String ERROR_GET_STORESLIST_MGR_ERROR_DESC="Query Exception occured in getStoresList method in StoreManager";
	public static final String ERROR_GET_DEPARTMENT_DETAIL_MGR_ERROR_DESC="Query Exception occured in getDeptDetails method in RequisitionDetailsManager";
	public static final String ERROR_GET_STOREDETAILS_SERVICE_ERROR_DESC="Exception occured in getStoreDetails method in StaffingService";
	public static final String ERROR_GET_STORESLIST_SERVICE_ERROR_DESC="Exception occured in getListOfStoresAndDC method in StaffingService";
	public static final String ERROR_JSON_PARSER_ERROR_DESC="JSON Parser Exception occured in StaffingService";
	public static final String ERROR_BACKGROUNDCHECK_INVALIDINPUT_ERROR_DESC="Input Request is null";
	public static final String ERROR_GET_JOBREQUISITION_DETAIL_SERVICE_DESC="Exception occured in getJobRequisitionResults method in StaffingService";
	public static final String ERROR_UPDATE_REQ_SERVICE_DESC="Exception occured in updateRequisitionDetails method in StaffingService";
	public static final String ERROR_UPDATE_BGC_SERVICE_DESC="Exception occured in updateBackgroundCheckResults method in StaffingService";
	public static final String ERROR_GET_DEPARTMENT_DETAIL_SERVICE_DESC="Exception occured in getDepartmentDetails method in StaffingService";
	public static final String ERROR_GET_NOSTOREDETAILS="No Store Details retrieved. Please provide an existing StoreNumber";
	public static final String ERROR_GET_NOSTOREDCDETAILS="No Store/DC Details retrieved.";
	public static final String ERROR_GET_NOREQUISITION="No Requisition Details retrieved for the provided Store number";
	public static final String ERROR_GET_NO_DEPTDETAILS="No Department Details retrieved for the Provided Inputs";
	public static final String ERROR_GET_VERSION_VALIDATION_ERROR_DESC="Validation Exception occured in validateVersion method in ValidationUtils";
	public static final String ERROR_GET_ILLEGALARGUMENT_VALIDATION_ERROR_DESC="IllegalArgument Exception occured in validateVersion method in ValidationUtils";
	public static final String ERROR_GET_STORENUMBER_VALIDATION_ERROR_DESC="Validation Exception occured in validateUsingRegex method in ValidationUtils";
	public static final String ERROR_GET_ILLEGALARGUMENT_STORENUMBER_VALIDATION_ERROR_DESC="IllegalArgument Exception occured in validateUsingRegex method in ValidationUtils";
	public static final String ERROR_CLOSEREQ_PHONEINTVW_SCHEDULE_COUNT="Cannot Close the Requisition.Phone interviews scheduled with no interview results recorded is one or more";
	public static final String ERROR_EMPTY_REQ="Requisition Number is null in the input";
	public static final String ERROR_CLOSEREQ_CANDIDATE_OFFERRESULT_COUNT="Cannot Close the Requisition. No of candidates who are extended an offer without that offers result being recorded is one or more";
	public static final String ERROR_CLOSEREQ_MISSING_INTVW_RESULT="Cannot Close the Requisition. No of Missing Interview Result Count is one or more";
	public static final String ERROR_CLOSEREQ_DAO_ERROR_DESC="Query Exception occured in submitReqDtlsForTHDTable method in UpdateRequisitionDAO";
	public static final String ERROR_QUERY_CLOSEREQ_PHONEINTVW_SCHEDULE_COUNT="Query Exception occured in getPhoneIntvwScheduleCount in CloseRequisitionDAO";
	public static final String ERROR_QUERY_CLOSEREQ_CANDIDATE_OFFERRESULT_COUNT="Query Exception occured in getCandidateOfferResultCount in CloseRequisitionDAO";
	public static final String ERROR_QUERY_CLOSEREQ_MISSING_INTVW_RESULT="Query Exception occured in getMissingInterviewCount in CloseRequisitionDAO";
	public static final String ERROR_QUERY_CLOSEREQ_UPDATE_REQUISITION_FLAG="Query Exception occured in updateRequisitionFlag in CloseRequisitionDAO";
	public static final String ERROR_QUERY_GET_PHONESCREENIDS_REQUISITION_FLAG="Query Exception occured in getPhoneScrnIds in CloseRequisitionDAO";
	public static final String ERROR_QUERY_UPDATE_PHONESCREENIDS_STATUSCD_FLAG="Query Exception occured in updatePhoneScrnIdForStatCds in CloseRequisitionDAO";
	public static final String ERROR_QUERY_UPDATE_ASSOCIATES="Query Exception occured in updateAssociates in CloseRequisitionDAO";
	public static final String ERROR_QUERY_UPDATE_APPLICANTS="Query Exception occured in updateApplicants in CloseRequisitionDAO";
	public static final String ERROR_GET_QUERY_CLOSEREQ_CANDIDATE_ERROR_DESC="Query Exception occured in getListOfCandidatIds method in CloseRequistionDAO";
	public static final String ERROR_GET_QUERY_BACKGROUNDCHECK_ERROR_DESC="Query Exception occured in getBackgroundCriminalAndDMVRecords method in CandidateDAO";
	public static final String ERROR_GET_QUERY_BACKGRO_ERROR_DESC="Query Exception occured in getBackgroundCriminalAndDMVRecords method in CandidateDAO";
	public static final String ERROR_EMPTY_REQ_DESC="Invalid Input Provided. Requisition Number cannot be null";
	public static final String ERROR_GET_PERFORMANCESUMMARY="Exception occured in getPerformanceSummaryReport method in ReportingService";
	public static final String ERROR_GET_ASSOCIATE_PROFILE="Exception occured in getAssociateProfileReport method in ReportingService";
	public static final String ERROR_GET_EXPORTTOEXCEL="Exception occured in getExcelSheetReport method in ReportingService";
	
	
	
	
	
	
		
    // Browser error codes
	public static final int BAD_REQUEST = 400;
	public static final int UN_AUTHORIZED_REQUEST = 401;
	public static final int HTTP_OK = 200 ;
	
	
	
	// Generic exception
	public static final String RUN_TIME_ERROR="Encountering technical issue, please try after some time";
	public static final String GENERIC_ERROR_MESSAGE = "Encountering issues, while processing your request.";
	
	
	// Errors for applicant profile page
	public static final String ERROR_GET_APPL_PROFILE_ERROR_DESC="Exception occured in getApplPersonalInfo method in ApplProfileDetailDAO";	
	public static final String ERROR_GET_ASSOCIATE_INFO_ERROR_DESC="Exception occured in getAssociateInfo method in ApplProfileDetailDAO";
	public static final String ERROR_GET_ASSOCIATE_REVIEW_ERROR_DESC="Exception occured in getAssociateReview method in ApplProfileDetailDAO";
	public static final String ERROR_GET_APPLICANT_AVAILABLEDETAILS_ERROR_DESC="Exception occured in getApplicantAvailabilityDetails method in ApplProfileDetailDAO";
	public static final String ERROR_GET_ASSOCIATE_PREV_POSITION_ERROR_DESC="Exception occured in getAssociatePrevPosistion method in ApplProfileDetailDAO";
	public static final String ERROR_GET_APPL_EDUCATION_INFO_ERROR_DESC="Exception occured in getApplEducationInfo method in ApplProfileDetailDAO";
	public static final String ERROR_GET_APPL_WORK_HISTORY_ERROR_DESC="Exception occured in getApplWorkHistroyInfo method in ApplProfileDetailDAO";
	public static final String ERROR_GET_APPL_JOB_PREF_ERROR_DESC="Exception occured in getApplJobPrefInfo method in ApplProfileDetailDAO";
	public static final String ERROR_GET_APPL_LANG_INFO_ERROR_DESC="Exception occured in getApplLangInfo method in ApplProfileDetailDAO";
	public static final String ERROR_GET_ASSOC_LANG_INFO_ERROR_DESC="Exception occured in getAssoLangInfo method in ApplProfileDetailDAO";
	public static final String ERROR_GET_APPL_SKIL_INFO_ERROR_DESC="Exception occured in getApplSkillInfo method in ApplProfileDetailDAO";
	public static final String ERROR_GET_APPL_PHONE_SCREEN_ERROR_DESC="Exception occured in getApplPhnScreenInfo method in ApplProfileDetailDAO";
	public static final String ERROR_GET_APPL_HISTORY_INFO_ERROR_DESC="Exception occured in getApplHistoryInfo method in ApplProfileDetailDAO";
	public static final String ERROR_GET_APPL_APP_HISTORY_INFO_ERROR_DESC="Exception occured in getApplAppHistoryInfo method in ApplProfileDetailDAO";
	public static final String ERROR_GET_ASSOC_APPL_HISTORYINFO_ERROR_DESC="Exception occured in getAssoAppHistoryInfo method in ApplProfileDetailDAO";
	public static final String ERROR_GET_APPL_APP_HISTORY_INFO_2_ERROR_DESC="Exception occured in getApplAppHistoryInfo2 method in ApplProfileDetailDAO";
	public static final String ERROR_GET_ASSOC_APP_HISTORY_INFO_2_ERROR_DESC="Exception occured in getAssoAppHistoryInfo2 method in ApplProfileDetailDAO";	
		
	public static final String ERROR_GET_APPL_PROFILE_MGR_ERROR_DESC="HR Review Exception occured in getApplicantProfile method in ApplProfileManager";
	public static final String ERROR_GET_APPL_PROFILE_SERVICE_ERROR_DESC="Exception occured in getApplProfile method in StaffingService";
	public static final String ERROR_GET_NO_APPL_PROFILE_DETAILS="No Candidate Details retrieved for given ApplicantID";
		
	public static final String ERROR_PARSE_TIMESTAMP = "Error occurred when parsing the timestamp.";
	
	//Errors for Candidate List
	public static final String ERROR_GET_CANDIDATELIST = "Exception occured in getCandidateList" ;
	public static final String ERROR_GET_CANDIDATELIST_QUERY = "QueryException occured in getCandidateList" ;
	public static final String ERROR_RELEASE_CANDIDATE = "Exception occured in releaseCandidate" ;
	public static final String ERROR_RELEASE_CANDIDATE_QUERY = "QueryException occured in releaseCandidate" ;
	public static final String ERROR_MOVE_CANDIDATE = "Exception occured in moveCandidate" ;
	public static final String ERROR_RELEASE_YELLOW_BGC = "Candidate can not be released from requisition because background check status is yellow.  Must be overridden to green or red.";

	
	//Errors for Release Candidate
	public static final String ERROR_INCORRECT_APPLICANT_ID = "Incorrect value for applicant ID" ;
	public static final String ERROR_INCORRECT_CANDIDATE_REF_NUMBER = "Incorrect value for Candidate Reference Number" ;
	
	//Errors for Move Candidate
	public static final String ERROR_INVALID_REQ_NUMBER = "Error moving candidate.  Invalid requisition number." ;
	public static final String ERROR_RED_BGC_RESULT = "Candidate cannot be moved because background check result is red.";
	public static final String QE_APPLICANT_INTERVIEW = "QueryException in move candidate updating Applicant Interview";

	public static final String ERROR_INTERVIEW_SCHEDULED_QUERY = "QueryException occurred in interviewedscheduled in candidateManager" ;
	public static final String ERROR_BACKGROUND_CHECK = "QueryException occurred in backgroundColorExist in candidateManager" ;
	
	
	// Errors for Attach Candidate to requisition
	public static final String ERROR_ATTACH_CANDIDATE_TO_REQUISITION = "Exception occurred in attachApplToRequisition" ;
	public static final String ERROR_ATTACH_CANDIDATE_TO_REQUISITION_QUERY = "QueryException occurred in attachApplToRequisition" ;
	
	// Errors for Search Candidate
	public static final String ERROR_SEARCH_CANDIDATE_LIST = "Exception occured in searchCandidate" ;
	public static final String ERROR_SEARCH_CANDIDATE_LIST_QUERY = "QueryException occured in searchCandidate" ;
	
	// Errors for Search Candidate - inline data
	public static final String ERROR_SEARCH_CANDIDATE_MORE_INFO = "Exception occured in searchCandidate" ;
	public static final String ERROR_SEARCH_CANDIDATE_MORE_INFO_QUERY = "QueryException occured in searchCandidate" ;
	
	// Errors for Search Candidate
	public static final String ERROR_PROCESS_REQUISITION_SEARCH_RESULTS_INFO_QUERY = "QueryException occured in processRequisitionSearchResults" ;
		
	// Errors for Add Applicant to requisition
	public static final String APPLICANT_HTB_RESULTS_FAILED = "error.message.applicant.htb.results.failed";
	public static final String APPLICANT_NOT_AVAILABLE = "error.message.applicant.not.available";
	// Errors for Create rejection details
	public static final String REJECTION_DETAILS_CREATED = "Rejection Details have been created successfully" ;
	public static final int INSERTING_REJECTION_DETAILS_ERROR_CODE = 410 ;
	public static final String INSERTING_REJECTION_DETAILS_ERROR_MSG = "Error occurred while inserting rejectionCode for ";
	public static final String ERROR_CREATE_REJECT_CODE = "Exception occured in createRejectDetails" ;
	public static final String ERROR_CREATE_REJECT_CODE_QUERY = "QueryException occured in createRejectDetails" ;
	
	// Errors for Background check Results
	public static final String ERROR_BACKGROUND_CHECK_RESULTS = "Exception occured in Background check Results" ;
	public static final String ERROR_BACKGROUND_CHECK_RESULTS_QUERY = "QueryException occured in Background check Results" ;
		
	// Errors for Background check Results
	public static final String ERROR_ATTACH_APPLICANT = "Exception occured in Attach Applicant" ;
	public static final String ERROR_ATTACH_APPLICANT_QUERY = "QueryException occured in Attach Applicant" ;
	
	// Errors for Drug Test Results
	public static final String ERROR_DRUG_TEST_RESULTS = "Exception occured in Drug Test Results" ;
	public static final String ERROR_DRUG_TEST_RESULTS_QUERY = "QueryException occured in Drug Test Results" ;
	
	// Errors for Search Drug Test Results
	public static final String ERROR_SEARCH_DRUG_TEST_RESULTS = "Exception occured in Search Drug Test Results" ;
	public static final String ERROR_SEARCH_DRUG_TEST_RESULTS_QUERY = "QueryException occured in Search Drug Test Results" ;
	
	// Errors for latestRejectReason
	public static final String ERROR_GET_LATEST_REJECT_REASON = "Exception occured in getLatestRejectReason" ;
	public static final String ERROR_GET_LATEST_REJECT_REASONE_QUERY = "QueryException occured in getLatestRejectReason" ;
	public static final String LATEST_REJECTION_DETAILS_FOUND = "Rejection Details have been retrieved successfully" ;
		
	// Errors for RejectReason
	public static final String ERROR_GET_REJECT_REASON = "Exception occured in getRejectDetails" ;
	public static final String ERROR_GET_REJECT_REASONE_QUERY = "QueryException occured in getRejectDetails" ;
	public static final String GET_REJECTION_DETAILS_FOUND = "Rejection Details have been retrieved successfully" ;
		
	// Errors for markApplicantsAsConsideredInQP
	public static final String ERROR_MARK_APPLICANT_AS_CONSIDERED_IN_QP = "Exception occured in markApplicantsAsConsideredInQP" ;
	public static final String ERROR_MARK_APPLICANT_AS_CONSIDERED_IN_QP_QUERY = "QueryException occured in markApplicantsAsConsideredInQP" ;
	public static final String INSERTING_MARK_APPLICANT_AS_CONSIDERED_ERROR_MSG = "Error occurred inserting considered status for applicants.";
	
	//User error msgs for Close requisition
	public static final String CLOSE_REQUISITION_OFFERRESULTS_FAILED = "error.message.closeRequsition.offerresults.failed";
	public static final String CLOSE_REQUISITION_INTERVIEW_RESULTS_FAILED = "error.message.closeRequsition.interviewresults.failed";
	
	//Reporting Constants
	public static final String ERROR_GET_EXCEL_REPORT_DETAIL_DESC="Exception Occured in getExcelSheetReport method in ReportingManager";
	public static final String ERROR_GET_CURRINDIVIDUAL_PERFORMACEREPORT_DESC="Exception Occured in generateCurrentIndividualPerformanceReport method in ReportingManager";
	public static final String ERROR_GET_EXPORTTOEXCEL_DESC="Exception occured in createExcel method in ReportingManager";
	public static final String ERROR_GENERATE_PERFORMANCESUMMARY="Exception occured in perfmDevSummRpt in ReportingManager";
	public static final String ERROR_GENERATE_ASSOCIATEPROFILE="Exception occured in generateAssocProfileReport in FOPReport";
	
	public static final String ERROR_GET_CURRINDIVIDUAL_ASSOCIATEPROFILE_REPORT_DESC="Exception Occured in getAssociateProfileReport method in ReportingManager";
	
	public static final String EXCEL_REPORT_STR="EXCELREPORT";
	
	/**
	 * String constant used as KEY in Hashtable to store the Literals Resource Bundle, shared accross the Application
	 */
	public static String LITERALS_RESOURCE_BUNDLE = "LITERALS_RESOURCE_BUNDLE";
	public static String PG_ASSO_PFL_REPORT= "ASSOCIATE_PFL_REPORT";
	public static String REP_FOR_CURR = "C";
	public static String REP_CODE = "RepCode";
	public static String DROP_OPTIONS = "drpoptions";
	public static String CRITERIA = "10";
	public static int OP_LOAD_EXECUTIVE_BACKUPS = 1;
	public static int OP_LOAD_ASSOC_TBL_PROFILE = 7;
	public static int OP_LOAD_EXPORT_TO_EXCEL = 26;
	public static int OP_LOAD_PERF_DEV_SUMMARY = 23;
	public static int OP_LOAD_PND_ELONG = 28;
	
	//TODO-- modify the class package below
	public static String ASSOC_TBL_PROFILE_CLASS = "com.homedepot.hr.gd.reports.bl.AssocPflReport";
	
	public static String ASAP_DESC = "ASAP_DESC";
	public static String PERF_DEV_SUMMARY = "com.homedepot.hr.gd.reports.bl.PerfDevSummaryReport";
	public static String EXPORT_TO_EXCEL = "com.homedepot.hr.gd.reports.bl.ExportToExcelReport";
	public static String SELECTED_EXCEL_COL_CATEGORY= "ExcelColumnCategory";
	public static String INCLUDE_RATINGS = "IncludeRatings";
	public static final String ELONG_PND_ELONG = "com.homedepot.hr.gd.reports.bl.ElongPerfDevSumReport";
	public static String CUSTOM_REPORT_TITLE = "01";
	public static String REP_FOR_ALL = "A";
	public static String REP_FOR_SRCH = "S";
	
	public static final int FATAL_MSG_NBR = 100;
	public static final char FATAL = 'F';
	
	public static final String LANG_EN_US = "en_US" ;
	
	public static final String ERROR_MEESAGE_MORE_THAN_10K_RESULTS = "Your search criteria returns more than 10K records, Please enter new search criteria." ;
	
			
} // end interface Constants
package com.homedepot.hr.gd.hrreview.bl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.avalon.framework.logger.ConsoleLogger;
import org.apache.fop.apps.Driver;
import org.apache.fop.apps.FOPException;
import org.apache.fop.messaging.MessageHandler;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.homedepot.hr.gd.hrreview.dao.AssociateProfileReportDAO;
import com.homedepot.hr.gd.hrreview.dao.ExcelReportingDAO;
import com.homedepot.hr.gd.hrreview.dao.PerformanceReportDAO;
import com.homedepot.hr.gd.hrreview.dto.AssocProfilePersonal;
import com.homedepot.hr.gd.hrreview.dto.AssocProfilePrintDTO;
import com.homedepot.hr.gd.hrreview.dto.Associate;
import com.homedepot.hr.gd.hrreview.dto.AssociateList;
import com.homedepot.hr.gd.hrreview.dto.AssociateProfileDTO;
import com.homedepot.hr.gd.hrreview.dto.Education;
import com.homedepot.hr.gd.hrreview.dto.EducationList;
import com.homedepot.hr.gd.hrreview.dto.ExcelReportDTO;
import com.homedepot.hr.gd.hrreview.dto.ExportToExcelRequest;
import com.homedepot.hr.gd.hrreview.dto.ExternalWorkHistory;
import com.homedepot.hr.gd.hrreview.dto.ExternalWorkHistoryList;
import com.homedepot.hr.gd.hrreview.dto.KeyCoursesList;
import com.homedepot.hr.gd.hrreview.dto.Language;
import com.homedepot.hr.gd.hrreview.dto.LanguageList;
import com.homedepot.hr.gd.hrreview.dto.Mobility;
import com.homedepot.hr.gd.hrreview.dto.MobilityLang;
import com.homedepot.hr.gd.hrreview.dto.PerformanceReportDTO;
import com.homedepot.hr.gd.hrreview.dto.PlanningList;
import com.homedepot.hr.gd.hrreview.dto.THDWorkHistory;
import com.homedepot.hr.gd.hrreview.dto.THDWorkHistoryList;
import com.homedepot.hr.gd.hrreview.exceptions.HrReviewApplLogMessage;
import com.homedepot.hr.gd.hrreview.exceptions.HrReviewException;
import com.homedepot.hr.gd.hrreview.interfaces.Constants;
import com.homedepot.hr.gd.hrreview.response.BasicInformationResponse;
import com.homedepot.hr.gd.hrreview.response.ReportingResponse;
import com.homedepot.hr.gd.hrreview.util.AppUtil;
import com.homedepot.hr.gd.hrreview.util.ApplicationAids;
import com.homedepot.hr.gd.hrreview.util.PerfmDevSumLabel;
import com.homedepot.hr.gd.hrreview.util.XMLGenerator;



public class ReportingManager implements Constants {

	private static final Logger mLogger = Logger.getLogger(ReportingManager.class);
	final static String PDF_EXTENSION = ".pdf";
	static PerfmDevSumLabel perfmDev1 = new PerfmDevSumLabel();
	private static HSSFWorkbook workbook = new HSSFWorkbook();
	private static HSSFSheet worksheet =null;
	private static int rowNum;
	private static String associateID=null;
	private static String firstName=null;
	private static String lastName=null;
	private static String jobTitle=null;
	private static String divisionName=null;
	private static String departmentName=null;
	private static String location=null;
	private static String effectiveBgnDate=null;
	private static String divisionCode=null;
	private static String storeNumber=null;
	private static String  performanceCode=null;
	private static String  leadershipCd=null;
	private static String  potentialCd=null;
	private static String company=null;
	 private static String overrideJobTitleDesc;
	 private static String overrideDivisionName;
      private static String startDate;
      private static String endDate;
      //Education
      private static String gradeYear;
      private static String schoolName;
      private static String degree;
      //KeyCourse
      private static String course;
      private static String organization;
      private static String courseCompletionDate;
      //CareerPlanning
      private static String jtGrpDesc;
      private static String title; 
      private static String potentialTiming;
      //Mobility
      private static String mobilityLocation;
      private static String relocationTypeDesc;
      //Language
      private static String language;
      //Departmental Experience
      private static String departmentExperience;
      private static String experienceYrs;

	private static String columnText1=null;
	private static String columnText2=null;
	private static String columnText3=null;
	private static String columnText4=null;
	private static String columnText5=null;
	private static String columnText6=null;
	private static String columnText7=null;
	private static String columnText8=null;
	private static String columnText9=null;
	private static String columnText10=null;
	private static String columnText11=null;
	private static String columnText12=null;
	private static String columnText13=null;
	private static String columnText14=null;
	private static String columnText15=null;
	
	private static HashMap ratingDispCodeMap = null;
	
    public static BasicInformationResponse getExcelSheetReport(ExportToExcelRequest exportToExcelRequest,HttpServletResponse httpResponse) throws HrReviewException 
	{
		List<ExcelReportDTO> excelReportDTOList= null;
		List<ExcelReportDTO> tempExcelReportDTOList= null;
		List<ExcelReportDTO> internalWorkHistDTO=null;
		List<ExcelReportDTO> tempInternalWorkHistDTO=null;
		String reportCategory=exportToExcelRequest.getReportCategory();
		boolean ratingsFlag=Boolean.valueOf(exportToExcelRequest.getRatingsFlag());
		String userID=AppUtil.getLdapId();
		String xml=null;
		ReportingResponse reportingResponse= new ReportingResponse();
		List<String> zeroEmployeeIdList= null;
		long startTime = 0;
		if (mLogger.isDebugEnabled()) {
			startTime = System.nanoTime();
		}

		try {
			resetColumnsHeaderText();
			
			if(reportCategory.equals("1")) //Basic Report
			{
				excelReportDTOList= new ArrayList<ExcelReportDTO>();
				int i=0;
				zeroEmployeeIdList= new ArrayList<String>();
				
				for(AssociateList associateList:exportToExcelRequest.getAssociateList())
				{
				i++;	
				if(i==500)
				{
					i=0;
					zeroEmployeeIdList.add(associateList.getAssocId());
					tempExcelReportDTOList=ExcelReportingDAO.generateExcelBasicReport(userID,ratingsFlag,zeroEmployeeIdList);
					zeroEmployeeIdList= new ArrayList<String>();
					excelReportDTOList.addAll(tempExcelReportDTOList);
				}
				else
				{
					zeroEmployeeIdList.add(associateList.getAssocId());	
				}
					
				}
				if(i<500)
				{
					tempExcelReportDTOList=ExcelReportingDAO.generateExcelBasicReport(userID,ratingsFlag,zeroEmployeeIdList);
					excelReportDTOList.addAll(tempExcelReportDTOList);
				}
				
				setColumnsHeaderText("ASSOCIATE_ID","NAME_FIRST","NAME_LAST","CURR_JOB_TITLE","CURR_DIVISION_NAME","CURR_DEPT_NAME","CURR_LOCATION","STORE_NUMBER",null,null,null,null,null,null,null);
				if(ratingsFlag)
				setColumnsHeaderText("ASSOCIATE_ID","NAME_FIRST","NAME_LAST","CURR_JOB_TITLE","CURR_DIVISION_NAME","CURR_DEPT_NAME","CURR_LOCATION","STORE_NUMBER","PERFORMANCE_CODE","LEADERSHIP_CODE","POTENTIAL_CODE",null,null,null,null);	
			}
			if(reportCategory.equals("2")) //WorkHistoryExternalReport
			{
				excelReportDTOList= new ArrayList<ExcelReportDTO>();
				int i=0;
				zeroEmployeeIdList= new ArrayList<String>();
				
				for(AssociateList associateList:exportToExcelRequest.getAssociateList())
				{
				i++;	
				if(i==500)
				{
					i=0;
					zeroEmployeeIdList.add(associateList.getAssocId());
					tempExcelReportDTOList=ExcelReportingDAO.generateExcelWorkHistoryExternalReport(userID,ratingsFlag,zeroEmployeeIdList);
					zeroEmployeeIdList= new ArrayList<String>();
					excelReportDTOList.addAll(tempExcelReportDTOList);
				}
				else
				{
					zeroEmployeeIdList.add(associateList.getAssocId());	
				}
					
				}
				if(i<500)
				{
					tempExcelReportDTOList=ExcelReportingDAO.generateExcelWorkHistoryExternalReport(userID,ratingsFlag,zeroEmployeeIdList);
					excelReportDTOList.addAll(tempExcelReportDTOList);
				}
				
				setColumnsHeaderText("ASSOCIATE_ID","NAME_FIRST","NAME_LAST","CURR_JOB_TITLE","CURR_DIVISION_NAME","CURR_DEPT_NAME","CURR_LOCATION","STORE_NUMBER","COMPANY","JOB_TITLE","START_DATE","END_DATE",null,null,null);
				if(ratingsFlag)
					setColumnsHeaderText("ASSOCIATE_ID","NAME_FIRST","NAME_LAST","CURR_JOB_TITLE","CURR_DIVISION_NAME","CURR_DEPT_NAME","CURR_LOCATION","STORE_NUMBER","PERFORMANCE_CODE","LEADERSHIP_CODE","POTENTIAL_CODE","COMPANY","JOB_TITLE","START_DATE","END_DATE");
				
			}
			if(reportCategory.equals("3")) //WorkHistoryInternalReport
			{
				excelReportDTOList= new ArrayList<ExcelReportDTO>();
				internalWorkHistDTO=new ArrayList<ExcelReportDTO>();
				int i=0;
				zeroEmployeeIdList= new ArrayList<String>();
				
				for(AssociateList associateList:exportToExcelRequest.getAssociateList())
				{
				i++;	
				if(i==500)
				{
					i=0;
					zeroEmployeeIdList.add(associateList.getAssocId());
					tempExcelReportDTOList=ExcelReportingDAO.generateExcelBasicReport(userID,ratingsFlag,zeroEmployeeIdList);
					tempInternalWorkHistDTO=ExcelReportingDAO.generateExcelWorkHistoryInternalReport(userID, ratingsFlag,zeroEmployeeIdList);
					zeroEmployeeIdList= new ArrayList<String>();
					excelReportDTOList.addAll(tempExcelReportDTOList);
					internalWorkHistDTO.addAll(tempInternalWorkHistDTO);
				}
				else
				{
					zeroEmployeeIdList.add(associateList.getAssocId());	
				}
					
				}
				if(i<500)
				{
					tempExcelReportDTOList=ExcelReportingDAO.generateExcelBasicReport(userID,ratingsFlag,zeroEmployeeIdList);
					tempInternalWorkHistDTO=ExcelReportingDAO.generateExcelWorkHistoryInternalReport(userID, ratingsFlag,zeroEmployeeIdList);
					excelReportDTOList.addAll(tempExcelReportDTOList);
					internalWorkHistDTO.addAll(tempInternalWorkHistDTO);
				}
				
				for(ExcelReportDTO internallReportDTO:internalWorkHistDTO)
				{
					for (ExcelReportDTO excelReportDTO:excelReportDTOList)
					{
						if(excelReportDTO.getAssociateID().equals(internallReportDTO.getAssociateID()))
						{
							internallReportDTO.setLocation(excelReportDTO.getLocation());
							internallReportDTO.setStoreNumber(excelReportDTO.getStoreNumber());
							internallReportDTO.setDivisionName(excelReportDTO.getDivisionName());
							internallReportDTO.setDepartmentName(excelReportDTO.getDepartmentName());
							internallReportDTO.setJobTitle(excelReportDTO.getJobTitle());
							if(ratingsFlag)
							{
							if(!(AppUtil.isEmptyString(excelReportDTO.getPerformanceCode())))
							internallReportDTO.setPerformanceCode(excelReportDTO.getPerformanceCode());
							else
							internallReportDTO.setPerformanceCode("-");
							if(!(AppUtil.isEmptyString(excelReportDTO.getLeadershipCode())))
							internallReportDTO.setLeadershipCode(excelReportDTO.getLeadershipCode());
							else
							internallReportDTO.setLeadershipCode("-");
							if(!(AppUtil.isEmptyString(excelReportDTO.getPotentialCode())))
							internallReportDTO.setPotentialCode(excelReportDTO.getPotentialCode());
							else
							internallReportDTO.setPotentialCode("-");
							}
						}
					}
				}
				excelReportDTOList=internalWorkHistDTO;
				
				setColumnsHeaderText("ASSOCIATE_ID","NAME_FIRST","NAME_LAST","CURR_JOB_TITLE","CURR_DIVISION_NAME","CURR_DEPT_NAME","CURR_LOCATION","STORE_NUMBER","JOB_TITLE","DIVISION_NAME","START_DATE","END_DT",null,null,null);
				if(ratingsFlag)
				setColumnsHeaderText("ASSOCIATE_ID","NAME_FIRST","NAME_LAST","CURR_JOB_TITLE","CURR_DIVISION_NAME","CURR_DEPT_NAME","CURR_LOCATION","STORE_NUMBER","PERFORMANCE_CODE","LEADERSHIP_CODE","POTENTIAL_CODE","JOB_TITLE","DIVISION_NAME","START_DATE","END_DT");
			}
			if(reportCategory.equals("4")) //Education Report
			{
				excelReportDTOList= new ArrayList<ExcelReportDTO>();
				int i=0;
				zeroEmployeeIdList= new ArrayList<String>();
				
				for(AssociateList associateList:exportToExcelRequest.getAssociateList())
				{
				i++;	
				if(i==500)
				{
					i=0;
					zeroEmployeeIdList.add(associateList.getAssocId());
					tempExcelReportDTOList=ExcelReportingDAO.generateExcelEducationReport(userID,ratingsFlag,zeroEmployeeIdList);
					zeroEmployeeIdList= new ArrayList<String>();
					excelReportDTOList.addAll(tempExcelReportDTOList);
				}
				else
				{
					zeroEmployeeIdList.add(associateList.getAssocId());	
				}
					
				}
				if(i<500)
				{
					tempExcelReportDTOList=ExcelReportingDAO.generateExcelEducationReport(userID,ratingsFlag,zeroEmployeeIdList);
					excelReportDTOList.addAll(tempExcelReportDTOList);
				}
				
				setColumnsHeaderText("ASSOCIATE_ID","NAME_FIRST","NAME_LAST","CURR_JOB_TITLE","CURR_DIVISION_NAME","CURR_DEPT_NAME","CURR_LOCATION","STORE_NUMBER","GRAD_YEAR","SCHOOL_NAME","DEGREE",null,null,null,null);
				if(ratingsFlag)
				setColumnsHeaderText("ASSOCIATE_ID","NAME_FIRST","NAME_LAST","CURR_JOB_TITLE","CURR_DIVISION_NAME","CURR_DEPT_NAME","CURR_LOCATION","STORE_NUMBER","PERFORMANCE_CODE","LEADERSHIP_CODE","POTENTIAL_CODE","GRAD_YEAR","SCHOOL_NAME","DEGREE",null);
			}
			if(reportCategory.equals("5")) //KeyCourse Report
			{
				excelReportDTOList= new ArrayList<ExcelReportDTO>();
				int i=0;
				zeroEmployeeIdList= new ArrayList<String>();
				
				for(AssociateList associateList:exportToExcelRequest.getAssociateList())
				{
				i++;	
				if(i==500)
				{
					i=0;
					zeroEmployeeIdList.add(associateList.getAssocId());
					tempExcelReportDTOList=ExcelReportingDAO.generateExcelKeyCourseReport(userID,ratingsFlag,zeroEmployeeIdList);
					zeroEmployeeIdList= new ArrayList<String>();
					excelReportDTOList.addAll(tempExcelReportDTOList);
				}
				else
				{
					zeroEmployeeIdList.add(associateList.getAssocId());	
				}
					
				}
				if(i<500)
				{
					tempExcelReportDTOList=ExcelReportingDAO.generateExcelKeyCourseReport(userID,ratingsFlag,zeroEmployeeIdList);
					excelReportDTOList.addAll(tempExcelReportDTOList);
				}
				
				setColumnsHeaderText("ASSOCIATE_ID","NAME_FIRST","NAME_LAST","CURR_JOB_TITLE","CURR_DIVISION_NAME","CURR_DEPT_NAME","CURR_LOCATION","STORE_NUMBER","COURSE","ORGANIZATION","COMPLETION_DATE",null,null,null,null);
			    if(ratingsFlag)
			    setColumnsHeaderText("ASSOCIATE_ID","NAME_FIRST","NAME_LAST","CURR_JOB_TITLE","CURR_DIVISION_NAME","CURR_DEPT_NAME","CURR_LOCATION","STORE_NUMBER","PERFORMANCE_CODE","LEADERSHIP_CODE","POTENTIAL_CODE","COURSE","ORGANIZATION","COMPLETION_DATE",null);
			}
			if(reportCategory.equals("6")) //CareerPlanningReport
			{
				excelReportDTOList= new ArrayList<ExcelReportDTO>();
				int i=0;
				zeroEmployeeIdList= new ArrayList<String>();
				
				for(AssociateList associateList:exportToExcelRequest.getAssociateList())
				{
				i++;	
				if(i==500)
				{
					i=0;
					zeroEmployeeIdList.add(associateList.getAssocId());
					tempExcelReportDTOList=ExcelReportingDAO.generateCareerPlanningReport(userID,ratingsFlag,zeroEmployeeIdList);
					zeroEmployeeIdList= new ArrayList<String>();
					excelReportDTOList.addAll(tempExcelReportDTOList);
				}
				else
				{
					zeroEmployeeIdList.add(associateList.getAssocId());	
				}
					
				}
				if(i<500)
				{
					tempExcelReportDTOList=ExcelReportingDAO.generateCareerPlanningReport(userID,ratingsFlag,zeroEmployeeIdList);
					excelReportDTOList.addAll(tempExcelReportDTOList);
				}
				
				
				setColumnsHeaderText("ASSOCIATE_ID","NAME_FIRST","NAME_LAST","CURR_JOB_TITLE","CURR_DIVISION_NAME","CURR_DEPT_NAME","CURR_LOCATION","STORE_NUMBER","FUNCTION_BUSINESS","TITLE","POTENTIAL_TIMING",null,null,null,null);
				if(ratingsFlag)
				setColumnsHeaderText("ASSOCIATE_ID","NAME_FIRST","NAME_LAST","CURR_JOB_TITLE","CURR_DIVISION_NAME","CURR_DEPT_NAME","CURR_LOCATION","STORE_NUMBER","PERFORMANCE_CODE","LEADERSHIP_CODE","POTENTIAL_CODE","FUNCTION_BUSINESS","TITLE","POTENTIAL_TIMING",null);
			}
			if(reportCategory.equals("7")) //Mobility Report
			{
				excelReportDTOList= new ArrayList<ExcelReportDTO>();
				int i=0;
				zeroEmployeeIdList= new ArrayList<String>();
				
				for(AssociateList associateList:exportToExcelRequest.getAssociateList())
				{
				i++;	
				if(i==500)
				{
					i=0;
					zeroEmployeeIdList.add(associateList.getAssocId());
					tempExcelReportDTOList=ExcelReportingDAO.generateExcelMobilityReport(userID,ratingsFlag,zeroEmployeeIdList);
					zeroEmployeeIdList= new ArrayList<String>();
					excelReportDTOList.addAll(tempExcelReportDTOList);
				}
				else
				{
					zeroEmployeeIdList.add(associateList.getAssocId());	
				}
					
				}
				if(i<500)
				{
					tempExcelReportDTOList=ExcelReportingDAO.generateExcelMobilityReport(userID,ratingsFlag,zeroEmployeeIdList);
					excelReportDTOList.addAll(tempExcelReportDTOList);
				}
				
				
			    setColumnsHeaderText("ASSOCIATE_ID","NAME_FIRST","NAME_LAST","CURR_JOB_TITLE","CURR_DIVISION_NAME","CURR_DEPT_NAME","CURR_LOCATION","STORE_NUMBER","MOBILITY_LOCATION","TERM",null,null,null,null,null);
			    if(ratingsFlag)
			    setColumnsHeaderText("ASSOCIATE_ID","NAME_FIRST","NAME_LAST","CURR_JOB_TITLE","CURR_DIVISION_NAME","CURR_DEPT_NAME","CURR_LOCATION","STORE_NUMBER","PERFORMANCE_CODE","LEADERSHIP_CODE","POTENTIAL_CODE","MOBILITY_LOCATION","TERM",null,null);
			}
			if(reportCategory.equals("8")) //Language Report
			{	
				excelReportDTOList= new ArrayList<ExcelReportDTO>();
				int i=0;
				zeroEmployeeIdList= new ArrayList<String>();
				
				for(AssociateList associateList:exportToExcelRequest.getAssociateList())
				{
				i++;	
				if(i==500)
				{
					i=0;
					zeroEmployeeIdList.add(associateList.getAssocId());
					tempExcelReportDTOList=ExcelReportingDAO.generateExcelLanguageReport(userID,ratingsFlag,zeroEmployeeIdList);
					zeroEmployeeIdList= new ArrayList<String>();
					excelReportDTOList.addAll(tempExcelReportDTOList);
				}
				else
				{
					zeroEmployeeIdList.add(associateList.getAssocId());	
				}
					
				}
				if(i<500)
				{
					tempExcelReportDTOList=ExcelReportingDAO.generateExcelLanguageReport(userID,ratingsFlag,zeroEmployeeIdList);
					excelReportDTOList.addAll(tempExcelReportDTOList);
				}
				
				
				setColumnsHeaderText("ASSOCIATE_ID","NAME_FIRST","NAME_LAST","CURR_JOB_TITLE","CURR_DIVISION_NAME","CURR_DEPT_NAME","CURR_LOCATION","STORE_NUMBER","LANGUAGE",null,null,null,null,null,null);
				if(ratingsFlag)
				setColumnsHeaderText("ASSOCIATE_ID","NAME_FIRST","NAME_LAST","CURR_JOB_TITLE","CURR_DIVISION_NAME","CURR_DEPT_NAME","CURR_LOCATION","STORE_NUMBER","PERFORMANCE_CODE","LEADERSHIP_CODE","POTENTIAL_CODE","LANGUAGE",null,null,null);
			}
			if(reportCategory.equals("9")) //Departmental Experience Report
			{
				excelReportDTOList= new ArrayList<ExcelReportDTO>();
				int i=0;
				zeroEmployeeIdList= new ArrayList<String>();
				
				for(AssociateList associateList:exportToExcelRequest.getAssociateList())
				{
				i++;	
				if(i==500)
				{
					i=0;
					zeroEmployeeIdList.add(associateList.getAssocId());
					tempExcelReportDTOList=ExcelReportingDAO.generateExcelDepartmentExperienceReport(userID,ratingsFlag,zeroEmployeeIdList);
					zeroEmployeeIdList= new ArrayList<String>();
					excelReportDTOList.addAll(tempExcelReportDTOList);
				}
				else
				{
					zeroEmployeeIdList.add(associateList.getAssocId());	
				}
					
				}
				if(i<500)
				{
					tempExcelReportDTOList=ExcelReportingDAO.generateExcelDepartmentExperienceReport(userID,ratingsFlag,zeroEmployeeIdList);
					excelReportDTOList.addAll(tempExcelReportDTOList);
				}
				
				
				setColumnsHeaderText("ASSOCIATE_ID","NAME_FIRST","NAME_LAST","CURR_JOB_TITLE","CURR_DIVISION_NAME","CURR_DEPT_NAME","CURR_LOCATION","STORE_NUMBER","DEPARTMENT_EXP","NUMBER_OF_YEARS",null,null,null,null,null);
				if(ratingsFlag)
				setColumnsHeaderText("ASSOCIATE_ID","NAME_FIRST","NAME_LAST","CURR_JOB_TITLE","CURR_DIVISION_NAME","CURR_DEPT_NAME","CURR_LOCATION","STORE_NUMBER","PERFORMANCE_CODE","LEADERSHIP_CODE","POTENTIAL_CODE","DEPARTMENT_EXP","NUMBER_OF_YEARS",null,null);
			}

			reportingResponse.setExcelReportDTO(excelReportDTOList);
			xml=XMLGenerator.generateXML(reportingResponse);
			mLogger.debug("Excelsheet Reporting XML"+xml);
			
			createExcel(xml,httpResponse,ratingsFlag);
		}
		
		catch (HrReviewException e) {
      	  throw e;
		   }

		catch (Exception e) {
			mLogger.error(HrReviewApplLogMessage.create(HrReviewApplLogMessage.FATAL_ERROR,
					Constants.ERROR_GET_EXCEL_REPORT_DETAIL_DESC,e));
			throw new HrReviewException(Constants.BAD_REQUEST,Constants.ERROR_GET_EXCEL_REPORT_DETAIL_DESC, e);
			
		}

		if (mLogger.isDebugEnabled()) {
			long endTime = System.nanoTime();
			if (startTime == 0) {
				startTime = endTime;
			}
			mLogger.debug(String
					.format("Exiting getExcelSheetReport(). Total time to process request: %.4f seconds",
							(((float) endTime - startTime) / NANOS_IN_SECOND)));
		}
		return null;
	}
	
	public static void setColumnsHeaderText(String col1,String col2,String col3,String col4,String col5,String col6,String col7,String col8,String col9,String col10,String col11,String col12,String col13,String col14,String col15)
	{
		columnText1=col1;
		columnText2=col2;
		columnText3=col3;
		columnText4=col4;
		columnText5=col5;
		columnText6=col6;
		columnText7=col7;
		columnText8=col8;
		columnText9=col9;
		columnText10=col10;
		columnText11=col11;
		columnText12=col12;
		columnText13=col13;
		columnText14=col14;
		columnText15=col15;
		
		
	}
	public static void resetColumnsHeaderText()
	{
		columnText1=null;
		columnText2=null;
		columnText3=null;
		columnText4=null;
		columnText5=null;
		columnText6=null;
		columnText7=null;
		columnText8=null;
		columnText9=null;
		columnText10=null;
		columnText11=null;
		columnText12=null;
		columnText13=null;
		columnText14=null;
		columnText15=null;
		
	}
	
	public static byte[] getPerformanceSummaryReport(String reportCategory,String associateID,HttpServletResponse response) throws HrReviewException 
	{
		String userID=AppUtil.getLdapId();
		String associateId=associateID;
		long startTime = 0;
		List<PerformanceReportDTO> performanceReportListDTO= new ArrayList<PerformanceReportDTO>();
		PerformanceReportDTO performanceDTO= null;
		perfmDev1 = new PerfmDevSumLabel();
		
		if (mLogger.isDebugEnabled()) {
			startTime = System.nanoTime();
		}
        byte[] content=null;
		try {
			
			//call for report generation
			String rptId = AppUtil.generateRandomInt();
			
			String perfDispCode = "";
			String potDispCode = "";
			String ldrDispCode = "";
						
			if(reportCategory.equals("1"))
			{
			performanceReportListDTO=PerformanceReportDAO.generateCurrentIndvdlPersonalPerformanceReport(userID,associateId);//personalSQL
			if(performanceReportListDTO!=null)
			{
		    performanceDTO= new PerformanceReportDTO();
			performanceDTO=performanceReportListDTO.get(0);
			perfmDev1.setAssociateName(performanceDTO.getFirstName()+""+performanceDTO.getLastName());
			perfmDev1.setBusinessUnit(performanceDTO.getBusinessName());
			perfmDev1.setJobTitle(performanceDTO.getJobTtlDesc());
     		}
			performanceReportListDTO=PerformanceReportDAO.generateCurrentIndvdlCareerPlnPerformanceReport(userID,associateId);//careerPlanSQL
			if(performanceReportListDTO!=null && performanceReportListDTO.size()>0)
			{
			performanceDTO= new PerformanceReportDTO();
			performanceDTO=performanceReportListDTO.get(0);
			perfmDev1.setPositionComment(performanceDTO.getJobTtlDesc());
			perfmDev1.setPotentialTiming(performanceDTO.getSplnGstatDesc());
			}
			performanceReportListDTO=PerformanceReportDAO.generateCurrentIndvdlDevProfilePerformanceReport(userID,associateId);//DevProfileSQL
			if(performanceReportListDTO!=null && performanceReportListDTO.size()>0)
			{
		     Vector vectElement=new Vector();
			for(PerformanceReportDTO performanceReportDTO:performanceReportListDTO)
			{
				vectElement.addElement(performanceReportDTO.getReviewDtlTxt());
			}
			performanceDTO= new PerformanceReportDTO();
			performanceDTO=performanceReportListDTO.get(performanceReportListDTO.size()-1);
			vectElement.addElement(performanceDTO.getAssocRevID());
			vectElement.addElement(performanceDTO.getRevTypDesc());
			perfmDevSumProfileInfo(vectElement);

			}
			performanceReportListDTO=PerformanceReportDAO.generateCurrentIndvdlRatingsDisplyCd();
			if(performanceReportListDTO!=null && performanceReportListDTO.size()>0)
			{
			ratingDispCodeMap = new HashMap(20);
			for(PerformanceReportDTO performanceReportDTO:performanceReportListDTO)
			{
				
				String dispCode=performanceReportDTO.getDispEvalRtgCd();
				dispCode = dispCode == null ? "" : dispCode.trim();
				ratingDispCodeMap.put(dispCode,performanceReportDTO.getEvalCatgryCode());
			}
            }
			
			performanceReportListDTO=PerformanceReportDAO.generateCurrentIndvdlRatingsReport(userID,associateId);
			if(performanceReportListDTO!=null && performanceReportListDTO.size()>0)
			{
			performanceDTO= new PerformanceReportDTO();
		
			performanceDTO=performanceReportListDTO.get(0);
			 if (!(performanceDTO.getPerfEvalRtgCode().equals("0")))
					perfDispCode = (String)ratingDispCodeMap.get(String.valueOf(performanceDTO.getPerfEvalRtgCode()));
             if (!(performanceDTO.getLeadershipEvalRtgCode().equals("0")))
					ldrDispCode = (String)ratingDispCodeMap.get(String.valueOf(performanceDTO.getLeadershipEvalRtgCode()));
				if (!(performanceDTO.getPotentialEvalRtgCode().equals("0")))
					potDispCode = (String)ratingDispCodeMap.get(String.valueOf(performanceDTO.getPotentialEvalRtgCode()));
				
				performanceDTO.setPerfEvalRtgCode(perfDispCode);
				performanceDTO.setLeadershipEvalRtgCode(ldrDispCode);
				performanceDTO.setPotentialEvalRtgCode(potDispCode);
				
				perfmRateCode(performanceDTO);
				potenRateCode(performanceDTO);
			}
			}
		content=perfmDevSummRpt(rptId,response);
			
		} 
		catch (HrReviewException e) {
        	  throw e;
		   }
		catch (Exception e) {
			mLogger.error(HrReviewApplLogMessage.create(HrReviewApplLogMessage.FATAL_ERROR,
					Constants.ERROR_GET_CURRINDIVIDUAL_PERFORMACEREPORT_DESC,e));
			throw new HrReviewException(Constants.BAD_REQUEST,Constants.ERROR_GET_CURRINDIVIDUAL_PERFORMACEREPORT_DESC, e);
			
		}

		if (mLogger.isDebugEnabled()) {
			long endTime = System.nanoTime();
			if (startTime == 0) {
				startTime = endTime;
			}
			mLogger.debug(String
					.format("Exiting getPerformanceSummaryReport(). Total time to process request: %.4f seconds",
							(((float) endTime - startTime) / NANOS_IN_SECOND)));
		}
		return content;
	}
	
	public static byte[] getAssociateProfileReport(String reportCategory,String associateId,HttpServletResponse response) throws HrReviewException 
	{
		String userID=AppUtil.getLdapId();
		long startTime = 0;
		if (mLogger.isDebugEnabled()) {
			startTime = System.nanoTime();
		}
		AssocProfilePrintDTO assocProfilePrintDTO= new AssocProfilePrintDTO();
		AssociateProfileDTO associateProfileDTO=new AssociateProfileDTO();
		List<AssociateProfileDTO> associateProfileListDTO=new ArrayList<AssociateProfileDTO>();
		Education educationDTO= new Education();
		EducationList educationList= new EducationList();
		List<EducationList> educationFinalList= new ArrayList<EducationList>();
		THDWorkHistory thdWorkHistory= new THDWorkHistory(); 
		THDWorkHistoryList thdWorkHistoryList= new THDWorkHistoryList();
		List<THDWorkHistoryList> finalTHDWorkHistoryList= new ArrayList<THDWorkHistoryList>();
		ExternalWorkHistoryList externalWorkHistoryList= new ExternalWorkHistoryList();
		ExternalWorkHistory externalWorkHistory= new ExternalWorkHistory();
		List<ExternalWorkHistoryList> finalExternalWorkHistoryList= new ArrayList<ExternalWorkHistoryList>();
		PlanningList planning= new PlanningList();
		List<PlanningList> finalPlanningList= new ArrayList<PlanningList>();
		LanguageList languageList= null;
		//List<LanguageList> finalLanguageList= new ArrayList<LanguageList>();
		Language language= new Language();
		
		Mobility mobility= new Mobility();
		String xml=null;

		try {
						
			if(reportCategory.equals("1"))
			{
			Associate associate= new Associate();
			associate.setPrintedDate("");			
			//assocProfilePrintDTO.setAssociate(associate);
			associateProfileDTO=AssociateProfileReportDAO.generatePersonalReport(userID, associateId);
			AssocProfilePersonal assocProfilePersonal=new AssocProfilePersonal();
			assocProfilePersonal.setName(associateProfileDTO.getFirstName()+" "+associateProfileDTO.getLastName());
			assocProfilePersonal.setTitle(associateProfileDTO.getJobTitleDesc());
			assocProfilePersonal.setDivision(associateProfileDTO.getDivisonName());
			assocProfilePersonal.setRegion(associateProfileDTO.getDivisonName());
			assocProfilePersonal.setLocation(associateProfileDTO.getLocation());
			assocProfilePersonal.setDepartment(associateProfileDTO.getDeptName());
			assocProfilePersonal.setOriginal_hire_dt(associateProfileDTO.getDateHired());
			assocProfilePersonal.setTime_w_co("");
			assocProfilePersonal.setStore_num(associateProfileDTO.getStrNbr());
			assocProfilePersonal.setCitizenship(associateProfileDTO.getCtznCntryCode());
			associate.setAssocProfilePersonal(assocProfilePersonal);
			
			associateProfileListDTO=AssociateProfileReportDAO.generateEducationReport(userID, associateId);
			for(AssociateProfileDTO assocProfileDTO:associateProfileListDTO)
			{
				educationList=new EducationList();
				educationList.setSchool(assocProfileDTO.getSchool());
				educationList.setLocation(assocProfileDTO.getLocText());
				educationList.setDegree(assocProfileDTO.getDegreeDesc());
				educationList.setMajor(assocProfileDTO.getCollegeMjr());
				educationList.setGraduated(assocProfileDTO.getGradeYesNo());
				educationFinalList.add(educationList);
			}
			
			//educationDTO.setEducation(educationFinalList);
			
			associate.setEducation(educationFinalList);
			
			
			associateProfileListDTO=AssociateProfileReportDAO.generateCourseReport(userID, associateId);
			//KeyCourses keyCourses= new KeyCourses();
			KeyCoursesList keyCoursesList= null;
			List<KeyCoursesList> finalKeyCoursesList= new ArrayList<KeyCoursesList>();
			
			for(AssociateProfileDTO assocProfileDTO:associateProfileListDTO)
			{
			keyCoursesList= new KeyCoursesList();
			keyCoursesList.setOrganization(assocProfileDTO.getOvrdSchoolName());
			keyCoursesList.setDescription(assocProfileDTO.getSplnCourseDesc());
			keyCoursesList.setComplete(assocProfileDTO.getCourseEndDate());
			finalKeyCoursesList.add(keyCoursesList);
			}
			//keyCourses.setKeyCoursesList(finalKeyCoursesList);
			associate.setKeyCourses(finalKeyCoursesList);
			
			
			associateProfileListDTO=AssociateProfileReportDAO.generateTHDWorkHistoryReport(userID, associateId);
			for(AssociateProfileDTO assocProfileDTO:associateProfileListDTO)
			{
			thdWorkHistoryList= new THDWorkHistoryList();
			thdWorkHistoryList.setPosition(assocProfileDTO.getJobTitle());
			thdWorkHistoryList.setForWhom(assocProfileDTO.getDivisonName());
			thdWorkHistoryList.setTo(assocProfileDTO.getEndDate());
			thdWorkHistoryList.setFrom(assocProfileDTO.getStartDate());	
			finalTHDWorkHistoryList.add(thdWorkHistoryList);
			}
			//thdWorkHistory.setThdWorkHistoryList(finalTHDWorkHistoryList);
			associate.setThdWorkHistory(finalTHDWorkHistoryList);
			
			associateProfileListDTO=AssociateProfileReportDAO.generateExternalEmploymentReport(userID, associateId);
			for(AssociateProfileDTO assocProfileDTO:associateProfileListDTO)
			{
			externalWorkHistoryList= new ExternalWorkHistoryList();
			externalWorkHistoryList.setPosition(assocProfileDTO.getOvrdJobTtlDesc());
			//externalWorkHistoryList.setForWhom(associateProfileDTO.getEmplyrName());
			externalWorkHistoryList.setForWhom(assocProfileDTO.getEmplyrName());
			externalWorkHistoryList.setTo(assocProfileDTO.getWrkEndDate());
			externalWorkHistoryList.setFrom(assocProfileDTO.getWrkBgnDate());	
			finalExternalWorkHistoryList.add(externalWorkHistoryList);
			}
			
			associate.setExternalWorkHistory(finalExternalWorkHistoryList);
			
			associateProfileListDTO=AssociateProfileReportDAO.generateCareerInterestReport(userID, associateId);
			
			for(AssociateProfileDTO assocProfileDTO:associateProfileListDTO)
			{
			planning= new PlanningList();
			planning.setFunction(assocProfileDTO.getJobTitleDesc());
			planning.setTiming(assocProfileDTO.getSplnGstatDesc());
			planning.setTitle(assocProfileDTO.getPosition());
			
			finalPlanningList.add(planning);
			}
			associate.setPlanning(finalPlanningList);
		
			associateProfileDTO=AssociateProfileReportDAO.generateMobileReport(userID, associateId);
			
			//TODOL chk if the below short desc is needed or not
			//mobility.setShortDesc(associateProfileDTO.getReloDesription());
            MobilityLang mobilityLang= new MobilityLang();
            mobilityLang.setDepends(associateProfileDTO.getDependson());
            mobility.setShortDesc(associateProfileDTO.getReloDesription());
            mobilityLang.setMobility(mobility);
            
			
            associateProfileListDTO=AssociateProfileReportDAO.generateLanguageReport(userID, associateId);
            Hashtable<String, LanguageList> hashAssocProfile = new Hashtable<String, LanguageList>();
			
            for(AssociateProfileDTO assocProfileDTO:associateProfileListDTO)
			{
            if (!hashAssocProfile.containsKey(assocProfileDTO.getLanguage().trim())) 
            {
            	languageList= new LanguageList();
            	languageList.setLanguage(assocProfileDTO.getLanguage());
            	hashAssocProfile.put(assocProfileDTO.getLanguage().trim(),languageList);
            }
            else {
            	languageList = hashAssocProfile.get(assocProfileDTO.getLanguage().trim());					
			}
            
            if(assocProfileDTO.getLangUseTypDesc().equalsIgnoreCase("speak"))
            {
			languageList.setSpeak(assocProfileDTO.getLangPrfncyDesc());
            }
            if(assocProfileDTO.getLangUseTypDesc().equalsIgnoreCase("read"))
            {
            languageList.setRead(assocProfileDTO.getLangPrfncyDesc());	
            }
            if(assocProfileDTO.getLangUseTypDesc().equalsIgnoreCase("write"))
            {
            languageList.setWrite(assocProfileDTO.getLangPrfncyDesc());	
            }
            
            //finalLanguageList.add(languageList);
			}
            List<LanguageList> dtoList = (new ArrayList<LanguageList>(hashAssocProfile.values()));
			
			//language.setLanguageList(dtoList);
		    
			mobilityLang.setLanguageList(dtoList);
			associate.setMobilityLang(mobilityLang);
			assocProfilePrintDTO.setAssociate(associate);
			
			xml=XMLGenerator.generateXML(assocProfilePrintDTO);
			
			FOPReport.generateAssocProfileReport(xml,response);
			
			}
			
		} 
		
		catch (HrReviewException e) {
      	  throw e;
		   }
		
		catch (Exception e) {
			mLogger.error(HrReviewApplLogMessage.create(HrReviewApplLogMessage.FATAL_ERROR,
					Constants.ERROR_GET_CURRINDIVIDUAL_ASSOCIATEPROFILE_REPORT_DESC,e));
			throw new HrReviewException(Constants.BAD_REQUEST,Constants.ERROR_GET_CURRINDIVIDUAL_ASSOCIATEPROFILE_REPORT_DESC, e);
			
		}

		if (mLogger.isDebugEnabled()) {
			long endTime = System.nanoTime();
			if (startTime == 0) {
				startTime = endTime;
			}
			mLogger.debug(String
					.format("Exiting getAssociateProfileReport(). Total time to process request: %.4f seconds",
							(((float) endTime - startTime) / NANOS_IN_SECOND)));
		}
		return null;
	}
	
	
	public static void createExcel(String XML,HttpServletResponse httpResponse,boolean ratingsFlag) throws HrReviewException
	{
		rowNum=0;
		String inputXML=XML;
		//String inputXML="<ReportingResponse><ExcelReportDTO><AssociateId>1234</AssociateId><Time> 1230</Time><LastName>xxx</LastName><JobTitle>asdf</JobTitle><DivisionName>abcd</DivisionName><DeptName>tttt</DeptName><Location>erer</Location></ExcelReportDTO>></ReportingResponse>";
		workbook = new HSSFWorkbook();
		worksheet= workbook.createSheet("ExportToExcel Reporting");
		
			try {
	        	 DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
	            Document doc = docBuilder.parse(new InputSource(new ByteArrayInputStream(inputXML.getBytes("utf-8"))));
	            // normalize text representation
	            doc.getDocumentElement().normalize();
	            if(mLogger.isDebugEnabled())
	            mLogger.debug("Root element of the doc is :\" "+ doc.getDocumentElement().getNodeName() + "\"");
	            NodeList reportingResponse = doc.getElementsByTagName("ReportingResponse");
	            int totalPersons = reportingResponse.getLength();
	            if(mLogger.isDebugEnabled())
	            mLogger.debug("Total no of nodes : " + totalPersons);
	            	            
	            NodeList excelReportTag =null; 
	            excelReportTag=doc.getElementsByTagName("ExcelReportDTO");
	            int totalExcelReportTag = excelReportTag.getLength();
	            if(mLogger.isDebugEnabled())
	            mLogger.debug("Total no of excelReportTag : " + totalExcelReportTag);
	            generateTableHeaders(ratingsFlag);
	            rowNum++;
	            for (int s = 0; s < excelReportTag.getLength(); s++) 
	            {
	                Node excelReportTagNode = null;
	                excelReportTagNode=excelReportTag.item(s);
	                if (excelReportTagNode.getNodeType() == Node.ELEMENT_NODE) 
	                {
	                	 Element excelReportTagElement = (Element) excelReportTagNode;
		                 
		                 
		                 associateID=null;
		                 firstName=null;
		                 lastName=null;
		                 jobTitle=null;
		                 divisionName=null;
		                 departmentName=null;
		                 location=null;
		                 effectiveBgnDate=null;
		                 divisionCode=null;
		                 storeNumber=null;
		                 performanceCode=null;
		                 leadershipCd=null;
		                 potentialCd=null;
		                 company=null;
		                 overrideJobTitleDesc=null;
		                 overrideDivisionName=null;
		                 startDate=null;
		                 endDate=null;
		                 //Education
		                 gradeYear=null;
		                 schoolName=null;
		                 degree=null;
		                 //KeyCourse
		                 course=null;
		                 organization=null;
		                 courseCompletionDate=null;
		                 //CareerPlanning
		                 jtGrpDesc=null;
		                 title=null; 	
		                 potentialTiming=null;
		                 //Mobility
		                 mobilityLocation=null;
		                 relocationTypeDesc=null;
		                 //Language
		                 language=null;
		                 //Departmental Experience
		                 departmentExperience=null;
		                 experienceYrs=null;
		                 
		                 
		                        NodeList associateIdNode =null;
		 	            		associateIdNode = excelReportTagElement.getElementsByTagName("associateID");
		 	            		if(associateIdNode!=null && associateIdNode.item(0)!=null )
		 	            		associateID =  associateIdNode.item(0).getFirstChild().getNodeValue();
		 	            		else
		 	            		associateID="-";
		 	            		
		 	            		NodeList firstNameNode = excelReportTagElement.getElementsByTagName("firstName");
		 	            		if(firstNameNode!=null && firstNameNode.item(0)!=null )
		 	            		firstName =  firstNameNode.item(0).getFirstChild().getNodeValue();
		 	            		else
		 	            		firstName="-";
		 	            		
		 	            		NodeList lastNameNode = excelReportTagElement.getElementsByTagName("lastName");
		 	            		if(null!=lastNameNode && lastNameNode.item(0)!=null )
		 	            		lastName =  lastNameNode.item(0).getFirstChild().getNodeValue();
		 	            		else
		 	            		lastName="-";
		 	            		
		 	            		NodeList jobTitleListNode = excelReportTagElement.getElementsByTagName("jobTitle");
		 	            		if(jobTitleListNode!=null && jobTitleListNode.item(0)!=null  )
		 	            		jobTitle =  jobTitleListNode.item(0).getFirstChild().getNodeValue();
		 	            		else
		 	            		jobTitle="-";
		 	            		
		 	            		NodeList divisionNameNode = excelReportTagElement.getElementsByTagName("divisionName");
		 	            		if(divisionNameNode!=null && divisionNameNode.item(0)!=null )
		 	            		divisionName =  divisionNameNode.item(0).getFirstChild().getNodeValue();
		 	            		else
		 	            		divisionName="-";
		 	            		
		 	            		NodeList departmentNameNode = excelReportTagElement.getElementsByTagName("departmentName");
		 	            		if(departmentNameNode!=null && departmentNameNode.item(0)!=null )
		 	            		departmentName =  departmentNameNode.item(0).getFirstChild().getNodeValue();
		 	            		else
		 	            		departmentName="-";
		 	            		
		 	            		NodeList locationNode = excelReportTagElement.getElementsByTagName("location");
		 	            		if(locationNode!=null && locationNode.item(0)!=null )
		 	            		location =  locationNode.item(0).getFirstChild().getNodeValue();
		 	            		else
		 	            		location="-";
		 	            		
//		 	            		NodeList effectiveBgnDateNode = excelReportTagElement.getElementsByTagName("effectiveBgnDate");
//		 	            		if(effectiveBgnDateNode!=null && effectiveBgnDateNode.item(0)!=null )
//		 	            		effectiveBgnDate =  effectiveBgnDateNode.item(0).getFirstChild().getNodeValue();
//		 	            		
//		 	            		NodeList divisionCodeNode = excelReportTagElement.getElementsByTagName("divisionCode");
//		 	            		if(divisionCodeNode!=null && divisionCodeNode.item(0)!=null )
//		 	            		divisionCode =  divisionCodeNode.item(0).getFirstChild().getNodeValue();
		 	            		
		 	            		NodeList storeNumberNode = excelReportTagElement.getElementsByTagName("storeNumber");
		 	            		if(storeNumberNode!=null && storeNumberNode.item(0)!=null )
		 	            		storeNumber =  storeNumberNode.item(0).getFirstChild().getNodeValue();
		 	            		else
		 	            		storeNumber="-";
		 	            		
		 	            		NodeList performanceCodeNode = excelReportTagElement.getElementsByTagName("performanceCode");
		 	            		if(performanceCodeNode!=null && performanceCodeNode.item(0)!=null && performanceCodeNode.item(0).getFirstChild()!=null)
		 	            		{
		 	            		performanceCode =  getPerformanceCodeDesc(performanceCodeNode.item(0).getFirstChild().getNodeValue());
		 	            		if(AppUtil.isEmptyString(performanceCode))
		 	            		performanceCode="-";
		 	            		}
		 	            		else if(performanceCodeNode!=null && performanceCodeNode.item(0)!=null)
		 	            		performanceCode="-";
		 	            		
		 	            		NodeList leaderShipCdNode = excelReportTagElement.getElementsByTagName("leadershipCode");
		 	            		if(leaderShipCdNode!=null && leaderShipCdNode.item(0)!=null && leaderShipCdNode.item(0).getFirstChild()!=null)
		 	            		{
		 	            		leadershipCd =  leaderShipCdNode.item(0).getFirstChild().getNodeValue();
		 	            		if(AppUtil.isEmptyString(leadershipCd))
		 	            		leadershipCd="-";
		 	            		}
		 	            		else if (leaderShipCdNode!=null && leaderShipCdNode.item(0)!=null)
		 	            		leadershipCd="-";
		 	            		
		 	            		NodeList potentialCdNode = excelReportTagElement.getElementsByTagName("potentialCode");
		 	            		if(potentialCdNode!=null && potentialCdNode.item(0)!=null && potentialCdNode.item(0).getFirstChild()!=null )
		 	            		{
		 	            		potentialCd =  potentialCdNode.item(0).getFirstChild().getNodeValue();
		 	            		
		 	            		if(AppUtil.isEmptyString(potentialCd))
		 	            		potentialCd="-";
		 	            		}
		 	            		else if(potentialCdNode!=null && potentialCdNode.item(0)!=null)
		 	            		potentialCd="-";
		 	            		
		 	            		NodeList companyNode = excelReportTagElement.getElementsByTagName("company");
		 	            		if(companyNode!=null && companyNode.item(0)!=null && companyNode.item(0).getFirstChild()!=null)
		 	            		company =  companyNode.item(0).getFirstChild().getNodeValue();
		 	            		else if(companyNode!=null && companyNode.item(0)!=null)
		 	            		company="-";
		 	            		
		 	            		NodeList overrideJobTitleNode = excelReportTagElement.getElementsByTagName("overrideJobTitleDesc");
		 	            		if(overrideJobTitleNode!=null && overrideJobTitleNode.item(0)!=null && overrideJobTitleNode.item(0).getFirstChild()!=null)
		 	            		overrideJobTitleDesc =  overrideJobTitleNode.item(0).getFirstChild().getNodeValue();
		 	            		else if(overrideJobTitleNode!=null && overrideJobTitleNode.item(0)!=null)
		 	            		overrideJobTitleDesc="-";
		 	            		
		 	            		NodeList overrideDivisionNode = excelReportTagElement.getElementsByTagName("overrideDivisionName");
		 	            		if(overrideDivisionNode!=null && overrideDivisionNode.item(0)!=null && overrideDivisionNode.item(0).getFirstChild()!=null )
		 	            		overrideDivisionName =  overrideDivisionNode.item(0).getFirstChild().getNodeValue();
		 	            		else if(overrideDivisionNode!=null && overrideDivisionNode.item(0)!=null)
		 	            		overrideDivisionName="-";
		 	            		
		 	            		NodeList startDateNode = excelReportTagElement.getElementsByTagName("startDate");
		 	            		if(startDateNode!=null && startDateNode.item(0)!=null  && startDateNode.item(0).getFirstChild()!=null)
		 	            		startDate =  startDateNode.item(0).getFirstChild().getNodeValue();
		 	            		else if(startDateNode!=null && startDateNode.item(0)!=null)
		 	            		startDate="-";	
		 	            		
		 	            		NodeList endDateNode = excelReportTagElement.getElementsByTagName("endDate");
		 	            		if(endDateNode!=null && endDateNode.item(0)!=null && endDateNode.item(0).getFirstChild()!=null)
		 	            	    endDate =  endDateNode.item(0).getFirstChild().getNodeValue();
		 	            		else if(endDateNode!=null && endDateNode.item(0)!=null)
		 	            		endDate="-";
		 	            		
		 	            		NodeList gradeYearNode = excelReportTagElement.getElementsByTagName("gradeYear");
		 	            		if(gradeYearNode!=null && gradeYearNode.item(0)!=null && gradeYearNode.item(0).getFirstChild()!=null)
		 	            		gradeYear =  gradeYearNode.item(0).getFirstChild().getNodeValue();
		 	            		else if(gradeYearNode!=null && gradeYearNode.item(0)!=null)
		 	            		gradeYear="-";
		 	            		
		 	            		NodeList schoolNameNode = excelReportTagElement.getElementsByTagName("schoolName");
		 	            		if(schoolNameNode!=null && schoolNameNode.item(0)!=null &&  schoolNameNode.item(0).getFirstChild()!=null )
		 	            		schoolName =  schoolNameNode.item(0).getFirstChild().getNodeValue();
		 	            		else if(schoolNameNode!=null && schoolNameNode.item(0)!=null)
		 	            		schoolName="-"; 	
		 	            		
		 	            		NodeList degreeNode = excelReportTagElement.getElementsByTagName("degree");
		 	            		if(degreeNode!=null && degreeNode.item(0)!=null && degreeNode.item(0).getFirstChild()!=null )
		 	            		degree =  degreeNode.item(0).getFirstChild().getNodeValue();
		 	            		else if(degreeNode!=null && degreeNode.item(0)!=null)
		 	            		degree="-";
		 	            		
		 	            		NodeList courseNode = excelReportTagElement.getElementsByTagName("course");
		 	            		if(courseNode!=null && courseNode.item(0)!=null && courseNode.item(0).getFirstChild()!=null )
		 	                    course =  courseNode.item(0).getFirstChild().getNodeValue();
		 	            		else if (courseNode!=null && courseNode.item(0)!=null)
		 	            		course="-";
		 	            		
		 	            		NodeList organizationNode = excelReportTagElement.getElementsByTagName("organization");
		 	            		if(organizationNode!=null && organizationNode.item(0)!=null && organizationNode.item(0).getFirstChild()!=null)
		 	            		organization =  organizationNode.item(0).getFirstChild().getNodeValue();
		 	            		else if(organizationNode!=null && organizationNode.item(0)!=null)
		 	            		organization="-";
		 	            			
		 	            		NodeList courseCompletionDateNode = excelReportTagElement.getElementsByTagName("courseCompletionDate");
		 	            		if(courseCompletionDateNode!=null && courseCompletionDateNode.item(0)!=null && courseCompletionDateNode.item(0).getFirstChild()!=null )
		 	            		courseCompletionDate =  courseCompletionDateNode.item(0).getFirstChild().getNodeValue();
		 	            		else if(courseCompletionDateNode!=null && courseCompletionDateNode.item(0)!=null)
		 	            		courseCompletionDate="-";
		 	                   
		 	            		NodeList jtGrpDescNode = excelReportTagElement.getElementsByTagName("jtGrpDesc");
		 	            		if(jtGrpDescNode!=null && jtGrpDescNode.item(0)!=null && jtGrpDescNode.item(0).getFirstChild()!=null )
		 	            		jtGrpDesc =  jtGrpDescNode.item(0).getFirstChild().getNodeValue();
		 	            		else if(jtGrpDescNode!=null && jtGrpDescNode.item(0)!=null)
		 	            		jtGrpDesc="-";
		 	            		
		 	            		NodeList titleNode = excelReportTagElement.getElementsByTagName("title");
		 	            		if(titleNode!=null && titleNode.item(0)!=null && titleNode.item(0).getFirstChild()!=null)
		 	            		title =  titleNode.item(0).getFirstChild().getNodeValue();
		 	            		else if (titleNode!=null && titleNode.item(0)!=null)
		 	            		title="-";
		 	            		
		 	            		NodeList potentialTimingNode = excelReportTagElement.getElementsByTagName("potentialTiming");
		 	            		if(potentialTimingNode!=null && potentialTimingNode.item(0)!=null &&  potentialTimingNode.item(0).getFirstChild()!=null )
		 	            		potentialTiming =  potentialTimingNode.item(0).getFirstChild().getNodeValue();
		 	            		else if (potentialTimingNode!=null && potentialTimingNode.item(0)!=null)
		 	            		potentialTiming="-";
		 	            		
		 	            		NodeList mobilityLocationNode = excelReportTagElement.getElementsByTagName("mobilityLocation");
		 	            		if(mobilityLocationNode!=null && mobilityLocationNode.item(0)!=null && mobilityLocationNode.item(0).getFirstChild()!=null )
		 	            		mobilityLocation =  mobilityLocationNode.item(0).getFirstChild().getNodeValue();
		 	            		else if (mobilityLocationNode!=null && mobilityLocationNode.item(0)!=null)
		 	            		mobilityLocation="-";
		 	            		
		 	            		NodeList relocationTypeDescNode = excelReportTagElement.getElementsByTagName("relocationTypeDesc");
		 	            		if(relocationTypeDescNode!=null && relocationTypeDescNode.item(0)!=null && relocationTypeDescNode.item(0).getFirstChild()!=null)
		 	            		relocationTypeDesc =  relocationTypeDescNode.item(0).getFirstChild().getNodeValue();
		 	            		else if(relocationTypeDescNode!=null && relocationTypeDescNode.item(0)!=null)
		 	            		relocationTypeDesc="-";
		 	            		
		 	            		NodeList languageNode = excelReportTagElement.getElementsByTagName("language");
		 	            		if(languageNode!=null && languageNode.item(0)!=null && languageNode.item(0).getFirstChild()!=null) 
		 	            		language =  languageNode.item(0).getFirstChild().getNodeValue();
		 	            		else if(languageNode!=null && languageNode.item(0)!=null)
		 	            		language="-";
		 	            		
		 	            		NodeList departmentExperienceNode = excelReportTagElement.getElementsByTagName("departmentExperience");
		 	            		if(departmentExperienceNode!=null && departmentExperienceNode.item(0)!=null && departmentExperienceNode.item(0).getFirstChild()!=null )
		 	            		departmentExperience =  departmentExperienceNode.item(0).getFirstChild().getNodeValue();
		 	            		else if(departmentExperienceNode!=null && departmentExperienceNode.item(0)!=null)
		 	            		departmentExperience="-";
		 	            		
		 	            		NodeList experienceYrsNode = excelReportTagElement.getElementsByTagName("experienceYrs");
		 	            		if(experienceYrsNode!=null && experienceYrsNode.item(0)!=null && experienceYrsNode.item(0).getFirstChild()!=null)
		 	            		experienceYrs =  experienceYrsNode.item(0).getFirstChild().getNodeValue();
		 	            		else if(experienceYrsNode!=null && experienceYrsNode.item(0)!=null)
		 	            		experienceYrs="-";
		 	            		
	
		 	            	}
		 	            	generateTableColumnValues();
		 	            	rowNum++;
			            
	                }
	                
	            for(int columnIndex = 0; columnIndex < 16; columnIndex++) {    
	            	worksheet.autoSizeColumn(columnIndex); 
	            	} 

	            worksheet.setColumnWidth(0, 2000);
	            worksheet.setColumnWidth(1, 2000);
	            worksheet.setColumnWidth(2, 2200);
	            worksheet.setColumnWidth(3, 3500);
	            worksheet.setColumnWidth(4, 2000);
	            worksheet.setColumnWidth(5, 2500);
	            worksheet.setColumnWidth(6, 3500);
	            worksheet.setColumnWidth(7, 2000);
	            worksheet.setColumnWidth(8, 2000);
	            worksheet.setColumnWidth(9, 1800);
	            worksheet.setColumnWidth(10, 2000);
	            worksheet.setColumnWidth(11, 1800);
	            worksheet.setColumnWidth(12, 1800);
	            worksheet.setColumnWidth(13, 1800);
	            worksheet.setColumnWidth(14, 1800);
	            worksheet.setColumnWidth(15, 1800);
	            
               // Setup the output 
	            String contentType = "application/vnd.ms-excel"; 
	            httpResponse.setContentType(contentType); 
	            ServletOutputStream out = httpResponse.getOutputStream(); 
	            workbook.write(out); 
	            out.flush(); 
	            out.close(); 

} 
           catch (Exception e) 
	        {
        	   mLogger.error(HrReviewApplLogMessage.create(HrReviewApplLogMessage.FATAL_ERROR,
   					Constants.ERROR_GET_EXPORTTOEXCEL_DESC,e));
   			   throw new HrReviewException(Constants.BAD_REQUEST,Constants.ERROR_GET_EXPORTTOEXCEL_DESC, e);
	        }
			
		} 	
	
	public static void generateExcelTemplate()
	{
	    Calendar printDate = Calendar.getInstance();
		// index from 0,0... cell A1 is cell(0,0)
		HSSFRow row1 = worksheet.createRow((short) 0);
		
		HSSFCellStyle cellStyle = workbook.createCellStyle();
				
		HSSFCellStyle boldCellStyle = workbook.createCellStyle();
		boldCellStyle.setWrapText(true);
		
		HSSFCellStyle textStyle = workbook.createCellStyle();
		textStyle.setWrapText(true);
		
		HSSFCell cellA1 = row1.createCell((short) 0);
		cellA1.setCellValue("ExportToExcel Report");
		//cellStyle = workbook.createCellStyle();
		
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short)15);
		cellStyle.setFont(font);
		cellStyle.setFillForegroundColor(HSSFColor.GOLD.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellA1.setCellStyle(cellStyle);
				
		HSSFFont bolderHeader = workbook.createFont();
		bolderHeader.setFontHeightInPoints((short)7);
		bolderHeader.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		boldCellStyle.setFont(bolderHeader);
		
		HSSFFont normalText = workbook.createFont();
		normalText.setFontHeightInPoints((short)7);
		textStyle.setFont(normalText);
		
        worksheet.addMergedRegion(new CellRangeAddress(0,0,0,4));

	}
	
	public static void generateTableHeaders(boolean ratingsFlag)
	{
		
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setWrapText(true);
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short)9);
		cellStyle.setFont(font);
		//cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
		//cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		HSSFRow row5 = worksheet.createRow((short) rowNum);
		if(!AppUtil.isEmptyString(columnText1))
		{
        HSSFCell associateId = row5.createCell((short) 0);
        associateId.setCellStyle(cellStyle);
        associateId.setCellValue(columnText1);
		}
		if(!AppUtil.isEmptyString(columnText2))
		{
        HSSFCell firstName = row5.createCell((short) 1);
        firstName.setCellStyle(cellStyle);
        firstName.setCellValue(columnText2);
		}
		if(!AppUtil.isEmptyString(columnText3))
		{
        HSSFCell lastName = row5.createCell((short) 2);
        lastName.setCellStyle(cellStyle);
        lastName.setCellValue(columnText3);
		}
		if(!AppUtil.isEmptyString(columnText4))
		{
        HSSFCell jobTitle = row5.createCell((short)3);
        jobTitle.setCellStyle(cellStyle);
        jobTitle.setCellValue(columnText4);
		}
		if(!AppUtil.isEmptyString(columnText5))
		{
        HSSFCell divisionName = row5.createCell((short) 4);
        divisionName.setCellStyle(cellStyle);
        divisionName.setCellValue(columnText5);
		}
		if(!AppUtil.isEmptyString(columnText6))
		{
        HSSFCell departmentName = row5.createCell((short) 5);
        departmentName.setCellStyle(cellStyle);
        departmentName.setCellValue(columnText6);
		}
		if(!AppUtil.isEmptyString(columnText7))
		{
        HSSFCell location = row5.createCell((short) 6);
        location.setCellStyle(cellStyle);
        location.setCellValue(columnText7);
		}
		if(!AppUtil.isEmptyString(columnText8))
		{
        HSSFCell effectiveBeginDate = row5.createCell((short) 7);
        effectiveBeginDate.setCellStyle(cellStyle);
        effectiveBeginDate.setCellValue(columnText8);
		}
		if(!AppUtil.isEmptyString(columnText9))
		{
        HSSFCell divisionCode = row5.createCell((short) 8);
        divisionCode.setCellStyle(cellStyle);
        divisionCode.setCellValue(columnText9);
		}
		if(!AppUtil.isEmptyString(columnText10))
		{
        HSSFCell storeNumber = row5.createCell((short) 9);
        storeNumber.setCellStyle(cellStyle);
        storeNumber.setCellValue(columnText10);
		}
        //if(ratingsFlag)
        //{
		if(!AppUtil.isEmptyString(columnText11))
		{
        HSSFCell performanceCode = row5.createCell((short) 10);
        performanceCode.setCellStyle(cellStyle);
        performanceCode.setCellValue(columnText11);
		}
		if(!AppUtil.isEmptyString(columnText12))
		{
        HSSFCell leadershipCode = row5.createCell((short) 11);
        leadershipCode.setCellStyle(cellStyle);
        leadershipCode.setCellValue(columnText12);
		}
		if(!AppUtil.isEmptyString(columnText13))
		{
        HSSFCell potentialCode = row5.createCell((short) 12);
        potentialCode.setCellStyle(cellStyle);
        potentialCode.setCellValue(columnText13);
		}
		if(!AppUtil.isEmptyString(columnText14))
		{
        HSSFCell col13 = row5.createCell((short) 13);
        col13.setCellStyle(cellStyle);
        col13.setCellValue(columnText14);
		}
		if(!AppUtil.isEmptyString(columnText15))
		{
        HSSFCell col14 = row5.createCell((short) 14);
        col14.setCellStyle(cellStyle);
        col14.setCellValue(columnText15);
		}
        
       // rowNum++;
	}
	
	
	public static void generateTableColumnValues()
	{
		int colIndex=0;
		HSSFCellStyle textStyle = workbook.createCellStyle();
		textStyle.setWrapText(true);
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short)7);
		textStyle.setFont(font);
		
		HSSFRow row5 = worksheet.createRow((short) rowNum);
        HSSFCell associateIDCell = row5.createCell((short) 0);
        associateIDCell.setCellValue(associateID);
        associateIDCell.setCellStyle(textStyle);
        HSSFCell firstNameCell = row5.createCell((short) 1);
        firstNameCell.setCellValue(firstName);
        firstNameCell.setCellStyle(textStyle);
        HSSFCell lastNameNoCell = row5.createCell((short) 2);
        lastNameNoCell.setCellValue(lastName);
        lastNameNoCell.setCellStyle(textStyle);
        HSSFCell jobTitleCell = row5.createCell((short)3);
        jobTitleCell.setCellValue(jobTitle);
        jobTitleCell.setCellStyle(textStyle);
        HSSFCell divisionNameCell = row5.createCell((short) 4);
        divisionNameCell.setCellValue(divisionName);
        divisionNameCell.setCellStyle(textStyle);
        HSSFCell departmentNameCell = row5.createCell((short) 5);
        departmentNameCell.setCellValue(departmentName);
        departmentNameCell.setCellStyle(textStyle);
        HSSFCell locationCell = row5.createCell((short) 6);
        locationCell.setCellValue(location);
        locationCell.setCellStyle(textStyle);
        
        HSSFCell storeNoCell = row5.createCell((short) 7);
        storeNoCell.setCellValue(storeNumber);
        storeNoCell.setCellStyle(textStyle);
        
        colIndex=8;
        
        if(!(AppUtil.isEmptyString(performanceCode)))
        {
        HSSFCell jobCodeCell = row5.createCell((short) colIndex);
        jobCodeCell.setCellValue(performanceCode);
        jobCodeCell.setCellStyle(textStyle);
        colIndex++;
        }
        if(!(AppUtil.isEmptyString(leadershipCd)))
        {
        HSSFCell hiringStorecell = row5.createCell((short) colIndex);
        hiringStorecell.setCellValue(leadershipCd);
        hiringStorecell.setCellStyle(textStyle);
        colIndex++;
        }
        if(!(AppUtil.isEmptyString(potentialCd)))
        {
        HSSFCell storeDeptnoCell = row5.createCell((short) colIndex);
        storeDeptnoCell.setCellValue(potentialCd);
        storeDeptnoCell.setCellStyle(textStyle);
        colIndex++;
        }
        
        if(!(AppUtil.isEmptyString(company)))
        {
        HSSFCell companycell = row5.createCell((short) colIndex);
        companycell.setCellValue(company);
        companycell.setCellStyle(textStyle);
        colIndex++;
        }
        if(!(AppUtil.isEmptyString(overrideJobTitleDesc)))
        {
        HSSFCell overrideJobTitleDescCell = row5.createCell((short) colIndex);
        overrideJobTitleDescCell.setCellValue(overrideJobTitleDesc);
        overrideJobTitleDescCell.setCellStyle(textStyle);
        colIndex++;
        }
        
        if(!(AppUtil.isEmptyString(overrideDivisionName)))
        {
        HSSFCell overrideDivisionDescCell = row5.createCell((short) colIndex);
        overrideDivisionDescCell.setCellValue(overrideDivisionName);
        overrideDivisionDescCell.setCellStyle(textStyle);
        colIndex++;
        }
        
        if(!(AppUtil.isEmptyString(startDate)))
        {
        HSSFCell startDateCell = row5.createCell((short) colIndex);
        startDateCell.setCellValue(startDate);
        startDateCell.setCellStyle(textStyle);
        colIndex++;
        }
        if(!(AppUtil.isEmptyString(endDate)))
        {
        HSSFCell endDateCell = row5.createCell((short) colIndex);
        endDateCell.setCellValue(endDate);
        endDateCell.setCellStyle(textStyle);
        colIndex++;
        }
        if(!(AppUtil.isEmptyString(gradeYear)))
        {
        HSSFCell gradeYearCell = row5.createCell((short) colIndex);
        gradeYearCell.setCellValue(gradeYear);
        gradeYearCell.setCellStyle(textStyle);
        colIndex++;
        }
        if(!(AppUtil.isEmptyString(schoolName)))
        {
        HSSFCell schoolNameCell = row5.createCell((short) colIndex);
        schoolNameCell.setCellValue(schoolName);
        schoolNameCell.setCellStyle(textStyle);
        colIndex++;
        }
        if(!(AppUtil.isEmptyString(degree)))
        {
        HSSFCell degreeCell = row5.createCell((short) colIndex);
        degreeCell.setCellValue(degree);
        degreeCell.setCellStyle(textStyle);
        colIndex++;
        }
        if(!(AppUtil.isEmptyString(course)))
        {
        HSSFCell courseCell = row5.createCell((short) colIndex);
        courseCell.setCellValue(course);
        courseCell.setCellStyle(textStyle);
        colIndex++;
        }
        if(!(AppUtil.isEmptyString(organization)))
        {
        HSSFCell organizationCell = row5.createCell((short) colIndex);
        organizationCell.setCellValue(organization);
        organizationCell.setCellStyle(textStyle);
        colIndex++;
        }
        if(!(AppUtil.isEmptyString(courseCompletionDate)))
        {
        HSSFCell courseCompletionDateCell = row5.createCell((short) colIndex);
        courseCompletionDateCell.setCellValue(courseCompletionDate);
        courseCompletionDateCell.setCellStyle(textStyle);
        colIndex++;
        }
        if(!(AppUtil.isEmptyString(jtGrpDesc)))
        {
        HSSFCell jtGrpDescCell = row5.createCell((short) colIndex);
        jtGrpDescCell.setCellValue(jtGrpDesc);
        jtGrpDescCell.setCellStyle(textStyle);
        colIndex++;
        }
        if(!(AppUtil.isEmptyString(title)))
        {
        HSSFCell titleCell = row5.createCell((short) colIndex);
        titleCell.setCellValue(title);
        titleCell.setCellStyle(textStyle);
        colIndex++;
        }
        if(!(AppUtil.isEmptyString(potentialTiming)))
        {    
        HSSFCell potentialTimingCell = row5.createCell((short) colIndex);
        potentialTimingCell.setCellValue(potentialTiming);
        potentialTimingCell.setCellStyle(textStyle);
        colIndex++;
        }
        if(!(AppUtil.isEmptyString(mobilityLocation)))
        {     
        HSSFCell mobilityLocationCell = row5.createCell((short) colIndex);
        mobilityLocationCell.setCellValue(mobilityLocation);
        mobilityLocationCell.setCellStyle(textStyle);
        colIndex++;
        }
        if(!(AppUtil.isEmptyString(relocationTypeDesc)))
        {     
        HSSFCell relocationTypeDescCell = row5.createCell((short) colIndex);
        relocationTypeDescCell.setCellValue(relocationTypeDesc);
        relocationTypeDescCell.setCellStyle(textStyle);
        colIndex++;
        }
        if(!(AppUtil.isEmptyString(language)))
        {
        HSSFCell languageCell = row5.createCell((short) colIndex);
        languageCell.setCellValue(language);
        languageCell.setCellStyle(textStyle);
        colIndex++;
        }
        if(!(AppUtil.isEmptyString(departmentExperience)))
        {
        HSSFCell departmentExperieneCell = row5.createCell((short) colIndex);
        departmentExperieneCell.setCellValue(departmentExperience);
        departmentExperieneCell.setCellStyle(textStyle);
        colIndex++;
        }
        
        if(!(AppUtil.isEmptyString(experienceYrs)))
        {
        HSSFCell experienceYrsCell = row5.createCell((short) colIndex);
        experienceYrsCell.setCellValue(experienceYrs);
        experienceYrsCell.setCellStyle(textStyle);
        colIndex++;
        }
        
    }
	
	public static byte[] perfmDevSummRpt(String rptId,HttpServletResponse response) throws HrReviewException {
		byte content[]=null;
		try {

			PerfmDevSumLabel perfmDevSum = new PerfmDevSumLabel();

			String xsltfile = ApplicationAids
					.getRunTimeProperty("PerfmDevXslFilename");
			String pdffile = ApplicationAids
					.getRunTimeProperty("PerfmDevPdfFilename")+ rptId + PDF_EXTENSION;
            if(mLogger.isDebugEnabled())
			mLogger.debug("ConvertToPDF:perfmDevSummRpt() - Start To Generate PDF - Transforming...");

			content=convertPerfmDevSumRpt2PDF(perfmDevSumRptXml(perfmDevSum),
					xsltfile, pdffile,response);
			if(mLogger.isDebugEnabled())
			mLogger.debug("ConvertToPDF:perfmDevSummRpt() - Generated PDF Successfully");

		} 
		catch (Exception e) {
			
			mLogger.error(HrReviewApplLogMessage.create(HrReviewApplLogMessage.FATAL_ERROR,
   					Constants.ERROR_GENERATE_PERFORMANCESUMMARY,e));
   			   throw new HrReviewException(Constants.BAD_REQUEST,Constants.ERROR_GENERATE_PERFORMANCESUMMARY, e);
			
		}
		return content;
	}
	
	public static byte[] convertPerfmDevSumRpt2PDF(PerfmDevSumLabel perfmDevSum,
			String xslt, String pdf,HttpServletResponse response) throws IOException, FOPException,
			TransformerException {

		// Construct driver
		Driver driver = new Driver();
		byte[] content=null;

		org.apache.avalon.framework.logger.Logger logger = new ConsoleLogger(ConsoleLogger.LEVEL_INFO);
		driver.setLogger(logger);
		MessageHandler.setScreenLogger(logger);

		// Setup Renderer (output format)
		driver.setRenderer(Driver.RENDER_PDF);

		// Setup output
		//OutputStream out = new java.io.FileOutputStream(pdf);
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		try {
			driver.setOutputStream(outStream);

			// Setup XSLT
			TransformerFactory factory = TransformerFactory.newInstance();
			
            // Setup input for XSLT transformation
			Source src = perfmDevSum.getSourceForPerfmHeadLabel();
			//String xml="<PerfmDevSumRpt><HomeDepot></HomeDepot><DevSummary>Test Summary</DevSummary><Name>MyName</Name><AssociateName>TestAssociateName</AssociateName><BusinessUnit></BusinessUnit><BusUnit></BusUnit><Title></Title><JobTitle>SALES MGR</JobTitle><asAppears></asAppears><LabelType></LabelType><ReportDesc></ReportDesc><DocumentId></DocumentId><DocumentIdNo></DocumentIdNo><ReportCodeSummary></ReportCodeSummary><PageFooter></PageFooter><Printedon></Printedon><PrintDate></PrintDate><LeadSummRpt></LeadSummRpt><KeyStrength></KeyStrength><keyStrenRpt></keyStrenRpt><DevNeed></DevNeed><keyDevNeed></keyDevNeed><DevPlan></DevPlan><DevTrain></DevTrain><NextPos></NextPos><HourStore></HourStore><Position></Position><PotentialTime></PotentialTime><PositionName></PositionName><PotentialTiming></PotentialTiming><PosComment></PosComment><PositionComment></PositionComment><AssociateComment>Store associate</AssociateComment><AttachSheet></AttachSheet><AssociateCommentRpt></AssociateCommentRpt><ReportContinue></ReportContinue><PerfmCode></PerfmCode><TopPerformer></TopPerformer><valuedAssociate></valuedAssociate><Unaccept></Unaccept><PotentialCode></PotentialCode><Promotable></Promotable><WellPosition></WellPosition><NotApplicable> </NotApplicable><SummaryApproval></SummaryApproval><LeaderDate></LeaderDate><AssociateDate></AssociateDate></PerfmDevSumRpt>";
			//Source src =new StreamSource(new StringReader(xml));
			ClassLoader classLoader = FOPReport.class.getClassLoader();
	        File xslFileName = new File(classLoader.getResource("com/homedepot/hr/gd/hrreview/resources/perfmDevSumRpt.xsl").getFile());
	        Source xsltSrc = new StreamSource(xslFileName);
			// Resulting SAX events (the generated FO) must be piped through to
			Transformer transformer = TransformerFactory.newInstance().newTransformer(xsltSrc);
			// FOP
			Result res = new SAXResult(driver.getContentHandler());

			// Start XSLT transformation and FOP processing
			transformer.transform(src, res);
			
			content = outStream.toByteArray();

		} finally {
			//out.close();
		}
		return content;
	}
	
	private static PerfmDevSumLabel perfmDevSumRptXml(
			PerfmDevSumLabel perfmDevSum) {

		perfmDevSum
				.setAsAppear(ApplicationAids
						.getRunTimeProperty("txt.lbl.report.perfdev.summary.as.appears"));
		perfmDevSum
				.setAssoComment(ApplicationAids
						.getRunTimeProperty("txt.lbl.report.perfdev.summary.associate.comments"));
		perfmDevSum
				.setAssoDate(ApplicationAids
						.getRunTimeProperty("txt.lbl.report.perfdev.summary.associate.date"));
		perfmDevSum
				.setAttachSheet(ApplicationAids
						.getRunTimeProperty("txt.lbl.report.perfdev.summary.attach.sheets"));
		perfmDevSum.setBusUnit(ApplicationAids
				.getRunTimeProperty("txt.lbl.report.perfdev.summary.bus.unit"));
		perfmDevSum
				.setDevNeed(ApplicationAids
						.getRunTimeProperty("txt.lbl.report.perfdev.summary.key.dev.needs"));
		perfmDevSum
				.setDevPlan(ApplicationAids
						.getRunTimeProperty("txt.lbl.report.perfdev.summary.dev.plans"));
		perfmDevSum.setDevSummary(ApplicationAids
				.getRunTimeProperty("txt.lbl.report.perfdev.summary"));
		perfmDevSum
				.setHomeDepot(ApplicationAids
						.getRunTimeProperty("txt.lbl.report.perfdev.summary.home.depot"));
		perfmDevSum
				.setHourAssociate(ApplicationAids
						.getRunTimeProperty("txt.lbl.report.perfdev.summary.for.hourly.assoc"));
		perfmDevSum
				.setHourStore(ApplicationAids
						.getRunTimeProperty("txt.lbl.report.perfdev.summary.for.hourly.store"));
		perfmDevSum
				.setKeyStrength(ApplicationAids
						.getRunTimeProperty("txt.lbl.report.perfdev.summary.key.strengths"));
		perfmDevSum
				.setLeadCode(ApplicationAids
						.getRunTimeProperty("txt.lbl.report.perfdev.summary.leadership.code"));
		perfmDevSum
				.setLeaderDate(ApplicationAids
						.getRunTimeProperty("txt.lbl.report.perfdev.summary.leader.date"));
		perfmDevSum
				.setLeaderSummary(ApplicationAids
						.getRunTimeProperty("txt.lbl.report.perfdev.summary.leaders.summary"));
		perfmDevSum.setName(ApplicationAids.getRunTimeProperty("txt.lbl.name"));
		perfmDevSum
				.setNextPos(ApplicationAids
						.getRunTimeProperty("txt.lbl.report.perfdev.summary.pot.next.pos"));
		perfmDevSum
				.setPerfCode(ApplicationAids
						.getRunTimeProperty("txt.lbl.report.perfdev.summary.perf.code"));
		perfmDevSum
				.setPosComment(ApplicationAids
						.getRunTimeProperty("txt.lbl.report.perfdev.summary.position.comments"));
		perfmDevSum.setPosition(ApplicationAids
				.getRunTimeProperty("txt.lbl.position"));
		perfmDevSum
				.setPotenCode(ApplicationAids
						.getRunTimeProperty("txt.lbl.report.perfdev.summary.potential.code"));
		perfmDevSum.setPotenTime(ApplicationAids
				.getRunTimeProperty("txt.lbl.potential.timing"));
		perfmDevSum.setRepContinue(ApplicationAids
				.getRunTimeProperty("txt.lbl.reports.continued"));
		perfmDevSum.setSummApproval(ApplicationAids
				.getRunTimeProperty("txt.lbl.report.perfdev.summary.approval"));
		perfmDevSum.setTitle(ApplicationAids
				.getRunTimeProperty("txt.lbl.title"));

		perfmDevSum.setDocumentId(ApplicationAids
				.getRunTimeProperty("txt.lbl.document.id"));
		perfmDevSum.setLabelType(ApplicationAids
				.getRunTimeProperty("txt.lbl.type"));
		perfmDevSum.setPageFooter(ApplicationAids
				.getRunTimeProperty("msg.report.page.footer"));
		perfmDevSum.setPrintedOnText(ApplicationAids
				.getRunTimeProperty("txt.lbl.printed.on"));
		perfmDevSum.setReportCodeSummary(ApplicationAids
				.getRunTimeProperty("RPT_CD_PFMDV_SUMMARY"));

		perfmDevSum.setAssociateName(perfmDev1.getAssociateName());
		perfmDevSum.setBusinessUnit(perfmDev1.getBusinessUnit());
		perfmDevSum.setJobTitle(perfmDev1.getJobTitle());
		perfmDevSum.setPrintDate(perfmDev1.getPrintDate());

		perfmDevSum.setPositionName(perfmDev1.getJobTitle());
 		perfmDevSum.setPotentialTiming(perfmDev1.getPotentialTiming());

		perfmDevSum.setNotaAppl(ApplicationAids
				.getRunTimeProperty("txt.lbl.reports.perfdev.not.appli"));
		perfmDevSum
				.setTopPerfm(ApplicationAids
						.getRunTimeProperty("txt.lbl.reports.perfdev.summary.top.perform"));
		perfmDevSum.setValuedAsso(ApplicationAids
				.getRunTimeProperty("txt.lbl.reports.perfdev.value.associate"));
		perfmDevSum.setUnaccept(ApplicationAids
				.getRunTimeProperty("txt.lbl.reports.perfdev.unaccept.perfm"));
		perfmDevSum.setWellPosition(ApplicationAids
				.getRunTimeProperty("txt.lbl.reports.perfdev.well.positiom"));
		perfmDevSum.setPromote(ApplicationAids
				.getRunTimeProperty("txt.lbl.reports.perfdev.promote"));
		
		perfmDevSum.setLeadSummaryRpt(perfmDev1.getLeadSummaryRpt());
		perfmDevSum.setKeyStrengthRpt(perfmDev1.getKeyStrengthRpt());
		perfmDevSum.setKeyDevPlanRpt(perfmDev1.getKeyDevPlanRpt());
		perfmDevSum.setDevTrainRpt(perfmDev1.getDevTrainRpt());
		perfmDevSum.setAssociateComment(perfmDev1.getAssociateComment());
		perfmDevSum.setPositionComment(perfmDev1.getPositionComment());
		perfmDevSum.setDocumentIdNo(perfmDev1.getDocumentIdNo());
		perfmDevSum.setReportDesc(perfmDev1.getReportDesc());
		
        perfmDevSum.setTopPerfmCode(perfmDev1.getTopPerfmCode());
		perfmDevSum.setValuedAssoCode(perfmDev1.getValuedAssoCode());
		perfmDevSum.setUnacceptCode(perfmDev1.getUnacceptCode());

		perfmDevSum.setPromoteCode(perfmDev1.getPromoteCode());
		perfmDevSum.setWellPositionCode(perfmDev1.getWellPositionCode());
		perfmDevSum.setNotaApplCode(perfmDev1.getNotaApplCode());

		perfmDevSum.setPerfmRateCode(perfmDev1.getPerfmRateCode());
		perfmDevSum.setPotenRateCode(perfmDev1.getPotenRateCode());

		return perfmDevSum;
	}
	
	public static void perfmRateCode(PerformanceReportDTO performanceDTO) {
		PerformanceReportDTO performanceReportDTO=performanceDTO;
		Integer hh= new Integer(28);
		HashMap hashMap = new HashMap();
		hashMap.put("O", new Integer(28));
		hashMap.put("V", new Integer(29));
		hashMap.put("I", new Integer(30));

		perfmDev1.setTopPerfmCode(hashMap.get("O").toString());
		perfmDev1.setValuedAssoCode(hashMap.get("V").toString());
		perfmDev1.setUnacceptCode(hashMap.get("I").toString());

		if ((AppUtil.isEmptyString(performanceReportDTO.getPerfEvalRtgCode()))) {

			if(mLogger.isDebugEnabled())
			mLogger.debug("ConvertToPDF:perfmRateCode()      - No Performance Ratings for the Associate ");


		} else {
			perfmDev1.setPerfmRateCode(hashMap
					.get(performanceReportDTO.getPerfEvalRtgCode()).toString());

			if ((perfmDev1.getPerfmRateCode().equalsIgnoreCase(hashMap.get("O")
					.toString()))
					|| (perfmDev1.getPerfmRateCode().equalsIgnoreCase(hashMap
							.get("V").toString()))
					|| (perfmDev1.getPerfmRateCode().equalsIgnoreCase(hashMap
							.get("I").toString()))) {
				if(mLogger.isDebugEnabled())
				{
				mLogger.debug("ConvertToPDF:perfmRateCode()  - Value for Performance Rating Code "+ performanceReportDTO.getPerfEvalRtgCode().toString());
				mLogger.debug("ConvertToPDF:perfmRateCode()  - HashMap Value for Performance Rating Code "
								+ hashMap
								.get(performanceReportDTO.getPerfEvalRtgCode()).toString());
				}
			}

			else {
				if(mLogger.isDebugEnabled())
				mLogger.debug("ConvertToPDF:perfmRateCode()      - No Performance Ratings for the Associate ");
			}

		}

	}
	
	public static void perfmDevSumProfileInfo(Vector vect) {
		perfmDev1.setLeadSummaryRpt(vect.elementAt(1).toString());
		perfmDev1.setKeyStrengthRpt(vect.elementAt(2).toString());
		perfmDev1.setKeyDevPlanRpt(vect.elementAt(3).toString());
		perfmDev1.setDevTrainRpt(vect.elementAt(4).toString());
		perfmDev1.setAssociateComment(vect.elementAt(5).toString());
		perfmDev1.setPositionComment(vect.elementAt(6).toString());
		perfmDev1.setDocumentIdNo(vect.elementAt(7).toString());
		perfmDev1.setReportDesc(vect.elementAt(8).toString());

	}

	public static void potenRateCode(PerformanceReportDTO performanceDTO) {
		PerformanceReportDTO performanceReportDTO=performanceDTO;
		HashMap hashMap = new HashMap();
		hashMap.put("1", new Integer(31));
		hashMap.put("2", new Integer(32));
		hashMap.put("9", new Integer(33));

		perfmDev1.setPromoteCode(hashMap.get("1").toString());
		perfmDev1.setWellPositionCode(hashMap.get("2").toString());
		perfmDev1.setNotaApplCode(hashMap.get("9").toString());

		if (AppUtil.isEmptyString(performanceReportDTO.getPotentialEvalRtgCode())) {

			if(mLogger.isDebugEnabled())
			mLogger.debug("ConvertToPDF:potenRateCode()      - No Potential Ratings for the Associate ");

			
        } else {
			perfmDev1.setPotenRateCode(hashMap
					.get(performanceReportDTO.getPotentialEvalRtgCode()).toString());

			if ((perfmDev1.getPotenRateCode().equalsIgnoreCase(hashMap.get("1")
					.toString()))
					|| (perfmDev1.getPotenRateCode().equalsIgnoreCase(hashMap
							.get("2").toString()))
					|| (perfmDev1.getPotenRateCode().equalsIgnoreCase(hashMap
							.get("3").toString()))) {
				
				if(mLogger.isDebugEnabled())
				{
				mLogger.debug("ConvertToPDF:potenRateCode()  - Value for Potential Rating Code"
								+ performanceReportDTO.getPotentialEvalRtgCode());
				mLogger.debug("ConvertToPDF:potenRateCode()  - HashMap Value for Potential Rating Code"
								+ hashMap.get(performanceReportDTO.getPotentialEvalRtgCode())
										.toString());
				}
			} else {
				mLogger.debug("ConvertToPDF:potenRateCode()      - No Potential Ratings for the Associate ");

			}
		}

	}
	
	public static String getPerformanceCodeDesc(String pfcode)
	{
		String perfCodeDesc=null;
		HashMap<String,String> mapPerfCode= new HashMap<String,String>();
		mapPerfCode.put("1", "(I) Imp. Required");
		mapPerfCode.put("2", "(I) Imp. Required");
		mapPerfCode.put("3", "(P) Performer");
		mapPerfCode.put("4", "(V) Achiever");
		mapPerfCode.put("5", "(O) Outstanding");
		if(mapPerfCode!=null && pfcode!=null && mapPerfCode.get(pfcode.trim())!=null)
		{
		perfCodeDesc=mapPerfCode.get(pfcode.trim()).substring(1,2);
		}
		return perfCodeDesc;
	}
	
}

package com.homedepot.hr.gd.hrreview.bl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;

import com.homedepot.hr.gd.hrreview.dto.IndustryDetailsDTO;
import com.homedepot.hr.gd.hrreview.dao.DataDAO;
import com.homedepot.hr.gd.hrreview.dao.PersonalDAO;
import com.homedepot.hr.gd.hrreview.dto.BasicInformationDTO;
import com.homedepot.hr.gd.hrreview.dto.CityInfoDTO;
import com.homedepot.hr.gd.hrreview.dto.CourseDTO;
import com.homedepot.hr.gd.hrreview.dto.DegreeSearchDTO;
import com.homedepot.hr.gd.hrreview.dto.HomeAddressDTO;
import com.homedepot.hr.gd.hrreview.dto.LanguageDTO;
import com.homedepot.hr.gd.hrreview.dto.LanguageDetailsDTO;
import com.homedepot.hr.gd.hrreview.dto.LanguageProficiencyDTO;
import com.homedepot.hr.gd.hrreview.dto.LanguageSearchDTO;
import com.homedepot.hr.gd.hrreview.dto.LanguageTypeDTO;
import com.homedepot.hr.gd.hrreview.dto.MajorsSearchDTO;
import com.homedepot.hr.gd.hrreview.dto.SchoolDTO;
import com.homedepot.hr.gd.hrreview.dto.SchoolType;
import com.homedepot.hr.gd.hrreview.dto.WorkAddressDTO;
import com.homedepot.hr.gd.hrreview.dto.WorkHistoryExternalDTO;
import com.homedepot.hr.gd.hrreview.dto.WorkHistoryHomeDepotDTO;
import com.homedepot.hr.gd.hrreview.dto.WorkHistoryHomeDepotPre95DTO;
import com.homedepot.hr.gd.hrreview.exceptions.HrReviewApplLogMessage;
import com.homedepot.hr.gd.hrreview.exceptions.HrReviewException;
import com.homedepot.hr.gd.hrreview.interfaces.Constants;
import com.homedepot.hr.gd.hrreview.response.AddressResponse;
import com.homedepot.hr.gd.hrreview.response.BasicInformationResponse;
import com.homedepot.hr.gd.hrreview.response.EducationResponse;
import com.homedepot.hr.gd.hrreview.response.LanguageResponse;
import com.homedepot.hr.gd.hrreview.response.WorkHistoryResponse;
import com.homedepot.hr.gd.hrreview.util.AppUtil;
import com.homedepot.ta.aa.dao.exceptions.QueryException;

public class PersonalManager implements Constants {

	private static final Logger mLogger = Logger
			.getLogger(PersonalManager.class);
	
	//Get the basic information
	public static BasicInformationResponse getBasicInformation(
			String associateId) throws HrReviewException {

		long startTime = 0;
		BasicInformationResponse basicRes = null;
		List<BasicInformationDTO> basicInformationDTO = null;

		if (mLogger.isDebugEnabled()) {
			startTime = System.nanoTime();
		}

		try {
			basicInformationDTO = (List<BasicInformationDTO>) PersonalDAO.getBasicInformation(associateId);
			basicRes = new BasicInformationResponse();
			basicRes.setSuccess(true);
			
			if (basicInformationDTO != null && basicInformationDTO.size() >   0) {				
				basicRes.setBasicInformationDTO(basicInformationDTO.get(0));
				
				// Gender				
				if (basicRes != null && basicRes.getBasicInformationDTO() != null && basicRes.getBasicInformationDTO().getSexTypeCode() != null && 
					basicRes.getBasicInformationDTO().getSexTypeCode().equalsIgnoreCase("M")) {
					basicRes.getBasicInformationDTO().setSexTypeCode("Male");
				} else if (basicRes != null && basicRes.getBasicInformationDTO() != null && basicRes.getBasicInformationDTO().getSexTypeCode() != null && 
					basicRes.getBasicInformationDTO().getSexTypeCode().equalsIgnoreCase("F")) {
					basicRes.getBasicInformationDTO().setSexTypeCode("Female");
				}
				
				// Age
				if (basicRes != null && basicRes.getBasicInformationDTO() != null && basicRes.getBasicInformationDTO().getBirthDate() != null) {
					Date associateBirthDate = basicRes.getBasicInformationDTO().getBirthDate();
					Date currentDate = new Date(System.currentTimeMillis());
					basicRes.getBasicInformationDTO().setAge(AppUtil.getDuration(associateBirthDate, currentDate));
				}
				
				// Marital Status
				if (basicRes != null && basicRes.getBasicInformationDTO() != null && basicRes.getBasicInformationDTO().getMaritalStatusCode() != null) {
					if(basicRes.getBasicInformationDTO().getMaritalStatusCode().equalsIgnoreCase("M"))
					{
						basicRes.getBasicInformationDTO().setMaritalStatusCode("Married");
					}
					else if(basicRes.getBasicInformationDTO().getMaritalStatusCode().equalsIgnoreCase("S"))
					{
						basicRes.getBasicInformationDTO().setMaritalStatusCode("Single");
					}
					else
					{
						basicRes.getBasicInformationDTO().setMaritalStatusCode("Not known");
					}
				}
				
				// set date in current position
				WorkHistoryHomeDepotDTO workDTO = getDateInCurrentPosition(associateId);
				if (workDTO != null) {
					basicRes.getBasicInformationDTO().setDateInCurrPosAndLoc(workDTO.getRelationEndDate());
					basicRes.getBasicInformationDTO().setDateInCurrentTitle(workDTO.getEffectiveDate());
				}
			}

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
					.format("Exiting getBasicInformation(). Total time to process request: %.4f seconds",
							(((float) endTime - startTime) / NANOS_IN_SECOND)));
		}
		return basicRes;
	}

	//Get home address, work address, and phone/fax
	public static AddressResponse getAddressDetails(String associateId)
			throws HrReviewException {

		long startTime = 0;
		AddressResponse addressRes = null;
		List<HomeAddressDTO> homeAddressDTO = new ArrayList<HomeAddressDTO>();
		List<CityInfoDTO> cityInfoDTO = null;		

		HomeAddressDTO homeAddress = null;
		WorkAddressDTO workAddress = null;

		if (mLogger.isDebugEnabled()) {
			startTime = System.nanoTime();
		}

		//Remove empty values for address
		try {
			homeAddressDTO = (List<HomeAddressDTO>) PersonalDAO.getHomeAddressDetails(associateId);
			if(!homeAddressDTO.isEmpty())
			{
				for(int i = homeAddressDTO.size()-1; i >=0; i--)
				{
					if(!homeAddressDTO.get(i).getAddressLineOneLong().trim().equals(""))
					{
						homeAddress = homeAddressDTO.get(i);
						break;
					}
				}
			}
			if(homeAddress == null)
				homeAddress = new HomeAddressDTO();
			//homeAddressDTO = (List<HomeAddressDTO>) PersonalDAO.getHomeAddressDetails(associateId);
			workAddress =  PersonalDAO
					.getWorkAddressDetails(associateId);
			
//			if(workAddressDTO != null && workAddressDTO.size() > 0)
//				workAddress = workAddressDTO.get(0);
//			if(workAddress == null)
//				workAddress = new WorkAddressDTO();
			
			cityInfoDTO = (List<CityInfoDTO>) PersonalDAO
					.getCityInfoDetails(associateId);
			
			//Set fields from cityInfoDTO
			for(int i = 0; i < cityInfoDTO.size(); i++)
			{
				if(cityInfoDTO.get(i) != null )
				{
					//Home Cell
					if(cityInfoDTO.get(i).getContactMethodCode() == (short) 2)
					{
						homeAddress.setAreaCityCode(cityInfoDTO.get(i).getPhoneAreaCityCode());
						homeAddress.setPhoneNumber(cityInfoDTO.get(i).getPhoneLocalNumber());
					}
					//Home
					else if(cityInfoDTO.get(i).getContactMethodCode() == (short) 4)
					{
						homeAddress.setFaxAreaCityCode(cityInfoDTO.get(i).getPhoneAreaCityCode());
						homeAddress.setFaxNumber(cityInfoDTO.get(i).getPhoneLocalNumber());
					}
					//Work Email
					else if(cityInfoDTO.get(i).getContactMethodCode() == (short) 5)
					{
						workAddress.setEmail(cityInfoDTO.get(i).getEmailAddressText());
					}
					//Work Extension
					else if(cityInfoDTO.get(i).getContactMethodCode() == (short) 1)
					{
						workAddress.setPhoneExtension(cityInfoDTO.get(i).getPhoneExtensionNumber());
					}
				}
			}
			
			addressRes = new AddressResponse();
			addressRes.setSucess(true);
			addressRes.setHomeAddressDTO(homeAddress);
			addressRes.setWorkAddressDTO(workAddress);
			//addressRes.setCityInfoDTO(cityInfoDTO);

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
					.format("Exiting getAddressDetails(). Total time to process request: %.4f seconds",
							(((float) endTime - startTime) / NANOS_IN_SECOND)));
		}
		return addressRes;
	}

	//School and Course Details
	public static EducationResponse getSchoolAndCourseDetails(String associateId)
			throws HrReviewException {

		long startTime = 0;
		EducationResponse educationRes = null;

		if (mLogger.isDebugEnabled()) {
			startTime = System.nanoTime();
		}

		try {
			List<SchoolDTO> schoolList = (List<SchoolDTO>) PersonalDAO.getSchoolDetails(associateId);
			
			// School Type
			List<SchoolType> schoolTypeDetails = (List<SchoolType>) DataDAO.getSchoolTypes();
			Hashtable<String,String> schoolTypeList = new Hashtable<String,String>();
			for (SchoolType sclType : schoolTypeDetails) {
				String schTypecd = String.valueOf(sclType.getSchoolTypeCode());
				if (!schoolTypeList.contains(schTypecd))
					schoolTypeList.put(schTypecd, sclType.getSchoolTypeDescription());
			}
			
			// Degree Type
			List<DegreeSearchDTO> degreeDetails = (List<DegreeSearchDTO>) DataDAO.getDegreeDetails();
			Hashtable<String,String> degreeDetailsList = new Hashtable<String,String>();
			for (DegreeSearchDTO degDetails : degreeDetails) {
				if (!degreeDetailsList.contains(degDetails.getDegreeTypeCode()))
					degreeDetailsList.put(degDetails.getDegreeTypeCode(), degDetails.getDegreeTypeDescription());
			}
			
			//Major Type Details
			List<MajorsSearchDTO> majorDetails = (List<MajorsSearchDTO>) DataDAO.getMajorsDetails();
			Hashtable<String,String> majorDetailsList = new Hashtable<String,String>();
			for (MajorsSearchDTO mjrDetails : majorDetails) {
				if (!majorDetailsList.contains(mjrDetails.getCollegeMajorCode()))
					majorDetailsList.put(mjrDetails.getCollegeMajorCode(), mjrDetails.getCollegeMajorDescription());
			}
			
			// loop thru associate school details and update school type code, degree code and major code to decsriptions
			for (SchoolDTO school : schoolList) {
				
				// Retrieve the school type description
				if (school.getOverrideSchoolTypeCode() != null && schoolTypeList.containsKey(school.getOverrideSchoolTypeCode()))
					school.setOverrideSchoolName(schoolTypeList.get(school.getOverrideSchoolTypeCode()));
				
				// Retrieve the degree type description
				if (school.getDegreeTypeCode() != null && degreeDetailsList.containsKey(school.getDegreeTypeCode()))
					school.setOverrideDegreeDescription(degreeDetailsList.get(school.getDegreeTypeCode()));
					
				// Retrieve the major type description
				if (school.getCollegeMajorCode() != null && majorDetailsList.containsKey(school.getCollegeMajorCode()))
					school.setOverrideCollegeMajorDescription(majorDetailsList.get(school.getCollegeMajorCode()));
				
			}
			
			List<CourseDTO> courseList = (List<CourseDTO>) PersonalDAO.getCourseDetails(associateId);
			educationRes = new EducationResponse();
			educationRes.setSuccess(true);
			educationRes.setEducationDTO(schoolList);
			educationRes.setCourseDTO(courseList);

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
					.format("Exiting getSchoolAndCourseDetails(). Total time to process request: %.4f seconds",
							(((float) endTime - startTime) / NANOS_IN_SECOND)));
		}
		return educationRes;
	}

	//Language
	public static LanguageResponse getLanguageDetails(String associateId)
			throws HrReviewException {

		long startTime = 0;
		LanguageResponse languageRes = null;
		Hashtable<String, LanguageDetailsDTO> languageDetailsDTOList = new Hashtable<String, LanguageDetailsDTO>();

		if (mLogger.isDebugEnabled()) {
			startTime = System.nanoTime();
		}

		try {
			List<LanguageDTO> languageDTO = (List<LanguageDTO>) PersonalDAO.getLanguageDetails(associateId);
			
			List<LanguageSearchDTO> languageName = (List<LanguageSearchDTO>)DataDAO.getLanguageDetails();
			Hashtable<String,String> languageNameList = new Hashtable<String,String>();
			for (LanguageSearchDTO lname : languageName) {
				if (!languageNameList.contains(lname.getInternationalOrganizationForStandardsLanguageCode().trim()))
					languageNameList.put(lname.getInternationalOrganizationForStandardsLanguageCode().trim(), lname.getInternationalOrganizationForStandardsLanguageName());
			}
			
			List<LanguageTypeDTO> languageType = (List<LanguageTypeDTO>)DataDAO.getLanguageType();
			Hashtable<Short,String> languageTypeList = new Hashtable<Short,String>();
			for (LanguageTypeDTO lType : languageType) {
				if (!languageTypeList.contains(lType.getLanguageUseTypeCode()))
					languageTypeList.put(lType.getLanguageUseTypeCode(), lType.getLanguageUseTypeDescription());
			}
			
			List<LanguageProficiencyDTO> LanguageProficiency = (List<LanguageProficiencyDTO>)DataDAO.getLanguageProficiency();
			Hashtable<String,String> languageProficiencyList = new Hashtable<String,String>();
			for (LanguageProficiencyDTO lProficiency : LanguageProficiency) {
				if (!languageProficiencyList.contains(lProficiency.getLanguageProficiencyCode()))
					languageProficiencyList.put(lProficiency.getLanguageProficiencyCode(), lProficiency.getLanguageProficiencyDescription());
			}
			
			LanguageDetailsDTO lDetails = null;
			for (LanguageDTO langDTO : languageDTO) {
				
				if (!languageDetailsDTOList.containsKey(langDTO.getInternationalOrganizationForStandardsLanguageCode().trim())) {
					lDetails = new LanguageDetailsDTO();
					lDetails.setLanguageName(languageNameList.get(langDTO.getInternationalOrganizationForStandardsLanguageCode().trim()));
					lDetails.setPreferenceLanguageFlag(langDTO.getPreferenceLanguageFlag());					
					lDetails.setZeroEmployeeId(langDTO.getZeroEmployeeId());
					languageDetailsDTOList.put(langDTO.getInternationalOrganizationForStandardsLanguageCode().trim(), lDetails);
				} else {
					lDetails = languageDetailsDTOList.get(langDTO.getInternationalOrganizationForStandardsLanguageCode().trim());					
				}

				if (languageTypeList.containsKey(langDTO.getLanguageUseTypeCode()) && langDTO.getLanguageUseTypeCode() == 100) {
					lDetails.setRead(languageProficiencyList.get(langDTO.getLanguageProficiencyCode()));
				} else if (languageTypeList.containsKey(langDTO.getLanguageUseTypeCode()) && langDTO.getLanguageUseTypeCode() == 200) {
					lDetails.setWrite(languageProficiencyList.get(langDTO.getLanguageProficiencyCode()));
				} else if (languageTypeList.containsKey(langDTO.getLanguageUseTypeCode()) && langDTO.getLanguageUseTypeCode() == 300) {
					lDetails.setSpeak(languageProficiencyList.get(langDTO.getLanguageProficiencyCode()));
				}
			}
			
			languageRes = new LanguageResponse();
			languageRes.setSuccess(true);
			List<LanguageDetailsDTO> dtoList = (new ArrayList<LanguageDetailsDTO>(languageDetailsDTOList.values()));
			languageRes.setLanguageDetailsDTO(dtoList);

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
					.format("Exiting getLanguageDetails(). Total time to process request: %.4f seconds",
							(((float) endTime - startTime) / NANOS_IN_SECOND)));
		}
		return languageRes;
	}

	//Work History External, Home Depot, Home Depot pre 1995
	public static WorkHistoryResponse getWorkHistoryDetails(String associateId)
			throws HrReviewException {
		long startTime = 0;
		WorkHistoryResponse workHistoryRes = null;
		List<WorkHistoryHomeDepotDTO> workHistoryDTOHomeDepot = null;
		List<WorkHistoryHomeDepotPre95DTO> workHistoryDTOHomeDepotPre95 = null;
		List<WorkHistoryExternalDTO> workHistoryDTOExternal = null;
		List<IndustryDetailsDTO> industryDetailsDTO = null;
		
		if (mLogger.isDebugEnabled()) {
			startTime = System.nanoTime();
		}

		try {
			workHistoryDTOHomeDepot = (List<WorkHistoryHomeDepotDTO> ) PersonalDAO.getWorkHistoryDetailsHomeDepot(associateId);			
			workHistoryDTOHomeDepotPre95 = (List<WorkHistoryHomeDepotPre95DTO>) PersonalDAO.getWorkHistoryDetailsHomeDepotPre95(associateId);
			workHistoryDTOExternal = (List<WorkHistoryExternalDTO> ) PersonalDAO.getWorkHistoryDetailsExternal(associateId);
			industryDetailsDTO = (List<IndustryDetailsDTO> ) DataDAO.getIndustryTypeDetails();
			
			Hashtable<Short, String> industryDetails = new Hashtable<Short, String>();
			for (IndustryDetailsDTO iDetails: industryDetailsDTO) {
				if (!industryDetails.containsKey(iDetails.getSuccessionPlanIndustryTypeCode())) {
					industryDetails.put(iDetails.getSuccessionPlanIndustryTypeCode(), iDetails.getSuccessionPlanIndustryTypeDescription().trim());
				}
			}
			
			workHistoryRes = new WorkHistoryResponse();
			workHistoryRes.setSuccess(true);
			
			//Sort the list
			Collections.sort(workHistoryDTOHomeDepot, new Comparator<WorkHistoryHomeDepotDTO>() {
				  public int compare(WorkHistoryHomeDepotDTO o1, WorkHistoryHomeDepotDTO o2) {
				      if (o1.getEffectiveDate() == null || o2.getEffectiveDate() == null)
				        return 0;
				      if(o1.getEffectiveDate().equals(o2.getEffectiveDate()))
		    		  {
				    	  if(o1.getOrganizationOne().equals(o2.getOrganizationOne()))
				    	  {
					    	  if(o1.getRelationEndDate().before(o2.getRelationEndDate()))
					    		  return 1;
					    	  else if(o1.getRelationEndDate().after(o2.getRelationEndDate()))
					    		  return -1;
					    	  else
					    		  return 0;
				    	  }
		    		  }
				      else if(o1.getEffectiveDate().before(o2.getEffectiveDate()))
		    		  {
				    		  return 1;
		    		  }
				      else if(o1.getEffectiveDate().after(o2.getEffectiveDate()))
		    		  {
				    		  return -1;
		    		  }
				      return 0;
				  }  
				});
			
			WorkHistoryHomeDepotDTO prev = null;
			if(workHistoryDTOHomeDepot.size() > 0)
				prev = workHistoryDTOHomeDepot.get(0);
			List<WorkHistoryHomeDepotDTO> revisedList = new ArrayList<WorkHistoryHomeDepotDTO>();
			for(int i = 1; i < workHistoryDTOHomeDepot.size(); i++)
			{
				//Compare the previous job title to the current one get(i). If different, add the prev to the list
				if(!prev.getJobTitleDescription().trim().toUpperCase().equals(workHistoryDTOHomeDepot.get(i).getJobTitleDescription().trim().toUpperCase()) )
				{
					revisedList.add(prev);
					prev = workHistoryDTOHomeDepot.get(i);
				}
				//Compare the previous store number to the current one get(i). If different, add the prev to the list
				else if(!prev.getOrganizationOne().trim().toUpperCase().equals(workHistoryDTOHomeDepot.get(i).getOrganizationOne().trim().toUpperCase()) )
				{
					revisedList.add(prev);
					prev = workHistoryDTOHomeDepot.get(i);
				}
				//Set the begin date and description of previous with the current get(i)
				prev.setEffectiveDate(workHistoryDTOHomeDepot.get(i).getEffectiveDate());
				prev.setPositionActionDescription(workHistoryDTOHomeDepot.get(i).getPositionActionDescription());
			}
			
			//Add the last entry
			if(prev != null)
			revisedList.add(prev);
			
			//Remove first entry
			if(revisedList != null && revisedList.size() != 0)
				revisedList.remove(0);
			
			//Sort the list
			Collections.sort(revisedList, new Comparator<WorkHistoryHomeDepotDTO>() {
				  public int compare(WorkHistoryHomeDepotDTO o1, WorkHistoryHomeDepotDTO o2) {
				      if (o1.getEffectiveDate() == null || o2.getEffectiveDate() == null)
				        return 0;
				      if(o1.getEffectiveDate().equals(o2.getEffectiveDate()))
		    		  {
				    	  //if(o1.getOrganizationOne().equals(o2.getOrganizationOne()))
				    	  //{
					    	  if(o1.getRelationEndDate().before(o2.getRelationEndDate()))
					    		  return 1;
					    	  else if(o1.getRelationEndDate().after(o2.getRelationEndDate()))
					    		  return -1;
					    	  else
					    		  return 0;
				    	  //}
		    		  }
				      else if(o1.getEffectiveDate().before(o2.getEffectiveDate()))
		    		  {
				    		  return 1;
		    		  }
				      else if(o1.getEffectiveDate().after(o2.getEffectiveDate()))
		    		  {
				    		  return -1;
		    		  }
				      return 0;
				  }  
				});
			
			for (WorkHistoryHomeDepotDTO workHistory: revisedList) {
				// update duration
				if(workHistory.getRelationEndDate().after(new Date(System.currentTimeMillis())) )
					workHistory.setRelationEndDate(new Date(System.currentTimeMillis()));    	
				workHistory.setDuration(AppUtil.getDuration(workHistory.getEffectiveDate(), workHistory.getRelationEndDate()));
			}
			workHistoryRes.setWorkHistoryHomeDepotDTO(revisedList);
			
			for (WorkHistoryHomeDepotPre95DTO pre95: workHistoryDTOHomeDepotPre95) {
				// update duration
				pre95.setDuration(AppUtil.getDuration(pre95.getWorkBeginDate(), pre95.getWorkEndDate()));
			}
			workHistoryRes.setWorkHistoryHomeDepotPre95DTO(workHistoryDTOHomeDepotPre95);
			
			for (WorkHistoryExternalDTO extDTO : workHistoryDTOExternal) {
				
				// Update Industry description				
				if (industryDetails.containsKey(extDTO.getSuccessionPlanIndustryTypeCode()))
					extDTO.setIndustryTypeDescription(industryDetails.get(extDTO.getSuccessionPlanIndustryTypeCode()));
				
				// Update duration
				extDTO.setDuration(AppUtil.getDuration(extDTO.getWorkBeginDate(), extDTO.getWorkEndDate()));
			}
			
			workHistoryRes.setWorkHistoryExternalDTO(workHistoryDTOExternal);
			

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
					.format("Exiting getWorkHistoryDetails(). Total time to process request: %.4f seconds",
							(((float) endTime - startTime) / NANOS_IN_SECOND)));
		}
		return workHistoryRes;
	}
	
	public static WorkHistoryHomeDepotDTO getDateInCurrentPosition(String associateId) throws HrReviewException {
		
		long startTime = 0;
		WorkHistoryHomeDepotDTO currHomeDepot = null;
		
		List<WorkHistoryHomeDepotDTO> workHistoryDTOHomeDepot = null;
		
		if (mLogger.isDebugEnabled()) {
			startTime = System.nanoTime();
		}

		try {
			
			workHistoryDTOHomeDepot = (List<WorkHistoryHomeDepotDTO> ) PersonalDAO.getWorkHistoryDetailsHomeDepot(associateId);			
			List<WorkHistoryHomeDepotDTO> updatedDTOList = new ArrayList<WorkHistoryHomeDepotDTO>();
			if (workHistoryDTOHomeDepot != null && workHistoryDTOHomeDepot.size() > 0) {
				
				WorkHistoryHomeDepotDTO previuosTHDWorkHistoryDTO = null;
				WorkHistoryHomeDepotDTO currentTHDWorkHistoryDTO = null;
				WorkHistoryHomeDepotDTO updatedTHDWorkHistoryDTO = null;
				
				int recordCount = 0;
				for (WorkHistoryHomeDepotDTO workHistory: workHistoryDTOHomeDepot) {
					
					recordCount++;	
					if (recordCount == 1) {
						previuosTHDWorkHistoryDTO = workHistory;
						continue;
					} else {
						currentTHDWorkHistoryDTO = workHistory;
						updatedTHDWorkHistoryDTO = new WorkHistoryHomeDepotDTO();
						// If job title matches
						if(! (previuosTHDWorkHistoryDTO.getJobTitleId().equals(currentTHDWorkHistoryDTO.getJobTitleId())))
						{
							//Create a THDWorkExperience bean with the previous record setveBeginDate()
							updatedTHDWorkHistoryDTO.setJobTitleDescription(previuosTHDWorkHistoryDTO.getJobTitleDescription());
							updatedTHDWorkHistoryDTO.setHumanResourcesSystemDivisionName(previuosTHDWorkHistoryDTO.getHumanResourcesSystemDivisionName());
							updatedTHDWorkHistoryDTO.setPositionActionDescription(previuosTHDWorkHistoryDTO.getPositionActionDescription());
							updatedTHDWorkHistoryDTO.setEffectiveDate(previuosTHDWorkHistoryDTO.getEffectiveDate());
							updatedTHDWorkHistoryDTO.setRelationEndDate(previuosTHDWorkHistoryDTO.getRelationEndDate());
							updatedTHDWorkHistoryDTO.setDuration(AppUtil.getDuration(updatedTHDWorkHistoryDTO.getEffectiveDate(), updatedTHDWorkHistoryDTO.getRelationEndDate()));
							
							//Add the THDWorkExperience DTO to the updatedDTOList
							updatedDTOList.add(updatedTHDWorkHistoryDTO);
							//store the current as previous for further processing
							previuosTHDWorkHistoryDTO = currentTHDWorkHistoryDTO;
							
							continue;
						}
						
						//If the previous store is not equal to the current store
						if(! (previuosTHDWorkHistoryDTO.getOrganizationOne().equals(currentTHDWorkHistoryDTO.getOrganizationOne())))
						{
							//Create a THDWorkExperience DTO with the previous record set
							updatedTHDWorkHistoryDTO.setJobTitleDescription(previuosTHDWorkHistoryDTO.getJobTitleDescription());
							updatedTHDWorkHistoryDTO.setHumanResourcesSystemDivisionName(previuosTHDWorkHistoryDTO.getHumanResourcesSystemDivisionName());
							updatedTHDWorkHistoryDTO.setPositionActionDescription(previuosTHDWorkHistoryDTO.getPositionActionDescription());
							updatedTHDWorkHistoryDTO.setEffectiveDate(previuosTHDWorkHistoryDTO.getEffectiveDate());
							updatedTHDWorkHistoryDTO.setRelationEndDate(previuosTHDWorkHistoryDTO.getRelationEndDate());
							
							updatedTHDWorkHistoryDTO.setDuration(AppUtil.getDuration(updatedTHDWorkHistoryDTO.getEffectiveDate(), updatedTHDWorkHistoryDTO.getRelationEndDate()));
							
							//Add the THDWorkExperience DTO to the result vector
							updatedDTOList.add(updatedTHDWorkHistoryDTO);
							
							//store the current as previous for further processing
							previuosTHDWorkHistoryDTO = currentTHDWorkHistoryDTO;
							
							continue;
						}
						previuosTHDWorkHistoryDTO.setEffectiveDate(currentTHDWorkHistoryDTO.getEffectiveDate());
						previuosTHDWorkHistoryDTO.setPositionActionDescription(currentTHDWorkHistoryDTO.getPositionActionDescription());						
					}
				} // end for loop
				
				//Create a THDWorkExperience bean with the previous record
				updatedTHDWorkHistoryDTO = new WorkHistoryHomeDepotDTO();
				updatedTHDWorkHistoryDTO.setJobTitleDescription(previuosTHDWorkHistoryDTO.getJobTitleDescription());
				updatedTHDWorkHistoryDTO.setHumanResourcesSystemDivisionName(previuosTHDWorkHistoryDTO.getHumanResourcesSystemDivisionName());
				updatedTHDWorkHistoryDTO.setPositionActionDescription(previuosTHDWorkHistoryDTO.getPositionActionDescription());
				updatedTHDWorkHistoryDTO.setEffectiveDate(previuosTHDWorkHistoryDTO.getEffectiveDate());
				updatedTHDWorkHistoryDTO.setRelationEndDate(previuosTHDWorkHistoryDTO.getRelationEndDate());
				updatedTHDWorkHistoryDTO.setDuration(AppUtil.getDuration(updatedTHDWorkHistoryDTO.getEffectiveDate(), updatedTHDWorkHistoryDTO.getRelationEndDate()));
				
				// Adding last record to the list
				updatedDTOList.add(updatedTHDWorkHistoryDTO);
				
				// removed the first record - which has no end date
				if (updatedDTOList != null && updatedDTOList.size() > 0) {
					updatedDTOList.remove(0);
				}
				
				if (updatedDTOList != null && updatedDTOList.size() > 0) {
					return (WorkHistoryHomeDepotDTO)updatedDTOList.get(0);					
				}
			
			}
			
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
					.format("Exiting getDateInCurrentPosition(). Total time to process request: %.4f seconds",
							(((float) endTime - startTime) / NANOS_IN_SECOND)));
		}
				
		return null; 
	}
}

package com.homedepot.hr.gd.hrreview.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.homedepot.hr.gd.hrreview.dto.CountrySearchDTO;
import com.homedepot.hr.gd.hrreview.dto.CourseSearchDTO;
import com.homedepot.hr.gd.hrreview.dto.DegreeSearchDTO;
import com.homedepot.hr.gd.hrreview.dto.DepartmentSearchDTO;
import com.homedepot.hr.gd.hrreview.dto.DistrictSearchDTO;
import com.homedepot.hr.gd.hrreview.dto.DivisionSearchDTO;
import com.homedepot.hr.gd.hrreview.dto.GetSchoolSearchDTO;
import com.homedepot.hr.gd.hrreview.dto.IndustryDetailsDTO;
import com.homedepot.hr.gd.hrreview.dto.JobTitleSearchDTO;
import com.homedepot.hr.gd.hrreview.dto.LanguageProficiencyDTO;
import com.homedepot.hr.gd.hrreview.dto.LanguageSearchDTO;
import com.homedepot.hr.gd.hrreview.dto.LanguageTypeDTO;
import com.homedepot.hr.gd.hrreview.dto.LocationSearchDTO;
import com.homedepot.hr.gd.hrreview.dto.MajorsSearchDTO;
import com.homedepot.hr.gd.hrreview.dto.PotentialPerformanceSearchDTO;
import com.homedepot.hr.gd.hrreview.dto.RaceSearchDTO;
import com.homedepot.hr.gd.hrreview.dto.ReadinessSearchDTO;
import com.homedepot.hr.gd.hrreview.dto.RegionSearchDTO;
import com.homedepot.hr.gd.hrreview.dto.SchoolType;
import com.homedepot.hr.gd.hrreview.dto.StateSearchDTO;
import com.homedepot.hr.gd.hrreview.dto.StoreSearchDTO;
import com.homedepot.hr.gd.hrreview.interfaces.Constants;
import com.homedepot.hr.gd.hrreview.interfaces.DAOConstants;
import com.homedepot.ta.aa.dao.Inputs;
import com.homedepot.ta.aa.dao.Query;
import com.homedepot.ta.aa.dao.Results;
import com.homedepot.ta.aa.dao.ResultsReader;
import com.homedepot.ta.aa.dao.basic.BasicDAO;
import com.homedepot.ta.aa.dao.builder.DAO;
import com.homedepot.ta.aa.dao.exceptions.QueryException;
import com.homedepot.ta.aa.dao.stream.MapStream;

public class DataDAO implements DAOConstants, Constants {

	private static final Logger logger = Logger.getLogger(DataDAO.class);
	//TODO: change to match your business use id
	private static final int BUSINESS_USE_ID = 55;
	//Contract name for WorkforceSuccessionPlanning
	private static final String WORKFORCESUCCESSIONPLANNING_NAME = "WorkforceSuccessionPlanning";
	//Version number for WorkforceSuccessionPlanning
	private static final int WORKFORCESUCCESSIONPLANNING_VERSION = 1;

	public static List<MajorsSearchDTO> getMajorsDetails()
			throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start getMajorsDetails");
		}
		//		List<Object> inputUnmatchedCollegeMajorDescriptionList = new ArrayList<Object>();

		if (logger.isDebugEnabled()) {
			logger.debug("executing the query");
		}
		return DAO
				.select("WorkforceSuccessionPlanning.1.readNlsCollegeMajorByInputList") //TODO: Verify contract name, version, and replace values below
				//				.input("collegeMajorCode", "value") // optional
				.input("languageCode", DAOConstants.lang_code) // optional
				//				.input("collegeMajorDescription", "value") // optional
				//				.input("unmatchedCollegeMajorDescriptionList",
				//						inputUnmatchedCollegeMajorDescriptionList) // optional
				.qualify("isOrderByRequiredFlag", true) // optional
				.list(MajorsSearchDTO.class);
	}

	public static List<DegreeSearchDTO> getDegreeDetails()
			throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start getDegreeDetails");
		}
		//		List<Object> inputUnmatchedDegreeTypeDescriptionList = new ArrayList<Object>();

		if (logger.isDebugEnabled()) {
			logger.debug("executing the query");
		}
		return DAO
				.select("WorkforceSuccessionPlanning.1.readNlsDegreeTypeByInputList") //TODO: Verify contract name, version, and replace values below
				//				.input("degreeTypeCode", "value") // optional
				.input("languageCode", DAOConstants.lang_code) // optional
				//				.input("degreeTypeDescription", "value") // optional
				//				.input("unmatchedDegreeTypeDescriptionList",
				//						inputUnmatchedDegreeTypeDescriptionList) // optional
				.qualify("isOrderByRequiredFlag", true) // optional
				.list(DegreeSearchDTO.class);
	}

	public static List<ReadinessSearchDTO> getReadinessDetails()
			throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start getReadinessDetails");
		}
		//		List<Object> inputUnmatchedSuccessionPlanGoalStatusDescriptionList = new ArrayList<Object>();

		if (logger.isDebugEnabled()) {
			logger.debug("executing the query");
		}
		return DAO
				.select("WorkforceSuccessionPlanning.1.readNlsSuccessionPlanGoalStatusByInputList") //TODO: Verify contract name, version, and replace values below
				//				.input("successionPlanGoalStatusCode", (short) 0) // optional
				.input("languageCode", DAOConstants.lang_code) // optional
				//				.input("successionPlanGoalStatusDescription", "value") // optional
				//				.input("unmatchedSuccessionPlanGoalStatusDescriptionList",
				//						inputUnmatchedSuccessionPlanGoalStatusDescriptionList) // optional
				.qualify("isOrderByRequiredFlag", true) // optional
				.list(ReadinessSearchDTO.class);
	}

	public static List<JobTitleSearchDTO> getJobTitleDetails(boolean prev)
			throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start readNlsAndJobTitleDetails");
		}
		//		List<Object> inputUnmatchedJobTitleDescriptionList = new ArrayList<Object>();
		//		Date date = new Date(System.currentTimeMillis());

		if (logger.isDebugEnabled()) {
			logger.debug("executing the query");
		}
		if (prev) {
			return DAO
					.select("WorkforceSuccessionPlanning.1.readNlsAndJobTitleDetails") //TODO: Verify contract name, version, and replace values below
					.input("effectiveBeginDate",
							new Date(System.currentTimeMillis())) // optional
					.input("languageCode", DAOConstants.lang_code) // optional
					//					.input("unmatchedJobTitleDescription", "value") // optional
					//					.input("titleEffectiveBeginDate",
					//							new Date(System.currentTimeMillis())) // optional
					//					.inputAllowNull("effectiveEndDate",
					//							new Date(System.currentTimeMillis())) // can be null, optional
					//				.input("unmatchedJobTitleDescriptionList",
					//						inputUnmatchedJobTitleDescriptionList) // optional
					//				.qualify("effectiveBeginDateGreaterThanEqualTo", false) // optional
					//				.qualify("effectiveBeginDateGreaterThan", false) // optional
					.qualify("effectiveBeginDateLessThanEqualTo", true) // optional
					//				.qualify("effectiveBeginDateLessThan", false) // optional
					//				.qualify("effectiveBeginDateEqualTo", false) // optional
					//				.qualify("effectiveBeginDateNotEqualTo", false) // optional
					//				.qualify("titleEffectiveBeginDateGreaterThanEqualTo", false) // optional
					//				.qualify("titleEffectiveBeginDateGreaterThan", false) // optional
					//					.qualify("titleEffectiveBeginDateLessThanEqualTo", date) // optional
					//				.qualify("titleEffectiveBeginDateLessThan", false) // optional
					//				.qualify("titleEffectiveBeginDateEqualTo", false) // optional
					//				.qualify("titleEffectiveBeginDateNotEqualTo", false) // optional
					//					.qualify("effectiveEndDateGreaterThanEqualTo", date) // optional
					//				.qualify("effectiveEndDateGreaterThan", false) // optional
					//				.qualify("effectiveEndDateLessThanEqualTo", false) // optional
					//				.qualify("effectiveEndDateLessThan", false) // optional
					//				.qualify("effectiveEndDateEqualTo", false) // optional
					//				.qualify("effectiveEndDateNotEqualTo", false) // optional
					.list(JobTitleSearchDTO.class);
		} else {
			return DAO
					.select("WorkforceSuccessionPlanning.1.readNlsAndJobTitleDetails") //TODO: Verify contract name, version, and replace values below
					.input("effectiveBeginDate",
							new Date(System.currentTimeMillis())) // optional
					.input("languageCode", DAOConstants.lang_code) // optional
					//				.input("unmatchedJobTitleDescription", "value") // optional
					.input("titleEffectiveBeginDate",
							new Date(System.currentTimeMillis())) // optional
					.inputAllowNull("effectiveEndDate",
							new Date(System.currentTimeMillis())) // can be null, optional
					//				.input("unmatchedJobTitleDescriptionList",
					//						inputUnmatchedJobTitleDescriptionList) // optional
					//				.qualify("effectiveBeginDateGreaterThanEqualTo", false) // optional
					//				.qualify("effectiveBeginDateGreaterThan", false) // optional
					.qualify("effectiveBeginDateLessThanEqualTo", true) // optional
					//				.qualify("effectiveBeginDateLessThan", false) // optional
					//				.qualify("effectiveBeginDateEqualTo", false) // optional
					//				.qualify("effectiveBeginDateNotEqualTo", false) // optional
					//				.qualify("titleEffectiveBeginDateGreaterThanEqualTo", false) // optional
					//				.qualify("titleEffectiveBeginDateGreaterThan", false) // optional
					.qualify("titleEffectiveBeginDateLessThanEqualTo", true) // optional
					//				.qualify("titleEffectiveBeginDateLessThan", false) // optional
					//				.qualify("titleEffectiveBeginDateEqualTo", false) // optional
					//				.qualify("titleEffectiveBeginDateNotEqualTo", false) // optional
					.qualify("effectiveEndDateGreaterThanEqualTo", true) // optional
					//				.qualify("effectiveEndDateGreaterThan", false) // optional
					//				.qualify("effectiveEndDateLessThanEqualTo", false) // optional
					//				.qualify("effectiveEndDateLessThan", false) // optional
					//				.qualify("effectiveEndDateEqualTo", false) // optional
					//				.qualify("effectiveEndDateNotEqualTo", false) // optional
					.list(JobTitleSearchDTO.class);
		}
	}

	public static List<RegionSearchDTO> getRegionDetails()
			throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start getRegionDetails");
		}
		//		List<Object> inputUnmatchedOperationsGroupNameList = new ArrayList<Object>();

		if (logger.isDebugEnabled()) {
			logger.debug("executing the query");
		}
		return DAO
				.select("BusinessOrganizationThdOrganization.2.readNlsHumanResourcesSystemOperationsGroupByInputList") //TODO: Verify contract name, version, and replace values below
				//				.input("humanResourcesSystemOperationsGroupCode", "value") // optional
				.input("languageCode", DAOConstants.lang_code) // optional
				//				.input("unmatchedOperationsGroupName", "value") // optional
				//				.input("unmatchedOperationsGroupNameList",
				//						inputUnmatchedOperationsGroupNameList) // optional
				.qualify("isOrderByRequired", true) // optional
				.list(RegionSearchDTO.class);
	}

	public static List<StoreSearchDTO> getStoreDetails() throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start getStoreDetails");
		}
		//		List<Object> inputUnmatchedHumanResourcesSystemStoreNumberList = new ArrayList<Object>();
		//		Date date = new Date(System.currentTimeMillis());

		if (logger.isDebugEnabled()) {
			logger.debug("executing the query");
		}
		return DAO
				.select("BusinessOrganizationThdOrganization.2.readHumanResourcesSystemStoreByInputList") //TODO: Verify contract name, version, and replace values below
				//				.input("humanResourcesSystemStoreNumber", "value") // optional
				.input("effectiveBeginDate",
						new Date(System.currentTimeMillis())) // optional
				.inputAllowNull("effectiveEndDate",
						new Date(System.currentTimeMillis())) // can be null, optional
				//				.input("unmatchedHumanResourcesSystemStoreNumberList",
				//						inputUnmatchedHumanResourcesSystemStoreNumberList) // optional
				//				.qualify("effectiveBeginDateGreaterThanEqualTo", false) // optional
				//				.qualify("effectiveBeginDateGreaterThan", false) // optional
				.qualify("effectiveBeginDateLessThanEqualTo", true) // optional
				//				.qualify("effectiveBeginDateLessThan", false) // optional
				//				.qualify("effectiveBeginDateEqualTo", false) // optional
				//				.qualify("effectiveBeginDateNotEqualTo", false) // optional
				.qualify("effectiveEndDateGreaterThanEqualTo", true) // optional
				//				.qualify("effectiveEndDateGreaterThan", false) // optional
				//				.qualify("effectiveEndDateLessThanEqualTo", false) // optional
				//				.qualify("effectiveEndDateLessThan", false) // optional
				//				.qualify("effectiveEndDateEqualTo", false) // optional
				//				.qualify("effectiveEndDateNotEqualTo", false) // optional
				.qualify("isOrderByRequired", true) // optional
				.list(StoreSearchDTO.class);
	}

	public static List<DivisionSearchDTO> getDivisionDetails()
			throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start readNlsHumanResourcesSystemDivisionDetails");
		}
		//		List<Object> inputUnmatchedHumanResourcesSystemDivisionNameList = new ArrayList<Object>();
		//		Date date = new Date(System.currentTimeMillis());
		if (logger.isDebugEnabled()) {
			logger.debug("executing the query");
		}
		return DAO
				.select("BusinessOrganizationThdOrganization.2.readNlsHumanResourcesSystemDivisionDetails") //TODO: Verify contract name, version, and replace values below
				.input("humanResourcesStoringEffectiveBeginDate",
						new Date(System.currentTimeMillis())) // optional
				.input("languageCode", DAOConstants.lang_code) // optional
				//				.input("humanResourcesSystemDivisionName", "value") // optional
				//				.input("unmatchedHumanResourcesSystemDivisionNameList",
				//						inputUnmatchedHumanResourcesSystemDivisionNameList) // optional
				//				.qualify(
				//						"humanResourcesStoringEffectiveBeginDateGreaterThanEqualTo",
				//						false) // optional
				//				.qualify("humanResourcesStoringEffectiveBeginDateGreaterThan",
				//						false) // optional
				.qualify(
						"humanResourcesStoringEffectiveBeginDateLessThanEqualTo",
						true) // optional
				//				.qualify("humanResourcesStoringEffectiveBeginDateLessThan",
				//						false) // optional
				//				.qualify("humanResourcesStoringEffectiveBeginDateEqualTo",
				//						false) // optional
				//				.qualify("humanResourcesStoringEffectiveBeginDateNotEqualTo",
				//						false) // optional
				.qualify("isOrderByRequired", true) // optional
				.list(DivisionSearchDTO.class);
	}

	public static List<LanguageSearchDTO> getLanguageDetails()
			throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start getLanguageDetails");
		}
		//		List<Object> inputUnmatchedInternationalOrganizationForStandardsLanguageNameList = new ArrayList<Object>();

		if (logger.isDebugEnabled()) {
			logger.debug("executing the query");
		}
		return DAO
				.select("WorkforceRecruitment.1.readNlsInternationalOrganizationForStandardsLanguageAndUsageGroupDetails") //TODO: Verify contract name, version, and replace values below
				.input("applicationUsageGroupCode", (short) 9) // optional
				.input("unmatchedInternationalOrganizationForStandardsLanguageCode",
						"mis") // optional
				.input("languageCode", DAOConstants.lang_code) // optional
				//				.input("unmatchedInternationalOrganizationForStandardsLanguageName",
				//						"value") // optional
				//				.input("unmatchedInternationalOrganizationForStandardsLanguageNameList",
				//						inputUnmatchedInternationalOrganizationForStandardsLanguageNameList) // optional

				.list(LanguageSearchDTO.class);
	}

	public static List<PotentialPerformanceSearchDTO> getPotentialPerformanceDetails()
			throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start getPotentialDetails");
		}
		//		List<Object> inputUnmatchedEvaluateRatingDescriptionList = new ArrayList<Object>();
		//		Date date = new Date(System.currentTimeMillis());

		if (logger.isDebugEnabled()) {
			logger.debug("executing the query");
		}
		return DAO
				.select("WorkforceSuccessionPlanning.1.readDistinctNlsEvaluateRatingCodeAndEvaluateCategoryHumanResourcesRatingDetails") //TODO: Verify contract name, version, and replace values below
				.input("languageCode", DAOConstants.lang_code) // optional
				//				.input("evaluateRatingDescription", "value") // optional
				//				.input("unmatchedEvaluateRatingDescription", "value") // optional
				//				.input("unmatchedEvaluateRatingDescriptionList",
				//						inputUnmatchedEvaluateRatingDescriptionList) // optional
				.input("effectiveBeginDate",
						new Date(System.currentTimeMillis())) // optional
				.input("effectiveEndDate", new Date(System.currentTimeMillis())) // optional
				//				.qualify("effectiveBeginDateGreaterThanEqualTo", false) // optional
				//				.qualify("effectiveBeginDateGreaterThan", false) // optional
				.qualify("effectiveBeginDateLessThanEqualTo", true) // optional
				//				.qualify("effectiveBeginDateLessThan", false) // optional
				//				.qualify("effectiveBeginDateEqualTo", false) // optional
				//				.qualify("effectiveBeginDateNotEqualTo", false) // optional
				.qualify("effectiveEndDateGreaterThanEqualTo", true) // optional
				//				.qualify("effectiveEndDateGreaterThan", false) // optional
				//				.qualify("effectiveEndDateLessThanEqualTo", false) // optional
				//				.qualify("effectiveEndDateLessThan", false) // optional
				//				.qualify("effectiveEndDateEqualTo", false) // optional
				//				.qualify("effectiveEndDateNotEqualTo", false) // optional
				.list(PotentialPerformanceSearchDTO.class);
	}

	public static List<DepartmentSearchDTO> getDepartmentDetails()
			throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start readNlsHumanResourcesSystemDepartmentByInputList");
		}
		List<Object> inputUnmatchedHumanResourcesSystemDepartmentNameList = new ArrayList<Object>();
		inputUnmatchedHumanResourcesSystemDepartmentNameList.add("");
		inputUnmatchedHumanResourcesSystemDepartmentNameList.add("");

		//		Date date = new Date(System.currentTimeMillis());
		if (logger.isDebugEnabled()) {
			logger.debug("executing the query");
		}
		return DAO
				.select("WorkforceSuccessionPlanning.1.readNlsHumanResourcesSystemDepartmentByInputList") //TODO: Verify contract name, version, and replace values below
				//				.input("humanResourcesSystemDepartmentNumber", "value") // optional
				//.input("effectiveBeginDate",
				//new Date(System.currentTimeMillis())) // optional
				.input("humanResourceEffectiveBeginDate",
						new Date(System.currentTimeMillis())) // optional
				.input("languageCode", DAOConstants.lang_code) // optional
				//				.input("unmatchedHumanResourcesSystemDepartmentName", "value") // optional
				.input("unmatchedHumanResourcesSystemDepartmentNameList",
						inputUnmatchedHumanResourcesSystemDepartmentNameList) // optional
				//				.qualify("effectiveBeginDateGreaterThanEqualTo", false) // optional
				//				.qualify("effectiveBeginDateGreaterThan", false) // optional
				//.qualify("effectiveBeginDateLessThanEqualTo", date) // optional
				//				.qualify("effectiveBeginDateLessThan", false) // optional
				//				.qualify("effectiveBeginDateEqualTo", false) // optional
				//				.qualify("effectiveBeginDateNotEqualTo", false) // optional
				//				.qualify("humanResourceEffectiveBeginDateGreaterThanEqualTo",
				//						date) // optional
				//				.qualify("humanResourceEffectiveBeginDateGreaterThan", false) // optional
				.qualify("humanResourceEffectiveBeginDateLessThanEqualTo", true) // optional
				//				.qualify("humanResourceEffectiveBeginDateLessThan", false) // optional
				//				.qualify("humanResourceEffectiveBeginDateEqualTo", false) // optional
				//				.qualify("humanResourceEffectiveBeginDateNotEqualTo", false) // optional
				.qualify("isOrderByRequired", true) // optional
				.list(DepartmentSearchDTO.class);
	}

	public static List<RaceSearchDTO> getRaceDetails() throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start getRaceDetails");
		}
		//		Date date = new Date(System.currentTimeMillis());

		if (logger.isDebugEnabled()) {
			logger.debug("executing the query");
		}
		return DAO
				.select("WorkforceSuccessionPlanning.1.readNlsRaceByInputList") //TODO: Verify contract name, version, and replace values below
				//				.input("raceCode", "value") // optional
				.input("effectiveBeginDate",
						new Date(System.currentTimeMillis())) // optional
				.input("raceEffectiveBeginDate",
						new Date(System.currentTimeMillis())) // optional
				.input("languageCode", DAOConstants.lang_code) // optional
				.input("unmatchedRaceDescription", "value") // optional
				//				.qualify("effectiveBeginDateGreaterThanEqualTo", false) // optional
				//				.qualify("effectiveBeginDateGreaterThan", false) // optional
				.qualify("effectiveBeginDateLessThanEqualTo", true) // optional
				//				.qualify("effectiveBeginDateLessThan", false) // optional
				//				.qualify("effectiveBeginDateEqualTo", false) // optional
				//				.qualify("effectiveBeginDateNotEqualTo", false) // optional
				//				.qualify("raceEffectiveBeginDateGreaterThanEqualTo", false) // optional
				//				.qualify("raceEffectiveBeginDateGreaterThan", false) // optional
				.qualify("raceEffectiveBeginDateLessThanEqualTo", true) // optional
				//				.qualify("raceEffectiveBeginDateLessThan", false) // optional
				//				.qualify("raceEffectiveBeginDateEqualTo", false) // optional
				//				.qualify("raceDescriptionIsNotNullFlag", false) // optional
				.qualify("isOrderByRequired", true) // optional
				.list(RaceSearchDTO.class);
	}

	public static List<CourseSearchDTO> getCourseDetails()
			throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start readDistinctSuccessionPlanCourseAndTransferDetails");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("executing the query");
		}
		final List<CourseSearchDTO> courseListDTO = new ArrayList<CourseSearchDTO>();
		MapStream inputs = new MapStream(
				"readDistinctSuccessionPlanCourseAndTransferDetails");
		BasicDAO.getResult(WORKFORCESUCCESSIONPLANNING_NAME, BUSINESS_USE_ID,
				WORKFORCESUCCESSIONPLANNING_VERSION, inputs,
				new ResultsReader() {
					public void readResults(Results results, Query query,
							Inputs inputs) throws QueryException {
						CourseSearchDTO courseDTO = null;
						while (results.next()) {
							courseDTO = new CourseSearchDTO();
							courseDTO.setSpCrsDesc(results
									.getString("successionPlanCourseDescription").trim());
							courseListDTO.add(courseDTO);
						}
					}
				});
		return courseListDTO;
	}

	public static List<DistrictSearchDTO> getDistrictDetails()
			throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start getDistrictDetails");
		}
		//		List<Object> inputUnmatchedHumanResourcesSystemRegionNameList = new ArrayList<Object>();
		//		Date date = new Date(System.currentTimeMillis());
		if (logger.isDebugEnabled()) {
			logger.debug("executing the query");
		}
		return DAO
				.select("WorkforceSuccessionPlanning.1.readNlsHumanResourcesSystemRegionByInputList") //TODO: Verify contract name, version, and replace values below
				.input("humanResourcesStoreEffectiveBeginDate",
						new Date(System.currentTimeMillis())) // optional
				.input("languageCode", DAOConstants.lang_code) // optional
				//				.input("unmatchedHumanResourcesSystemRegionNameList",
				//						inputUnmatchedHumanResourcesSystemRegionNameList) // optional
				//				.qualify(
				//						"humanResourcesStoreEffectiveBeginDateGreaterThanEqualTo",
				//						false) // optional
				//				.qualify("humanResourcesStoreEffectiveBeginDateGreaterThan",
				//						false) // optional
				.qualify(
						"humanResourcesStoreEffectiveBeginDateLessThanEqualTo",
						true) // optional
				//				.qualify("humanResourcesStoreEffectiveBeginDateLessThan", false) // optional
				//				.qualify("humanResourcesStoreEffectiveBeginDateEqualTo", false) // optional
				//				.qualify("humanResourcesStoreEffectiveBeginDateNotEqualTo",
				//						false) // optional
				.list(DistrictSearchDTO.class);
	}

	public static List<StateSearchDTO> getLocationStateDetails()
			throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start readDistinctEducationFacilityAndStateByInputList");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("executing the query");
		}
		return DAO
				.select("WorkforceSuccessionPlanning.1.readDistinctEducationFacilityAndStateByInputList") //TODO: Verify contract name, version, and replace values below
				//				.input("stateCode", "value") // optional
				//				.qualify("stateCodeGreaterThanEqualTo", false) // optional
				//				.qualify("stateCodeGreaterThan", false) // optional
				//				.qualify("stateCodeLessThanEqualTo", false) // optional
				//				.qualify("stateCodeLessThan", false) // optional
				//				.qualify("stateCodeEqualTo", false) // optional
				//				.qualify("stateCodeNotEqualTo", false) // optional
				.list(StateSearchDTO.class);
	}

	public static List<CountrySearchDTO> getLocationCountryDetails()
			throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start readDistinctEducationFacilityAndCountryByInputList");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("executing the query");
		}
		return DAO
				.select("WorkforceSuccessionPlanning.1.readDistinctEducationFacilityAndCountryByInputList") //TODO: Verify contract name, version, and replace values below
				//				.input("countryCode", "value") // optional
				//				.qualify("countryCodeGreaterThanEqualTo", false) // optional
				//				.qualify("countryCodeGreaterThan", false) // optional
				//				.qualify("countryCodeLessThanEqualTo", false) // optional
				//				.qualify("countryCodeLessThan", false) // optional
				//				.qualify("countryCodeEqualTo", false) // optional
				//				.qualify("countryCodeNotEqualTo", false) // optional
				.list(CountrySearchDTO.class);
	}

	public static List<GetSchoolSearchDTO> getSchoolDetails()
			throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start readDistinctEducationFacilityAndNameByInputList");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("executing the query");
		}
		return DAO
				.select("WorkforceSuccessionPlanning.1.readDistinctEducationFacilityAndNameByInputList") //TODO: Verify contract name, version, and replace values below
				//				.input("facilityName", "value") // optional
				//				.qualify("facilityNameGreaterThanEqualTo", false) // optional
				//				.qualify("facilityNameGreaterThan", false) // optional
				//				.qualify("facilityNameLessThanEqualTo", false) // optional
				//				.qualify("facilityNameLessThan", false) // optional
				//				.qualify("facilityNameEqualTo", false) // optional
				//				.qualify("facilityNameNotEqualTo", false) // optional
				.list(GetSchoolSearchDTO.class);
	}

	public static List<SchoolType> getSchoolTypes() throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start getSchoolTypes");
		}
		List<Object> inputUnmatchedSchoolTypeDescriptionList = new ArrayList<Object>();
		inputUnmatchedSchoolTypeDescriptionList.add("");

		if (logger.isDebugEnabled()) {
			logger.debug("executing the query");
		}
		return DAO
				.select("WorkforceSuccessionPlanning.1.readNlsSchoolTypeByInputList") //TODO: Verify contract name, version, and replace values below
				//.input("schoolTypeCode", (short) 0) // optional
				.input("languageCode", "en_US") // optional
				//.input("schoolTypeDescription", "value") // optional
				.input("unmatchedSchoolTypeDescriptionList",
						inputUnmatchedSchoolTypeDescriptionList) // optional
				.qualify("isOrderByRequiredFlag", false) // optional
				.list(SchoolType.class);
	}

	public static List<LanguageProficiencyDTO> getLanguageProficiency()
			throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start getLanguageProficiency");
		}
		List<Object> inputUnmatchedLanguageProficiencyDescriptionList = new ArrayList<Object>();
		inputUnmatchedLanguageProficiencyDescriptionList.add("");

		if (logger.isDebugEnabled()) {
			logger.debug("executing the query");
		}

		return DAO
				.select("WorkforceRecruitment.1.readNlsLanguageProficiencyLevelByInputList") //TODO: Verify contract name, version, and replace values below
				//.input("languageProficiencyCode", "mis") // optional
				.input("languageCode", "en_US") // optional
				//.input("unmatchedLanguageProficiencyDescription", null) // optional
				.input("unmatchedLanguageProficiencyDescriptionList",
						inputUnmatchedLanguageProficiencyDescriptionList) // optional
				.qualify("isOrderByRequired", true) // optional
				.list(LanguageProficiencyDTO.class);
	}

	public static List<LanguageTypeDTO> getLanguageType() throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start getLanguageType");
		}
		List<Object> inputUnmatchedLanguageUseTypeDescriptionList = new ArrayList<Object>();
		inputUnmatchedLanguageUseTypeDescriptionList.add("");

		if (logger.isDebugEnabled()) {
			logger.debug("executing the query");
		}
		return DAO
				.select("WorkforceRecruitment.1.readNlsLanguageUseTypeByInputList") //TODO: Verify contract name, version, and replace values below
				//.input("languageUseTypeCode", (short) 0) // optional
				.input("languageCode", "en_US") // optional
				.input("unmatchedLanguageUseTypeDescription", "") // optional
				//.input("unmatchedLanguageUseTypeDescriptionList",
				//		inputUnmatchedLanguageUseTypeDescriptionList) // optional
				.qualify("isOrderByRequired", true) // optional
				.list(LanguageTypeDTO.class);
	}

	public static List<IndustryDetailsDTO> getIndustryTypeDetails()
			throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start getIndustryTypeDetails");
		}
		List<Object> inputUnmatchedSuccessionPlanIndustryTypeDescriptionList = new ArrayList<Object>();
		inputUnmatchedSuccessionPlanIndustryTypeDescriptionList.add("");

		if (logger.isDebugEnabled()) {
			logger.debug("executing the query");
		}
		return DAO
				.select("WorkforceSuccessionPlanning.1.readMaximumNlsSuccessionPlanIndustryTypeDetails") //TODO: Verify contract name, version, and replace values below
				.input("languageCode", "en_US") // optional
				.input("effectiveBeginDate",
						new Date(System.currentTimeMillis())) // optional
				//.input("successionPlanIndustryTypeDescription", "value") // optional
				.input("unmatchedSuccessionPlanIndustryTypeDescriptionList",
						inputUnmatchedSuccessionPlanIndustryTypeDescriptionList) // optional
				//.qualify("effectiveBeginDateGreaterThanEqualTo", false) // optional
				//.qualify("effectiveBeginDateGreaterThan", false) // optional
				.qualify("effectiveBeginDateLessThanEqualTo", true) // optional
				//.qualify("effectiveBeginDateLessThan", false) // optional
				//.qualify("effectiveBeginDateEqualTo", false) // optional
				//.qualify("effectiveBeginDateNotEqualTo", false) // optional
				.list(IndustryDetailsDTO.class);
	}

	public static List<LocationSearchDTO> getLocationDeatils()
			throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start getLocationDeatils");
		}
		//List<Object> inputHumanResourcesSystemDivisionCodeList = new ArrayList<Object>();

		if (logger.isDebugEnabled()) {
			logger.debug("executing the query");
		}
		return DAO
				.select("BusinessOrganizationThdOrganizationStructure.1.readHumanResourcesSystemStoreOrganizationByInputList") //TODO: Verify contract name, version, and replace values below
				//.input("humanResourcesSystemStoreNumber", "value") // optional
				.input("humanResourcesStoreEffectiveBeginDate",
						new Date(System.currentTimeMillis())) // optional
				.inputAllowNull("humanResourcesStoreEffectiveEndDate",
						new Date(System.currentTimeMillis())) // can be null, optional
				//.input("humanResourcesSystemDivisionCodeList",
				//		inputHumanResourcesSystemDivisionCodeList) // optional
				.input("humanResourcesSystemStoreName", "") // optional
				.qualify("humanResourcesSystemStoreNameNotEqualTo", true) // optional
				//.qualify("humanResourcesStoreEffectiveBeginDateGreaterThan",
				//		false) // optional
				.qualify(
						"humanResourcesStoreEffectiveBeginDateLessThanEqualTo",
						true) // optional
				//.qualify("humanResourcesStoreEffectiveBeginDateLessThan", false) // optional
				//.qualify("humanResourcesStoreEffectiveBeginDateEqualTo", false) // optional
				//.qualify("humanResourcesStoreEffectiveBeginDateNotEqualTo",
				//		false) // optional
				.qualify(
						"humanResourcesStoreEffectiveEndDateGreaterThanEqualTo",
						true) // optional
				//.qualify("humanResourcesStoreEffectiveEndDateGreaterThan",
				//		true) // optional
				//.qualify("humanResourcesStoreEffectiveEndDateLessThanEqualTo",
				//		false) // optional
				//.qualify("humanResourcesStoreEffectiveEndDateLessThan", false) // optional
				//.qualify("humanResourcesStoreEffectiveEndDateEqualTo", false) // optional
				//.qualify("humanResourcesStoreEffectiveEndDateNotEqualTo", false) // optional
				//.qualify("humanResourcesSystemStoreNameGreaterThanEqualTo",
				//		false) // optional
				//.qualify("humanResourcesSystemStoreNameGreaterThan", false) // optional
				//.qualify("humanResourcesSystemStoreNameLessThanEqualTo", false) // optional
				//.qualify("humanResourcesSystemStoreNameLessThan", false) // optional
				//.qualify("humanResourcesSystemStoreNameEqualTo", false) // optional
				//.qualify("humanResourcesSystemStoreNameNotEqualTo", false) // optional
				.qualify("isOrderByRequired", true) // optional
				.qualify("humanResourcesSystemStoreNameNotNullFlag", true) // optional
				.list(LocationSearchDTO.class);
	}


}

package com.homedepot.hr.gd.hrreview.bl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.homedepot.hr.gd.hrreview.dao.DataDAO;
import com.homedepot.hr.gd.hrreview.dto.CourseSearchDTO;
import com.homedepot.hr.gd.hrreview.dto.DegreeSearchDTO;
import com.homedepot.hr.gd.hrreview.dto.DepartmentSearchDTO;
import com.homedepot.hr.gd.hrreview.dto.DistrictSearchDTO;
import com.homedepot.hr.gd.hrreview.dto.DivisionSearchDTO;
import com.homedepot.hr.gd.hrreview.dto.GetSchoolSearchDTO;
import com.homedepot.hr.gd.hrreview.dto.JobTitleSearchDTO;
import com.homedepot.hr.gd.hrreview.dto.LanguageSearchDTO;
import com.homedepot.hr.gd.hrreview.dto.LocationSearchDTO;
import com.homedepot.hr.gd.hrreview.dto.MajorsSearchDTO;
import com.homedepot.hr.gd.hrreview.dto.PotentialPerformanceSearchDTO;
import com.homedepot.hr.gd.hrreview.dto.RaceSearchDTO;
import com.homedepot.hr.gd.hrreview.dto.ReadinessSearchDTO;
import com.homedepot.hr.gd.hrreview.dto.RegionSearchDTO;
import com.homedepot.hr.gd.hrreview.dto.StoreSearchDTO;
import com.homedepot.hr.gd.hrreview.exceptions.HrReviewApplLogMessage;
import com.homedepot.hr.gd.hrreview.exceptions.HrReviewException;
import com.homedepot.hr.gd.hrreview.interfaces.Constants;
import com.homedepot.hr.gd.hrreview.response.CourseSearchResponse;
import com.homedepot.hr.gd.hrreview.response.DegreeSearchResponse;
import com.homedepot.hr.gd.hrreview.response.DepartmentSearchResponse;
import com.homedepot.hr.gd.hrreview.response.DistrictSearchResponse;
import com.homedepot.hr.gd.hrreview.response.DivisionSearchResponse;
import com.homedepot.hr.gd.hrreview.response.JobTitleSearchResponse;
import com.homedepot.hr.gd.hrreview.response.LanguageSearchResponse;
import com.homedepot.hr.gd.hrreview.response.LocationSearchResponse;
import com.homedepot.hr.gd.hrreview.response.MajorsSearchResponse;
import com.homedepot.hr.gd.hrreview.response.PerformanceSearchResponse;
import com.homedepot.hr.gd.hrreview.response.PotentialSearchResponse;
import com.homedepot.hr.gd.hrreview.response.RaceSearchResponse;
import com.homedepot.hr.gd.hrreview.response.ReadinessSearchResponse;
import com.homedepot.hr.gd.hrreview.response.RegionSearchResponse;
import com.homedepot.hr.gd.hrreview.response.SchoolSearchResponse;
import com.homedepot.hr.gd.hrreview.response.StoreSearchResponse;
import com.homedepot.ta.aa.dao.exceptions.QueryException;

public class DataManager implements Constants {

	private static final Logger mLogger = Logger.getLogger(DataManager.class);

	public static CourseSearchResponse getCourseDetails()
			throws HrReviewException {
		long startTime = 0;
		CourseSearchResponse courseRes = null;
		List<CourseSearchDTO> courseDTO = null;

		if (mLogger.isDebugEnabled()) {
			startTime = System.nanoTime();
		}

		try {
			courseDTO = (List<CourseSearchDTO>) DataDAO.getCourseDetails();
			courseRes = new CourseSearchResponse();
			
			//Remove duplicates
			int index = 0;
			List<String> courseTitleList = new ArrayList<String>();
			List<CourseSearchDTO> revisedList = new ArrayList<CourseSearchDTO>();
			for (CourseSearchDTO courseTitle : courseDTO) {				
				if (courseTitle != null && !courseTitleList.contains(courseTitle.getSpCrsDesc().trim().toUpperCase())) {
					courseTitle.setInd(index++);
					revisedList.add(courseTitle);
					courseTitleList.add(courseTitle.getSpCrsDesc().trim().toUpperCase());
				}
			}
			courseRes.setCourseSearchDTO(revisedList);

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
					.format("Exiting getCourseDetails(). Total time to process request: %.4f seconds",
							(((float) endTime - startTime) / NANOS_IN_SECOND)));
		}
		return courseRes;
	}

	public static DegreeSearchResponse getDegreeDetails()
			throws HrReviewException {
		long startTime = 0;
		DegreeSearchResponse degreeRes = null;
		List<DegreeSearchDTO> degreeDTO = null;

		if (mLogger.isDebugEnabled()) {
			startTime = System.nanoTime();
		}

		try {
			degreeDTO = (List<DegreeSearchDTO>) DataDAO.getDegreeDetails();
			degreeRes = new DegreeSearchResponse();
			degreeRes.setSucess(true);
			degreeRes.setDegreeSearchDTO(degreeDTO);
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
					.format("Exiting getDegreeDetails(). Total time to process request: %.4f seconds",
							(((float) endTime - startTime) / NANOS_IN_SECOND)));
		}
		return degreeRes;
	}

	public static DepartmentSearchResponse getDepartmentDetails()
			throws HrReviewException {
		long startTime = 0;
		DepartmentSearchResponse departmentRes = null;
		List<DepartmentSearchDTO> departmentDTO = null;

		if (mLogger.isDebugEnabled()) {
			startTime = System.nanoTime();
		}

		try {

			departmentDTO = (List<DepartmentSearchDTO>) DataDAO
					.getDepartmentDetails();
			departmentRes = new DepartmentSearchResponse();
			departmentRes.setSucess(true);
			departmentRes.setDepartmentSearchDTO(departmentDTO);

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
					.format("Exiting getDepartmentDetails(). Total time to process request: %.4f seconds",
							(((float) endTime - startTime) / NANOS_IN_SECOND)));
		}
		return departmentRes;
	}

	public static DistrictSearchResponse getDistrictDetails()
			throws HrReviewException {
		long startTime = 0;
		DistrictSearchResponse districtRes = null;
		List<DistrictSearchDTO> districtDTO = null;

		if (mLogger.isDebugEnabled()) {
			startTime = System.nanoTime();
		}

		try {
			districtDTO = (List<DistrictSearchDTO>) DataDAO.getDistrictDetails();
			districtRes = new DistrictSearchResponse();
			districtRes.setSucess(true);
			districtRes.setDistrictSearchDTO(districtDTO);

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
					.format("Exiting getDistrictDetails(). Total time to process request: %.4f seconds",
							(((float) endTime - startTime) / NANOS_IN_SECOND)));
		}
		return districtRes;
	}

	public static DivisionSearchResponse getDivisionDetails()
			throws HrReviewException {
		long startTime = 0;
		DivisionSearchResponse divisionRes = null;
		List<DivisionSearchDTO> divisionDTO = null;

		if (mLogger.isDebugEnabled()) {
			startTime = System.nanoTime();
		}

		try {
			divisionDTO = (List<DivisionSearchDTO>) DataDAO
					.getDivisionDetails();
			divisionRes = new DivisionSearchResponse();
			divisionRes.setSucess(true);
			divisionRes.setDivisionSearchDTO(divisionDTO);

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
					.format("Exiting getDivisionDetails(). Total time to process request: %.4f seconds",
							(((float) endTime - startTime) / NANOS_IN_SECOND)));
		}
		return divisionRes;
	}

	public static ReadinessSearchResponse getReadinessDetails()
			throws HrReviewException {
		long startTime = 0;
		ReadinessSearchResponse readinessRes = null;
		List<ReadinessSearchDTO> readinessDTO = null;

		if (mLogger.isDebugEnabled()) {
			startTime = System.nanoTime();
		}

		try {
			readinessDTO = (List<ReadinessSearchDTO>) DataDAO
					.getReadinessDetails();
			readinessRes = new ReadinessSearchResponse();
			readinessRes.setSucess(true);
			readinessRes.setReadinessSearchDTO(readinessDTO);

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
					.format("Exiting getReadinessDetails(). Total time to process request: %.4f seconds",
							(((float) endTime - startTime) / NANOS_IN_SECOND)));
		}
		return readinessRes;
	}

	public static JobTitleSearchResponse getJobTitleDetails()
			throws HrReviewException {
		long startTime = 0;
		JobTitleSearchResponse jobTitleRes = null;
		List<JobTitleSearchDTO> jobTitleDTO = null;

		if (mLogger.isDebugEnabled()) {
			startTime = System.nanoTime();
		}

		try {
			jobTitleDTO = (List<JobTitleSearchDTO>) DataDAO.getJobTitleDetails(false);
			jobTitleRes = new JobTitleSearchResponse();
			jobTitleRes.setSucess(true);
			
			List<String> jobTitleList = new ArrayList<String>();
			List<JobTitleSearchDTO> revisedList = new ArrayList<JobTitleSearchDTO>();
			for (JobTitleSearchDTO jobTitle : jobTitleDTO) {
				
				if (jobTitle != null && !jobTitleList.contains(jobTitle.getJobTitleDescription().trim().toUpperCase())) {
					revisedList.add(jobTitle);
					jobTitleList.add(jobTitle.getJobTitleDescription().trim().toUpperCase());
				}
			}
			jobTitleRes.setJobTitleSearchDTO(revisedList);

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
					.format("Exiting getJobTitleDetails(). Total time to process request: %.4f seconds",
							(((float) endTime - startTime) / NANOS_IN_SECOND)));
		}
		return jobTitleRes;
	}

	public static LanguageSearchResponse getLanguageDetails()
			throws HrReviewException {
		long startTime = 0;
		LanguageSearchResponse languageRes = null;
		List<LanguageSearchDTO> languageDTO = null;

		if (mLogger.isDebugEnabled()) {
			startTime = System.nanoTime();
		}

		try {
			languageDTO = (List<LanguageSearchDTO>) DataDAO
					.getLanguageDetails();
			languageRes = new LanguageSearchResponse();
			languageRes.setSucess(true);
			languageRes.setLanguageSearchDTO(languageDTO);

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

	public static LocationSearchResponse getLocationDetails()
			throws HrReviewException {
		long startTime = 0;
		LocationSearchResponse locationRes = null;
		List<LocationSearchDTO> locationDTO = null;
		
		
		if (mLogger.isDebugEnabled()) {
			startTime = System.nanoTime();
		}

		try {
			locationDTO = (List<LocationSearchDTO>) DataDAO.getLocationDeatils();
			locationRes = new LocationSearchResponse();
			locationRes.setSucess(true);
			locationRes.setLocationSearchDTO(locationDTO);

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
					.format("Exiting getLocationDetails(). Total time to process request: %.4f seconds",
							(((float) endTime - startTime) / NANOS_IN_SECOND)));
		}
		return locationRes;
	}

	public static MajorsSearchResponse getMajorsDetails()
			throws HrReviewException {
		long startTime = 0;
		MajorsSearchResponse majorsRes = null;
		List<MajorsSearchDTO> majorsDTO = null;

		if (mLogger.isDebugEnabled()) {
			startTime = System.nanoTime();
		}

		try {

			majorsDTO = (List<MajorsSearchDTO>) DataDAO.getMajorsDetails();
			majorsRes = new MajorsSearchResponse();
			majorsRes.setSucess(true);
			majorsRes.setMajorsSearchDTO(majorsDTO);

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
					.format("Exiting getMajorsDetails(). Total time to process request: %.4f seconds",
							(((float) endTime - startTime) / NANOS_IN_SECOND)));
		}
		return majorsRes;
	}

	public static PerformanceSearchResponse getPerformanceDetails()
			throws HrReviewException {
		long startTime = 0;
		PerformanceSearchResponse performanceRes = null;
		List<PotentialPerformanceSearchDTO> potentialPerformanceSearchDTO = null;

		if (mLogger.isDebugEnabled()) {
			startTime = System.nanoTime();
		}

		try {
			potentialPerformanceSearchDTO = (List<PotentialPerformanceSearchDTO>) DataDAO
					.getPotentialPerformanceDetails();
			performanceRes = new PerformanceSearchResponse();
			performanceRes.setSucess(true);
			
			List<PotentialPerformanceSearchDTO> revisedList = new ArrayList<PotentialPerformanceSearchDTO>();
			for(PotentialPerformanceSearchDTO temp: potentialPerformanceSearchDTO)
			{
				if(temp.getCategoryCode()== (short) 1)
				{
					revisedList.add(temp);
				}
			}
			performanceRes.setPotentialPerformanceSearchDTO(revisedList);

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
					.format("Exiting getPerformanceDetails(). Total time to process request: %.4f seconds",
							(((float) endTime - startTime) / NANOS_IN_SECOND)));
		}
		return performanceRes;
	}

	public static PotentialSearchResponse getPotentialDetails()
			throws HrReviewException {
		long startTime = 0;
		PotentialSearchResponse potentialRes = null;
		List<PotentialPerformanceSearchDTO> potentialPerformanceSearchDTO = null;

		if (mLogger.isDebugEnabled()) {
			startTime = System.nanoTime();
		}

		try {
			potentialPerformanceSearchDTO = (List<PotentialPerformanceSearchDTO>) DataDAO
					.getPotentialPerformanceDetails();
			potentialRes = new PotentialSearchResponse();
			potentialRes.setSucess(true);
			
			List<PotentialPerformanceSearchDTO> revisedList = new ArrayList<PotentialPerformanceSearchDTO>();
			for(PotentialPerformanceSearchDTO temp: potentialPerformanceSearchDTO)
			{
				if(temp.getCategoryCode()== (short) 2)
				{
					revisedList.add(temp);
				}
			}
			potentialRes.setPotentialPerformanceSearchDTO(revisedList);
			
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
					.format("Exiting getPotentialDetails(). Total time to process request: %.4f seconds",
							(((float) endTime - startTime) / NANOS_IN_SECOND)));
		}
		return potentialRes;
	}
	

	public static JobTitleSearchResponse getPrevTitleDetails() throws HrReviewException {

		long startTime = 0;
		JobTitleSearchResponse jobTitleRes = null;
		List<JobTitleSearchDTO> jobTitleDTO = null;

		if (mLogger.isDebugEnabled()) {
			startTime = System.nanoTime();
		}

		try {
			jobTitleDTO = (List<JobTitleSearchDTO>) DataDAO.getJobTitleDetails(true);

			List<String> jobTitleList = new ArrayList<String>();
			List<JobTitleSearchDTO> revisedList = new ArrayList<JobTitleSearchDTO>();
			for (JobTitleSearchDTO jobTitle : jobTitleDTO) 
			{				
				if (jobTitle != null && !jobTitleList.contains(jobTitle.getJobTitleDescription()) &&
						(!jobTitle.getJobTitleCode().trim().equals("") || !jobTitle.getJobTitleDescription().trim().equals(""))) {
					revisedList.add(jobTitle);
					jobTitleList.add(jobTitle.getJobTitleDescription());
				}
			}
			jobTitleRes = new JobTitleSearchResponse();
			jobTitleRes.setSucess(true);
			jobTitleRes.setJobTitleSearchDTO(revisedList);

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
					.format("Exiting getPrevTitleDetails(). Total time to process request: %.4f seconds",
							(((float) endTime - startTime) / NANOS_IN_SECOND)));
		}
		return jobTitleRes;
	}

	public static RaceSearchResponse getRaceDetails() throws HrReviewException {
		long startTime = 0;
		RaceSearchResponse raceRes = null;
		List<RaceSearchDTO> raceDTO = null;

		if (mLogger.isDebugEnabled()) {
			startTime = System.nanoTime();
		}

		try {
			raceDTO = (List<RaceSearchDTO>) DataDAO.getRaceDetails();
			raceRes = new RaceSearchResponse();
			raceRes.setSucess(true);
			//Remove empty Spaces
			for(int i = 0; i < raceDTO.size(); i++)
			{
				if (raceDTO.get(i) != null)
				{
					if(raceDTO.get(i).getRaceCode().trim().isEmpty() || raceDTO.get(i).getRaceDescription().trim().isEmpty())
					{
						raceDTO.remove(i);
					}
				}
			}
			raceRes.setRaceSearchDTO(raceDTO);

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
					.format("Exiting getRaceDetails(). Total time to process request: %.4f seconds",
							(((float) endTime - startTime) / NANOS_IN_SECOND)));
		}
		return raceRes;
	}

	public static RegionSearchResponse getRegionDetails()
			throws HrReviewException {
		long startTime = 0;
		RegionSearchResponse regionRes = null;
		List<RegionSearchDTO> regionDTO = null;

		if (mLogger.isDebugEnabled()) {
			startTime = System.nanoTime();
		}

		try {
			regionDTO = (List<RegionSearchDTO>) DataDAO.getRegionDetails();
			regionRes = new RegionSearchResponse();
			regionRes.setSucess(true);
			regionRes.setRegionSearchDTO(regionDTO);

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
					.format("Exiting getRegionDetails(). Total time to process request: %.4f seconds",
							(((float) endTime - startTime) / NANOS_IN_SECOND)));
		}
		return regionRes;
	}

	public static SchoolSearchResponse getSchoolDetails()
			throws HrReviewException {
		long startTime = 0;
		SchoolSearchResponse schoolRes = null;
		List<GetSchoolSearchDTO> schoolDTO = null;

		if (mLogger.isDebugEnabled()) {
			startTime = System.nanoTime();
		}

		try {
			schoolDTO = (List<GetSchoolSearchDTO>) DataDAO.getSchoolDetails();
			schoolRes = new SchoolSearchResponse();
			schoolRes.setSucess(true);
			schoolRes.setSchoolSearchDTO(schoolDTO);

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
					.format("Exiting getSchoolDetails(). Total time to process request: %.4f seconds",
							(((float) endTime - startTime) / NANOS_IN_SECOND)));
		}
		return schoolRes;
	}

	public static StoreSearchResponse getStoreDetails()
			throws HrReviewException {
		long startTime = 0;
		StoreSearchResponse storeRes = null;
		List<StoreSearchDTO> storeDTO = null;

		if (mLogger.isDebugEnabled()) {
			startTime = System.nanoTime();
		}

		try {
			storeDTO = (List<StoreSearchDTO>) DataDAO.getStoreDetails();
			storeRes = new StoreSearchResponse();
			storeRes.setSucess(true);
			storeRes.setStoreSearchDTO(storeDTO);

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
					.format("Exiting getStoreDetails(). Total time to process request: %.4f seconds",
							(((float) endTime - startTime) / NANOS_IN_SECOND)));
		}
		return storeRes;
	}

}

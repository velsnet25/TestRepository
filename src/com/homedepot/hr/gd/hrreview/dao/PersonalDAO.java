package com.homedepot.hr.gd.hrreview.dao;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.homedepot.hr.gd.hrreview.dto.BasicInformationDTO;
import com.homedepot.hr.gd.hrreview.dto.CityInfoDTO;
import com.homedepot.hr.gd.hrreview.dto.CourseDTO;
import com.homedepot.hr.gd.hrreview.dto.EnterpriseWorkAddressDTO;
import com.homedepot.hr.gd.hrreview.dto.HomeAddressDTO;
import com.homedepot.hr.gd.hrreview.dto.LanguageDTO;
import com.homedepot.hr.gd.hrreview.dto.SchoolDTO;
import com.homedepot.hr.gd.hrreview.dto.WorkAddressDTO;
import com.homedepot.hr.gd.hrreview.dto.WorkContactDTO;
import com.homedepot.hr.gd.hrreview.dto.WorkHistoryExternalDTO;
import com.homedepot.hr.gd.hrreview.dto.WorkHistoryHomeDepotDTO;
import com.homedepot.hr.gd.hrreview.dto.WorkHistoryHomeDepotPre95DTO;
import com.homedepot.hr.gd.hrreview.interfaces.Constants;
import com.homedepot.hr.gd.hrreview.interfaces.DAOConstants;
import com.homedepot.ta.aa.dao.builder.DAO;
import com.homedepot.ta.aa.dao.exceptions.QueryException;

public class PersonalDAO implements DAOConstants, Constants {

	private static final Logger logger = Logger.getLogger(PersonalDAO.class);

	public static List<WorkHistoryExternalDTO> getWorkHistoryDetailsExternal(
			String associateId) throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start getWorkHistoryDetailsExternal");
			logger.debug("associateId: \"" + associateId + "\"");
			logger.debug("executing the query");
		}
		return DAO
				.select("WorkforceSuccessionPlanning.1.readSuccessionPlanEmployeeEmploymentHistoryAndTesseractCrossReferenceDetails") //TODO: Verify contract name, version, and replace values below
				//				.input("employmentHistoryTypeIndicator", "value") // optional
				.input("unmatchedEmploymentHistoryTypeIndicator", "I") // optional
				.inputAllowNull("zeroEmplid", associateId) // can be null, optional
				//				.qualify("sortParameter", "value") // optional
				.list(WorkHistoryExternalDTO.class);
	}

	public static List<WorkHistoryHomeDepotPre95DTO> getWorkHistoryDetailsHomeDepotPre95(
			String associateId) throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start getWorkHistoryDetailsHomeDepotPre95");
			logger.debug("associateId: \"" + associateId + "\"");
			logger.debug("executing the query");
		}
		return DAO
				.select("WorkforceSuccessionPlanning.1.readSuccessionPlanEmployeeEmploymentHistoryAndTesseractCrossReferenceDetails") //TODO: Verify contract name, version, and replace values below
				.input("employmentHistoryTypeIndicator", "I") // optional
				//.input("unmatchedEmploymentHistoryTypeIndicator", "value") // optional
				.inputAllowNull("zeroEmplid", associateId) // can be null, optional
				//.qualify("sortParameter", "value") // optional
				.list(WorkHistoryHomeDepotPre95DTO.class);
	}

	public static List<WorkHistoryHomeDepotDTO> getWorkHistoryDetailsHomeDepot(
			String associateId) throws QueryException {
		
		if (logger.isDebugEnabled()) {
			logger.debug("start getWorkHistoryDetailsHomeDepot");
			logger.debug("associateId: \"" + associateId + "\"");
			logger.debug("executing the query");
		}
		
		List<Object> inputRecordStatusList = new ArrayList<Object>();
		inputRecordStatusList.add("A");
		inputRecordStatusList.add("");
		Date date = new Date(System.currentTimeMillis());

		return DAO
				.select("WorkforceSuccessionPlanning.1.readNlsHumanResourceSystemStoreOrganizationAndDivisionDetails") //TODO: Verify contract name, version, and replace values below
				.input("humanResourcesStoreEffectiveBeginDate",
						new Date(System.currentTimeMillis())) // optional
				.inputAllowNull("humanResourcesStoreEffectiveEndDate",
						new Date(System.currentTimeMillis())) // can be null, optional
				.input("languageCode", DAOConstants.lang_code) // optional
				.input("actionLanguageCode", DAOConstants.lang_code) // optional
				.inputAllowNull("effectiveDate",
						new Date(System.currentTimeMillis())) // can be null, optional
				.input("recordStatusList", inputRecordStatusList) // required
				.input("divisionHumanResourcesStoreEffectiveBeginDate",
						new Date(System.currentTimeMillis())) // optional
				.input("effectiveBeginDate",
						new Date(System.currentTimeMillis())) // optional
				.inputAllowNull("zeroEmployeeId", associateId) // can be null, optional
				.input("jobLanguageCode", DAOConstants.lang_code) // optional
				.qualify("sortParameter", "POS.eff_dt desc, Pos.rel_end_dt desc, Pos.ORGANIZATION_1 DESC, Pos.JOB_TITLE_ID")
				.qualify("effectiveBeginDateLessThanEqualTo", date) // optional
				.qualify("effectiveDateLessThanEqualTo", date) // optional
				.qualify("humanResourcesStoreEffectiveBeginDateLessThanEqualTo",date) // optional
				.qualify("humanResourcesStoreEffectiveEndDateGreaterThanEqualTo", date) // optional
				.list(WorkHistoryHomeDepotDTO.class);
	}

	public static WorkAddressDTO getWorkAddressDetails(String associateId)
			throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start getWorkAddressDetails");
			logger.debug("associateId: \"" + associateId + "\"");
			logger.debug("executing the query");
		}
		/*return DAO
				.select("WorkforceSuccessionPlanning.1.readSuccessionPlanEmployeeAndEnterpriseStoreDetails") //TODO: Verify contract name, version, and replace values below
				.inputAllowNull("zeroEmplid", associateId) // can be null, optional

				.list(WorkAddressDTO.class);*/

		String storeNbr = DAO
				.useJNDI(workForceSuccessionPlanning)
				.setSQL("select S.HR_SYS_STR_NBR "
						+ "from qa1hr.HR_EMPL_TSRT_XREF X, "
						+ "qa1hr.spempl S " + "where X.Z_EMPLID = ? "
						+ "and X.TSRT_EMPL_ID = S.HR_SYS_EMPL_ID WITH UR", associateId)
				.get(String.class);

		List<WorkContactDTO> workContactInfoList = readWorkContactInfo(storeNbr);
		List<EnterpriseWorkAddressDTO> ewa = readEnterprisePartySourceCrossReferenceAndAddressDetails(storeNbr);
		WorkAddressDTO workAddress = new WorkAddressDTO();
		if (!ewa.isEmpty()) {
			workAddress.setAddressLineOneLong(ewa.get(0).getAddressLineOneText());
			workAddress.setAddressLineTwoLong(ewa.get(0).getAddressLineTwoText());
			workAddress.setAddressLineThreeText(ewa.get(0).getAddressLineThreeText());
			workAddress.setAddressLine4Text(ewa.get(0).getAddressLineFourText());
			workAddress.setAddressCity(ewa.get(0).getCityName());
			workAddress.setCounty(ewa.get(0).getCountryName()); //Country = County?
			workAddress.setStateProvince(ewa.get(0).getStateCode());
			workAddress.setPostalCodeEleven(ewa.get(0).getPostalCode());
			workAddress.setCountry(ewa.get(0).getCountryCode());
		}
			workAddress.setEmail(PersonalDAO.getSysUserEmail(associateId));
			
		if (!workContactInfoList.isEmpty()) {
			for (WorkContactDTO wcDTO : workContactInfoList) {
				//if 1 then Phone #, 4 then FAX
				if (wcDTO.getContactMethodCode() == 1) {
					workAddress.setPhoneNumber(wcDTO.getPhoneAreaCityCode()
							+ wcDTO.getPhoneLocalNumber());
					workAddress.setPhoneExtension(wcDTO
							.getPhoneExtensionNumber());
				} else if (wcDTO.getContactMethodCode() == 4) {
					workAddress.setFaxNumber(wcDTO.getPhoneAreaCityCode()
							+ wcDTO.getPhoneLocalNumber());
				}
			}
		}
		return workAddress;
	}

	public static List<HomeAddressDTO> getHomeAddressDetails(String associateId)
			throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start getHomeAddressDetails");
			logger.debug("associateId: \"" + associateId + "\"");
			logger.debug("executing the query");
		}
		List<Object> inputAddressRecordStatusList = new ArrayList<Object>();
		inputAddressRecordStatusList.add("A");
		inputAddressRecordStatusList.add("");
		List<Object> inputRecordStatusList = new ArrayList<Object>();
		inputRecordStatusList.add("A");
		inputRecordStatusList.add("");
		Date date = new Date(System.currentTimeMillis());

		return DAO
				.select("WorkforceSuccessionPlanning.1.readSuccessionPlanEmployeeAddressAndTypeDetails") //TODO: Verify contract name, version, and replace values below
				.inputAllowNull("effectiveDate",
						new Date(System.currentTimeMillis())) // can be null, optional
				.input("addressType", new BigDecimal(1)) // optional
				.input("addressRecordStatusList", inputAddressRecordStatusList) // optional
				.input("recordStatusList", inputRecordStatusList) // optional
				.inputAllowNull("zeroEmplid", associateId) // can be null, optional
				.input("addressRoleCode", (short) 1) // optional
				.input("addressPreferenceNumber", (short) 1) // optional
				.qualify("effectiveDateLessThanEqualTo", date) // optional
				.list(HomeAddressDTO.class);
	}

	public static List<CityInfoDTO> getCityInfoDetails(String associateId)
			throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start getCityInfoDetails");
			logger.debug("associateId: \"" + associateId + "\"");
			logger.debug("executing the query");
		}
		final List<Object> inputContactMethodCodeList = new ArrayList<Object>();
		inputContactMethodCodeList.add((short) 2);
		inputContactMethodCodeList.add((short) 4);
		final List<Object> inputMethodCodeList = new ArrayList<Object>();
		inputMethodCodeList.add((short) 1);
		inputMethodCodeList.add((short) 5);

		return DAO
				.select("WorkforceSuccessionPlanning.1.readSuccessionPlanEmployeeContactMechanismAndTesseractCrossReferenceDetails") //TODO: Verify contract name, version, and replace values below
				.inputAllowNull("zeroEmplid", associateId) // can be null, optional
				.input("contactMechanismRoleCode", (short) 2) // optional
				.input("mechanismRoleCode", (short) 1) // optional
				.input("contactMethodCodeList", inputContactMethodCodeList) // optional
				.input("methodCodeList", inputMethodCodeList) // optional
				.input("contactMechanismPreferenceNumber", (short) 1) // optional

				.list(CityInfoDTO.class);
	}

	public static List<BasicInformationDTO> getBasicInformation(
			String associateId) throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start getBasicInformation");
			logger.debug("associateId: \"" + associateId + "\"");
			logger.debug("executing the query");
		}
		
		List<Object> inputRecordStatusList = new ArrayList<Object>();
		Date date = new Date(System.currentTimeMillis());
		
		return DAO
				.select("WorkforceSuccessionPlanning.1.readNlsSuccessionPlanEmployeeAndRaceDetails") //TODO: Verify contract name, version, and replace values below
				.inputAllowNull("zeroEmployeeId", associateId) // can be null, optional
				.input("caseInput", "0") // required
				.inputAllowNull("effectiveDate",
						new Date(System.currentTimeMillis())) // can be null, optional
				.input("humanResourcesStoreEffectiveBeginDate",
						new Date(System.currentTimeMillis())) // optional
				.inputAllowNull("humanResourcesStoreEffectiveEndDate",
						new Date(System.currentTimeMillis())) // can be null, optional
				.input("effectiveBeginDate",
						new Date(System.currentTimeMillis())) // optional
				.input("languageCode", DAOConstants.lang_code) // required
				.input("storeEffectiveBeginDate",
						new Date(System.currentTimeMillis())) // optional
				.input("tableNumber", "00009") // optional
				.input("startDate", new Date(System.currentTimeMillis())) // optional
				.input("endDate", new Date(System.currentTimeMillis())) // optional
				.input("humanResourceEffectiveBeginDate",
						new Date(System.currentTimeMillis())) // optional
				//				.input("recordStatusList", inputRecordStatusList) // optional
				.input("countryLanguageCode", DAOConstants.lang_code) // optional				
				//				.input("recordStatusList", inputRecordStatusList) // optional
				.qualify(
						"humanResourcesStoreEffectiveBeginDateLessThanEqualTo",
						date) // optional
				.qualify("humanResourcesStoreEffectiveEndDateGreaterThan", date) // optional
				.qualify("effectiveBeginDateLessThanEqualTo", date) // optional
				.qualify("humanResourceEffectiveBeginDateLessThanEqualTo", date) // optional
				.qualify("startDateLessThanEqualTo", date) // optional
				.qualify("endDateGreaterThanEqualTo", date) // optional
				.qualify("storeEffectiveBeginDateLessThanEqualTo", date) // optional
				.qualify("effectiveDateLessThanEqualTo", date) // optional
				.list(BasicInformationDTO.class);
	}

	public static List<LanguageDTO> getLanguageDetails(String associateId)
			throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start getLanguageDetails");
			logger.debug("associateId: \"" + associateId + "\"");
			logger.debug("executing the query");
		}
		return DAO
				.select("WorkforceSuccessionPlanning.1.readSuccesionPlanningEmployeeLanguageUseAndHumanResourceTransferReferenceDetails") //TODO: Verify contract name, version, and replace values below
				.inputAllowNull("zeroEmployeeId", associateId) // can be null, optional

				.list(LanguageDTO.class);
	}

	public static List<SchoolDTO> getSchoolDetails(String associateId)
			throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start getSchoolDetails");
			logger.debug("associateId: \"" + associateId + "\"");
			logger.debug("executing the query");
		}
		return DAO
				.select("WorkforceSuccessionPlanning.1.readSuccessionPlanEmployeeEducationAndFacilityDetails") //TODO: Verify contract name, version, and replace values below
				.inputAllowNull("zeroEmployeeId", associateId) // can be null, optional

				.list(SchoolDTO.class);
	}

	public static List<CourseDTO> getCourseDetails(String associateId)
			throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start getCourseDetails(String)");
			logger.debug("associateId: \"" + associateId + "\"");
			logger.debug("start getCourseDetails");
		}
		return DAO
				.select("WorkforceSuccessionPlanning.1.readSuccessionPlanEmployeeCourseAndHumanResourceTransferReferenceDetails") //TODO: Verify contract name, version, and replace values below
				.inputAllowNull("zeroEmployeeId", associateId) // can be null, optional

				.list(CourseDTO.class);
	}

	public static List<WorkContactDTO> readWorkContactInfo(String storeNbr)
			throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start readNlsContactMethodCodeAndEnterpriseContactMechanismDetails");
			logger.debug("Store Number: " + storeNbr);
			logger.debug("executing the query");
		}
		List<Object> inputContactMethodCodeList = new ArrayList<Object>();

		inputContactMethodCodeList.add((short) 1);
		inputContactMethodCodeList.add((short) 4);

		return DAO
				.select("BusinessOrganizationThdOrganization.2.readNlsContactMethodCodeAndEnterpriseContactMechanismDetails") //TODO: Verify contract name, version, and replace values below
				.input("languageCode", LANG_EN_US) // optional
				.inputAllowNull("humanResourcesSystemStoreNumber", storeNbr) // can be null, optional
				.input("contactMethodCodeList", inputContactMethodCodeList) // optional

				.list(WorkContactDTO.class);
	}

	public static List<EnterpriseWorkAddressDTO> readEnterprisePartySourceCrossReferenceAndAddressDetails(String strNbr)
			throws QueryException {
		if (logger.isDebugEnabled()) {
			logger.debug("start readEnterprisePartySourceCrossReferenceAndAddressDetails");
			logger.debug("Store Number: " + strNbr);
			logger.debug("executing the query");
		}
		List<Short> inputBusinessUnitTypeCodeList = new ArrayList<Short>();
		Short[] codeList = new Short[]{3,4,6,7,8,9,12} ;
		inputBusinessUnitTypeCodeList.addAll(Arrays.asList(codeList));


		return DAO
				.select("BusinessOrganizationThdOrganization.2.readEnterprisePartySourceCrossReferenceAndAddressDetails") //TODO: Verify contract name, version, and replace values below
				.inputAllowNull("humanResourcesSystemStoreNumber", strNbr) // can be null, optional
					.input("contactMechanismRoleCode", (short) 1) // optional
					.input("contactMechanismPreferenceNumber", (short) 1) // optional
					.input("contactMethodCode", (short) 8) // optional
					.inputAllowNull("effectiveEndDate",
							new Date(System.currentTimeMillis())) // can be null, optional
					.input("businessUnitTypeCodeList",
							inputBusinessUnitTypeCodeList) // optional
					.qualify("effectiveEndDateGreaterThanEqualTo", true) // optional
				.list(EnterpriseWorkAddressDTO.class);
	}

	public static String getSysUserEmail(String associateID) throws QueryException
	{
		if (logger.isDebugEnabled()) {
			logger.debug("start getSysUserEmail(String)");
			logger.debug("associateID: \"" + associateID + "\"");
			logger.debug("executing the query");
		}
		
		return DAO
				.useJNDI(workForceSuccessionPlanning)
				.setSQL("SELECT EMAIL_ADDR_TXT " 
				+ "FROM EPR_ASSOC EA "
				+ ", SYSUSR SU "
				+ " WHERE EA.HR_ASSOC_ID = ? "
				+ " AND EA.ASSOC_ID = SU.PRSN_ID "
				+ "WITH UR", associateID )   
				.get(String.class);
	}

}

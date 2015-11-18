package com.homedepot.hr.gd.hrreview.bl;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.homedepot.hr.gd.hrreview.dao.AdvancedSearchDAO;
import com.homedepot.hr.gd.hrreview.dto.AdvancedQuickSearchCategoryDTO;
import com.homedepot.hr.gd.hrreview.dto.AdvancedSearchCategoryDTO;
import com.homedepot.hr.gd.hrreview.dto.AdvancedSearchDTO;
import com.homedepot.hr.gd.hrreview.dto.ApplyAllAssociateDTO;
import com.homedepot.hr.gd.hrreview.dto.ApplyAssociateDTO;
import com.homedepot.hr.gd.hrreview.dto.CourseSearchDTO;
import com.homedepot.hr.gd.hrreview.dto.FindDTO;
import com.homedepot.hr.gd.hrreview.dto.LoadQueryDTO;
import com.homedepot.hr.gd.hrreview.exceptions.HrReviewApplLogMessage;
import com.homedepot.hr.gd.hrreview.exceptions.HrReviewException;
import com.homedepot.hr.gd.hrreview.interfaces.Constants;
import com.homedepot.hr.gd.hrreview.request.FindRequest;
import com.homedepot.hr.gd.hrreview.response.AdvancedSearchCategoryResponse;
import com.homedepot.hr.gd.hrreview.response.AdvancedSearchResponse;
import com.homedepot.hr.gd.hrreview.response.ApplyAssociateResponse;
import com.homedepot.hr.gd.hrreview.response.DeleteQueryResponse;
import com.homedepot.hr.gd.hrreview.response.FindResponse;
import com.homedepot.hr.gd.hrreview.response.LoadQueryResponse;
import com.homedepot.hr.gd.hrreview.response.SaveSQLQueriesResponse;
import com.homedepot.hr.gd.hrreview.response.ApplyAllAssociateResponse;
import com.homedepot.hr.gd.hrreview.util.AppUtil;
import com.homedepot.ta.aa.dao.exceptions.QueryException;

public class AdvancedSearchManager implements Constants {

	private static final Logger mLogger = Logger.getLogger(AdvancedSearchManager.class);
	
	//Get Search Categories: Quick Search Categories & Advanced Search Dropdown
	public static AdvancedSearchCategoryResponse getSearchCategoryResults() throws HrReviewException 
	{
		long startTime = 0;
		AdvancedSearchCategoryResponse advancedSearchCategoryRes = null;
		List<AdvancedQuickSearchCategoryDTO> advancedQuickSearchCategoryDTO = null;
		List<AdvancedSearchCategoryDTO> advancedSearchCategoryDTO = null;
		
		if (mLogger.isDebugEnabled()) {
			startTime = System.nanoTime();
		}

		try {
			advancedQuickSearchCategoryDTO = (List<AdvancedQuickSearchCategoryDTO>) AdvancedSearchDAO.getAdvancedQuickSearchCategoryDetails();					
			advancedSearchCategoryDTO = (List<AdvancedSearchCategoryDTO>) AdvancedSearchDAO.getAdvancedSearchCategoryDetails();					
			
			advancedSearchCategoryRes = new AdvancedSearchCategoryResponse();
			advancedSearchCategoryRes.setSuccess(true);
			advancedSearchCategoryRes.setAdvancedQuickSearchCategoryDTO(advancedQuickSearchCategoryDTO);
			advancedSearchCategoryRes.setAdvancedSearchCategoryDTO(advancedSearchCategoryDTO);
			
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
//			mLogger.debug(String
//					.format("Exiting getSearchCategoryResults(). Total time to process request: %.4f seconds",
//							(((float) endTime - startTime) / NANOS_IN_SECOND)));
		}
		return advancedSearchCategoryRes;
	}
	
	//Get results for the quick search by inputting the String of what was selected
	public static AdvancedSearchResponse getAdvancedQuickSearch(String selected) throws HrReviewException 
	{
		long startTime = 0;
		AdvancedSearchResponse advancedSearchRes = null;
		List<AdvancedSearchDTO> advancedSearchDTO = null;

		if (mLogger.isDebugEnabled()) {
			startTime = System.nanoTime();
		}

		try {
			String ldap = AppUtil.getLdapId();			
			advancedSearchDTO = (List<AdvancedSearchDTO>) AdvancedSearchDAO.getAdvancedQuickSearchDetails(ldap, selected);					
			advancedSearchRes = new AdvancedSearchResponse();
			
			if (advancedSearchDTO != null && advancedSearchDTO.size() > 10000) {
				advancedSearchRes.setMessage(Constants.ERROR_MEESAGE_MORE_THAN_10K_RESULTS);
				advancedSearchRes.setSuccess(false);
			} else if (advancedSearchDTO != null && advancedSearchDTO.size() > 0) {	
				advancedSearchRes.setSuccess(true);
				List<String> ldapList = new ArrayList<String>();
				List<AdvancedSearchDTO> revisedList = new ArrayList<AdvancedSearchDTO>();
				for (AdvancedSearchDTO searchDTO : advancedSearchDTO) {				
					if (searchDTO != null && !ldapList.contains(searchDTO.getZeroEmployeeId().trim())) {
						revisedList.add(searchDTO);
						ldapList.add(searchDTO.getZeroEmployeeId().trim());
					}
				}
				advancedSearchRes.setAdvancedQuickSearchDTO(revisedList);
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
					.format("Exiting getAdvancedQuickSearch(). Total time to process request: %.4f seconds",
							(((float) endTime - startTime) / NANOS_IN_SECOND)));
		}
		return advancedSearchRes;
	}
	
	//Get results for advanced search by inputting a list of the Advanced Search queries
	public static AdvancedSearchResponse getAdvancedSearch(List<AdvancedSearch> query) throws HrReviewException{
		long startTime = 0;
		AdvancedSearchResponse advancedSearchRes = null;
		List<AdvancedSearchDTO> advancedSearchDTO = null;

		if (mLogger.isDebugEnabled()) {
			startTime = System.nanoTime();
		}
		String ldap = AppUtil.getLdapId();
		try {
			for(int i = 0; i < query.size(); i++)
			{
				//Set Like booleans
				if(query.get(i).getDataValue().contains("%"))
				{
					int location = query.get(i).getDataValue().indexOf("%");
					int location2 = query.get(i).getDataValue().lastIndexOf("%"); 
					//Check if there are 2 locations
					if(location != location2)
					{
						query.get(i).setContainsLike(true);
					}
					else
					{
						if(location == 0)
						{
							query.get(i).setEndsWithLike(true);
						}
						else if(location == (query.get(i).getDataValue().length()-1))
						{
							query.get(i).setBeginsWithLike(true);
						}
					}
					query.get(i).setDataValue(query.get(i).getDataValue().replaceAll("%", ""));
				}
				//Set Input List if written in dataValue by user
				else if(query.get(i).getDataValue().contains(",") && query.get(i).getInputList().size() == 0)
				{
					query.get(i).setInputList(new ArrayList<String>(Arrays.asList((query.get(i).getDataValue().split(",")))));
					query.get(i).setDataValue("");
				}
			}
			
			advancedSearchDTO = (List<AdvancedSearchDTO>) AdvancedSearchDAO.getAdvancedSearchDetails(query, ldap);					
			advancedSearchRes = new AdvancedSearchResponse();

			if (advancedSearchDTO != null && advancedSearchDTO.size() > 10000) {
				advancedSearchRes.setMessage(Constants.ERROR_MEESAGE_MORE_THAN_10K_RESULTS);
				advancedSearchRes.setSuccess(false);
			} else if (advancedSearchDTO != null && advancedSearchDTO.size() > 0) {	
				advancedSearchRes.setSuccess(true);
				advancedSearchRes.setAdvancedQuickSearchDTO(advancedSearchDTO);
				//Remove duplicates
//				List<String> ldapList = new ArrayList<String>();
//				List<AdvancedSearchDTO> revisedList = new ArrayList<AdvancedSearchDTO>();
//				for (AdvancedSearchDTO searchDTO : advancedSearchDTO) {				
//					if (searchDTO != null && !ldapList.contains(searchDTO.getZeroEmployeeId().trim())) {
//						revisedList.add(searchDTO);
//						ldapList.add(searchDTO.getZeroEmployeeId().trim());
//					}
//				}
//				if (revisedList != null && revisedList.size() > 10000) {
//					advancedSearchRes.setMessage(Constants.ERROR_MEESAGE_MORE_THAN_10K_RESULTS);
//					advancedSearchRes.setSuccess(false);
//				} else {
//					advancedSearchRes.setAdvancedQuickSearchDTO(revisedList);
//				}
				
				
			}
		} 
		catch (QueryException e) {
			mLogger.error(HrReviewApplLogMessage.create(
					HrReviewApplLogMessage.FATAL_ERROR,
					ERROR_SEARCH_CANDIDATE_LIST_QUERY, e));
			throw new HrReviewException(HrReviewApplLogMessage.FATAL_ERROR,
					ERROR_SEARCH_CANDIDATE_LIST_QUERY, e);
		} catch (ParseException e) {
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
					.format("Exiting getAdvancedSearch(). Total time to process request: %.4f seconds",
							(((float) endTime - startTime) / NANOS_IN_SECOND)));
		}
		return advancedSearchRes;
	}

	//Save SQL query into SQL list and SQL Parm List
	public static SaveSQLQueriesResponse saveSQLQuery(List<AdvancedSearch> query, String description, String queryName) throws HrReviewException 
	{
		String ldap = AppUtil.getLdapId();
		long startTime = 0;
		SaveSQLQueriesResponse saveSQLQueriesRes = null;
		int countSQL = 0;
		boolean successFromService;
		boolean successFromParameterService = false;
		if (mLogger.isDebugEnabled()) {
			startTime = System.nanoTime();
		}

		try {
			//Find last number of list and add 1 for the next SQL id
			if (mLogger.isDebugEnabled()) {
				mLogger.info("Getting the count of the list");
			}
			List<Integer> countList =  AdvancedSearchDAO.getQueryId();
			if(countList != null && countList.size() > 0)
				countSQL = countList.get(0) + 1;
			
			if (mLogger.isDebugEnabled()) {
				mLogger.info("Inserting into query table");
			}
			successFromService = AdvancedSearchDAO.saveSQLQuery(countSQL, ldap, description, queryName);
			
			if(query != null)
			{
				int dataField = -1;
				String openParentValue = "";
				String operator = "";
				String dataValue = "";
				String andOr = "";
				String closeParentValue = "";
				for(int i = 0; i < query.size(); i++)
				{
					//1 Int SQL ID
					//2 Int Row Counter
					//3 Int Data Field Code
					//4 String Open Bracket Y or N
					//5 String Operator
					//6 String Data Value + "$$" + Data Value Code
					//7 String And/Or
					//8 String Close Bracket Y or N
					//9 String ldap
					dataField = query.get(i).getDataFieldCode();
					
					if (query.get(i).getOpenParenVal() != null && query.get(i).getOpenParenVal().equalsIgnoreCase("(")) {
						openParentValue = "Y";
					} else 
						openParentValue = "N";
					
					operator = query.get(i).getOperator();
					
					if( (query.get(i).getDataValueCode()!=null) && (!query.get(i).getDataValueCode().equals(null)) &&
							(query.get(i).getDataValueCode().trim().length()>0)	)
						{
							dataValue = query.get(i).getDataValue() + "$$" + query.get(i).getDataValueCode();
						}
						else
						{
							dataValue = query.get(i).getDataValue();
						}
						
					andOr = query.get(i).getAndOr();
					if (query.get(i).getCloseParenVal() != null && query.get(i).getCloseParenVal().equalsIgnoreCase(")")) {
						closeParentValue = "Y";
					} else closeParentValue = "N";
					
					if (mLogger.isDebugEnabled()) {
						mLogger.info("Inserting into parm table");
					}
					AdvancedSearchDAO.saveSQLQueryParameter(countSQL, (short)i, dataField, openParentValue, operator, dataValue, andOr, closeParentValue, ldap);
				}
			}
			saveSQLQueriesRes = new SaveSQLQueriesResponse();
			saveSQLQueriesRes.setSuccessFromService(successFromService);
			saveSQLQueriesRes.setSuccessFromParameterService(successFromParameterService);
			
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
					.format("Exiting saveSQLQuery(). Total time to process request: %.4f seconds",
							(((float) endTime - startTime) / NANOS_IN_SECOND)));
		}
		return saveSQLQueriesRes;
	}
	
	//Delete SQL Query from list by passing in the ID number that is to be deleted
	public static DeleteQueryResponse deleteSQLQuery(int sqlId) throws HrReviewException 
	{
		long startTime = 0;
		DeleteQueryResponse deleteQueryRes = null;
		boolean successDeleteQuery = false;
		boolean successDeleteQueryParm = false;

		if (mLogger.isDebugEnabled()) {
			startTime = System.nanoTime();
		}

		try {
			successDeleteQuery = AdvancedSearchDAO.deleteQuery(sqlId);			
			successDeleteQueryParm = AdvancedSearchDAO.deleteQueryParm(sqlId);		
			deleteQueryRes = new DeleteQueryResponse();
			deleteQueryRes.setSuccess(true);
			deleteQueryRes.setDeleteQuery(successDeleteQuery);
			deleteQueryRes.setDeleteQueryParm(successDeleteQueryParm);

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
					.format("Exiting deleteSQLQuery(). Total time to process request: %.4f seconds",
							(((float) endTime - startTime) / NANOS_IN_SECOND)));
		}
		return deleteQueryRes;
	}

	public static ApplyAssociateResponse applyAssociate() throws HrReviewException  {
	
		long startTime = 0;
		ApplyAssociateResponse applyAssociateRes = null;
		ApplyAssociateDTO applyAssociateDTO = null;
		
		
		if (mLogger.isDebugEnabled()) {
			startTime = System.nanoTime();
		}
	
		try {
			String ldap = AppUtil.getLdapId();
			
			applyAssociateDTO = (ApplyAssociateDTO) AdvancedSearchDAO.applyAssociate(ldap);	
			boolean success1 = AdvancedSearchDAO.applyAssociateUpdate(ldap, true, false);
			boolean success2 = AdvancedSearchDAO.applyAssociateUpdate(ldap, false, false);	
	
			applyAssociateRes = new ApplyAssociateResponse();
			applyAssociateRes.setApplyAssociateDTO(applyAssociateDTO);
			
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
					.format("Exiting geCandidateSearchList(). Total time to process request: %.4f seconds",
							(((float) endTime - startTime) / NANOS_IN_SECOND)));
		}
		return applyAssociateRes;
	}

	//Load SQL queries for the ldap
	public static LoadQueryResponse loadSQLQuery() throws HrReviewException {
		long startTime = 0;
		LoadQueryResponse loadQueryRes = null;
		List<LoadQueryDTO> successLoadQuery = null;
		String ldap = AppUtil.getLdapId();
		
		if (mLogger.isDebugEnabled()) {
			startTime = System.nanoTime();
		}

		try {
			successLoadQuery = AdvancedSearchDAO.loadQuery(ldap);			
			loadQueryRes = new LoadQueryResponse();
			loadQueryRes.setSuccess(true);
			loadQueryRes.setLoadQuery(successLoadQuery);
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
					.format("Exiting loadSQLQuery(). Total time to process request: %.4f seconds",
							(((float) endTime - startTime) / NANOS_IN_SECOND)));
		}
		return loadQueryRes;
	}
	
	public static ApplyAllAssociateResponse applyAllAssociate() throws   HrReviewException{
		long startTime = 0;
		ApplyAllAssociateResponse applyAllAssociateRes = null;
		ApplyAllAssociateDTO applyAllAssociateDTO = null;

		if (mLogger.isDebugEnabled()) {
			startTime = System.nanoTime();
		}

		try {
			String ldap = AppUtil.getLdapId();
			applyAllAssociateDTO = (ApplyAllAssociateDTO) AdvancedSearchDAO.applyAllAssociate(ldap);
			boolean success1 = AdvancedSearchDAO.applyAssociateUpdate(ldap, true, true);
			boolean success2 = AdvancedSearchDAO.applyAssociateUpdate(ldap, false, true);						
			applyAllAssociateRes = new ApplyAllAssociateResponse();
			applyAllAssociateRes.setApplyAssociateDTO(applyAllAssociateDTO);

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
					.format("Exiting geCandidateSearchList(). Total time to process request: %.4f seconds",
							(((float) endTime - startTime) / NANOS_IN_SECOND)));
		}
		return applyAllAssociateRes;
	}
	
	//Get results from find page
	public static FindResponse getFindDetails(final FindRequest findRequest) throws HrReviewException {

		long startTime = 0;
		FindResponse findRes = null;
		List<FindDTO> findDTO = null;
		if (mLogger.isDebugEnabled()) {
			startTime = System.nanoTime();
		}

		try {
			mLogger.info("using getLdapId");
			String ldap = AppUtil.getLdapId();
			
			findRes = new FindResponse();
			findDTO = AdvancedSearchDAO.getFind(ldap, findRequest);
			
			if (findDTO != null && findDTO.size() > 10000) {
				findRes.setMessage(Constants.ERROR_MEESAGE_MORE_THAN_10K_RESULTS);
				findRes.setSuccess(false);
			} else if (findDTO != null && findDTO.size() > 0) {	
				findRes.setCount(findDTO.size());
				findRes.setFindDTO(findDTO);
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
					.format("Exiting getFindDetails(). Total time to process request: %.4f seconds",
							(((float) endTime - startTime) / NANOS_IN_SECOND)));
		}
		return findRes;
	}
}

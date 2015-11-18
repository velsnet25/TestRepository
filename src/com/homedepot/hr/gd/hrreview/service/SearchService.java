package com.homedepot.hr.gd.hrreview.service;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.homedepot.hr.gd.hrreview.bl.AdvancedSearchManager;
import com.homedepot.hr.gd.hrreview.bl.SaveSearch;
import com.homedepot.hr.gd.hrreview.exceptions.HrReviewException;
import com.homedepot.hr.gd.hrreview.interfaces.Constants;
import com.homedepot.hr.gd.hrreview.request.FindRequest;
import com.homedepot.hr.gd.hrreview.response.AdvancedSearchCategoryResponse;
import com.homedepot.hr.gd.hrreview.response.AdvancedSearchResponse;
import com.homedepot.hr.gd.hrreview.response.ApplyAllAssociateResponse;
import com.homedepot.hr.gd.hrreview.response.ApplyAssociateResponse;
import com.homedepot.hr.gd.hrreview.response.DeleteQueryResponse;
import com.homedepot.hr.gd.hrreview.response.FindResponse;
import com.homedepot.hr.gd.hrreview.response.LoadQueryResponse;
import com.homedepot.hr.gd.hrreview.response.SaveSQLQueriesResponse;
import com.homedepot.hr.gd.hrreview.util.JSONConverter;
import com.homedepot.hr.gd.hrreview.bl.AdvancedSearch;

@Path("/SearchService")
public class SearchService implements Constants {
	// Logger instance
	private static final Logger mLogger = Logger.getLogger(SearchService.class);
	private Gson gson = new Gson();

	@POST
	@Produces(APPLICATION_JSON)
	@Path("/getFindDetails")
	public String getFindDetails(
			String request, 
			@DefaultValue("1") @QueryParam("version") int version)
			throws HrReviewException {
		if (mLogger.isDebugEnabled()) {
			mLogger.info(this.getClass() + "Enters getFindDetails  method in SearchService");			
			mLogger.debug("version: '" + version + "'");
		}

		String result = null;

		if (version == 1) {
			if (mLogger.isDebugEnabled()) {
				mLogger.debug("in version 1");
			}

			FindRequest findRequest= null;
			try
			{
				JSONConverter<FindRequest> jsonParser = new JSONConverter<FindRequest>();
				findRequest = jsonParser.createObject(request, FindRequest.class);        			
			}
			catch(Exception exp)
			{
				throw new HrReviewException(Constants.BAD_REQUEST,Constants.ERROR_JSON_PARSER_ERROR_DESC);
            }
			
			FindResponse response = AdvancedSearchManager.getFindDetails(findRequest);
			result = gson.toJson(response, FindResponse.class);

		} else {
			throw new HrReviewException(400, "Invalid version.  1 supported");
		}

		if (mLogger.isDebugEnabled()) {
		//	mLogger.debug("result: '" + result + "'");
		//	mLogger.debug("End getAuthorization");
		}
		return result;
	}
	/**
	 * Advanced quick search drop-down search
	 * 
	 * @param selected
	 * @param version
	 * @return
	 * @throws HrReviewException
	 */
	@GET
	@Produces(APPLICATION_JSON)
	@Path("/getAdvancedQuickSearch")
	public String getAdvancedQuickSearch(
			@QueryParam("selected") String selected,
			@DefaultValue("1") @QueryParam("version") int version)
			throws HrReviewException {
//		if (mLogger.isDebugEnabled()) {
			mLogger.debug(this.getClass()
					+ "Enters getAdvancedSearchResults  method in SearchService ");
			mLogger.debug("selected: '" + selected + "'");
			mLogger.debug("version: '" + version + "'");
//		}
		String result = null;

		if (version == 1) {
			if (mLogger.isDebugEnabled()) {
				mLogger.debug("in version 1");
			}

			AdvancedSearchResponse response = AdvancedSearchManager
					.getAdvancedQuickSearch(selected);
			result = gson.toJson(response, AdvancedSearchResponse.class);

		} else {
			throw new HrReviewException(400, "Invalid version.  1 supported");
		}

//		if (mLogger.isDebugEnabled()) {
			mLogger.debug("result: '" + result + "'");
			mLogger.debug("End getAuthorization");
//		}
		return result;
	}

	@POST
	@Produces(APPLICATION_JSON)
	@Path("/getAdvancedSearch")
	public String getAdvancedSearch(String request,
			@DefaultValue("1") @QueryParam("version") int version)
			throws HrReviewException {
		
		if (mLogger.isDebugEnabled()) {
			mLogger.info(this.getClass()
					+ "Enters getAdvancedSearchResults  method in SearchService");
			mLogger.debug("version: '" + version + "'");
		}

		String result = null;

		if (version == 1) {
			if (mLogger.isDebugEnabled()) {
				mLogger.debug("in version 1");
			}
			
			List <AdvancedSearch> advancedSearchRequestList= new <AdvancedSearch> ArrayList();
			AdvancedSearch advancedSearchRequest = null;
			try
			{
				advancedSearchRequestList = gson.fromJson(request, new TypeToken<ArrayList<AdvancedSearch>>() {}.getType());
			}
			catch(Exception exp)
			{
				throw new HrReviewException(Constants.BAD_REQUEST,Constants.ERROR_JSON_PARSER_ERROR_DESC);
            }
			
			AdvancedSearchResponse response = AdvancedSearchManager.getAdvancedSearch(advancedSearchRequestList);
			result = gson.toJson(response, AdvancedSearchResponse.class);

		} else {
			throw new HrReviewException(400, "Invalid version.  1 supported");
		}

		if (mLogger.isDebugEnabled()) {
			//mLogger.debug("result: '" + result + "'");
			//mLogger.debug("End getAuthorization");
		}
		return result;
	}
	
	/**
	 * Retrieves search category lists
	 * 
	 * @param version
	 * @return
	 * @throws HrReviewException
	 */	
	@GET
	@Produces(APPLICATION_JSON)
	@Path("/getAdvancedSearchCategory")
	public String getAdvancedSearchCategory(
			@DefaultValue("1") @QueryParam("version") int version)
			throws HrReviewException {
		if (mLogger.isDebugEnabled()) {
			//mLogger.info(this.getClass() + "Enters getAdvancedSearchCategory  method in SearchService : ");
			mLogger.debug("version: '" + version + "'");
		}

		String result = null;
		if (version == 1) {
			if (mLogger.isDebugEnabled()) {
				mLogger.debug("in version 1");
			}

			AdvancedSearchCategoryResponse response = AdvancedSearchManager
					.getSearchCategoryResults();
			result = gson
					.toJson(response, AdvancedSearchCategoryResponse.class);

		} else {
			throw new HrReviewException(400, "Invalid version.  1 supported");
		}

		if (mLogger.isDebugEnabled()) {
			mLogger.debug("result: '" + result + "'");
			mLogger.debug("End getAuthorization");
		}
		return result;
	}

		
	@POST
	@Produces(APPLICATION_JSON)
	@Path("/saveSQLQuery")
	public String saveSQLQuery(String request,
			@DefaultValue("1") @QueryParam("version") int version)
			throws HrReviewException {
		if (mLogger.isDebugEnabled()) {
			mLogger.info(this.getClass()
					+ "Enters saveSQLQueries  method in SearchService");
			mLogger.debug("version: '" + version + "'");
		}

		String result = null;

		if (version == 1) {
			if (mLogger.isDebugEnabled()) {
				mLogger.debug("in version 1 "+request);
			}
			
			SaveSearch ss = null;
			String description = null;
			String queryName = null;
			try
			{
				Gson gson = new Gson();
				ss = gson.fromJson(request, new TypeToken<SaveSearch>() {}.getType());
			}
			catch(Exception exp)
			{
				throw new HrReviewException(Constants.BAD_REQUEST,Constants.ERROR_JSON_PARSER_ERROR_DESC);
            }
			if (mLogger.isDebugEnabled()) {
				mLogger.debug("SaveSearch description "+ss.getDescription());
				mLogger.debug("SaveSearch queryName   "+ss.getQueryName());
				mLogger.debug("SaveSearch query       "+ss.getQuery());
			}
			SaveSQLQueriesResponse response = AdvancedSearchManager.saveSQLQuery(ss.getQuery(), ss.getDescription(), ss.getQueryName());
			result = gson.toJson(response, SaveSQLQueriesResponse.class);

		} else {
			throw new HrReviewException(400, "Invalid version.  1 supported");
		}

		if (mLogger.isDebugEnabled()) {
			mLogger.debug("result: '" + result + "'");
			mLogger.debug("End getAuthorization");
		}
		return result;
	}

	@GET
	@Produces(APPLICATION_JSON)
	@Path("/deleteSQLQuery")
	public String deleteSQLQuery(@QueryParam("query") int sqlId,
			@DefaultValue("1") @QueryParam("version") int version)
			throws HrReviewException {
		if (mLogger.isDebugEnabled()) {
			mLogger.info(this.getClass()
					+ "Enters saveSQLQueries  method in SearchService");
			mLogger.debug("data: '" + sqlId + "'");
			mLogger.debug("version: '" + version + "'");
		}

		String result = null;

		if (version == 1) {
			if (mLogger.isDebugEnabled()) {
				mLogger.debug("in version 1");
			}

			DeleteQueryResponse response = AdvancedSearchManager.deleteSQLQuery(sqlId);
			result = gson.toJson(response, DeleteQueryResponse.class);

		} else {
			throw new HrReviewException(400, "Invalid version.  1 supported");
		}

		if (mLogger.isDebugEnabled()) {
			mLogger.debug("result: '" + result + "'");
			mLogger.debug("End getAuthorization");
		}
		return result;
	}

	@GET
	@Produces(APPLICATION_JSON)
	@Path("/loadSQLQuery")
	public String loadSQLQuery(@DefaultValue("1") @QueryParam("version") int version)
			throws HrReviewException {
		if (mLogger.isDebugEnabled()) {
			mLogger.info(this.getClass()
					+ "Enters saveSQLQueries  method in SearchService");
			mLogger.debug("version: '" + version + "'");
		}

		String result = null;

		if (version == 1) {
			if (mLogger.isDebugEnabled()) {
				mLogger.debug("in version 1");
			}
			
			LoadQueryResponse response = AdvancedSearchManager.loadSQLQuery();
			result = gson.toJson(response, LoadQueryResponse.class);

		} else {
			throw new HrReviewException(400, "Invalid version.  1 supported");
		}

		if (mLogger.isDebugEnabled()) {
			mLogger.debug("result: '" + result + "'");
			mLogger.debug("End getAuthorization");
		}
		return result;
	}
	
	@GET
	@Produces(APPLICATION_JSON)
	@Path("/applyAssociate")
	public String applyAssociate(@DefaultValue("1") @QueryParam("version") int version)
			throws HrReviewException {
		if (mLogger.isDebugEnabled()) {
			mLogger.info(this.getClass()
					+ "Enters applyAssociate  method in SearchService : ");
			mLogger.debug("version: '" + version + "'");
		}

		String result = null;

		if (version == 1) {
			if (mLogger.isDebugEnabled()) {
				mLogger.debug("in version 1");
			}

			ApplyAssociateResponse response = AdvancedSearchManager
					.applyAssociate();
			result = gson.toJson(response, ApplyAssociateResponse.class);

		} else {
			throw new HrReviewException(400, "Invalid version.  1 supported");
		}

		if (mLogger.isDebugEnabled()) {
			mLogger.debug("result: '" + result + "'");
			mLogger.debug("End getAuthorization");
		}
		return result;
	}

	@GET
	@Produces(APPLICATION_JSON)
	@Path("/applyAllAssociate")
	public String applyAllAssociate(@DefaultValue("1") @QueryParam("version") int version)
			throws HrReviewException {
		if (mLogger.isDebugEnabled()) {
			mLogger.info(this.getClass()
					+ "Enters applyAllAssociate  method in SearchService : ");
			mLogger.debug("version: '" + version + "'");
		}

		String result = null;

		if (version == 1) {
			if (mLogger.isDebugEnabled()) {
				mLogger.debug("in version 1");
			}

			ApplyAllAssociateResponse response = AdvancedSearchManager
					.applyAllAssociate();
			result = gson.toJson(response, ApplyAllAssociateResponse.class);

		} else {
			throw new HrReviewException(400, "Invalid version.  1 supported");
		}

		if (mLogger.isDebugEnabled()) {
			mLogger.debug("result: '" + result + "'");
			mLogger.debug("End getAuthorization");
		}
		return result;
	}
}

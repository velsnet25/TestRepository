package com.homedepot.hr.gd.hrreview.service;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.homedepot.hr.gd.hrreview.bl.DataManager;
import com.homedepot.hr.gd.hrreview.dto.ErrorRequest;
import com.homedepot.hr.gd.hrreview.dto.Response;
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
import com.homedepot.hr.gd.hrreview.util.JSONConverter;

@Path("/DataService")
public class DataService implements Constants
{
    // Logger instance
    private static final Logger mLogger = Logger.getLogger(DataService.class);
    private Gson gson=new Gson();    
    
    @GET
	@Produces(APPLICATION_JSON)
	@Path("/getCourseSearchDetails")
	public String getCourseDetails (
    		@DefaultValue("1") @QueryParam("version") int version) throws HrReviewException
	{
		if(mLogger.isDebugEnabled()){
           	mLogger.info(this.getClass());
           	mLogger.debug("version: '"+version+"'");
           }
        
        String result = null ;       

        if(version == 1)
        {
        	if(mLogger.isDebugEnabled()) 
        	{ 
        		mLogger.debug("in version 1"); 
        	}
        	
        	 CourseSearchResponse response = DataManager.getCourseDetails() ;
        	 result = gson.toJson(response, CourseSearchResponse.class) ;
        	
        }
        else
        {
        	throw new HrReviewException(400, "Invalid version.  1 supported") ;
        }
        
        if(mLogger.isDebugEnabled()){
            //mLogger.debug("result: '"+result+"'");
            mLogger.debug("End getAuthorization");
        }
		return result ;
	}
    
    @GET
	@Produces(APPLICATION_JSON)
	@Path("/getDegreeSearchDetails")
	public String getDegreeDetails (
    		@DefaultValue("1") @QueryParam("version") int version) throws HrReviewException
	{
		if(mLogger.isDebugEnabled()){
           	mLogger.info(this.getClass());
           	mLogger.debug("version: '"+version+"'");
           }
        
        String result = null ;       

        if(version == 1)
        {
        	if(mLogger.isDebugEnabled()) 
        	{ 
        		mLogger.debug("in version 1"); 
        	}
        	
        	 DegreeSearchResponse response = DataManager.getDegreeDetails() ;
        	 result = gson.toJson(response, DegreeSearchResponse.class) ;
        	
        }
        else
        {
        	throw new HrReviewException(400, "Invalid version.  1 supported") ;
        }
        
        if(mLogger.isDebugEnabled()){
            //mLogger.debug("result: '"+result+"'");
            mLogger.debug("End getAuthorization");
        }
		return result ;
	}
    
    @GET
	@Produces(APPLICATION_JSON)
	@Path("/getDepartmentSearchDetails")
	public String getDepartmentDetails (
    		@DefaultValue("1") @QueryParam("version") int version) throws HrReviewException
	{
		if(mLogger.isDebugEnabled()){
           	mLogger.info(this.getClass());
           	mLogger.debug("version: '"+version+"'");
           }
        
        String result = null ;       

        if(version == 1)
        {
        	if(mLogger.isDebugEnabled()) 
        	{ 
        		mLogger.debug("in version 1"); 
        	}
        	
        	 DepartmentSearchResponse response = DataManager.getDepartmentDetails() ;
        	 result = gson.toJson(response, DepartmentSearchResponse.class) ;
        	
        }
        else
        {
        	throw new HrReviewException(400, "Invalid version.  1 supported") ;
        }
        
        if(mLogger.isDebugEnabled()){
            ////mLogger.debug("result: '"+result+"'");
            //mLogger.debug("End getAuthorization");
        }
		return result ;
	}
    
    @GET
	@Produces(APPLICATION_JSON)
	@Path("/getDistrictSearchDetails")
	public String getDistrictDetails (
    		@DefaultValue("1") @QueryParam("version") int version) throws HrReviewException
	{
		if(mLogger.isDebugEnabled()){
           	mLogger.info(this.getClass());
           	mLogger.debug("version: '"+version+"'");
           }
        
        String result = null ;       

        if(version == 1)
        {
        	if(mLogger.isDebugEnabled()) 
        	{ 
        		mLogger.debug("in version 1"); 
        	}
        	
        	 DistrictSearchResponse response = DataManager.getDistrictDetails() ;
        	 result = gson.toJson(response, DistrictSearchResponse.class) ;
        	
        }
        else
        {
        	throw new HrReviewException(400, "Invalid version.  1 supported") ;
        }
        
        if(mLogger.isDebugEnabled()){
            //mLogger.debug("result: '"+result+"'");
            mLogger.debug("End getAuthorization");
        }
		return result ;
	}    
    
    @GET
	@Produces(APPLICATION_JSON)
	@Path("/getDivisionSearchDetails")
	public String getDivisionDetails (
    		@DefaultValue("1") @QueryParam("version") int version) throws HrReviewException
	{
		if(mLogger.isDebugEnabled()){
           	mLogger.info(this.getClass());
           	mLogger.debug("version: '"+version+"'");
           }
        
        String result = null ;       

        if(version == 1)
        {
        	if(mLogger.isDebugEnabled()) 
        	{ 
        		mLogger.debug("in version 1"); 
        	}
        	
        	 DivisionSearchResponse response = DataManager.getDivisionDetails() ;
        	 result = gson.toJson(response, DivisionSearchResponse.class) ;
        	
        }
        else
        {
        	throw new HrReviewException(400, "Invalid version.  1 supported") ;
        }
        
        if(mLogger.isDebugEnabled()){
            //mLogger.debug("result: '"+result+"'");
            mLogger.debug("End getAuthorization");
        }
		return result ;
	}
    
    //Same as Next Position
    @GET
   	@Produces(APPLICATION_JSON)
   	@Path("/getIndividualCareerSearchDetails")
   	public String getIndividualCareerDetails (
       		@DefaultValue("1") @QueryParam("version") int version) throws HrReviewException
   	{
    	String result = null ;       

        if(version == 1)
        {
        	if(mLogger.isDebugEnabled()) 
        	{ 
        		mLogger.debug("in version 1"); 
        	}
        	
        	 JobTitleSearchResponse response = DataManager.getJobTitleDetails() ;
        	 result = gson.toJson(response, JobTitleSearchResponse.class) ;
        	
        }
        else
        {
        	throw new HrReviewException(400, "Invalid version.  1 supported") ;
        }
        
        if(mLogger.isDebugEnabled()){
            //mLogger.debug("result: '"+result+"'");
            mLogger.debug("End getAuthorization");
        }
		return result ;
   	}
    
    @GET
	@Produces(APPLICATION_JSON)
	@Path("/getIndvidualCareerReadinessSearchDetails")
	public String getCareerReadinessDetails (
    		@DefaultValue("1") @QueryParam("version") int version) throws HrReviewException
	{
		if(mLogger.isDebugEnabled()){
           	mLogger.info(this.getClass());
           	mLogger.debug("version: '"+version+"'");
           }
        
        String result = null ;       

        if(version == 1)
        {
        	if(mLogger.isDebugEnabled()) 
        	{ 
        		mLogger.debug("in version 1"); 
        	}
        	
        	 ReadinessSearchResponse response = DataManager.getReadinessDetails() ;
        	 result = gson.toJson(response, ReadinessSearchResponse.class) ;
        	
        }
        else
        {
        	throw new HrReviewException(400, "Invalid version.  1 supported") ;
        }
        
        if(mLogger.isDebugEnabled()){
            //mLogger.debug("result: '"+result+"'");
            mLogger.debug("End getAuthorization");
        }
		return result ;
	}
    
    @GET
	@Produces(APPLICATION_JSON)
	@Path("/getJobTitleSearchDetails")
	public String getJobTitleDetails (
    		@DefaultValue("1") @QueryParam("version") int version) throws HrReviewException
	{
		if(mLogger.isDebugEnabled()){
           	mLogger.info(this.getClass());
           	mLogger.debug("version: '"+version+"'");
           }
        
        String result = null ;       

        if(version == 1)
        {
        	if(mLogger.isDebugEnabled()) 
        	{ 
        		mLogger.debug("in version 1"); 
        	}
        	
        	 JobTitleSearchResponse response = DataManager.getJobTitleDetails() ;
        	 result = gson.toJson(response, JobTitleSearchResponse.class) ;
        	
        }
        else
        {
        	throw new HrReviewException(400, "Invalid version.  1 supported") ;
        }
        
        if(mLogger.isDebugEnabled()){
            //mLogger.debug("result: '"+result+"'");
            mLogger.debug("End getAuthorization");
        }
		return result ;
	}
    @GET
  	@Produces(APPLICATION_JSON)
  	@Path("/getJobTitleSearch")
  	public String getJobTitleSearch (
      		@DefaultValue("1") @QueryParam("version") int version) throws HrReviewException
  	{
  		if(mLogger.isDebugEnabled()){
             	mLogger.info(this.getClass());
             	mLogger.debug("version: '"+version+"'");
             }
          
          String result = null ;       

          if(version == 1)
          {
          	if(mLogger.isDebugEnabled()) 
          	{ 
          		mLogger.debug("in version 1"); 
          	}
          	
          	 JobTitleSearchResponse response = DataManager.getJobTitleDetails() ;
          	 result = gson.toJson(response, JobTitleSearchResponse.class) ;
          	
          }
          else
          {
          	throw new HrReviewException(400, "Invalid version.  1 supported") ;
          }
          
          if(mLogger.isDebugEnabled()){
              //mLogger.debug("result: '"+result+"'");
              mLogger.debug("End getAuthorization");
          }
  		return result ;
  	}
    @GET
   	@Produces(APPLICATION_JSON)
   	@Path("/getLanguageSearchDetails")
   	public String getLanguageDetails (
       		@DefaultValue("1") @QueryParam("version") int version) throws HrReviewException
   	{
   		if(mLogger.isDebugEnabled()){
              	mLogger.info(this.getClass());
              	mLogger.debug("version: '"+version+"'");
              }
           
           String result = null ;       

           if(version == 1)
           {
           	if(mLogger.isDebugEnabled()) 
           	{ 
           		mLogger.debug("in version 1"); 
           	}
           	
           	 LanguageSearchResponse response = DataManager.getLanguageDetails() ;
           	 result = gson.toJson(response, LanguageSearchResponse.class) ;
           	
           }
           else
           {
           	throw new HrReviewException(400, "Invalid version.  1 supported") ;
           }
           
           if(mLogger.isDebugEnabled()){
               //mLogger.debug("result: '"+result+"'");
               mLogger.debug("End getAuthorization");
           }
   		return result ;
   	}
    
    @GET
   	@Produces(APPLICATION_JSON)
   	@Path("/getLocationSearchDetails")
   	public String getLocationDetails (
       		@DefaultValue("1") @QueryParam("version") int version) throws HrReviewException
   	{
   		if(mLogger.isDebugEnabled()){
              	mLogger.info(this.getClass());
              	mLogger.debug("version: '"+version+"'");
              }
           
           String result = null ;       

           if(version == 1)
           {
           	if(mLogger.isDebugEnabled()) 
           	{ 
           		mLogger.debug("in version 1"); 
           	}
           	
           	 LocationSearchResponse response = DataManager.getLocationDetails() ;
           	 result = gson.toJson(response, LocationSearchResponse.class) ;
           	
           }
           else
           {
           	throw new HrReviewException(400, "Invalid version.  1 supported") ;
           }
           
           if(mLogger.isDebugEnabled()){
               //mLogger.debug("result: '"+result+"'");
               mLogger.debug("End getAuthorization");
           }
   		return result ;
   	}
    
    @GET
   	@Produces(APPLICATION_JSON)
   	@Path("/getMajorsSearchDetails")
   	public String getMajorsDetails (
       		@DefaultValue("1") @QueryParam("version") int version) throws HrReviewException
   	{
   		if(mLogger.isDebugEnabled()){
              	mLogger.info(this.getClass());
              	mLogger.debug("version: '"+version+"'");
              }
           
           String result = null ;       

           if(version == 1)
           {
           	if(mLogger.isDebugEnabled()) 
           	{ 
           		mLogger.debug("in version 1"); 
           	}
           	
           	 MajorsSearchResponse response = DataManager.getMajorsDetails() ;
           	 result = gson.toJson(response, MajorsSearchResponse.class) ;
           	
           }
           else
           {
           	throw new HrReviewException(400, "Invalid version.  1 supported") ;
           }
           
           if(mLogger.isDebugEnabled()){
               //mLogger.debug("result: '"+result+"'");
               mLogger.debug("End getAuthorization");
           }
   		return result ;
   	}
    
    @GET
   	@Produces(APPLICATION_JSON)
   	@Path("/getPotentialSearchDetails")
   	public String getPotentialDetails (
       		@DefaultValue("1") @QueryParam("version") int version) throws HrReviewException
   	{
   		if(mLogger.isDebugEnabled()){
              	mLogger.info(this.getClass());
              	mLogger.debug("version: '"+version+"'");
              }
           
           String result = null ;       

           if(version == 1)
           {
           	if(mLogger.isDebugEnabled()) 
           	{ 
           		mLogger.debug("in version 1"); 
           	}
           	
           	 PotentialSearchResponse response = DataManager.getPotentialDetails() ;
           	 result = gson.toJson(response, PotentialSearchResponse.class) ;
           	
           }
           else
           {
           	throw new HrReviewException(400, "Invalid version.  1 supported") ;
           }
           
           if(mLogger.isDebugEnabled()){
               //mLogger.debug("result: '"+result+"'");
               mLogger.debug("End getAuthorization");
           }
   		return result ;
   	}
    
    @GET
   	@Produces(APPLICATION_JSON)
   	@Path("/getPerformanceSearchDetails")
   	public String getPerformanceDetails (
       		@DefaultValue("1") @QueryParam("version") int version) throws HrReviewException
   	{
   		if(mLogger.isDebugEnabled()){
              	mLogger.info(this.getClass());
              	mLogger.debug("version: '"+version+"'");
              }
           
           String result = null ;       

           if(version == 1)
           {
           	if(mLogger.isDebugEnabled()) 
           	{ 
           		mLogger.debug("in version 1"); 
           	}
           	
           	 PerformanceSearchResponse response = DataManager.getPerformanceDetails() ;
           	 result = gson.toJson(response, PerformanceSearchResponse.class) ;
           	
           }
           else
           {
           	throw new HrReviewException(400, "Invalid version.  1 supported") ;
           }
           
           if(mLogger.isDebugEnabled()){
               //mLogger.debug("result: '"+result+"'");
               mLogger.debug("End getAuthorization");
           }
   		return result ;
   	}
    
    @GET
   	@Produces(APPLICATION_JSON)
   	@Path("/getPrevTitleDetails")
   	public String getPrevTitleSearchDetails (
       		@DefaultValue("1") @QueryParam("version") int version) throws HrReviewException
   	{
   		if(mLogger.isDebugEnabled()){
              	mLogger.info(this.getClass());
              	mLogger.debug("version: '"+version+"'");
              }
           
           String result = null ;       

           if(version == 1)
           {
           	if(mLogger.isDebugEnabled()) 
           	{ 
           		mLogger.debug("in version 1"); 
           	}
           	
           	JobTitleSearchResponse response = DataManager.getPrevTitleDetails() ;
           	 result = gson.toJson(response, JobTitleSearchResponse.class) ;
           	
           }
           else
           {
           	throw new HrReviewException(400, "Invalid version.  1 supported") ;
           }
           
           if(mLogger.isDebugEnabled()){
               //mLogger.debug("result: '"+result+"'");
               mLogger.debug("End getAuthorization");
           }
   		return result ;
   	}
    
    @GET
   	@Produces(APPLICATION_JSON)
   	@Path("/getNextPositionReadinessSearchDetails")
   	public String getNextPositionReadinessDetails (
       		@DefaultValue("1") @QueryParam("version") int version) throws HrReviewException
   	{
    	if(mLogger.isDebugEnabled()){
           	mLogger.info(this.getClass());
           	mLogger.debug("version: '"+version+"'");
           }
        
        String result = null ;       

        if(version == 1)
        {
        	if(mLogger.isDebugEnabled()) 
        	{ 
        		mLogger.debug("in version 1"); 
        	}
        	
        	 ReadinessSearchResponse response = DataManager.getReadinessDetails() ;
        	 result = gson.toJson(response, ReadinessSearchResponse.class) ;
        	
        }
        else
        {
        	throw new HrReviewException(400, "Invalid version.  1 supported") ;
        }
        
        if(mLogger.isDebugEnabled()){
            //mLogger.debug("result: '"+result+"'");
            mLogger.debug("End getAuthorization");
        }
		return result ;
   	}
    
    @GET
   	@Produces(APPLICATION_JSON)
   	@Path("/getNextPositionSearchDetails")
   	public String getNextPositionDetails (
       		@DefaultValue("1") @QueryParam("version") int version) throws HrReviewException
   	{
    	String result = null ;       

        if(version == 1)
        {
        	if(mLogger.isDebugEnabled()) 
        	{ 
        		mLogger.debug("in version 1"); 
        	}
        	
        	 JobTitleSearchResponse response = DataManager.getJobTitleDetails() ;
        	 result = gson.toJson(response, JobTitleSearchResponse.class) ;
        	
        }
        else
        {
        	throw new HrReviewException(400, "Invalid version.  1 supported") ;
        }
        
        if(mLogger.isDebugEnabled()){
            //mLogger.debug("result: '"+result+"'");
            mLogger.debug("End getAuthorization");
        }
		return result ;
   	}
    
    @GET
   	@Produces(APPLICATION_JSON)
   	@Path("/getRaceSearchDetails")
   	public String getRaceDetails (
       		@DefaultValue("1") @QueryParam("version") int version) throws HrReviewException
   	{
   		if(mLogger.isDebugEnabled()){
              	mLogger.info(this.getClass());
              	mLogger.debug("version: '"+version+"'");
              }
           
           String result = null ;       

           if(version == 1)
           {
           	if(mLogger.isDebugEnabled()) 
           	{ 
           		mLogger.debug("in version 1"); 
           	}
           	
           	 RaceSearchResponse response = DataManager.getRaceDetails() ;
           	 result = gson.toJson(response, RaceSearchResponse.class) ;
           	
           }
           else
           {
           	throw new HrReviewException(400, "Invalid version.  1 supported") ;
           }
           
           if(mLogger.isDebugEnabled()){
               //mLogger.debug("result: '"+result+"'");
               mLogger.debug("End getAuthorization");
           }
   		return result ;
   	}
    
    @GET
   	@Produces(APPLICATION_JSON)
   	@Path("/getRegionSearchDetails")
   	public String getRegionDetails (
       		@DefaultValue("1") @QueryParam("version") int version) throws HrReviewException
   	{
   		if(mLogger.isDebugEnabled()){
              	mLogger.info(this.getClass());
              	mLogger.debug("version: '"+version+"'");
              }
           
           String result = null ;       

           if(version == 1)
           {
           	if(mLogger.isDebugEnabled()) 
           	{ 
           		mLogger.debug("in version 1"); 
           	}
           	
           	 RegionSearchResponse response = DataManager.getRegionDetails() ;
           	 result = gson.toJson(response, RegionSearchResponse.class) ;
           	
           }
           else
           {
           	throw new HrReviewException(400, "Invalid version.  1 supported") ;
           }
           
           if(mLogger.isDebugEnabled()){
               //mLogger.debug("result: '"+result+"'");
               mLogger.debug("End getAuthorization");
           }
   		return result ;
   	}
    
    @GET
   	@Produces(APPLICATION_JSON)
   	@Path("/getSchoolSearchDetails")
   	public String getSchoolDetails (
       		@DefaultValue("1") @QueryParam("version") int version) throws HrReviewException
   	{
   		if(mLogger.isDebugEnabled()){
              	mLogger.info(this.getClass());
              	mLogger.debug("version: '"+version+"'");
              }
           
           String result = null ;       

           if(version == 1)
           {
           	if(mLogger.isDebugEnabled()) 
           	{ 
           		mLogger.debug("in version 1"); 
           	}
           	
           	 SchoolSearchResponse response = DataManager.getSchoolDetails() ;
           	 result = gson.toJson(response, SchoolSearchResponse.class) ;
           	
           }
           else
           {
           	throw new HrReviewException(400, "Invalid version.  1 supported") ;
           }
           
           if(mLogger.isDebugEnabled()){
               //mLogger.debug("result: '"+result+"'");
               mLogger.debug("End getAuthorization");
           }
   		return result ;
   	}
    
    @GET
   	@Produces(APPLICATION_JSON)
   	@Path("/getStoreSearchDetails")
   	public String getStoreDetails (
       		@DefaultValue("1") @QueryParam("version") int version) throws HrReviewException
   	{
   		if(mLogger.isDebugEnabled()){
              	mLogger.info(this.getClass());
              	mLogger.debug("version: '"+version+"'");
              }
           
           String result = null ;       

           if(version == 1)
           {
           	if(mLogger.isDebugEnabled()) 
           	{ 
           		mLogger.debug("in version 1"); 
           	}
           	
           	 StoreSearchResponse response = DataManager.getStoreDetails() ;
           	 result = gson.toJson(response, StoreSearchResponse.class) ;
           	
           }
           else
           {
           	throw new HrReviewException(400, "Invalid version.  1 supported") ;
           }
           
           if(mLogger.isDebugEnabled()){
              // mLogger.debug("result: "+result.toString());
               mLogger.debug("End getAuthorization");
           }
   		return result ;
   	}
    
    @POST
   	@Produces(APPLICATION_JSON)
       @Path("/setErrorMessage")
       public String setErrorMessage(String errorMessage, @DefaultValue("1") @QueryParam("version") int version) throws HrReviewException
       {
    	
    		long starttTime = System.currentTimeMillis();
    	
       		if(mLogger.isDebugEnabled()){
       			mLogger.debug(this.getClass() + "Enters setErrorMessage method in StaffingService");
           }
       		
           ErrorRequest errorRequest = null;
           String response = null;
           try{			
        	   switch(version){
        	   		case 1:
        	   			if(mLogger.isDebugEnabled()) 
        	   				mLogger.debug("in version 1");
   	                  	             
	 					JSONConverter<ErrorRequest> jsonParser = new JSONConverter<ErrorRequest>();
	 					errorRequest = jsonParser.createObject(errorMessage, ErrorRequest.class);        			
			
	 					String eMessage = null;
	 					if (errorRequest != null && errorRequest.getMessage() != null && !errorRequest.getMessage().isEmpty()) {
	 						eMessage = "Message: "+errorRequest.getMessage();
	 					}
	 					
	 					if (errorRequest != null && errorRequest.getUrl() != null && !errorRequest.getUrl().isEmpty()) {
	 						eMessage = eMessage + " URL: "+errorRequest.getUrl();
	 					}
	 					
	 					if (errorRequest != null && errorRequest.getCause() != null && !errorRequest.getCause().isEmpty()) {
	 						eMessage = eMessage + " Cause: "+errorRequest.getCause();
	 					}
	 					
	 					if (!eMessage.isEmpty()) {
	 						mLogger.error(eMessage);
	 					}
	 					
	 					Response res = new Response();
	 					res.setStatus(Constants.SUCCESS_APP_STATUS);
	 					response = gson.toJson(res, Response.class); 				
   	             
	 					break;
                   default: throw new RuntimeException("Unsupported version: " + version);
               }
           }
           
           catch(RuntimeException e){
           	
   			mLogger.error(HrReviewApplLogMessage.create(HrReviewApplLogMessage.FATAL_ERROR,
   					Constants.ERROR_GET_STORESLIST_SERVICE_ERROR_DESC,e));
   			mLogger.error(Constants.ERROR_GET_STORESLIST_SERVICE_ERROR_DESC,e);
   			   			
           }
           
           
   			long endtTime=System.currentTimeMillis();
   			mLogger.debug("Time taken for JSON"+(endtTime - starttTime) / 1000);
           
           
           if(mLogger.isDebugEnabled()){
               mLogger.debug(this.getClass() + " Leaving method setErrorMessage in StaffingService");
           }
   		return response;
       }
}

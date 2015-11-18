package com.homedepot.hr.gd.hrreview.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.homedepot.hr.gd.hrreview.bl.AccessManager;
import com.homedepot.hr.gd.hrreview.bl.PerformanceDevelopmentManager;
import com.homedepot.hr.gd.hrreview.bl.PersonalManager;
import com.homedepot.hr.gd.hrreview.dto.AccessInfoTO;
import com.homedepot.hr.gd.hrreview.exceptions.HrReviewException;
import com.homedepot.hr.gd.hrreview.interfaces.Constants;
import com.homedepot.hr.gd.hrreview.response.AddressResponse;
import com.homedepot.hr.gd.hrreview.response.BasicInformationResponse;
import com.homedepot.hr.gd.hrreview.response.EducationResponse;
import com.homedepot.hr.gd.hrreview.response.LanguageResponse;
import com.homedepot.hr.gd.hrreview.response.NextPositionResponse;
import com.homedepot.hr.gd.hrreview.response.RatingResponse;
import com.homedepot.hr.gd.hrreview.response.WorkHistoryResponse;


@Path("/AssociateService")
public class AssociateService implements Constants
{
    // Logger instance
    private static final Logger mLogger = Logger.getLogger(AssociateService.class);
    private Gson gson=new Gson();    
        
    @GET
	@Produces(APPLICATION_JSON)
	@Path("/getAccessInfo")
	public String getAccessInfo(@Context HttpServletRequest request, 
    		@DefaultValue("1") @QueryParam("version") int version) throws HrReviewException
	{
		if(mLogger.isDebugEnabled())
        {
			mLogger.debug("Start getAcessInfo");
			mLogger.debug("version: '"+version+"'");
        }
        
        String result = null ;
       
        if(version == 1)
        {
        	if(mLogger.isDebugEnabled()) 
        	{ 
        		mLogger.debug("in version 1"); 
        	}
        	
        	 AccessInfoTO access = AccessManager.getAccess(request) ;
        	 result = gson.toJson(access, AccessInfoTO.class) ;
        	
        }
        else
        {
        	throw new HrReviewException(400, "Invalid version.  1 supported") ;
        }
        
        if(mLogger.isDebugEnabled()){
            mLogger.debug("result: '"+result+"'");
            mLogger.debug("End getAuthorization");
        }
		return result ;
	}
    
	@GET
	@Produces(APPLICATION_JSON)
	@Path("/getBasicInformation")
	public String getBasicInformation(@QueryParam("associateId") String associateId, 
    		@DefaultValue("1") @QueryParam("version") int version) throws HrReviewException
	{
		if(mLogger.isDebugEnabled()){
           	mLogger.info(this.getClass() + "Enters getBasicInformation method in AssociateService with AssociateId: " + associateId);
           	mLogger.debug("version: '"+version+"'");
           }
        
        String result = null ;
       
        if(version == 1)
        {
        	if(mLogger.isDebugEnabled()) 
        	{ 
        		mLogger.debug("in version 1"); 
        	}
        	 BasicInformationResponse response = PersonalManager.getBasicInformation(associateId) ;
        	 result = gson.toJson(response, BasicInformationResponse.class) ;
        }
        else
        {
        	throw new HrReviewException(400, "Invalid version.  1 supported") ;
        }
        
        if(mLogger.isDebugEnabled()){
            mLogger.debug("result: '"+result+"'");
            mLogger.debug("End getAuthorization");
        }
		return result ;
	}
	
	@GET
	@Produces(APPLICATION_JSON)
	@Path("/getAddressDetails")
	public String getAddressDetails(@QueryParam("associateId") String associateId, 
    		@DefaultValue("1") @QueryParam("version") int version) throws HrReviewException
	{
		if(mLogger.isDebugEnabled()){
           	mLogger.info(this.getClass() + "Enters getAddress method in AssociateService with AssociateId: " + associateId);
           	mLogger.debug("version: '"+version+"'");
        }
        
        String result = null ;

        if(version == 1)
        {
        	if(mLogger.isDebugEnabled()) 
        	{ 
        		mLogger.debug("in version 1"); 
        	}
        	
        	 AddressResponse response = PersonalManager.getAddressDetails(associateId);
        	 response.setSucess(true);
        	 result = gson.toJson(response, AddressResponse.class) ;
        	
        }
        else
        {
        	throw new HrReviewException(400, "Invalid version.  1 supported") ;
        }
        
        if(mLogger.isDebugEnabled()){
            mLogger.debug("result: '"+result+"'");
            mLogger.debug("End getAuthorization");
        }
		return result ;
	}
	
	@GET
	@Produces(APPLICATION_JSON)
	@Path("/getSchoolAndCourseDetails")
	public String getSchoolAndCourseDetails(@QueryParam("associateId") String associateId, 
    		@DefaultValue("1") @QueryParam("version") int version) throws HrReviewException
	{
		if(mLogger.isDebugEnabled()){
           	mLogger.info(this.getClass() + "Enters getSchoolAndCourseDetails method in AssociateService with AssociateId: " + associateId);
           	mLogger.debug("version: '"+version+"'");
           }
        
        String result = null ;
        
        if(version == 1)
        {
        	if(mLogger.isDebugEnabled()) 
        	{ 
        		mLogger.debug("in version 1"); 
        	}
        	
        	 EducationResponse response = PersonalManager.getSchoolAndCourseDetails(associateId) ;
        	 result = gson.toJson(response, EducationResponse.class) ;
        }
        else
        {
        	throw new HrReviewException(400, "Invalid version.  1 supported") ;
        }
        
        if(mLogger.isDebugEnabled()){
            mLogger.debug("result: '"+result+"'");
            mLogger.debug("End getAuthorization");
        }
		return result ;
	}
	
	@GET
	@Produces(APPLICATION_JSON)
	@Path("/getLanguageDetails")
	public String getLanguageDetails(@QueryParam("associateId") String associateId, 
    		@DefaultValue("1") @QueryParam("version") int version) throws HrReviewException
	{
		if(mLogger.isDebugEnabled()){
           	mLogger.info(this.getClass() + "Enters getLanguageDetails method in AssociateService with AssociateId: " + associateId);
           	mLogger.debug("version: '"+version+"'");
           }
        
        String result = null ;
        if(version == 1)
        {
        	if(mLogger.isDebugEnabled()) 
        	{ 
        		mLogger.debug("in version 1"); 
        	}
        	 LanguageResponse response = PersonalManager.getLanguageDetails(associateId) ;
        	 result = gson.toJson(response, LanguageResponse.class) ;
        }
        else
        {
        	throw new HrReviewException(400, "Invalid version.  1 supported") ;
        }
        
        if(mLogger.isDebugEnabled()){
            mLogger.debug("result: '"+result+"'");
            mLogger.debug("End getAuthorization");
        }
		return result ;
	}
	
	@GET
	@Produces(APPLICATION_JSON)
	@Path("/getWorkHistoryDetails")
	public String getWorkHistoryDetails(@QueryParam("associateId") String associateId, 
    		@DefaultValue("1") @QueryParam("version") int version) throws HrReviewException
	{
		if(mLogger.isDebugEnabled()){
           	mLogger.info(this.getClass() + "Enters getWorkHistoryDetails method in AssociateService with AssociateId: " + associateId);
           	mLogger.debug("version: '"+version+"'");
           }
        
        String result = null ;
       
        if(version == 1)
        {
        	if(mLogger.isDebugEnabled()) 
        	{ 
        		mLogger.debug("in version 1"); 
        	}
        	 WorkHistoryResponse response = PersonalManager.getWorkHistoryDetails(associateId) ;
        	 response.setSuccess(true);
        	 result = gson.toJson(response, WorkHistoryResponse.class) ;
        }
        else
        {
        	throw new HrReviewException(400, "Invalid version.  1 supported") ;
        }
        
        if(mLogger.isDebugEnabled()){
            mLogger.debug("result: '"+result+"'");
            mLogger.debug("End getAuthorization");
        }
		return result ;
	}
	
	@GET
	@Produces(APPLICATION_JSON)
	@Path("/getRatingDetails")
	public String getRatingDetails(@QueryParam("associateId") String associateId, 
    		@DefaultValue("1") @QueryParam("version") int version) throws HrReviewException
	{
		if(mLogger.isDebugEnabled()){
           	mLogger.info(this.getClass() + "Enters getRatingDetails method in AssociateService with AssociateId: " + associateId);
           	mLogger.debug("version: '"+version+"'");
           }
        
        String result = null ;
        if(version == 1)
        {
        	if(mLogger.isDebugEnabled()) 
        	{ 
        		mLogger.debug("in version 1"); 
        	}
        	 RatingResponse response = PerformanceDevelopmentManager.getRatingDetails(associateId) ;
        	 result = gson.toJson(response, RatingResponse.class) ;
        }
        else
        {
        	throw new HrReviewException(400, "Invalid version.  1 supported") ;
        }
        
        if(mLogger.isDebugEnabled()){
            mLogger.debug("result: '"+result+"'");
            mLogger.debug("End getAuthorization");
        }
		return result ;
	}
	
	@GET
	@Produces(APPLICATION_JSON)
	@Path("/getNextPositionDetails")
	public String getNextPositionDetails(@QueryParam("associateId") String associateId, 
    		@DefaultValue("1") @QueryParam("version") int version) throws HrReviewException
	{
		if(mLogger.isDebugEnabled()){
           	mLogger.info(this.getClass() + "Enters getNextPositionDetails method in AssociateService with AssociateId: " + associateId);
           	mLogger.debug("version: '"+version+"'");
           }
        
        String result = null ;
        if(version == 1)
        {
        	if(mLogger.isDebugEnabled()) 
        	{ 
        		mLogger.debug("in version 1"); 
        	}
        	 NextPositionResponse response = PerformanceDevelopmentManager.getNextPositionDetails(associateId) ;
        	 result = gson.toJson(response, NextPositionResponse.class) ;
        }
        else
        {
        	throw new HrReviewException(400, "Invalid version.  1 supported") ;
        }
        
        if(mLogger.isDebugEnabled()){
            mLogger.debug("result: '"+result+"'");
            mLogger.debug("End getAuthorization");
        }
		return result ;
	}
}

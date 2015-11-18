package com.homedepot.hr.gd.hrreview.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import org.apache.log4j.Logger;

import com.homedepot.hr.gd.hrreview.bl.ReportingManager;
import com.homedepot.hr.gd.hrreview.dto.ExportToExcelRequest;
import com.homedepot.hr.gd.hrreview.exceptions.HrReviewApplLogMessage;
import com.homedepot.hr.gd.hrreview.exceptions.HrReviewException;
import com.homedepot.hr.gd.hrreview.interfaces.Constants;
import com.homedepot.hr.gd.hrreview.util.JSONConverter;

@Path("/ReportingService")
public class ReportingService implements Constants
{
    // Logger instance
    private static final Logger mLogger = Logger.getLogger(ReportingService.class);
    
    @POST
    @Produces(APPLICATION_XLS)
	@Path("/getExcelSheetReport")
    public String getExcelSheetReport(@FormParam("exportToExcelInput") String exportToExcelInput,@DefaultValue("1") @QueryParam("version") int version,@Context HttpServletResponse httpResponse) throws HrReviewException
	{

		if(mLogger.isDebugEnabled()){
           	mLogger.debug(this.getClass() + "Enters getExcelSheetReport method in ReportingService" );
           	mLogger.debug("ExportToExcelInput from UI"+exportToExcelInput);
           	mLogger.debug("version: '"+version+"'");
           }
        
        String result = null ;
        ExportToExcelRequest exportToExcelRequest= new ExportToExcelRequest();
        try
        {
        if(version == 1)
        {
        	if(mLogger.isDebugEnabled()) 
        	{ 
        		mLogger.debug("in version 1"); 
        	}
        	
        	if(exportToExcelInput==null)
			{
				throw new HrReviewException(Constants.BAD_REQUEST,Constants.INVALID_INPUT_DESC);
			}
			
        	exportToExcelRequest = JSONConverter.createObject(exportToExcelInput, ExportToExcelRequest.class);
			ReportingManager.getExcelSheetReport(exportToExcelRequest,httpResponse);
        }
        else
        {
        	throw new HrReviewException(Constants.BAD_REQUEST, "Invalid version.  1 supported") ;
        }
        }
        
        catch(Exception e)
		{
        	mLogger.error(HrReviewApplLogMessage.create(HrReviewApplLogMessage.FATAL_ERROR,
					Constants.ERROR_GET_EXPORTTOEXCEL,e));
			mLogger.error(ERROR_GET_EXPORTTOEXCEL, e);
		}
        
        if(mLogger.isDebugEnabled()){
            mLogger.debug("result: '"+result+"'");
            mLogger.debug("End getExcelSheetReport method in ReportingService");
        }
		return result ;
	}
	
    @GET
	@Produces(APPLICATION_JSON)
	@Path("/getPerformanceSummaryReport")
	public String getPerformanceSummaryReport(@QueryParam("reportCategory") String reportCategory, 
			@QueryParam("associateId") String associateId,@Context HttpServletResponse httpResponse,@DefaultValue("1") @QueryParam("version") int version) throws HrReviewException
	{
		if(mLogger.isDebugEnabled()){
           	mLogger.debug(this.getClass() + "Enters getPerformanceSummaryReport method in ReportingService");
           	 mLogger.debug("version: '"+version+"'");
           	 mLogger.debug("ReportCategory: "+reportCategory);
           	 mLogger.debug("AssociateId: "+associateId);
           }
        
        String result = null ;
        
        byte pdfBytes[] = null;
       
         
        try
        {
        if(version == 1)
        {
        	if(mLogger.isDebugEnabled()) 
        	{ 
        		mLogger.debug("in version 1"); 
        	}
        	
        	        	
			pdfBytes=ReportingManager.getPerformanceSummaryReport(reportCategory,associateId,httpResponse);
        	httpResponse.setContentType(APPLICATION_PDF);
			if(pdfBytes!=null)
			{
			httpResponse.setContentLength(pdfBytes.length);
			httpResponse.getOutputStream().write(pdfBytes);
			}
        }
        else
        {
        	throw new HrReviewException(Constants.BAD_REQUEST, "Invalid version.  1 supported") ;
        }
        }
        
        catch(Exception e)
		{
        	mLogger.error(HrReviewApplLogMessage.create(HrReviewApplLogMessage.FATAL_ERROR,
					Constants.ERROR_GET_PERFORMANCESUMMARY,e));
			mLogger.error(ERROR_GET_PERFORMANCESUMMARY, e);
		}
        
        if(mLogger.isDebugEnabled()){
            mLogger.debug("result: '"+result+"'");
            mLogger.debug("End getPerformanceSummaryReport method in ReportingService");
        }
		return result ;
	}
    
    @GET
	@Produces(APPLICATION_JSON)
	@Path("/getAssociateProfileReport")
	public String getAssociateProfileReport(@QueryParam("reportCategory") String reportCategory,  
			@QueryParam("associateId") String associateId,@Context HttpServletResponse httpResponse,@DefaultValue("1") @QueryParam("version") int version) throws HrReviewException
	{
		if(mLogger.isDebugEnabled()){
           	mLogger.debug(this.getClass() + "Enters getAssociateProfileReport method in ReportingService " );
           	mLogger.debug("version: '"+version+"'");
            mLogger.debug("ReportCategory"+reportCategory);
            mLogger.debug("AssociateId"+associateId);
           }
        
        String result = null ;
        byte pdfBytes[] = null;
        try
        {
        if(version == 1)
        {
        	if(mLogger.isDebugEnabled()) 
        	{ 
        		mLogger.debug("in version 1"); 
        	}
        	
        	pdfBytes=ReportingManager.getAssociateProfileReport(reportCategory,associateId,httpResponse);
        	httpResponse.setContentType(APPLICATION_PDF);
			if(pdfBytes!=null)
			{
			httpResponse.setContentLength(pdfBytes.length);
			httpResponse.getOutputStream().write(pdfBytes);
			}
        }
        else
        {
        	throw new HrReviewException(Constants.BAD_REQUEST, "Invalid version.  1 supported") ;
        }
        }
        catch(Exception e)
		{
        	mLogger.error(HrReviewApplLogMessage.create(HrReviewApplLogMessage.FATAL_ERROR,
					Constants.ERROR_GET_ASSOCIATE_PROFILE,e));
			mLogger.error(ERROR_GET_ASSOCIATE_PROFILE, e);
		}
        
        if(mLogger.isDebugEnabled()){
            mLogger.debug("result: '"+result+"'");
            mLogger.debug("End getAssociateProfileReport method in ReportingService");
        }
		return result ;
	}
    
	}

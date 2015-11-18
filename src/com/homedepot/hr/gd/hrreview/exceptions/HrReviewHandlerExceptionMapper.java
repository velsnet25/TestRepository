package com.homedepot.hr.gd.hrreview.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


@Provider
public class HrReviewHandlerExceptionMapper implements ExceptionMapper<HrReviewException>  {    
	
	@Override    
	public Response toResponse(HrReviewException exception)      {        
		return Response.status(exception.getcode()).entity(exception.getMessage()).type("application/json").build();    
	} 
}  

package com.homedepot.hr.gd.hrreview.response;

import java.util.List;

import com.homedepot.hr.gd.hrreview.dto.ExcelReportDTO;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ReportingResponse")
public class ReportingResponse {
	
	//@XStreamAlias("ExcelReportDTO")
	  protected List<ExcelReportDTO> excelReportDTO;

	public List<ExcelReportDTO> getExcelReportDTO() {
		return excelReportDTO;
	}
 
	
	public void setExcelReportDTO(List<ExcelReportDTO> excelReportDTO) {
		this.excelReportDTO = excelReportDTO;
	}
	  
}

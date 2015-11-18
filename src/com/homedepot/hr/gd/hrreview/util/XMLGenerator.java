package com.homedepot.hr.gd.hrreview.util;

import java.rmi.server.ExportException;

import org.apache.log4j.Logger;

import com.homedepot.hr.gd.hrreview.dao.ExcelReportingDAO;
import com.homedepot.hr.gd.hrreview.dto.AssocProfilePersonal;
import com.homedepot.hr.gd.hrreview.dto.AssocProfilePrintDTO;
import com.homedepot.hr.gd.hrreview.dto.Associate;
import com.homedepot.hr.gd.hrreview.dto.Education;
import com.homedepot.hr.gd.hrreview.dto.EducationList;
import com.homedepot.hr.gd.hrreview.dto.ExcelReportDTO;
import com.homedepot.hr.gd.hrreview.dto.ExternalWorkHistory;
import com.homedepot.hr.gd.hrreview.dto.ExternalWorkHistoryList;
import com.homedepot.hr.gd.hrreview.dto.KeyCourses;
import com.homedepot.hr.gd.hrreview.dto.KeyCoursesList;
import com.homedepot.hr.gd.hrreview.dto.Language;
import com.homedepot.hr.gd.hrreview.dto.LanguageList;
import com.homedepot.hr.gd.hrreview.dto.Mobility;
import com.homedepot.hr.gd.hrreview.dto.MobilityLang;
import com.homedepot.hr.gd.hrreview.dto.Planning;
import com.homedepot.hr.gd.hrreview.dto.PlanningList;
import com.homedepot.hr.gd.hrreview.dto.THDWorkHistory;
import com.homedepot.hr.gd.hrreview.dto.THDWorkHistoryList;
import com.homedepot.hr.gd.hrreview.response.ReportingResponse;
import com.sun.xml.bind.v2.schemagen.xmlschema.List;

public class XMLGenerator {
	private static final Logger logger = Logger.getLogger(XMLGenerator.class);

	private final static LiberalXStream xstream = new LiberalXStream() {
		{
			
			this.processAnnotations(AssocProfilePrintDTO.class);
			this.processAnnotations(AssocProfilePersonal.class);
			this.processAnnotations(Education.class);
			this.processAnnotations(EducationList.class);
			this.processAnnotations(KeyCourses.class);
			this.processAnnotations(KeyCoursesList.class);
			this.processAnnotations(ExternalWorkHistory.class);
			this.processAnnotations(ExternalWorkHistoryList.class);
			this.processAnnotations(THDWorkHistory.class);
			this.processAnnotations(THDWorkHistoryList.class);
			this.processAnnotations(Planning.class);
			this.processAnnotations(PlanningList.class);
			this.processAnnotations(MobilityLang.class);
			this.processAnnotations(Mobility.class);
			this.processAnnotations(Language.class);
			this.processAnnotations(LanguageList.class);
			this.processAnnotations(Associate.class);
			this.processAnnotations(ReportingResponse.class);
			this.processAnnotations(ExcelReportDTO.class);
			
			
		}
	};


	/**
	 * Returns a string representing the exception.
	 * 
	 * @return String XML
	 */

	public static String generateXML(Object to) {
		String xml = null;
		try {
			xstream.alias("", List.class);
			xml = xstream.toXML(to);
		} catch (Exception e) {
			logger.error(e);
		}
		return xml;
	}
	
	
}

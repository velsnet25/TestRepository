package com.homedepot.hr.gd.hrreview.util;

/*
 * THIS CLASS HAS BEEN CREATED AS PART OF "WAS to Tomcat Migration Project
 * 
 * XMLReader implementation for the PerfmDevSumLabel class. This class is used to
 * generate SAX events from the PerfmDevSumLabel class.
 * 
 */

//Java
import java.io.IOException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


 
 
public class PerfmDevSumRptXMLReader extends AbstractObjectReader {

    /**
     * @see org.xml.sax.XMLReader#parse(InputSource)
     */
    public void parse(InputSource input) throws IOException, SAXException {
    	
    	
        if (input instanceof PerfmDevSumLabelInputSource) {
            parse(((PerfmDevSumLabelInputSource)input).getPerfmDevSumLabel());
        } else {
            throw new SAXException("Unsupported InputSource specified. Must be a ProjectTeamInputSource");
        }
    }


    /**
     * Starts parsing the PerfmDevSumLabel object.
     * @param perfmDevSum The object to parse
     * @throws SAXException In case of a problem during SAX event generation
     */
    public void parse(PerfmDevSumLabel perfmDevSum) throws SAXException {
        if (perfmDevSum == null) {
            throw new NullPointerException("Parameter projectTeam must not be null");
        }
        if (handler == null) {
            throw new IllegalStateException("ContentHandler not set");
        }
        
        //Start the document
        handler.startDocument();
        
        //Generate SAX events for the ProjectTeam
        generateFor(perfmDevSum);
        
        //End the document
        handler.endDocument();        
    }

    
    /**
     * Generates SAX events for a PerfmDevSumLabel object.
     * @param perfmHead PerfmDevSumLabel object to use
     * @throws SAXException In case of a problem during SAX event generation
     */
    protected void generateFor(PerfmDevSumLabel perfmDevSum) throws SAXException {
        if (perfmDevSum == null) {
            throw new NullPointerException("Parameter projectTeam must not be null");
        }
        if (handler == null) {
            throw new IllegalStateException("ContentHandler not set");
        }
        
               
        handler.startElement("PerfmDevSumRpt");
        handler.element("asAppears",perfmDevSum.getAsAppear());
        handler.element("AssociateComment",perfmDevSum.getAssoComment());
        handler.element("AssociateDate",perfmDevSum.getAssoDate());
        handler.element("AttachSheet",perfmDevSum.getAttachSheet());
        handler.element("BusinessUnit",perfmDevSum.getBusUnit());
        handler.element("DevNeed",perfmDevSum.getDevNeed());
        handler.element("DevPlan",perfmDevSum.getDevPlan());
        handler.element("DevSummary",perfmDevSum.getDevSummary());
        handler.element("HomeDepot",perfmDevSum.getHomeDepot());
        handler.element("HourAssociate",perfmDevSum.getHourAssociate());
        handler.element("HourStore",perfmDevSum.getHourStore());
        handler.element("KeyStrength",perfmDevSum.getKeyStrength());
        handler.element("LeadCode",perfmDevSum.getLeadCode());
        handler.element("LeaderDate",perfmDevSum.getLeaderDate());
        handler.element("LeaderSummary",perfmDevSum.getLeaderSummary());
        handler.element("Name",perfmDevSum.getName());
        handler.element("NextPos",perfmDevSum.getNextPos());
        handler.element("PerfmCode",perfmDevSum.getPerfCode());
        handler.element("PosComment",perfmDevSum.getPosComment());
        handler.element("Position",perfmDevSum.getPosition());
        handler.element("PotentialCode",perfmDevSum.getPotenCode());
        handler.element("PotentialTime",perfmDevSum.getPotenTime());
        handler.element("ReportContinue",perfmDevSum.getRepContinue());
        handler.element("SummaryApproval",perfmDevSum.getSummApproval());
        handler.element("Title",perfmDevSum.getTitle());
        
        handler.element("DocumentId",perfmDevSum.getDocumentId());
        handler.element("LabelType",perfmDevSum.getLabelType());
        handler.element("Printedon",perfmDevSum.getPrintedOnText());
        handler.element("ReportCodeSummary",perfmDevSum.getReportCodeSummary());
        handler.element("PageFooter",perfmDevSum.getPageFooter());
        handler.element("PrintDate", perfmDevSum.getPrintDate());
        
        handler.element("PrintDate", perfmDevSum.getPrintDate());
        handler.element("AssociateName",perfmDevSum.getAssociateName()); 
        handler.element("BusUnit",perfmDevSum.getBusinessUnit());
        handler.element("JobTitle",perfmDevSum.getJobTitle());
        
        handler.element("PositionName", perfmDevSum.getPositionName());
        handler.element("PotentialTiming", perfmDevSum.getPotentialTiming());
                  
        
        handler.element("TopPerformer",perfmDevSum.getTopPerfm());
        handler.element("Unaccept", perfmDevSum.getUnaccept());
        handler.element("valuedAssociate",perfmDevSum.getValuedAsso() );
        handler.element("Promotable",perfmDevSum.getPromote());
        handler.element("WellPosition",perfmDevSum.getWellPosition());
        handler.element("NotApplicable", perfmDevSum.getNotaAppl());
        
        handler.element("LeadSummRpt", perfmDevSum.getLeadSummaryRpt() );
        handler.element("keyStrenRpt", perfmDevSum.getKeyStrengthRpt());
        handler.element("keyDevNeed", perfmDevSum.getKeyDevPlanRpt() );
        handler.element("DevTrain", perfmDevSum.getDevTrainRpt());
        handler.element("AssociateCommentRpt",perfmDevSum.getAssociateComment());
        handler.element("PositionComment", perfmDevSum.getPositionComment());
        handler.element("DocumentIdNo", perfmDevSum.getDocumentIdNo());
        handler.element("ReportDesc",perfmDevSum.getReportDesc());
        
        handler.element("PerfmRateCode", perfmDevSum.getPerfmRateCode());
        handler.element("LeadRateCode", perfmDevSum.getLeadRateCode());
        handler.element("PotenRateCode", perfmDevSum.getPotenRateCode());
        
        handler.element("TopPerformCode",perfmDevSum.getTopPerfmCode() );
        handler.element("ValueAssociateCode",perfmDevSum.getValuedAssoCode() );
        handler.element("UnAcceptableCode",perfmDevSum.getUnacceptCode());
        
        handler.element("PromoteRateCode", perfmDevSum.getPromoteCode());
        handler.element("WellPositionRateCode", perfmDevSum.getWellPositionCode());
        handler.element("NotApplicableCode", perfmDevSum.getNotaApplCode());
        
        handler.endElement("PerfmDevSumRpt");
       
               
    }
    
     

    
}

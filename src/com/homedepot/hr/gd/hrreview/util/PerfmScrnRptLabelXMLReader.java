package com.homedepot.hr.gd.hrreview.util;

/*
 * THIS CLASS HAS BEEN CREATED AS PART OF "WAS to Tomcat Migration Project
 * 
 * XMLReader implementation for the PerfmScrnHeadLabel class. This class is used to
 * generate SAX events from the PerfmScrnHeadLabel class.
 * 
 */

//Java
import java.util.Iterator;
import java.io.IOException;

//SAX
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.homedepot.hr.gd.hrreview.dto.PerfmScrnHeadLabel;




public class PerfmScrnRptLabelXMLReader extends AbstractObjectReader {

    /**
     * @see org.xml.sax.XMLReader#parse(InputSource)
     */
    public void parse(InputSource input) throws IOException, SAXException {
    	
    	
        if (input instanceof PerfmScrnRptLabelInputSource) {
            parse(((PerfmScrnRptLabelInputSource)input).getPerfmScrnHeadLabel());
        } else {
            throw new SAXException("Unsupported InputSource specified. Must be a ProjectTeamInputSource");
        }
    }


    /**
     * Starts parsing the PerfmScrnHeadLabel object.
     * @param perfmHead The object to parse
     * @throws SAXException In case of a problem during SAX event generation
     */
    public void parse(PerfmScrnHeadLabel perfmHead) throws SAXException {
        if (perfmHead == null) {
            throw new NullPointerException("Parameter projectTeam must not be null");
        }
        if (handler == null) {
            throw new IllegalStateException("ContentHandler not set");
        }
        
        //Start the document
        handler.startDocument();
        
        //Generate SAX events for the ProjectTeam
        generateFor(perfmHead);
        
        //End the document
        handler.endDocument();        
    }

    
    /**
     * Generates SAX events for a PerfmScrnHeadLabel object.
     * @param perfmHead PerfmScrnHeadLabel object to use
     * @throws SAXException In case of a problem during SAX event generation
     */
    protected void generateFor(PerfmScrnHeadLabel perfmHead) throws SAXException {
        if (perfmHead == null) {
            throw new NullPointerException("Parameter projectTeam must not be null");
        }
        if (handler == null) {
            throw new IllegalStateException("ContentHandler not set");
        }
        
               
        handler.startElement("PerfmScrnRpt");
        handler.element("financial", perfmHead.getBlockFinancial());
        handler.element("operational", perfmHead.getBlockOperational());
        handler.element("people", perfmHead.getBlockPeople());
        handler.element("customer", perfmHead.getCustomer());
        handler.element("screen", perfmHead.getPerfScrn());
        handler.element("asAppears", perfmHead.getPerfScrnAsAppears());
        handler.element("associate", perfmHead.getPerfScrnAssociate());
        handler.element("busunit", perfmHead.getPerfScrnBusUnit());
        handler.element("date", perfmHead.getPerfScrnDate());
        handler.element("homedepot", perfmHead.getPerfScrnHomeDepot());
        handler.element("goals", perfmHead.getPerfScrnIndivGoals());
        handler.element("goalside1", perfmHead.getPerfScrnIndivGoalsSide1());
        handler.element("goalside2", perfmHead.getPerfScrnIndivGoalsSide2());
        handler.element("goalside", perfmHead.getPerfScrnIndivGoalsSide());
        handler.element("goalside3", perfmHead.getPerfScrnIndivGoalsSide3());
        handler.element("results", perfmHead.getPerfScrnIndivResults());
        handler.element("resultside1", perfmHead.getPerfScrnIndivSide1());
        handler.element("resultside2", perfmHead.getPerfScrnIndivSide2());
        handler.element("leader", perfmHead.getPerfScrnLeader());
        handler.element("scrname", perfmHead.getPerfScrnName());
        handler.element("title", perfmHead.getPerfScrnTitle());     
       
        handler.element("DocumentId",perfmHead.getDocumentId());
        handler.element("LabelType",perfmHead.getLabelType());
        handler.element("Printedon",perfmHead.getPrintedOnText());
        handler.element("ReportCodeSummary",perfmHead.getReportCodeSummary());
        handler.element("PageFooter",perfmHead.getPageFooter());
        handler.element("SummaryApproval",perfmHead.getSummaryApproval());
        handler.element("PrintDate", perfmHead.getPrintDate());
        
        handler.element("AssociateName",perfmHead.getAssociateName()); 
        handler.element("BusinessUnit",perfmHead.getBusinessUnit());
        handler.element("JobTitle",perfmHead.getJobTitle());
        handler.element("DocumentIdValue",perfmHead.getDocumentIdValue());
        
        handler.element("AccomplishFinancial",perfmHead.getAccomSectFinancial()); 
        handler.element("AccomplishOperational",perfmHead.getAccomSectOperational());      
        handler.element("AccomplishPeople",perfmHead.getAccomSectPeople());
        handler.element("AccomplishCustomer",perfmHead.getAccomSectProcess());
        handler.element("GoalFinancial",perfmHead.getGoalSectFinancial()); 
        handler.element("GoalOperational",perfmHead.getGoalSectOperational()); 
        handler.element("GoalPeople",perfmHead.getGoalSectPeople()); 
        handler.element("GoalCustomer",perfmHead.getGoalSectProcess()); 
        
        
        
        
       
        handler.endElement("PerfmScrnRpt");
       
    }
    
    /*private void generate(PerfmScrnFootLabel perfmFoot)throws SAXException
    {
    	 if (perfmFoot == null) {
             throw new NullPointerException("Parameter projectTeam must not be null");
         }
         if (handler == null) {
             throw new IllegalStateException("ContentHandler not set");
         }
         
         handler.startElement("PerfmFooterLabel");
         handler.element("DocumentId",perfmFoot.getDocumentId());
         handler.element("LabelType",perfmFoot.getLabelType());
         handler.element("Printedon",perfmFoot.getPrintedOnText());
         handler.element("ReportCodeSummary",perfmFoot.getReportCodeSummary());
         handler.element("PageFooter",perfmFoot.getPageFooter());
         handler.endElement("PerfmFooterLabel");
         
         
    }
*/    

    
}

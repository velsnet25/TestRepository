package com.homedepot.hr.gd.hrreview.dto;

/*
 * THIS CLASS HAS BEEN CREATED AS PART OF "WAS to Tomcat Migration Project
 * 
 * Class Name  : PerfmScrnHeadLabel.java
 * Application : ConvertToPDF.java
 * 
 */

import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;

import com.homedepot.hr.gd.hrreview.util.PerfmScrnRptLabelXMLReader;

public class PerfmScrnHeadLabel {
	
	private String  perfScrn=null;
	private String  perfScrnHomeDepot= null;
	private String  perfScrnName= null;
	private String  perfScrnBusUnit= null;
	private String  perfScrnTitle= null;
	private String  perfScrnAsAppears= null;
	private String  blockFinancial= null;
	private String  blockOperational= null;
	private String  customer=null;
	private String  blockProcess= null;
	private String  blockPeople= null;
	private String  perfScrnIndivGoals= null;
	private String  perfScrnIndivGoalsSide1= null;
	private String  perfScrnIndivGoalsSide2= null;
	private String  perfScrnIndivGoalsSide= null;
	private String  perfScrnIndivGoalsSide3= null;
	private String  perfScrnIndivResults= null;
	private String  perfScrnIndivSide1= null;
	private String  perfScrnIndivSide2= null;
	private String  perfScrnAssociate= null;
	private String  perfScrnDate= null;
	private String  perfScrnLeader= null;
	private String  summaryApproval=null;
	
	private String reportCodeSummary=null;
	private String printedOnText=null;
	private String pageFooter=null;
	private String documentId=null;
	private String labelType=null;
	private String printDate=null;
	private String documentIdValue=null;
	
	private String associateName=null;
	private String businessUnit=null;
	private String jobTitle=null;
	
	private String AccomSectPeople=null;
	private String AccomSectProcess=null;
	private String AccomSectOperational=null;
	private String AccomSectFinancial=null;
	private String GoalSectPeople=null;
	private String GoalSectProcess=null;
	private String GoalSectOperational=null;
	private String GoalSectFinancial=null;
	
	public String getAccomSectPeople() {
		return AccomSectPeople;
	}
	public void setAccomSectPeople(String accomSectPeople) {
		AccomSectPeople = accomSectPeople;
	}
	public String getAccomSectProcess() {
		return AccomSectProcess;
	}
	public void setAccomSectProcess(String accomSectProcess) {
		AccomSectProcess = accomSectProcess;
	}
	public String getAccomSectOperational() {
		return AccomSectOperational;
	}
	public void setAccomSectOperational(String accomSectOperational) {
		AccomSectOperational = accomSectOperational;
	}
	public String getAccomSectFinancial() {
		return AccomSectFinancial;
	}
	public void setAccomSectFinancial(String accomSectFinancial) {
		AccomSectFinancial = accomSectFinancial;
	}
	public String getGoalSectPeople() {
		return GoalSectPeople;
	}
	public void setGoalSectPeople(String goalSectPeople) {
		GoalSectPeople = goalSectPeople;
	}
	public String getGoalSectProcess() {
		return GoalSectProcess;
	}
	public void setGoalSectProcess(String goalSectProcess) {
		GoalSectProcess = goalSectProcess;
	}
	public String getGoalSectOperational() {
		return GoalSectOperational;
	}
	public void setGoalSectOperational(String goalSectOperational) {
		GoalSectOperational = goalSectOperational;
	}
	public String getGoalSectFinancial() {
		return GoalSectFinancial;
	}
	public void setGoalSectFinancial(String goalSectFinancial) {
		GoalSectFinancial = goalSectFinancial;
	}
	
	
	public String getAssociateName() {
		return associateName;
	}
	public void setAssociateName(String associateName) {
		this.associateName = associateName;
	}
	public String getBusinessUnit() {
		return businessUnit;
	}
	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getPerfScrn() {
		return perfScrn;
	}
	public void setPerfScrn(String perfScrn) {
		this.perfScrn = perfScrn;
	}

	public String getPerfScrnHomeDepot() {
		return perfScrnHomeDepot;
	}
	public void setPerfScrnHomeDepot(String perfScrnHomeDepot) {
		this.perfScrnHomeDepot = perfScrnHomeDepot;
	}
	public String getPerfScrnName() {
		return perfScrnName;
	}
	public void setPerfScrnName(String perfScrnName) {
		this.perfScrnName = perfScrnName;
	}
	public String getPerfScrnBusUnit() {
		return perfScrnBusUnit;
	}
	public void setPerfScrnBusUnit(String perfScrnBusUnit) {
		this.perfScrnBusUnit = perfScrnBusUnit;
	}
	public String getPerfScrnTitle() {
		return perfScrnTitle;
	}
	public void setPerfScrnTitle(String perfScrnTitle) {
		this.perfScrnTitle = perfScrnTitle;
	}
	public String getPerfScrnAsAppears() {
		return perfScrnAsAppears;
	}
	public void setPerfScrnAsAppears(String perfScrnAsAppears) {
		this.perfScrnAsAppears = perfScrnAsAppears;
	}
	public String getBlockFinancial() {
		return blockFinancial;
	}
	public void setBlockFinancial(String blockFinancial) {
		this.blockFinancial = blockFinancial;
	}
	public String getBlockOperational() {
		return blockOperational;
	}
	public void setBlockOperational(String blockOperational) {
		this.blockOperational = blockOperational;
	}
	public String getBlockProcess() {
		return blockProcess;
	}
	public void setBlockProcess(String blockProcess) {
		this.blockProcess = blockProcess;
	}
	public String getBlockPeople() {
		return blockPeople;
	}
	public void setBlockPeople(String blockPeople) {
		this.blockPeople = blockPeople;
	}
	public String getPerfScrnIndivGoals() {
		return perfScrnIndivGoals;
	}
	public void setPerfScrnIndivGoals(String perfScrnIndivGoals) {
		this.perfScrnIndivGoals = perfScrnIndivGoals;
	}
	public String getPerfScrnIndivGoalsSide1() {
		return perfScrnIndivGoalsSide1;
	}
	public void setPerfScrnIndivGoalsSide1(String perfScrnIndivGoalsSide1) {
		this.perfScrnIndivGoalsSide1 = perfScrnIndivGoalsSide1;
	}
	public String getPerfScrnIndivGoalsSide2() {
		return perfScrnIndivGoalsSide2;
	}
	public void setPerfScrnIndivGoalsSide2(String perfScrnIndivGoalsSide2) {
		this.perfScrnIndivGoalsSide2 = perfScrnIndivGoalsSide2;
	}
	public String getPerfScrnIndivGoalsSide3() {
		return perfScrnIndivGoalsSide3;
	}
	public void setPerfScrnIndivGoalsSide3(String perfScrnIndivGoalsSide3) {
		this.perfScrnIndivGoalsSide3 = perfScrnIndivGoalsSide3;
	}
	public String getPerfScrnIndivResults() {
		return perfScrnIndivResults;
	}
	public void setPerfScrnIndivResults(String perfScrnIndivResults) {
		this.perfScrnIndivResults = perfScrnIndivResults;
	}
	public String getPerfScrnIndivSide1() {
		return perfScrnIndivSide1;
	}
	public void setPerfScrnIndivSide1(String perfScrnIndivSide1) {
		this.perfScrnIndivSide1 = perfScrnIndivSide1;
	}
	public String getPerfScrnIndivSide2() {
		return perfScrnIndivSide2;
	}
	public void setPerfScrnIndivSide2(String perfScrnIndivSide2) {
		this.perfScrnIndivSide2 = perfScrnIndivSide2;
	}
	public String getPerfScrnAssociate() {
		return perfScrnAssociate;
	}
	public void setPerfScrnAssociate(String perfScrnAssociate) {
		this.perfScrnAssociate = perfScrnAssociate;
	}
	public String getPerfScrnDate() {
		return perfScrnDate;
	}
	public void setPerfScrnDate(String perfScrnDate) {
		this.perfScrnDate = perfScrnDate;
	}
	public String getPerfScrnLeader() {
		return perfScrnLeader;
	}
	public void setPerfScrnLeader(String perfScrnLeader) {
		this.perfScrnLeader = perfScrnLeader;
	}
	
	public String getReportCodeSummary() {
		return reportCodeSummary;
	}
	public void setReportCodeSummary(String reportCodeSummary) {
		this.reportCodeSummary = reportCodeSummary;
	}
	public String getPrintedOnText() {
		return printedOnText;
	}
	public void setPrintedOnText(String printedOnText) {
		this.printedOnText = printedOnText;
	}
	public String getPageFooter() {
		return pageFooter;
	}
	public void setPageFooter(String pageFooter) {
		this.pageFooter = pageFooter;
	}
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	public String getLabelType() {
		return labelType;
	}
	public void setLabelType(String labelType) {
		this.labelType = labelType;
	}
	

	public String getSummaryApproval() {
		return summaryApproval;
	}
	public void setSummaryApproval(String summaryApproval) {
		this.summaryApproval = summaryApproval;
	}
	
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	
	public String getPerfScrnIndivGoalsSide() {
		return perfScrnIndivGoalsSide;
	}
	public void setPerfScrnIndivGoalsSide(String perfScrnIndivGoalsSide) {
		this.perfScrnIndivGoalsSide = perfScrnIndivGoalsSide;
	}
	
	public String getPrintDate() {
		return printDate;
	}
	public void setPrintDate(String printDate) {
		this.printDate = printDate;
	}
	
	public String getDocumentIdValue() {
		return documentIdValue;
	}
	public void setDocumentIdValue(String documentIdValue) {
		this.documentIdValue = documentIdValue;
	}
	 /**
     * Resturns a Source object for this object so it can be used as input for
     * a JAXP transformation.
     * @return Source The Source object
     */
    public Source getSourceForPerfmHeadLabel() {
        return new SAXSource (new PerfmScrnRptLabelXMLReader(),
                new PerfmScrnRptLabelInputSource(this));
    }
	
	
}
	
	
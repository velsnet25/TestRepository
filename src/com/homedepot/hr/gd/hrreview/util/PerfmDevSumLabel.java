package com.homedepot.hr.gd.hrreview.util;

/*
 * THIS CLASS HAS BEEN CREATED AS PART OF "WAS to Tomcat Migration Project
 * 
 * Class Name  : PerfmDevSumLabel.java
 * Application : ConvertToPDF.java
 * 
 */
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;

import com.homedepot.hr.gd.hrreview.util.PerfmDevSumRptXMLReader;

public class PerfmDevSumLabel {

	private String devSummary=null;
	private String homeDepot=null;
	private String name=null;
	private String busUnit=null;
	private String title=null;
	private String asAppear=null;
	private String hourAssociate=null;
	private String leaderSummary=null;
	private String keyStrength=null;
	private String devNeed=null;
	private String nextPos=null;
	private String hourStore=null;
	private String position=null;
	private String potenTime=null;
	private String assoComment=null;
	private String attachSheet=null;
	private String perfCode=null;
	private String leadCode=null;
	private String potenCode=null;
	private String summApproval=null;
	private String leaderDate=null;
	private String assoDate=null;
	private String repContinue=null;
	private String posComment=null;
	private String devPlan=null;
	
	private String printedOnText=null;
	private String pageFooter=null;
	private String documentId=null;
	private String labelType=null;
	private String printDate=null;
	private String ReportCodeSummary=null;
	
	private String associateName=null;
	private String businessUnit=null;
	private String jobTitle=null;
	
	private String positionName=null;
	private String potentialTiming=null;
	private String positionComment=null;
	
	private String topPerfm=null;
	private String valuedAsso=null;
	private String unaccept=null;
	private String promote=null;
	private String wellPosition=null;
	private String notaAppl=null;
	
	private String topPerfmCode=null;
	private String valuedAssoCode=null;
	private String unacceptCode=null;
	private String promoteCode=null;
	private String wellPositionCode=null;
	private String notaApplCode=null;
	
	private String leadSummaryRpt=null;
	private String keyStrengthRpt=null;
	private String keyDevPlanRpt=null;
	private String devTrainRpt=null;
	private String associateComment=null;
	private String documentIdNo=null;
	private String reportDesc=null;
	
	private String perfmRateCode=null;
	private String leadRateCode=null;
	private String potenRateCode=null;
	
	public String getTopPerfmCode() {
		return topPerfmCode;
	}
	public void setTopPerfmCode(String topPerfmCode) {
		this.topPerfmCode = topPerfmCode;
	}
	public String getValuedAssoCode() {
		return valuedAssoCode;
	}
	public void setValuedAssoCode(String valuedAssoCode) {
		this.valuedAssoCode = valuedAssoCode;
	}
	public String getUnacceptCode() {
		return unacceptCode;
	}
	public void setUnacceptCode(String unacceptCode) {
		this.unacceptCode = unacceptCode;
	}
	public String getPromoteCode() {
		return promoteCode;
	}
	public void setPromoteCode(String promoteCode) {
		this.promoteCode = promoteCode;
	}
	public String getWellPositionCode() {
		return wellPositionCode;
	}
	public void setWellPositionCode(String wellPositionCode) {
		this.wellPositionCode = wellPositionCode;
	}
	public String getNotaApplCode() {
		return notaApplCode;
	}
	public void setNotaApplCode(String notaApplCode) {
		this.notaApplCode = notaApplCode;
	}

	
	public String getPerfmRateCode() {
		return perfmRateCode;
	}
	public void setPerfmRateCode(String perfmRateCode) {
		this.perfmRateCode = perfmRateCode;
	}
	public String getLeadRateCode() {
		return leadRateCode;
	}
	public void setLeadRateCode(String leadRateCode) {
		this.leadRateCode = leadRateCode;
	}
	public String getPotenRateCode() {
		return potenRateCode;
	}
	public void setPotenRateCode(String potenRateCode) {
		this.potenRateCode = potenRateCode;
	}

	public String getAssociateComment() {
		return associateComment;
	}
	public void setAssociateComment(String associateComment) {
		this.associateComment = associateComment;
	}
	public String getDocumentIdNo() {
		return documentIdNo;
	}
	public void setDocumentIdNo(String documentIdNo) {
		this.documentIdNo = documentIdNo;
	}
	public String getReportDesc() {
		return reportDesc;
	}
	public void setReportDesc(String reportDesc) {
		this.reportDesc = reportDesc;
	}


	
	
	
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public String getPotentialTiming() {
		return potentialTiming;
	}
	public void setPotentialTiming(String potentialTiming) {
		this.potentialTiming = potentialTiming;
	}
	public String getPositionComment() {
		return positionComment;
	}
	public void setPositionComment(String positionComment) {
		this.positionComment = positionComment;
	}

	
	public String getTopPerfm() {
		return topPerfm;
	}
	public void setTopPerfm(String topPerfm) {
		this.topPerfm = topPerfm;
	}
	public String getValuedAsso() {
		return valuedAsso;
	}
	public void setValuedAsso(String valuedAsso) {
		this.valuedAsso = valuedAsso;
	}
	public String getUnaccept() {
		return unaccept;
	}
	public void setUnaccept(String unaccept) {
		this.unaccept = unaccept;
	}
	public String getPromote() {
		return promote;
	}
	public void setPromote(String promote) {
		this.promote = promote;
	}
	public String getWellPosition() {
		return wellPosition;
	}
	public void setWellPosition(String wellPosition) {
		this.wellPosition = wellPosition;
	}
	public String getNotaAppl() {
		return notaAppl;
	}
	public void setNotaAppl(String notaAppl) {
		this.notaAppl = notaAppl;
	}

	
	
	public String getReportCodeSummary() {
		return ReportCodeSummary;
	}
	public void setReportCodeSummary(String reportCodeSummary) {
		ReportCodeSummary = reportCodeSummary;
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
	public String getPrintDate() {
		return printDate;
	}
	public void setPrintDate(String printDate) {
		this.printDate = printDate;
	}

	
	
	
	public String getDevSummary() {
		return devSummary;
	}
	public void setDevSummary(String devSummary) {
		this.devSummary = devSummary;
	}
	public String getHomeDepot() {
		return homeDepot;
	}
	public void setHomeDepot(String homeDepot) {
		this.homeDepot = homeDepot;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBusUnit() {
		return busUnit;
	}
	public void setBusUnit(String busUnit) {
		this.busUnit = busUnit;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAsAppear() {
		return asAppear;
	}
	public void setAsAppear(String asAppear) {
		this.asAppear = asAppear;
	}
	public String getHourAssociate() {
		return hourAssociate;
	}
	public void setHourAssociate(String hourAssociate) {
		this.hourAssociate = hourAssociate;
	}
	public String getLeaderSummary() {
		return leaderSummary;
	}
	public void setLeaderSummary(String leaderSummary) {
		this.leaderSummary = leaderSummary;
	}
	public String getKeyStrength() {
		return keyStrength;
	}
	public void setKeyStrength(String keyStrength) {
		this.keyStrength = keyStrength;
	}
	public String getDevNeed() {
		return devNeed;
	}
	public void setDevNeed(String devNeed) {
		this.devNeed = devNeed;
	}
	public String getNextPos() {
		return nextPos;
	}
	public void setNextPos(String nextPos) {
		this.nextPos = nextPos;
	}
	public String getHourStore() {
		return hourStore;
	}
	public void setHourStore(String hourStore) {
		this.hourStore = hourStore;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getPotenTime() {
		return potenTime;
	}
	public void setPotenTime(String potenTime) {
		this.potenTime = potenTime;
	}
	public String getAssoComment() {
		return assoComment;
	}
	public void setAssoComment(String assoComment) {
		this.assoComment = assoComment;
	}
	public String getAttachSheet() {
		return attachSheet;
	}
	public void setAttachSheet(String attachSheet) {
		this.attachSheet = attachSheet;
	}
	public String getPerfCode() {
		return perfCode;
	}
	public void setPerfCode(String perfCode) {
		this.perfCode = perfCode;
	}
	public String getLeadCode() {
		return leadCode;
	}
	public void setLeadCode(String leadCode) {
		this.leadCode = leadCode;
	}
	public String getPotenCode() {
		return potenCode;
	}
	public void setPotenCode(String potenCode) {
		this.potenCode = potenCode;
	}
	public String getSummApproval() {
		return summApproval;
	}
	public void setSummApproval(String summApproval) {
		this.summApproval = summApproval;
	}
	public String getLeaderDate() {
		return leaderDate;
	}
	public void setLeaderDate(String leaderDate) {
		this.leaderDate = leaderDate;
	}
	public String getAssoDate() {
		return assoDate;
	}
	public void setAssoDate(String assoDate) {
		this.assoDate = assoDate;
	}
	public String getRepContinue() {
		return repContinue;
	}
	public void setRepContinue(String repContinue) {
		this.repContinue = repContinue;
	}
	public String getPosComment() {
		return posComment;
	}
	public void setPosComment(String posComment) {
		this.posComment = posComment;
	}
	public String getDevPlan() {
		return devPlan;
	}
	public void setDevPlan(String devPlan) {
		this.devPlan = devPlan;
	}
	
	public String getLeadSummaryRpt() {
		return leadSummaryRpt;
	}
	public void setLeadSummaryRpt(String leadSummaryRpt) {
		this.leadSummaryRpt = leadSummaryRpt;
	}
	public String getKeyStrengthRpt() {
		return keyStrengthRpt;
	}
	public void setKeyStrengthRpt(String keyStrengthRpt) {
		this.keyStrengthRpt = keyStrengthRpt;
	}
	public String getKeyDevPlanRpt() {
		return keyDevPlanRpt;
	}
	public void setKeyDevPlanRpt(String keyDevPlanRpt) {
		this.keyDevPlanRpt = keyDevPlanRpt;
	}
	public String getDevTrainRpt() {
		return devTrainRpt;
	}
	public void setDevTrainRpt(String devTrainRpt) {
		this.devTrainRpt = devTrainRpt;
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

	
	 /**
     * Resturns a Source object for this object so it can be used as input for
     * a JAXP transformation.
     * @return Source The Source object
     */
    public Source getSourceForPerfmHeadLabel() {
        return new SAXSource (new PerfmDevSumRptXMLReader(),
                new PerfmDevSumLabelInputSource(this));
    }
	
}
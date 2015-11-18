package com.homedepot.hr.gd.hrreview.dto;

import java.io.Serializable;
import java.util.Date;
/**
 * <P><B>Data Object Class</B>
 * Class stores the data releated to employee evaluation
 *
 * <P><B>Class Name: PerformanceRating</B><BR>
 * <P><B>Change Log: </B><BR>
 *
 * @author Will Collins
 * @version 1.0
 *
 */
public class PerformanceRating implements Serializable,  Cloneable, Comparable {
	
	static final int PERFORMANCE_CODE = 1;
	static final int POTENTIAL_CODE = 2;
	static final int LEADERSHIP_CODE = 4;
	
	// Contains the evaluation date for the current evaluation
	private Date evalDate = null;
	
	// The rating for Performance
	private String performance = null;
	
	// The rating for Leadership
	private String leadership = null;
	
	// The rating for Potential
	private String potential = null;
	
	private String catagory = null;
	
	private String reviewRating = null;
	
	private String nineBoxCode = null;
	
	public PerformanceRating(){}
	
	
	public void setPerformance(String aPerformance, String aPerformanceDesc){
		this.performance = aPerformance + " "+ aPerformanceDesc;
	}
	public void setPerformance(String aPerformanceDesc){
		this.performance = aPerformanceDesc;
	}
	public void setEvalDate(Date aEvalDate){
		this.evalDate = aEvalDate;
	}
	public void setPotential(String aPotential, String aPotentialDesc){
		this.potential = aPotential + " "+ aPotentialDesc;
	}
	public void setPotential(String aPotentialDesc){
		this.potential = aPotentialDesc;
	}
	public void setLeadership(String aLeadership, String aLeadershipDesc){
		this.leadership = aLeadership + " " + aLeadershipDesc;
	}
	public void setLeadership(String aLeadershipDesc){
		this.leadership = aLeadershipDesc;
	}
	public void setReviewRating(String aRating){
		this.reviewRating = aRating;
	}
	public void setNineBoxCode(String aNineBox){
		this.nineBoxCode = aNineBox;
	}
	
	/**
	 * Accessor method for <B>nineBoxCode</B>
	 */
	public String getNineBoxCode()
	{
		return this.nineBoxCode;
	}
	

	
	/**
	 * Accessor method for <B>evalDate</B>
	 */
	public Date getEvalDate()
	{
		return this.evalDate;
	}
	
	/**
	 * Accessor method for <B>performance</B>
	 */
	public String getPerformance()
	{
		return this.performance;
	}
	
	/**
	 * Accessor method for <B>Leadership</B>
	 */
	public String getLeadership()
	{
		return this.leadership;
	}
	
	/**
	 * Accessor method for <B>potential</B>
	 */
	public String getPotential()
	{
		return this.potential;
	}
	
	/**
	 * Accessor method for <B>reviewRating</B>
	 */
	public String getReviewRating()
	{
		return this.reviewRating;
	}
	
	
	
	/**
	 * method to add ratings codes from Horizons System and Teserract 
	 * review rating 
	 */
	
	public void addOldRatingsCodes(Date aEvalDate,String aRating,String aCtgry,int aCtgryCode,String aNineBox){
		
		if(aCtgryCode == PERFORMANCE_CODE){
			this.performance = aRating;
		}
		else if(aCtgryCode == POTENTIAL_CODE){
			this.potential = aRating;
		}
		else if(aCtgryCode == LEADERSHIP_CODE){
			this.leadership = aRating;
		}
		
		this.nineBoxCode = aNineBox;
		this.evalDate = aEvalDate;
		
	}//addOldRatingsCodes
	
	/* 
	 * Method to define ordering of performance rating objects.
	 * sorting based on evalDate
	 */
	
	public int compareTo(Object cmpRating){
		
		if (evalDate.getTime() > ((PerformanceRating)cmpRating).getEvalDate().getTime()){
			return -1;
		}
		else if (evalDate.getTime() < ((PerformanceRating)cmpRating).getEvalDate().getTime()){
			return 1;
		}
		else{
			return 0;
		}
	}
	
	/**
	 * clone method
	 */
	
	public Object clone() 
	{
		PerformanceRating performanceRating = new PerformanceRating();
		performanceRating.performance = this.performance;
		performanceRating.potential = this.potential;
		performanceRating.leadership = this.leadership;
		performanceRating.reviewRating = this.reviewRating;
		performanceRating.nineBoxCode = this.nineBoxCode;
		
		if(this.evalDate != null)
			performanceRating.evalDate = new Date(this.evalDate.getTime());
		return performanceRating;
	}
}

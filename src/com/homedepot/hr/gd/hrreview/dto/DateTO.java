/*
 * Created on Nov 20, 2009
 *
 * This program is proprietary to The Home Depot and is not to be reproduced,
 * used, or disclosed without permission of:
 *    The Home Depot
 *    2455 Paces Ferry Road, NW
 *    Atlanta, GA 30339-4024
 *
 * Application:RetailStaffing
 *
 * File Name: DateTO.java
 */
package com.homedepot.hr.gd.hrreview.dto;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class DateTO implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5324353819935551681L;
	
	String month;
	String day;
	String year;

	
	/**
	 * @return the month
	 */
	public String getMonth()
	{
		return month;
	}


	/**
	 * @param month the month to set
	 */
	public void setMonth(String month)
	{
		this.month = month;
	}


	/**
	 * @return the day
	 */
	public String getDay()
	{
		return day;
	}


	/**
	 * @param day the day to set
	 */
	public void setDay(String day)
	{
		this.day = day;
	}


	/**
	 * @return the year
	 */
	public String getYear()
	{
		return year;
	}


	/**
	 * @param year the year to set
	 */
	public void setYear(String year)
	{
		this.year = year;
	}


	public String toString()
	{
		return (getMonth() + "/" + getDay() + "/" + getYear());
	}

}

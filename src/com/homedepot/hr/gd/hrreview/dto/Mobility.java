package com.homedepot.hr.gd.hrreview.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class Mobility {
	
@XStreamAlias("short")
private String shortDesc;

public String getShortDesc() {
	return shortDesc;
}

public void setShortDesc(String shortDesc) {
	this.shortDesc = shortDesc;
}


}

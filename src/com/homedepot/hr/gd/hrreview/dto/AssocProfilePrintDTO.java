package com.homedepot.hr.gd.hrreview.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("assoc-pfl")
@XmlRootElement(name = "assoc-pfl")
public class AssocProfilePrintDTO {
	
	@XStreamAlias("associate")
	private Associate associate;
	
	

	public Associate getAssociate() {
		return associate;
	}

	public void setAssociate(Associate associate) {
		this.associate = associate;
	}
	
	
	
	
	
	
    
	
	
    	
}


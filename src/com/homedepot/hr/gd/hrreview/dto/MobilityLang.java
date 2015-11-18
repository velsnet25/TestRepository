package com.homedepot.hr.gd.hrreview.dto;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;


public class MobilityLang {
	
	@XStreamAlias("depends")
	private String depends;
	
	@XStreamAlias("mobility")
	private Mobility mobility;
	
//	@XStreamAlias("language")
//	private Language language;
	
	@XStreamAlias("language")
	private List<LanguageList> languageList;

	public String getDepends() {
		return depends;
	}

	public void setDepends(String depends) {
		this.depends = depends;
	}

	public Mobility getMobility() {
		return mobility;
	}

	public void setMobility(Mobility mobility) {
		this.mobility = mobility;
	}

	public List<LanguageList> getLanguageList() {
		return languageList;
	}

	public void setLanguageList(List<LanguageList> languageList) {
		this.languageList = languageList;
	}

//	public Language getLanguage() {
//		return language;
//	}
//
//	public void setLanguage(Language language) {
//		this.language = language;
//	}
	
	
	
	
	

}

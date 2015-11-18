package com.homedepot.hr.gd.hrreview.dto;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class Language {
	
	
	private List<LanguageList> languageList;

	public List<LanguageList> getLanguageList() {
		return languageList;
	}

	public void setLanguageList(List<LanguageList> languageList) {
		this.languageList = languageList;
	}

}

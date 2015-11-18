package com.homedepot.hr.gd.hrreview.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("language-line")
public class LanguageList {
	
	private String language;
	private String speak;
	private String write;
	private String read;
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getSpeak() {
		return speak;
	}
	public void setSpeak(String speak) {
		this.speak = speak;
	}
	public String getWrite() {
		return write;
	}
	public void setWrite(String write) {
		this.write = write;
	}
	public String getRead() {
		return read;
	}
	public void setRead(String read) {
		this.read = read;
	}
	
	
}

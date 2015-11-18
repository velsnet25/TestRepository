package com.homedepot.hr.gd.hrreview.dto;

public class LanguageDetailsDTO {

	private String read;
	private String write;
	private String speak;
	private String languageName;
	private boolean preferenceLanguageFlag;
	private String zeroEmployeeId;
	public String getRead() {
		return read;
	}
	public void setRead(String read) {
		this.read = read;
	}
	public String getWrite() {
		return write;
	}
	public void setWrite(String write) {
		this.write = write;
	}
	public String getSpeak() {
		return speak;
	}
	public void setSpeak(String speak) {
		this.speak = speak;
	}
	public String getLanguageName() {
		return languageName;
	}
	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}
	public boolean isPreferenceLanguageFlag() {
		return preferenceLanguageFlag;
	}
	public void setPreferenceLanguageFlag(boolean preferenceLanguageFlag) {
		this.preferenceLanguageFlag = preferenceLanguageFlag;
	}
	public String getZeroEmployeeId() {
		return zeroEmployeeId;
	}
	public void setZeroEmployeeId(String zeroEmployeeId) {
		this.zeroEmployeeId = zeroEmployeeId;
	}

	
}

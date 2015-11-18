package com.homedepot.hr.gd.hrreview.dto;

import java.io.Serializable;


import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Response")
public class Response implements Serializable {
	private static final long serialVersionUID = 362498820763181265L;

	private String status;
	
	private String message;
	
	private int code;


	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
}


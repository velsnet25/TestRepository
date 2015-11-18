package com.homedepot.hr.gd.hrreview.util;

import com.google.gson.Gson;

public class JSONConverter<T> {

	private static Gson gson = new Gson();
	
	public static String toJson(Object obj){
		return gson.toJson(obj);
	}
	
	public JSONConverter()
	{
		
	}
//	public T createObject(String jsonString, Class<T> t){
//		return gson.fromJson(jsonString, t);
//	}
	
	public static <T> T createObject(String jsonString, Class<T> t){
	return gson.fromJson(jsonString, t);
	}
	
}

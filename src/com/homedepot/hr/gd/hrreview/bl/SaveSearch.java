package com.homedepot.hr.gd.hrreview.bl;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.homedepot.hr.gd.hrreview.request.FindRequest;
import com.homedepot.hr.gd.hrreview.util.JSONConverter;


public class SaveSearch {
	
	private List <AdvancedSearch> query;
	private String description;
	private String queryName;
	
	
	
	public List<AdvancedSearch> getQuery() {
		return query;
	}



	public void setQuery(List<AdvancedSearch> query) {
		this.query = query;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getQueryName() {
		return queryName;
	}



	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}



	@Override
	public String toString() {
		return "SaveSearch [query=" + query + ", description=" + description
				+ ", queryName=" + queryName + "]";
	}



//	public static void main (String args[]){
//		String request = "{\"query\":[{\"dataField\":\"AssociateID\",\"dataFieldCode\":\"1\",\"operator\":\"=\",\"dataValue\":\"1\",\"andOr\":\"AND\",\"inputList\":[],\"openParenVal\":\"\",\"closeParenVal\":\"\",\"containsLike\":false,\"beginsWithLike\":false,\"endsWithLike\":false}],\"description\":\"test\",\"queryName\":\"test\"}";
//		Gson gson = new Gson();
//		SaveSearch ss = gson.fromJson(request, new TypeToken<SaveSearch>() {}.getType());
//		 System.out.println(ss.getQueryName());
//		 System.out.println(ss.getDescription());
//		 System.out.println(ss.getQuery());
//			
//	}
	
}

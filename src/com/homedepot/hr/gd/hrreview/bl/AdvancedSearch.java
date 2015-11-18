package com.homedepot.hr.gd.hrreview.bl;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.homedepot.hr.gd.hrreview.util.JSONConverter;

public class AdvancedSearch {
	
	private String dataField;
	private int dataFieldCode;
	private String operator;
	private String dataValue;
	private String dataValueCode;
	private String 	andOr;
	private List<String> inputList;
	private String openParenVal = "";
	private String closeParenVal = "";
	private boolean containsLike = false;
	private boolean beginsWithLike = false;
	private boolean endsWithLike = false;
	
	public String getDataField() {
		return dataField;
	}
	public int getDataFieldCode() {
		return dataFieldCode;
	}
	public void setDataFieldCode(int dataFieldCode) {
		this.dataFieldCode = dataFieldCode;
	}
	public void setDataField(String dataField) {
		this.dataField = dataField;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getDataValue() {
		return dataValue;
	}
	public void setDataValue(String dataValue) {
		if (dataValue != null && !dataValue.trim().isEmpty())
			this.dataValue = dataValue.toUpperCase().trim();
	}
	public String getDataValueCode() {
		return dataValueCode;
	}
	public void setDataValueCode(String dataValueCode) {
		this.dataValueCode = dataValueCode;
	}
	public String getAndOr() {
		return andOr;
	}
	public void setAndOr(String andOr) {
		this.andOr = andOr;
	}
	public List<String> getInputList() {
		return inputList;
	}
	public void setInputList(List<String> inputList) {
		this.inputList = inputList;
	}
	public String getOpenParenVal() {
		return openParenVal;
	}
	public void setOpenParenVal(String openParenVal) {
		this.openParenVal = openParenVal;
	}
	public String getCloseParenVal() {
		return closeParenVal;
	}
	public void setCloseParenVal(String closeParenVal) {
		this.closeParenVal = closeParenVal;
	}
	public boolean hasOpenParenVal() {
		if(getOpenParenVal() != null && getOpenParenVal().equals("("))
			return true;
		else
			return false;
	}
	public boolean hasCloseParenVal() {
		if(getOpenParenVal() != null && getOpenParenVal().equals(")"))
			return true;
		else
			return false;
	}
	public boolean isContainsLike() {
		return containsLike;
	}
	public void setContainsLike(boolean containsLike) {
		this.containsLike = containsLike;
	}
	public boolean isBeginsWithLike() {
		return beginsWithLike;
	}
	public void setBeginsWithLike(boolean beginsWithLike) {
		this.beginsWithLike = beginsWithLike;
	}
	public boolean isEndsWithLike() {
		return endsWithLike;
	}
	public void setEndsWithLike(boolean endsWithLike) {
		this.endsWithLike = endsWithLike;
	}
	@Override
	public String toString() {
		return "AdvancedSearch [dataField=" + dataField + ", dataFieldCode="
				+ dataFieldCode + ", operator=" + operator + ", dataValue="
				+ dataValue + ", dataValueCode=" + dataValueCode + ", andOr="
				+ andOr + ", inputList=" + inputList + ", openParenVal="
				+ openParenVal + ", closeParenVal=" + closeParenVal
				+ ", containsLike=" + containsLike + ", beginsWithLike="
				+ beginsWithLike + ", endsWithLike=" + endsWithLike + "]";
	}	
	
//	public static void main (String args[]){
//		
//		String request = //"{	\"AdvancedSearch\":	    		"
//				 "{    			"
//				+ "dataField : \"Degree\",    			"
//				+ "dataFieldCode : 0,    			"
//				+ "operator : \">\",    			"
//				+ "dataValue : \"AS\",    			"
//				+ "dataValueCode : \" \",    			"
//				+ "andOr : \" \",    			"
//				+ "inputList : [],    			"
//				+ "openParenVal : \" \",    			"
//				+ "closeParenVal : \" \",    			"
//				+ "containsLike : \"false\",    			"
//				+ "beginsWithLike : \"false\",    			"
//				+ "endsWithLike : \"false\"    		"
//				//+ "}    	    "
//				+ "}";
//		
//		
//		AdvancedSearch a = new AdvancedSearch();
//		a.setDataField("Degree");
//		a.setOperator(">");
//		a.setDataValue("AS");
//		StringBuilder sb = new StringBuilder();
//		sb.append("[");
//		sb.append(request);
//		sb.append(",");
//		sb.append(request);
//		sb.append("]");
//		System.out.println(sb);
//		
//		Gson gson = new Gson();
//		
//		ArrayList<AdvancedSearch> searchList = gson.fromJson(sb.toString(), new TypeToken<ArrayList<AdvancedSearch>>() {}.getType());
//		 System.out.println(searchList.get(0).toString());
			
//	}
	
}

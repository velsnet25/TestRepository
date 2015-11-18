package com.homedepot.hr.gd.hrreview.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;


	//This inner class was generated for convenience.  You may move this code
	//to an external class if desired.
	@XStreamAlias("LoadSQLQueries")
	public class DeleteQueryDTO {
		private String associateId;
		private String queryID;
		
		public String getAssociateId() {
			return associateId;
		}

		public void setAssociateId(String associateId) {
			this.associateId = associateId;
		}

		public String getQueryID() {
			return queryID;
		}

		public void setQueryID(String queryID) {
			this.queryID = queryID;
		}		
}



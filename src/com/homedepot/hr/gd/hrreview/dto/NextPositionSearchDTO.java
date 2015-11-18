package com.homedepot.hr.gd.hrreview.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

	//This inner class was generated for convenience.  You may move this code
	//to an external class if desired.
	@XStreamAlias("NextPositionSearch")
	public class NextPositionSearchDTO {
		private String nextPositionId;
		private String nextPositionDescription;
		private String nextPositionLang;
		
		public String getNextPositionId() {
			return nextPositionId;
		}
		public void setNextPositionId(String nextPositionId) {
			this.nextPositionId = nextPositionId;
		}
		public String getNextPositionDescription() {
			return nextPositionDescription;
		}
		public void setNextPositionDescription(String nextPositionDescription) {
			this.nextPositionDescription = nextPositionDescription;
		}
		public String getNextPositionLang() {
			return nextPositionLang;
		}
		public void setNextPositionLang(String nextPositionLang) {
			this.nextPositionLang = nextPositionLang;
		}		
}
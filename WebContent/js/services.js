/**
* Common location for all services used in the app.
* @class SharedServices
* @memberOf app
* @namespace app.services
*/
angular.module("app.services", [])
    /**
     * Service methods to interact with the server
     */
    .factory("SharedServices", ["$http", "$q", "$filter", 'ErrorModal', 'errorLogService','$localstorage','slalom.browser.CookieManager',
        function ($http, $q, $filter, ErrorModal, ErrorLog, $localstorage,CookieManager) {
            /**
             * Checks the response package for success and message values. 
             * Displays a dialog box with the message text if supplied. 
             * Sets the dalog title to Info if the request was successful.
             * @param  {object} data service response payload
             */
            function checkResponseErrors(data) {
                if (data) {
                    var title = "Error";
                    if (data.status && data.status === "SUCCESS") {
                        title = "Info";
                    }
                    if (data.message) {
                        ErrorModal.modalTitle = title;
                        ErrorModal.show(data.message);
                    }
                }
            }

            /**
            * Common method to get simple service requests
            * @async
            * @param  {string} Target url to retrieve
            * @return {promise} deferred promise resolved when the data has been retrieved. 
            */
            function getSimple(target, options, suppressMessages) {
            	
            	var deferred = $q.defer();
                $http.get(target, options)
                    .success(function (data) {
                        if (!suppressMessages) {
                            checkResponseErrors(data);
                        }
                        deferred.resolve(data);
                    })
                    .error(function (data) {
                        if (!suppressMessages) {
                            ErrorModal.showSystemError();
                        }
                        ErrorLog(data);
                        deferred.reject(data);
                    });
                return deferred.promise;
            }
            
            
            /**
             * Common method to post service requests. 
             * Centralizes checking for response message handling and error notification.
             * @param  {string} target           Target URL
             * @param  {object} data             json body payload
             * @param  {boolean} suppressMessages optional flag to suppress message notifications. If suppressed, the caller will be responsible for displaying modal notifications as necessary.
             * @return {[type]}                  jquery deferred promis resolved when the data has been retrieved.
             */
            function postSimple(target, data, suppressMessages) {
                var deferred = $q.defer();
                $http.post(target, data)
                    .success(function (data) {
                        if (!suppressMessages) {
                            checkResponseErrors(data);
                        }
                        deferred.resolve(data);
                    })
                    .error(function (data) {
                        if (!suppressMessages) {
                            ErrorModal.showSystemError();
                        }
                        ErrorLog(data);
                        deferred.reject(data);
                    });
                return deferred.promise;
            }

            /**
            * @class Service methods
            * @public
            */
            return {
            	
            	/**
            	 * HR Review methods starts from here
            	 */
            	navBasicInfo : function () {
                    $location.path('/basicInfo/');
                },navWorkHistory : function () {
                    $location.path('/workHistory/');
                },navRating : function () {
                    $location.path('/rating/');
                },navNextPosition : function () {
                    $location.path('/nextPosition/');
                },navReport1 : function () {
                    $location.path('/report1/');
                },navReport2 : function () {
                    $location.path('/report2/');
                },navReport3 : function () {
                    $location.path('/report3/');
                },
                getCandidates: function (filter) {
                    var url = 'rs/SearchService/getFindDetails';
                    var body = {
                    		 	nameLast: filter.nameLast,
                            	associateId: filter.associateId,
                            	nameContain: filter.nameContain,
                            	firstNameStartsWith: filter.firstNameStartsWith,
                            	preferredNameStartsWith: filter.preferredNameStartsWith,
                            	jobTitle: filter.jobTitle,
                            	storeNo: filter.storeNo,
                            	lastfirstName: filter.lastfirstName
                    };                    
                    return postSimple(url, body);
                    //return getSimple(url);
                },
                getBasicInformation: function (associateID) {
                    //return getSimple("rs/StaffingService/getApplProfile?profileID=" + profileID);
                    return getSimple("rs/AssociateService/getBasicInformation?associateId="+associateID);
                },
                getAddressInformation: function (associateID) {
                    return getSimple("rs/AssociateService/getAddressDetails?associateId="+associateID);
                },
                getEducationInformation: function (associateID) {
                    return getSimple("rs/AssociateService/getSchoolAndCourseDetails?associateId="+associateID);
                },
                getLanguageInformation: function (associateID) {
                    return getSimple("rs/AssociateService/getLanguageDetails?associateId="+associateID);
                },
                getRatingInformation: function (associateID) {
                    return getSimple("rs/AssociateService/getRatingDetails?associateId="+associateID);
                },
                getNextPositionsInformation: function (associateID) {
                    return getSimple("rs/AssociateService/getNextPositionDetails?associateId="+associateID);
                },
                getWorkHistoryInformation: function (associateID) {
                    return getSimple("rs/AssociateService/getWorkHistoryDetails?associateId="+associateID);
                },
                getLoadSQLInformation: function () {
                    return getSimple("rs/SearchService/loadSQLQuery");
                },
                deleteSQLQuery: function (queryId) {
                    return getSimple("rs/SearchService/deleteSQLQuery?query="+queryId);
                },
                saveSQLQuery: function (query, description, queryName) {
                    var url = ("rs/SearchService/saveSQLQuery");
                    var body = {
                    		query: query,
                    		description: description,
                    		queryName: queryName,
                        	
                    };                    
                    return postSimple(url, body);
                },
                getExportToExcel: function(category, includeRating) {
                	
                	var url = "../HRReviewV2/rs/ReportingService/getExcelSheetReport";
                	var body = { 
                    		reportCategory:category ,
                    	    ratingsFlag:includeRating,
                    	    associateList : [] 
                    };
                	
                	var records = $localstorage.getObject('acount');  
                	for (var i = 0; i < records.length; i++) { 
                		var associateId = records[i].zeroEmployeeId;
                		body.associateList.push({ AssocId: associateId});
                	}
                	
                	return JSON.stringify(body);                	
                	
                },
                deleteSelectAssociateCookie: function (){
               	 	CookieManager.deleteCookie("associateId");
               	 	CookieManager.deleteCookie("firstName");
               	 	CookieManager.deleteCookie("lastName");
               	 	CookieManager.deleteCookie("jobTitle");
                },
                getAssociateProfileInformation: function (associateID) {
                    return getSimple("rs/ReportingService/getAssociateProfileReport?reportCategory=1&userId=pxd02&associateId="+associateID);
                },
                getExportToExcelInformation: function (associateID) {
                    return getSimple("rs/ReportingService/getExcelSheetReport?reportCategory=1&userId=pxd02&associateId="+associateID);
                },
                getElongatedFormInformation: function (associateID) {
                    return getSimple("rs/ReportingService/getPerformanceSummaryReport?reportCategory=1&userId=pxd02&associateId="+associateID);
                },                
                getAdvancedSearch:  function (advancedSearch) {
                	
                    var body = advancedSearch;
                    return postSimple("rs/SearchService/getAdvancedSearch", body);
                },
                //CSY2MFG
                getAdvancedQuickSearch:  function (filter) {
                    return getSimple("rs/SearchService/getAdvancedQuickSearch?selected="+filter.quickSearchSelect);
                   
                },
                //CSY2MFG
                getAdvancedSearchDataFields: function() {
                	 return getSimple('testData/advancedSearchDataFieldList.json');
                },
                //CSY2MFG
                getAdvancedSearchOperator: function() {
                	 return getSimple('testData/advancedSearchOperatorList.json');
                },

                //CSY2MFG
                getDivisionSearchDetails: function() {
                	return getSimple("rs/DataService/getDivisionSearchDetails");
                },
                //CSY2MFG
                getMajorsSearchDetails: function(){
                	return getSimple("rs/DataService/getMajorsSearchDetails");
                },
                getCandidateSearchDetails: function () {
                    return getSimple('testData/SearchDetails.json');
                },
                getAdvancedSearchDataSelectBlank: function () {
                    return getSimple('testData/advancedSearchDataBlank.json');
                },
                getAdvancedSearchDataFirstRow: function () {
                    return getSimple('testData/advancedSearchDataFirstRow.json');
                },
                getAdvancedSearchSexList: function () {
                    return getSimple('testData/advancedSearchSexList.json');
                },
                getAdvancedSearchYesNoList: function () {
                    return getSimple('testData/advancedSearchYesNoList.json');
                },
                //CSY2MFG
                getAdvancedSearchEnterDataValueList: function(){
                	return getSimple("testData/advancedSearchEnterDataValueList.json");
                },
                //CSY2MFG
                getAdvancedSearchQuickSearchList: function(){
                	return getSimple("testData/advancedSearchQuickSearchList.json");
                },
                //CSY2MFG
                getAdvancedSearchEnterDateList: function(){
                	return getSimple("testData/advancedSearchEnterDateList.json");
                },
                //CSY2MFG
                getPrevTitleDetails: function(){
                	return getSimple("rs/DataService/getPrevTitleDetails");
                },
                //CSY2MFG
                getDegreeSearchDetails: function(){
                	return getSimple("rs/DataService/getDegreeSearchDetails");
                },
                //CSY2MFG
                getDepartmentSearchDetails: function(){
                	return getSimple("rs/DataService/getDepartmentSearchDetails");
                }, 
                //CSY2MFG
                getDistrictSearchDetails: function(){
                	return getSimple("rs/DataService/getDistrictSearchDetails");
                },
                //CSY2MFG
                getDivisionSearchDetails: function(){
                	return getSimple("rs/DataService/getDivisionSearchDetails");
                },
                //CSY2MFG
                getIndividualCareerSearchDetails: function(){
                	return getSimple("rs/DataService/getIndividualCareerSearchDetails");
                },
                //CSY2MFG
                getIndvidualCareerReadinessSearchDetails: function(){
                	return getSimple("rs/DataService/getIndvidualCareerReadinessSearchDetails");
                },
                //CSY2MFG
                getJobTitleSearchDetails: function(){
                	return getSimple("rs/DataService/getJobTitleSearchDetails");
                },
                //CSY2MFG
                getLanguageSearchDetails: function(){
                	return getSimple("rs/DataService/getLanguageSearchDetails");
                },
                //CSY2MFG
                getPotentialSearchDetails: function(){
                	return getSimple("rs/DataService/getPotentialSearchDetails");
                },
                //CSY2MFG
                getPerformanceSearchDetails: function(){
                	return getSimple("rs/DataService/getPerformanceSearchDetails");
                },
                //CSY2MFG
                getCourseSearchDetails: function(){
                	return getSimple("rs/DataService/getCourseSearchDetails");
                },
                //CSY2MFG
                getNextPositionReadinessSearchDetails: function(){
                	return getSimple("rs/DataService/getNextPositionReadinessSearchDetails");
                },
                //CSY2MFG
                getNextPositionSearchDetails: function(){
                	return getSimple("rs/DataService/getNextPositionSearchDetails");
                },
                //CSY2MFG
                getRaceSearchDetails: function(){
                	return getSimple("rs/DataService/getRaceSearchDetails");
                },
                //CSY2MFG
                getRegionSearchDetails: function(){
                	return getSimple("rs/DataService/getRegionSearchDetails");
                },
                //CSY2MFG
                getSchoolSearchDetails: function(){
                	return getSimple("rs/DataService/getSchoolSearchDetails");
                },
                //CSY2MFG
                getStoreSearchDetails: function(){
                	return getSimple("rs/DataService/getStoreSearchDetails");
                },
                getLocationSearchDetails: function(){
                	return getSimple("rs/DataService/getLocationSearchDetails");
                },
            	getAddress: function(){
                	return getSimple("rs/AssociateService/getAddressDetails?"+associateID);
                },
                /*getWorkHistory: function () {
                    return getSimple('testData/workHistory.json');
                },
                getRating: function () {
                    return getSimple('testData/rating.json');
                },
                getNextPosition: function () {
                    return getSimple('testData/nextPosition.json');
                },
                getEducation: function(){
                	return getSimple('testData/EducationList.json');
                },
                getLanguages: function () {
                    return getSimple('testData/languages.json', { cache: true });
                },*/
                /**
                 * Common method for prev and next button in all the pages
                 * @param  {boolean} data   prev
                 * @param  {boolean} data   next
                 * @param  {boolean} data   pchg
                 * Updates the aId, prv, nxt cookie values
                 */
                getPageInfo : function(prev, next) {
                	var records = $localstorage.getObject('acount');  
                	var count = records.length;
                	var index = 0;
                	for (var i = 0; i < records.length; i++) { 
                	    if (CookieManager.getCookie('associateId') == records[i].zeroEmployeeId) {
                	    	index = i;
                	    	break;
                	    }
                	}
                	
                	if (prev) {
                		index--;
                		CookieManager.setCookie('associateId', records[index].zeroEmployeeId);
                    	CookieManager.setCookie('firstName', records[index].firstName);
                    	CookieManager.setCookie('lastName', records[index].lastName);
                    	CookieManager.setCookie('jobTitle', records[index].jobTitleDescription);
                    	
                    	if (count > 0 && count > index)
                    		CookieManager.setCookie('nxt', true);
                    	else
                    		CookieManager.setCookie('nxt', false);
                    	
                    	if (count > 0 && (index - 1) > -1)
                    		CookieManager.setCookie('prv', true);
                    	else
                    		CookieManager.setCookie('prv', false);
                    	
                    	
                	} else if (next) {
                		index++;
                		var associateId=records[index].zeroEmployeeId;
                		CookieManager.setCookie('associateId', records[index].zeroEmployeeId);
                    	CookieManager.setCookie('firstName', records[index].firstName);
                    	CookieManager.setCookie('lastName', records[index].lastName);
                    	CookieManager.setCookie('jobTitle', records[index].jobTitleDescription);
                    	
                    	if (count > 0 && index < (count - 1))
                    		CookieManager.setCookie('nxt', true);
                    	else
                    		CookieManager.setCookie('nxt', false);
                    	
                    	if (count > 0 && (index - 1) > -1)
                    		CookieManager.setCookie('prv', true);
                    	else
                    		CookieManager.setCookie('prv', false);
                	}
                	
                	
                	
                },
                
                /**
                 * Load user's security claims
                 * @return {object}     Promise when request completes
                 */
                getAccessInfo: function () {
                    return getSimple("rs/AssociateService/getAccessInfo");
                    //return getSimple("testData/AccessInfo.json");
                }
            };
        } ]);
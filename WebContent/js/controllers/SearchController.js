/*jslint browser: true*/
/*globals define, app, $, confirm*/
/**
* @memberOf app
* @class CandidateSearchController ViewModel for Candidate Search view.
* @constructor
*/
define("controllers/SearchController", ['app'], function (app) {
    app.controller('SearchController', ['$scope', '$timeout','$modal', '$location', '$filter', 'SharedServices', 'MessageService', 'CollectionBase', 'SpinnerService', 'slalom.browser.CookieManager', 'ErrorModal', 'ConfirmModal', 'ValidatorHelper', '$localstorage',
        function ($scope, $timeout, $modal, $location, $filter, services, messageService, CollectionBaseFactory, SpinnerService, CookieManager, ErrorModal, ConfirmModal, ValidatorHelper, $localstorage) {

    
//    	window.MY_SCOPE = $scope;//mrpolak
    	
    	$scope.getReport = function () {
    		if (!CookieManager.getCookie('associateId') || !CookieManager.getCookie('firstName') || !CookieManager.getCookie('lastName') || !CookieManager.getCookie('jobTitle'))
    			$location.path("/report2");	
        	 else
        		$location.path("/report");
        };
       
    	
    	document.getElementById("subnav1").style.display = 'none';
            // Locals
            var spinnerField = "candidateSearchSpinner";
            var searchSpinner = "advancedSearchSpinner";
            var defaultFilter = {
                    associateId: '',
                    nameFirst: '',
                    nameLast: '',
                    nameContain: '',
                    firstNameStartsWith: '',
                    preferredNameStartsWith: '',
                    jobTitle: '',
                    storeNo: '',
                    lastfirstName: ''
            };


            /**
             * For search validation at least one of the candidate Ref, Associate Id, or Last Name are required
             * @param  {object} item filter instance
             * @return {boolean}      true if valid input is supplied
             */
            var inputRequired = function (item) {
                return item.candidateRef || item.associateId || item.nameLast;
            };
            /**
             * Concatenates the id values to navigate to the app profile
             * @return {string} concatenated Id
             */
            var getProfileId = function () {
                var appl = $scope.model.records.currentItem;
                var appType = appl.assocAppl === "AP" ? "AP" : "AS";
                var param3 = appl.assocAppl === "AP" ? appl.dateApplied : appl.assocAppl;
                var profileId = $.trim(appl.applicantId) + "_" + // ApplicantID
                    appType + "_" + // ApplicantType
                    param3; // AppliedDate for applicants, store number for associates

                return profileId;
            };

            // Change the message banner for this page.
            messageService.clearMessage();
            messageService.addMessage("Candidate Search");

            // Bound values
            $scope.searchFilter = angular.copy(defaultFilter);
            $scope.notFound = "No records found. Please enter new search criteria and try again or contact MYTHDHR at 1-866-MYTHDHR (1-866-698-4347) for assistance.";

            /**
            * Flag indicating if we are in search or view results mode. Manages the accordian control that flips between the views.
            * @type {Boolean}
            */
            $scope.ShowResults = false;
            /**
             * Flag indicating whether we are in the "applied" view after records were selected
             */
            $scope.ShowApplied = false;
            /**
             * Global limit for drop-down list length in place until IE8 performance is resolved
             * Initially set to 5000.  
             */
            $scope.GlobalListLimit = 3000;

            /**
            * Search model object
            * @type {Object}
            */
            $scope.Reset = function () {
                $scope.searchFilter = angular.copy(defaultFilter);
                $scope.validator.instance = $scope.searchFilter;
                
            };

            /**
             * Validator helper for the search filter values
             * @type {ValidatorHelper}
             */
            $scope.validator = new ValidatorHelper($scope.searchFilter);
            
            $scope.jobs = new CollectionBaseFactory();
            /**
             * Validation rules for the search filter
             * @type {Array}
             */
            $scope.validator.rules = [
                { "fieldName": "associateId", "message": "Numbers Only", "validator": function (item) { return !item.associateId || item.associateId.match(/^[0-9]*$/); } },
                { "fieldName": "associateId", "message": "At least one value is required", "validator": inputRequired },
                { "fieldName": "associateId", "message": "Must be 9 digits", "validator": function (item) { return !item.associateId || item.associateId.match(/^[0-9]{9}$/); } },
                { "fieldName": "nameLast", "message": "At least one value is required", "validator": inputRequired },
                { "fieldName": "nameContain", "message": "At least one value is required", "validator": inputRequired },
                { "fieldName": "firstNameStartsWith", "message": "At least one value is required", "validator": inputRequired },
                { "fieldName": "preferredNameStartsWith", "message": "At least one value is required", "validator": inputRequired },
                { "fieldName": "jobTitle", "message": "At least one value is required", "validator": inputRequired },
                { "fieldName": "lastfirstName", "message": "At least one value is required", "validator": inputRequired },{ "fieldName": "store", "message": "Invalid Store", "validator": function (item) {
                    // Make sure the store number is valid. If so, display the matching store name
                    item.storeName = "";
                    if (!item.store) { return true; }
                    if (item.store.length !== 4) { return false; }
                    if (!$scope.stores) { return true; } // don't penalize them if stores are not supplied.
                    var index = 0;
                    for (index; index < $scope.stores.length; index++) {
                        if ($.trim($scope.stores[index].number) === item.store) {
                            item.storeName = $scope.stores[index].name;
                            return true;
                        }
                    }
                    return false;
                }}
            ];

            $scope.Reset(); // call it to initialize the filters

            /**
             * Free text filter
             * @type {String}
             */
            $scope.reqFilter = '';
           /**
            * Search results model object
            * @type {Object}
            */
            $scope.model = { records: new CollectionBaseFactory() };
            $scope.model.records.items = []; // Hack to force not found footer to appear while searching
           
            $scope.model.records.rowFilter = function (item) {
                return ($scope.reqFilter === "" || $scope.reqFilter === null || $filter('filter')([item], $scope.reqFilter, false).length > 0);
            };
          
            
            /**
             * Handler to toggle the rehire eligibility text message
             */
            
            //$scope.showToggleRehireEligibilityMessage=false;
            $scope.toggleRehireEligibilityMessage = function () {
            	if ($scope.model != null && $scope.model.records != undefined && $scope.model.records.items != undefined && $scope.model.records.items.length > 0) {
            		for(var i=0;i<$scope.model.records.items.length;i++){
                	    if($scope.model.records.items[i].rehire=='*'){
                	    	return true;
                	    }
                	}
            	}            	
        	    return false;
            };
            /**
             * Handler to toggle the detail line items. Lazy load the details asynchronously
             * @param  {object} item candidate line to fetch the details on
             
            $scope.toggleDetails = function (item) {
                item.showDetails = !item.showDetails;
                $scope.$apply(); // We need to force the detail to show in order to be able to close the spinner properly.

                if (!item.details) {
                    SpinnerService.show("candidateSearchSpinner-" + item.applicantId);
                    services.getCandidateSearchDetails(item)
                        .then(function (data) {
                            item.details = data.candidateSearchMoreInfoTO;
                            SpinnerService.hide("candidateSearchSpinner-" + item.applicantId);
                        }, function () {
                            SpinnerService.hide("candidateSearchSpinner-" + item.applicantId);
                        });
                } else {
                    SpinnerService.hide("candidateSearchSpinner-" + item.applicantId);
                }
            };
*/
            /**
             * populate advancedQuickSearchList
             */
            //CSY2MFG
            services.getAdvancedSearchQuickSearchList()
            	.then(function (data) {
            		$scope.model.quickSearchSelect = data.advancedSearchQuickSearchList;
            		 SpinnerService.hide(spinnerField);
            }, function () {
                	$scope.model.quickSearchSelect = [];
                	 SpinnerService.hide(spinnerField);
            });
            /**
             * 
             */
        
            $scope.model.advancedSearchOperatorListNames = [];

											        	
            $scope.model.advancedSearchOperatorLists = [];
            
            $scope.model.advancedSearchDataSelectDisplays = [];
                       
             $scope.model.advancedSearchDataAndOr = [];
            
       		$scope.model.advancedSearchDataValueName0 = ' ';
			$scope.model.advancedSearchDataValueName1 = '';
       		$scope.model.advancedSearchDataValueName2 = '';
       		$scope.model.advancedSearchDataValueName3 = '';
       		$scope.model.advancedSearchDataValueName4 = '';
       		$scope.model.advancedSearchDataValueName5 = '';
       		$scope.model.advancedSearchDataValueName6 = '';
       		$scope.model.advancedSearchDataValueName7 = '';
       		$scope.model.advancedSearchDataValueName8 = '';
       		$scope.model.advancedSearchDataValueName9 = '';
       		$scope.model.advancedSearchDataValueName10 = '';
       		$scope.model.advancedSearchDataValueName11 = '';
       		$scope.model.advancedSearchDataValueName12 = '';
       		$scope.model.advancedSearchDataValueName13 = '';
       		$scope.model.advancedSearchDataValueName14 = '';
       		$scope.model.advancedSearchDataValueName15 = '';
       		$scope.model.advancedSearchDataValueName16 = '';
       		$scope.model.advancedSearchDataValueName17 = '';
       		$scope.model.advancedSearchDataValueName18 = '';
       		$scope.model.advancedSearchDataValueName19 = '';
       		$scope.model.advancedSearchDataValueName20 = '';
            /**
             * populate advancedSearchSelect 
             */
            
            //CSY2MFG
            services.getAdvancedSearchDataFields()
	           	.then(function (data) {
	           		$scope.model.advancedSearchDataField = data.advancedSearchDataFields;
	           		
	           		$scope.model.advancedSearchDataField0 = data.advancedSearchDataFields;
	           		$scope.model.advancedSearchDataField2 = data.advancedSearchDataFields;
	           		$scope.model.advancedSearchDataField3 = data.advancedSearchDataFields;
	           		$scope.model.advancedSearchDataField4 = data.advancedSearchDataFields;
	           		$scope.model.advancedSearchDataField5 = data.advancedSearchDataFields;
	           		$scope.model.advancedSearchDataField6 = data.advancedSearchDataFields;
	           		$scope.model.advancedSearchDataField7 = data.advancedSearchDataFields;
	           		$scope.model.advancedSearchDataField8 = data.advancedSearchDataFields;
	           		$scope.model.advancedSearchDataField9 = data.advancedSearchDataFields;
	           		$scope.model.advancedSearchDataField10 = data.advancedSearchDataFields;
	           		$scope.model.advancedSearchDataField11 = data.advancedSearchDataFields;
	           		$scope.model.advancedSearchDataField12 = data.advancedSearchDataFields;
	           		$scope.model.advancedSearchDataField13 = data.advancedSearchDataFields;
	           		$scope.model.advancedSearchDataField14 = data.advancedSearchDataFields;
	           		$scope.model.advancedSearchDataField15 = data.advancedSearchDataFields;
	           		$scope.model.advancedSearchDataField16 = data.advancedSearchDataFields;
	           		$scope.model.advancedSearchDataField17 = data.advancedSearchDataFields;
	           		$scope.model.advancedSearchDataField18 = data.advancedSearchDataFields;
	           		$scope.model.advancedSearchDataField19 = data.advancedSearchDataFields;
	           		$scope.model.advancedSearchDataField20 = data.advancedSearchDataFields;
	           		SpinnerService.hide(spinnerField);
	           	}, function () {
	           		$scope.model.advancedSearchDataFields = [];
	           		$scope.model.advancedSearchDataField0 = [];
	           		$scope.model.advancedSearchDataField4 = [];
	           		$scope.model.advancedSearchDataField5 = [];
	           		$scope.model.advancedSearchDataField6 = [];
	           		$scope.model.advancedSearchDataField7 = [];
	           		$scope.model.advancedSearchDataField8 = [];
	           		$scope.model.advancedSearchDataField9 = [];
	           		$scope.model.advancedSearchDataField10 = [];
	           		$scope.model.advancedSearchDataField11 = [];
	           		$scope.model.advancedSearchDataField12 = [];
	           		$scope.model.advancedSearchDataField13 = [];
	           		$scope.model.advancedSearchDataField14 = [];
	           		$scope.model.advancedSearchDataField15 = [];
	           		$scope.model.advancedSearchDataField16 = [];
	           		$scope.model.advancedSearchDataField17 = [];
	           		$scope.model.advancedSearchDataField18 = [];
	           		$scope.model.advancedSearchDataField19 = [];
	           		$scope.model.advancedSearchDataField20 = [];
	           	 SpinnerService.hide(spinnerField);

                });
            
            /**
             * populate advanced search data select
             */ 

            $scope.model.advancedSearchOperatorLists1 = [{ "id" : "=", "label" : "=" }];
            $scope.model.advancedSearchOperatorLists2 = [{ "id" : "=", "label" : "=" },
                                                        { "id" : "IN", "label" : "In" }];
            $scope.model.advancedSearchOperatorLists3 = [{ "id" : "=", "label" : "=" },
                                                        { "id" : "IN", "label" : "In" },
                                                        { "id" : "LIKE", "label" : "Like" }];
            $scope.model.advancedSearchOperatorLists4 = [{ "id" : "=", "label" : "=" },
                                                        { "id" : "LIKE", "label" : "Like" }];
            $scope.model.advancedSearchOperatorLists5 = [{ "id" : "=", "label" : "=" },
                                                        { "id" : "<>", "label" : "<>" },
                                                        { "id" : "IN", "label" : "In" },
                                                        { "id" : "NOT IN", "label" : "Not In" },
                                                        { "id" : "LIKE", "label" : "Like" }];
            $scope.model.advancedSearchOperatorLists6 = [{ "id" : "=", "label" : "=" },
                                                        { "id" : ">", "label" : ">" },
                                             			{ "id" : "<", "label" : "<" },				 							
                                             			{ "id" : ">=", "label" : ">=" },				 							
                                             			{ "id" : "<=", "label" : "<=" },	
                                             			{ "id" : "BETWEEN", "label" : "Between" }];
            $scope.model.advancedSearchOperatorLists7 = [{ "id" : "=", "label" : "=" },
                                                        { "id" : ">", "label" : ">" },
                                             			{ "id" : "<", "label" : "<" },				 							
                                             			{ "id" : ">=", "label" : ">=" },				 							
                                             			{ "id" : "<=", "label" : "<=" },	
                                             			{ "id" : "BETWEEN", "label" : "Between" },
                                             			{ "id" : "NOT BETWEEN", "label" : "Not Between" }];
            $scope.model.advancedSearchOperatorLists8 = [{ "id" : "=", "label" : "=" },
                                                        { "id" : ">", "label" : ">" },
                                             			{ "id" : "<", "label" : "<" },				 							
                                             			{ "id" : ">=", "label" : ">=" },				 							
                                             			{ "id" : "<=", "label" : "<=" },
                                             			{ "id" : "LIKE", "label" : "Like" },
                                             			{ "id" : "BETWEEN", "label" : "Between" },
                                             			{ "id" : "NOT BETWEEN", "label" : "Not Between" }];             
            /**
             * populate operator list
             */
            //CSY2MFG
            services.getAdvancedSearchOperator()
           	.then(function (data) {
           		$scope.model.advancedSearchOperatorList = data.advancedSearchOperatorList;
           		$scope.model.advancedSearchOperatorLists[0] = data.advancedSearchOperatorList;
           		$scope.model.advancedSearchOperatorLists[1] = $scope.model.advancedSearchOperatorLists1;
           		$scope.model.advancedSearchOperatorLists[2] = $scope.model.advancedSearchOperatorLists2;
           		$scope.model.advancedSearchOperatorLists[3] = $scope.model.advancedSearchOperatorLists3;
           		$scope.model.advancedSearchOperatorLists[4] = $scope.model.advancedSearchOperatorLists4;
           		$scope.model.advancedSearchOperatorLists[5] = $scope.model.advancedSearchOperatorLists5;
           		$scope.model.advancedSearchOperatorLists[6] = $scope.model.advancedSearchOperatorLists6;
           		$scope.model.advancedSearchOperatorLists[7] = $scope.model.advancedSearchOperatorLists7;
           		$scope.model.advancedSearchOperatorLists[8] = $scope.model.advancedSearchOperatorLists8;

           	 SpinnerService.hide(searchSpinner);
           	}, function () {
                $scope.model.advancedSearchOperatorList = [];
                $scope.model.advancedSearchOperatorLists = [];
                
                $scope.model.advancedSearchOperatorList0 = [];
           		$scope.model.advancedSearchOperatorList1 = [];
           		$scope.model.advancedSearchOperatorList2 = [];
           		$scope.model.advancedSearchOperatorList3 = [];
           		$scope.model.advancedSearchOperatorList4 = [];
           		$scope.model.advancedSearchOperatorList5 = [];
           		$scope.model.advancedSearchOperatorList6 = [];
           		$scope.model.advancedSearchOperatorList7 = [];
           		$scope.model.advancedSearchOperatorList8 = [];
           		$scope.model.advancedSearchOperatorList9 = [];
           		$scope.model.advancedSearchOperatorList10 = [];
           		$scope.model.advancedSearchOperatorList11 = [];
           		$scope.model.advancedSearchOperatorList12 = [];
           		$scope.model.advancedSearchOperatorList13 = [];
           		$scope.model.advancedSearchOperatorList14 = [];
           		$scope.model.advancedSearchOperatorList15 = [];
           		$scope.model.advancedSearchOperatorList16 = [];
           		$scope.model.advancedSearchOperatorList17 = [];
           		$scope.model.advancedSearchOperatorList18 = [];
           		$scope.model.advancedSearchOperatorList19 = [];
           		$scope.model.advancedSearchOperatorList20 = [];
                SpinnerService.hide(searchSpinner);
            });

            /**
             * Array to hold advanced search queries
             */
            	$scope.model.queries = [];
               
            	/**
            	 * pre-populate query.
            	 * This represents the data model on advanced search screen
            	 */
                $scope.pushNewQueryRow =function(newIndex){
               	 	var query = {
               			 "id": newIndex, 
               			 "show": "", 
               			 "selected": false, 
               			 "dataFieldName" : "",
               			 "operatorListType": 0,
               			 "operator" : "=",
               			 "multipleSelect" : false,
                		 "dataValue" : "",
               			 "inputList": [],
               			 "dataSelect" : "",
               			 "openParenVal" : "",
               			 "closeParenVal" : "",
               			 "andOr" : "AND"
           			 };
               	 	$scope.model.queries.push(query);

                };
               $scope.pushNewQueryRow(0);


            	
            /**
             * Do apply logic here.
             */
            $scope.ApplyAll = function (){
              var i = 0;
              var selectedAssociates = [];
              for (i; i < $scope.model.records.items.length; i++) {
              		$scope.model.records.items[i].selected = true;
              		selectedAssociates.push({  
       		         "zeroEmployeeId":$scope.model.records.items[i].zeroEmployeeId,
       		         "firstName":$scope.model.records.items[i].firstName,
       		         "lastName":$scope.model.records.items[i].lastName,
       		         "humanResourcesSystemStoreNumber":$scope.model.records.items[i].humanResourcesSystemStoreNumber,
       		         "jobTitleDescription":$scope.model.records.items[i].jobTitleDescription
       		      });
              }
              
              	$scope.model.records.items = $filter('filter')($scope.model.records.items, {selected: true});
              	$scope.ShowApplied = true;
         	 	$scope.model.count = $scope.model.records.items.length;
         	 	// ToDo - store only 6 columns
         	 	
         	 	if (selectedAssociates != null && selectedAssociates.length > 0)
         	 		$localstorage.setObject('acount', selectedAssociates);
         	 	services.deleteSelectAssociateCookie();
         	 	$location.path("/select");
            };

            $scope.ApplySelected = function (){
            	var somethingSelected = false;
                var i = 0;
                var selectedAssociates = [];
                for (i; i < $scope.model.records.items.length; i++) {
                		if($scope.model.records.items[i].selected == true){
                			somethingSelected = true;
                			selectedAssociates.push({  
                		         "zeroEmployeeId":$scope.model.records.items[i].zeroEmployeeId,
                		         "firstName":$scope.model.records.items[i].firstName,
                		         "lastName":$scope.model.records.items[i].lastName,
                		         "humanResourcesSystemStoreNumber":$scope.model.records.items[i].humanResourcesSystemStoreNumber,
                		         "jobTitleDescription":$scope.model.records.items[i].jobTitleDescription
                		      });
                		};
                }
                if(!somethingSelected){
                	 ErrorModal.show("Select a record or records to apply.");
                	 return false;
                }
                $scope.model.records.items = $filter('filter')($scope.model.records.items, {selected: true});
                $scope.ShowApplied = true;
           	 	$scope.model.count = $scope.model.records.items.length;
           	 	// ToDo - store only 5 columns
           	 	if (selectedAssociates != null && selectedAssociates.length > 0)
           	 		$localstorage.setObject('acount', selectedAssociates);
           	 	services.deleteSelectAssociateCookie();
           	 	$location.path("/select");

            };           
            

            /** 
            * Handler to call the getCandidates service and display the results when completed
            * @async
            */
            $scope.DoFind = function () {
                if ($scope.ShowResults) {
                    // Toggle show/hide search panel
                    $scope.ShowResults = false;
                    document.getElementById("subnav1").style.display = 'none';
                    // clear previous search results
                    $scope.model.records.items = [];
                } else {
                    $scope.ShowResults = true;
                    SpinnerService.show(spinnerField);
                    document.getElementById("subnav1").style.display = 'none';
                    services.getSearchDetails($scope.searchFilter)
                        .then(function (data) {
                            $scope.model.records.items = data.searchList;
                            //$scope.showToggleRehireEligibilityMessage=$scope.toggleRehireEligibilityMessage();
                            SpinnerService.hide(spinnerField);
                        }, function () {
                            SpinnerService.hide(spinnerField);
                            $scope.ShowResults = false;
                        });
                }
            };

            /**
            * Handler for the show applicant button. Redirects to the applicant info screen for the selected candidate
            */
            $scope.ShowApplicant = function () {
                $location.path("/applicant/" + getProfileId());
            };
            /**
            * Handler for the print applicant button. Redirects to the applicant print screen for the selected candidate
            */
            $scope.PrintApplicant = function () {
                var url = "../StaffingForms/service/PrintApplicationService/getAppPdfView?"
                    + "phnScrn=" + $.trim($scope.model.records.currentItem.applicantId) // Applicant ID
                    + "&intExt=" + ($scope.model.records.currentItem.assocAppl === "AP" ? "E" : "I") // Internal or external
                    + "&version=2";
                window.open(url, 'printView');
            };

            /**
             * Handler to populate Data Value based on Data Select drop-down selection
             * CSY2MFG
             */
            $scope.updateDataValue = function (index){
            		
            		var x = 0;
            		for (x; x < $scope.model.advancedSearchDataSelectDisplays[index].length; x++) {
            			
            		     if ($scope.model.queries[index].dataSelect == $scope.model.advancedSearchDataSelectDisplays[index][x].dataValue){
            		    	 $scope.model.queries[index].dataValueCode = $scope.model.advancedSearchDataSelectDisplays[index][x].id;//sometimes id is numeric??? 
            		    	 $scope.model.queries[index].dataValue =  $.trim($scope.model.advancedSearchDataSelectDisplays[index][x].dataValue);
            		     };
            		};
            	
            };
           
            
            $scope.updateDataValueMultiple = function (index){
            	
        		var dataValue = ""; //display
        		var inputList = []; //data array for SQL
        		var i = 0;
        		for (i; i < $scope.model.queries[index].dataSelect.length; i++) {
        			//THis is the id of the selected choice
        			var id = $scope.model.queries[index].dataSelect[i];
        			var x = 0;
        			for (x; x < $scope.model.advancedSearchDataSelectDisplays[index].length; x++) {
        				//find dataValue for the corresponding id
	           		     if (id == $scope.model.advancedSearchDataSelectDisplays[index][x].dataValue){
		           		    	if(dataValue == ""){
		        					dataValue = $.trim($scope.model.advancedSearchDataSelectDisplays[index][x].dataValue);
		        				}else{
		        					dataValue = dataValue + ", " + $.trim($scope.model.advancedSearchDataSelectDisplays[index][x].dataValue);
		        				}
		           		    	inputList.push($.trim($scope.model.advancedSearchDataSelectDisplays[index][x].dataValue));
		           		  };
	  
           		     };
       			};
        		$scope.model.queries[index].dataValue = dataValue;
        		$scope.model.queries[index].inputList = inputList;
            };            
            
            
            $scope.checkMultipleSelect = function(index){
            	
            	$scope.model.queries[index].multipleSelect = false;
            	
         	   if($scope.model.queries[index].operator == 'IN' || $scope.model.queries[index].operator == 'NOT IN'){
         		  $scope.model.queries[index].multipleSelect = true;
        	   }
            	
            	
            };
            $scope.getAdvancedSearch = function (apply) {
     
        		
         /**
            - Select must be selected
       	 	- Data value must be filled
        	- IN and NOT IN must have multiples with commas
        	- BETWEEN must have a comma
        	- LIKE and NOT LIKE must have percentage sign
        	- Parens must be paired
          */
            	var openParenCount = 0;
            	var closeParenCount = 0;
            	            	
            	var error = false;
            	var errorMessage = "";
            	var i=0;
        		for (i; i < $scope.model.queries.length; i++) {
        			var dataFieldName = $scope.model.queries[i].dataFieldName;
        			var dataValue = $scope.model.queries[i].dataValue;
        			var operator = $scope.model.queries[i].operator;
        			var openParenVal = $scope.model.queries[i].openParenVal;
        			var closeParenVal = $scope.model.queries[i].closeParenVal;
        			
        			var line = i+1;
        			 if ((!error) && ((!dataFieldName) || ($.trim(dataFieldName)) === "")){
        				 errorMessage = errorMessage +"Make a selection from Data Field list on line "+line+". ";
        				error = true;        
        			 }; 
        			 if ((!error) && ((!dataValue) || ($.trim(dataValue)) === "")){
        				errorMessage = errorMessage +"Enter a value into Data Value field on line "+line+". ";
						error = true;
        			 };	 
        				 
        			 if((!error) && ((operator) && operator === "IN" || operator === "NOT IN")){
        				 if(dataValue.indexOf(",") < 0){
        					 errorMessage = errorMessage +"For this Operator enter multiple Data Values separated by a comma on line "+line+". ";
							error = true;
        				 };
        			 };	 
        				 
        			 if((!error) && ((operator) && operator === "LIKE" || operator === "NOT LIKE")){
        				if(dataValue.indexOf("%") < 0){
        					errorMessage = errorMessage +"For this Operator Data Value must contain % on line"+line+". ";
        					error = true;			
        				 };
        			 };
        			 if((!error) && ((operator) && operator === "BETWEEN" || operator === "NOT BETWEEN")){
        				 if(dataValue.indexOf(",") < 0){
        					 errorMessage = errorMessage +"For this Operator Data Value must be separated by a comma on line "+line+". ";
							error = true;
        				 };
        			 };	 
        			 
        			 if((openParenVal) && openParenVal === "("){
        				 openParenCount ++;
        			 };  
        			 if((closeParenVal) && closeParenVal === ")"){
        				 closeParenCount ++;
        			 };
        		
        		};
        		
        		//Left this logic in place in case we can display more than one error at a time
        		if((!error) && openParenCount != closeParenCount){
        			errorMessage = errorMessage +"Enter paired Parentheses. ";
        			error = true;
        		};
        		
        		if(error){
        			ErrorModal.show(errorMessage);
        		}else{
        			$scope.forToLetAdvancedSearch(apply);
        		};
            };
            /**
             * Method to fetch advanced search query parameters and send request
             * if parameter "apply" is true, results will be "applied"
             * CSY2MFG
             */
            $scope.forToLetAdvancedSearch = function (apply) {
            	$scope.model.records.items = [];
            	var advancedSearchQuery = [];
                       if ($scope.ShowResults) {
                           // Toggle show/hide search panel
                           $scope.ShowResults = false;
                           document.getElementById("subnav1").style.display = 'none';
                           // clear previous search results
                           $scope.model.records.items = [];
                       } else {
                           $scope.ShowResults = true;
                           SpinnerService.show(spinnerField);
                           document.getElementById("subnav1").style.display = 'none';
                           //Spin through arrays to get data
                           advancedSearchQuery = $scope.getAdvancedSearchObject($scope.model.queries);	   
                           };
                           services.getAdvancedSearch(advancedSearchQuery)
                               .then(function (data) {
                              	 $scope.model.count = data.count;
                           	       services.deleteSelectAssociateCookie();
                                   $scope.model.records.items = data.advancedQuickSearchDTO;
                                   SpinnerService.hide(spinnerField);
                                   if(apply == true){
                                	   $scope.ApplyAll();
                                   }
                               }, function () {
                                   SpinnerService.hide(spinnerField);
                                   $scope.ShowResults = false;
                               });
                       
                   
               };  
            
               $scope.getAdvancedSearchObject = function(queries){
                   var advancedSearchQuery = [];
                   var i = 0;
                   for (i; i < queries.length; i++) {
                	
//                	   var inputListT = [];
                	   
//                     private String dataField; "id" from search drop down
//                     private int dataFieldCode;
//                     private String operator; // =, >, <, >=, <=, Like, Between, Not Between
//                     private String dataValue;
//                     private String dataValueCode;
//                     private String     andOr; //AND OR for successive lines
//                     private List<String> inputList;
//                     private String openParenVal;
//                     private String closeParenVal;
//                     private boolean containsLike = false;
//                     private boolean beginsWithLike = false;
//                     private boolean endsWithLike = false;

                	   advancedSearch = {
                          		dataField : queries[i].dataFieldName, //Alpha value of the Data Field drop down
                          		dataFieldCode : queries[i].dataFieldCode,  //Numeric Code of the Data Field drop down
                          		operator : queries[i].operator, 
                          		dataValue : $.trim(queries[i].dataValue),
                	   			andOr: queries[i].andOr,
                	   			dataValueCode : queries[i].dataValueCode,//Numeric Code of the Data Value Selection
                	   			inputList : queries[i].inputList,
                	   			openParenVal : queries[i].openParenVal,
                	   			closeParenVal : queries[i].closeParenVal,
                	   			containsLike : false,
                	   			beginsWithLike : false,
                	   			endsWithLike : false
                	   };
                	   advancedSearchQuery.push(advancedSearch);
                   };
                   return advancedSearchQuery;
               };
            
            
            /**
             * Handler to fetch the quick search results for quick search criteria
             */

             $scope.getAdvancedQuickSearch = function () {
            
             	if (!$scope.searchFilter.quickSearchSelect || ($scope.searchFilter.quickSearchSelect === "" )) {
                     ErrorModal.show("Quick Search Selection Is Required");
                     return false;
                 } else {
                     if ($scope.ShowResults) {
                         // Toggle show/hide search panel
                         $scope.ShowResults = false;
                         document.getElementById("subnav1").style.display = 'none';
                         // clear previous search results
                         $scope.model.records.items = [];
                     } else {
                         $scope.ShowResults = true;
                         $scope.model.records.items = [];
                         SpinnerService.show(spinnerField);
                         document.getElementById("subnav1").style.display = 'none';
                         services.getAdvancedQuickSearch($scope.searchFilter)
                             .then(function (data) {
                            	 $scope.model.count = data.count;
                            	// $localstorage.setObject('acount', data.advancedQuickSearchDTO);
                            	 services.deleteSelectAssociateCookie();
                                 $scope.model.records.items = data.advancedQuickSearchDTO;
                                 SpinnerService.hide(spinnerField);
                             }, function () {
                                 SpinnerService.hide(spinnerField);
                                 $scope.ShowResults = false;
                             });
                     }
                 }
             };


              $scope.getAdvancedQuickSearchAndApply = function () {
            	  $scope.getAdvancedQuickSearch();
            	  $scope.ApplyAll();
              };
             
   
             $scope.insertRow = function (index) {
            	 var newIndex = $scope.model.queries.length;
            	 $scope.pushNewQueryRow(newIndex);
             };
             $scope.deleteRow = function () {
            	 var deleteQuery = confirm("You are about to delete a query row.  Are you sure?");
            	
            	 if(deleteQuery){
            		
            	 
	            	 var queriesT = [];
	            	 var advancedSearchDataSelectDisplaysT = [];
	            	 var i=0;
	            	 for (i; i < $scope.model.queries.length; i++) {
	            		
	            		 if($scope.model.queries[i].selected == false){
	            			 //copy items to keep
	            			 queriesT.push($scope.model.queries[i]);
	            			 advancedSearchDataSelectDisplaysT.push($scope.model.advancedSearchDataSelectDisplays[i]);
	            		 }
	            	 }
	            	 
	            		$scope.model.queries = queriesT;
	            		$scope.model.advancedSearchDataSelectDisplays = advancedSearchDataSelectDisplaysT;
	            		if($scope.model.queries.length < 3){
	            			for (i; i < $scope.model.queries.length; i++) {
	   	            		 $scope.model.queries[i].openParenVal = "";
	   	            		 $scope.model.queries[i].closeParenVal = "";
	   	            	 	};	
	            		};
            	};
             };     
             
             $scope.newSearch = function (){
            	 		var  i = 0;
            	 		for (i; i < $scope.model.queries.length; i++) {
			            	 $scope.model.advancedSearchDataSelectDisplays[i] = [];
		            	 };
            	 		 $scope.model.queries = [];
             	 		 $scope.pushNewQueryRow();
             };
             $scope.clearValues = function(){
            	//Just clear the data values
            	 var  i = 0;
     	 		for (i; i < $scope.model.queries.length; i++) {
     	 			$scope.model.queries[i].dataValue =  "";
            	 };
                     
             };
 

             /**
              * Handler to fetch items for Data Select drop down
              */
             
             $scope.getAdvancedSearchSelectData = function (index, reset) {
            	 SpinnerService.show(searchSpinner);
            	 var dataFieldName = $scope.model.queries[index].dataFieldName;
            	 //Initialize with an empty array
            	 $scope.model.advancedSearchDataSelectDisplays[index] = [];
            	 //variable to determine list of operators to be shown based on search list selection - see advancedSearchOperatorLists and their indexing
            	 var operatorListType = 0;
            	 //Show "loading" in dropdown while list data is fetched
            	 $scope.model.queries[index].show = "loading";
            	//variable to determine whether we show a data select dropdown after data fetch - presume we show dropdown
            	 var show = "dropdown";
            	
            	 
                //BEWARE very large IF statements below
            	if (dataFieldName === "Major" ) {
                    	 operatorListType = 3;
                         services.getMajorsSearchDetails()
                             .then(function (data) {
                                 var majorsSearchData = data.majorsSearchDTO;
                                 
                                 var i = 0;
                                 for (i; i < majorsSearchData.length& i < $scope.GlobalListLimit; i++) {
                                 	var advancedSearchData = {
                                		"id" : majorsSearchData[i].collegeMajorCode,
                                		"label": $.trim(majorsSearchData[i].collegeMajorDescription),
                                		"dataValue" : $.trim(majorsSearchData[i].collegeMajorDescription)
                                	};
                                 	$scope.model.advancedSearchDataSelectDisplays[index][i] = advancedSearchData;
                                 }
                                 $scope.model.queries[index].show = show;
                                 
                             }, function () {
                   
                                 $scope.model.advancedSearchDataSelectDisplays[index] = [];
                             });
                     }else if (dataFieldName === "CoursesCertification" ) {
                    	 operatorListType = 4;
                         services.getCourseSearchDetails()
                             .then(function (data) {
                                 var courseSearchData = data.courseSearchDTO;
                                 
                                 var i = 0;
                                 for (i; i < courseSearchData.length& i < $scope.GlobalListLimit; i++) {
                                 	var advancedSearchData = {
                                		"id" : courseSearchData[i].ind,
                                		"label": $.trim(courseSearchData[i].spCrsDesc),
                                		"dataValue" :$.trim(courseSearchData[i].spCrsDesc)
                                	};
                                 	
                                 	$scope.model.advancedSearchDataSelectDisplays[index][i] = advancedSearchData;
                                 }
                                 $scope.model.queries[index].show = show;
                                 
                             }, function () {
                   
                                 $scope.model.advancedSearchDataSelectDisplays[index] = [];
                             });
                     }else if (dataFieldName === "Degree" ) {
                    	 operatorListType = 5;
                    	 
                         services.getDegreeSearchDetails()
                             .then(function (data) {
                                 var degreeSearchData = data.degreeSearchDTO;
                                 
                                 var i = 0;
                                 for (i; i < degreeSearchData.length; i++) {
                                 	var advancedSearchData = {
                                		"id" : degreeSearchData[i].degreeTypeCode,
                                		"label": $.trim(degreeSearchData[i].degreeTypeDescription),
                                		"dataValue" : $.trim(degreeSearchData[i].degreeTypeDescription)
                                	};

                                 	$scope.model.advancedSearchDataSelectDisplays[index][i] = advancedSearchData;
                                 }
                                 $scope.model.queries[index].show = show;
                                 
                             }, function () {
                   
                                 $scope.model.advancedSearchDataSelectDisplays[index] = [];
                             });
                     }else if (dataFieldName === "Department" ) {
                    	 operatorListType = 3;
                         services.getDepartmentSearchDetails()
                             .then(function (data) {
                                 var getDepartmentSearchDetailsData = data.departmentSearchDTO;
                                 
                                 var i = 0;
                                 for (i; i < getDepartmentSearchDetailsData.length; i++) {
                                 	var advancedSearchData = {
                                		"id" : getDepartmentSearchDetailsData[i].humanResourcesSystemDepartmentNumber,
                                		"label": $.trim(getDepartmentSearchDetailsData[i].humanResourcesSystemDepartmentName),
                                		"dataValue" : $.trim(getDepartmentSearchDetailsData[i].humanResourcesSystemDepartmentNumber)
                                	};
                                 	$scope.model.advancedSearchDataSelectDisplays[index][i] = advancedSearchData;
                                 }
                                 $scope.model.queries[index].show = show;
                                 
                             }, function () {
                   
                                 $scope.model.advancedSearchDataSelectDisplays[index] = [];
                             });
                     }else if (dataFieldName === "District" ) {
                    	 operatorListType = 3;
                         services.getDistrictSearchDetails()
                             .then(function (data) {
                                 var getDistrictSearchDetailsData = data.districtSearchDTO;
                                 
                                 var i = 0;
                                 for (i; i < getDistrictSearchDetailsData.length; i++) {
                                 	var advancedSearchData = {
                                		"id" : getDistrictSearchDetailsData[i].humanResourcesSystemRegionCode,
                                		"label": $.trim(getDistrictSearchDetailsData[i].humanResourcesSystemRegionCode),
                                		"dataValue" : $.trim(getDistrictSearchDetailsData[i].humanResourcesSystemRegionCode)
                                	};
                                 	$scope.model.advancedSearchDataSelectDisplays[index][i] = advancedSearchData;
                                 }
                                 $scope.model.queries[index].show = show;
                                 
                             }, function () {
                                
                                 $scope.model.advancedSearchDataSelectDisplays[index] = [];
                             });
                     }else if (dataFieldName === "Division" ) {
                    	 operatorListType = 2;
                         services.getDivisionSearchDetails()
                             .then(function (data) {
                                 var getDivisionSearchDetailsData = data.divisionSearchDTO;
                                 
                                 var i = 0;
                                 for (i; i < getDivisionSearchDetailsData.length; i++) {
                                 	var advancedSearchData = {
                                		"id" : getDivisionSearchDetailsData[i].humanResourcesSystemDivisionCode,
                                		"label": $.trim(getDivisionSearchDetailsData[i].humanResourcesSystemDivisionName),
                                		"dataValue" : $.trim(getDivisionSearchDetailsData[i].humanResourcesSystemDivisionCode)
                                	};
                                 	$scope.model.advancedSearchDataSelectDisplays[index][i] = advancedSearchData;
                                 }
                                 
                                 $scope.model.queries[index].show = show;
                                 
                             }, function () {
                            	 
                                 $scope.model.advancedSearchDataSelectDisplays[index] = [];
                             });
                     }else if (dataFieldName === "IndividualCareerInterest" ) {
                    	 operatorListType = 3;
                         services.getIndividualCareerSearchDetails()
                             .then(function (data) {
                                 var getIndividualCareerSearchDetailsData = data.jobTitleSearchDTO;
                                 
                                 var i = 0;
                                 for (i; i < getIndividualCareerSearchDetailsData.length& i < $scope.GlobalListLimit; i++) {
                                 	var advancedSearchData = {
                                		"id" : getIndividualCareerSearchDetailsData[i].jobTitleCode,
                                		"label": $.trim(getIndividualCareerSearchDetailsData[i].jobTitleDescription),
                                		"dataValue" : $.trim(getIndividualCareerSearchDetailsData[i].jobTitleDescription)
                                	};
                                 	$scope.model.advancedSearchDataSelectDisplays[index][i] = advancedSearchData;
                                 }
                                 $scope.model.queries[index].show = show;
                                 
                             }, function () {
                         
                                 $scope.model.advancedSearchDataSelectDisplays[index] = [];
                             });
                     }else if (dataFieldName === "IndividualCareerInterestReadiness" ) {
                    	 operatorListType = 2;
                         services.getIndvidualCareerReadinessSearchDetails()
                             .then(function (data) {
                                 var getIndvidualCareerReadinessSearchDetails = data.readinessSearchDTO;
                                 
                                 var i = 0;
                                 for (i; i < getIndvidualCareerReadinessSearchDetails.length; i++) {
                                 	var advancedSearchData = {
                                		"id" : getIndvidualCareerReadinessSearchDetails[i].successionPlanGoalStatusCode,
                                		"label": $.trim(getIndvidualCareerReadinessSearchDetails[i].successionPlanGoalStatusDescription),
                                		"dataValue" : $.trim(getIndvidualCareerReadinessSearchDetails[i].successionPlanGoalStatusDescription)
                                	};
                                 	$scope.model.advancedSearchDataSelectDisplays[index][i] = advancedSearchData;
                                 }
                                 $scope.model.queries[index].show = show;
                                 
                             }, function () {
                         
                                 $scope.model.advancedSearchDataSelectDisplays[index] = [];
                             });
                     }else if (dataFieldName === "JobTitle" ) {
                    	 operatorListType = 3;
                         services.getJobTitleSearchDetails()
                             .then(function (data) {
                                 var getJobTitleSearchDetailsData = data.jobTitleSearchDTO;
                                 
                                 var i = 0;
                                 for (i; i < getJobTitleSearchDetailsData.length& i < $scope.GlobalListLimit; i++) {
                                 	var advancedSearchData = {
                                		"id" : $.trim(getJobTitleSearchDetailsData[i].jobTitleCode),
                                		"label": $.trim(getJobTitleSearchDetailsData[i].jobTitleDescription),
                                		"dataValue" : $.trim(getJobTitleSearchDetailsData[i].jobTitleCode)
                                	};
                                 	$scope.model.advancedSearchDataSelectDisplays[index][i] = advancedSearchData;
                                 }
                                 $scope.model.queries[index].show = show;
                                 
                             }, function () {
                                 
                                 $scope.model.advancedSearchDataSelectDisplays[index] = [];
                             });
                     }else if (dataFieldName === "KnownLanguage" ) {
                    	 operatorListType = 3;
                         services.getLanguageSearchDetails()
                             .then(function (data) {
                                 var getLanguageSearchDetailsData = data.languageSearchDTO;
                                 
                                 var i = 0;
                                 for (i; i < getLanguageSearchDetailsData.length; i++) {
                                 	var advancedSearchData = {
                                		"id" : getLanguageSearchDetailsData[i].internationalOrganizationForStandardsLanguageCode,
                                		"label": $.trim(getLanguageSearchDetailsData[i].internationalOrganizationForStandardsLanguageName),
                                		"dataValue" : $.trim(getLanguageSearchDetailsData[i].internationalOrganizationForStandardsLanguageName)
                                	};
                                 	$scope.model.advancedSearchDataSelectDisplays[index][i] = advancedSearchData;
                                 }
                                 $scope.model.queries[index].show = show;
                                 
                             }, function () {
                         
                                 $scope.model.advancedSearchDataSelectDisplays[index] = [];
                             });
                     }else  if (dataFieldName === "Location" ) {
                    	 operatorListType = 3;
                         services.getLocationSearchDetails()
                             .then(function (data) {
                                 var getLocationSearchDetailsData = data.locationSearchDTO;
                                 
                                 var i = 0;
                                 for (i; i < getLocationSearchDetailsData.length; i++) {
                                 	var advancedSearchData = {
                                		"id" : getLocationSearchDetailsData[i].humanResourcesSystemStoreNumber,
                                		"label": $.trim(getLocationSearchDetailsData[i].humanResourcesSystemStoreName),
                                		"dataValue" : $.trim(getLocationSearchDetailsData[i].humanResourcesSystemStoreNumber)
                                	};
                                 	$scope.model.advancedSearchDataSelectDisplays[index][i] = advancedSearchData;
                                 }
                                 $scope.model.queries[index].show = show;
                         
                             }, function () {
                         
                                 $scope.model.advancedSearchDataSelectDisplays[index] = [];
                             });
                     }else 	if (dataFieldName === "PerformanceCode" ) {
                    	 operatorListType = 2;
                         services.getPerformanceSearchDetails()
                             .then(function (data) {
                                 var getPotentialSearchDetailsData = data.potentialPerformanceSearchDTO;
                                 
                                 var i = 0;
                                 for (i; i < getPotentialSearchDetailsData.length; i++) {
                                 	var advancedSearchData = {
                                		"id" : getPotentialSearchDetailsData[i].evaluateRatingCode,
                                		"label": $.trim(getPotentialSearchDetailsData[i].description),
                                		"dataValue" : $.trim(getPotentialSearchDetailsData[i].id)
                                	};
                                 	$scope.model.advancedSearchDataSelectDisplays[index][i] = advancedSearchData;
                                 }
                                 $scope.model.queries[index].show = show;
                                 
                             }, function () {
                         
                                 $scope.model.advancedSearchDataSelectDisplays[index] = [];
                             });
                     }else 	if (dataFieldName === "PotentialCode" ) {
                    	 operatorListType = 2;
                         services.getPotentialSearchDetails()
                             .then(function (data) {
                                 var getPotentialSearchDetailsData = data.potentialPerformanceSearchDTO;
                                 
                                 var i = 0;
                                 for (i; i < getPotentialSearchDetailsData.length; i++) {
                                 	var advancedSearchData = {
                                		"id" : getPotentialSearchDetailsData[i].evaluateRatingCode,
                                		"label": $.trim(getPotentialSearchDetailsData[i].description),
                                		"dataValue" : $.trim(getPotentialSearchDetailsData[i].id)
                                	};
                                 	$scope.model.advancedSearchDataSelectDisplays[index][i] = advancedSearchData;
                                 }
                                 $scope.model.queries[index].show = show;
                                 
                             }, function () {
                         
                                 $scope.model.advancedSearchDataSelectDisplays[index] = [];
                             });
                     }else if (dataFieldName === "PotentialNextPositionReadiness" ) {
                    	 operatorListType = 2;
                         services.getNextPositionReadinessSearchDetails()
                             .then(function (data) {
                                 var getNextPositionReadinessSearchDetailsData = data.readinessSearchDTO;
                                 
                                 var i = 0;
                                 for (i; i < getNextPositionReadinessSearchDetailsData.length; i++) {
                                 	var advancedSearchData = {
                                		"id" : getNextPositionReadinessSearchDetailsData[i].successionPlanGoalStatusCode,
                                		"label": $.trim(getNextPositionReadinessSearchDetailsData[i].successionPlanGoalStatusDescription),
                                		"dataValue" : $.trim(getNextPositionReadinessSearchDetailsData[i].successionPlanGoalStatusDescription),
                                	};
                                 	$scope.model.advancedSearchDataSelectDisplays[index][i] = advancedSearchData;
                                 }
                                 $scope.model.queries[index].show = show;
                                 
                             }, function () {
                         
                                 $scope.model.advancedSearchDataSelectDisplays[index] = [];
                             });
                     }else if (dataFieldName === "jobDescriptionOrRequested" ) {
                    	 //Potential Next Position
                    	 operatorListType = 3;
                         services.getNextPositionSearchDetails()
                             .then(function (data) {
                                 var getNextPositionSearchDetailsData = data.jobTitleSearchDTO;
                                 
                                 var i = 0;
                                 for (i; i < getNextPositionSearchDetailsData.length& i < $scope.GlobalListLimit; i++) {
                                 	var advancedSearchData = {
                                		"id" : getNextPositionSearchDetailsData[i].jobTitleCode,
                                		"label": $.trim(getNextPositionSearchDetailsData[i].jobTitleDescription),
                                		"dataValue" : $.trim(getNextPositionSearchDetailsData[i].jobTitleCode)
                                	};
                                 	$scope.model.advancedSearchDataSelectDisplays[index][i] = advancedSearchData;
                                 }
                                 $scope.model.queries[index].show = show;
                                 
                             }, function () {
                         
                                 $scope.model.advancedSearchDataSelectDisplays[index] = [];
                             });
                     }else if (dataFieldName === "RaceEthnicity" ) {
                    	 operatorListType = 2;
                         services.getRaceSearchDetails()
                             .then(function (data) {
                                 var getRaceSearchDetailsData = data.raceSearchDTO;
                                 
                                 var i = 0;
                                 for (i; i < getRaceSearchDetailsData.length; i++) {
                                 	var advancedSearchData = {
                                		"id" : getRaceSearchDetailsData[i].raceCode,
                                		"label": $.trim(getRaceSearchDetailsData[i].raceDescription),
                                		"dataValue" : $.trim(getRaceSearchDetailsData[i].raceCode)
                                	};
                                 	$scope.model.advancedSearchDataSelectDisplays[index][i] = advancedSearchData;
                                 }
                                 $scope.model.queries[index].show = show;
                                 
                             }, function () {
                         
                                 $scope.model.advancedSearchDataSelectDisplays[index] = [];
                             });
                     }else if (dataFieldName === "Region" ) {
                    	 operatorListType = 3;
                         services.getRegionSearchDetails()
                             .then(function (data) {
                                 var getRegionSearchDetailsData = data.regionSearchDTO;
                                 
                                 var i = 0;
                                 for (i; i < getRegionSearchDetailsData.length; i++) {
                                 	var advancedSearchData = {
                                		"id" : getRegionSearchDetailsData[i].humanResourcesSystemOperationsGroupCode,
                                		"label": $.trim(getRegionSearchDetailsData[i].operationsGroupName),
                                		"dataValue" : $.trim(getRegionSearchDetailsData[i].humanResourcesSystemOperationsGroupCode)
                                	};
                                 	$scope.model.advancedSearchDataSelectDisplays[index][i] = advancedSearchData;
                                 }
                                 $scope.model.queries[index].show = show;
                                 
                             }, function () {
                         
                                 $scope.model.advancedSearchDataSelectDisplays[index] = [];
                             });
                     }else if (dataFieldName === "SchoolName" ) {
                    	 operatorListType = 3;
                         services.getSchoolSearchDetails()
                             .then(function (data) {
                                 var getSchoolSearchDetailsData = data.schoolSearchDTO;
                                 
                                 var i = 0;
                                 for (i; i < getSchoolSearchDetailsData.length & i < $scope.GlobalListLimit; i++) {
                                 	var advancedSearchData = {
                                		"id" : getSchoolSearchDetailsData[i].id,
                                		"label": $.trim(getSchoolSearchDetailsData[i].description),
                                		"dataValue" : $.trim(getSchoolSearchDetailsData[i].description)
                                	};
                                 	$scope.model.advancedSearchDataSelectDisplays[index][i] = advancedSearchData;
                                 }
                                 $scope.model.queries[index].show = show;
                                 
                             }, function () {
                         
                                 $scope.model.advancedSearchDataSelectDisplays[index] = [];
                             });
                     }else if (dataFieldName === "StoreNumber" ) {
                    	 operatorListType = 2;
                         services.getStoreSearchDetails()
                             .then(function (data) {
                                 var getStoreSearchDetailsData = data.storeSearchDTO;
                                 
                                 var i = 0;
                                 for (i; i < getStoreSearchDetailsData.length; i++) {
                                 	var advancedSearchData = {
                                		"id" : getStoreSearchDetailsData[i].humanResourcesSystemStoreNumber,
                                		"label": getStoreSearchDetailsData[i].humanResourcesSystemStoreNumber,
                                		"dataValue" : getStoreSearchDetailsData[i].humanResourcesSystemStoreNumber
                                	};
                                 	$scope.model.advancedSearchDataSelectDisplays[index][i] = advancedSearchData;
                                 }
                                 $scope.model.queries[index].show = show;
                                 
                             }, function () {
                         
                                 $scope.model.advancedSearchDataSelectDisplays[index] = [];
                             });
                     }else if (dataFieldName === "Gender" ) {
                    	 operatorListType = 1;
                         services.getAdvancedSearchSexList()
                             .then(function (data) {
                                 var getAdvancedSearchSexListData = data.advancedSearchSexList;
                                 
                                 var i = 0;
                                 for (i; i < getAdvancedSearchSexListData.length; i++) {
                                 	var advancedSearchData = {
                                		"id" : getAdvancedSearchSexListData[i].id,
                                		"label": getAdvancedSearchSexListData[i].label,
                                		"dataValue" : getAdvancedSearchSexListData[i].id
                                	};
                                 	$scope.model.advancedSearchDataSelectDisplays[index][i] = advancedSearchData;
                                 }
                                 $scope.model.queries[index].show = show;
                                 
                             }, function () {
                                 
                                 $scope.model.advancedSearchDataSelectDisplays[index] = [];
                             });
                     } else if (dataFieldName === "PreviousTitle" ) {
                    	 operatorListType = 3;
                         services.getPrevTitleDetails()
                             .then(function (data) {
                                 var getPrevTitleDetailsData = data.jobTitleSearchDTO;
                                 
                                 var i = 0;
                                 for (i; i < getPrevTitleDetailsData.length& i < $scope.GlobalListLimit; i++) {
                                 	var advancedSearchData = {
                                		"id" : getPrevTitleDetailsData[i].jobTitleCode,
                                		"label": $.trim(getPrevTitleDetailsData[i].jobTitleDescription),
                                		"dataValue" : $.trim(getPrevTitleDetailsData[i].jobTitleCode)
                                	};
                                 	$scope.model.advancedSearchDataSelectDisplays[index][i] = advancedSearchData;
                                 	
                                 }
                                 $scope.model.queries[index].show = show;
                                 
                             }, function () {
                                 
                                 $scope.model.advancedSearchDataSelectDisplays[index] = [];
                             });
                     } else if (dataFieldName === "HaveDegree" ||
                    		    dataFieldName === "Relocate") {
                    	 operatorListType = 1;
                         services.getAdvancedSearchYesNoList()
                             .then(function (data) {
                            	 var advancedSearchYesNoList = data.advancedSearchYesNoList;
                            	 
                            	 var i = 0;
                                 for (i; i < advancedSearchYesNoList.length; i++) {
                                 	var advancedSearchData = {
                                		"id" : advancedSearchYesNoList[i].id,
                                		"label": advancedSearchYesNoList[i].label,
                                		"dataValue" : advancedSearchYesNoList[i].id
                                	};
                                 	$scope.model.advancedSearchDataSelectDisplays[index][i] = advancedSearchData;
                                 	
                                 }
                                 $scope.model.queries[index].show = show;
                                 
                             }, function () {
                                 $scope.model.advancedSearchDataSelectDisplays[index] = [];
                             });
                         
                     }else 	if (dataFieldName === "AssociateID" ||
                    		 dataFieldName === "FirstName" ||
                    		 dataFieldName === "Group" ||
                    		 dataFieldName === "LastName" ||
                    		 dataFieldName === "PreviousEmployer" ||
                    		 dataFieldName === "Region" ||
                    		 dataFieldName === "TimeInTitle" ||
                    		 dataFieldName === "TimeWithCompany") {
                    	 
                    	 //Ugh, separate operator logic
                    	 if (dataFieldName === "AssociateID"){
                    		 operatorListType = 2;
                    	 }else if (dataFieldName === "FirstName"){
                    		 operatorListType = 4;
                    	 }else if (dataFieldName === "Group"){
                    		 operatorListType = 2;
                    	 }else if (dataFieldName === "LastName"){
                    		 operatorListType = 8;
                    	 }else if (dataFieldName === "PreviousEmployer"){
                    		 operatorListType = 3;
                    	 }else if (dataFieldName === "Region"){
                    		 operatorListType = 3;
                    	 }else if (dataFieldName === "TimeInTitle"){
                    		 operatorListType = 6;
                    	 }else if (dataFieldName === "TimeWithCompany"){
                    		 operatorListType = 6;
                    	 };
                    	 
                    	 
                    	 
                    	 //above selections require entering data by actual typing, so we don't show a drop-down
                    	 show = "enterdata";
                         services.getAdvancedSearchEnterDataValueList()
                             .then(function (data) {
                            	 $scope.model.advancedSearchDataSelectDisplays[index] = data.advancedSearchEnterDataValueList;
                            	 $scope.model.queries[index].show = show;
                             }, function () {
                                 $scope.model.advancedSearchDataSelectDisplays[index] = [];
                             });

                     } else if (dataFieldName === "OriginalHireDate" ) {
                    	 operatorListType = 7;
                    	 show = "enterdata";
                         services.getAdvancedSearchEnterDateList()
                             .then(function (data) {
                                 $scope.model.advancedSearchDataSelectDisplays[index] = data.advancedSearchEnterDateList;
                                 $scope.model.queries[index].show = show;
                             }, function () {
                                 $scope.model.advancedSearchDataSelectDisplays[index] = [];
                             });
                         

                     } else if (dataFieldName === null ) {
                    	 //someone selected the "Select" topmost value so we handle it here
                         $scope.model.advancedSearchDataSelectDisplays[index] = [];
                     } else {
                    	 //Call 911
                    	 ErrorModal.show("Data Field type not found!");
                     }
            	
            	 var i = 0;
            	 var dataFieldCode = "";
                 for (i; i < $scope.model.advancedSearchDataField.length; i++) {
                	 if(dataFieldName == $scope.model.advancedSearchDataField[i].id){
                		 dataFieldCode = $scope.model.advancedSearchDataField[i].code;
                	 }
                 }
            				if(reset == true){ //blank is true for method calls triggered by screen change, so reset these fields
            					$scope.model.queries[index].dataValue = "";
            					$scope.model.queries[index].operator = "=";
            				}
            				$scope.model.queries[index].selected = false; 
            				$scope.model.queries[index].operatorListType = operatorListType;
            				$scope.model.queries[index].multipleSelect = false;
            				$scope.model.queries[index].dataFieldCode = dataFieldCode;
            	 

            
               	 SpinnerService.hide(searchSpinner);
               	 
             };
             
           
             
             
            /**
             * Controller for the Edit Job modal dialog
             * @param {object} $scope         model to bind to
             * @param {object} $modalInstance handler to the modal dialog instance
             * @param {object} items          currently selected item to edit
             */
             var LoadSQLModalCtrl = function ($scope, $modalInstance, loadSQL) {
            	 //define in LoadSQLModalCtrl scope.  Modal scope is isolated, so we need to pass things back and forth.
            	 $scope.selected  = {
            			 listSelect: ''
            	 };
            	 /**
            	  * Method for getting saved search data
            	  */
            	 $scope.getSQLList = function() {
            		 $scope.sqlModalList = [{"id" : 0, "label" : "Loading List..."}];
                 	  //Refresh SQL list
                 	  services.getLoadSQLInformation()
                   	 .then(function (data) {
		                      		 $scope.loadSQL=data.loadQuery;
		                           	 var sqlModalList = [];
		                           	 var x = 0;// old-fashioned loops are the fastest
		                           	 for(x; x < $scope.loadSQL.length; x++){
		                                    var key = $scope.loadSQL[x].successionPlanQueryId;
		                                    var label = $scope.loadSQL[x].successionPlanQueryName;
		                                    var unique = true;
		                                    var i=0;
		                                    for(i; i < sqlModalList.length; i++){
		                                   	if(sqlModalList[i].id == key){
		                                   		unique = false;
		                                   		break;
		                                   	}; 
		                                    };
		                                    if(unique){
		                                   	 sqlModalList.push({
		                                   		 "id" : key,
		                                   		 "label" : label
		                                   	 })	;
		                                    }
		                                };
		                                
		                                $scope.sqlModalList = sqlModalList;
		                      		 
                             }, function () {
                             	//Nothing here
                             });
            	 };
            	 /**
            	  *This is a call to populate the list when modal loads 
            	  */
            	 $scope.getSQLList();
            	 /**
            	  *modal load button click 
            	  */
                 $scope.load = function () {
                	 //we close the modal and return to page since sql data is already loaded and selected.  We pass back $scope.loadSQL since it is in local scope
                	 $modalInstance.close({'action':'sqlLoad','select': $scope.selected.listSelect, 'loadSQL': $scope.loadSQL} );
                 };
                 /**
                  *modal append button click 
                  */
                 $scope.append = function () {
                	 //we close the modal and return to page since sql data is already loaded and selected
                	 $modalInstance.close({'action':'sqlAppend','select': $scope.selected.listSelect, 'loadSQL': $scope.loadSQL} );
                 };
                 /**
                  * modal delete button click
                  */
                 $scope.deleteClick = function () {
                	 //are you sure
                	 var confirm = ConfirmModal.show("Are you really sure you want to delete your hard work?");
                     confirm.result.then(function (result) {
                         if (result) {
                          	 services.deleteSQLQuery($scope.selected.listSelect)
                         	 .then(function (data) {
                         		 //process result	
                         		 if(data.deleteQuery = true){
                         			alert("Deleted as you wished.");
                         			//refresh list
                         			$scope.getSQLList();
                         		 } else {
                         			 alert("Sorry, it didn't work out.");
                         		 }
                         	 
                         	 });
                         		 
                         };
                     
                     });
                	 

                 };            	 
            	 
                 /**
                  * modal cancel button click
                  */
                 $scope.cancel = function () {
                    // undoEdit();
                     $modalInstance.dismiss('cancel');
                 };

             };
             
             /**
              * Handler for Load Saved Searches dialog.  Loads modal for saved searches
              */
              $scope.model.loadSQL = [{}];
              $scope.ShowLoadSQLModal = function () {
                  var modalInstance = $modal.open({
                      templateUrl: 'Templates/loadSQLDialog.html',
                      controller: LoadSQLModalCtrl,
                      backdrop: 'static',
                      keyboard: false,
                      size: 'lg',
                      resolve: {
                     	 loadSQL: function () {
                        	 //return something, it will be passed to the Modal as a param in loadSQL, if you need it
                          }
                      }
                  });
                  modalInstance.result.then(function (result) {
                     
                 	 if (result.action === 'sqlLoad') {
                 		 $scope.loadSelectedModalSql(result.select, result.loadSQL);
                 		                         }
                      if (result.action === 'sqlAppend') {
                    	  $scope.appendSelectedModalSql(result.select, result.loadSQL);
                      }
                      if (result.action === 'sqlDelete'){
                    	  //Yes, we returned here form the Modal, but we leave it up.

                      }
                  });
              };
              
              $scope.loadSelectedModalSql = function (selectedSql, loadSQL){
            	  //a trick to use one method for both things
            	  $scope.model.queries = [];
            	  $scope.appendSelectedModalSql(selectedSql, loadSQL);
              };
              
              $scope.appendSelectedModalSql = function (selectedSql, loadSQL){
            	  var scopeIndex = $scope.model.queries.length;
            	  var sqlArray = [];
            	  //selectedSql is succesionPlanQueryId, the key of saved sql queries
            	  //loadSQL is the full list of saved SQL searches
            	  //load array with selected sql queries
            	  var arrayIndex = 0;
            	  for(arrayIndex; arrayIndex < loadSQL.length; arrayIndex++){
                      var key = loadSQL[arrayIndex].successionPlanQueryId;
                      if(key == selectedSql){
                    	  sqlArray.push(loadSQL[arrayIndex]);
                      };
            	  };
            	  //spin through saved sql and find each matching query id 
            	  var sqlIndex = 0;
            	  for(sqlIndex; sqlIndex < sqlArray.length; sqlIndex++){
            		  var loopScopeIndex = scopeIndex + sqlIndex;
            		  $scope.pushNewQueryRow(loopScopeIndex);
                    	//load things
                    	  var columnId = sqlArray[sqlIndex].successionPlanColumnId;

		             	 //Load Data Select 
		             	 //ID is successionPlanColumnId  
		             	 //model.advancedSearchDataFieldNames[$index]
		            	 var y = 0;
		              	 for(y; y < $scope.model.advancedSearchDataField.length; y++){
		                       var code = $scope.model.advancedSearchDataField[y].code;
		                       if(code == columnId){
		                    	   $scope.model.queries[loopScopeIndex].dataFieldName = $scope.model.advancedSearchDataField[y].id;
		                       };
		                 };
	                   //select the data select drop-down. This also creates query object at proper index
		                 //sqlArray is passed to getAdvancedSearchSelectData for loading after lists are populated
	                   $scope.getAdvancedSearchSelectData(loopScopeIndex, false);

	                   
	    			var dataValue = "";
	       			var inputList = [];
	       			var openParenVal = "";
	       			var closeParenVal  = ""; 
	       			var andOr = "";
	       			var operator = "";
	                 
	       			
	   	             	 //Load open parens
	   	             	 //loadQuery.openParenthesesValue is Y or N
	   	  	              if(sqlArray[sqlIndex].openParenthesesValue == "Y"){
	   	  	            	 openParenVal = "(";	  	            	  
	   	  	              }
	   	  	              //Load close parens
	   	              	 //query.closeParenVal     
	   	  	              if(sqlArray[sqlIndex].closeParenthesesValue == "Y"){
	   	  	            	 closeParenVal = ")";	  	            	  
	   		  	          }

	   	                  //Load Query andOr
	   	                  andOr = $.trim(sqlArray[sqlIndex].connectOperatorText);
	   			              //Load Operator
	   			              //Operator value operatorText
	   	                  operator = $.trim(sqlArray[sqlIndex].operatorText);
	   			    	    
	   			    	   
	   					   //Load values and array if applicable
	   			           var parameterValue = $.trim(sqlArray[sqlIndex].parameterValue);
	   			           
	   			           //TODO sometimes data values are before, sometimes after $$.  We are assuming after since this matches most of saved searches
		   			        if(parameterValue.indexOf("$$") > -1){
		   			           var array = sqlArray[sqlIndex].parameterValue.split("$$");
		   			            if(array.length > 0){
		   			            	parameterValue = array[1];
		   			            };
		   			        };
	   			    
	   			            dataValue = parameterValue;
	   			            $scope.model.queries[loopScopeIndex].dataSelect = parameterValue; //attempt to set select list to a value
	   			            $scope.model.queries[loopScopeIndex].dataValue = parameterValue;
	   			            
	   			            if(parameterValue.indexOf(",") > -1){
	   			            	inputList = parameterValue.split(",");
	   			            	//TODO translate descriptions
	   			            };
	                
	   			            
	   			        $scope.model.queries[loopScopeIndex].dataValue = dataValue;
	   			        $scope.model.queries[loopScopeIndex].inputList = inputList;
	   			        $scope.model.queries[loopScopeIndex].openParenVal = openParenVal;
	   			        $scope.model.queries[loopScopeIndex].closeParenVal  = closeParenVal; 
	   			        $scope.model.queries[loopScopeIndex].andOr = andOr;
	   			        $scope.model.queries[loopScopeIndex].operator = operator;
	   			     
		  	              //Check multiple select
		                  $scope.checkMultipleSelect(loopScopeIndex);
		                  
		                  
                 };
             	
                 
              };
  
             
             
              /**
               * Controller for the SAVE SQL modal dialog
               * @param {object} $scope         model to bind to
               * @param {object} $modalInstance handler to the modal dialog instance
               * @param {object} items          currently selected item to edit
               */
               var SaveSQLModalCtrl = function ($scope, $modalInstance, saveSQL) {
              	 //define in SaveSQLModalCtrl scope.  Modal scope is isolated, so we need to pass things back and forth.
            	   //saveSQL contains query details to save
              	 $scope.selected  = {
              			 listSelect: ''
              	 };
              	 
               	 /**
            	  * Method for getting saved search data
            	  */
            	 $scope.getSQLList = function() {
            		 $scope.sqlModalList = [{"id" : 0, "label" : "Loading List..."}];
                 	  //Refresh SQL list
                 	  services.getLoadSQLInformation()
                   	 .then(function (data) {
		                      		 $scope.loadSQL=data.loadQuery;
		                           	 var sqlModalList = [];
		                           	 var x = 0;// old-fashioned loops are the fastest
		                           	 for(x; x < $scope.loadSQL.length; x++){
		                                    var key = $scope.loadSQL[x].successionPlanQueryId;
		                                    var label = $scope.loadSQL[x].successionPlanQueryName;
		                                    var unique = true;
		                                    var i=0;
		                                    for(i; i < sqlModalList.length; i++){
		                                   	if(sqlModalList[i].id == key){
		                                   		unique = false;
		                                   		break;
		                                   	}; 
		                                    };
		                                    if(unique){
		                                   	 sqlModalList.push({
		                                   		 "id" : key,
		                                   		 "label" : label
		                                   	 })	;
		                                    }
		                                };
		                                
		                                $scope.sqlModalList = sqlModalList;
		                                $scope.save.show= true;
                             }, function () {
                             	//Nothing here
                             });
            	 };
            	 /**
            	  *This is a call to populate the list when modal loads 
            	  */
            	 $scope.getSQLList();

              	              	 
              	 //Allow user to enter a name in the text field
              	 //Check if name is duplicate
              	 //Send search data to service
              	 //Process and display result
              	 //Allow user to click "close" to exit modal
              	 /**
              	  * Method for getting saved search data
              	  */
              	 $scope.saveSQLList = function() {
                	 //define in SaveSQLModalCtrl scope.  Modal scope is isolated, so we need to pass things back and forth.
                	 $scope.save  = {
                			 SQLName: '',
                			 show: false
                	 };
              	 };
              	 
              	 /**
              	  *modal save button click 
              	  */
                   $scope.save = function () {
                	   
                	   var chosenName = $.trim($scope.save.SQLName);
                	   if(chosenName.length == 0 ){
                		   ErrorModal.show("Please enter a name under which to save the query.");
                       		return false;
                	   }
                	   var x = 0;// old-fashioned loops are the fastest
                       	 for(x; x < $scope.sqlModalList.length; x++){
                       		var savedName = $.trim($scope.sqlModalList[x].label);
                       		if(chosenName === savedName){
                              	ErrorModal.show("This name is already in use.  Please choose another.");
                              	return false;
                            };
                          };
                    //we close the modal and return to process saving the search since the modal does not have $scope.model.queries in scope. 
                  	 $modalInstance.close({'action':'save','select': $scope.save.SQLName } );
                   };
                   /**
                    * modal close button click
                    */
                   $scope.cancel = function () {
                       $modalInstance.dismiss('cancel');
                   };

               };
               
               /**
                * Handler for Load Saved Searches dialog.  Loads modal for to let save searches
                */
         
                $scope.ShowSaveSQLModal = function () {
                    var modalInstance = $modal.open({
                        templateUrl: 'Templates/saveSQLDialog.html',
                        controller: SaveSQLModalCtrl,
                        backdrop: 'static',
                        keyboard: false,
                        size: 'lg',
                        resolve: {
                       	 saveSQL: function () {
                          	 //return something, it will be passed to the Modal as a param in saveSQL, if you need it
                       		 
                       		 saveSQL = [];// pass nothing
                       		 return saveSQL;
                       	 }
                     }
                    });
                    
                    modalInstance.result.then(function (result) {
                    	if (result.action === 'save') {
                      		 $scope.saveAdvancedSearch(result.select);
                        }
                    });
                
                };
              
       		 $scope.saveAdvancedSearch = function(SQLName){
       			$scope.callSaveSearchService($scope.getAdvancedSearchObject($scope.model.queries), SQLName);
       		 };
       		 
       		 $scope.callSaveSearchService = function(advancedSearchObject, SQLName){
       			
// 			 	@QueryParam("query") List<AdvancedSearch> query, 
//				@QueryParam("description")String description,
//				@QueryParam("queryName") String queryName,
       			services.saveSQLQuery(advancedSearchObject, SQLName, SQLName)
                .then(function (data) {
                    if (data) {
//                    	alert(JSON.stringify(data));
//                        $scope.currentCandidate = null;
//                        $scope.moveFromReqNum = null;
                        // Refetch job list to reflect changes.
                        // Also necessary to remove category filter applied during move.
//                        $scope.loadUsers();
                    }else{
                    	alert("I'm sorry, it didn't work out.");
                    }
                });
       			
       		 };
  
             $scope.navSearch = function () {
               // $location.path('/search/');
            	$scope.ShowResults = false;
            };
            
			 $scope.ShowBasicInfo = function () {
				 
				 	var selectedAssociate = $scope.model.records.currentItem;
	                var associateId = $.trim(selectedAssociate.zeroEmployeeId);
	                var firstName = "";
	                var lastName = "";
	                var fullName = $.trim(selectedAssociate.associates);
	                var jobTitle = $.trim(selectedAssociate.jobTitleDescription);       
	                
	                $scope.array = fullName.split(',');
	                
	                if($scope.array.length > 1){
	                	lastName = $scope.array[0];
	                	firstName = $scope.array[1];
	                }
	                CookieManager.deleteCookie("associateId");
	                CookieManager.deleteCookie("firstName");
	                CookieManager.deleteCookie("lastName");
	                CookieManager.deleteCookie("jobTitle");
	                
	                CookieManager.setCookie('associateId', associateId);
	                CookieManager.setCookie('firstName', firstName);
	                CookieManager.setCookie('lastName', lastName);
	                CookieManager.setCookie('jobTitle', jobTitle);
	                $location.path("/basicInfo");
	            };
				 $scope.Reports = function () {
					 
					 $location.path("/report");
		      };
               
        }]);
});
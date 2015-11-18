/*jslint browser: true*/
/*globals define, app, $, confirm*/
/**
* @memberOf app
* @class CandidateSearchController ViewModel for Candidate Search view.
* @constructor
*/
define("controllers/FindController", ['app'], function (app) {
    app.controller('FindController', ['$scope', '$location', '$filter', 'SharedServices', 'MessageService', 'CollectionBase', 'SpinnerService', 'slalom.browser.CookieManager', 'ErrorModal', 'ValidatorHelper', '$localstorage',
        function ($scope, $location, $filter, services, messageService, CollectionBaseFactory, SpinnerService, CookieManager, ErrorModal, ValidatorHelper, $localstorage) {

    	document.getElementById("subnav1").style.display = 'none';

            // Locals
            var spinnerField = "candidateSearchSpinner";
            var defaultFilter = {
            		nameLast: '',
            		associateId: '',
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
                return item.associateId ||  item.nameLast  || item.nameContain || item.firstNameStartsWith  || item.preferredNameStartsWith || item.jobTitle  || item.storeNo || item.lastfirstName;
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
                { "fieldName": "lastfirstName", "message": "At least one value is required", "validator": inputRequired },
                //{ "fieldName": "storeNo", "message": "Numbers Only", "validator": function (item) { return !item.storeNo || item.storeNo.match(/^[0-9]*$/); } },
                { "fieldName": "storeNo", "message": "At least one value is required", "validator": inputRequired },
                { "fieldName": "storeNo", "message": "Must be 4 characters", "validator": function (item) { return !item.storeNo || item.storeNo.match(/^[0-9a-zA-Z]{4}$/); } }
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
            $scope.model.records.items = [{}]; // Hack to force not found footer to appear while searching
            $scope.model.records.rowFilter = function (item) {
                return ($scope.reqFilter === "" || $scope.reqFilter === null || $filter('filter')([item], $scope.reqFilter, false).length > 0);
            };
            $scope.model.count=0;
          
            /** 
            * Handler to call the getCandidates service and display the results when completed
            * @async
            */
            $scope.DoFind = function () {
                if ($scope.ShowResults) {
                    $scope.ShowResults = false;
                    $scope.model.records.items = [{}];
                } else {
                    $scope.ShowResults = true;
                    SpinnerService.show(spinnerField);
                    services.getCandidates($scope.searchFilter)
                        .then(function (data) {
                        	$scope.model.count = data.count;                        	
                        	$localstorage.setObject('acount', data.findDTO);
                        	$scope.model.records.items = data.findDTO;
                            SpinnerService.hide(spinnerField);
                        }, function () {
                            SpinnerService.hide(spinnerField);
                            $scope.ShowResults = false;
                        });
                }
            };
            
            
			 $scope.ShowBasicInfo = function () {
				 
			 	var selectedAssociate = $scope.model.records.currentItem;
                var associateId = $.trim(selectedAssociate.zeroEmployeeId);
                var firstName = $.trim(selectedAssociate.firstName);
                var lastName = $.trim(selectedAssociate.lastName);
                var jobTitle = $.trim(selectedAssociate.jobTitleDescription);       
                
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
            
        }]);
});
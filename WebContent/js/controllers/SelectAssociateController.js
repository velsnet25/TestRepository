/*jslint browser: true*/
/*globals define, app, $, confirm*/
/**
* @memberOf app
* @class CandidateSearchController ViewModel for Candidate Search view.
* @constructor
*/
define("controllers/SelectAssociateController", ['app'], function (app) {
    app.controller('SelectAssociateController', ['$scope', '$location', '$filter', 'SharedServices', 'MessageService', 'CollectionBase', 'SpinnerService', 'slalom.browser.CookieManager', 'ErrorModal', 'ValidatorHelper', '$localstorage',
        function ($scope, $location, $filter, services, messageService, CollectionBaseFactory, SpinnerService, CookieManager, ErrorModal, ValidatorHelper, $localstorage) {

    	document.getElementById("subnav1").style.display = 'none';

           
            // Change the message banner for this page.
            messageService.clearMessage();
            messageService.addMessage("Select Associate");

           $scope.notFound = "Associates not found, please enter new search criteria and try again or contact MYTHDHR at 1-866-MYTHDHR (1-866-698-4347) for assistance.";

            /**
            * Flag indicating if we are in search or view results mode. Manages the accordian control that flips between the views.
            * @type {Boolean}
            */
            $scope.ShowResults = false;
           
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
            $scope.doSelect = function () {                
                $scope.ShowResults = true;
                $scope.model.records.items = $localstorage.getObject('acount');     
            	$scope.model.count = $scope.model.records.items.length;
            };
            $scope.doSelect();
            
            $scope.getReport = function () {
        		if (!CookieManager.getCookie('associateId') || !CookieManager.getCookie('firstName') || !CookieManager.getCookie('lastName') || !CookieManager.getCookie('jobTitle'))
        			$location.path("/report2");	
            	 else
            		$location.path("/report");
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
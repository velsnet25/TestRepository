/*jslint browser: true*/
/*globals define, app, $, confirm*/
/**
* @memberOf app
* @class CandidateSearchController ViewModel for Candidate Search view.
* @constructor
*/

define("controllers/WorkController", ['app'], function (app) {
    app.controller('WorkController', ['$scope', '$location', '$filter', 'SharedServices', 'MessageService', 'CollectionBase', 'SpinnerService', 'slalom.browser.CookieManager', 'ErrorModal', 'ValidatorHelper',
        function ($scope, $location, $filter, services, messageService, CollectionBaseFactory, SpinnerService, CookieManager, ErrorModal, ValidatorHelper) {

    	document.getElementById("subnav1").style.display = 'block';
    
    	// Bound values
        $scope.ShowResults = true;
        var spinnerField = "workHistorySpinner";
        
        $scope.associateId = null;
    	$scope.firstName = null;
    	$scope.lastName = null;
    	$scope.jobTitle = null;
        $scope.hasPrevious = false;
        $scope.hasNext = false;
              
        if (!CookieManager.getCookie('associateId') || !CookieManager.getCookie('firstName') || !CookieManager.getCookie('lastName') || !CookieManager.getCookie('jobTitle')) {
            $location.path("/main");
        }
        
        
        /**
         * Search results model object
         * @type {Object}
         */
         $scope.model = { records: new CollectionBaseFactory() };
         $scope.model.records= [{}]; // Hack to force not found footer to appear while searching
         $scope.model.records2 = [{}];
         $scope.model.records3 = [{}];
         
         $scope.loadWorkHistory = function () {
         SpinnerService.show(spinnerField);
         services.getWorkHistoryInformation(CookieManager.getCookie('associateId'))
             .then(function (data) {
            	 $scope.model.records = data.workHistoryHomeDepotDTO; 
            	 $scope.model.records2 = data.workHistoryHomeDepotPre95DTO;
            	 $scope.model.records3 = data.workHistoryExternalDTO;
            	 
            	 $scope.associateId = CookieManager.getCookie('associateId');
             	 $scope.firstName = CookieManager.getCookie('firstName');
             	 $scope.lastName = CookieManager.getCookie('lastName');
             	 $scope.jobTitle = CookieManager.getCookie('jobTitle');
                 $scope.hasPrevious = CookieManager.getCookie('prv')=="true"?true:false;
                 $scope.hasNext = CookieManager.getCookie('nxt')=="true"?true:false;
                 
                 SpinnerService.hide(spinnerField);
             }, function () {
                 SpinnerService.hide(spinnerField);
             });
         };
         $scope.loadWorkHistory();
        
         // Prev Buton
         $scope.prevAssociate = function () {
        	 services.getPageInfo(true, false);        	 
             $scope.loadWorkHistory();
         };
         
         // Next Button
         $scope.nextAssociate = function () {
        	 services.getPageInfo(false, true);        	 
             $scope.loadWorkHistory();
         };
    	
         // Select button    
  	    $scope.selectAssociate = function () {
  	    	$location.path("/select");
  		};
        
        }]);
    
	
});
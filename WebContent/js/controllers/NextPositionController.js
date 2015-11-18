/*jslint browser: true*/
/*globals define, app, $, confirm*/
/**
* @memberOf app
* @class CandidateSearchController ViewModel for Candidate Search view.
* @constructor
*/
define("controllers/NextPositionController", ['app'], function (app) {
    app.controller('NextPositionController', ['$scope', '$location', '$filter', 'SharedServices', 'MessageService', 'CollectionBase', 'SpinnerService', 'slalom.browser.CookieManager', 'ErrorModal', 'ValidatorHelper',
        function ($scope, $location, $filter, services, messageService, CollectionBaseFactory, SpinnerService, CookieManager, ErrorModal, ValidatorHelper) {

    	document.getElementById("subnav1").style.display = 'block';
    	
    	// Change the message banner for this page.
        messageService.clearMessage();
        messageService.addMessage("Candidate Search");

        // Bound values
        $scope.ShowResults = true;
        var spinnerField = "nextPositionSpinner";
        
        $scope.associateId = null;
    	$scope.firstName = null;
    	$scope.lastName = null;
    	$scope.jobTitle = null;
        $scope.hasPrevious = false;
        $scope.hasNext = false;
        $scope.currentTime = new Date();
              
        if (!CookieManager.getCookie('associateId') || !CookieManager.getCookie('firstName') || !CookieManager.getCookie('lastName') || !CookieManager.getCookie('jobTitle')) {
            $location.path("/main");
        }
                
        /**
         * Search results model object
         * @type {Object}
         */
         $scope.model = { records: new CollectionBaseFactory() };
         $scope.model.records = [{}]; // Hack to force not found footer to appear while searching
         $scope.model.records.rowFilter = function (item) {
             return ($scope.reqFilter === "" || $scope.reqFilter === null || $filter('filter')([item], $scope.reqFilter, false).length > 0);
         };
         
         $scope.loadNextPosition = function () {
         SpinnerService.show(spinnerField);
         services.getNextPositionsInformation(CookieManager.getCookie('associateId'))
             .then(function (data) {
            	 $scope.model.records = data.nextPositionDTO;                 
                 SpinnerService.hide(spinnerField);
                 
                 $scope.associateId = CookieManager.getCookie('associateId');
             	 $scope.firstName = CookieManager.getCookie('firstName');
             	 $scope.lastName = CookieManager.getCookie('lastName');
             	 $scope.jobTitle = CookieManager.getCookie('jobTitle');
                 $scope.hasPrevious = CookieManager.getCookie('prv')=="true"?true:false;
                 $scope.hasNext = CookieManager.getCookie('nxt')=="true"?true:false;
             }, function () {
                 SpinnerService.hide(spinnerField);                 
             });
         };
         $scope.loadNextPosition();
         
                  
         // Prev Buton
         $scope.prevAssociate = function () {
        	 services.getPageInfo(true, false);        	 
             $scope.loadNextPosition();
         };
         
         // Next Button
         $scope.nextAssociate = function () {
        	 services.getPageInfo(false, true);        	 
             $scope.loadNextPosition();
         };
         
         // Select button    
  	    $scope.selectAssociate = function () {
  	    	$location.path("/select");
  		};
         
        }]);
});
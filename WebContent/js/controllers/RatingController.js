/*jslint browser: true*/
/*globals define, app, $, confirm*/
/**
* @memberOf app
* @class CandidateSearchController ViewModel for Candidate Search view.
* @constructor
*/
define("controllers/RatingController", ['app'], function (app) {
    app.controller('RatingController', ['$scope', '$location', '$filter', 'SharedServices', 'MessageService', 'CollectionBase', 'SpinnerService', 'slalom.browser.CookieManager', 'ErrorModal', 'ValidatorHelper',
        function ($scope, $location, $filter, services, messageService, CollectionBaseFactory, SpinnerService, CookieManager, ErrorModal, ValidatorHelper) {

    	document.getElementById("subnav1").style.display = 'block';
    	
    	// Change the message banner for this page.
        messageService.clearMessage();
        messageService.addMessage("Candidate Search");

        // Bound values
        $scope.ShowResults = true;
        var spinnerField = "ratingSpinner";
        
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
         $scope.model.records = [{}]; // Hack to force not found footer to appear while searching
         $scope.model.records1 = [{}];
         $scope.model.records2 = [{}];
         $scope.model.records.rowFilter = function (item) {
             return ($scope.reqFilter === "" || $scope.reqFilter === null || $filter('filter')([item], $scope.reqFilter, false).length > 0);
         };
         
         $scope.loadRating = function () {
         SpinnerService.show(spinnerField);
         services.getRatingInformation(CookieManager.getCookie('associateId'))
             .then(function (data) {
                 $scope.model.records = data.currentRatingDTO;    
                 $scope.model.records1 = data.tesseractRatingDTO; 
                 $scope.model.records2 = data.tesseractRatingDTOBefore2002; 
                 
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
         $scope.loadRating();
         
         // Prev Buton
         $scope.prevAssociate = function () {
        	 services.getPageInfo(true, false);        	 
             $scope.loadRating();
         };
         
         // Next Button
         $scope.nextAssociate = function () {
        	 services.getPageInfo(false, true);        	 
             $scope.loadRating();
         };
         
         // Select button    
  	    $scope.selectAssociate = function () {
  	    	$location.path("/select");
  		};
    	
        }]);
});
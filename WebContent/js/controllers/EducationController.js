/*jslint browser: true*/
/*globals define, app, $, confirm*/
/**
* @memberOf app
* @class JobsController ViewModel for the landing Job List view.
* @constructor
*/
define("controllers/EducationController", ['app'], function (app) {
    app.controller('EducationController', ['$scope', '$modal', '$filter', '$location', 'SharedServices', 'MessageService', 'CollectionBase', 'SpinnerService', 'slalom.browser.CookieManager', 'ErrorModal', "ConfirmModal", "ValidatorHelper",
        function ($scope, $modal, $filter, $location, services, messageService, CollectionBaseFactory, SpinnerService, CookieManager, ErrorModal, ConfirmModal, ValidatorHelper) {
           
    	var spinnerField = "educationSpinner";
    	var spinnerField2 = "courseSpinner";
        document.getElementById("subnav1").style.display = 'block';
        
        $scope.associateId = null;
    	$scope.firstName = null;
    	$scope.lastName = null;
    	$scope.jobTitle = null;
        $scope.hasPrevious = false;
        $scope.hasNext = false;
              
        if (!CookieManager.getCookie('associateId') || !CookieManager.getCookie('firstName') || !CookieManager.getCookie('lastName') || !CookieManager.getCookie('jobTitle')) {
            $location.path("/main");
        }
        
        $scope.convertToYesOrNo = function(arg) {return arg ? 'Yes' : 'No'};
        
    	 $scope.courses = [{}];
    	 $scope.education = [{}];
    	 
    	$scope.loadEducation = function () { 
    	SpinnerService.show(spinnerField);
    	SpinnerService.show(spinnerField2);
    	services.getEducationInformation(CookieManager.getCookie('associateId'))
          .then(function (data) {
              $scope.education = data.educationDTO;  
              $scope.courses = data.courseDTO; 
              
              $scope.associateId = CookieManager.getCookie('associateId');
          	  $scope.firstName = CookieManager.getCookie('firstName');
          	  $scope.lastName = CookieManager.getCookie('lastName');
          	  $scope.jobTitle = CookieManager.getCookie('jobTitle');
              $scope.hasPrevious = CookieManager.getCookie('prv')=="true"?true:false;
              $scope.hasNext = CookieManager.getCookie('nxt')=="true"?true:false;
              
              SpinnerService.hide(spinnerField);
              SpinnerService.hide(spinnerField2);
          	}, function () {
              SpinnerService.hide(spinnerField);
              SpinnerService.hide(spinnerField2);
          	});
    	 };
         $scope.loadEducation();
    	
    	// Prev Buton
        $scope.prevAssociate = function () {
       	 services.getPageInfo(true, false);        	 
            $scope.loadEducation();
        };
        
        // Next Button
        $scope.nextAssociate = function () {
       	 services.getPageInfo(false, true);        	 
            $scope.loadEducation();
        };
        
        // Select button    
 	    $scope.selectAssociate = function () {
 	    	$location.path("/select");
 		};
        
        } ]);
});
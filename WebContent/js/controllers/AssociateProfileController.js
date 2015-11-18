/*jslint browser: true*/
/*globals define, app, $, confirm*/
/**
* @memberOf app
* @class JobsController ViewModel for the landing Job List view.
* @constructor
*/
define("controllers/AssociateProfileController", ['app'], function (app) {
    app.controller('AssociateProfileController', ['$scope', '$modal', '$filter', '$location', 'SharedServices', 'MessageService', 'CollectionBase', 'SpinnerService', 'slalom.browser.CookieManager', 'ErrorModal', "ConfirmModal", "ValidatorHelper",
        function ($scope, $modal, $filter, $location, services, messageService, CollectionBaseFactory, SpinnerService, CookieManager, ErrorModal, ConfirmModal, ValidatorHelper) {
           
            document.getElementById("subnav1").style.display = 'block'; 
            
            if (!CookieManager.getCookie('associateId') || !CookieManager.getCookie('firstName') || !CookieManager.getCookie('lastName') || !CookieManager.getCookie('jobTitle')) {
                $location.path("/main");
            }
            
            $scope.associateId = CookieManager.getCookie('associateId');
            
            /**
            * Model instance
            */
            $scope.model = null;
           	
            // Select Associate button    
     	    $scope.selectAssociate = function () {
     	    	$location.path("/select");
     		};
     		
            $scope.onPrint = function () {
            	var url = "../HRReviewV2/rs/ReportingService/getAssociateProfileReport?reportCategory=1&associateId="+$scope.associateId;
                window.open(url, 'PrintAssociateProfile');
            };

             
        } ]);
});
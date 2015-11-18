/*jslint browser: true*/
/*globals define, app, $, confirm*/
/**
* @memberOf app
* @class JobsController ViewModel for the landing Job List view.
* @constructor
*/
define("controllers/ReportingController", ['app'], function (app) {
    app.controller('ReportingController', ['$scope', '$modal', '$filter', '$location', 'SharedServices', 'MessageService', 'CollectionBase', 'SpinnerService', 'slalom.browser.CookieManager', 'ErrorModal', "ConfirmModal", "ValidatorHelper",
        function ($scope, $modal, $filter, $location, services, messageService, CollectionBaseFactory, SpinnerService, CookieManager, ErrorModal, ConfirmModal, ValidatorHelper) {
            
            $scope.displayBackToSerachButton = false;
            if (!CookieManager.getCookie('associateId') || !CookieManager.getCookie('firstName') || !CookieManager.getCookie('lastName') || !CookieManager.getCookie('jobTitle')) {
            	$scope.displayBackToSerachButton = true;
            	document.getElementById("subnav1").style.display = 'none'; 
            } else {
            	document.getElementById("subnav1").style.display = 'block'; 
            }
            
            $scope.associateId = CookieManager.getCookie('associateId');
        	$scope.firstName = CookieManager.getCookie('firstName');
        	$scope.lastName = CookieManager.getCookie('lastName');
        	$scope.jobTitle = CookieManager.getCookie('jobTitle');
                    	 
            /**
            * Model instance
            */
            $scope.model = null;
            
            // Select Associate button    
     	    $scope.selectAssociate = function () {
     	    	$location.path("/select");
     		};
           	
            $scope.onPrint = function () {
            	if (!$scope.reportSelection || $scope.reportSelection === "0") {
                    ErrorModal.show("Report name is required.");
                    return false;
                } else {
                	var includeRating = false;
                	if (document.getElementById("IncludeRatings").checked == true)
                		includeRating = true;
                	document.getElementById("exportToExcelInput").value=services.getExportToExcel($scope.reportSelection, includeRating);
                	document.excelForm.submit();
                	return true;
                }
            };
            
            
          
        } ]);
});
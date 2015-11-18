/**
* @memberOf app
* @class JobsController ViewModel for the landing Job List view.
* @constructor
*/
define("controllers/LanguageController", ['app'], function (app) {
    app.controller('LanguageController', ['$scope', '$modal', '$filter', '$location', 'SharedServices', 'MessageService', 'CollectionBase', 'SpinnerService', 'slalom.browser.CookieManager', 'ErrorModal', "ConfirmModal", "ValidatorHelper",
        function ($scope, $modal, $filter, $location, services, messageService, CollectionBaseFactory, SpinnerService, CookieManager, ErrorModal, ConfirmModal, ValidatorHelper) {
            var spinnerField = "languageSpinner";
 
            document.getElementById("subnav1").style.display = 'block';
            
            // Update message banner
            messageService.clearMessage();
            messageService.addMessage("Show languages");

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
            * Error message to display
            * @type {string}
            */
            $scope.errorMessage = null;
            
            $scope.language = new CollectionBaseFactory();
            $scope.language.items = [{}];
            
            $scope.loadlanguage = function () {
            services.getLanguageInformation(CookieManager.getCookie('associateId'))
            .then(function (data) {
                $scope.language = data.languageDetailsDTO; 
                
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
            $scope.loadlanguage();
            
            // Prev Buton
            $scope.prevAssociate = function () {
           	 services.getPageInfo(true, false);        	 
                $scope.loadlanguage();
            };
            
            // Next Button
            $scope.nextAssociate = function () {
           	 services.getPageInfo(false, true);        	 
                $scope.loadlanguage();
            };
            
            // Select button    
     	    $scope.selectAssociate = function () {
     	    	$location.path("/select");
     		};
           
        } ]);
});
/**
* @memberOf app
* @class AddressController ViewModel
* @constructor
*/
define("controllers/AddressController", ['app'], function (app) {
    app.controller('AddressController', ['$scope', '$modal', '$filter', '$location', 'SharedServices', 'MessageService', 'CollectionBase', 'SpinnerService', 'slalom.browser.CookieManager', 'ErrorModal', "ConfirmModal", "ValidatorHelper",
        function ($scope, $modal, $filter, $location, services, messageService, CollectionBaseFactory, SpinnerService, CookieManager, ErrorModal, ConfirmModal, ValidatorHelper) {
           
    	    var spinnerField = "addressSpinner";
 
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
            
            // Update message banner
            messageService.clearMessage();
            messageService.addMessage("Show addresses");

            /**
            * Error message to display
            * @type {string}
            */
            $scope.errorMessage = null;
            
            $scope.address = new CollectionBaseFactory();
            $scope.address.items = [{}];
            
            $scope.loadAddress = function () {
            // Fetch the values for the supplied applicant
            SpinnerService.show(spinnerField);
             services.getAddressInformation(CookieManager.getCookie('associateId'))
                 .then(function (data) {
                     $scope.item = data;
                     
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
            $scope.loadAddress();
             
             // Prev Buton
             $scope.prevAssociate = function () {
            	 services.getPageInfo(true, false);        	 
                 $scope.loadAddress();
             };
             
             // Next Button
             $scope.nextAssociate = function () {
            	 services.getPageInfo(false, true);        	 
                 $scope.loadAddress();
             };
             
             // Select button    
      	    $scope.selectAssociate = function () {
      	    	$location.path("/select");
      		};
            
        } ]);
});
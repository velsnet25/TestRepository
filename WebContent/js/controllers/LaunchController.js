/*jslint browser: true*/
/*globals define, app, $, confirm*/
/**
* @memberOf app
* @class JobsController ViewModel for the landing Job List view.
* @constructor
*/
define("controllers/LaunchController", ['app'], function (app) {
    app.controller('LaunchController', ['$scope', '$modal', '$filter', '$location', 'SharedServices', 'MessageService', 'CollectionBase', 'SpinnerService', 'slalom.browser.CookieManager', 'ErrorModal', "ConfirmModal", "ValidatorHelper",
        function ($scope, $modal, $filter, $location, services, messageService, CollectionBaseFactory, SpinnerService, CookieManager, ErrorModal, ConfirmModal, ValidatorHelper) {
            
    	document.getElementById("subnav1").style.display = 'none';
            
            $scope.closeWindow = function()
			{
				if(window.opener == null)
				{
					window.opener = window.self;
				} // end if(window.opener == null)
				
				window.close();
			}; // end function closeWindow()
            
            // Update message banner
            messageService.clearMessage();
            messageService.addMessage("Confidentiality Agreement");

             /*$scope.ShowFind = function () {
                 $location.path("/find");
             }*/;
             
             $scope.ShowMain = function () {
                 CookieManager.setCookieJSON('agree', "true");
                 $location.path("/main");
             };
             $scope.ShowQuit = function () {
                 CookieManager.setCookieJSON('quit', "true");
                 $location.path("/quit");
             };
             /*$scope.navFind = function () {
                 $location.path('/find/');
             };
             
             $scope.navSearch = function () {
                 $location.path('/search/');
             };
             
             $scope.navReporting = function () {
                 $location.path('/reporting/');
             };
             
             $scope.navSecurity = function () {
                 $location.path('/security/');
             };*/
        } ]);
});
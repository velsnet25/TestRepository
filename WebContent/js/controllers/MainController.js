/*jslint browser: true*/
/*globals define, app, $, confirm, moment*/
/**
 * @memberOf app
 * @class MainController ViewModel for the master page view.
 * @constructor
 */
define("controllers/MainController", ['app'], function (app) {
    app.controller('MainController', ['$scope', '$location', 'SharedServices', 'MessageService', 'slalom.browser.CookieManager',
        function ($scope, $location, services, MessageService, CookieManager) {

            // Get the current store from the cookie if set. Should persist for 14 days per URD.
    		document.getElementById("subnav1").style.display = 'none';
            var currentStore = CookieManager.getCookie('agree');
            if (!currentStore) {
                $location.path("/agree");
            } 
                         	

            // Fields
            $scope.messages = [];
            if ($scope.main === undefined) {
                services.getAccessInfo()
                    .then(function (data) {
                        $scope.main = {
                            uiLoadingIndicator: 0,
                            tagLine: "ManageRequisitions",
                            user: data
                        };
                        if (currentStore) {
                            $scope.main.user.location = currentStore;
                            $scope.main.storeName = CookieManager.getCookie('currentStoreName');
                        }
                        app.user = $scope.main.user;
                    });
            }

            $scope.navFind = function () {
                $location.path('/find/');
            };
            
            $scope.navSearch = function () {
                $location.path('/search/');
            };
            $scope.navBasicInfo = function () {
                $location.path('/basicInfo/');
            };
            $scope.navWorkHistory = function () {
                $location.path('/workHistory/');
            };
            $scope.navRating = function () {
                $location.path('/rating/');
            };
            $scope.navNextPosition = function () {
                $location.path('/nextPosition/');
            };
            $scope.navReport1 = function () {
                $location.path('/report1/');
            };
            $scope.navReport2 = function () {
                $location.path('/report2/');
            };
            $scope.navReport3 = function () {
                $location.path('/report3/');
            };
            
            $scope.getWorkHistory = function () {
            	$location.path('/workHistory/');
            };
            
            $scope.navAddress = function () {
            	$location.path('/address/');
            };
            
            $scope.navEducation = function () {
            	$location.path('/education/');
            };
            
            $scope.navLanguage = function () {
            	$location.path('/language/');
            };
            
            $scope.navSecurity = function () {
            	$location.path('/security/');
            };
            
            $scope.ShowMain = function () {
                $location.path("/main/");
            };
            
            $scope.goBack = function () {
                window.history.back();
            };
            
            $scope.navReport = function () {
                $location.path('/report/');
            };
            
        }]);
});
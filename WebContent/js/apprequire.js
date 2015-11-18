/*jslint browser: true*/
/*globals define, app, $, confirm*/

/**
 * Configuration for require paths and module dependencies
 * @type {String}
 */
require.config({
    baseUrl : 'js/libs',
    paths : {
        auth : '../auth',
        controllers : '../controllers',
        common : '../common',
        services : '../services',
        //services : '../testServices',
        filters : '../filters',
        apprequire : '../app.require',
        app : '../app',
        jquery : 'jquery-1.11.0.min',
        jqueryUi: 'jquery-ui-1.10.4.min',
        angular: 'angular.min',
        angularroute: 'angular-route.min',
        uibootstrap: 'ui-bootstrap-tpls-0.10.0'
        //jasmine: '../test/jasmine-2.0.0/jasmine'
    },
    shim : {
        'angular' : {
            deps : [ 'jquery' ],
            exports : 'angular'
        },
        'angularroute': ['angular'],
        'angular-resource.min' : [ 'angular' ],
        'angular-sanitize.min' : [ 'angular' ],
        'uibootstrap' : [ 'angular' ],

        'jquery.cookie' : ['jquery'],
        'common/angular-slalom' : [ 'angular', 'jquery.cookie' ],
        'common/common.services' : [ 'common/angular-slalom' ],
        'common/common.templates' : [ 'common/common.services' ],
        'common/common' : [ 'common/common.templates' ],

        'common/d3' : [ 'angular' ],

        // App specific routes
        'auth/auth.services' : [ 'common/common' ],
        'auth/auth.templates' : [ 'auth/auth.services' ],
        'auth/auth' : [ 'auth/auth.templates' ],

        'app' : ['angular', 'angularroute', 'common/angular-slalom'],
        'controllers/JobsController': ['jqueryUi', 'app'],
        'filters': ['angular']
    }
});

/**
 * Bootstrap app with angular routing and require library dependencies
 * @param  {object} angular AngularJS
 * @param  {object} app)        App instance global value
 */
require([
    'angular',
    'app',
    'angularroute',
    'common/angular-slalom',
    'controllers/BasicInfoController',
    'controllers/ReportingController',
    'controllers/MainController',
    'controllers/AddressController',
    'controllers/EducationController',
    'controllers/FindController',
    'controllers/LanguageController',
    'controllers/LaunchController',
    'controllers/WorkController',
    'controllers/RatingController',
    'controllers/NextPositionController',
    'controllers/SearchController',
    'controllers/AssociateProfileController',
    'controllers/DevelopmentAndSummaryController',
    'controllers/SelectAssociateController',
    'uibootstrap',
], function (angular, app) {
    angular.element(document).ready(function () {
        app.config(['$routeProvider', function ($routeProvider) {
            $routeProvider.when('/main', { templateUrl: 'Templates/MainPage.html', controller: 'LaunchController' });
            $routeProvider.when('/agree', { templateUrl: 'Templates/LaunchApplication.html', controller: 'LaunchController' });
            $routeProvider.when('/quit', { templateUrl: 'Templates/quit.html', controller: 'LaunchController' });
            $routeProvider.when('/find', { templateUrl: 'Templates/find.html', controller: 'FindController' });
            $routeProvider.when('/search', { templateUrl: 'Templates/search.html', controller: 'SearchController' });
            $routeProvider.when('/basicInfo', {templateUrl: 'Templates/BasicInfo.html', controller: 'BasicInfoController'});
            $routeProvider.when('/education', {templateUrl: 'Templates/Education.html', controller: 'EducationController'});
            $routeProvider.when('/language', {templateUrl: 'Templates/Language.html', controller: 'LanguageController'});
            $routeProvider.when('/address', {templateUrl: 'Templates/Address.html', controller: 'AddressController'});
            $routeProvider.when('/workHistory', {templateUrl: 'Templates/WorkHistory.html', controller: 'WorkController'});
            $routeProvider.when('/rating', {templateUrl: 'Templates/Rating.html', controller: 'RatingController'});
            $routeProvider.when('/nextPosition', {templateUrl: 'Templates/NextPosition.html', controller: 'NextPositionController'});
            $routeProvider.when('/report',  { templateUrl: 'Templates/reporting.html', controller: 'ReportingController' });
            $routeProvider.when('/report1', { templateUrl: 'Templates/AssociateProfile.html', controller: 'AssociateProfileController' });
            $routeProvider.when('/report2', { templateUrl: 'Templates/ExportToExcel.html', controller: 'ReportingController' });
            $routeProvider.when('/report3', { templateUrl: 'Templates/ElongatedForm.html', controller: 'DevelopmentAndSummaryController' });
            $routeProvider.when('/select', { templateUrl: 'Templates/SelectAssociate.html', controller: 'SelectAssociateController' });
            $routeProvider.otherwise({ redirectTo: '/agree' });
        } ]);

        angular.bootstrap(document, ['app']);
    });
});
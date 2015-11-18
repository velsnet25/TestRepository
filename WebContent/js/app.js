/*jslint browser: true*/
/*globals define, app, $, confirm*/

/**
 * app global variable including require dependencies for the solution
 * @param  {class} angular
 * @return {class}  app module
 */
define("app", ['angular', 'angularroute', 'uibootstrap', 'common/angular-slalom', 'services', 'moment', 'filters'],
    function (angular) {
        'use strict';

        var app = angular.module('app', ['ngRoute', 'ui.bootstrap', 'app.services', 'commonFilters', 'slalom.common.browser', 'slalom.common.notifications', 'slalom.common.modals', 'slalom.common.collections', 'slalom.common.forms', 'slalom.common.errors']);

        return app;
    });

/**
 * addDays method extending native javascript Date type.
 * @param {number} days Number of days to add
 */
Date.prototype.addDays = function (days) {
    var dat = new Date(this.valueOf());
    dat.setDate(dat.getDate() + days);
    return dat;
};
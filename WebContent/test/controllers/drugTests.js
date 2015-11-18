/*jslint browser: true*/
/*globals define, app, $, confirm, alert, describe, beforeEach, module, inject, it, expect*/
describe('app', function () {

    // Bootstrap app
    
    beforeEach(function () {
        
        module('ngRoute');
        module('ui.bootstrap');
        module('commonFilters');
        module('slalom.common.browser');
        module('slalom.common.notifications');
        module('slalom.common.modals');
        module('slalom.common.collections');
        module('slalom.common.forms');
        module('slalom.common.errors', function ($provide) {
            var modalMock = {};
            $provide.value('ModalService', modalMock);
        });
        module('app.services', function ($provide) {
            var modalMock = {};
            $provide.value('ModalService', modalMock);
        });

        // module('app', function ($provide) {
        //     var modalMock = {};
        //     $provide.value('ModalService', modalMock);
        // });
    });

    describe('controllers', function () {
        //beforeEach(module('app'));
        describe('DrugTestController', function () {
            //beforeEach(function () {

                //module('app');
                // module('app.services', function ($provide) {
                //     var modalMock = {};
                //     $provide.value('ModalService', modalMock);
                // });

                // inject(function (SharedServices) {

                // })
            //});

            it('should populate test data for applicant',
                inject(function ($rootScope, $controller) {
                    var scope = $rootScope.$new();
            //        var ctrl = $controller('DrugTestController', { $scope: scope });
            //        expect(scope.model).not.toBeNull();
                }));
        });
    });
});
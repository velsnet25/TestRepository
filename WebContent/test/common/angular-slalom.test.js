/*jslint browser: true*/
/*globals it, describe, beforeEach, inject, afterEach, expect, spyOn, jasmine, $provide*/

describe('Slalom Common Errors', function () {
    beforeEach(module('slalom.common.modals'));
    beforeEach(module('slalom.common.errors', function ($provide) {
        var ModalServiceMock = { };

        $provide.value('ModalService', ModalServiceMock);

    }));
    describe('ErrorModal', function () {
        var modal;
        beforeEach(inject(function (ErrorModal) {
            modal = ErrorModal;
        }));
        it('should show system error', function () {
            spyOn(modal, 'show');
            modal.showSystemError();
            expect(modal.show).toHaveBeenCalledWith("A system error has occurred. Please contact the help desk for support.");
        });
    });
});

describe('Slalom Common DOM', function () {
    //Load the slalom.common.dom module
    beforeEach(module('slalom.common.dom'));
    //Describe the ngVisible directive 
    describe('ngVisible', function () {
        var html,
            elem,
            compiled,
            scope;
        //Build out the test template
        html = '<div id="testVis" ng-visible="true">' +
                'Test Content Visible' +
            '</div>' +
            '<div id="testNotVis" ng-visible="false">' +
                'Test Content Not Visible' +
            '</div>';
        //inject scope and compile the template
        beforeEach(inject(function ($rootScope, $compile) {
            //Create a new child scope from $rootScope
            scope = $rootScope.$new();
            //Create an angular element 
            elem = angular.element(html);
            //Manually compile the template
            compiled = $compile(elem)(scope);
            //For this test we have to attach to the body since we are testing visibility
            $("body").append(compiled[0]);
            //Manually trigger a digest
            scope.$digest();
        }));
        //Test invisible
        it('should be invisible', function () {
            expect(compiled[1]).not.toBeVisible();
        });
        //Test visible
        it('should be visible', function () {
            expect(compiled[0]).toBeVisible();
        });
    });
    // 
    describe('inputPatternFilter', function () {
        var html,
            elem,
            //compiled,
            scope;
        //Build out the test template
        html = '<div>' +
                '<ng-form name="testForm">' +
                    '<input name="patternTest" ng-model="model.patternTest" input-pattern-filter type = "text" ng-pattern="pattern"/>' +
                '</ng-form>' +
            '</div>';
        //inject scope and compile the template
        beforeEach(inject(function ($rootScope, $compile) {
            scope = $rootScope.$new();
            elem = angular.element(html);
            scope.model = {
                patternTest: ''
            };
            scope.pattern = /[a-zA-Z]+/;
            //compiled = 
            $compile(elem)(scope);
            scope.$digest();
        }));
        //Test invisible
        it('should remove numbers', function () {
            scope.testForm.patternTest.$setViewValue('1233abc');
            expect(scope.model.patternTest).toEqual('abc');
            scope.testForm.patternTest.$setViewValue('123');
            expect(scope.model.patternTest).toEqual('');
        });
    });
    //TODO this test isnt working right.  Not covering the whole function
    describe('localtime', function () {
        var dateFilter,
            localTimeFilter,
            dateTime;

        // load filter function into variable
        beforeEach(inject(function ($filter) {
            dateFilter = $filter('date');
            localTimeFilter = $filter('localtime');
            dateTime = '2013-01-31T12:34:56';
        }));
        //Test invisible
        it('should undo angulars default local time shifting', function () {
            expect(localTimeFilter(dateFilter(dateTime, "yyyy-MM-ddTHH:mm:ss.000Z"))).toEqual(dateFilter(dateTime, "yyyy-MM-ddTHH:mm:ss.000Z"));
        });
    });
    //TODO this test isnt working right.  Not covering the whole function
    describe('unsafe', function () {
        var unsafeFilter,
            unsafeString;

        // load filter function into variable
        beforeEach(inject(function ($filter) {
            unsafeFilter = $filter('unsafe');
            unsafeString = 'Test </br> <code>test</code>';
        }));
        //Test invisible
        it('should undo angulars default local time shifting', function () {
            expect(unsafeFilter(unsafeString).$$unwrapTrustedValue()).toEqual(unsafeString);
        });
    });
});



describe('Slalom Common Forms', function () {
    //Load the slalom.common.forms module
    beforeEach(module('slalom.common.forms'));
    //Describe the CommonValidation Methods Factory
    describe('CommonValidationMethods', function () {
        //Describe the int method
        describe('int matcher', function () {
            //Test passing a string numeric string
            it('should return an integer if you pass it a number in a string', inject(function (CommonValidationMethods) {
                expect(CommonValidationMethods.int('3')).toEqual(3);
            }));
            //Test passing a string an integer
            it('should return an integer if you pass it an integer', inject(function (CommonValidationMethods) {
                expect(CommonValidationMethods.int(3)).toEqual(3);
                expect(CommonValidationMethods.int(4)).not.toEqual(3);
            }));
            //Test passing a text string.  
            it('should return NaN if you pass a text string, object or array', inject(function (CommonValidationMethods) {
                expect(CommonValidationMethods.int('abc')).toBeNaN();
                expect(CommonValidationMethods.int({ test: 1 })).toBeNaN();
                expect(CommonValidationMethods.int(['test', 'test2'])).toBeNaN();
            }));
            //Test passing a decimal.
            it('should return a rounded (floor) integer if you pass it an integer', inject(function (CommonValidationMethods) {
                expect(CommonValidationMethods.int(4.9)).toEqual(4);
            }));
        });
        //Describe the isEmpty method
        describe('isEmpty matcher', function () {
            //Test passing a string numeric string
            it('should return false if you pass it any value', inject(function (CommonValidationMethods) {
                expect(CommonValidationMethods.isEmpty('3')).toBe(false);
                expect(CommonValidationMethods.isEmpty(3)).toBe(false);
                expect(CommonValidationMethods.isEmpty({ test: 1 })).toBe(false);
                expect(CommonValidationMethods.isEmpty(['test', 'test2'])).toBe(false);
            }));
            it('should return true if you pass it an empty string', inject(function (CommonValidationMethods) {
                expect(CommonValidationMethods.isEmpty('')).toBe(true);
            }));
            it('should return true if you pass it null', inject(function (CommonValidationMethods) {
                expect(CommonValidationMethods.isEmpty(null)).toBe(true);
            }));
            it('should return true if you pass it nothing (undefined)', inject(function (CommonValidationMethods) {
                expect(CommonValidationMethods.isEmpty()).toBe(true);
            }));
        });
    });
    describe('GlobalFormStateService', function () {
        //describe('getDirtyForm', function(){
        //  it('should return false if the global form is pristine', inject(function(GlobalFormStateService){
        //      expect( GlobalFormStateService.getDirtyForm() ).toBe(false);
        //  }));
        //});
        describe('setDirtyForm', function () {
            it('should set dirtyForm to true', inject(function (GlobalFormStateService) {
                GlobalFormStateService.setDirtyForm(true);
                expect(GlobalFormStateService.getDirtyForm()).toBe(true);
            }));
        });
    });
});

describe('Slalom Common Collections', function () {
    //Load the slalom.common.forms module
    beforeEach(module('slalom.common.collections'));

    describe('CollectionBase Tests', function () {
        var base;
        beforeEach(inject(function (CollectionBase) {
            base = new CollectionBase();
            base.items = [1, 2, 3, 4, 5];
        }));

        it('should deselect currentItem when paging changed', function () {
            base.currentItem = base.items[2];
            expect(base.currentItem).toEqual(3);
            base.goToPage(2);
            expect(base.currentItem).toBeNull();
        });
    });

    //Describe the CommonValidation Methods Factory
    describe('PagingFilter', function () {

        var pagingFilter;
        beforeEach(inject(function (_pagingFilterFilter_) {
            pagingFilter = _pagingFilterFilter_;
        }));

        it('should select array subset if more records found than page size', function () {
            var source = [1, 2, 3, 4, 5];
            var pageSize = 2,
                currentPage = 2;
            var result = pagingFilter(source, pageSize, currentPage);
            expect(result.length).toBe(2);
            expect(result[0]).toBe(3);
        });
        it('should select remaining records in paged results if insufficient records exist', function () {
            var source = [1, 2, 3, 4, 5],
                pageSize = 2,
                currentPage = 3;
            var result = pagingFilter(source, pageSize, currentPage);
            expect(result.length).toBe(1);
            expect(result[0]).toBe(5);
        });
    });

    describe('ValidatorHelper', function () {
        var validatorHelper;
        var testObj = {val1: "1", val2: "2"};

        beforeEach(inject(function (ValidatorHelper) {
            validatorHelper = new ValidatorHelper(testObj);
            validatorHelper.rules = [
                {fieldName: 'val1', message: 'NumbersOnly', validator: function (item) { return item.val1.match(/^[0-9]*$/); }},
                {fieldName: 'val2', message: 'Required', validator: function (item) { return item.val2; }},
                {fieldName: 'val1', message: 'InRange', validator: function (item) {return item.val1 > 0 && item.val1 < 2; }},
                {fieldName: 'val2', message: 'Must be 9 digits', validator: function (item) { return item.val2.match(/^[0-9]{9}$/); }},
            ];
        }));

        it('should be valid when all rules met', function () {
            testObj.val1 = "1";
            testObj.val2 = "123456789";
            expect(validatorHelper.isValid()).toBe(true);
        });

        it('should be invalid if one rule fails', function () {
            testObj.val2 = "a";
            expect(validatorHelper.isValid()).toBe(false);
        });

        it('should return message for invalid field', function () {
            testObj.val1 = "a";
            expect(validatorHelper.fieldValidMessage('val1')).toEqual('NumbersOnly');
        });

        it('should indicate field invalid', function () {
            testObj.val1 = "a";
            expect(validatorHelper.fieldValid('val1')).toEqual(false);
        });

        it('should indicate field valid', function () {
            testObj.val1 = "1";
            expect(validatorHelper.fieldValid('val1')).toEqual(true);
        });

        it('should indicate field invalid when out of range', function () {
            testObj.val1 = "0";
            expect(validatorHelper.fieldValid('val1')).toEqual(false);
            testObj.val1 = "2";
            expect(validatorHelper.fieldValid('val1')).toEqual(false);
        });
    });

});
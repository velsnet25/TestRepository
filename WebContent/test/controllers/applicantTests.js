describe('app', function () {
    describe('controllers', function () {
        beforeEach(module('app'));
        describe('ApplicantController', function () {
            it('should populate test data for applicant',
                inject(function ($rootScope, $controller) {
                    var scope = $rootScope.$new();
                    var ctrl = $controller('ApplicantController', { $scope: scope });
                    expect(scope.model).not.toBeNull();
                })
            ); 
        });
    });
});
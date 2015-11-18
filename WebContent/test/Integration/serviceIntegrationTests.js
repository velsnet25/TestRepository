describe('services', function () {
    var services;
    var $httpBackend;

    //Load the commonFilters module
    beforeEach(module('app.services'));
    beforeEach(inject(function (_SharedServices_) {
        services = _SharedServices_;
    }));

    afterEach(function () {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });

    //checkmark filter tests
    describe('services', function () {
        it('should fetch access info for current user', function () {
            services.getAccessInfo()
            .then(function (user) {
                expect(user.location).toBe('9100');
            });
            $httpBackend.flush();
        });

    });

});
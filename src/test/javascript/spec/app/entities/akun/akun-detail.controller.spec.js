'use strict';

describe('Controller Tests', function() {

    describe('Akun Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockAkun;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockAkun = jasmine.createSpy('MockAkun');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Akun': MockAkun
            };
            createController = function() {
                $injector.get('$controller')("AkunDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'raBbvetApp:akunUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

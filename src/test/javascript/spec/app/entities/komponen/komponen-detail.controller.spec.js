'use strict';

describe('Controller Tests', function() {

    describe('Komponen Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockKomponen, MockOutput;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockKomponen = jasmine.createSpy('MockKomponen');
            MockOutput = jasmine.createSpy('MockOutput');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Komponen': MockKomponen,
                'Output': MockOutput
            };
            createController = function() {
                $injector.get('$controller')("KomponenDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'raBbvetApp:komponenUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

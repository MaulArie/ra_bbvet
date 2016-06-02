'use strict';

describe('Controller Tests', function() {

    describe('Subkomponen Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSubkomponen, MockKomponen;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSubkomponen = jasmine.createSpy('MockSubkomponen');
            MockKomponen = jasmine.createSpy('MockKomponen');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Subkomponen': MockSubkomponen,
                'Komponen': MockKomponen
            };
            createController = function() {
                $injector.get('$controller')("SubkomponenDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'raBbvetApp:subkomponenUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

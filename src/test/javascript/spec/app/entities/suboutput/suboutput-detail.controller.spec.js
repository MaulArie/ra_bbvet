'use strict';

describe('Controller Tests', function() {

    describe('Suboutput Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSuboutput, MockOutput, MockKegiatan;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSuboutput = jasmine.createSpy('MockSuboutput');
            MockOutput = jasmine.createSpy('MockOutput');
            MockKegiatan = jasmine.createSpy('MockKegiatan');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Suboutput': MockSuboutput,
                'Output': MockOutput,
                'Kegiatan': MockKegiatan
            };
            createController = function() {
                $injector.get('$controller')("SuboutputDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'raBbvetApp:suboutputUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

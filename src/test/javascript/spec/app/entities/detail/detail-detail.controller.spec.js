'use strict';

describe('Controller Tests', function() {

    describe('Detail Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockDetail, MockProgram, MockKegiatan, MockOutput, MockSuboutput, MockKomponen, MockSubkomponen, MockAkun;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockDetail = jasmine.createSpy('MockDetail');
            MockProgram = jasmine.createSpy('MockProgram');
            MockKegiatan = jasmine.createSpy('MockKegiatan');
            MockOutput = jasmine.createSpy('MockOutput');
            MockSuboutput = jasmine.createSpy('MockSuboutput');
            MockKomponen = jasmine.createSpy('MockKomponen');
            MockSubkomponen = jasmine.createSpy('MockSubkomponen');
            MockAkun = jasmine.createSpy('MockAkun');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Detail': MockDetail,
                'Program': MockProgram,
                'Kegiatan': MockKegiatan,
                'Output': MockOutput,
                'Suboutput': MockSuboutput,
                'Komponen': MockKomponen,
                'Subkomponen': MockSubkomponen,
                'Akun': MockAkun
            };
            createController = function() {
                $injector.get('$controller')("DetailDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'raBbvetApp:detailUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

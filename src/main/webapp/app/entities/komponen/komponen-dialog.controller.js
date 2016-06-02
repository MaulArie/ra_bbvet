(function() {
    'use strict';

    angular
        .module('raBbvetApp')
        .controller('KomponenDialogController', KomponenDialogController);

    KomponenDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Komponen', 'Output'];

    function KomponenDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Komponen, Output) {
        var vm = this;
        vm.komponen = entity;
        vm.outputs = Output.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('raBbvetApp:komponenUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.komponen.id !== null) {
                Komponen.update(vm.komponen, onSaveSuccess, onSaveError);
            } else {
                Komponen.save(vm.komponen, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.komponen_update = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();

(function() {
    'use strict';

    angular
        .module('raBbvetApp')
        .controller('KegiatanDialogController', KegiatanDialogController);

    KegiatanDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Kegiatan', 'Program'];

    function KegiatanDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Kegiatan, Program) {
        var vm = this;
        vm.kegiatan = entity;
        vm.programs = Program.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('raBbvetApp:kegiatanUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.kegiatan.id !== null) {
                Kegiatan.update(vm.kegiatan, onSaveSuccess, onSaveError);
            } else {
                Kegiatan.save(vm.kegiatan, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.kegiatan_update = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();

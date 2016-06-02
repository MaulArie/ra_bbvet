(function() {
    'use strict';

    angular
        .module('raBbvetApp')
        .controller('OutputDialogController', OutputDialogController);

    OutputDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Output', 'Kegiatan'];

    function OutputDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Output, Kegiatan) {
        var vm = this;
        vm.output = entity;
        vm.kegiatans = Kegiatan.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('raBbvetApp:outputUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.output.id !== null) {
                Output.update(vm.output, onSaveSuccess, onSaveError);
            } else {
                Output.save(vm.output, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.output_update = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();

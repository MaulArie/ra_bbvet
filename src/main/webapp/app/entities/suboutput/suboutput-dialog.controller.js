(function() {
    'use strict';

    angular
        .module('raBbvetApp')
        .controller('SuboutputDialogController', SuboutputDialogController);

    SuboutputDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Suboutput', 'Output', 'Kegiatan'];

    function SuboutputDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Suboutput, Output, Kegiatan) {
        var vm = this;
        vm.suboutput = entity;
        vm.outputs = Output.query();
        vm.kegiatans = Kegiatan.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('raBbvetApp:suboutputUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.suboutput.id !== null) {
                Suboutput.update(vm.suboutput, onSaveSuccess, onSaveError);
            } else {
                Suboutput.save(vm.suboutput, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.suboutput_update = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();

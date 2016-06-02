(function() {
    'use strict';

    angular
        .module('raBbvetApp')
        .controller('ProgramDialogController', ProgramDialogController);

    ProgramDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Program'];

    function ProgramDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Program) {
        var vm = this;
        vm.program = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('raBbvetApp:programUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.program.id !== null) {
                Program.update(vm.program, onSaveSuccess, onSaveError);
            } else {
                Program.save(vm.program, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.program_update = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();

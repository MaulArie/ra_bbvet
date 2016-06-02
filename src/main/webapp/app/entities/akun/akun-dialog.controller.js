(function() {
    'use strict';

    angular
        .module('raBbvetApp')
        .controller('AkunDialogController', AkunDialogController);

    AkunDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Akun'];

    function AkunDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Akun) {
        var vm = this;
        vm.akun = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('raBbvetApp:akunUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.akun.id !== null) {
                Akun.update(vm.akun, onSaveSuccess, onSaveError);
            } else {
                Akun.save(vm.akun, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.akun_update = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();

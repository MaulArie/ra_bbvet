(function() {
    'use strict';

    angular
        .module('raBbvetApp')
        .controller('SubkomponenDialogController', SubkomponenDialogController);

    SubkomponenDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Subkomponen', 'Komponen'];

    function SubkomponenDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Subkomponen, Komponen) {
        var vm = this;
        vm.subkomponen = entity;
        vm.komponens = Komponen.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('raBbvetApp:subkomponenUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.subkomponen.id !== null) {
                Subkomponen.update(vm.subkomponen, onSaveSuccess, onSaveError);
            } else {
                Subkomponen.save(vm.subkomponen, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.subkomponen_update = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();

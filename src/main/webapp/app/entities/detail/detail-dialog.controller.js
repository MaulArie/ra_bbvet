(function() {
    'use strict';

    angular
        .module('raBbvetApp')
        .controller('DetailDialogController', DetailDialogController);

    DetailDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Detail', 'Program', 'Kegiatan', 'Output', 'Suboutput', 'Komponen', 'Subkomponen', 'Akun'];

    function DetailDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Detail, Program, Kegiatan, Output, Suboutput, Komponen, Subkomponen, Akun) {
        var vm = this;
        vm.detail = entity;
        vm.programs = Program.query();
        vm.kegiatans = Kegiatan.query();
        vm.outputs = Output.query();
        vm.suboutputs = Suboutput.query();
        vm.komponens = Komponen.query();
        vm.subkomponens = Subkomponen.query();
        vm.akuns = Akun.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('raBbvetApp:detailUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.detail.id !== null) {
                Detail.update(vm.detail, onSaveSuccess, onSaveError);
            } else {
                Detail.save(vm.detail, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();

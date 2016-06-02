(function() {
    'use strict';

    angular
        .module('raBbvetApp')
        .controller('AkunDeleteController',AkunDeleteController);

    AkunDeleteController.$inject = ['$uibModalInstance', 'entity', 'Akun'];

    function AkunDeleteController($uibModalInstance, entity, Akun) {
        var vm = this;
        vm.akun = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Akun.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();

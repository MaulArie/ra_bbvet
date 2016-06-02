(function() {
    'use strict';

    angular
        .module('raBbvetApp')
        .controller('KomponenDeleteController',KomponenDeleteController);

    KomponenDeleteController.$inject = ['$uibModalInstance', 'entity', 'Komponen'];

    function KomponenDeleteController($uibModalInstance, entity, Komponen) {
        var vm = this;
        vm.komponen = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Komponen.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();

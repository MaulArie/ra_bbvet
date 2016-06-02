(function() {
    'use strict';

    angular
        .module('raBbvetApp')
        .controller('SubkomponenDeleteController',SubkomponenDeleteController);

    SubkomponenDeleteController.$inject = ['$uibModalInstance', 'entity', 'Subkomponen'];

    function SubkomponenDeleteController($uibModalInstance, entity, Subkomponen) {
        var vm = this;
        vm.subkomponen = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Subkomponen.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();

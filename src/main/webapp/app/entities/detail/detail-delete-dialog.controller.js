(function() {
    'use strict';

    angular
        .module('raBbvetApp')
        .controller('DetailDeleteController',DetailDeleteController);

    DetailDeleteController.$inject = ['$uibModalInstance', 'entity', 'Detail'];

    function DetailDeleteController($uibModalInstance, entity, Detail) {
        var vm = this;
        vm.detail = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Detail.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();

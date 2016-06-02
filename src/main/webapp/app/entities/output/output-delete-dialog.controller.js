(function() {
    'use strict';

    angular
        .module('raBbvetApp')
        .controller('OutputDeleteController',OutputDeleteController);

    OutputDeleteController.$inject = ['$uibModalInstance', 'entity', 'Output'];

    function OutputDeleteController($uibModalInstance, entity, Output) {
        var vm = this;
        vm.output = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Output.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();

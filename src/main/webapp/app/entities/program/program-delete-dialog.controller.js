(function() {
    'use strict';

    angular
        .module('raBbvetApp')
        .controller('ProgramDeleteController',ProgramDeleteController);

    ProgramDeleteController.$inject = ['$uibModalInstance', 'entity', 'Program'];

    function ProgramDeleteController($uibModalInstance, entity, Program) {
        var vm = this;
        vm.program = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Program.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();

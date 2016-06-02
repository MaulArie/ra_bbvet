(function() {
    'use strict';

    angular
        .module('raBbvetApp')
        .controller('SuboutputDeleteController',SuboutputDeleteController);

    SuboutputDeleteController.$inject = ['$uibModalInstance', 'entity', 'Suboutput'];

    function SuboutputDeleteController($uibModalInstance, entity, Suboutput) {
        var vm = this;
        vm.suboutput = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Suboutput.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();

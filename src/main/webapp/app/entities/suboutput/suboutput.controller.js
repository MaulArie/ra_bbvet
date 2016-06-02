(function() {
    'use strict';

    angular
        .module('raBbvetApp')
        .controller('SuboutputController', SuboutputController);

    SuboutputController.$inject = ['$scope', '$state', 'Suboutput'];

    function SuboutputController ($scope, $state, Suboutput) {
        var vm = this;
        vm.suboutputs = [];
        vm.loadAll = function() {
            Suboutput.query(function(result) {
                vm.suboutputs = result;
            });
        };

        vm.loadAll();
        
    }
})();

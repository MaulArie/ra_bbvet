(function() {
    'use strict';

    angular
        .module('raBbvetApp')
        .controller('OutputController', OutputController);

    OutputController.$inject = ['$scope', '$state', 'Output'];

    function OutputController ($scope, $state, Output) {
        var vm = this;
        vm.outputs = [];
        vm.loadAll = function() {
            Output.query(function(result) {
                vm.outputs = result;
            });
        };

        vm.loadAll();
        
    }
})();

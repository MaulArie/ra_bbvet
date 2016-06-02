(function() {
    'use strict';

    angular
        .module('raBbvetApp')
        .controller('ProgramController', ProgramController);

    ProgramController.$inject = ['$scope', '$state', 'Program'];

    function ProgramController ($scope, $state, Program) {
        var vm = this;
        vm.programs = [];
        vm.loadAll = function() {
            Program.query(function(result) {
                vm.programs = result;
            });
        };

        vm.loadAll();
        
    }
})();

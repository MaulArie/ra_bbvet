(function() {
    'use strict';

    angular
        .module('raBbvetApp')
        .controller('AkunController', AkunController);

    AkunController.$inject = ['$scope', '$state', 'Akun'];

    function AkunController ($scope, $state, Akun) {
        var vm = this;
        vm.akuns = [];
        vm.loadAll = function() {
            Akun.query(function(result) {
                vm.akuns = result;
            });
        };

        vm.loadAll();
        
    }
})();

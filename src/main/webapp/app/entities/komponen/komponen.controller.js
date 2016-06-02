(function() {
    'use strict';

    angular
        .module('raBbvetApp')
        .controller('KomponenController', KomponenController);

    KomponenController.$inject = ['$scope', '$state', 'Komponen'];

    function KomponenController ($scope, $state, Komponen) {
        var vm = this;
        vm.komponens = [];
        vm.loadAll = function() {
            Komponen.query(function(result) {
                vm.komponens = result;
            });
        };

        vm.loadAll();
        
    }
})();

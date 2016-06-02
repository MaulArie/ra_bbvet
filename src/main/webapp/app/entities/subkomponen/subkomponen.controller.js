(function() {
    'use strict';

    angular
        .module('raBbvetApp')
        .controller('SubkomponenController', SubkomponenController);

    SubkomponenController.$inject = ['$scope', '$state', 'Subkomponen'];

    function SubkomponenController ($scope, $state, Subkomponen) {
        var vm = this;
        vm.subkomponens = [];
        vm.loadAll = function() {
            Subkomponen.query(function(result) {
                vm.subkomponens = result;
            });
        };

        vm.loadAll();
        
    }
})();

(function() {
    'use strict';

    angular
        .module('raBbvetApp')
        .controller('DetailController', DetailController);

    DetailController.$inject = ['$scope', '$state', 'Detail'];

    function DetailController ($scope, $state, Detail) {
        var vm = this;
        vm.details = [];
        vm.loadAll = function() {
            Detail.query(function(result) {
                vm.details = result;
            });
        };

        vm.loadAll();
        
    }
})();

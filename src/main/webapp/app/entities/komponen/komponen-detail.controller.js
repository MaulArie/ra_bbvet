(function() {
    'use strict';

    angular
        .module('raBbvetApp')
        .controller('KomponenDetailController', KomponenDetailController);

    KomponenDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Komponen', 'Output'];

    function KomponenDetailController($scope, $rootScope, $stateParams, entity, Komponen, Output) {
        var vm = this;
        vm.komponen = entity;
        
        var unsubscribe = $rootScope.$on('raBbvetApp:komponenUpdate', function(event, result) {
            vm.komponen = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();

(function() {
    'use strict';

    angular
        .module('raBbvetApp')
        .controller('AkunDetailController', AkunDetailController);

    AkunDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Akun'];

    function AkunDetailController($scope, $rootScope, $stateParams, entity, Akun) {
        var vm = this;
        vm.akun = entity;
        
        var unsubscribe = $rootScope.$on('raBbvetApp:akunUpdate', function(event, result) {
            vm.akun = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();

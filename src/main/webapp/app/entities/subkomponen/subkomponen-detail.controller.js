(function() {
    'use strict';

    angular
        .module('raBbvetApp')
        .controller('SubkomponenDetailController', SubkomponenDetailController);

    SubkomponenDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Subkomponen', 'Komponen'];

    function SubkomponenDetailController($scope, $rootScope, $stateParams, entity, Subkomponen, Komponen) {
        var vm = this;
        vm.subkomponen = entity;
        
        var unsubscribe = $rootScope.$on('raBbvetApp:subkomponenUpdate', function(event, result) {
            vm.subkomponen = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();

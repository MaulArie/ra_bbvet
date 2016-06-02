(function() {
    'use strict';

    angular
        .module('raBbvetApp')
        .controller('OutputDetailController', OutputDetailController);

    OutputDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Output', 'Kegiatan'];

    function OutputDetailController($scope, $rootScope, $stateParams, entity, Output, Kegiatan) {
        var vm = this;
        vm.output = entity;
        
        var unsubscribe = $rootScope.$on('raBbvetApp:outputUpdate', function(event, result) {
            vm.output = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();

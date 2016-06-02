(function() {
    'use strict';

    angular
        .module('raBbvetApp')
        .controller('ProgramDetailController', ProgramDetailController);

    ProgramDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Program'];

    function ProgramDetailController($scope, $rootScope, $stateParams, entity, Program) {
        var vm = this;
        vm.program = entity;
        
        var unsubscribe = $rootScope.$on('raBbvetApp:programUpdate', function(event, result) {
            vm.program = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();

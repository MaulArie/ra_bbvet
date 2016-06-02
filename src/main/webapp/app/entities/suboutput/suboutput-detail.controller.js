(function() {
    'use strict';

    angular
        .module('raBbvetApp')
        .controller('SuboutputDetailController', SuboutputDetailController);

    SuboutputDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Suboutput', 'Output', 'Kegiatan'];

    function SuboutputDetailController($scope, $rootScope, $stateParams, entity, Suboutput, Output, Kegiatan) {
        var vm = this;
        vm.suboutput = entity;
        
        var unsubscribe = $rootScope.$on('raBbvetApp:suboutputUpdate', function(event, result) {
            vm.suboutput = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();

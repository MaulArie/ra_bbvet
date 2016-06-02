(function() {
    'use strict';

    angular
        .module('raBbvetApp')
        .controller('DetailDetailController', DetailDetailController);

    DetailDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Detail', 'Program', 'Kegiatan', 'Output', 'Suboutput', 'Komponen', 'Subkomponen', 'Akun'];

    function DetailDetailController($scope, $rootScope, $stateParams, entity, Detail, Program, Kegiatan, Output, Suboutput, Komponen, Subkomponen, Akun) {
        var vm = this;
        vm.detail = entity;
        
        var unsubscribe = $rootScope.$on('raBbvetApp:detailUpdate', function(event, result) {
            vm.detail = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();

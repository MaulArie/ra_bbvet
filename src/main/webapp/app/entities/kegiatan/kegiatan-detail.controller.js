(function() {
    'use strict';

    angular
        .module('raBbvetApp')
        .controller('KegiatanDetailController', KegiatanDetailController);

    KegiatanDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Kegiatan', 'Program'];

    function KegiatanDetailController($scope, $rootScope, $stateParams, entity, Kegiatan, Program) {
        var vm = this;
        vm.kegiatan = entity;
        
        var unsubscribe = $rootScope.$on('raBbvetApp:kegiatanUpdate', function(event, result) {
            vm.kegiatan = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();

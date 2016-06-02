(function() {
    'use strict';

    angular
        .module('raBbvetApp')
        .controller('KegiatanController', KegiatanController);

    KegiatanController.$inject = ['$scope', '$state', 'Kegiatan'];

    function KegiatanController ($scope, $state, Kegiatan) {
        var vm = this;
        vm.kegiatans = [];
        vm.loadAll = function() {
            Kegiatan.query(function(result) {
                vm.kegiatans = result;
            });
        };

        vm.loadAll();
        
    }
})();

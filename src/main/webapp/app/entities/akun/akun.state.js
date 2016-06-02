(function() {
    'use strict';

    angular
        .module('raBbvetApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('akun', {
            parent: 'entity',
            url: '/akun',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Akuns'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/akun/akuns.html',
                    controller: 'AkunController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('akun-detail', {
            parent: 'entity',
            url: '/akun/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Akun'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/akun/akun-detail.html',
                    controller: 'AkunDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Akun', function($stateParams, Akun) {
                    return Akun.get({id : $stateParams.id});
                }]
            }
        })
        .state('akun.new', {
            parent: 'akun',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/akun/akun-dialog.html',
                    controller: 'AkunDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                kd_akun: null,
                                akun: null,
                                biaya_akun: null,
                                akun_update: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('akun', null, { reload: true });
                }, function() {
                    $state.go('akun');
                });
            }]
        })
        .state('akun.edit', {
            parent: 'akun',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/akun/akun-dialog.html',
                    controller: 'AkunDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Akun', function(Akun) {
                            return Akun.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('akun', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('akun.delete', {
            parent: 'akun',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/akun/akun-delete-dialog.html',
                    controller: 'AkunDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Akun', function(Akun) {
                            return Akun.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('akun', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

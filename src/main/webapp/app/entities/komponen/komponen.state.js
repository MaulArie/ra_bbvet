(function() {
    'use strict';

    angular
        .module('raBbvetApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('komponen', {
            parent: 'entity',
            url: '/komponen',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Komponens'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/komponen/komponens.html',
                    controller: 'KomponenController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('komponen-detail', {
            parent: 'entity',
            url: '/komponen/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Komponen'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/komponen/komponen-detail.html',
                    controller: 'KomponenDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Komponen', function($stateParams, Komponen) {
                    return Komponen.get({id : $stateParams.id});
                }]
            }
        })
        .state('komponen.new', {
            parent: 'komponen',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/komponen/komponen-dialog.html',
                    controller: 'KomponenDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                kd_komponen: null,
                                komponen: null,
                                biaya_komponen: null,
                                komponen_update: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('komponen', null, { reload: true });
                }, function() {
                    $state.go('komponen');
                });
            }]
        })
        .state('komponen.edit', {
            parent: 'komponen',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/komponen/komponen-dialog.html',
                    controller: 'KomponenDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Komponen', function(Komponen) {
                            return Komponen.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('komponen', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('komponen.delete', {
            parent: 'komponen',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/komponen/komponen-delete-dialog.html',
                    controller: 'KomponenDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Komponen', function(Komponen) {
                            return Komponen.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('komponen', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

(function() {
    'use strict';

    angular
        .module('raBbvetApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('subkomponen', {
            parent: 'entity',
            url: '/subkomponen',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Subkomponens'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/subkomponen/subkomponens.html',
                    controller: 'SubkomponenController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('subkomponen-detail', {
            parent: 'entity',
            url: '/subkomponen/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Subkomponen'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/subkomponen/subkomponen-detail.html',
                    controller: 'SubkomponenDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Subkomponen', function($stateParams, Subkomponen) {
                    return Subkomponen.get({id : $stateParams.id});
                }]
            }
        })
        .state('subkomponen.new', {
            parent: 'subkomponen',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subkomponen/subkomponen-dialog.html',
                    controller: 'SubkomponenDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                kd_subkomponen: null,
                                subkomponen: null,
                                biaya_subkomponen: null,
                                subkomponen_update: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('subkomponen', null, { reload: true });
                }, function() {
                    $state.go('subkomponen');
                });
            }]
        })
        .state('subkomponen.edit', {
            parent: 'subkomponen',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subkomponen/subkomponen-dialog.html',
                    controller: 'SubkomponenDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Subkomponen', function(Subkomponen) {
                            return Subkomponen.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('subkomponen', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('subkomponen.delete', {
            parent: 'subkomponen',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subkomponen/subkomponen-delete-dialog.html',
                    controller: 'SubkomponenDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Subkomponen', function(Subkomponen) {
                            return Subkomponen.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('subkomponen', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

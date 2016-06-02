(function() {
    'use strict';

    angular
        .module('raBbvetApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('detail', {
            parent: 'entity',
            url: '/detail',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Details'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/detail/details.html',
                    controller: 'DetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('detail-detail', {
            parent: 'entity',
            url: '/detail/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Detail'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/detail/detail-detail.html',
                    controller: 'DetailDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Detail', function($stateParams, Detail) {
                    return Detail.get({id : $stateParams.id});
                }]
            }
        })
        .state('detail.new', {
            parent: 'detail',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/detail/detail-dialog.html',
                    controller: 'DetailDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                detail: null,
                                volume_detail: null,
                                satuan_detail: null,
                                biaya_detail: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('detail', null, { reload: true });
                }, function() {
                    $state.go('detail');
                });
            }]
        })
        .state('detail.edit', {
            parent: 'detail',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/detail/detail-dialog.html',
                    controller: 'DetailDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Detail', function(Detail) {
                            return Detail.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('detail', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('detail.delete', {
            parent: 'detail',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/detail/detail-delete-dialog.html',
                    controller: 'DetailDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Detail', function(Detail) {
                            return Detail.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('detail', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

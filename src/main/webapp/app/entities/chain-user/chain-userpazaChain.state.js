(function() {
    'use strict';

    angular
        .module('pazachaingatewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('chain-userpazaChain', {
            parent: 'entity',
            url: '/chain-userpazaChain',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pazachaingatewayApp.chainUser.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/chain-user/chain-userspazaChain.html',
                    controller: 'ChainUserPazaChainController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('chainUser');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('chain-userpazaChain-detail', {
            parent: 'entity',
            url: '/chain-userpazaChain/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pazachaingatewayApp.chainUser.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/chain-user/chain-userpazaChain-detail.html',
                    controller: 'ChainUserPazaChainDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('chainUser');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ChainUser', function($stateParams, ChainUser) {
                    return ChainUser.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'chain-userpazaChain',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('chain-userpazaChain-detail.edit', {
            parent: 'chain-userpazaChain-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chain-user/chain-userpazaChain-dialog.html',
                    controller: 'ChainUserPazaChainDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ChainUser', function(ChainUser) {
                            return ChainUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('chain-userpazaChain.new', {
            parent: 'chain-userpazaChain',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chain-user/chain-userpazaChain-dialog.html',
                    controller: 'ChainUserPazaChainDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                uAaUserId: null,
                                eCert: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('chain-userpazaChain', null, { reload: 'chain-userpazaChain' });
                }, function() {
                    $state.go('chain-userpazaChain');
                });
            }]
        })
        .state('chain-userpazaChain.edit', {
            parent: 'chain-userpazaChain',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chain-user/chain-userpazaChain-dialog.html',
                    controller: 'ChainUserPazaChainDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ChainUser', function(ChainUser) {
                            return ChainUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('chain-userpazaChain', null, { reload: 'chain-userpazaChain' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('chain-userpazaChain.delete', {
            parent: 'chain-userpazaChain',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chain-user/chain-userpazaChain-delete-dialog.html',
                    controller: 'ChainUserPazaChainDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ChainUser', function(ChainUser) {
                            return ChainUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('chain-userpazaChain', null, { reload: 'chain-userpazaChain' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

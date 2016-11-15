(function() {
    'use strict';

    angular
        .module('pazachaingatewayApp')
        .controller('ChainUserPazaChainDetailController', ChainUserPazaChainDetailController);

    ChainUserPazaChainDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ChainUser'];

    function ChainUserPazaChainDetailController($scope, $rootScope, $stateParams, previousState, entity, ChainUser) {
        var vm = this;

        vm.chainUser = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pazachaingatewayApp:chainUserUpdate', function(event, result) {
            vm.chainUser = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

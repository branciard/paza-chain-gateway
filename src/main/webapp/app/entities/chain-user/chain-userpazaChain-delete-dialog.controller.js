(function() {
    'use strict';

    angular
        .module('pazachaingatewayApp')
        .controller('ChainUserPazaChainDeleteController',ChainUserPazaChainDeleteController);

    ChainUserPazaChainDeleteController.$inject = ['$uibModalInstance', 'entity', 'ChainUser'];

    function ChainUserPazaChainDeleteController($uibModalInstance, entity, ChainUser) {
        var vm = this;

        vm.chainUser = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ChainUser.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

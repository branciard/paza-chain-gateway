(function() {
    'use strict';

    angular
        .module('pazachaingatewayApp')
        .controller('ChainUserPazaChainDialogController', ChainUserPazaChainDialogController);

    ChainUserPazaChainDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ChainUser'];

    function ChainUserPazaChainDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ChainUser) {
        var vm = this;

        vm.chainUser = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.chainUser.id !== null) {
                ChainUser.update(vm.chainUser, onSaveSuccess, onSaveError);
            } else {
                ChainUser.save(vm.chainUser, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pazachaingatewayApp:chainUserUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

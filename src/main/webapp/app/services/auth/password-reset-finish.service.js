(function() {
    'use strict';

    angular
        .module('pazachaingatewayApp')
        .factory('PasswordResetFinish', PasswordResetFinish);

    PasswordResetFinish.$inject = ['$resource'];

    function PasswordResetFinish($resource) {
        var service = $resource('pazauaa/api/account/reset_password/finish', {}, {});

        return service;
    }
})();

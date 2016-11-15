(function () {
    'use strict';

    angular
        .module('pazachaingatewayApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('pazauaa/api/register', {}, {});
    }
})();

(function() {
    'use strict';

    angular
        .module('pazachaingatewayApp')
        .factory('Password', Password);

    Password.$inject = ['$resource'];

    function Password($resource) {
        var service = $resource('pazauaa/api/account/change_password', {}, {});

        return service;
    }
})();

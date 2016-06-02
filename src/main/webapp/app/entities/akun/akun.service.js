(function() {
    'use strict';
    angular
        .module('raBbvetApp')
        .factory('Akun', Akun);

    Akun.$inject = ['$resource', 'DateUtils'];

    function Akun ($resource, DateUtils) {
        var resourceUrl =  'api/akuns/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.akun_update = DateUtils.convertDateTimeFromServer(data.akun_update);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

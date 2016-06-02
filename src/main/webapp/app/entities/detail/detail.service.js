(function() {
    'use strict';
    angular
        .module('raBbvetApp')
        .factory('Detail', Detail);

    Detail.$inject = ['$resource'];

    function Detail ($resource) {
        var resourceUrl =  'api/details/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

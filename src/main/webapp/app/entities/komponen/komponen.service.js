(function() {
    'use strict';
    angular
        .module('raBbvetApp')
        .factory('Komponen', Komponen);

    Komponen.$inject = ['$resource', 'DateUtils'];

    function Komponen ($resource, DateUtils) {
        var resourceUrl =  'api/komponens/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.komponen_update = DateUtils.convertDateTimeFromServer(data.komponen_update);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

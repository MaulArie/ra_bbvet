(function() {
    'use strict';
    angular
        .module('raBbvetApp')
        .factory('Subkomponen', Subkomponen);

    Subkomponen.$inject = ['$resource', 'DateUtils'];

    function Subkomponen ($resource, DateUtils) {
        var resourceUrl =  'api/subkomponens/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.subkomponen_update = DateUtils.convertDateTimeFromServer(data.subkomponen_update);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

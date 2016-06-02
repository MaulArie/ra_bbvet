(function() {
    'use strict';
    angular
        .module('raBbvetApp')
        .factory('Output', Output);

    Output.$inject = ['$resource', 'DateUtils'];

    function Output ($resource, DateUtils) {
        var resourceUrl =  'api/outputs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.output_update = DateUtils.convertDateTimeFromServer(data.output_update);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

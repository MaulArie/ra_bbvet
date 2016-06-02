(function() {
    'use strict';
    angular
        .module('raBbvetApp')
        .factory('Program', Program);

    Program.$inject = ['$resource', 'DateUtils'];

    function Program ($resource, DateUtils) {
        var resourceUrl =  'api/programs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.program_update = DateUtils.convertDateTimeFromServer(data.program_update);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

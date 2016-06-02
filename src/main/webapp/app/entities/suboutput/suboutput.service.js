(function() {
    'use strict';
    angular
        .module('raBbvetApp')
        .factory('Suboutput', Suboutput);

    Suboutput.$inject = ['$resource', 'DateUtils'];

    function Suboutput ($resource, DateUtils) {
        var resourceUrl =  'api/suboutputs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.suboutput_update = DateUtils.convertDateTimeFromServer(data.suboutput_update);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

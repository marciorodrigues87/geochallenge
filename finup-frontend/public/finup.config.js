'use strict';

angular.
  module('finupFrontEnd').
  config(['$locationProvider' ,'$routeProvider',
    function config($locationProvider, $routeProvider) {
      $locationProvider.hashPrefix('!');

      $routeProvider.
        when('/signup', {
          template: '<signup></signup>'
        }).
        when('/survey', {
          template: '<survey></survey>'
        }).
        otherwise('/signup');
    }
  ]);
'use strict';

angular.
	module('signup').
	component('signup', {
		templateUrl: 'signup/signup.template.html',
		controller: ('SignupController', ['$scope', '$http',
			function SignupController($scope, $http) {
				$scope.signup = function() {
					$scope.pending = true;
    				$http.post('/v1/signups', $scope.model)
    					.then(
    						function success(response) {
    							$scope.success = true;
    							$scope.pending = false;
    							$scope.model.email = '';
    						}, 
    						function error(response) {
    							$scope.error = true;
    							$scope.pending = false;
    						});
  				};
			}
		])
	});
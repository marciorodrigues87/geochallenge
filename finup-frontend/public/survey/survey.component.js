'use strict';

angular.
	module('survey').
	component('survey', {
		templateUrl: 'survey/survey.template.html',
		controller: ('SurveyController', ['$scope', '$http', '$routeParams',
			function SignupController($scope, $http, $routeParams) {

				$scope.key = $routeParams.key;

				$scope.respond = function() {
					$scope.model.key = $scope.key;
					$scope.pending = true;
    				$http.post('/v1/surveys', $scope.model)
    					.then(
    						function success(response) {
    							$scope.success = true;
    							$scope.pending = false;
    							$scope.model = null;
    						}, 
    						function error(response) {
    							$scope.error = true;
    							$scope.pending = false;
    						});
  				};
			}
		])
	});
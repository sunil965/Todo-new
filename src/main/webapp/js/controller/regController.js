myApp.controller('regController', function($scope, $state, registerService) {
	console.log("inside the reg controller");
	
	/*$scope.passwordpattern="((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";*/

	$scope.userRegistration = function() {

		var regdata = {};
		regdata.name = $scope.name;
		regdata.email = $scope.email;
		regdata.contact = $scope.contact;
		regdata.password = $scope.password;

		var httpObj = registerService.register(regdata);

		httpObj.then(function(response) {
			if (response.status == 200) {
				console.log(response.data);
				console.log("/registration API called...")
				$state.go('login');
			} else {
				console.log("/registration failed...")
				$state.go('register')
			}
		})
	}

	/** *********************** Without Service **************************************/

	/*
	 * $scope.userRegistration = function() { $http({ method : "POST", url :
	 * "registration", data : $scope.user
	 * 
	 * }).then(function mySuccess(response) { $scope.myWelcome = response.data;
	 * $state.go("login"); }, function myError(response) { $scope.myWelcome =
	 * response.statusText; }); }
	 */

})

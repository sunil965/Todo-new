myApp.controller('loginCtrl', function($scope, $state, loginService) {
	
	console.log("inside the login controller");
	
	$scope.userlogin = function() {
		
		var user = {};
		user.email = $scope.email;
		user.password = $scope.password;

		var httpObj = loginService.login(user);

		httpObj.then(function(response)
		{
			console.log(response.data)
			if (response.data.status == 1)
			{
				console.log(response.data.status)
				localStorage.setItem("accesstoken", response.data.token.accesstoken),
				$state.go('notes');
			}
			else 
			{
				console.log("login unsuccessfull");
				console.log(response.data.status);
				$state.go('login');
			}
		})

	}
})

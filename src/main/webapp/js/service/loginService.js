myApp.service("loginService", function($http) {

	console.log("in loginservice");

	this.login = function(user) {
		return $http({
			url : "todoLogin",
			method : "post",
			data : user
		})
	}
})
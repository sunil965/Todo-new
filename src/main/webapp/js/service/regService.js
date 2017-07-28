myApp.service('registerService', function($http) {

	console.log("in registerService");

	this.register = function(regdata) {
		return $http({
			url : "registration",
			method : "POST",
			data : regdata
		})
	}
})
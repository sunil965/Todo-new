myApp.controller('socialController', function($scope, $state,$location,socialService) {
	console.log("inside the socialController controller");
	console.log("get url :: ",$location.search());
	var tokenString1=$location.search().tokeninurl;
	console.log("token variable string ....",tokenString);
	
	var tokenString = {};
	tokenString.string = tokenString1;
	
	var httpObject = socialService.getTokenByUrl(tokenString1);
	httpObject.then(function(response) {
		if (response.data.status == 1) {
			localStorage.setItem("accesstoken",	response.data.token.accesstoken);
			console.log("check data.token on note creation "+ response);
			$state.go('note');
			
		} else {
			console.log("Refresh token expired please login again...");
			$state.go('login');
		}
	});
}).service("socialService", function($http) {

	console.log("in socialService");

	this.getTokenByUrl = function(tokenString) {
		console.log("login token"+tokenString);
		return $http({
			url : "getTokenByUrl",
			method : "post",
			headers : {
				'tt' : tokenString
			}
		})
	}
});
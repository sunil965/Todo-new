myApp.controller('socialController', function($scope, $state,$location,socialService)
		{
				console.log("inside the socialController controller");
				
				console.log("get url :: ",$location.search());
				
				var tokenKey=$location.search().tokeninurl;
				
				console.log("token variable string ....",tokenKey);
				
				var tokenString = {};
				tokenString.string = tokenKey;
				
				var httpObject = socialService.getTokenByUrl(tokenKey);
				httpObject.then(function(response) {
					
					if (response.data.status == 5) {
						localStorage.setItem("accesstoken",	response.data.token.accesstoken);
						$state.go('notes');
					} 
					else {
						console.log("Access token expired please login again...");
						$state.go('login');
					}
				});
		}
).service("socialService", function($http)
		{
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
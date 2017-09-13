myApp.controller('resetpasswordController',	function($scope, $state, resetpasswordService) {
	
	console.log("Inside resetpasswordController !");
	
	$scope.sendmail=function(){
		var data = {};
		data.getemail = $scope.resetmailid;
		console.log("Email in Data :: ",data.getemail);
		
		var httpObject = resetpasswordService.sendREsetPasswordMail(data);
		httpObject.then(function(response) {
			if (response.data.status == 101) {
				$state.go('sendemail');
			} 
		});
	};

	
	$scope.resetPassword=function(){
		var newPassword = {};
		newPassword.password = $scope.newpassword;
		console.log("newPassword is :: ",newPassword.password);
		
		var httpObject = resetpasswordService.resetPassword(newPassword);
		httpObject.then(function(response) {
			if (response.data.status == 102) {
				$state.go('login');
			} 
		});
	};
	
});


myApp.service('resetpasswordService', function($http) {
	console.log("in resetpasswordService");

	this.sendREsetPasswordMail = function(resetdata) {
		return $http({
			url : "sendResetPasswordUrl",
			method : "post",
			data : resetdata,
		})
	}
	
	this.resetPassword = function(resetdata) {
		return $http({
			url : "updatepassword",
			method : "post",
			data : resetdata,
		})
	}
})
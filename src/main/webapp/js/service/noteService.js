myApp.service('noteservice', function($http) {

	console.log("in noteservice");

	this.create = function(notedata) {
		return $http({
			url : "rest/create",
			method : "post",
			data : notedata,
			headers : {
				'AccessToken' : localStorage.getItem("accesstoken")
			}
		})
	}

	this.findAllNotes = function() {
		return $http({
			url : "rest/getAllNotes",
			method : "get",
			headers : {
				'AccessToken' : localStorage.getItem("accesstoken")
			}
		})
	}

	this.verifyRefreshToken = function() {
		return $http({
			url : "generateNewAccessToken",
			method : "post",
			headers : {
				'AccessToken' : localStorage.getItem("accesstoken")
			}
		})
	}

	this.exit = function() {
		$http({
			url : "logout",
			headers : {
				'AccessToken' : localStorage.getItem("accesstoken")
			}
		})
	}

	this.deleteNote = function(id) {
		return $http({
			url : "rest/delete/" + id,
			/* method:"post", */
			headers : {
				"AccessToken" : localStorage.getItem("accesstoken")
			}
		})
	}

	this.update = function(data) {
		return $http({
			url : "rest/editNote",
			method : "post",
			data : data,
			headers : {
				'AccessToken' : localStorage.getItem("accesstoken")
			}
		})
	}

	this.getOtherUser = function(email) {
		return $http({
			url : "getUserDetailsById",
			method : "post",
			data : email
		})
	}
	
	this.saveCollab= function(collaboratorobject){
		console.log("in service.js",collaboratorobject);
		return $http({
			url :'createCollab',
			method :"post",
			data : collaboratorobject,
			
		})
		
	}
})
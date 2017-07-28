myApp.controller('notesController', function($scope, $state, noteservice){
	
	console.log("inside the notes controller");
   
    $scope.hideAndshow=function(){
        $scope.showfullbody = true;
    }
 
    
    /********************  Note Creation Logic.  ********************/
    
    $scope.savenote=function(){
    	$scope.showfullbody = false;
    	var title= $('#title').text();
    	var desc=$('#description').text();
    	
    	var notedata = {};
    	 notedata.title=title;
    	 notedata.description=desc;
       	 console.log(notedata);
       	 
       	 var httpObject = noteservice.create(notedata);
       	 httpObject.then(function(response)
       	  {
       		  console.log(response);
			  if(response.data.status == -4)
			  {
				  /*********  Generate New Access Token If Refresh Token is Valid Logic. ********/
				  
				var checkRefreshToken = noteservice.verifyRefreshToken();
				checkRefreshToken.then(function(res) 
					{
					if(res.data.status == 1){
						console.log("refresh call");
						localStorage.setItem("accesstoken", res.data.token.accesstoken),
						noteservice.create(notedata).then(function(responseagain){	});
					}else{
						console.log("Refresh token expired please login again...");
						$state.go('login');
					}
				})
			  }
		  })
       	 
       	$('#title').html("");
       	$('#description').html("");
    	
    }
    
    /*****************  Get All Notes Logic.  *****************/
    
    $scope.getAllNotes=function()
    {
    	console.log("logout method call");
    	var allnotes = noteservice.findAllNotes();
    	allnotes.then(function(response1)
    	 {
      		console.log(response1);
      		$scope.allnotes=response1.data.reverse();
      	 });
    }
    
    
    /********************  Logout Logic.  ********************/
    
    $scope.logout=function(){
    	
    	console.log("logout method call");
    	var logoutresponse=noteservice.exit();
    	console.log("logout service called");
    	 $state.go('login');
    	 console.log("logout from controller done");
         /*logoutresponse.then(function(){
        	 $state.go('login');
        })*/
    }
	
    $scope.getAllNotes();
});

var myApp = angular.module('todo', ['ui.router']).config(function ($stateProvider, $urlRouterProvider)
	{
		$stateProvider
		.state("login",
				{
				 url:"/login",
				 templateUrl:"template/login.html",
				 controller:"loginCtrl"
				})
  
        .state("register",
    		   {
    	   		url:"/register",
    	   		templateUrl:"template/registration.html",
    	   		controller:"regController"
    		   })
      
        .state("notes",
    		   {
    	   		url:"/notes",
    	   		templateUrl:"template/todonote.html",
    	   		controller:"notesController"
    		   })

		$urlRouterProvider.otherwise('/login');

	}
);



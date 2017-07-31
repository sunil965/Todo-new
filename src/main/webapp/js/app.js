
var myApp = angular.module('todo', ['ui.router','ngSanitize','ui.bootstrap']).config(function ($stateProvider, $urlRouterProvider)
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

myApp.directive('divdata', [function() {
    return {
        require: '?ngModel',
        scope: {
        },
        link: function(scope, element, attrs, ctrl) {
            // view -> model (when div gets blur update the view value of the model)
            element.bind('blur', function() {
                scope.$apply(function() {
                    ctrl.$setViewValue(element.html());
                });
            });

            // model -> view
            ctrl.$render = function() {
                element.html(ctrl.$viewValue);
            };

            // load init value from DOM
            ctrl.$render();

            // remove the attached events to element when destroying the scope
            scope.$on('$destroy', function() {
                element.unbind('blur');
                element.unbind('paste');
                element.unbind('focus');
            });
        }
    };

}]);


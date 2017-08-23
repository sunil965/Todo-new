var myApp = angular.module('todo',[ 'ui.router', 'ngSanitize', 'ui.bootstrap', 'tooltips' ]).config(
		function($stateProvider, $urlRouterProvider) {
			$stateProvider.state("login", {
				url : "/login",
				templateUrl : "template/login.html",
				controller : "loginCtrl"
			})

			.state("register", {
				url : "/register",
				templateUrl : "template/registration.html",
				controller : "regController"
			})

			.state("notes", {
				url : "/notes",
				templateUrl : "template/todonote.html",
				controller : "notesController"
			})

			.state("archive", {
				url : "/archive",
				templateUrl : "template/todonote.html",
				controller : "archiveController"
			})

			.state("trash", {
				url : "/trash",
				templateUrl : "template/todonote.html",
				controller : "trashController"
			})

			$urlRouterProvider.otherwise('/login');

		});

/*
 * ******************** This direvctive is used to make contents of div
 * editable.**************
 */

myApp.directive('divdata', [ function() {
	return {
		require : '?ngModel',
		scope : {},
		link : function(scope, element, attrs, ctrl) {
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

} ]);

/* **********This direvctive is used drag & drop of div.*********** */

myApp.directive('testpackery', ['$rootScope','$timeout',function($rootScope, $timeout) 
		{
			return {
				restrict : 'A',
				link : function(scope, element, attrs) 
				{
					if ($rootScope.packery === undefined || $rootScope.packery === null) 
					{
						scope.element = element;
						$rootScope.packery = new Packery(
								element[0].parentElement, 
								{
									isResizeBound : true,
									// rowHeight: 230,
									// columnWidth: 230,
									itemSelector : '.item'
								});
						$rootScope.packery.bindResize();
						var draggable1 = new Draggabilly(element[0]);
						$rootScope.packery.bindDraggabillyEvents(draggable1);

						draggable1.on('dragEnd', function(instance, event,
								pointer) {
							$timeout(function() {
								$rootScope.packery.layout();
								// $rootScope.packery.reloadItems();
							}, 200);
						});

//						 var orderItems = function() {
//						 var itemElems = $rootScope.packery.getItemElements();
//						 $(itemElems).each(function(i, itemElem) {
//						 $(itemElem).text(i + 1);
//						 });
//						 };
//
//						 $rootScope.packery.on('layoutComplete', orderItems);
//						 $rootScope.packery.on('dragItemPositioned',orderItems);

					} 
					else 
					{
						var draggable2 = new Draggabilly(element[0]);
						$rootScope.packery.bindDraggabillyEvents(draggable2);

						draggable2.on('dragEnd', function(instance, event,
								pointer) {
							$timeout(function() {
								$rootScope.packery.layout();
							}, 200);
						});

					}
					$timeout(function()
					{
						$rootScope.packery.reloadItems();
						$rootScope.packery.layout();
					}, 100);
				}
			};

		} ])

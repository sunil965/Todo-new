var myApp = angular.module('todo',[ 'ui.router', 'ngSanitize', 'ui.bootstrap', 'tooltips' ]).config(function($stateProvider, $urlRouterProvider)
		{
	
	console.log("Inside app.js");
	
			$stateProvider.state("login", {
				url : "/login",
				templateUrl : "template/login.html",
				controller : "loginCtrl"
			})
			
			.state("resetpassword", {
				url : "/resetpassword",
				templateUrl : "template/askemail.html",
				controller : "resetpasswordController"
			})
			
			.state("newpassword", {
				url : "/newpasswordpage",
				templateUrl : "template/newpasswordpage.html",
				controller : "resetpasswordController"
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
			
			.state("redirect", {
				url : "/redirect",
				templateUrl : "template/blankredirect.html",
				controller : "socialController"
			})
			
			.state("sendemail", {
				url : "/mail_sent",
				templateUrl : "template/afterRegistration.html",
			})
			// window.location = "home";
			$urlRouterProvider.otherwise('/login');

		});




/** ******* This direvctive is used to make contents of div editable.****** */

myApp.directive('divdata', [ function()
	{
	return {
		require : '?ngModel',
		scope : {},
		link : function(scope, element, attrs, ctrl) {
			// view -> model (when div gets blur update the view value of the
			// model)
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

myApp.directive('testpackery', [
		'$rootScope',
		'$timeout',
		function($rootScope, $timeout) {
			return {
				restrict : 'A',
				link : function(scope, element, attrs) {
					if ($rootScope.packery === undefined
							|| $rootScope.packery === null) {
						scope.element = element;
						$rootScope.packery = new Packery(
								element[0].parentElement, {
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

						// var orderItems = function() {
						// var itemElems = $rootScope.packery.getItemElements();
						// $(itemElems).each(function(i, itemElem) {
						// $(itemElem).text(i + 1);
						// });
						// };
						//
						// $rootScope.packery.on('layoutComplete', orderItems);
						// $rootScope.packery.on('dragItemPositioned',orderItems);

					} else {
						var draggable2 = new Draggabilly(element[0]);
						$rootScope.packery.bindDraggabillyEvents(draggable2);

						draggable2.on('dragEnd', function(instance, event,
								pointer) {
							$timeout(function() {
								$rootScope.packery.layout();
							}, 200);
						});

					}
					$timeout(function() {
						$rootScope.packery.reloadItems();
						$rootScope.packery.layout();
					}, 100);
				}
			};

		} ])
		
		
		
/*************** IMAGE FILE UPLOAD*****************/
		
  myApp.directive("ngFileSelect", function(fileReader, $timeout) {
    return {
      scope: {
        ngModel: '='
      },
      link: function($scope, el) {
        function getFile(file) {
          fileReader.readAsDataUrl(file, $scope)
            .then(function(result) {
              $timeout(function() {
                $scope.ngModel = result;
              });
            });
        }

        el.bind("change", function(e) {
          var file = (e.srcElement || e.target).files[0];
          getFile(file);
        });
      }
    };
  });

myApp.factory("fileReader", function($q, $log) {
  var onLoad = function(reader, deferred, scope) {
    return function() {
      scope.$apply(function() {
        deferred.resolve(reader.result);
      });
    };
  };

  var onError = function(reader, deferred, scope) {
    return function() {
      scope.$apply(function() {
        deferred.reject(reader.result);
      });
    };
  };

  var onProgress = function(reader, scope) {
    return function(event) {
      scope.$broadcast("fileProgress", {
        total: event.total,
        loaded: event.loaded
      });
    };
  };

  var getReader = function(deferred, scope) {
    var reader = new FileReader();
    reader.onload = onLoad(reader, deferred, scope);
    reader.onerror = onError(reader, deferred, scope);
    reader.onprogress = onProgress(reader, scope);
    return reader;
  };

  var readAsDataURL = function(file, scope) {
    var deferred = $q.defer();

    var reader = getReader(deferred, scope);
    reader.readAsDataURL(file);

    return deferred.promise;
  };

  return {
    readAsDataUrl: readAsDataURL
  };
});


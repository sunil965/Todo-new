myApp.controller('trashController',['$scope', '$controller', function($scope, $controller) {
	
	$controller('notesController', {$scope: $scope}),
	
	console.log("trash controller !!!!");
	$scope.createnote=false;
	$scope.homepage=false;
	$scope.pin=false;
	$scope.trashpage=true;
	$scope.archivepage=false;
	
}]);
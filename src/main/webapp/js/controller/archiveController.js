myApp.controller('archiveController',['$scope', '$controller', function($scope, $controller) {
	
	$controller('notesController', {$scope: $scope}),
	
	console.log("archive controller !!!!");
	$scope.createnote=false;
	$scope.homepage=false;
	$scope.pin=false;
	$scope.trashpage=false;
	$scope.archivepage=true;
	
}]);
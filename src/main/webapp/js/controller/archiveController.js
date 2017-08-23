myApp.controller('archiveController',['$scope', '$controller', function($scope, $controller) {
	
	$controller('notesController', {$scope: $scope}),
	
	$scope.apptitle="Archive Notes";
	$scope.apptitle.color="white";
	$scope.navstyle={
			"height": "60px",
			"background-color": "rgb(96, 125, 139)",
			"border-color": "rgb(96, 125, 139)",
	}
	$scope.navfontstyle={"size" : "5px", "color" : "white", "font-size":"20px"}
	
	console.log("archive controller !!!!");
	$scope.createnote=false;
	$scope.homepage=false;
	$scope.pinpage=false;
	$scope.trashpage=false;
	$scope.archivepage=true;
	
}]);
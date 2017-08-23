myApp.controller('trashController',['$scope', '$controller', function($scope, $controller) {
	
	$controller('notesController', {$scope: $scope}),
	
	$scope.apptitle="Trash Notes";
	$scope.navstyle={
			"height": "60px",
			"background-color": "rgb(99, 99, 99)",
			"border-color": "rgb(99, 99, 99)"
	}
	$scope.navfontstyle={"size" : "5px", "color" : "white","font-size":"20px"}
	
	console.log("trash controller !!!!");
	$scope.createnote=false;
	$scope.homepage=false;
	$scope.pinpage=false;
	$scope.trashpage=true;
	$scope.archivepage=false;
	
	
	$scope.openalert= function(){
		
	}
	
}]);
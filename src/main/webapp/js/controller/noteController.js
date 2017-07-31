myApp
		.controller(
				'notesController',
				function($scope, $state, noteservice/*, $uibModal*/) {

					console.log("inside the notes controller");

					$scope.hideAndshow = function() {
						$scope.showfullbody = true;
					}

//					$scope.allnotes = [];
					/**
					 * Note Creation Logic.
					 */
					$scope.savenote = function() {
						$scope.showfullbody = false;
						var notedata = {};
						notedata.title = $scope.title;
						notedata.description = $scope.desc;

						var httpObject = noteservice.create(notedata);
						httpObject.then(function(response) {
									if (response.data.status == -4) {
										/**
										 * Generate New Access Token If
										 * Refresh Token is Valid.
										 */
										var checkRefreshToken = noteservice.verifyRefreshToken();
										checkRefreshToken.then(function(res) {
													if (res.data.status == 1) {
																localStorage.setItem("accesstoken",	res.data.token.accesstoken),
																noteservice.create(notedata).then(function(responseagain) {
																					$scope.allnotes = responseagain.data.list;
																				});
													} else {
														console.log("Refresh token expired please login again...");
														$state.go('login');
													}
												})
									} else {
										$scope.allnotes = response.data.list.reverse();
									}
								});
						$('#title').html("");
						$('#description').html("");
						$scope.title = "";
						$scope.desc = "";
					}

					/** *************** Get All Notes Logic. **************** */

					$scope.getAllNotes = function() {
						var allnotes = noteservice.findAllNotes();
						allnotes.then(function(response) {
							if (response.data.status == -4) {
								var checkRefreshToken = noteservice.verifyRefreshToken();
								checkRefreshToken.then(function(res) {
											if (res.data.status == 1) {
														localStorage.setItem("accesstoken",	res.data.token.accesstoken),
														noteservice.findAllNotes().then(function(responseagain) {
																			$scope.allnotes = responseagain.data.list;
																		});
											} else {
												console.log("Refresh token expired please login again...");
												$state.go('login');
											}
										})
							}
							$scope.allnotes = response.data.reverse();
						});
					}

					/** ****************** Logout Logic. ******************* */

					$scope.logout = function() {
						console.log("logout method call");
						var logoutresponse = noteservice.exit();
						console.log("logout service called");
						$state.go('login');
						console.log("logout from controller done");
					}
					
					
					/** ****************** Delete Note Logic. ******************* */
					
					$scope.deleteNote = function(id){
						noteservice.deleteNote(id).then(function(response){
							$scope.allnotes = response.data.reverse();
						})
					} 
					
					/** ****************** Open Model Note Logic. ******************* */
					
					/*$scope.openPopUp = function(){
						console.log("open popup");
						var modalInstance = $uibModal.open({
					       	templateUrl: "template/popupdiv.html"
					       		)}
					}*/

					$scope.getAllNotes();
				});
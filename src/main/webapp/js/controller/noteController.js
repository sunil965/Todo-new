myApp.controller('notesController',	function($scope, $state, noteservice, $uibModal) {

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
																localStorage.setItem("accesstoken",	res.data.token.accesstoken);
																console.log("check data.token on note creation "+res);
																noteservice.create(notedata).then(function(responseagain) {
																	$scope.getAllNotes();
																				});
													} else {
														console.log("Refresh token expired please login again...");
														$state.go('login');
													}
												})
									} 
									$scope.getAllNotes();
								});
						/*$('#title').html("");
						$('#description').html("");*/
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
															$scope.allnotes = response.data.reverse();
																		});
											} else {
												console.log("Refresh token expired please login again...");
												$state.go('login');
											}
										});
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
							if(response.data.status == -4){
								var checkRefreshToken = noteservice.verifyRefreshToken();
								checkRefreshToken.then(function(res) {
											if (res.data.status == 1) {
														localStorage.setItem("accesstoken",	res.data.token.accesstoken),
														noteservice.deleteNote(id).then(function(responseagain) {
																	$scope.getAllNotes();
																		});
											} else {
												console.log("Refresh token expired please login again...");
												$state.go('login');
											}
										})
							}
							$scope.getAllNotes();
						});
					} 
					
					/** ****************** Open Model Note Logic. ******************* */
					
					$scope.openPopUp = function(x){
						console.log("open popup",x.title," ",x.description);
						var modalInstance = $uibModal.open({
					       	templateUrl: "template/popupdiv.html",
					       	controller: function($uibModalInstance) {
					       		var $ctrl = this;
					       		this.titledata=x.title;
					       		this.discriptiondata=x.description;
					       		this.noteId=x.id;
					       		this.notedate=x.date;
					       		this.noteuser=x.user;
					       		
					       		console.log(this.titledata,"  ",this.discriptiondata);
					       		
					       		this.updateNote=function(id){
					       			console.log("note id is :: ",id);
					       			console.log("title is :: ",this.title1);
					       			console.log("title is :: ",this.desc1);
					       			$uibModalInstance.dismiss('Done');
					       			var updateobj={};
					       			updateobj.id = this.noteId;
					       			updateobj.title = this.title1;
					       			updateobj.description = this.desc1;
					       			updateobj.user=this.noteuser;
					       			
					       			var httpObject = noteservice.update(updateobj);
					       			httpObject.then(function(response){
					       				if(response.data.status == -4){
					       					var checkRefreshToken = noteservice.verifyRefreshToken();
											checkRefreshToken.then(function(res) {
														if (res.data.status == 1) {
																	localStorage.setItem("accesstoken",	res.data.token.accesstoken),
																	noteservice.update(updateobj).then(function(responseagain)
																	{
																		$scope.allnotes = response.data.reverse();
																	});
														} else 
														{
															console.log("Refresh token expired please login again...");
															$state.go('login');
														}
													});
					       				}
					       				$scope.getAllNotes();
					       			});
					       		}
					       		
					       	},
					       	controllerAs: "$ctrl",
					       	
						});
						
					}
					/** ****************** Initial Note Display Logic. ******************* */

					$scope.getAllNotes();
				});
myApp.controller('notesController',	function($scope, $state, noteservice, $uibModal) {

					console.log("inside the notes controller");
					$scope.createnote=true;
					$scope.homepage=true;
					$scope.archivepage=false;
					$scope.trashpage=false;
					
					$scope.refresh=function($window){
						$state.reload()
					}

					$scope.hideAndshow = function() {
						$scope.showfullbody = true;
					}
					
					$scope.hideNoteCreate = function(){
						$scope.showfullbody = false;
					}
					
					$scope.showinlist = function(){
						$scope.listbtn=false;
						$scope.gridbtn=true;
						$scope.spacecol2="col-lg-2";
						$scope.changeView="col-lg-8";
						localStorage.setItem("view", "list");
					}
					
					$scope.showingrid = function(){
						$scope.listbtn=true;
						$scope.gridbtn=false;
						$scope.spacecol2="";
						$scope.changeView="col-lg-4 col-sm-9 col-md-4 col-xs-12 item";
						localStorage.setItem("view", "grid");
					}
					
					if (localStorage.view == "list") {
						$scope.showinlist();
						} else {
						$scope.showingrid();
						}
					
					/**
					 * Note Creation Logic.
					 */
					$scope.savenote = function() {
						$scope.showfullbody = false;
						var notedata = {};
						notedata.title = $scope.title;
						notedata.description = $scope.desc;
						notedata.color=$scope.colorOnCreate;

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
						$scope.title = "";
						$scope.desc = "";
						$scope.colorOnCreate="";
					}
					
					

				/** *************** Update Color Notes Logic. **************** */
					
					$scope.notecolor=function(note, color){
						note.color=color;
						var httpObject = noteservice.update(note);
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
					
				/** *************** Update/Set Reminder Notes Logic. **************** */
					
					$scope.setreminder=function(note, string){
						var remindAt = new Date();
						if(string=='today'){
							note.reminddate=remindAt.setHours(20,00,00);
						}
						else if(string =='tomorrow'){
							var tomorrow = new Date();
				            tomorrow.setDate(tomorrow.getDate() + 1);
				            tomorrow.setHours(8,00,00);
				            note.reminddate=tomorrow;
						}
						else{
							var nextweek = new Date();
							nextweek.setDate(nextweek.getDate() + 7);
							nextweek.setHours(8,00,00);
							note.reminddate=nextweek;
						}
						var httpObject = noteservice.update(note);
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
					
					
					/** *************** Delete/Remove Reminder(By making it null). **************** */
					
					$scope.deleteReminder=function(note){
						note.reminddate="";
						var httpObject = noteservice.update(note);
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
		       			});/*		$scope.archive=function(note){
						var value=note.archive;
						if(value==null || value=='false'){
							note.archive='true';
							$scope.reload();
						}
						else if(value==null || value=='true'){
							note.archive='false';
							$scope.reload();
						}
						
					}*/
					}
					
					/** *************** Get All Notes Logic. **************** */

					$scope.getAllNotes = function() {
						
						var allnotes = noteservice.findAllNotes();
						allnotes.then(function(response) {
							
							if (response.data.status == -4) {
								console.log("Logout from Read operation method.");
								$scope.logout();
								/*var checkRefreshToken = noteservice.verify$scope.listbtn=true;
							$scope.gridbtn=false;
							$scope.showlist=false;
							$scope.showgrid=true;RefreshToken();
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
										});*/
							}
							$scope.reminder = response.data.reminddate;
							$scope.name=response.data[0].user.name;
							$scope.email=response.data[0].user.email;
							$scope.allnotes = response.data.reverse();
						});
					}

					/** ****************** Logout Logic. ******************* */

					$scope.logout = function() {
						var logoutresponse = noteservice.exit();
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
						var modalInstance = $uibModal.open({
					       	templateUrl: "template/popupdiv.html",
					       	controller: function($uibModalInstance) {
					       		var $ctrl = this;
					       		this.titledata=x.title;
					       		this.discriptiondata=x.description;
					       		this.noteId=x.id;
					       		this.notedate=x.date;
					       		this.noteuser=x.user;
					       		this.notecolor=x.color;
					       		
					       		this.updateNote=function(id){
					       			$uibModalInstance.dismiss('Update');
					       			
					       			var updateobj={};
					       			updateobj.id = this.noteId;
					       			updateobj.title = this.titledata;
					       			updateobj.description = this.discriptiondata;
					       			updateobj.user=this.noteuser;
					       			updateobj.color=this.notecolor;
					       			
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
					
					/** ****************** Archive Logic. ******************* */
					
					$scope.archive=function(note){
						var value=note.archive;
						if(value == false){
							note.archive=true;
						}
						var httpObject = noteservice.update(note);
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
					
					/** ****************** Unarchive Logic. ******************* */
					
					$scope.unarchive=function(note){
						var value=note.archive;
						if(value == true){
							note.archive=false;
						}
						var httpObject = noteservice.update(note);
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
					
					/** ****************** Trash Logic. ******************* */
					
					$scope.doTrash=function(note){
						var value=note.trash;
						if(value == false){
							note.trash=true;
						}
						var httpObject = noteservice.update(note);
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
					
					/** ******************Recover Trash Logic. ******************* */
					
					$scope.doUnTrash=function(note){
						var value=note.trash;
						if(value == true){
							note.trash=false;
						}
						var httpObject = noteservice.update(note);
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
					
					/** ****************** Initial Note Display Logic. ******************* */

					$scope.getAllNotes();
				});
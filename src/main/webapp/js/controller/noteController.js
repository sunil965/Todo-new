myApp.controller('notesController',	function($scope, $state, noteservice, $uibModal, fileReader) {

					console.log("inside the notes controller");
					
					var user=null;
					
					$scope.apptitle = "Fundoo Notes";
					$scope.navstyle = {
						"height" : "60px",
						"background-color" : "#fb0",
						"border-color" : "#FFC107"
					}
					$scope.navfontstyle = {
						"size" : "5px",
						"color" : "black",
						"font-size" : "20px"
					}

					$scope.createnote = true;
					$scope.homepage = true;
					$scope.pinpage = true;
					$scope.archivepage = false;
					$scope.trashpage = false;

					$scope.refresh = function($window) {
						$state.reload()
					}

					$scope.hideAndshow = function() {
						$scope.showfullbody = true;
					}

					$scope.hideNoteCreate = function() {
						$scope.showfullbody = false;
					}

					$scope.showinlist = function() {
						$scope.listbtn = false;
						$scope.gridbtn = true;
						$scope.spacecol2 = "col-lg-2";
						$scope.changeView = "col-lg-8";
						$scope.onpin = {
							"margin-top" : "50px",
							"margin-bottom" : "-19px",
							"margin-left" : "157px",
							"color" : "rgba(0,0,0,.54)",
							"font-family" : "'Roboto',arial,sans-serif",
							"font-size" : "14px",
							"font-weight" : "bold"
						}
						localStorage.setItem("view", "list");
					}

					$scope.showingrid = function() {
						$scope.listbtn = true;
						$scope.gridbtn = false;
						$scope.spacecol2 = "";
						$scope.changeView = "col-lg-4 col-sm-12 col-md-6 col-xs-12 item";
						$scope.onpin = {
							"margin-top" : "50px",
							"margin-bottom" : "-19px",
							"margin-left" : "16px",
							"color" : "rgba(0,0,0,.54)",
							"font-family" : "'Roboto',arial,sans-serif",
							"font-size" : "14px",
							"font-weight" : "bold"
						}
						localStorage.setItem("view", "grid");
					}

					if (localStorage.view == "list") {
						$scope.showinlist();
					} else {
						$scope.showingrid();
					}
					
					/** ********* Image Note Creation. ************* */
					$scope.uploadImage = function() {
						document.getElementById("profileImg").click();
					}
					
					/** ********* Image Note Creation. ************* */
					$scope.addImage = function() {
						document.getElementById("addImg").click();
					}

					/**	 ********** Note Creation Logic. ******* */
					$scope.savenote = function() {
						$scope.showfullbody = false;
						var notedata = {};
						notedata.title = $scope.title;
						notedata.description = $scope.desc;
						notedata.color = $scope.colorOnCreate;
						notedata.picture = $scope.imageSrc;
						
						var httpObject = noteservice.create(notedata);
						httpObject.then(function(response) {
									if (response.data.status == -4) {
										/**
										 * Generate New Access Token If Refresh
										 * Token is Valid.
										 */
										var checkRefreshToken = noteservice.verifyRefreshToken();
										checkRefreshToken.then(function(res) {
													if (res.data.status == 1) {
														localStorage.setItem("accesstoken",	res.data.token.accesstoken);
														console.log("check data.token on note creation "+ res);
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
						$scope.colorOnCreate = "";
						$scope.imageSrc = "element.image || '//:0'";
					}

					/** ***** Update Color Notes Logic.********** */

					$scope.notecolor = function(note, color) {
						note.color = color;
						var httpObject = noteservice.update(note);
						httpObject.then(function(response) {
									if (response.data.status == -4) {
										var checkRefreshToken = noteservice.verifyRefreshToken();
										checkRefreshToken.then(function(res) {
													if (res.data.status == 1) {
																localStorage.setItem("accesstoken",	res.data.token.accesstoken),
																noteservice.update(updateobj).then(function(responseagain) {
																					$scope.allnotes = response.data.reverse();
																				});
													} else {
														console.log("Refresh token expired please login again...");
														$state.go('login');
													}
												});
									}
									$scope.getAllNotes();
								});
					}

					/** ***** Update/Set Reminder Notes Logic.****** */

					$scope.setreminder = function(note, string) {
						var remindAt = new Date();
						if (string == 'today') {
							note.reminddate = remindAt.setHours(20, 00, 00);
						} else if (string == 'tomorrow') {
							var tomorrow = new Date();
							tomorrow.setDate(tomorrow.getDate() + 1);
							tomorrow.setHours(8, 00, 00);
							note.reminddate = tomorrow;
						} else {
							var nextweek = new Date();
							nextweek.setDate(nextweek.getDate() + 7);
							nextweek.setHours(8, 00, 00);
							note.reminddate = nextweek;
						}
						var httpObject = noteservice.update(note);
						httpObject.then(function(response) {
									if (response.data.status == -4) {
										var checkRefreshToken = noteservice.verifyRefreshToken();
										checkRefreshToken.then(function(res) {
													if (res.data.status == 1) {
																localStorage.setItem("accesstoken",	res.data.token.accesstoken),
																noteservice.update(updateobj).then(function(responseagain) {
																					$scope.allnotes = response.data.reverse();
																				});
													} else {
														console.log("Refresh token expired please login again...");
														$state.go('login');
													}
												});
									}
									$scope.getAllNotes();
								});
					}

					/** ******* Delete/Remove Reminder(By making it null) ******* */

					$scope.deleteReminder = function(note) {
						note.reminddate = "";
						var httpObject = noteservice.update(note);
						httpObject.then(function(response) {
									if (response.data.status == -4) {
										var checkRefreshToken = noteservice.verifyRefreshToken();
										checkRefreshToken.then(function(res) {
													if (res.data.status == 1) {
																localStorage.setItem("accesstoken",	res.data.token.accesstoken),
																noteservice.update(updateobj).then(function(responseagain) {
																					$scope.allnotes = response.data.reverse();
																				});
													} else {
														console.log("Refresh token expired please login again...");
														$state.go('login');
													}
												});
									}
									$scope.getAllNotes();
								});
					}

					/** ******** Get Logged-in user. ********* */
					$scope.getCurrentUser = function() {
						user = noteservice.findCurrentUser();
						user.then(function(response) {
									if (response.data.status == -4) {
										var checkRefreshToken = noteservice.verifyRefreshToken();
										checkRefreshToken.then(function(res) {
													if (res.data.status == 1) {
																localStorage.setItem("accesstoken",	res.data.token.accesstoken),
																noteservice.findCurrentUser().then(function(responseuser) {
																					$scope.name = responseuser.data.user.name;
																					$scope.email = responseuser.data.user.email;
																				});
													} else {
														console.log("Refresh token expired please login again...");
														$state.go('login');
													}
												});
									}
									$scope.name = response.data.user.name;
									$scope.email = response.data.user.email;
								});
					}

					/** ********* Get All Notes Logic. ********** */

					$scope.getAllNotes = function() {

						var allnotes = noteservice.findAllNotes();
						allnotes.then(function(response) {
									if (response.data.status == -4) {
										console.log("Logout from Read operation method.");
										$scope.logout();
										/*
										 * $scope.listbtn=true;
										 * $scope.gridbtn=false;
										 * $scope.showlist=false;
										 * $scope.showgrid=true;
										 * 
										 * var checkRefreshToken =
										 * noteservice.verifyRefreshToken();
										 * checkRefreshToken.then(function(res) {
										 * if (res.data.status == 1) {
										 * localStorage.setItem("accesstoken",
										 * res.data.token.accesstoken),
										 * noteservice.findAllNotes().then(function(responseagain) {
										 * $scope.allnotes =
										 * response.data.reverse(); }); } else {
										 * console.log("Refresh token expired
										 * please login again...");
										 * $state.go('login'); } });
										 */
									}
									/*
									 * console.log("Response with scrap
									 * "+response.data) $scope.reminder =
									 * response.data.reminddate;
									 */
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

					$scope.deleteNote = function(id) {
						noteservice.deleteNote(id).then(function(response) {
											if (response.data.status == -4) {
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

					/** *********** Open Model Note Logic.**************** */

					$scope.updatePopUp = function(x) {
						var modalInstance = $uibModal
								.open({
									templateUrl : "template/popupdiv.html",
									controller : function($uibModalInstance) {
										var $ctrl = this;
										this.titledata = x.title;
										this.discriptiondata = x.description;
										this.noteId = x.id;
										this.notedate = x.date;
										this.noteuser = x.user;
										this.notecolor = x.color;
										this.notepin = x.pin;
										this.notepic = x.picture;

										this.updateNote = function(id) {
											$uibModalInstance.dismiss('Update');

											var updateobj = {};
											updateobj.id = this.noteId;
											updateobj.title = this.titledata;
											updateobj.description = this.discriptiondata;
											updateobj.user = this.noteuser;
											updateobj.color = this.notecolor;
											updateobj.pin = this.notepin;
											updateobj.archive = this.notearchive;
											updateobj.picture = x.picture;

											var httpObject = noteservice.update(updateobj);
											httpObject.then(function(response) {
														if (response.data.status == -4) {
															var checkRefreshToken = noteservice.verifyRefreshToken();
															checkRefreshToken.then(function(res) {
																		if (res.data.status == 1) {
																					localStorage
																							.setItem(
																									"accesstoken",
																									res.data.token.accesstoken),
																					noteservice
																							.update(
																									updateobj)
																							.then(
																									function(
																											responseagain) {
																										$scope.allnotes = response.data
																												.reverse();
																									});
																		} else {
																			console
																					.log("Refresh token expired please login again...");
																			$state
																					.go('login');
																		}
																	});
														}
														$scope.getAllNotes();
													});
										}
									},
									controllerAs : "$ctrl"
								});
					}

					/** ********* Collaboration Logic. ********* */

					$scope.collaboratePopUp = function(note) {

						var callabrateModel = $uibModal
								.open({
									templateUrl : "template/collaboratediv.html",
									controller : function($uibModalInstance) {
										var $ctrl = this;
										this.ownerEmail = note.user.email;
										var collaboratorid;
										this.sharedEmailWith = function() {
											$uibModalInstance.dismiss();

											var collaboratorobject = {};
											collaboratorobject.noteid = note.id;
											collaboratorobject.sharedwith = this.othersemail;

											console.log("collaboratorobject  "
													+ collaboratorobject);
											console.log("save");
											noteservice
													.saveCollab(collaboratorobject);
											console.log("save end");

											/*
											 * var otherUser =
											 * {"email":this.othersemail,};
											 * noteservice.getOtherUser(otherUser).then(function(response) {
											 * collaboratoremail=response.data.user.email;
											 * 
											 * var collaboratorobject={};
											 * console.log("save note
											 * "+note.id);
											 * 
											 * collaboratorobject.note =
											 * note.id;
											 * collaboratorobject.shareduserid =
											 * otherUser;
											 * 
											 * console.log("save");
											 * noteservice.saveCollab(collaboratorobject);
											 * console.log("save end"); });
											 */
										}
									},
									controllerAs : "$ctrl"
								});
					}

					/** ************* Archive Logic. ************* */

					$scope.archive = function(note) {
						var value = note.archive;
						if (value == false) {
							note.archive = true;
							note.pin = false;
						}
						var httpObject = noteservice.update(note);
						httpObject
								.then(function(response) {
									if (response.data.status == -4) {
										var checkRefreshToken = noteservice
												.verifyRefreshToken();
										checkRefreshToken
												.then(function(res) {
													if (res.data.status == 1) {
																localStorage
																		.setItem(
																				"accesstoken",
																				res.data.token.accesstoken),
																noteservice
																		.update(
																				updateobj)
																		.then(
																				function(
																						responseagain) {
																					$scope.allnotes = response.data
																							.reverse();
																				});
													} else {
														console
																.log("Refresh token expired please login again...");
														$state.go('login');
													}
												});
									}
									$scope.getAllNotes();
								});
					}

					/** ************ Unarchive Logic. ************ */

					$scope.unarchive = function(note) {
						var value = note.archive;
						if (value == true) {
							note.archive = false;
						}
						var httpObject = noteservice.update(note);
						httpObject
								.then(function(response) {
									if (response.data.status == -4) {
										var checkRefreshToken = noteservice
												.verifyRefreshToken();
										checkRefreshToken
												.then(function(res) {
													if (res.data.status == 1) {
																localStorage
																		.setItem(
																				"accesstoken",
																				res.data.token.accesstoken),
																noteservice
																		.update(
																				updateobj)
																		.then(
																				function(
																						responseagain) {
																					$scope.allnotes = response.data
																							.reverse();
																				});
													} else {
														console
																.log("Refresh token expired please login again...");
														$state.go('login');
													}
												});
									}
									$scope.getAllNotes();
								});
					}

					/** ****************** Trash Logic. ******************* */

					$scope.doTrash = function(note) {
						var value = note.trash;
						if (value == false) {
							note.trash = true;
						}
						var httpObject = noteservice.update(note);
						httpObject
								.then(function(response) {
									if (response.data.status == -4) {
										var checkRefreshToken = noteservice
												.verifyRefreshToken();
										checkRefreshToken
												.then(function(res) {
													if (res.data.status == 1) {
																localStorage
																		.setItem(
																				"accesstoken",
																				res.data.token.accesstoken),
																noteservice
																		.update(
																				updateobj)
																		.then(
																				function(
																						responseagain) {
																					$scope.allnotes = response.data
																							.reverse();
																				});
													} else {
														console
																.log("Refresh token expired please login again...");
														$state.go('login');
													}
												});
									}
									$scope.getAllNotes();
								});
					}

					/** *****Recover Trash Logic. ************** */

					$scope.doUnTrash = function(note) {
						var value = note.trash;
						if (value == true) {
							note.trash = false;
						}
						var httpObject = noteservice.update(note);
						httpObject
								.then(function(response) {
									if (response.data.status == -4) {
										var checkRefreshToken = noteservice
												.verifyRefreshToken();
										checkRefreshToken
												.then(function(res) {
													if (res.data.status == 1) {
																localStorage
																		.setItem(
																				"accesstoken",
																				res.data.token.accesstoken),
																noteservice
																		.update(updateobj)
																		.then(
																				function(
																						responseagain) {
																					$scope.allnotes = response.data
																							.reverse();
																				});
													} else {
														console
																.log("Refresh token expired please login again...");
														$state.go('login');
													}
												});
									}
									$scope.getAllNotes();
								});
					}

					/** ****************** Pin Logic. ******************* */

					$scope.setpin = function(note) {
						console.log("Pin call");
						var value = note.pin;
						if (value == false) {
							note.pin = true;
							note.archive = false;
						}
						var httpObject = noteservice.update(note);
						httpObject
								.then(function(response) {
									if (response.data.status == -4) {
										var checkRefreshToken = noteservice
												.verifyRefreshToken();
										checkRefreshToken
												.then(function(res) {
													if (res.data.status == 1) {
																localStorage
																		.setItem(
																				"accesstoken",
																				res.data.token.accesstoken),
																noteservice
																		.update(
																				updateobj)
																		.then(
																				function(
																						responseagain) {
																					$scope.allnotes = response.data
																							.reverse();
																				});
													} else {
														console
																.log("Refresh token expired please login again...");
														$state.go('login');
													}
												});
									}
									$scope.getAllNotes();
								});
					}

					/** ****************** Unpin Logic. ******************* */

					$scope.unpin = function(note) {
						var value = note.pin;
						if (value == true) {
							note.pin = false;
						}
						var httpObject = noteservice.update(note);
						httpObject
								.then(function(response) {
									if (response.data.status == -4) {
										var checkRefreshToken = noteservice
												.verifyRefreshToken();
										checkRefreshToken
												.then(function(res) {
													if (res.data.status == 1) {
																localStorage
																		.setItem(
																				"accesstoken",
																				res.data.token.accesstoken),
																noteservice
																		.update(
																				updateobj)
																		.then(
																				function(
																						responseagain) {
																					$scope.allnotes = response.data
																							.reverse();
																				});
													} else {
														console
																.log("Refresh token expired please login again...");
														$state.go('login');
													}
												});
									}
									$scope.getAllNotes();
								});
					}

					/** ********* Share On Facebook Logic. ************* */

					$scope.facebookshare = function(note) {
						FB.init({
							appId : '493599474327815',
							status : true,
							xfbml : true,
							version : 'v2.10',
						});

						FB.ui({
							method : 'share_open_graph',
							action_type : 'og.shares',
							action_properties : JSON.stringify({
								object : {
									'og:title' : note.title,
									'og:description' : note.description,
								}
							})
						});
					}

					/** ********* Initial Note Display Logic. ************* */
					$scope.getCurrentUser();
					$scope.getAllNotes();
				});
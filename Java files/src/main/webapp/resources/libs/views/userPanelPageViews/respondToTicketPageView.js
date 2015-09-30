/* ================================================= */

/* Respond to ticket page view */


var RespondToTicketPageView = GenericUserPanelPageView.extend({

	events : {
		'click #respondToTicketButton' : 'submit',
		'click #closeTheTicketButton' : 'close',
		'click #assignUserToTicketButton' : 'reassignAdmin',
		'click #reassignPriorityToTicketButton' : 'reassingPriority'
	},

	initialize : function() {
		
		var self = this.model;
		
		var ticket = new GetTicketContentModel({
			ticketId : this.model.get("ticketId")
		});
		
		var lastLeftCommentUserId = 0;
		var lastRightCommentUserId = 0;
		var loggedUserId = Number(sessionStorage.loggedUserId);
		
		ticket.save({}, {
			success : function(model, response) {
					if (response.type == "success") {
					$('#ticketCommentsWrapper').empty();


					_.each(response.data.comments, function(e) {
						var date = new Date(e.dateTime);
						var hour = addZero(date.getHours());
						var minute = addZero(date.getMinutes());
						var dateString = date.toLocaleDateString() + " " +hour +":"+ minute ;
						
						var ticketPriority = response.data.priority.priorityId;
						console.log(ticketPriority);
						
						var priorities =  new GetPrioritiesModel({
							userId : sessionStorage.loggedUserId
						});
					
						
						priorities.save({}, {
							success : function(model, response) {
								if (response.type == "success"){
									$('#ticketPrioritiesDropBox').find('option').remove().end();
									_.each(response.data, function(e) {
										if (ticketPriority == e.priorityId) {
											$('#ticketPrioritiesDropBox').append("<option value=" +e.priorityId + " selected>" + e.priorityName + "</option>");
										} else {
											$('#ticketPrioritiesDropBox').append("<option value=" +e.priorityId + ">" + e.priorityName + "</option>");
										}
										
									});
								} else {
									if (response.type == "error"){
										alert(response.description);
									} else {
										alert("Unknown error!");
									}
								}
							},
							error : function(model, response) {
								console.log(response);
							}
						});
						
						if(response.data.state.stateName == "Closed") {
							$('#ticketResponseWrapper').remove();
						}
						
						if (loggedUserId == e.user.userId) {
							if(lastLeftCommentUserId == loggedUserId){
								$('#ticketCommentsWrapper').append($("<div class='commentWrapper'>"+
									"<div class='commentBlockLeft'>" + e.comment +
									"<div class='commentDateLeft'>" + dateString +
									"</div></div>"));
								lastRightCommentUserId=0;
								
							} else {
								$('#ticketCommentsWrapper').append($("<div class='commentWrapper'>"+
									"<div class='userNameLeft'>" + e.user.firstName + "</div>" +
									"<div class='commentBlockLeft'>" + e.comment +
									"<div class='commentDateLeft'>" + dateString +
									"</div></div>"));
								lastLeftCommentUserId = loggedUserId;
								lastRightCommentUserId =0;
								
							}
						} else {
							if(lastRightCommentUserId !=0 && lastRightCommentUserId == e.user.userId){
								$('#ticketCommentsWrapper').append($("<div class='commentWrapper'>"+
										"<div class='commentBlockRight'>" + e.comment +
										"<div class='commentDateRight'>" + dateString +
										"</div></div>"));
								lastLeftCommentUserId =0;
							} else {
								if(lastRightCommentUserId==0){
									$('#ticketCommentsWrapper').append($("<div class='commentWrapper'>"+
										"<div class='userNameRight'>" + e.user.firstName + "</div>" +
										"<div class='commentBlockRight'>" + e.comment +
										"<div class='commentDateRight'>" + dateString +
										"</div></div>"));
								} else {
									$('#ticketCommentsWrapper').append($("<div class='commentWrapper'>"+
											"<div class='secondUserNameRight'>" + e.user.firstName + "</div>" +
											"<div class='commentBlockRight'>" + e.comment +
											"<div class='commentDateRight'>" + dateString +
											"</div></div>"));
								}
									lastRightCommentUserId =e.user.userId;
									lastLeftCommentUserId =0;
							}		
						}
					
					});
					$('#ticketTitle').empty().append("\""+response.data.subject +"\""+" is ");
					var assignedToId = response.data.assignedToUser.userId;
					var ticketPriority = response.data.priorityId;
			
					var categoryAdmins = new getAdminForCategory({
						categoryId : response.data.category.categoryId
					})
					
					var ticketSubject = response.data.subject;
					var ticketState = response.data.state.stateName;
					
					var ticketOwnerId = response.data.comments[0].user.userId;
					
					categoryAdmins.save({},{
						
						success : function(model,response) {
							
							if (response.type == "success"){
								
								var assignedUserName;
								$('#ticketAdminsDropBox').find('option').remove().end();
								_.each(response.data,function(e) {
									if (assignedToId == e.userId) {
										assignedUserName = e.firstName + " "+ e.lastName;
									}
								});
								
								$('#ticketPrioritiesDropBox').show();
								$('#reassignPriorityToTicketButton').show();
								
								if (sessionStorage.loggedUserRights == "Admin" || sessionStorage.loggedUserRights == "SysAdmin" ) {
									
									if (self.get("provenience") == "assignedTickets") {
										$('#ticketAdminsDropBox').show();
										$('#assignUserToTicketButton').show();
										
									} else {
										if (self.get("provenience") == "userTickets") {
											$('#ticketAdminsDropBox').hide();
											$('#assignUserToTicketButton').hide();
										} else {
											console.log("Invalid Session Storage Data!");
										}
									}
									
									if (assignedToId == 0) {
										$('#ticketAdminsDropBox').append("<option value=0>Set ticket as unassigned</option>");
										$('#ticketTitle').append("currently unassigned");
									} else {
										$('#ticketAdminsDropBox').append("<option value=0 selected>Set ticket as unassigned</option>");
										$('#ticketTitle').append("assigned to "+ assignedUserName);
									}
									
									_.each(response.data, function(e) {
										if (assignedToId == e.userId) {
											$('#ticketAdminsDropBox').append("<option value=" + e.userId + " selected>" + e.firstName + " " + e.lastName + "</option>");
										} else {
											$('#ticketAdminsDropBox').append("<option value=" + e.userId + ">" + e.firstName + " " + e.lastName + "</option>");
										}
									
									});
								} else {
									
									if (assignedToId == 0) {
										$('#ticketTitle').append("currently unassigned");
									} else {
										$('#ticketTitle').append("assigned to "+ assignedUserName);
									}
								}
								
								if(ticketState == "Closed") {
									$('#ticketTitle').empty().append("\""+ticketSubject +"\""+" is closed ");
								}
							} else {
								if (response.type == "error"){
									alert(response.description);
								} else {
									alert("Unknown error!");
								}
							}
						},
						error : function(model,response){
							console.log(response);
						}
					})
				} else {
					if (response.type == "error"){
						alert(response.description);
					} else {
						alert("Unknown error!");
					}
				}
			},
			error : function(model, response) {
				console.log(response);
			}
		});

	},
	
	reassignAdmin : function() {
		
		var selectedUser = new Backbone.Model({
			userId : $("#ticketAdminsDropBox option:selected").val()
			
		})
		
		var reassignAdminToTicket = new AssignAdminToTicket({
			assignedToUser : selectedUser,
			ticketId : this.model.get("ticketId")
		});
		
		reassignAdminToTicket.save({}, {
			success : function(model, response) {
				if (response.type == "success"){
					alert("Ticket reassigned!");
				} else {
					if (response.type == "error"){
						alert(response.description);
					} else {
						alert("Unknown error!");
					}
				}
			},
			error : function(model, response) {
				console.log(response);
			}
		});
	},
	
	reassingPriority : function() {
		
		var selectedPriority = new Backbone.Model({
			priorityId : $("#ticketPrioritiesDropBox  option:selected").val()
			
		})
		
		var reassignPriorityToTicket = new AssignPriorityToTicket({
			priority : selectedPriority,
			ticketId : this.model.get("ticketId")
		});
		
		reassignPriorityToTicket.save({}, {
			success : function(model, response) {
				if (response.type == "success"){
					alert("Priority changed successfully!");
				} else {
					if (response.type == "error"){
						alert(response.description);
					} else {
						alert("Unknown error!");
					}
				}
			},
			error : function(model, response) {
				console.log(response);
			}
		});
	},
	
	close : function(){
			var close = new CloseTicketModel({
				ticketId : this.model.get("ticketId")
			})
			
			if ($("#ticketResponse").val() != ""){
				this.submit();
			}
			
			close.save({},{
				async: false,
				success : function(model, response){
					if (response.type == "success"){
						alert("Ticket closed!");
					} else {
						if (response.type == "error"){
							alert(response.description);
						} else {
							alert("Unknown error!");
						}
					}
				},
				error : function(model, response){
					console.log(response);
				}
			});
			
			this.initialize();
		},

	appendData : function() {
		this.$el.append(_.template($('#respondToTicketTemplate').html()));
		return this;
	},


	submit : function() {

		var currentUser = new Backbone.Model({
			userId : sessionStorage.loggedUserId,
		})

		var ticketComment = new TicketComment({
			user : currentUser,
			dateTime : new Date().getTime(),
			comment : $("#ticketResponse").val()
		});

		
		var resp = new RespondToTicketModel({
			ticketId : this.model.get("ticketId"),
			comments : [ ticketComment ]
		});

		resp.save({}, {
			async: false,
			success : function(model, response) {
				if (response.type == "success"){
				} else {
					if (response.type == "error"){
						alert(response.description);
					} else {
						alert("Unknown error!");
					}
				}
			},
			error : function(model, response) {
				console.log(response);
			}
		});
		
		this.initialize();
		$("#ticketResponse").val('');
	}

});
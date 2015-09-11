/* ================================================= */
/* Respond to ticket page view */

var RespondToTicketPageView = GenericUserPanelPageView.extend({

	events : {
		'click #respondToTicketButton' : 'submit',
		'change #ticketAdminsDropBox' : 'reasignAdmin'
	},

	initialize : function() {
		var ticket = new GetTicketContentModel({
			ticketId : this.model.get("ticketId")
		});
		
		var lastLeftCommentUserId =0;
		var lastRightCommentUserId =0;
		var loggedUserId = Number(sessionStorage.loggedUserId);
		
		ticket.save({}, {
			success : function(model, response) {
				$('#ticketCommentsWrapper').empty();
				_.each(response.comments, function(e) {
					var date = new Date(e.dateTime);
					var hour = addZero(date.getHours());
					var minute = addZero(date.getMinutes());
					var dateString = date.toLocaleDateString() + " " +hour +":"+ minute ;
					
					if (loggedUserId == e.user.userId) {
						if(lastLeftCommentUserId == loggedUserId){
							$('#ticketCommentsWrapper').append($("<div class='commentWrapper'>"+
								"<div class='commentBlockLeft'>" + e.comment +
								"<div class='commentDateLeft'>" + dateString +
								"</div></div>"));
							lastRightCommentUserId=0;
							
						}
						else{
							$('#ticketCommentsWrapper').append($("<div class='commentWrapper'>"+
								"<div class='userNameLeft'>" + e.user.firstName + "</div>" +
								"<div class='commentBlockLeft'>" + e.comment +
								"<div class='commentDateLeft'>" + dateString +
								"</div></div>"));
							lastLeftCommentUserId = loggedUserId;
							
						}
						
					}
					else{
						if(lastRightCommentUserId !=0 && lastRightCommentUserId == e.user.userId){
							$('#ticketCommentsWrapper').append($("<div class='commentWrapper'>"+
									"<div class='commentBlockRight'>" + e.comment +
									"<div class='commentDateRight'>" + dateString +
									"</div></div>"));
							lastLeftCommentUserId =0;
						}
						else{
							if(lastRightCommentUserId==0){
								$('#ticketCommentsWrapper').append($("<div class='commentWrapper'>"+
										"<div class='userNameRight'>" + e.user.firstName + "</div>" +
										"<div class='commentBlockRight'>" + e.comment +
										"<div class='commentDateRight'>" + dateString +
										"</div></div>"));
							}
							else{
								$('#ticketCommentsWrapper').append($("<div class='commentWrapper'>"+
										"<div class='secondUserNameRight'>" + e.user.firstName + "</div>" +
										"<div class='commentBlockRight'>" + e.comment +
										"<div class='commentDateRight'>" + dateString +
										"</div></div>"));
							}
								lastRightCommentUserId =e.user.userId;
						}
							
					}	
				});
				console.log(response);
				$('#ticketTitle').append(response.subject + " - " + response.category.categoryName);

				var assignedToId = response.assignedToUser.userId;
		
				var categoryAdmins = new getAdminForCategory({
					categoryId : response.category.categoryId
				})
				
				categoryAdmins.save({},{
					success : function(model,response){
						if (assignedToId == 0)
						{
							$('#ticketAdminsDropBox').append("<option value=0>Unassign ticket</option>");
						}
						else
						{
							$('#ticketAdminsDropBox').append("<option value=0 selected>Unassign ticket</option>");
						}
						_.each(response, function(e) {
							if (assignedToId == e.userId)
							{
								$('#ticketAdminsDropBox').append("<option value=" + e.userId + " selected>" + e.firstName + " " + e.lastName + "</option>");
							}
							else
							{
								$('#ticketAdminsDropBox').append("<option value=" + e.userId + ">" + e.firstName + " " + e.lastName + "</option>");
							}
						});
						
					},
					error : function(model,response){
						console.log(response);
					}
				})
				
				
			},
			error : function(model, response) {
				console.log(response);
			}
		});

	},
	
	reasignAdmin : function() {
		//TODO: asignarea efectiva a adminilor
		console.log("assign admin SP must be called");
	},

	appendData : function() {
		this.$el.append(_.template($('#respondToTicketTemplate').html()));
		return this;
	},

	submit : function() {

		var tmpUser = new Backbone.Model({
			userId : sessionStorage.loggedUserId,
		})

		var ticketComment = new TicketComment({
			user : tmpUser,
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
				console.log(response);
			},
			error : function(model, response) {
				console.log(response);
			}
		});
		
		this.initialize();
		$("#ticketResponse").val('');
	}

});
/* ================================================= */
/* Respond to ticket page view */

var RespondToTicketPageView = GenericUserPanelPageView.extend({

	events : {
		'click #respondToTicketButton' : 'submit'
	},

	initialize : function() {

		function addZero(i) {
			if (i < 10) {
				i = "0" + i;
			}
			return i;
		}

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
			},
			error : function(model, response) {
				console.log(response);
			}
		});

	},

	appendData : function() {
		this.$el.append(_.template($('#respondToTicketTemplate').html()));
		this.$el.find('#ticketTitle').append(this.model.get("subject")).append(
				" - ").append(this.model.get("category"));

		// append assignation stuff 
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
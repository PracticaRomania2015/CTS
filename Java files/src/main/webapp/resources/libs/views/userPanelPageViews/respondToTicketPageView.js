/* ================================================= */
/* Respond to ticket page view */

var RespondToTicketPageView = GenericUserPanelPageView.extend({

	events : {
		'click #respondToTicketButton' : 'submit'
	},
	
	initialize : function() {
		
		var ticket = new GetTicketContentModel({
			ticketId : this.model.get("ticketId")
		});
		
		ticket.save({}, {
			success : function(model, response) {
				$('#ticketComments').empty();
				_.each(response.comments, function(e) {
					$('#ticketComments').append($("<div class='ticketInput ticketComments'></div>").append(e.comment));
				});
			},
			error : function(model, response) {
				console.log(response);
			}
		});
		
	},
	
	render : function() {
		this.$el.append(_.template($('#respondToTicketTemplate').html()));
		this.$el.find('#ticketTitle').append(this.model.get("subject")).append(" - ").append(this.model.get("category"));
		return this;
	},
	
	submit : function() {
		
		var tmpUser = new Backbone.Model({
			userId: sessionStorage.loggedUserId,
		})
		
		var ticketComment = new TicketComment({
			user: tmpUser,
			dateTime: new Date().getTime(),
			comment: $("#ticketResponse").val()
		});
		
		var resp = new RespondToTicketModel({
			ticketId : this.model.get("ticketId"),
			comments : [ticketComment]
		});
		
		resp.save({}, {
			success : function(model, response) {
				console.log(response);
			},
			error : function(model, response) {
				console.log(response);
			},
			wait: true
		});
		//TODO: fix refresh
		this.initialize();
		this.initialize();
		$("#ticketResponse").val('');
	}
		
});
/*! CTS Respond to ticket Model */

var RespondToTicketModel = Backbone.Model.extend({
	
	urlRoot: 'addCommentToTicket/',
	
	defaults: {
		ticketId: '',
		comments: [new Backbone.Model]
	}

});
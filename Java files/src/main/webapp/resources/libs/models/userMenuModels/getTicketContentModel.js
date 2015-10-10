/*! CTS Get ticket content Model */

var GetTicketContentModel = Backbone.Model.extend({
	
	urlRoot: 'getTicket/',
	
	defaults: {
		ticketId: ''
	}

});
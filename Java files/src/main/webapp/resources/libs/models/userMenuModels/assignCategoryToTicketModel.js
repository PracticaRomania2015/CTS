/*! CTS Assign category to ticket Model */

var AssignCategoryToTicket = Backbone.Model.extend({
	
	urlRoot: 'changeTicketCategory/',
	
	defaults: {
		category: new Backbone.Model,
		ticketId: ''
	}

});
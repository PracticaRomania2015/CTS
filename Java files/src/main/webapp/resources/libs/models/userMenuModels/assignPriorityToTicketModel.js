/*! CTS Assign priority to ticket Model */

var AssignPriorityToTicket = Backbone.Model.extend({
	
	urlRoot: 'changeTicketPriority/',
	
	defaults: {
		priority: new Backbone.Model,
		ticketId: ''
	}

});
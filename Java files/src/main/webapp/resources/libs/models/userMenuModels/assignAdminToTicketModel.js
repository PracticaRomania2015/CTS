/*! CTS Assign admin to ticket Model */

var AssignAdminToTicket = Backbone.Model.extend({
	
	urlRoot: 'assignAdminToTicket/',
	
	defaults: {
		assignedToUser: new Backbone.Model,
		ticketId: ''
	}

});
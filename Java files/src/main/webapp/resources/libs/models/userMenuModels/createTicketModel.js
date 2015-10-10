/*! CTS Create ticket Model */

var CreateTicketModel = Backbone.Model.extend({

	urlRoot: 'submitTicket/',
	
	defaults: {
		subject: '',
		category: new Backbone.Model,
		priority: new Backbone.Model,
		comments: [new Backbone.Model]
	},
	
	check: function () {
		
		var errors = {};
		
		if (this.get('subject') == '') {
			errors['#ticketSubject'] = getValue('mandatory');
		} else if (this.get('subject').length > 250) {
			errors['#ticketSubject'] = getValue('invalidTicketSubject');
		} else {
			errors['#ticketSubject'] = '';
		}
		
		return errors;
		
	}
});
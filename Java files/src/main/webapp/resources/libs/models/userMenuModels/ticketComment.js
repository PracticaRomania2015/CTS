/*! CTS Ticket comment Model */

var TicketComment = Backbone.Model.extend({
	
	defaults: {
		user: '',
//		/dateTime: '',
		comment: ''
	},
	
	check: function() {
		
		var errors = {};
		
		if (this.get('comment') == '') {
			errors['#ticketContent'] = getValue('mandatory');
		} else if (this.get('comment').length > 2000){
			errors['#ticketContent'] = getValue('invalidTicketComment');
		} else {
			errors['#ticketContent'] = '';
		};
		
		return errors;
	}
});
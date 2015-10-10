/*! CTS Priority Model */

var Priority = Backbone.Model.extend({ 
	
	defaults: {
		priorityId: '',
		priorityName: ''
	},
	
	check: function() {
		
		var errors = {};
		
		/*if (this.get('priorityId') == '') {
			errors['#priorityId'] = getValue('mandatory');
			errorsFound = true;
		};*/
		
		if (this.get('priorityName') == '') {
			errors['#ticketPriorityDropbox'] = getValue('mandatory');
		} else {
			errors['#ticketPriorityDropbox'] = '';
		};
		
		return errors;
	}
});
/*! CTS Category Model */

var Category = Backbone.Model.extend({ 
	
	defaults: {
		categoryId: '',
		categoryName: '',
		parentCategoryId: ''
	},
	
	check: function() {
		
		var errors = {};
		
		/*if (this.get('categoryId') == '') {
			errors['#categoryId'] = getValue('mandatory');
			errorsFound = true;
		};*/
		
		if (this.get('categoryName') == '') {
			errors['#ticketCategoryDropbox'] = getValue('mandatory');
		} else {
			errors['#ticketCategoryDropbox'] = '';
		};
		
		return errors;
	}
});
/*! CTS Recovery Model */

var RecoveryModel = Backbone.Model.extend({

	urlRoot: 'recovery/',
	
	defaults: {
		email: ''
	},

	check : function () {

		var errors = {};
		
		// email
		var regexp = /([A-Za-z.])+@([A-Za-z])+\.([A-Za-z])+/;
		var regexEmail = new RegExp(regexp);
		if (this.get('email') == '') {
			errors['#email'] = getValue('mandatory');
		} else if (!regexEmail.test(this.get('email'))) {
			errors['#email'] = getValue('invalidEmailFormat');
		} else {
			errors['#email'] = '';
		}

		return errors;
	}
});
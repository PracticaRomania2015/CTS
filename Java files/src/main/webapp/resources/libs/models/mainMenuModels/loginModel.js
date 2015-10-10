/*! CTS Login Model */

var LoginModel = Backbone.Model.extend({

	urlRoot: 'login/',
	
	defaults: {
		email: '',
		password: ''
	},
	
	check: function () {
		
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
		
		// password
		if (this.get('password') == '') {
			errors['#password'] = getValue('mandatory');
		} else {
			errors['#password'] = '';
		}
		
		return errors;
	}
});

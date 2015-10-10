/*! CTS User properties Model */

var UserPropertiesModel = Backbone.Model.extend({

	urlRoot : 'updateUserProperties/',
	
	defaults: {
		userId: '',
		title: '',
		firstName: '',
		lastName: '',
		email: '',
		oldPassword: '',
		password: '',
	},

	check : function () {

		var errors = {};
		
		/* TODO: view implementation
		// firstName
		var regexp = /[-'a-zA-Z ]+/;
		var regexName = new RegExp(regexp);
		if (this.get('firstName') == '') {
			errors['#firstName'] = getValue('mandatory');
		} else if (!regexName.test(this.get('firstName'))) {
			errors['#firstName'] = getValue('registerInvalidNameField');
		} else {
			errors['#firstName'] = '';
		}
		
		// lastName
		if (this.get('lastName') == '') {
			errors['#lastName'] = getValue('mandatory');
		} else if (!regexName.test(this.get('lastName'))) {
			errors['#lastName'] = getValue('registerInvalidNameField');
		} else {
			errors['#lastName'] = '';
		}
		
		// email
		var regexp = /([A-Za-z.])+@([A-Za-z])+\.([A-Za-z])+/;
		var regexEmail = new RegExp(regexp);
		if (this.get('email') == '') {
			errors['#email'] = getValue('mandatory');
		} else if (!regexEmail.test(this.get('email'))) {
			errors['#email'] = getValue('registerInvalidEmailField');
		} else {
			errors['#email'] = '';
		}
		*/
		
		// new password
		var regexp = /[A-Za-z0-9!@#$%^&*()_+]{8,20}/;
		var regexPass = new RegExp(regexp);
		if (this.get('password') == '') {
			errors['#newpassword'] = getValue('mandatory');
		} else if (!regexPass.test(this.get('password'))) {
			errors['#newpassword'] = getValue('registerInvalidPasswordField');
		} else {
			errors['#newpassword'] = '';
		}

		return errors;
	}

});
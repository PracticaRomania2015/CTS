/*! CTS Get admins for category Model */

var getAdminForCategory = Backbone.Model.extend({
	
	urlRoot: 'getAdminsForCategory/',
	
	defaults: {
		categoryId: ''
	}

});
/*! CTS Manage user roles Model */

var ManageUserRoleModel = Backbone.Model.extend({
	
	urlRoot : 'getUserRights/',
	
	defaults: {
		userId: ''
	}

})
/*! CTS Update user roles Model */

var UpdateUserRoleModel = Backbone.Model.extend({

	urlRoot : 'setUserRights/',
	
	defaults: {
		userId: '',
		sysAdmin: '',
		categoryAdminRights: ''
	}

});
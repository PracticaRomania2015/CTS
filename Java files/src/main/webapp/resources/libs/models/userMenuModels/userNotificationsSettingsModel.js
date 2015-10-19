var UserNotificationsSettingsModel = Backbone.Model.extend({
	
	urlRoot: 'updateUserNotificationsSettings/',
	
	defaults: {
		user: new Backbone.Model,
		getEmailForTicketResponse: '',
		categoriesNotificationsSettings: ''
	}
		
});
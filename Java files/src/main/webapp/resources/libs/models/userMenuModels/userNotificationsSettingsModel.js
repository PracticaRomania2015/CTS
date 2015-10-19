var UserNotificationsSettingsModel = Backbone.Model.extend({
	
	urlRoot: 'updateUserNotificationsSettings/',
	
	defaults: {
		userId: '',
		getEmailForTicketResponse: '',
		categoriesNotificationsSettings: ''
	}
		
});
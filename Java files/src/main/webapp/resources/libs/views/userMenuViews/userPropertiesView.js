/*! CTS User Properties View */

var UserPropertiesView = Backbone.View.extend({
	
	viewState: false,
	
	events:{
		'click #changePasswordButton' : 'submitNewPassword'
	},
	
	render: function () {
		this.$el.empty();
		var header = createElem('h1',{class:'userPage'},sessionStorage.loggedUserName + ' properties page');
		var subtitle = createElem('h3',{},'Update security');
		var oldPasswordInput = createElem('input',{id:'oldPassword',class:'validationTooltip savePassword fieldPass',type:'password',placeholder:'Old Password'});
		var newPasswordInput = createElem('input',{id:'newPassword',class:'validationTooltip savePassword fieldPass',type:'password',placeholder:'New Password'});
		var confirmNewPasswordInput = createElem('input',{id:'confirmNewPassword',class:'validationTooltip savePassword fieldPass',type:'password',placeholder:'Confirm New Password'});
		var changePasswordButton = createElem('div',{id:'changePasswordButton'},'Save');
		var changePasswordContainer = createElem('div',{id:'changePasswordContainer'},[oldPasswordInput,newPasswordInput,confirmNewPasswordInput,changePasswordButton]);
		var userPropertiesForm = createElem('form',{class:'propertiesPanelPage'},[changePasswordContainer]);
		this.$el.attr('id','userPropertiesTemplate').attr('class','context');
		this.$el.append(header,subtitle,userPropertiesForm);
		this.setElement(this.$el);
		return this;
	},
	
	submitNewPassword : function () {
		var newPassword = $('#newPassword').val();
		var confirmedNewPassword = $('#confirmNewPassword').val();
		//TODO change this to model confirmation
		//TODO alerts
		if (newPassword != confirmedNewPassword) {
			popNotification("New password doesn't match!");
		} else {
			var userForUpdate = new UserPropertiesModel({
				userId: sessionStorage.loggedUserId,
				title: sessionStorage.loggedUserTitle,
				firstName: sessionStorage.loggedUserFirstName,
				lastName: sessionStorage.loggedUserLastName,
				email: sessionStorage.loggedUserEmail,
				oldPassword: $('#oldPassword').val(),
				password: newPassword
			});
			userForUpdate.save({},{
				success: function (model,response) {
					if (response.type == "success") {
						popNotification("Your password was updated successfully!");
					} else if (response.type == "error") {
						popNotification(response.description);
					} else {
						popNotification("Unknown error!");
					};
				},
				error: function(model, response){
					console.log('Server error!');
				}
			});
		}
	}
});

/* User Properties Panel Page*/
/* ================================================= */
/* User Properties Panel Page view */

var UserPropertiesPageView = GenericUserPanelPageView.extend({
	
	events:{
		'click #changePasswordButton' : 'submitNewPassword'
	},

	appendData : function(){
		//probably gonna be named something different
		this.$el.append("<h1 class='userPage'>" + sessionStorage.loggedUserName + " properties page</h1>");
		this.$el.append(_.template($('#userPropertiesTemplate').html()));
		return this;
	},
	
	submitNewPassword : function(){
		
		var newPassword = $('#newPassword').val();
		var confirmedNewPassword = $('#confirmNewPassword').val();
		
		//TODO change this to model confirmation
		//TODO alerts
		if(newPassword != confirmedNewPassword){
			alert("New password doesn't match!");
		}else{
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
				success: function(model, response){
					if (response.type == "success"){
						alert("Your password was updated successfully!");
					}else {
						if (response.type == "error"){
							alert(response.description);
						} else {
							alert("Unknown error!");
						}
					}
					console.log(response);
				},
				error: function(model, response){
					
				}
			});
		}
	}
})
	
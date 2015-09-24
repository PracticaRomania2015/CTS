/* ================================================= */
/* Log in page view */

var LogInView = GenericFrontPageChildView.extend({
	

	events : {
		'keydown input' : 'submit',
		'click #logInButton' : 'submit'
	},

	appendData : function() {
		this.$el.append(_.template($('#logInTemplate').html()));
	},

	submit : function(e) {
		if ( e.keyCode  === 13 || e.type =="click") { 
			var user = new LogInModel({
				email : $("#logInMail").val(),
				password : $("#logInPass").val()
			});
			
			user.save({}, {
				success : function(model, response) {
					if (response.type == "success") {
						sessionStorage.loggedUserId = response.data.userId;
						sessionStorage.loggedUserRights = response.data.role.roleName;
						sessionStorage.loggedUserName = response.data.firstName + " " +response.data.lastName;
						$('#mainContainer').replaceWith(_.template($('#userPanelPageTemplate').html()));
						userPanelPageFunctionality();
					} else {
						if (response.type == "error"){
							alert(response.description);
						} else {
							alert("Unknown error!");
						}
					}
					
				},
				error : function(model, response) {
					console.log(response);
				}
			});
		}

	}
});
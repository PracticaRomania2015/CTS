/* ================================================= */
/* Log in page view */

var LogInView = GenericFrontPageChildView.extend({

	events : {
		'click #logInButton' : 'submit'
	},

	appendTextBox : function() {
		this.$el.append(_.template($('#logInTemplate').html()));
	},

	submit : function() {
		
		var user = new LogInModel({
			email : $("#logInMail").val(),
			password : $("#logInPass").val()
		});
		
		user.save({}, {
			success : function(model, response) {
				console.log(response);
				if (response.userId) {
					console.log(response.userId);
					sessionStorage.loggedUserId = response.userId;
					$(document).find('#logIn').hide();
					$(document).find('#frontPage').hide();
					$(document).find('#userPanelPage').show();
				} else {
					alert(response.description);
				}
			},
			error : function(model, response) {
				alert(response);
			}
		});
		
	}

});
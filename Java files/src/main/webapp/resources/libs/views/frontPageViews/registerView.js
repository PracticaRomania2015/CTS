/* ================================================= */
/* Register page views */

var RegisterView = GenericFrontPageChildView.extend({

	events : {
		'click #registerButton' : 'submit'
	},

	appendTextBox : function() {
		this.$el.append(_.template($('#registerTemplate').html()));
	},

	submit : function() {

		var user = new RegisterModel({
			title : $("#regTitle").attr("selected", "selected").val(),
			firstName : $("#regFirstName").val(),
			lastName : $("#regLastName").val(),
			email : $("#regMail").val(),
			password : $("#regPass").val()
		});

		user.save({}, {
			success : function(model, response) {
				if (response.description == "Success!") {
					$('#logIn').toggle();
					$('#register').hide();
					$('#recovery').hide();
					alert("Account created!");
				} else {
					alert(response.description);
				}
			},
			error : function(model, response) {
				console.log(response);
			}
		});

	}

});
/* ================================================= */
/* Register page views */

var RegisterView = GenericFrontPageChildView.extend({

	events : {
		'click #registerButton' : 'submit',
		'keydown input' : 'submit'
	},

	appendData : function() {
		this.$el.append(_.template($('#registerTemplate').html()));
	},

	submit : function(e) {
		if ( e.keyCode  === 13 || e.type =="click") { 
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
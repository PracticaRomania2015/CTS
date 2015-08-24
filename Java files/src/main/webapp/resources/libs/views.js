/* ==== FRONT PAGE ==== */
/* =========================================================================================================================== */
/* Generic front page view */

var GenericFrontPageView = Backbone.View.extend({

	render : function() {
		this.$el.append(_.template($('#frontPageTemplate').html()));
		return this;
	}

});


/* ================================================= */
/* Generic front page child view */

var GenericFrontPageChildView = Backbone.View.extend({

	id : "formTemplate",

	tagName : 'div',

	render : function() {
		this.appendTextBox();
		return this;
	}

});


/* ================================================= */
/* Log in page views */

var LogInView = GenericFrontPageChildView.extend({

	urlRoot : 'login/',

	events : {
		'click [id="logInButton"]' : 'submit'
	},

	appendTextBox : function() {
		this.$el.append(_.template($('#logInTemplate').html()));
	},

	submit : function() {
		console.log('Log in button pressed');
		var user = new LogInModel({
			email : $("#logInMail").val(),
			password : $("#logInPass").val()
		});
		user.save({}, {
			success : function(model, response) {
				console.log(model);
				console.log(response);
				// temporary (TODO: redirect)
				alert("Login successfully! UserID: " + response.userId);
			},
			error : function(model, response) {
				console.log(response.responseText);
				alert(response.responseText);
			}
		})
	}

});

var RegisterView = GenericFrontPageChildView.extend({

	urlRoot : 'register/',

	events : {
		'click [id="registerButton"]' : 'submit'
	},

	appendTextBox : function() {
		this.$el.append(_.template($('#registerTemplate').html()));
	},

	submit : function() {
		console.log('Register button pressed');
		var user = new RegisterModel({
			title : $("#regTitle").attr("selected", "selected").val(),
			firstName : $("#regFirstName").val(),
			lastName : $("#regLastName").val(),
			email : $("#regMail").val(),
			password : $("#regPass").val()
		});
		user.save({}, {
			success : function(model, response) {
				console.log(model);
				console.log(response);
				console.log(response.description);
				if (response.description == "Success!") {
					$('#logIn').toggle();
					$('#register').hide();
					$('#recover').hide();
					alert("Account created!");
				} else {
					alert(response.description);
				}
				console.log('success');
			},
			error : function(model, response) {
				console.log(model);
				console.log(response);
				console.log('error');
			}
		});
	}

});

var RecoverView = GenericFrontPageChildView.extend({

	urlRoot : 'recover/',
	
	events : {
		'click [id="recoveryButton"]' : 'submit'
	},

	appendTextBox : function() {
		this.$el.append(_.template($('#recoverTemplate').html()));
	},

	submit : function() {
		console.log('Send email button pressed');
		var user = new RecoverModel({
			email : $("#recoverMail").val()
		});
		var user = new RecoverModel({
			email : $("#recoverMail").val()
		});
		user.save({}, {
			success : function(model, response) {
				console.log(model);
				console.log(response);
				console.log(response.description);
				if (response.description == "Success!") {
					$('#logIn').show();
					$('#register').hide();
					$('#recover').hide();
					alert("Done!");
				} else {
					alert(response.responseText);
				}
				console.log('success');
			},
			error : function(model, response) {
				console.log(model);
				console.log(response);
				console.log('error');
			}

		});
	}
});


/* ==== USER PANEL PAGE ==== */
/* =========================================================================================================================== */
/* Generic user panel page view */

var GenericUserPanelPageView = Backbone.View.extend({

	render : function() {
		this.$el.append(_.template($('#userPanelPageTemplate').html()));
		return this;
	}

});


/* ================================================= */
/* Generic user panel page child view */

var UserTicketsView = GenericUserPanelPageView.extend({
	
	render : function() {
		this.$el.append(_.template($('#userTicketsTemplate').html()));
		return this;
	}

})

/* ================================================= */
/* Create ticket page view */

var CreateTicketPageView = GenericUserPanelPageView.extend({
	
	render : function() {
		this.$el.append(_.template($('#createTicketTemplate').html()));
		return this;
	}
	
});
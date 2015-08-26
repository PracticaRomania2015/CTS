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

	events : {
		'click #logInButton' : 'submit'
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

	events : {
		'click #registerButton' : 'submit'
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

	events : {
		'click #recoveryButton' : 'submit'
	},

	appendTextBox : function() {
		this.$el.append(_.template($('#recoverTemplate').html()));
	},

	submit : function() {
		console.log('Send email button pressed');
		var user = new RecoverModel({
			email : $("#recoverMail").val()
		});
		user.save({}, {
			success : function(model, response) {
				console.log(model);
				alert(response);
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

	events : {
		'click #submitTicketButton' : 'submit',
		'change #ticketCategoryDropbox' : 'populateSubcategories'
	},
	
	render : function() {
		this.$el.append(_.template($('#createTicketTemplate').html()));
		return this;
	},
	
	initialize : function() {
		var request = new TicketCategoriesModel({ });
		request.save({}, {
			success : function(model, response) {
				$('#ticketCategoryDropbox').find('option').remove().end().append('<option selected style="display:none;">Select your category</option>').val('');
				_.each(response, function(e) {
					if (e.parentCategoryId == 0) {
						$('#ticketCategoryDropbox').append(
								$("<option></option>").attr("value",
										e.categoryId).text(e.categoryName));
					}
				});
				
			},
			error : function(model, response) {
				console.log(model);
				console.log(response);
				console.log('error');
			}
		});
	},
	
	populateSubcategories : function() {
		var request = new TicketCategoriesModel({ });
		request.save({}, {
			success : function(model, response) {
				var selectedCategory = $('#ticketCategoryDropbox').val();
				$('#ticketSubcategoryDropbox').find('option').remove().end().append('<option selected style="display:none;">Select your subcategory</option>').val('');
				_.each(response, function(e) {
					if (selectedCategory == e.parentCategoryId) {
						$('#ticketSubcategoryDropbox').append(
								$("<option></option>").attr("value",
										e.categoryId).text(e.categoryName));
					}
				});
			},
			error : function(model, response) {
				console.log(model);
				console.log(response);
				console.log('error');
			}
		})
	},
	
	submit : function() {
		console.log('Submit ticket button pressed');
		var categoryId;
		if ($("#ticketSubcategoryDropbox option:selected").val() != "Select your subcategory"){
			categoryId = $("#ticketSubcategoryDropbox option:selected").val();
		} else {
			categoryId = $("#ticketCategoryDropbox option:selected").val();
		}
		/*
		 * TO DO
		var ticket = new CreateTicketModel({
			subject : $("#ticketSubject").val(),
			category : categoryId,
			description : $("#ticketContent").val()
		});
		ticket.save({}, {
			success : function(model, response) {
				console.log(model);
				console.log(response);
				console.log('success');
				alert("Message sent!");
			},
			error : function(model, response) {
				console.log(model);
				console.log(response);
				console.log('error');
			}
		});*/
	}
	
});
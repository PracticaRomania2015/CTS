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
		console.log(user.toJSON());
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
/* Assigned tickets page view */

var AssignedTicketsView = GenericUserPanelPageView.extend({
	
	render : function() {
		this.$el.append(_.template($('#userTicketsTemplate').html()));
		this.populateData();
		return this;
	},
	
	initialize : function() {
		this.$el.append("<h1 class='userPage'> Tickets for me to review </h1>");
	},
	
	populateData : function() {
		// just some test variables
		var test1 = "asigned";
		var test2 = "categ";
		var test3 = "date";
		var test4 = 1;
		var test5 = 2;
		// how to input the actual data (hint: use each)
		this.$el.find('tbody').append("<tr><td>" +
				test1 +
				"</td><td>" +
				test2 +
				"</td><td>" +
				test3 +
				"</td></tr>");
		// don't forget to input the page numbering as well
		this.$el.find('#ticketPagingNumbering').append(test4 + "/" + test5);
	}

})


/* ================================================= */
/* User tickets page view */

var UserTicketsView = GenericUserPanelPageView.extend({
	
	render : function() {
		this.$el.append(_.template($('#userTicketsTemplate').html()));
		this.populateData();
		return this;
	},
	
	initialize : function() {
		this.$el.append("<h1 class='userPage'> Tickets submitted by me </h1>");
	},
	
	populateData : function() {
		// just some test variables
		var test1 = "mine";
		var test2 = "categ";
		var test3 = "date";
		var test4 = 1;
		var test5 = 4;
		// how to input the actual data (hint: use each)
		this.$el.find('tbody').append("<tr><td>" +
				test1 +
				"</td><td>" +
				test2 +
				"</td><td>" +
				test3 +
				"</td></tr>");
		// don't forget to input the page numbering as well
		this.$el.find('#ticketPagingNumbering').append(test4 + "/" + test5);
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
		var category;
		if ($("#ticketSubcategoryDropbox option:selected").val() != "Select your subcategory"){
			category = $("#ticketSubcategoryDropbox option:selected").val();
		} else {
			category = $("#ticketCategoryDropbox option:selected").val();
		}
		
		var ticketComment = new TicketComment({
			// TO DO : Get ID from Session
			userId: 2,
			dateTime: new Date().getTime(),
			comment: $("#ticketContent").val()
		})

		var ticket = new CreateTicketModel({
			subject : $("#ticketSubject").val(),
			categoryId : category,
			comments : [ticketComment]
		});
		
		console.log(ticket.toJSON());
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
		});
	}
	
});
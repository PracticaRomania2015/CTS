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
		
		var user = new LogInModel({
			email : $("#logInMail").val(),
			password : $("#logInPass").val()
		});
		
		user.save({}, {
			success : function(model, response) {
				// TODO: session redirect
				if (response.userId) {
					alert("Login successfully! UserID: " + response.userId);
				} else {
					alert(response);
				}
			},
			error : function(model, response) {
				alert(response);
			}
		});
		
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
		
		var user = new RegisterModel({
			title : $("#regTitle").attr("selected", "selected").val(),
			firstName : $("#regFirstName").val(),
			lastName : $("#regLastName").val(),
			email : $("#regMail").val(),
			password : $("#regPass").val()
		});
		
		user.save({}, {
			success : function(model, response) {
				if (response == "Success!") {
					$('#logIn').toggle();
					$('#register').hide();
					$('#recover').hide();
					alert("Account created!");
				} else {
					alert(response);
				}
			},
			error : function(model, response) {
				console.log(response);
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
		
		var user = new RecoverModel({
			email : $("#recoverMail").val()
		});
		
		user.save({}, {
			success : function(model, response) {
				alert(response);
			},
			error : function(model, response) {
				console.log(response);
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
		var test3 = "submited";
		var test4 = "date";
		var testpg1 = 1;
		var testpg2 = 2;
		// how to input the actual data (hint: use each)
		this.$el.find('tbody').append("<tr><td>" +
				test1 +
				"</td><td>" +
				test2 +
				"</td><td>" +
				test3 +
				"</td><td>" +
				test4 +
				"</td></tr>");
		// don't forget to input the page numbering as well
		this.$el.find('#ticketPagingNumbering').append(testpg1 + "/" + testpg2);
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
		var test3 = "submited";
		var test4 = "date";
		var testpg1 = 1;
		var testpg2 = 4;
		// how to input the actual data (hint: use each)
		this.$el.find('tbody').append("<tr><td>" +
				test1 +
				"</td><td>" +
				test2 +
				"</td><td>" +
				test3 +
				"</td><td>" +
				test4 +
				"</td></tr>");
		// don't forget to input the page numbering as well
		this.$el.find('#ticketPagingNumbering').append(testpg1 + "/" + testpg2);
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
		
		var categories = new GetCategoriesModel({ });
		
		categories.save({}, {
			success : function(model, response) {
				$('#ticketCategoryDropbox').find('option').remove().end().append('<option selected style="display:none;">Select your category</option>').val('');
				_.each(response, function(e) {
						$('#ticketCategoryDropbox').append($("<option></option>").attr("value", e.categoryId).text(e.categoryName));
					
				});
				
			},
			error : function(model, response) {
				console.log(response);
			}
		});
		
	},
	
	populateSubcategories : function() {
		
		var categories = new GetSubcategoriesModel({
			categoryId : $("#ticketCategoryDropbox option:selected").val(),
			categoryName : $("#ticketCategoryDropbox option:selected").text()
		});
		
		categories.save({}, {
			success : function(model, response) {
				var selectedCategory = $('#ticketCategoryDropbox').val();
				$('#ticketSubcategoryDropbox').find('option').remove().end().append('<option selected style="display:none;">Select your subcategory</option>').val('');
				_.each(response, function(e) {
					$('#ticketSubcategoryDropbox').append($("<option></option>").attr("value", e.categoryId).text(e.categoryName));
				});
			},
			error : function(model, response) {
				console.log(response);
			}
		});
		
	},
	
	submit : function() {
		
		var ticketCategoryName, ticketCategoryId;
		if ($("#ticketSubcategoryDropbox option:selected").val() != "Select your subcategory"){
			ticketCategoryName = $("#ticketSubcategoryDropbox option:selected").text();
			ticketCategoryId = $("#ticketSubcategoryDropbox option:selected").val();
		} else {
			ticketCategoryName= $("#ticketCategoryDropbox option:selected").text();
			ticketCategoryId = $("#ticketCategoryDropbox option:selected").val();
		};
		
		var ticketCategory = new TicketCategory({
			categoryName: ticketCategoryName,
			categoryId: ticketCategoryId
		}, {validate: true});
		
		var ticketComment = new TicketComment({
			// TO DO : Get userId from Session
			userId: 2,
			dateTime: new Date().getTime(),
			comment: $("#ticketContent").val()
		}, {validate: true});
		
		var ticket = new CreateTicketModel({
			subject : $("#ticketSubject").val(),
			category : ticketCategory,
			comments : [ticketComment]
		});
		
		ticket.save({}, {
			success : function(model, response) {
				console.log(response);
				alert("Ticket submitted!");
			},
			error : function(model, response) {
				console.log(response);
			}
		});
		
	}
	
});
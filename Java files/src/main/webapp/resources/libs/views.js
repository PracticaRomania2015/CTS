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
					$('#recover').hide();
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
				alert(response.description);
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
		this.$el.find('#welcomeMessage').replaceWith("<h2 class='userPage'>Welcome to CTS v0.1_alpha</h2>")
		return this;
	}

});


/* ================================================= */
/* Assigned tickets page view */

var AssignedTicketsView = GenericUserPanelPageView.extend({

	events : {
		'change #ticketsPerPage' : function () { this.viewData( 1, "", "" ); },
		'click #firstPageReqBtn' : function () { this.viewData( 1, "", "" ); },
		'click #ticketSearchButton' : function () { this.viewData( 1, $('#ticketSearchBox').val(), $('#ticketSearchDropBox').val() ); },
		'click #lastPageReqBtn' : function () { this.viewData( this.model.get("totalNumberOfPages"), "", "" ); },
		'click #nextPageReqBtn' : function () { this.viewData( this.model.get("requestedPageNumber") + 1, "", "" ); },
		'click #prevPageReqBtn' : function () { this.viewData( this.model.get("requestedPageNumber") - 1, "", "" ); },
		'click .openTicketComments' : 'viewTicketComments'
	},
	
	render : function() {
		this.$el.append("<h1 class='userPage'> Tickets managed by me </h1>");
		this.viewData( 1, "", "" );
		this.$el.append(_.template($('#userTicketsTemplate').html()));
		return this;
	},
	
	viewTicketComments : function (clicked) { 
		var respondToTicketPageView = new RespondToTicketPageView({
			model : new RespondToTicketModel({
				ticketId : $(clicked.currentTarget).attr('id'),
				subject : $(clicked.currentTarget).find('td:nth-child(1)').text(),
				category : $(clicked.currentTarget).find('td:nth-child(2)').text()
			})
		});
		$('div#selectedTicket').empty();
		$('div#selectedTicket').append(respondToTicketPageView.render().el);
		
		$('#assignedTickets').hide();
		$('#myTickets').hide();
		$('#selectedTicket').show();
	},
	
	viewData : function( pgNr, srcTxt, srcTp ) {
		
		if( pgNr > this.model.get("totalNumberOfPages") ){
			pgNr = this.model.get("totalNumberOfPages");
		}
		else if( pgNr < 1 ){
			pgNr = 1;
		}
		
		this.model.unset('tickets');
		this.model.unset('totalNumberOfPages');
		
		this.model.set("user", {"userId": Number(sessionStorage.loggedUserId)});
		this.model.set("typeOfRequest", 1);
		this.model.set("requestedPageNumber", pgNr);
		if ( !this.$el.find('#ticketsPerPage').val() ){
			this.model.set("ticketsPerPage", 10);
		}
		else{
			this.model.set("ticketsPerPage", this.$el.find('#ticketsPerPage').val());
		}
		this.model.set("textToSearch", srcTxt);
		this.model.set("searchType", srcTp);
		var currentView = this;
		
		this.model.save({ }, {
			success : function(model, response) {
				currentView.populateData(pgNr, response.totalNumberOfPages, response.tickets);
			},
			error : function(model, response) {
				console.log(response);
			}
		});
	},
	
	addTicket : function( id, subj, categ, status, userName, ansDate, subDate ) {
		this.$el.find('tbody').append("<tr class='openTicketComments' id='" + id + "'><td><div class='columnOverflow'>" +
				subj + "</div></td><td><div class='columnOverflow'>" +
				categ + "</div></td><td><div class='columnOverflow'>" +
				status + " " + userName + "</div></td><td><div class='columnOverflow'>" + 
				ansDate + "</div></td><td><div class='columnOverflow'>" +
				subDate + "</div></td></tr>");
	},
	
	populateData : function( currentPage, totalPages, tkArray ) {
		function addZero(i) {
		    if (i < 10) {
		        i = "0" + i;
		    }
		    return i;
		}
		
		var currentView = this;
		this.$el.find('tbody').empty();
		_.each(tkArray, function(e) {
			var currentTkDate = new Date(e.comments[0].dateTime);
			var displayDate = currentTkDate.toLocaleDateString() +
							  " " + addZero(currentTkDate.getHours()) + ":" + addZero(currentTkDate.getMinutes());
			var asignedToUser;
			if ( e.state.stateName == "Active" || e.state.stateName == "Closed" ){
				asignedToUser = "";
			}
			else{
				asignedToUser = e.assignedToUserId; // change this 
			}
			currentView.addTicket(e.ticketId, e.subject, e.category.categoryName, e.state.stateName, asignedToUser, "bla",displayDate);
		});
		this.$el.find('#ticketPagingNumbering').empty().append(currentPage + "/" + totalPages);
	}

})


/* ================================================= */
/* User tickets page view */

var UserTicketsView = GenericUserPanelPageView.extend({

	events : {
		'change #ticketsPerPage' : function () { this.viewData( 1, "", "" ); },
		'click #firstPageReqBtn' : function () { this.viewData( 1, "", "" ); },
		'click #ticketSearchButton' : function () { this.viewData( 1, $('#ticketSearchBox').val(), $('#ticketSearchDropBox').val() ); },
		'click #lastPageReqBtn' : function () { this.viewData( this.model.get("totalNumberOfPages"), "", "" ); },
		'click #nextPageReqBtn' : function () { this.viewData( this.model.get("requestedPageNumber") + 1, "", "" ); },
		'click #prevPageReqBtn' : function () { this.viewData( this.model.get("requestedPageNumber") - 1, "", "" ); },
		'click .openTicketComments' : 'viewTicketComments'
	},
	
	render : function() {
		this.$el.append("<h1 class='userPage'> Tickets submitted by me </h1>");
		this.viewData( 1, "", "" );
		this.$el.append(_.template($('#userTicketsTemplate').html()));
		return this;
	},
	
	viewTicketComments : function (clicked) { 
		/*var clickedTicketId = $(clicked.currentTarget).attr('id');
		var clickedTicketSubject = $(clicked.currentTarget).find('td:nth-child(1)').text();
		var clickedTicketCategory = $(clicked.currentTarget).find('td:nth-child(2)').text();
		var clickedTicketStatus = $(clicked.currentTarget).find('td:nth-child(3)').text();*/
		/*var clickedTicket = new GenericModel.extend({
			
		});*/
		
		var respondToTicketPageView = new RespondToTicketPageView({
			model : new RespondToTicketModel({
				ticketId : $(clicked.currentTarget).attr('id'),
				subject : $(clicked.currentTarget).find('td:nth-child(1)').text(),
				category : $(clicked.currentTarget).find('td:nth-child(2)').text()
			})
		});
		$('div#selectedTicket').empty();
		$('div#selectedTicket').append(respondToTicketPageView.render().el);
		
		$('#assignedTickets').hide();
		$('#myTickets').hide();
		$('#selectedTicket').show();
	},
	
	viewData : function( pgNr, srcTxt, srcTp ) {
		
		if( pgNr > this.model.get("totalNumberOfPages") ){
			pgNr = this.model.get("totalNumberOfPages");
		}
		else if( pgNr < 1 ){
			pgNr = 1;
		}
		
		this.model.unset('tickets');
		this.model.unset('totalNumberOfPages');
		
		this.model.set("user", {"userId": Number(sessionStorage.loggedUserId)});
		this.model.set("typeOfRequest", 0);
		this.model.set("requestedPageNumber", pgNr);
		if ( !this.$el.find('#ticketsPerPage').val() ){
			this.model.set("ticketsPerPage", 10);
		}
		else{
			this.model.set("ticketsPerPage", this.$el.find('#ticketsPerPage').val());
		}
		this.model.set("textToSearch", srcTxt);
		this.model.set("searchType", srcTp);
		var currentView = this;
		
		this.model.save({ }, {
			success : function(model, response) {
				currentView.populateData(pgNr, response.totalNumberOfPages, response.tickets);
			},
			error : function(model, response) {
				console.log(response);
			}
		});
	},
	
	addTicket : function( id, subj, categ, status, userName, ansDate, subDate ) {
		this.$el.find('tbody').append("<tr class='openTicketComments' id='" + id + "'><td><div class='columnOverflow'>" +
				subj + "</div></td><td><div class='columnOverflow'>" +
				categ + "</div></td><td><div class='columnOverflow'>" +
				status + " " + userName + "</div></td><td><div class='columnOverflow'>" + 
				ansDate + "</div></td><td><div class='columnOverflow'>" +
				subDate + "</div></td></tr>");
	},
	
	populateData : function( currentPage, totalPages, tkArray ) {
		function addZero(i) {
		    if (i < 10) {
		        i = "0" + i;
		    }
		    return i;
		}
		
		var currentView = this;
		this.$el.find('tbody').empty();
		_.each(tkArray, function(e) {
			var currentTkDate = new Date(e.comments[0].dateTime);
			var displayDate = currentTkDate.toLocaleDateString() +
							  " " + addZero(currentTkDate.getHours()) + ":" + addZero(currentTkDate.getMinutes());
			var asignedToUser;
			if ( e.state.stateName == "Active" || e.state.stateName == "Closed" ){
				asignedToUser = "";
			}
			else{
				asignedToUser = e.assignedToUserId; // change this 
			}
			currentView.addTicket(e.ticketId, e.subject, e.category.categoryName, e.state.stateName, asignedToUser, "bla",displayDate);
		});
		this.$el.find('#ticketPagingNumbering').empty().append(currentPage + "/" + totalPages);
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
			userId: sessionStorage.loggedUserId,
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

/* ================================================= */
/* Respond to ticket page view */

var RespondToTicketPageView = GenericUserPanelPageView.extend({

	events : {
		'click #respondToTicketButton' : 'submit'
	},
	
	initialize : function() {
		
		var ticket = new GetTicket({
			ticketId : this.model.get("ticketId")
		});
		
		ticket.save({}, {
			success : function(model, response) {
				$('#ticketComments').empty();
				_.each(response.comments, function(e) {
					$('#ticketComments').append($("<div class='ticketInput ticketComments'></div>").append(e.comment));
				});
			},
			error : function(model, response) {
				console.log(response);
			}
		});
		
	},
	
	render : function() {
		this.$el.append(_.template($('#respondToTicketTemplate').html()));
		this.$el.find('#ticketTitle').append(this.model.get("subject")).append(" - ").append(this.model.get("category"));
		return this;
	},
	
	submit : function() {
		
		var ticketComment = new TicketComment({
			userId: sessionStorage.loggedUserId,
			dateTime: new Date().getTime(),
			comment: $("#ticketResponse").val()
		});
		
		var resp = new RespondToTicketModel({
			ticketId : this.model.get("ticketId"),
			comments : [ticketComment]
		});
		
		//console.log(resp.toJSON());
		
		resp.save({}, {
			success : function(model, response) {
				console.log(response);
			},
			error : function(model, response) {
				console.log(response);
			},
			wait: true
		});
		//TODO: fix refresh
		this.initialize();
		this.initialize();
		$("#ticketResponse").val('');
	}
		
});

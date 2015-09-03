/* =========================================================================================================================== */
/* Generic model */

var GenericModel = Backbone.Model.extend({});

/* ==== FRONT PAGE ==== */
/* =========================================================================================================================== */
/* Log in page models */

var LogInModel = GenericModel.extend({

	urlRoot : 'login/',

	validate : function(attrs) {

		var ok = false;

		var regexEmail = /^[a-zA-Z0-9]([a-zA-Z0-9-_+.])+\@gmail.com$/;
		$("#logInMail").removeAttr("title");
		$("#logInMail").removeClass("error");
		if (!attrs.email) {
			$("#logInMail").attr("title", getValue("mandatory"));
			$("#logInMail").addClass("error");
			ok = true;
		} else if (!regexEmail.test(attrs.email)) {
			$("#logInMail").attr("title", getValue("validMail"));
			$("#logInMail").addClass("error");
			ok = true;
		}

		$("#logInPass").removeAttr("title");
		$("#logInPass").removeClass("error");
		if (!attrs.password) {
			$("#logInPass").attr("title", getValue("mandatory"));
			$("#logInPass").addClass("error");
			ok = true;
		}

		if (ok) {
			return true;
		}
		return false;
	}

});

var RegisterModel = GenericModel.extend({

	urlRoot : 'register/',

	validate : function(attrs) {

		var ok = false;

		var regexName = /^[-'a-zA-Z ]+$/;
		$("#regFirstName").removeAttr("title");
		$("#regFirstName").removeClass("error");
		if (!attrs.firstName || !regexName.test(attrs.firstName)) {
			$("#regFirstName").attr("title", getValue("registerName"));
			$("#regFirstName").addClass("error");
			ok = true;
		}

		$("#regLastName").removeAttr("title");
		$("#regLastName").removeClass("error");
		if (!attrs.lastName || !regexName.test(attrs.lastName)) {
			$("#regLastName").attr("title", getValue("registerName"));
			$("#regLastName").addClass("error");
			ok = true;
		}

		var regexEmail = /^[a-zA-Z0-9]([a-zA-Z0-9-_+.])+\@gmail.com$/;
		$("#regMail").removeAttr("title");
		$("#regMail").removeClass("error");
		if (!attrs.email || !regexEmail.test(attrs.email)) {
			$("#regMail").attr("title", getValue("registerMail"));
			$("#regMail").addClass("error");
			ok = true;
		}

		var regexPass = /^[A-Za-z0-9!@#$%^&*()_+]{8,20}$/;
		$("#regPass").removeAttr("title");
		$("#regPass").removeClass("error");
		if (!attrs.password || !regexPass.test(attrs.password)) {
			$("#regPass").attr("title", getValue("registerPassword"));
			$("#regPass").addClass("error");
			ok = true;
		}

		$("#regConfPass").removeAttr("title");
		$("#regConfPass").removeClass("error");
		if ($("#regPass").val() != $("#regConfPass").val()) {
			$("#regConfPass").attr("title", getValue("registerSamePassword"));
			$("#regConfPass").addClass("error");
			ok = true;
		}

		if (ok) {
			return true;
		}
		return false;
	}

});

var RecoveryModel = GenericModel.extend({

	urlRoot : 'recoveryPassword/',

	validate : function(attrs) {

		var regexEmail = /^[a-zA-Z0-9]([a-zA-Z0-9-_+.])+\@gmail.com$/;
		$("#recoveryMail").removeAttr("title");
		$("#recoveryMail").removeClass("error");
		if (!attrs.email) {
			$("#recoveryMail").attr("title", getValue("mandatory"));
			$("#recoveryMail").addClass("error");
			return true;
		} else if (!regexEmail.test(attrs.email)) {
			$("#recoveryMail").attr("title", getValue("validMail"));
			$("#recoveryMail").addClass("error");
			return true;
		}
		
		return false;
	}

});

/* ==== USER PANEL PAGE ==== */
/* =========================================================================================================================== */
/* User panel page models */

/* ================================================= */
/* View tickets model */

var ViewTicketsModel = GenericModel.extend({

	urlRoot : 'viewTickets/'

});

/* ================================================= */
/* Create ticket page models */

var GetCategoriesModel = GenericModel.extend({

	urlRoot : 'getCategories/'

});

var GetSubcategoriesModel = GenericModel.extend({

	urlRoot : 'getSubCategories/'

});

var CreateTicketModel = GenericModel.extend({

	urlRoot : 'submitTicket/',

	validate : function(attrs) {
		
		var ok = false;
		
		$("#ticketSubject").removeAttr("title");
		$("#ticketSubject").removeClass("error");
		if (!attrs.subject) {
			$("#ticketSubject").attr("title", getValue("mandatory"));
			$("#ticketSubject").addClass("error");
			ok = true;
		} else if (attrs.subject.length > 50) {
			$("#ticketSubject").attr("title", getValue("ticketSubject"));
			$("#ticketSubject").addClass("error");
			ok = true;
		}
		
		if((attrs.category).validationError){
			ok = true;
		}
		
		if((attrs.comments[0]).validationError){
			ok = true;
		}
		
		if (ok) {
			return true;
		}
		return false;

	}

});

var TicketCategory = GenericModel.extend({ 
	
	validate : function(attrs) {
		
		$("#ticketCategoryDropbox").removeAttr("title");
		$("#ticketCategoryDropbox").removeClass("error");
		if ($("#ticketCategoryDropbox option:selected").val() == "Select your category"){
			$("#ticketCategoryDropbox").addClass("error");
			return true;
		}
		
		return false;
		
	}

});

var TicketComment = GenericModel.extend({
	
	validate : function(attrs) {
		
		$("#ticketContent").removeAttr("title");
		$("#ticketContent").removeClass("error");
		if (!attrs.comment) {
			$("#ticketContent").attr("title", getValue("mandatory"));
			$("#ticketContent").addClass("error");
			return true;
		} else if (attrs.comment.length > 250) {
			$("#ticketContent").attr("title", getValue("ticketComment"));
			$("#ticketContent").addClass("error");
			return true;
		}
		
		return false;

	}

});

/* ================================================= */
/* Respond to ticket page model */

var RespondToTicketModel = GenericModel.extend({
	
	urlRoot: 'addComment/',
	
	validate : function(attrs) {
		
		return false;

	}

});

var GetTicket = GenericModel.extend({
	
	urlRoot: 'viewTicket/'

});


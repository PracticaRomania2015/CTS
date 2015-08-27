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

		var regexEmail = /^[A-Za-z]([a-zA-Z.])+\@gmail.com$/;
		$("#logInMail").removeAttr("title");
		$("#logInMail").removeClass("error");
		if (!attrs.email) {
			$("#logInMail").attr("title", getError("mandatory"));
			$("#logInMail").addClass("error");
			ok = true;
		} else if (!regexEmail.test(attrs.email)) {
			$("#logInMail").attr("title", getError("validMail"));
			$("#logInMail").addClass("error");
			ok = true;
		}

		$("#logInPass").removeAttr("title");
		$("#logInPass").removeClass("error");
		if (!attrs.password) {
			$("#logInPass").attr("title", getError("mandatory"));
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

		var regexUser = /^[-_'a-zA-Z]+$/;
		$("#regFirstName").removeAttr("title");
		$("#regFirstName").removeClass("error");
		if (!attrs.firstName || !regexUser.test(attrs.firstName)) {
			$("#regFirstName").attr("title", getError("registerName"));
			$("#regFirstName").addClass("error");
			ok = true;
		}

		$("#regLastName").removeAttr("title");
		$("#regLastName").removeClass("error");
		if (!attrs.lastName || !regexUser.test(attrs.lastName)) {
			$("#regLastName").attr("title", getError("registerName"));
			$("#regLastName").addClass("error");
			ok = true;
		}

		var regexEmail = /^[A-Za-z]([a-zA-Z.])+\@gmail.com$/;
		$("#regMail").removeAttr("title");
		$("#regMail").removeClass("error");
		if (!attrs.email || !regexEmail.test(attrs.email)) {
			$("#regMail").attr("title", getError("registerMail"));
			$("#regMail").addClass("error");
			ok = true;
		}

		var regexPass = /^[A-Za-z0-9!@#$%^&*()_+]{8,20}$/;
		$("#regPass").removeAttr("title");
		$("#regPass").removeClass("error");
		if (!attrs.password || !regexPass.test(attrs.password)) {
			$("#regPass").attr("title", getError("registerPassword"));
			$("#regPass").addClass("error");
			ok = true;
		}

		$("#regConfPass").removeAttr("title");
		$("#regConfPass").removeClass("error");
		if ($("#regPass").val() != $("#regConfPass").val()) {
			$("#regConfPass").attr("title", getError("registerSamePassword"));
			$("#regConfPass").addClass("error");
			ok = true;
		}

		if (ok) {
			return true;
		}
		return false;
	}

});

var RecoverModel = GenericModel.extend({

	urlRoot : 'recoveryPassword/',

	validate : function(attrs) {

		var ok = false;

		var regexEmail = /^[A-Za-z]([a-zA-Z.])+\@gmail.com$/;
		$("#recoveryMail").removeAttr("title");
		$("#recoveryMail").removeClass("error");
		if (!attrs.email) {
			$("#recoveryMail").attr("title", getError("mandatory"));
			$("#recoveryMail").addClass("error");
			ok = true;
		} else if (!regexEmail.test(attrs.email)) {
			$("#recoveryMail").attr("title", getError("validMail"));
			$("#recoveryMail").addClass("error");
			ok = true;
		}

		if (ok) {
			return true;
		}
		return false;
	}

});

/* ==== USER PANEL PAGE ==== */
/* =========================================================================================================================== */
/* User panel page models */

var AssignedTicketsModel = GenericModel.extend({

	urlRoot : 'assignedTickets/'

});

var UserTicketsModel = GenericModel.extend({

	urlRoot : 'userTickets/'

});

var CreateTicketModel = GenericModel.extend({

	urlRoot : 'submitTicket/',

	validate : function(attrs) {

		console.log("DBG [CreateTicketModel]");
		
		/*var ok = false;
		
		var regexSubject = /^[A-Za-z0-9!@%*()-+:<>?]{1,50}$/;
		$("#ticketSubject").removeAttr("title");
		$("#ticketSubject").removeClass("error");
		if (!attrs.subject) {
			$("#ticketSubject").attr("title", getError("mandatory"));
			$("#ticketSubject").addClass("error");
			ok = true;
		} else if (!regexSubject.test(attrs.subject)) {
			$("#ticketSubject").attr("title", getError("ticketSubject"));
			$("#ticketSubject").addClass("error");
			ok = true;
		}*/
		
		//console.log(attrs.category.isValid());
		//var category = new TicketCategory(attrs.category);

		/*$.each((attrs.category).attributes, function(key, element) {
		    alert('key: ' + key + '\n' + 'value: ' + element);
		});*/
		
		//console.log($.isEmptyObject((attrs.category).attributes));
		
		/*if(!(attrs.category).attributes){
			ok = true;
			console.log((attrs.category).attributes);
			console.log("here");
		}*/
		
		//console.log(attrs.comments[0].isValid());
		//var comments = new TicketComment(attrs.comments[0]);
		//console.log(attrs.comments[0]);
		//console.log(comments);
		/*if(!(attrs.comments[0]).attributes){
			ok = true;
			console.log("here2");
		}
		
		if (ok) {
			return true;
		}*/
		return false;

	}

});

var TicketCategory = GenericModel.extend({ 
	
	validate : function(attrs) {

		console.log("DBG [TicketCategory]");
		
		/*var ok = false;
		
		$("#ticketCategoryDropbox").removeAttr("title");
		$("#ticketCategoryDropbox").removeClass("error");
		if ($("#ticketCategoryDropbox option:selected").val() == "Select your category"){
			$("#ticketCategoryDropbox").addClass("error");
			ok = true;
		}
		
		if (ok) {
			return true;
		}*/
		return false;
		
	}

});

var TicketComment = GenericModel.extend({
	
	validate : function(attrs) {

		console.log("DBG [TicketComment]");
		
		/*var ok = false;
		
		$("#ticketContent").removeAttr("title");
		$("#ticketContent").removeClass("error");
		if (!attrs.comment) {
			$("#ticketContent").attr("title", getError("mandatory"));
			$("#ticketContent").addClass("error");
			ok = true;
			console.log(attrs.comment);
		} else if (attrs.comment.length > 250) {
			$("#ticketContent").attr("title", getError("ticketComment"));
			$("#ticketContent").addClass("error");
			ok = true;
		}
		
		if (ok) {
			return true;
		}*/
		return false;

	}

});

var TicketCategoriesModel = GenericModel.extend({

	urlRoot : 'openSubmitTicketPage/',

	validate : function(attrs) {

		return false;

	}

});

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
		if (!attrs.email || !regexEmail.test(attrs.email)) {
			$("#logInMail").attr("title", getError("loginmail"));
			$("#logInMail").addClass("error");
			ok = true;
		}

		$("#logInPass").removeAttr("title");
		$("#logInPass").removeClass("error");
		if (!attrs.password) {
			$("#logInPass").attr("title", getError("loginpassword"));
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
			$("#regFirstName").attr("title", getError("name"));
			$("#regFirstName").addClass("error");
			ok = true;
		}

		$("#regLastName").removeAttr("title");
		$("#regLastName").removeClass("error");
		if (!attrs.lastName || !regexUser.test(attrs.lastName)) {
			$("#regLastName").attr("title", getError("name"));
			$("#regLastName").addClass("error");
			ok = true;
		}

		var regexEmail = /^[A-Za-z]([a-zA-Z.])+\@gmail.com$/;
		$("#regMail").removeAttr("title");
		$("#regMail").removeClass("error");
		if (!attrs.email || !regexEmail.test(attrs.email)) {
			$("#regMail").attr("title", getError("email"));
			$("#regMail").addClass("error");
			ok = true;
		}

		var regexPass = /^[A-Za-z0-9!@#$%^&*()_+]{8,20}$/;
		$("#regPass").removeAttr("title");
		$("#regPass").removeClass("error");
		if (!attrs.password || !regexPass.test(attrs.password)) {
			$("#regPass").attr("title", getError("password"));
			$("#regPass").addClass("error");
			ok = true;
		}
		
		$("#regConfPass").removeAttr("title");
		$("#regConfPass").removeClass("error");
		if ($("#regPass").val() != $("#regConfPass").val()) {
			$("#regConfPass").attr("title", "Passwords are not the same");
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

	urlRoot : 'recover/',

	validate : function(attrs) {

		var ok = false;

		var regexEmail = /^[A-Za-z]([a-zA-Z.])+\@gmail.com$/;
		$("#recoveryMail").removeAttr("title");
		$("#recoveryMail").removeClass("error");
		if (!attrs.email || !regexEmail.test(attrs.email)) {
			$("#recoveryMail").attr("title", getError("recovermail"));
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

// TO DO

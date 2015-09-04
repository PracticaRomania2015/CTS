/* ==== FRONT PAGE ==== */
/* =========================================================================================================================== */
/* Log in model */

var LogInModel = Backbone.Model.extend({

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
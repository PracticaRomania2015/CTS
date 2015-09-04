/* ==== FRONT PAGE ==== */
/* =========================================================================================================================== */
/* Register model */

var RegisterModel = Backbone.Model.extend({

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

		var regexEmail = /([A-Za-z.])*@([Gg][Mm][Aa][Ii][Ll])[.]([Cc][Oo][Mm])*\w+$/;
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
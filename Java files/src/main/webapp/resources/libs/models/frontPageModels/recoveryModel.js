/* ==== FRONT PAGE ==== */
/* =========================================================================================================================== */
/* Recovery model */

var RecoveryModel = Backbone.Model.extend({

	urlRoot : 'recoveryPassword/',

	validate : function(attrs) {

		var regexEmail = /([A-Za-z.])*@([Gg][Mm][Aa][Ii][Ll])[.]([Cc][Oo][Mm])*\w+$/;
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
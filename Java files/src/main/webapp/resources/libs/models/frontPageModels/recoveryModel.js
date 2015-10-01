/* ==== FRONT PAGE ==== */
/* =========================================================================================================================== */
/* Recovery model */

var RecoveryModel = Backbone.Model.extend({

	urlRoot : 'recovery/',

	validate : function(attrs) {

		var regexEmail = /([A-Za-z.])+@([A-Za-z])+\.([A-Za-z])+/;
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
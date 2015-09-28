/* ==== USER PANEL PAGE ==== */
/* ================================================= */
/* Create ticket model */

var CreateTicketModel = Backbone.Model.extend({

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
		
		if((attrs.priority).validationError){
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
/* ==== USER PANEL PAGE ==== */
/* ================================================= */
/* Ticket comment model */

var TicketComment = Backbone.Model.extend({
	
	validate : function(attrs) {
		
		$("#ticketContent").removeAttr("title");
		$("#ticketContent").removeClass("error");
		if (!attrs.comment) {
			$("#ticketContent").attr("title", getValue("mandatory"));
			$("#ticketContent").addClass("error");
			return true;
		} else if (attrs.comment.length > 2000) {
			$("#ticketContent").attr("title", getValue("ticketComment"));
			$("#ticketContent").addClass("error");
			return true;
		}
		
		return false;

	}

});
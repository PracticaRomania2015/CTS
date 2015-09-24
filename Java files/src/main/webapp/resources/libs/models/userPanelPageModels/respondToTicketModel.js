/* ==== USER PANEL PAGE ==== */
/* ================================================= */
/* Respond to ticket model */

var RespondToTicketModel = Backbone.Model.extend({
	
	urlRoot: 'addCommentToTicket/',
	
	validate : function(attrs) {
		
		return false;

	}

});
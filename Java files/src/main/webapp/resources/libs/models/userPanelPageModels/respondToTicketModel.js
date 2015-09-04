/* ==== USER PANEL PAGE ==== */
/* ================================================= */
/* Respond to ticket model */

var RespondToTicketModel = Backbone.Model.extend({
	
	urlRoot: 'addComment/',
	
	validate : function(attrs) {
		
		return false;

	}

});
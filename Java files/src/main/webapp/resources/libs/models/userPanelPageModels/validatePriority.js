/* ==== USER PANEL PAGE ==== */
/* ================================================= */
/* ticket category model */

var ValidatePriority = Backbone.Model.extend({ 
	
	validate : function(attrs) {
		
		$("#ticketPriorityDropbox").removeAttr("title");
		$("#ticketPriorityDropbox").removeClass("error");
		if ($("#ticketPriorityDropbox option:selected").val() == ""){
			$("#ticketPriorityDropbox").addClass("error");
			return true;
		}
		
		return false;
		
	}

});
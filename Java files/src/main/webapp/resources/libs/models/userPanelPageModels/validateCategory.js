/* ==== USER PANEL PAGE ==== */
/* ================================================= */
/* ticket category model */

var ValidateCategory = Backbone.Model.extend({ 
	
	validate : function(attrs) {
		
		$("#ticketCategoryDropbox").removeAttr("title");
		$("#ticketCategoryDropbox").removeClass("error");
		if ($("#ticketCategoryDropbox option:selected").val() == ""){
			$("#ticketCategoryDropbox").addClass("error");
			return true;
		}
		
		return false;
		
	}

});
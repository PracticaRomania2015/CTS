/* =========================================================================================================================== */
/* Generic system admin panel page view */

var GenericSystemAdminPanelPageView = Backbone.View.extend({

	tagName : "div",
	
	id : "systemAdminPageContainer",
	
	render : function() {
		this.appendData();
		return this;
	}

});
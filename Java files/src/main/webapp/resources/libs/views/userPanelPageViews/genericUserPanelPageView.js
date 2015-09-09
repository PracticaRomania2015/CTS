/* =========================================================================================================================== */
/* Generic user panel page view */

var GenericUserPanelPageView = Backbone.View.extend({

	tagName : "div",
	
	id : "userPanelPageContainer",
	
	render : function() {
		this.appendData();
		return this;
	}

});

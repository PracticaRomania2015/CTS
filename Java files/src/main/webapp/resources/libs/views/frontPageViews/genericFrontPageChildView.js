/* ================================================= */
/* Generic front page child view */

var GenericFrontPageChildView = Backbone.View.extend({

	tagName : "div",
	
	id : "frontPageContainer",
	
	render : function() {
		this.appendData();
		return this;
	}

});
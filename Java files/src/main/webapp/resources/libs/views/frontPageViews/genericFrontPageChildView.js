/* ================================================= */
/* Generic front page child view */

var GenericFrontPageChildView = Backbone.View.extend({

	id : "formTemplate",

	tagName : 'div',

	render : function() {
		this.appendTextBox();
		return this;
	}

});
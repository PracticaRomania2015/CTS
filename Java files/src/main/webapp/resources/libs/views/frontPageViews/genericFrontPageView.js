/* ==== FRONT PAGE ==== */
/* =========================================================================================================================== */
/* Generic front page view */

var GenericFrontPageView = Backbone.View.extend({

	render : function() {
		this.$el.append(_.template($('#frontPageTemplate').html()));
		return this;
	}

});
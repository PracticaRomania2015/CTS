/* =========================================================================================================================== */
/* Generic user panel page view */

var GenericUserPanelPageView = Backbone.View.extend({

	render : function() {
		this.$el.append(_.template($('#userPanelPageTemplate').html()));
		this.$el.find('#welcomeMessage').replaceWith("<h2 class='userPage'>Welcome to CTS v0.1_alpha</h2>")
		return this;
	}

});

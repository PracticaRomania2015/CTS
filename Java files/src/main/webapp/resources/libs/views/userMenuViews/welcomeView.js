/*! CTS Welcome View */

var WelcomeView = Backbone.View.extend({
	
	viewState: false,
	
	render: function() {
		preloadView();
		this.$el.empty();
		var title = createElem('h1',{class:'userPage'},'Welcome !');
		var subtitle = createElem('h2',{class:'userPage'},'CTS Version: 1.00_BETA');
		this.$el.attr('id','welcomePageTemplate').attr('class','context');
		this.$el.append(title,subtitle);
		this.setElement(this.$el);
		return this;
	}
});

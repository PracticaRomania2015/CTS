/* ================================================= */
/* Create ticket page view */

var UserPropertiesPageView = GenericUserPanelPageView.extend({
	
	appendData : function() {
		this.$el.append(_.template($('#userPropertiesTemplate').html()));
		return this;
	}
	
});

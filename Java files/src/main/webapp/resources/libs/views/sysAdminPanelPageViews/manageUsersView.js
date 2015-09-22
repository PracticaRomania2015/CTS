/* System Admin Panel Page*/
/* ================================================= */
/* Manage Users Page view */

var ManageUsersView = GenericUserPanelPageView.extend({

	appendData : function(){
		this.$el.append("<h1 class='userPage'> Manage Users </h1>");
		this.$el.append(_.template($('#manageUsersTemplate').html()));
		return this;
	}
})
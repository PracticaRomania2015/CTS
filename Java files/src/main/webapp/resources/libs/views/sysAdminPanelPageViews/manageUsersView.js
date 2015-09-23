/* System Admin Panel Page*/
/* ================================================= */
/* Manage Users Page view */

var ManageUsersView = GenericUserPanelPageView.extend({

	appendData : function(){
		this.$el.append("<h1 class='userPage'> Manage Users </h1>");
		this.viewData(1, "", "");
		this.$el.append(_.template($('#manageUsersTemplate').html()));
		return this;
	},
	
	events: {
		'change #usersPerPage' : function() {
			this.viewData(1, $('#usersSearchBox').val(), $('#usersSearchDropBox').val());
		},
		'click #firstPageReqBtn' : function() {
			this.viewData(1, $('#usersSearchBox').val(), $('#usersSearchDropBox').val());
		},
		'click #usersSearchButton' : function() {
			this.viewData(1, $('#usersSearchBox').val(), $('#usersSearchDropBox').val());
		},
		'click #lastPageReqBtn' : function() {
			this.viewData(this.model.get("totalNumberOfPages"), $('#usersSearchBox').val(), $('#usersSearchDropBox').val());
		},
		'click #nextPageReqBtn' : function() {
			this.viewData(this.model.get("requestedPageNumber") + 1, $('#usersSearchBox').val(), $('#usersSearchDropBox').val());
		},
		'click #prevPageReqBtn' : function() {
			this.viewData(this.model.get("requestedPageNumber") - 1, $('#usersSearchBox').val(), $('#usersSearchDropBox').val());
		},
		'click .openUser' : 'manageUser'
	},
	
	viewData: function(pageNumber, searchText, searchType){
		if(pageNumber > this.model.get("totalNumberOfPages")){
			pageNumber = this.model.get("totalNumberOfPages");
		}else if(pageNumber < 1){
			pageNumber = 1;
		}
		
		this.model.unset('users');
		this.model.unset('totalNumberOfPages');
		
		//to be continuedl
		this.model.set("user")
		
	},
	
	//viewTicketComments
	manageUser: function(clicked){},
	
	addUser: function(userId, firstName, lastName, email, role){
		//this might now show the id on the page
		this.$el.find('tbody').append(
				"<tr class='openUser' id='" + userId
					+ "'><td><div class='columnOverflow'>" + firstName
					+ "</div></td><td><div class='columnOverflow'>" + lastName
					+ "</div></td><td><div class='columnOverflow'>" + email
					+ "</div></td><td><div class='columnOverflow'>" + role
					+ "</div></td></tr");
	},
	
	populateData : function(currentPage, totalPages, usersArray){
		var currentView = this;
		this.$el.find('tbody').empty();
		_.each(usersArray,
				function(e){
					currentView.addUser(e.userId, e.firstName, e.lastName, e.email, e.role);
		});
		this.$el.find('#usersPagingNumbering').empty().append(currentPage + "/" + totalPages)
	}
})
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
		
		var self = this.model;
		
		if(pageNumber > this.model.get("totalNumberOfPages")){
			pageNumber = this.model.get("totalNumberOfPages");
		}else if(pageNumber < 1){
			pageNumber = 1;
		}
		
		this.model.unset('users');
		this.model.unset('totalNumberOfPages');
		
		this.model.set("requestedPageNumber", pageNumber);
		
		if(!this.$el.find('#usersPerPage').val()){
			this.model.set("usersPerPage", 10);
		}else{
			this.model.set("usersPerPage", this.$el.find('#usersPerPage').val());
		}
		this.model.set("textToSearch", searchText);
		this.model.set("searchType", searchType);
		
		var currentView = this;
		
		console.log(this.model);
		
		this.model.save({},{
			success: function(model,response){
				if (response.type == "success"){
					self.set("totalNumberOfPages", response.data[0]);
					self.set("users", response.data[1]);
					currentView.populateData(pageNumber, response.data[0], response.data[1]);
					self.unset("data");
					self.unset("type");
					self.unset("description");
				} else {
					if (response.type == "error"){
						alert(response.description);
					} else {
						alert("Unknown error!");
					}
				}
			},
			error: function(model, response){
				console.log(response);
			}
		})
		
	},
	
	//viewTicketComments
	manageUser: function(clicked){
		var manageUserRoleView = new ManageUserRoleView({
			model: new ManageUserRoleModel({
				userId: $(clicked.currentTarget).attr('id')
			})
		});
		
		$('#userPanelPageContainer').replaceWith(manageUserRoleView.render().el);
		setManageUsersViewToNull();
	},
	
	addUser: function(userId, firstName, lastName, email, role){
		this.$el.find('tbody').append(
				"<tr class='openUser' id='" + userId
					+ "'><td><div class='columnOverflow'>" + userId
					+ "</div></td><td><div class='columnOverflow'>" + firstName
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
					//change e.role stuff after the db change
					currentView.addUser(e.userId, e.firstName, e.lastName, e.email, e.role.roleName);
		});
		this.$el.find('#usersPagingNumbering').empty().append(currentPage + "/" + totalPages)
	}
})
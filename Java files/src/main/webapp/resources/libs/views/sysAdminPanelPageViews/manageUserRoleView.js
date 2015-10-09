/* System Admin Panel Page*/
/* ================================================= */
/* Manage User role Page view */

var ManageUserRoleView = GenericUserPanelPageView.extend({
	
	events:{
		'click #updateUserRoles' : 'submit'
	},
	
	appendData: function(){
		this.$el.append("<h1 class='userPage'> Manage "+this.userName+" Roles </h1>");
		this.$el.append(_.template($('#manageUserRoleTemplate').html()));
		return this;
	},
	
	initialize: function(options){
		
		var that = this;
		this.userName = options.userName;
		
		this.model.save({},{
			success: function(model, response){
				if(response.type == "success"){
					that.populateData(response.data);
				} else {
					if (response.type == "error"){
						popNotification(response.description);
					} else {
						popNotification("Unknown error!");
					}
				}
			},
			error: function(model, response){
				console.log(response);
			}
		});
	},
	
	addManagedCategory: function(adminStatus, categoryId, categoryName){
		var sysAdmin = adminStatus?"checked":"";
		this.$el.find('tbody').append("<tr><td><div class='columnOverflow'>" + categoryName
										+ "</div></td><td><div class='columnOverflow'><input type='checkbox' class='checkboxClassId' id='"+categoryId+"' "+ sysAdmin +"/>"
										+ "</div></td>");
		
	},
	
	populateData: function(userRights){
		var that = this;
		var checked = userRights.sysAdmin?"checked":"";
		$('#sysAdminDiv').empty().append("<input type='checkbox' id='sysAdminCheckbox'  " + checked + ">Set user as root admin</input>");
		$('#sysAdminCheckbox').click(function(){
			that.model.attributes.data.sysAdmin = !that.model.attributes.data.sysAdmin;
			console.log(that.model.attributes.data.sysAdmin);
		});
		this.$el.find('tbody').empty();
		_.each(userRights.categoryAdminRights,
				function(e){
					that.addManagedCategory(e.adminStatus, e.category.categoryId, e.category.categoryName);
					$("#" + e.category.categoryId).click(function(){
						e.adminStatus = !e.adminStatus;
						console.log(e.adminStatus);
					});
		})
	},
	
submit: function(){
		
		var userUpdate = new UpdateUserRoleModel({
			userId : this.model.get("data").userId,
			sysAdmin : this.model.get("data").sysAdmin,
			categoryAdminRights : this.model.get("data").categoryAdminRights,
			
		});
		
		userUpdate.save({},{
			success: function(model,response){
				if(response.type == "success"){
					if(sessionStorage.loggedUserId == userUpdate.attributes.userId && !userUpdate.attributes.sysAdmin){
						popNotification("Your SysAdmin rights have been revoked! Please login to continue!");
						$('#btn-logOut').trigger('click');
					}else{
						popNotification("The user was successfully updated!");
					}
				}else{
					if (response.type == "error"){
						popNotification(response.description);
					} else {
						popNotification("Unknown error!");
					}
				}
				
			},
			error: function(model, response){
				alert(response);
			}
		})
	}
	
})

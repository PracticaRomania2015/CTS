/* System Admin Panel Page*/
/* ================================================= */
/* Manage User role Page view */

var ManageUserRoleView = GenericUserPanelPageView.extend({
	
	events:{
		'click #updateUserRoles' : 'submit'
	},
	
	appendData: function(){
		this.$el.append("<h1 class='userPage'> Manage User Roles </h1>");
		this.$el.append(_.template($('#manageUserRoleTemplate').html()));
		return this;
	},
	
	initialize: function(){
		
		var that = this;
		
		this.model.save({},{
			success: function(model, response){
				if(response.type == "success"){
					that.populateData(response.data);
				}
			},
			error: function(model, response){
				console.log(response);
			}
		});
	},
	
	addManagedCategory: function(adminStatus, categoryId, categoryName){
		var sysAdmin = adminStatus?"checked":"";
		this.$el.find('tbody').append("<tr><td><div class='columnOverflow'>"
										+ "<input type='checkbox' class='checkboxClassId' value='sysAdmin' id='"+categoryId+"' "+ sysAdmin +"/>"
										+ "</div></td><td><div class='columnOverflow'>" + categoryId
										+ "</div></td><td><div class='columnOverflow'>" + categoryName
										+ "</div></td></tr>");
		
	},
	
	populateData: function(userRights){
		var that = this;
		var checked = userRights.sysAdmin?"checked":"";
		$('#sysAdminDiv').empty().append("<input type='checkbox' id='sysAdminCheckbox' value='sysAdmin' " + checked + ">isSysAdmin</input>");
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
		
		console.log(this.model);
		
		var userUpdate = new UpdateUserRoleModel({
			userId : this.model.get("data").userId,
			sysAdmin : this.model.get("data").sysAdmin,
			categoryAdminRights : this.model.get("data").categoryAdminRights,
			
		});
		console.log(userUpdate);
		
		userUpdate.save({},{
			success: function(model,response){
				console.log(response);
			},
			error: function(model,response){
				console.log(response);
			}
		});
		
	}
	
})

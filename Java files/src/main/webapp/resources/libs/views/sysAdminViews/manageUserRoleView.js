/*! CTS Manage User Roles View */

var ManageUserRoleView = Backbone.View.extend({
	
	viewState: false,
	
	events: {
		'click #updateUserRoles': 'submit',
		'mouseup ' : function(e){hideNotification(e)}
	},
	
	render: function () {
		preloadView();
		this.$el.empty();
		//TODO: no id for tableHeadElem2?
		var header = createElem('h1',{class:'userPage'},'Manage User Roles for' + ' ' + this.model.get('name'));
		this.model.unset('name');
		
		// Manage notifications
		var tableHeadElem1 = createElem('th',{class:'wide-col rounded-tl'},'Category Name');
		var tableHeadElem2 = createElem('th',{id:'', class:'wide-col rounded-tr'},'Check');
		var tableHeadRow = createElem('tr',{},[tableHeadElem1,tableHeadElem2]);
		var tableHeader = createElem('thead',{},[tableHeadRow]);

		var tableBody = createElem('tbody',{id:'userRolesBody'});

		var sysAdminDiv = createElem('div',{id:'sysAdminDiv'});
		var tableFootColumn = createElem('th',{id:'rounded-bot',colspan:'2'},[sysAdminDiv]);
		var tableFootRow = createElem('tr',{},[tableFootColumn]);
		var tableFooter = createElem('tfoot',{},[tableFootRow]);

		var table = createElem('table',{class:'userRoles'},[tableHeader,tableBody,tableFooter]);
		
		var updateUserRolesButton = createElem('div',{id:'updateUserRoles'},'Submit');

		var manageUserContainer = createElem('div',{id:'manageUserContainer'},[table,updateUserRolesButton]);

		this.$el.attr('id','manageUserRoleTemplate').attr('class','context');
		this.$el.append(header,manageUserContainer);
		this.setElement(this.$el);
		this.loadPageData();
		return this;
	},
	
	loadPageData: function(){
		var currentView = this;
		var currentModel = this.model;
		currentModel.save({},{
			async: true,
			success: function (model, response) {
				if(response.type == 'success') {
					var checked = response.data.sysAdmin ? "checked" : "";
					$('#sysAdminDiv').empty().append("<input type='checkbox' id='sysAdminCheckbox' value='sysAdmin' " + checked + ">System Administrator Rights</input>");
					$('#sysAdminCheckbox').click(function(){
						currentModel.attributes.data.sysAdmin = !currentModel.attributes.data.sysAdmin;
					});
					$('#userRolesBody').empty();
					_.each(response.data.categoryAdminRights, function (e) {
						currentView.addManagedCategory(e.adminStatus, e.category.categoryId, e.category.categoryName);
						$("#" + e.category.categoryId).click(function(){
							e.adminStatus = !e.adminStatus;
						});
					})
				} else if (response.type == 'error') {
					popNotification(response.description);
				} else {
					popNotification('Unknown error!');
				};
			},
			error: function (model, response) {
				console.log('Server error!');
			}
		});
	},
	
	addManagedCategory: function (adminStatus, categoryId, categoryName) {
		var isChecked = adminStatus ? true : false;
		var categoryNameElem = createElem('div',{class:'columnOverflow'},categoryName);
		var tableCategoryName = createElem('td',{},[categoryNameElem]);
		var checkBox = createElem('input',{id:categoryId,class:'checkboxClassId',type:'checkbox',checked:isChecked,});
		var checkBoxWrapper = createElem('div',{class:'columnOverflow'},checkBox);
		var tableCheckbox = createElem('td',{},[checkBoxWrapper]);
		var tableRow = createElem('tr',{},[tableCategoryName,tableCheckbox]);
		$('#userRolesBody').append(tableRow);
	},
	
	submit: function () {
		var currentModel = this.model;
		var userUpdate = new UpdateUserRoleModel({
			userId : currentModel.get("data").userId,
			sysAdmin : currentModel.get("data").sysAdmin,
			categoryAdminRights : currentModel.get("data").categoryAdminRights,
		});
		userUpdate.save({},{
			success: function (model,response) {
				if (response.type == "success") {
					if (sessionStorage.loggedUserId == userUpdate.attributes.userId && !userUpdate.attributes.sysAdmin) {
						popNotification("Your System Administrator rights have been revoked! Please login to continue!");
						$('#buttonLogout').click();
					} else {
						popNotification("The user was successfully updated!");
					}
				} else if (response.type == "error"){
					popNotification(response.description);
				} else {
					popNotification("Unknown error!");
				};
			},
			error: function(model, response){
				console.log('Server error!');
			}
		});
	}
})

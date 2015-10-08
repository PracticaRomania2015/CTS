/* System Admin Panel Page*/
/* ================================================= */
/* Manage Categories Page view */

var ManageCategoriesView = GenericUserPanelPageView.extend({

	appendData : function(){
		this.$el.append("<h1 class='userPage'> Manage Categories </h1>");
		this.$el.append(_.template($('#manageCategoriesTemplate').html()));
		return this;
	},
	
	events : {
		'click #addCategoryButton' : 'submitCategory',
		'click #addSubcategoryButton' : 'submitSubcategory',
		'click #removeCategoryButton' : 'removeCategory',
		'click #removeSubcategoryButton' : 'removeSubcategory',
		'click #editCategoryButton' : 'editCategory',
		'click #editSubcategoryButton' : 'editSubcategory',
		'change #categoryListDropboxRemoveSubcategory' : 'populateSubcategoriesForRemoveSubcategory',
		'change #categoryListDropboxEditSubcategory' : 'populateSubcategoriesForEdit'
	},
	
	initialize: function(){
		var categories = new GetCategoriesModel({});
		
		var that = this;
		
		categories.save({},{
			success: function(model, response){
				if (response.type == "success") {
					$('#userPanelPageContainer').empty();
					that.appendData();
					_.each(response.data,function(e){
						$('#categoryListDropboxSubcategory').append($("<option></option>").attr("value", e.categoryId).text(e.categoryName));
						$('#categoryListDropboxRemove').append($("<option></option>").attr("value", e.categoryId).text(e.categoryName));
						$('#categoryListDropboxEditCategory').append($("<option></option>").attr("value", e.categoryId).text(e.categoryName));
						$('#categoryListDropboxEditSubcategory').append($("<option></option>").attr("value", e.categoryId).text(e.categoryName));
						$('#categoryListDropboxRemoveSubcategory').append($("<option></option>").attr("value", e.categoryId).text(e.categoryName));
						that.populateCategoryRow(e.categoryId, e.categoryName);
						
					});
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
	
	addCategory: function(categoryName){
		this.$el.find('tbody').append("<tr><td><div class='columnOverflow'>" + categoryName
										+ "</div></td></tr>");
	},
	
	populateCategoryRow : function(categoryId, categoryName){
		this.addCategory(categoryName);
		
		var subcategories = new GetSubcategoriesModel({
			categoryId : categoryId
		})
		
		var that = this;
		
		//TODO append child subcategory to parent category accordingly
		subcategories.save({},{
			success: function(model, response){
				if(response.type == "success"){
					if(!$.isEmptyObject(response.data)){
						_.each(response.data, function(e){
							that.addCategory(e.categoryName);
						})
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
				console.log(response);
			}
		})
		
	},
	
	populateSubcategoriesForRemoveSubcategory : function() {	
		var categories = new GetSubcategoriesModel({
			categoryId : $("#categoryListDropboxRemoveSubcategory option:selected").val(),
			categoryName : $("#categoryListDropboxRemoveSubcategory option:selected").text()
		});
		
		categories.save({}, {
			success : function(model, response) {
				if (response.type == "success") {
					var selectedCategory = $('#categoryListDropboxRemoveSubcategory').val();
					$('#subcategoryListDropboxRemoveSubcategory').find('option').remove().end().append('<option selected style="display:none;">Select the subcategory...</option>').val('');
					if ($.isEmptyObject(response.data)) {
						$('#subcategoryListDropboxRemoveSubcategory').attr("disabled", true);
					} else {
						_.each(response.data, function(e) {
							$('#subcategoryListDropboxRemoveSubcategory').append($("<option></option>").attr("value", e.categoryId).text(e.categoryName));
						});
						$('#subcategoryListDropboxRemoveSubcategory').attr("disabled", false);
					}
				} else {
					if (response.type == "error"){
						popNotification(response.description);
					} else {
						popNotification("Unknown error!");
					}
				}
			},
			error : function(model, response) {
				console.log(response);
			}
		});
	},
	
	populateSubcategoriesForEdit : function(){
		var categories = new GetSubcategoriesModel({
			categoryId : $("#categoryListDropboxEditSubcategory option:selected").val(),
			categoryName : $("#categoryListDropboxEditSubcategory option:selected").text()
		});
		
		categories.save({}, {
			success : function(model, response) {
				if (response.type == "success") {
					var selectedCategory = $('#categoryListDropboxEditSubcategory').val();
					$('#subcategoryListDropboxEditSubcategory').find('option').remove().end().append('<option selected style="display:none;">Select the subcategory...</option>').val('');
					if ($.isEmptyObject(response.data)) {
						$('#subcategoryListDropboxEditSubcategory').attr("disabled", true);
					} else {
						_.each(response.data, function(e) {
							$('#subcategoryListDropboxEditSubcategory').append($("<option></option>").attr("value", e.categoryId).text(e.categoryName));
						});
						$('#subcategoryListDropboxEditSubcategory').attr("disabled", false);
					}
				} else {
					if (response.type == "error"){
						popNotification(response.description);
					} else {
						popNotification("Unknown error!");
					}
				}
			},
			error : function(model, response) {
				console.log(response);
			}
		});
	},
	
	submitCategory : function(){
		var category = new AddCategoryModel({
			categoryName : $('#categoryName').val()
		});
		
		category.save({},{
			async: false,
			success: function(model,response){
				if(response.type == "success"){
					popNotification("The new category was added successfully!");
				}else{
					if (response.type == "error"){
						popNotification(response.description);
					} else {
						popNotification("Unknown error!");
					}
				};
			},
			error: function(model,response){
				console.log(response);
			}
		});

		this.initialize();
	},
	
	submitSubcategory : function(){
		var categoryName = $("#categoryListDropboxSubcategory option:selected").text();
		var categoryId = $("#categoryListDropboxSubcategory option:selected").val();
		
		var subcategory = new AddSubcategoryModel({
			categoryName : $("#subcategoryName").val(),
			parentCategoryId : $("#categoryListDropboxSubcategory option:selected").val()
		});
		
		subcategory.save({},{
			async: false,
			success: function(model, response){
				if(response.type == "success"){
					popNotification("The new subcategory was added successfully!");
				}else{
					if (response.type == "error"){
						popNotification(response.description);
					} else {
						popNotification("Unknown error!");
					}
				};
			},
			error: function(model, response){
				console.log(response);
			}
		});

		this.initialize();
	},
	
	editCategory : function() {
		var category = new EditCategoryModel({
			categoryId : $("#categoryListDropboxEditCategory option:selected").val(),
			categoryName : $('#categoryNewName').val()
		});
		
		
		category.save({},{
			async: false,
			success: function(model,response){
				if(response.type == "success"){
					popNotification("The category was updated successfully!");
				}else{
					if (response.type == "error"){
						popNotification(response.description);
					} else {
						popNotification("Unknown error!");
					}
				};
			},
			error: function(model,response){
				console.log(response);
			}
		});

		this.initialize();
	},
	
	editSubcategory : function() {
		var subcategory = new EditCategoryModel({
			categoryId : $("#subcategoryListDropboxEditSubcategory option:selected").val(),
			categoryName : $('#subcategoryNewName').val()
		});
		
		subcategory.save({},{
			async: false,
			success: function(model, response){
				if(response.type == "success"){
					popNotification("The subcategory was updated successfully!");
				}else{
					if (response.type == "error"){
						popNotification(response.description);
					} else {
						popNotification("Unknown error!");
					}
				};
			},
			error: function(model, response){
				console.log(response);
			}
		});

		this.initialize();
	},
	
	removeSubcategory : function(){
		
		var category = new RemoveCategoryModel({
			categoryName : $("#subcategoryListDropboxRemoveSubcategory option:selected").text(),
			categoryId : $("#subcategoryListDropboxRemoveSubcategory option:selected").val()
		});
		
		category.save({},{
			async: false,
			success: function(model, response){
				if(response.type == "success"){
					popNotification("Successfully removed!");
				}else{
					if (response.type == "error"){
						popNotification(response.description);
					} else {
						popNotification("Unknown error!");
					}
				};
			},
			error: function(model, response){
				console.log(response);
			}
		});

		this.initialize();
	},
	
	removeCategory: function(){
	
		var category = new RemoveCategoryModel({
			categoryName : $("#categoryListDropboxRemove option:selected").text(),
			categoryId : $("#categoryListDropboxRemove option:selected").val()
		});
		
		category.save({},{
			async: false,
			success: function(model, response){
				if(response.type == "success"){
					popNotification("Successfully removed!");
				}else{
					if (response.type == "error"){
						popNotification(response.description);
					} else {
						popNotification("Unknown error!");
					}
				};
			},
			error: function(model, response){
				console.log(response);
			}
		});
	
		this.initialize();
	}
})
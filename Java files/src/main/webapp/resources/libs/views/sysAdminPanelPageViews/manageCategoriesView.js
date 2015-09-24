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
		'change #categoryListDropboxRemove' : 'populateSubcategories'
	},
	
	initialize: function(){
		var categories = new GetCategoriesModel({});
		
		var currentView = this;
		
		categories.save({},{
			success: function(model, response){
				if (response.type == "success") {
					$('#userPanelPageContainer').empty();
					currentView.appendData();
					_.each(response.data,function(e){
						$('#categoryListDropboxSubcategory').append($("<option></option>").attr("value", e.categoryId).text(e.categoryName));
						$('#categoryListDropboxRemove').append($("<option></option>").attr("value", e.categoryId).text(e.categoryName));
					});
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
		});
	},
	
	populateSubcategories : function() {	
		var categories = new GetSubcategoriesModel({
			categoryId : $("#categoryListDropboxRemove option:selected").val(),
			categoryName : $("#categoryListDropboxRemove option:selected").text()
		});
		
		categories.save({}, {
			success : function(model, response) {
				if (response.type == "success") {
					var selectedCategory = $('#categoryListDropboxRemove').val();
					$('#subcategoryListDropbox').find('option').remove().end().append('<option selected style="display:none;">Select the subcategory...</option>').val('');
					if ($.isEmptyObject(response.data)) {
						$('#subcategoryListDropbox').attr("disabled", true);
					} else {
						_.each(response.data, function(e) {
							$('#subcategoryListDropbox').append($("<option></option>").attr("value", e.categoryId).text(e.categoryName));
						});
						$('#subcategoryListDropbox').attr("disabled", false);
					}
				} else {
					if (response.type == "error"){
						alert(response.description);
					} else {
						alert("Unknown error!");
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
					alert("The new category was added successfully!");
				}else{
					if (response.type == "error"){
						alert(response.description);
					} else {
						alert("Unknown error!");
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
					alert("The new subcategory was added successfully!");
				}else{
					if (response.type == "error"){
						alert(response.description);
					} else {
						alert("Unknown error!");
					}
				};
			},
			error: function(model, response){
				console.log(response);
			}
		});

		this.initialize();
	},
	
	removeCategory : function(){
		var categoryNameVar, categoryIdVar;
		if ($("#subcategoryListDropbox option:selected").val() != "Select the subcategory..."){
			categoryNameVar = $("#subcategoryListDropbox option:selected").text();
			categoryIdVar = $("#subcategoryListDropbox option:selected").val();
		} else {
			categoryNameVar = $("#categoryListDropboxRemove option:selected").text();
			categoryIdVar = $("#categoryListDropboxRemove option:selected").val();
		};
		
		var category = new RemoveCategoryModel({
			categoryName : categoryNameVar,
			categoryId : categoryIdVar
		});
		
		category.save({},{
			async: false,
			success: function(model, response){
				if(response.type == "success"){
					alert("Successfully removed!");
				}else{
					if (response.type == "error"){
						alert(response.description);
					} else {
						alert("Unknown error!");
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
/* System Admin Panel Page*/
/* ================================================= */
/* Manage Categories Page view */

var ManageCategoriesView = GenericSystemAdminPanelPageView.extend({

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
				$('#systemAdminPageContainer').empty();
				currentView.appendData();
				_.each(response,function(e){
					$('#categoryListDropboxSubcategory').append($("<option></option>").attr("value", e.categoryId).text(e.categoryName));
					$('#categoryListDropboxRemove').append($("<option></option>").attr("value", e.categoryId).text(e.categoryName));
				});
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
				var selectedCategory = $('#categoryListDropboxRemove').val();
				$('#subcategoryListDropbox').find('option').remove().end().append('<option selected style="display:none;">Select the subcategory...</option>').val('');
				if ($.isEmptyObject(response)) {
					$('#subcategoryListDropbox').attr("disabled", true);
				} else {
					_.each(response, function(e) {
						$('#subcategoryListDropbox').append($("<option></option>").attr("value", e.categoryId).text(e.categoryName));
					});
					$('#subcategoryListDropbox').attr("disabled", false);
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
				console.log(response);
				if(response.responseType == "error"){
					alert("An error occured while trying to add the new category.");
				}else{
					alert("The new category was added successfully!");
				};
			},
			error: function(model,response){
				alert(response);
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
				console.log(response);
				if(response.responseType == "error"){
					alert("An error occured while trying to add the new subcategory.");
				}else{
					alert("The new subcategory was added successfully!");
				};
				//TODO showing message if added successfully
			},
			error: function(model, response){
				alert(response);
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
				console.log(response);
				if(response.responseType == "error"){
					alert("An error occured while trying to remove the selected category");
				}else{
					alert("Successfully removed!");
				};
				//TODO showing message if added successfully
			},
			error: function(model, response){
				alert(response);
			}
		});

		this.initialize();
	}
	
})
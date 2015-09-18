/* System Admin Panel Page*/
/* ================================================= */
/* Manage Categories Page view */

var ManageCategoriesView = GenericSystemAdminPanelPageView.extend({
	
	events : {
		'click #addCategoryDivButton' : 'addCategory',
		'click #addSubcategoryDivButton' : 'addSubcategory',
		'click #removeCategoryDivButton' : 'removeCategory'
	},
	
	appendData : function(){
		this.$el.append("<h1 class='userPage'> Manage Categories </h1>");
		this.$el.append(_.template($('#manageCategoriesTemplate').html()));
		return this;
	},

	addCategory : function(){
		var addCategoryView = new AddCategoryView({
			model: new AddCategoryModel({})
		});
		
		$('#manageCategoriesContainer').replaceWith(addCategoryView.render().el);
	},
	
	addSubcategory : function(){
		var addSubcategoryView = new AddSubcategoryView({
			model: new AddSubcategoryModel({})
		});
		
		$('#manageCategoriesContainer').replaceWith(addSubcategoryView.render().el);
	},
	
	removeCategory: function(){
		var removeCategoryView = new RemoveCategoryView({
			model: new RemoveCategoryModel({})
		});
		
		$('#manageCategoriesContainer').replaceWith(removeCategoryView.render().el);
	}
	
})
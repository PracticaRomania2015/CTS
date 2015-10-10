/*! CTS Manage Categories View */

var ManageCategoriesView = Backbone.View.extend({
	
	events: {
		'click #addCategoryButton': 'submitCategory',
		'click #addSubcategoryButton': 'submitSubcategory',
		'click #removeCategoryButton': 'removeCategory',
		'click #removeSubcategoryButton': 'removeSubcategory',
		'click #editCategoryButton': 'editCategory',
		'click #editSubcategoryButton': 'editSubcategory',
		'change #categoryListDropboxRemoveSubcategory': 'populateSubcategoriesForRemoveSubcategory',
		'change #categoryListDropboxEditSubcategory': 'populateSubcategoriesForEdit'
	},
	
	render: function () {
		this.$el.empty();
		// Header
		var header = createElem('h1',{class:'userPage'},'Manage Categories');
		// Vertical separator 1
		var verticalSeparatorGradient1 = createElem('div',{id:'gradient-verticalLine'});
		var verticalSeparatorContentForm1 = createElem('div',{class:'contentFormVerticalSeparator'},[verticalSeparatorGradient1]);
		var verticalSeparatorWrapper1 = createElem('div',{class:'verticalSeparatorWrapper'},[verticalSeparatorContentForm1]);
		// Vertical separator 2
		var verticalSeparatorGradient2 = createElem('div',{id:'gradient-verticalLine'});
		var verticalSeparatorContentForm2 = createElem('div',{class:'contentFormVerticalSeparator'},[verticalSeparatorGradient2]);
		var verticalSeparatorWrapper2 = createElem('div',{class:'verticalSeparatorWrapper'},[verticalSeparatorContentForm2]);
		// Vertical separator 3
		var verticalSeparatorGradient3 = createElem('div',{id:'gradient-verticalLine'});
		var verticalSeparatorContentForm3 = createElem('div',{class:'contentFormVerticalSeparator'},[verticalSeparatorGradient3]);
		var verticalSeparatorWrapper3 = createElem('div',{class:'verticalSeparatorWrapper'},[verticalSeparatorContentForm3]);
		// Vertical separator 4
		var verticalSeparatorGradient4 = createElem('div',{id:'gradient-verticalLine'});
		var verticalSeparatorContentForm4 = createElem('div',{class:'contentFormVerticalSeparator'},[verticalSeparatorGradient4]);
		var verticalSeparatorWrapper4 = createElem('div',{class:'verticalSeparatorWrapper'},[verticalSeparatorContentForm4]);
		// Horizontal separator
		var horizontalSeparatorGradient = createElem('div',{id:'gradient-horizontalLine'});
		var horizontalSeparatorContentForm = createElem('div',{class:'contentFormHorizontalSeparator'},[horizontalSeparatorGradient]);
		var horizontalSeparatorWrapper = createElem('div',{class:'horizontalSeparatorWrapper'},[horizontalSeparatorContentForm]);
		// Add category
		var addCategoryTitle = createElem('h3',{},'Add Category');
		var addCategoryInput = createElem('input',{id:'categoryName',type:'text',placeholder:'Category name', class:'validationTooltip categoryManagement fieldCateg'});
		var addCategoryButton = createElem('div',{id:'addCategoryButton'},'Add');
		var addCategoryContainer = createElem('div',{id:'addCategoryContainer'},[addCategoryTitle,addCategoryInput,addCategoryButton]);
		// Edit category
		var editCategoryTitle = createElem('h3',{},'Edit Category');
		var editCategoryDefaultOption = createElem('option',{selected:'',disabled:'',value:''},'Select the category...');
		var editCategoryDropbox = createElem('select',{id:'categoryListDropboxEditCategory',class:'categoryManagement dropDownCateg'},[editCategoryDefaultOption]);
		var editCategoryList = createElem('div',{id:'categoryListDivEditCategory'},[editCategoryDropbox]);
		var editCategoryInput = createElem('input',{id:'categoryNewName',class:'validationTooltip categoryManagement fieldCateg',type:'text',placeholder:'New category name'});
		var editCategoryButton = createElem('div',{id:'editCategoryButton'},'Change');
		var editCategoryContainer = createElem('div',{id:'editCategoryContainer'},[editCategoryTitle,editCategoryList,editCategoryInput,editCategoryButton]);
		// Remove category
		//TODO: removeCategoryTitle has userPage class?
		var removeCategoryTitle = createElem('h3',{class:'userPage'},'Remove category');
		var removeCategoryDefaultOption = createElem('option',{selected:'',disabled:'',value:''},'Select the category...');
		var removeCategoryDropbox = createElem('select',{id:'categoryListDropboxRemove',class:'categoryManagement dropDownCateg'},[removeCategoryDefaultOption]);
		var removeCategoryList = createElem('div',{id:'categoryListDivRemove'},[removeCategoryDropbox]);
		var removeCategoryButton = createElem('div',{id:'removeCategoryButton'},'Remove');
		var removeCategoryContainer = createElem('div',{id:'removeCategoryContainer'},[removeCategoryTitle,removeCategoryList,removeCategoryButton]);
		// Manage categories wrapper
		var manageCategories = createElem('div',{id:'manageCategoriesDiv'},[addCategoryContainer,verticalSeparatorWrapper1,editCategoryContainer,verticalSeparatorWrapper2,removeCategoryContainer]);
		// Add subcategory
		var addSubcategoryTitle = createElem('h3',{},'Add Subcategory');
		var addSubcategoryDefaultOption = createElem('option',{selected:'',disabled:'',value:''},'Select the category...');
		var addSubcategoryDropbox = createElem('select',{id:'categoryListDropboxSubcategory',class:'categoryManagement dropDownCateg'},[addSubcategoryDefaultOption]);
		var addSubcategoryList = createElem('div',{id:'categoryListDivSubcategory'},[addSubcategoryDropbox]);
		var addSubcategoryInput = createElem('input',{id:'subcategoryName',type:'text',placeholder:'Subcategory name', class:'validationTooltip categoryManagement fieldCateg'});
		var addSubcategoryButton = createElem('div',{id:'addSubcategoryButton'},'Add');
		var addSubcategoryContainer = createElem('div',{id:'addSubcategoryContainer'},[addSubcategoryTitle,addSubcategoryList,addSubcategoryInput,addSubcategoryButton]);
		// Edit subcategory
		var editSubcategoryTitle = createElem('h3',{},'Edit Subcategory');
		var editSubcategoryCategoriesDefaultOption = createElem('option',{selected:'',disabled:'',value:''},'Select the category...');
		var editSubcategoryCategoriesDropbox = createElem('select',{id:'categoryListDropboxEditSubcategory',class:'categoryManagement dropDownCateg'},[editSubcategoryCategoriesDefaultOption]);
		var editSubcategoryCategoriesList = createElem('div',{id:'categoryListDivEditSubcategory'},[editSubcategoryCategoriesDropbox]);
		var editSubcategorySubcategoriesDefaultOption = createElem('option',{selected:'',disabled:'',value:''},'Select the subcategory...');
		var editSubcategorySubcategoriesDropbox = createElem('select',{id:'subcategoryListDropboxEditSubcategory',class:'categoryManagement dropDownCateg',disabled:true},[editSubcategorySubcategoriesDefaultOption]);
		var editSubcategorySubcategoriesList = createElem('div',{id:'subcategoryListDivEditSubcategory'},[editSubcategorySubcategoriesDropbox]);
		var editSubcategoryInput = createElem('input',{id:'subcategoryNewName',class:'validationTooltip categoryManagement fieldCateg',type:'text',placeholder:'New subcategory name'});
		var editSubcategoryButton = createElem('div',{id:'editSubcategoryButton'},'Change');
		var editSubcategoryContainer = createElem('div',{id:'editSubcategoryContainer'},[editSubcategoryTitle,editSubcategoryCategoriesList,editSubcategorySubcategoriesList,editSubcategoryInput,editSubcategoryButton]);
		// Remove subcategory
		//TODO: no value for removeSubcategorySubcategoriesDefaultOption?
		var removeSubcategoryTitle = createElem('h3',{class:'userPage'},'Remove Subcategory');
		var removeSubcategoryCategoriesDefaultOption = createElem('option',{selected:'',disabled:'',value:''},'Select the category...');
		var removeSubcategoryCategoriesDropbox = createElem('select',{id:'categoryListDropboxRemoveSubcategory',class:'categoryManagement dropDownCateg'},[removeSubcategoryCategoriesDefaultOption]);
		var removeSubcategoryCategoriesList = createElem('div',{id:'categoryListDivRemoveSubcategory'},[removeSubcategoryCategoriesDropbox]);
		var removeSubcategorySubcategoriesDefaultOption = createElem('option',{selected:'',disabled:''},'Select the subcategory...');
		var removeSubcategoryubcategoriesDropbox = createElem('select',{id:'subcategoryListDropboxRemoveSubcategory',class:'categoryManagement dropDownCateg',disabled:true},[removeSubcategorySubcategoriesDefaultOption]);
		var removeSubcategoryubcategoriesList = createElem('div',{id:'subcategoryListDivRemoveSubcategory'},[removeSubcategoryubcategoriesDropbox]);
		var removeSubcategoryButton = createElem('div',{id:'removeSubcategoryButton'},'Remove');
		var removeSubcategoryContainer = createElem('div',{id:'removeCategoryContainer'},[removeSubcategoryTitle,removeSubcategoryCategoriesList,removeSubcategoryubcategoriesList,removeSubcategoryButton]);
		// Manage subcategories wrapper
		var manageSubcategories = createElem('div',{id:'manageSubcategoriesDiv'},[addSubcategoryContainer,verticalSeparatorWrapper3,editSubcategoryContainer,verticalSeparatorWrapper4,removeSubcategoryContainer]);
		// Wrappers
		var form = createElem('form',{class:'sysAdminPageForms'},[manageCategories,horizontalSeparatorWrapper,manageSubcategories]);
		var manageCategoriesContainer = createElem('div',{id:'manageCategoriesContainer'},form);
		this.$el.attr('id','manageCategoriesTemplate').attr('class','context');
		this.$el.append(header,manageCategoriesContainer);
		this.setElement(this.$el);
		this.loadPageData();
		return this;
	},
	
	loadPageData: function () {
		var currentView = this;
		var categories = new GetCategoriesModel({});
		categories.save({},{
			async: true,
			success: function (model, response) {
				if (response.type == 'success') {
					var dropdowns = ['#categoryListDropboxSubcategory','#categoryListDropboxSubcategory','#categoryListDropboxRemove','#categoryListDropboxEditCategory','#categoryListDropboxEditSubcategory','#categoryListDropboxRemoveSubcategory'];
					_.each(dropdowns, function (e) {
						$(e).empty();
						_.each(response.data, function (resp) {
							$(e).append(createElem('option',{value:resp.categoryId},resp.categoryName));
						});
					});
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
	
	populateSubcategoriesForRemoveSubcategory: function () {	
		var categories = new GetSubcategoriesModel({
			categoryId: $('#categoryListDropboxRemoveSubcategory option:selected').val(),
			categoryName: $('#categoryListDropboxRemoveSubcategory option:selected').text()
		});
		categories.save({}, {
			success: function(model, response) {
				if (response.type == 'success') {
					var selectedCategory = $('#categoryListDropboxRemoveSubcategory').val();
					$('#subcategoryListDropboxRemoveSubcategory').find('option').remove().end().append(createElem('option',{selected:'',value:'',style:'display: none;'},'Select the subcategory...'));
					if ($.isEmptyObject(response.data)) {
						$('#subcategoryListDropboxRemoveSubcategory').attr('disabled', true);
					} else {
						_.each(response.data, function(e) {
							$('#subcategoryListDropboxRemoveSubcategory').append(createElem('option',{value:e.categoryId},e.categoryName));
						});
						$('#subcategoryListDropboxRemoveSubcategory').attr('disabled', false);
					}
				} else if (response.type == 'error') {
					popNotification(response.description);
				} else {
					popNotification('Unknown error!');
				};
			},
			error : function (model, response) {
				console.log('Server error!');
			}
		});
	},
	
	populateSubcategoriesForEdit: function () {
		var categories = new GetSubcategoriesModel({
			categoryId : $('#categoryListDropboxEditSubcategory option:selected').val(),
			categoryName : $('#categoryListDropboxEditSubcategory option:selected').text()
		});
		categories.save({}, {
			success : function (model, response) {
				if (response.type == 'success') {
					var selectedCategory = $('#categoryListDropboxEditSubcategory').val();
					$('#subcategoryListDropboxEditSubcategory').find('option').remove().end().append(createElem('option',{selected:'',value:'',style:'display: none;'}));
					if ($.isEmptyObject(response.data)) {
						$('#subcategoryListDropboxEditSubcategory').attr("disabled", true);
					} else {
						_.each(response.data, function (e) {
							$('#subcategoryListDropboxEditSubcategory').append(createElem('option',{value:e.categoryId},e.categoryName));
						});
						$('#subcategoryListDropboxEditSubcategory').attr('disabled', false);
					}
				} else if (response.type == 'error') {
					popNotification(response.description);
				} else {
					popNotification('Unknown error!');
				};
			},
			error : function (model, response) {
				console.log('Server error!');
			}
		});
	},
	
	submitCategory: function () {
		var category = new AddCategoryModel({
			categoryName : $('#categoryName').val()
		});
		category.save({},{
			async: false,
			success: function (model,response) {
				if (response.type == 'success') {
					popNotification('The new category was added successfully!');
					$('#categoryName').val('');
				} else if (response.type == 'error') {
					popNotification(response.description);
				} else {
					popNotification('Unknown error!');
				};
			},
			error: function (model,response) {
				console.log('Server error!');
			}
		});
		this.loadPageData();
	},
	
	submitSubcategory: function () {
		var categoryName = $('#categoryListDropboxSubcategory option:selected').text();
		var categoryId = $('#categoryListDropboxSubcategory option:selected').val();
		var subcategory = new AddSubcategoryModel({
			categoryName : $('#subcategoryName').val(),
			parentCategoryId : $('#categoryListDropboxSubcategory option:selected').val()
		});
		subcategory.save({},{
			async: false,
			success: function (model,response) {
				if(response.type == 'success') {
					popNotification('The new subcategory was added successfully!');
					$('#subcategoryName').val('');
				} else if (response.type == 'error') {
					popNotification(response.description);
				} else {
					popNotification('Unknown error!');
				};
			},
			error: function (model,response) {
				console.log('Server error!');
			}
		});
		this.loadPageData();
	},
	
	editCategory: function () {
		var category = new EditCategoryModel({
			categoryId : $('#categoryListDropboxEditCategory option:selected').val(),
			categoryName : $('#categoryNewName').val()
		});
		category.save({},{
			async: false,
			success: function (model,response) {
				if(response.type == 'success') {
					popNotification('The category was updated successfully!');
					$('#categoryNewName').val('');
				} else if (response.type == 'error') {
					popNotification(response.description);
				} else {
					popNotification('Unknown error!');
				};
			},
			error: function (model,response) {
				console.log('Server error!');
			}
		});
		this.loadPageData();
	},
	
	editSubcategory: function () {
		var subcategory = new EditCategoryModel({
			categoryId : $('#subcategoryListDropboxEditSubcategory option:selected').val(),
			categoryName : $('#subcategoryNewName').val()
		});
		subcategory.save({},{
			async: false,
			success: function (model,response) {
				if (response.type == 'success') {
					popNotification('The subcategory was updated successfully!');
					$('#subcategoryNewName').val('');
					$('#subcategoryListDropboxEditSubcategory').get(0).selectedIndex = 0;
				} else if (response.type == 'error') {
					popNotification(response.description);
				} else {
					popNotification('Unknown error!');
				};
			},
			error: function (model,response) {
				console.log('Server error!');
			}
		});
		this.loadPageData();
	},
	
	removeSubcategory: function () {
		var category = new RemoveCategoryModel({
			categoryName : $('#subcategoryListDropboxRemoveSubcategory option:selected').text(),
			categoryId : $('#subcategoryListDropboxRemoveSubcategory option:selected').val()
		});
		category.save({},{
			async: false,
			success: function (model,response) {
				if (response.type == 'success') {
					popNotification('Successfully removed!');
				}else if (response.type == 'error') {
					popNotification(response.description);
					$('#subcategoryListDropboxRemoveSubcategory').get(0).selectedIndex = 0;
				} else {
					popNotification('Unknown error!');
				};
			},
			error: function (model,response) {
				console.log('Server error!');
			}
		});
		this.loadPageData();
	},
	
	removeCategory: function () {
		var category = new RemoveCategoryModel({
			categoryName : $('#categoryListDropboxRemove option:selected').text(),
			categoryId : $('#categoryListDropboxRemove option:selected').val()
		});
		category.save({},{
			async: false,
			success: function (model,response) {
				if (response.type == 'success') {
					popNotification('Successfully removed!');
				} else if (response.type == 'error') {
					popNotification(response.description);
				} else {
					popNotification('Unknown error!');
				};
			},
			error: function (model,response) {
				console.log('Server error!');
			}
		});
		this.loadPageData();
	}
})
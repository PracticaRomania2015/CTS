/*! CTS Add subcategory Model */

var AddSubcategoryModel = Backbone.Model.extend({

	urlRoot : 'rootAddCategory/',
	
	defaults: {
		parentCategoryId: '',
		categoryName: ''
	}

});
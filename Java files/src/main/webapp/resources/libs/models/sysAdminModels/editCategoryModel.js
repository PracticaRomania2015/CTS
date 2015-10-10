/*! CTS Edit category Model */

var EditCategoryModel = Backbone.Model.extend({

	urlRoot : 'rootEditCategory/',
	
	defaults: {
		categoryId: '',
		categoryName: ''
	}

});
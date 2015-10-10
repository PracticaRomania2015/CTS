/*! CTS Add category Model */

var AddCategoryModel = Backbone.Model.extend({

	urlRoot : 'rootAddCategory/',
	
	defaults: {
		categoryName: ''
	}

});
/*! CTS Get subcategories Model */

var GetSubcategoriesModel = Backbone.Model.extend({

	urlRoot : 'getSubcategories/',
	
	defaults: {
		categoryId: '',
		categoryName: ''
	}

});
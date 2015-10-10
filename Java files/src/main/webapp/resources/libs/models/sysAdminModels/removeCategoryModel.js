/*! CTS Remove category Model */

var RemoveCategoryModel = Backbone.Model.extend({

	urlRoot : 'rootRemoveCategory/',
	
	defaults: {
		categoryName: '',
		categoryId: ''
	}

});
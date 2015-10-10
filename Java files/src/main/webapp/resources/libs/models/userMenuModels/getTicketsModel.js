/*! CTS Get tickets Model */

var GetTicketsModel = Backbone.Model.extend({

	urlRoot : 'getTickets/',
	
	defaults: {
		user: '',
		requestedPageNumber: '',
		ticketsPerPage: '',
		textToSearch: '',
		searchType: '',
		selectedCategory: new Backbone.Model,
		selectedPriority: new Backbone.Model,
		selectedState: new Backbone.Model,
		isSearchASC: '',
		typeOfRequest: ''
	}

});
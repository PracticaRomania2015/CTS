/*! CTS Create Ticket View */

var CreateTicketView = Backbone.View.extend({

	viewState: false,
	
	events: {
		'click #submitTicketButton': 'submit',
		'change #ticketCategoryDropbox': 'populateSubcategories'
	},
	
	render: function () {
		this.$el.empty();
		var header = createElem('h1',{class:'userPage'},'Create a new ticket');

		var ticketSubject = createElem('input',{id:'ticketSubject', class:'validationTooltip ticketInput', type:'text', placeholder:'Subject'});

		var categoryDefaultOption = createElem('option',{selected:'', disabled:'', value:'', style:'display: none;'},'Select a category...');
		var ticketCategory = createElem('select',{id:'ticketCategoryDropbox', class:'validationTooltip ticketCategories'},[categoryDefaultOption]);

		var subcategoryDefaultOption = createElem('option',{selected:'', disabled:'', value:'', style:'display: none;'},'Select a subcategory...');
		var ticketSubcategory = createElem('select',{id:'ticketSubcategoryDropbox', class:'validationTooltip ticketCategories', style:'color: #808080'},subcategoryDefaultOption);

		var priorityDefaultOption = createElem('option',{selected:'', disabled:'', value:'', style:'display: none;'},'Select a priority...');
		var ticketPriority = createElem('select',{id:'ticketPriorityDropbox', class:'validationTooltip ticketPriorities'},priorityDefaultOption);

		var ticketContent = createElem('textarea',{id:'ticketContent', class:'validationTooltip ticketInput', rows:'10', maxlength:'2000', placeholder:'Describe your problem'});

		var submitTicketButton = createElem('div',{id:'submitTicketButton'},'Submit');

		this.$el.attr('id','createTicketTemplate').attr('class','context');
		this.$el.append(header,ticketSubject,ticketCategory,ticketSubcategory,ticketPriority,ticketContent,submitTicketButton);
		this.setElement(this.$el);
		this.loadPageData();
		return this;
	},
	
	loadPageData: function () {
		var currentView = this;
		var categories = new GetCategoriesModel({
			userId: sessionStorage.loggedUserId
		});
		var priorities =  new GetPrioritiesModel({
			userId: sessionStorage.loggedUserId
		});
		categories.save({}, {
			success: function(model, response) {
				if (response.type == 'success') {
					_.each(response.data, function(e) {
						$(currentView.$el).find('#ticketCategoryDropbox').append(createElem('option',{value:e.categoryId},e.categoryName));
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
		priorities.save({}, {
			success: function(model, response) {
				if (response.type == 'success') {
					_.each(response.data, function(e) {
						$(currentView.$el).find('#ticketPriorityDropbox').append(createElem('option',{value:e.priorityId},e.priorityName));
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
	
	populateSubcategories: function () {
		var categories = new GetSubcategoriesModel({
			categoryId : $('#ticketCategoryDropbox option:selected').val(),
			categoryName : $('#ticketCategoryDropbox option:selected').text()
		});
		categories.save({}, {
			success: function(model, response) {
				if (response.type == 'success') {
					var selectedCategory = $('#ticketCategoryDropbox').val();
					$('#ticketSubcategoryDropbox').find('option').remove().end().append(createElem('option',{selected:'',value:'',style:'display: none;'},'Select a subcategory'));
					if ($.isEmptyObject(response.data)) {
						$('#ticketSubcategoryDropbox').attr('disabled', true);
						$('#ticketSubcategoryDropbox').css('color', '#808080');
					} else {
						_.each(response.data, function (e) {
							$('#ticketSubcategoryDropbox').append(createElem('option',{value:e.categoryId},e.categoryName));
						});
						$('#ticketSubcategoryDropbox').attr('disabled', false);
						$('#ticketSubcategoryDropbox').removeAttr('style')
					}
				} else if (response.type == "error") {
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
	
	submit: function () {
		var ticketCategoryName, ticketCategoryId, ticketPriorityName, ticketPriorityId;
		// User
		var currentUser = new Backbone.Model({
			userId: sessionStorage.loggedUserId,
		});
		// Category/Subcategory
		var selectedCategory = $('#ticketCategoryDropbox option:selected');
		var selectedSubcategory = $('#ticketSubcategoryDropbox option:selected');
		//var defaultSelectedCategoryText = 'Select a subcategory';
		//var defaultSelectedSubcategoryText = 'Select a category';
		if (selectedSubcategory.val() != '') {
			ticketCategoryName = selectedSubcategory.text();
			ticketCategoryId = selectedSubcategory.val();
		} else if (selectedCategory.val() != '') {
			ticketCategoryName= selectedCategory.text();
			ticketCategoryId = selectedCategory.val();
		};
		var ticketCategory = new Category({
			categoryName: ticketCategoryName,
			categoryId: ticketCategoryId
		});
		var categoryErrors = ticketCategory.check();
		// Priority
		var selectedPriority = $('#ticketPriorityDropbox option:selected'); 
		//var defaultSelectedPriority = 'Select a priority';
		if (selectedPriority.val() != ''){
			ticketPriorityName = $('#ticketPriorityDropbox option:selected').text();
			ticketPriorityId = $('#ticketPriorityDropbox option:selected').val();
		}
		var ticketPriority = new Priority({
			priorityName: ticketPriorityName,
			priorityId: ticketPriorityId
		});
		var priorityErrors = ticketPriority.check();
		// Comment
		var ticketComment = new TicketComment({
			user: currentUser,
			comment: $('#ticketContent').val()
		});
		var commentErrors = ticketComment.check();
		// Ticket
		var ticket = new CreateTicketModel({
			subject: $('#ticketSubject').val(),
			category: ticketCategory,
			priority: ticketPriority,
			comments: [ticketComment]
		});
		var ticketErrors = ticket.check();
		loadValidationErrors(ticketErrors);
		loadValidationErrors(categoryErrors);
		loadValidationErrors(priorityErrors);
		loadValidationErrors(commentErrors);
		if (hasValues(ticketErrors) || hasValues(categoryErrors) || hasValues(priorityErrors) || hasValues(commentErrors)) {
			handleErrorStyle();
		} else {
			ticket.save({}, {
				success: function (model, response) {
					if (response.type == 'success') {
						popNotification('Ticket submitted!');
						createTicket.close();
						$('#mainContainer').append(userTickets.render().el);
						userMenu.activateMenuArrow('#arrowUserTickets');
					} else if (response.type == 'error') {
						popNotification(response.description);
					} else {
						popNotification('Unknown error!');
					}
				},
				error: function (model, response) {
					console.log('Server error!');
				}
			});
		};
	}
});

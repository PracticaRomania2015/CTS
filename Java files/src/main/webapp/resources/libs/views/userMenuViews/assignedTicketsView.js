/*! CTS Assigned Tickets View */

var AssignedTicketsView = Backbone.View.extend({
	
	viewState: false,
	
	initialize: function () {
		// Search
		var searchText = '';
		var searchType = '';
		// Sort
		var isAsc = false;
		var lastSortElem = '';
		this.model.set('sortType', 'Subject');
	},
	
	events: {
		'click #ticketSearchButton': 'ticketSearch',
		'click #firstPageReqBtn': 'firstPageRequest',
		'click #lastPageReqBtn': 'lastPageRequest',
		'click #prevPageReqBtn': 'previousPageRequest',
		'click #nextPageReqBtn': 'nextPageRequest',
		'change #ticketsPerPage': 'ticketsPerPage',
		'click .openTicketComments': 'viewTicketComments',
		'click #id-col,#subject-col,#category-col,#priority-col,#status-col,#lastComment-col,#submitDate-col': 'orderBy',
		'mouseup ' : function(e){hideNotification(e)}
	},
	
	render: function () {
		preloadView();
		this.$el.empty();
		/* Header */
		var header = createElem('h1',{class:'userPage'},'Tickets managed by me');
		/* Search */
		// Search input
		var searchInput = createElem('input',{id:'ticketSearchBox',class:'searchTickets',type:'text',placeholder:'Search'});
		// Select search parameter
		var firstOptionSearchParam = createElem('option',{selected:'',value:'TicketId'},'Ticket ID');
		var secondOptionSearchParam = createElem('option',{value:'Subject'},'Subject');
		var selectSearchParam = createElem('select',{id:'ticketSearchDropBox',class:'searchTickets'},[firstOptionSearchParam,secondOptionSearchParam]);
		//TODO: needs implementation
		//New category,priority,status select
		var searchCategoryDefaultOption = createElem('option',{selected:'',value:'0'},'All categories');
		var searchCategorySelect = createElem('select',{id:'ticketSearchCategory',class:'searchCategories'},[searchCategoryDefaultOption]);
		var searchPrioritiesDefaultOption = createElem('option',{selected:'',value:'0'},'All priorities');
		var searchPrioritiesSelect = createElem('select',{id:'ticketSearchPriority',class:'searchPriorities'},[searchPrioritiesDefaultOption]);
		var searchStatusFirstOption = createElem('option',{selected:'',value:'0'},'All status');
		var searchStatusSecondOption = createElem('option',{value:'1'},'Active');
		var searchStatusThirdOption = createElem('option',{value:'2'},'Answered')
		var searchStatusFourthOption = createElem('option',{value:'3'},'Closed')
		var searchStatusSelect = createElem('select',{id:'ticketSearchStatus',class:'searchStatus'},[searchStatusFirstOption,searchStatusSecondOption,searchStatusThirdOption,searchStatusFourthOption]);
		// Loading animation
		var loading = createElem('img',{id:'loadingAnimManage',src:'/cts/resources/img/loadingAnim.gif'});
		var loadingIconWrapper = createElem('div',{id:'loadingIconWrapper'},loading);
		// Search button
		var searchButton = createElem('div',{id:'ticketSearchButton'},'Search');
		/* Table headers */
		// Id
		var thIdItem1 = createElem('div',{id:'idTh'},'Id');
		var thIdItem2 = createElem('div',{id:'order'},[createElem('div',{class:'arrowUp'}),createElem('div',{class:'arrowDown'})]);
		var tableHeaderId = createElem('th',{id:'id-col',class:'id-col rounded-tl',dbName:'TicketId'},[thIdItem1,thIdItem2]);
		// Subject
		var thSubjectItem1 = createElem('div',{id:'subjectTh'},'Subject');
		var thSubjectItem2 = createElem('div',{id:'order'},[createElem('div',{class:'arrowUp'}),createElem('div',{class:'arrowDown'})]);
		var tableHeaderSubject = createElem('th',{id:'subject-col',class:'wide-col',dbName:'Subject'},[thSubjectItem1,thSubjectItem2]);
		// Category
		var thCategoryItem1 = createElem('div',{id:'categoryTh'},'Category');
		var thCategoryItem2 = createElem('div',{id:'order'},[createElem('div',{class:'arrowUp'}),createElem('div',{class:'arrowDown'})]);
		var tableHeaderCategory = createElem('th',{id:'category-col',class:'slim-col',dbName:'CategoryName'}, [thCategoryItem1,thCategoryItem2]);
		// Priority
		var thPriorityItem1 = createElem('div',{id:'priorityTh'},'Priority');
		var thPriorityItem2 = createElem('div',{id:'order'},[createElem('div',{class:'arrowUp'}),createElem('div',{class:'arrowDown'})]);
		var tableHeaderPriority = createElem('th',{id:'priority-col',class:'slim-col',dbName:'PriorityName'}, [thPriorityItem1,thPriorityItem2]);
		// Status
		var thStatusItem1 = createElem('div',{id:'statusTh'},'Status');
		var thStatusItem2 = createElem('div',{id:'order'},[createElem('div',{class:'arrowUp'}),createElem('div',{class:'arrowDown'})]);
		var tableHeaderStatus = createElem('th',{id:'status-col',class:'wide-col',dbName:'StateName'}, [thStatusItem1,thStatusItem2]);
		// Last Comment
		var thLastCommentItem1 = createElem('div',{id:'lastCommentTh'},'Last Comment');
		var thLastCommentItem2 = createElem('div',{id:'order'},[createElem('div',{class:'arrowUp'}),createElem('div',{class:'arrowDown'})]);
		var tableHeaderLastComment = createElem('th',{id:'lastComment-col',class:'wide-col',dbName:'AnswerDate'}, [thLastCommentItem1,thLastCommentItem2]);
		// Submit Date
		var thSubmitDateItem1 = createElem('div',{id:'submitDateTh'},'Submit On');
		var thSubmitDateItem2 = createElem('div',{id:'order'},[createElem('div',{class:'arrowUp'}),createElem('div',{class:'arrowDown'})]);
		var tableHeaderSubmitDate = createElem('th',{id:'submitDate-col',class:'wide-col rounded-tr',dbName:'SubmitDate'}, [thSubmitDateItem1,thSubmitDateItem2]);
		var tableHeadRow = createElem('tr','',[tableHeaderId,tableHeaderSubject,tableHeaderCategory,tableHeaderPriority,tableHeaderStatus,tableHeaderLastComment,tableHeaderSubmitDate]);
		var tableHead = createElem('thead','',[tableHeadRow]);
		/* Table footer */
		// Table navigation
		var firstPageButton = createElem('div',{id:'firstPageReqBtn',class:'table-surf'},'<<');
		var previousPageButton = createElem('div',{id:'prevPageReqBtn',class:'table-surf'},'<');
		var ticketPageNumbering = createElem('div',{id:'ticketPagingNumbering',style:'display: inline-block'},'1/1');
		var nextPageButton = createElem('div',{id:'nextPageReqBtn',class:'table-surf'},'>');
		var lastPageButton = createElem('div',{id:'lastPageReqBtn',class:'table-surf'},'>>');
		// Table row count
		var firstOptionTicketsPerPage = createElem('option',{selected:'',disabled:'',value:'10'},'10');
		var secondOptionTicketsPerPage = createElem('option',{value:'10'},'10');
		var thirdOptionTicketsPerPage = createElem('option',{value:'25'},'25');
		var fourthOptionTicketsPerPage = createElem('option',{value:'50'},'50');
		var ticketsPerPageSelect = createElem('select',{id:'ticketsPerPage'},[firstOptionTicketsPerPage,secondOptionTicketsPerPage,thirdOptionTicketsPerPage,fourthOptionTicketsPerPage]);
		var ticketsPerPage = createElem('div',{id:'tkPerPgDiv'},['Tickets/Page ',ticketsPerPageSelect]);
		var tableFootHeader = createElem('th',{id:'rounded-bot',colspan:'7'},[firstPageButton,'&emsp;',previousPageButton,'&emsp;',ticketPageNumbering,'&emsp;',nextPageButton,'&emsp;',lastPageButton,ticketsPerPage]);
		var tableFootRow = createElem('tr','',[tableFootHeader]);
		var tableFoot = createElem('tfoot','',[tableFootRow]);
		// Table body
		var tableBody = createElem('tbody',{id:'ticketViewBody'});
		// Actual table
		var ticketViewTable = createElem('table',{class:'ticketView'},[tableHead,tableFoot,tableBody]);
		
		this.$el.attr('id','assignedTicketsTemplate').attr('class','context');
		this.$el.append(header,searchInput,selectSearchParam,searchCategorySelect,searchPrioritiesSelect,searchStatusSelect,loadingIconWrapper,searchButton,ticketViewTable);
		this.setElement(this.$el);
		this.loadPageData();
		this.viewData(1, '', '');
		return this;
	},
	
	loadPageData: function () {
		// load categories
		var categoriesDropdown = new GetCategoriesModel({ userId : sessionStorage.loggedUserId });
		categoriesDropdown.save({}, {
			success : function (model, response) {
				if (response.type == 'success'){
					_.each(response.data, function(e) {
						$('#ticketSearchCategory').append(createElem('option',{value:e.categoryId}, e.categoryName));					
					});
				} else if (response.type == 'error'){
					popNotification(response.description);
				} else {
					popNotification('Unknown error!');
				};
			},
			error : function (model, response) {
				console.log('Server error!');
			}
		});
		
		// load priorities
		var prioritiesDropdown = new GetPrioritiesModel({ userId : sessionStorage.loggedUserId });
		prioritiesDropdown.save({}, {
			success : function (model, response) {
				if (response.type == 'success'){
					_.each(response.data, function(e) {
						$('#ticketSearchPriority').append(createElem('option',{value:e.priorityId}, e.priorityName));					
					});
				} else if (response.type == 'error'){
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
	
	ticketSearch: function () {
		this.searchText = $('#ticketSearchBox').val();
		this.searchType = $('#ticketSearchDropBox').val();
		this.viewData(1, this.searchText, this.searchType);
	},
	
	firstPageRequest: function () {
		this.viewData(1, this.searchText, this.searchType);
	},
	
	lastPageRequest: function () {
		var lastPage = this.model.get('totalNumberOfPages'); 
		this.viewData(lastPage, this.searchText, this.searchType);
	},
	
	nextPageRequest: function () {
		var currentPage = this.model.get('requestedPageNumber'); 
		this.viewData(currentPage + 1, this.searchText, this.searchType);
	},
	
	previousPageRequest: function () {
		var currentPage = this.model.get('requestedPageNumber'); 
		this.viewData(currentPage - 1, this.searchText, this.searchType);
	},
	
	ticketsPerPage: function () {
		this.viewData(1, this.searchText, this.searchType);
	},
	
	orderBy : function (clickedElem) {
		var target = clickedElem.currentTarget;
		var targetDbName = $(target).attr('dbName');
		if (this.lastSortElem == target) {
			if (this.isAsc==false) {
				this.isAsc=true;
				this.model.set('sortType',targetDbName);
				this.model.set('isSearchASC',this.isAsc);
				$(target).find('#order').empty();
				$(target).find('#order').append(createElem('div',{class:'arrowAsc'}));
			} else {
				this.isAsc=false;
				this.model.set('sortType',targetDbName);
				this.model.set('isSearchASC',this.isAsc);
				$(target).find('#order').empty();
				$(target).find('#order').append(createElem('div',{class:'arrowDesc'}));
			}
		} else {
			this.isAsc=true;
			this.model.set('sortType',targetDbName);
			this.model.set('isSearchASC',this.isAsc);
			$(target).find('#order').empty();
			$(target).find('#order').append(createElem('div',{class:'arrowAsc'}));
			$(this.lastSortElem).find('#order').empty();
			$(this.lastSortElem).find('#order').append(createElem('div',{class:'arrowUp'}),createElem('div',{class:'arrowDown'}));
		}
		this.lastSortElem = target;
		this.viewData(1, this.searchText, this.searchType);
	},

	viewTicketComments: function(clicked) {
		respondToTicket = new RespondToTicketView({
			model : new Backbone.Model({
				ticketId : $(clicked.currentTarget).attr('id'),
				provenience: 'assignedTickets'
			})
		});
		$('#mainContainer').append(respondToTicket.render().el);
		manageTickets.close();
	},

	viewData: function (pageNumber, searchText, searchType) {
		var currentModel = this.model;
		var currentView = this;
		if (pageNumber > Number(currentModel.get('totalNumberOfPages'))) {
			pageNumber = Number(currentModel.get('totalNumberOfPages'));
		} else if (pageNumber < 1) {
			pageNumber = 1;
		}
		currentModel.unset('tickets');
		currentModel.unset('totalNumberOfPages');
		currentModel.set('user', { 'userId' : Number(sessionStorage.loggedUserId) });
		currentModel.set('requestedPageNumber', pageNumber);
		currentModel.set('ticketsPerPage', this.$el.find('#ticketsPerPage option:selected').val());
		currentModel.set('textToSearch', searchText);
		currentModel.set('searchType', searchType);
		currentModel.set('selectedCategory', new Backbone.Model({ categoryId: this.$el.find('#ticketSearchCategory option:selected').val() }));
		currentModel.set('selectedPriority', new Backbone.Model({ priorityId: this.$el.find('#ticketSearchPriority option:selected').val() }));
		currentModel.set('selectedState', ticketState = new Backbone.Model({ stateId: this.$el.find('#ticketSearchStatus option:selected').val() }));
		currentModel.set('isSearchASC', this.isAsc);
		currentModel.set('typeOfRequest', 1);
		$('#loadingAnimManage').show();
		currentModel.save({}, {
			async: true,
			success : function(model, response) {
				if (response.type == 'success') {
					currentModel.set('totalNumberOfPages', response.data[0]);
					currentModel.set('tickets', response.data[1]);
					currentView.populateData(pageNumber, response.data[0], response.data[1]);
					currentModel.unset('data');
					currentModel.unset('type');
					currentModel.unset('description');
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

	addTicket: function (ticketId, subject, category, status, userName, answerDate, submitDate, priority) {
		var statusClassColor;
		if (status == 'Closed') {
			statusClassColor = 'classClosed';
		} else if (status == 'Answered') {
			statusClassColor = 'classAnswered';
		} else if (status == 'Active') {
			statusClassColor = 'classActive';
		};
		var idContent = createElem('div',{class:'columnOverflow'},ticketId);
		var id = createElem('td',{},[idContent]);
		var subjectContent = createElem('div',{class:'columnOverflow'},subject);
		var subject = createElem('td',{},[subjectContent]);
		var categoryContent = createElem('div',{class:'columnOverflow'},category);
		var category = createElem('td',{},[categoryContent]);
		var priorityContent = createElem('div',{class:'columnOverflow'},priority);
		var priority = createElem('td',{},[priorityContent]);
		var statusColor = createElem('div',{class:statusClassColor});
		var statusColorWrapper = createElem('div',{class:'statusColorWrapper'},[statusColor]);
		var statusContent = createElem('div',{class:'columnOverflow'},[status,userName]);
		var statusContentWrapper = createElem('div',{class:'columnOverflow'},[statusColorWrapper,statusContent]);
		var status = createElem('td',{},[statusContentWrapper]);
		var answerDateContent = createElem('div',{class:'columnOverflow'},answerDate);
		var answerDate = createElem('td',{},[answerDateContent]);
		var submitDateContent = createElem('div',{class:'columnOverflow'},submitDate);
		var submitDate = createElem('td',{},[submitDateContent]);
		var ticket = createElem('tr',{id:ticketId, class:'openTicketComments'}, [id,subject,category,priority,status,answerDate,submitDate]);
		this.$el.find('tbody').append(ticket);
	},

	populateData: function (currentPage, totalPages, ticketsArray) {
		var currentView = this;
		this.$el.find('tbody').empty();
		_.each (ticketsArray,
				function (e) {
					var answerDate;
					var currentSubmitTkDate = new Date(e.comments[0].dateTime);
					var submitDate = currentSubmitTkDate.toLocaleDateString() + ' ' + addZero(currentSubmitTkDate.getHours()) + ':' + addZero(currentSubmitTkDate.getMinutes());
					var currentAnswerTkDate = new Date(e.comments[1].dateTime);
					var answerDate = currentAnswerTkDate.toLocaleDateString()+ ' ' + addZero(currentAnswerTkDate.getHours())+ ':' + addZero(currentAnswerTkDate.getMinutes());
					if (e.assignedToUser.firstName == null || e.assignedToUser.lastName == null) {
						assignedToUser = '<br>Unassigned';
					} else {
						assignedToUser = '<br>Assigned to ' + e.assignedToUser.firstName;
					};
					currentView.addTicket(e.ticketId, e.subject, e.category.categoryName, e.state.stateName, e.state.stateName == 'Closed' ? '' : assignedToUser, answerDate, submitDate, e.priority.priorityName);
				});
		this.$el.find('#ticketPagingNumbering').empty().append(currentPage + '/' + totalPages);
		$('#loadingAnimManage').hide();
	}
});

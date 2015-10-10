/*! CTS Manage Users View */

var ManageUsersView = Backbone.View.extend({

	viewState: false,
	
	initialize: function () {
		// Search
		var searchText = '';
		var searchType = '';
	},
	
	events: {
		'click #usersSearchButton': 'usersSearch',
		'click #firstPageReqBtn': 'firstPageRequest',
		'click #lastPageReqBtn': 'lastPageRequest',
		'click #prevPageReqBtn': 'nextPageRequest',
		'click #nextPageReqBtn': 'previousPageRequest',
		'change #usersPerPage': 'usersPerPage',
		'click .openUser': 'manageUser'
	},
	
	render: function () {
		this.$el.empty();
		
		var header = createElem('h1',{class:'userPage'},'Manage Users');
		
		var input = createElem('input',{id:'usersSearchBox',class:'searchUsers',type:'text',placeholder:'Search'});

		var firstSearchOption = createElem('option',{select:'',value:'UserId'},'User ID');
		var secondSearchOption = createElem('option',{value:'FirstName'},'First Name');
		var thirdSearchOption = createElem('option',{value:'LastName'},'Last Name');
		var fourthSearchOption = createElem('option',{value:'Email'},'E-mail');
		var fifthSearchOption = createElem('option',{value:'RoleId'},'Role');
		var usersSearchDropbox = createElem('select',{id:'usersSearchDropBox',class:'searchUsers'},[firstSearchOption,secondSearchOption,thirdSearchOption,fourthSearchOption,fifthSearchOption]);

		// Loading animation
		var loading = createElem('img',{id:'loadingAnim',src:'/cts/resources/img/loadingAnim.gif'});
		var loadingIconWrapper = createElem('div',{id:'loadingIconWrapper'},loading);
		
		var searchButton = createElem('div',{id:'usersSearchButton'},'Search');

		var usersViewTableHeadElem1 = createElem('th',{id:'',class:'slim-col rounded-tl'},'User ID');
		var usersViewTableHeadElem2 = createElem('th',{class:'wide-col'},'First Name');
		var usersViewTableHeadElem3 = createElem('th',{class:'wide-col'},'Last Name');
		var usersViewTableHeadElem4 = createElem('th',{class:'wide-col'},'E-mail');
		var usersViewTableHeadElem5 = createElem('th',{id:'',class:'slim-col rounded-tr'},'Role');
		var usersViewTableHeadRow = createElem('tr',{},[usersViewTableHeadElem1,usersViewTableHeadElem2,usersViewTableHeadElem3,usersViewTableHeadElem4,usersViewTableHeadElem5]);
		var usersViewTableHeader = createElem('thead',{},[usersViewTableHeadRow]);


		var usersViewTableFootElem1 = createElem('div',{id:'firstPageReqBtn',class:'table-surf'},'<<');
		var usersViewTableFootElem2 = createElem('div',{id:'prevPageReqBtn',class:'table-surf'},'<');
		var usersViewTableFootElem3 = createElem('div',{id:'usersPagingNumbering',style:'display: inline-block;'},'1/1');
		var usersViewTableFootElem4 = createElem('div',{id:'nextPageReqBtn',class:'table-surf'},'>');
		var usersViewTableFootElem5 = createElem('div',{id:'lastPageReqBtn',class:'table-surf'},'>>');

		var firstOptionUsersPerPage = createElem('option',{selected:'',disabled:'',value:'10'},'10');
		var secondOptionUsersPerPage = createElem('option',{value:'10'},'10');
		var thirdOptionUsersPerPage = createElem('option',{value:'25'},'25');
		var fourthOptionUsersPerPage = createElem('option',{value:'50'},'50');
		var usersPerPageSelect = createElem('select',{id:'usersPerPage'},[firstOptionUsersPerPage,secondOptionUsersPerPage,thirdOptionUsersPerPage,fourthOptionUsersPerPage]);
		var usersPerPage = createElem('div',{id:'usersPerPgDiv'},['Users/Page ',usersPerPageSelect]);

		var usersViewTableFootWrap = createElem('th',{id:'rounded-bot',colspan:'5'},[usersViewTableFootElem1,'&emsp;',usersViewTableFootElem2,'&emsp;',usersViewTableFootElem3,'&emsp;',usersViewTableFootElem4,'&emsp;',usersViewTableFootElem5,usersPerPage]);
		var usersViewTableFootRow = createElem('tr',{},[usersViewTableFootWrap]);
		var usersViewTableFooter = createElem('tfoot',{},[usersViewTableFootRow]);

		var usersViewTableBody = createElem('tbody',{id:'usersViewBody'});

		var usersViewTable = createElem('table',{class:'usersView'},[usersViewTableHeader,usersViewTableFooter,usersViewTableBody]);
		
		this.$el.attr('id', 'manageUsersTemplate').attr('class','context');
		this.$el.append(header,input,usersSearchDropbox,loadingIconWrapper,searchButton,usersViewTable);
		this.setElement(this.$el);
		this.viewData(1, "", "");
		return this;
	},
	
	usersSearch: function () {
		this.searchText = $('#usersSearchBox').val();
		this.searchType = $('#usersSearchDropBox').val();
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
	
	usersPerPage: function () {
		this.viewData(1, this.searchText, this.searchType);
	},
	
	viewData: function (pageNumber, searchText, searchType) {
		var currentModel = this.model;
		var currentView = this;
		if (pageNumber > Number(currentModel.get('totalNumberOfPages'))) {
			pageNumber = Number(currentModel.get('totalNumberOfPages'));
		} else if (pageNumber < 1) {
			pageNumber = 1;
		}
		currentModel.unset('users');
		currentModel.unset('totalNumberOfPages');
		currentModel.set("requestedPageNumber", pageNumber);
		currentModel.set('usersPerPage', this.$el.find('#usersPerPage option:selected').val());
		currentModel.set("textToSearch", searchText);
		currentModel.set("searchType", searchType);
		$('#loadingAnim').show();
		currentModel.save({},{
			async: true,
			success: function (model,response) {
				if (response.type == 'success') {
					currentModel.set('totalNumberOfPages', response.data[0]);
					currentModel.set('users', response.data[1]);
					currentView.populateData(pageNumber, response.data[0], response.data[1]);
					currentModel.unset('data');
					currentModel.unset('type');
					currentModel.unset('description');
				} else if (response.type == 'error'){
					popNotification(response.description);
				} else {
					popNotification('Unknown error!');
				};
			},
			error: function(model, response){
				console.log('Server error!');
			}
		})
		
	},
	
	manageUser: function(clicked){
		var firstName = $(clicked.currentTarget.children[1].children[0]).text();
		var lastName = $(clicked.currentTarget.children[2].children[0]).text();
		manageUserRoleView = new ManageUserRoleView({
			model: new ManageUserRoleModel({
				userId: $(clicked.currentTarget).attr('id'),
				name: firstName + ' ' + lastName
			})
		});
		$('#mainContainer').append(manageUserRoleView.render().el);
		manageUsers.close();
	},
	
	addUser: function (userId, firstName, lastName, email, role) {
		var tableData1Content = createElem('div',{class:'columnOverflow'},userId);
		var tableData1 = createElem('td',{},tableData1Content);
		var tableData2Content = createElem('div',{class:'columnOverflow'},firstName);
		var tableData2 = createElem('td',{},tableData2Content);
		var tableData3Content = createElem('div',{class:'columnOverflow'},lastName);
		var tableData3 = createElem('td',{},tableData3Content);
		var tableData4Content = createElem('div',{class:'columnOverflow'},email);
		var tableData4 = createElem('td',{},tableData4Content);
		var tableData5Content = createElem('div',{class:'columnOverflow'},role);
		var tableData5 = createElem('td',{},tableData5Content);
		var tableRow = createElem('tr',{id:userId,class:'openUser'},[tableData1,tableData2,tableData3,tableData4,tableData5]);
		$('#usersViewBody').append(tableRow);
	},
	
	populateData: function (currentPage, totalPages, usersArray) {
		var currentView = this;
		$('#usersViewBody').empty();
		_.each(usersArray, function (e) {
			//change e.role stuff after the db change
			currentView.addUser(e.userId, e.firstName, e.lastName, e.email, e.role.roleName);
		});
		$('#usersPagingNumbering').empty().append(currentPage + "/" + totalPages)
		$('#loadingAnim').hide();
	}
})
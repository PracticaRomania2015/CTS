/*! CTS User Menu View */

var UserMenuView = Backbone.View.extend({
	
	viewState: false,
	
	events: {
		'click #toggleUserProperties': 'userProperties',
		'click #toggleManageTickets': 'manageTickets',
		'click #toggleUserTickets': 'userTickets',
		'click #toggleSubmitTicket': 'createTicket',
		'click #toggleManageCategories': 'manageCategories',
		'click #toggleManageUsers': 'manageUsers',
		'click #buttonLogout': 'logout'
	},
	
	tagName: 'nav',
	
	render: function () {
		this.$el.empty();
		var arrowUserProperties = createElem('div',{id:'arrowUserProperties',class:'menuRightArrow'});
		var toggleUserProperties = createElem('div',{id:'toggleUserProperties',class:'userMenuButton userMenuProfileButton'},[sessionStorage.loggedUserName,arrowUserProperties]);
		var arrowManageTickets = createElem('div',{id:'arrowManageTickets',class:'menuRightArrow'});
		var toggleManageTickets = createElem('div',{id:'toggleManageTickets',class:'userMenuButton'},['Manage Tickets',arrowManageTickets]);
		var arrowUserTickets = createElem('div',{id:'arrowUserTickets',class:'menuRightArrow'});
		var toggleUserTickets = createElem('div',{id:'toggleUserTickets',class:'userMenuButton'},['My Tickets',arrowUserTickets]);
		var arrowSubmitTicket = createElem('div',{id:'arrowSubmitTicket',class:'menuRightArrow'});
		var toggleSubmitTicket = createElem('div',{id:'toggleSubmitTicket',class:'userMenuButton'},['Submit a ticket',arrowSubmitTicket]);
		var arrowManageCategories = createElem('div',{id:'arrowManageCategories',class:'menuRightArrow'});
		var toggleManageCategories = createElem('div',{id:'toggleManageCategories',class:'userMenuButton'},['Manage Categories',arrowManageCategories]);
		var arrowManageUsers = createElem('div',{id:'arrowManageUsers',class:'menuRightArrow'});
		var toggleManageUsers = createElem('div',{id:'toggleManageUsers',class:'userMenuButton'},['Manage Users',arrowManageUsers]);
		var buttonLogout = createElem('div',{id:'buttonLogout',class:'userMenuButton'},'Log Out');
		this.$el.attr('id', 'userMenuTemplate');
		if (sessionStorage.loggedUserRights == "SysAdmin") {
			this.$el.append(toggleUserProperties,toggleManageTickets,toggleUserTickets,toggleSubmitTicket,toggleManageCategories,toggleManageUsers,buttonLogout);
		} else {
			this.$el.append(toggleUserProperties,toggleManageTickets,toggleUserTickets,toggleSubmitTicket,buttonLogout);
		}
		this.setElement(this.$el);
		if (!$('#mainContainer').hasClass('logged')) {
			$('#mainContainer').addClass('logged');
		}
		if (!$('header').hasClass('headerLogged')) {
			$('header').addClass('headerLogged');
		}
		return this;
	},
	
	activateMenuArrow: function (menu) {
		var menuItems = ['#arrowUserProperties','#arrowManageTickets','#arrowUserTickets','#arrowSubmitTicket','#arrowManageCategories','#arrowManageUsers'];
		if (!$('#mainContainer').hasClass('logged')) {
			$('#mainContainer').addClass('logged');
		}
		$.each (menuItems, function(index, value) {
			if (value == menu) {
				$(value).removeClass('menuRightArrow').addClass('menuRightArrowSelected');
			} else {
				$(value).removeClass('menuRightArrowSelected').addClass('menuRightArrow');
			};
		});
	},
	
	closeContext: function (exception) {
		var view = [welcome,userProperties,manageTickets,userTickets,createTicket,manageCategories,manageUsers,respondToTicket,manageUserRoleView];
		$.each(view, function(index, value) {
			if (value != exception) {
				if (typeof value != 'undefined') {
					value.close();
				}
			};
		});
	},
	
	userProperties: function () {
		if (!userProperties.viewState) {
			this.closeContext(userProperties);
			$('#mainContainer').append(userProperties.render().el);
			this.activateMenuArrow('#arrowUserProperties');
			userProperties.viewState = true;
			if (typeof errorTooltip !== 'undefined') { $('#errorTooltip').remove(); };
		} else {
			userProperties.close();
			this.activateMenuArrow('');
			$('#mainContainer').append(welcome.render().el);
		};
	},
	
	manageTickets: function () {
		if (!manageTickets.viewState) {
			this.closeContext(manageTickets);
			$('#mainContainer').append(manageTickets.render().el);
			this.activateMenuArrow('#arrowManageTickets');
			manageTickets.viewState = true;
			if (typeof errorTooltip !== 'undefined') { $('#errorTooltip').remove(); };
		} else {
			manageTickets.close();
			this.activateMenuArrow('');
			$('#mainContainer').append(welcome.render().el);
		};
	},
	
	userTickets: function () {
		if (!userTickets.viewState) {
			this.closeContext(userTickets);
			$('#mainContainer').append(userTickets.render().el);
			this.activateMenuArrow('#arrowUserTickets');
			userTickets.viewState = true;
			if (typeof errorTooltip !== 'undefined') { $('#errorTooltip').remove(); };
		} else {
			userTickets.close();
			this.activateMenuArrow('');
			$('#mainContainer').append(welcome.render().el);
		};
	},
	
	createTicket: function () {
		if (!createTicket.viewState) {
			this.closeContext(createTicket);
			$('#mainContainer').append(createTicket.render().el);
			this.activateMenuArrow('#arrowSubmitTicket');
			createTicket.viewState = true;
			errorTooltip = createElem('div',{id: 'errorTooltip'}).appendTo('body').fadeIn('slow').hide();
		} else {
			createTicket.close();
			this.activateMenuArrow('');
			$('#mainContainer').append(welcome.render().el);
		};
	},
	
	manageCategories: function () {
		if (!manageCategories.viewState) {
			this.closeContext(manageCategories);
			$('#mainContainer').append(manageCategories.render().el);
			this.activateMenuArrow('#arrowManageCategories');
			manageCategories.viewState = true;
			if (typeof errorTooltip !== 'undefined') { $('#errorTooltip').remove(); };
		} else {
			manageCategories.close();
			this.activateMenuArrow('');
			$('#mainContainer').append(welcome.render().el);
		};
	},
	
	manageUsers: function () {
		if (!manageUsers.viewState) {
			this.closeContext(manageUsers);
			$('#mainContainer').append(manageUsers.render().el);
			this.activateMenuArrow('#arrowManageUsers');
			manageUsers.viewState = true;
			if (typeof errorTooltip !== 'undefined') { $('#errorTooltip').remove(); };
		} else {
			manageUsers.close();
			this.activateMenuArrow('');
			$('#mainContainer').append(welcome.render().el);
		};
	},
	
	logout: function () {
		sessionStorage.clear();
		clearUserMenu();
		initMainMenu();
		$('#mainContainer').removeClass('logged');
		$('header').removeClass('headerLogged');
		$('#mainContainer').append(mainMenu.render().el);
	}
});
			
			
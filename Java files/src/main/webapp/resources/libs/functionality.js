/*! CTS Functionality */

// Global Variables
var mainMenu, login, register, recovery;
var userMenu, welcome, userProperties, manageTickets, userTickets, createTicket, manageCategories, manageUsers, respondToTicket, manageUserRoleView, loadUserNotificationsSettingsModel;


// @Override - Backbone close function
Backbone.View.prototype.close = function () {
	this.$el.remove();
	this.stopListening();
	this.viewState = false;
	return this;
};

function initMainMenu(){
	mainMenu = new MainMenuView({});
	login = new LoginView({ model: new LoginModel() });
	register = new RegisterView({ model: new RegisterModel() });
	recovery = new RecoveryView({ model: new RecoveryModel() });
	errorTooltip = createElem('div',{id: 'errorTooltip'}).appendTo('body').fadeIn('slow').hide();
};

function initUserMenu(){
	userMenu = new UserMenuView({});
	welcome = new WelcomeView({});
	userProperties = new UserPropertiesView({ model: new UserPropertiesModel() });
	manageTickets = new AssignedTicketsView({ model: new GetTicketsModel() });
	userTickets = new UserTicketsView({ model: new GetTicketsModel() });
	createTicket = new CreateTicketView({ model: new CreateTicketModel() });
	manageCategories = new ManageCategoriesView({});
	manageUsers = new ManageUsersView({ model: new GetUsersModel() });
};

function clearMainMenu() {
	var view = [mainMenu,login,register,recovery];
	$.each(view, function(index, value) {
		if (typeof value !== 'undefined'){
			value.close();
		};
	});
	$('#errorTooltip').remove();
};

function clearUserMenu() {
	var view = [userMenu,welcome,userProperties,manageTickets,userTickets,createTicket,manageCategories,manageUsers,respondToTicket,manageUserRoleView];
	$.each(view, function(index, value) {
		if (typeof value !== 'undefined'){
			value.close();
		};
	});
};

function addZero(i) {
	if (i < 10) {
		i = "0" + i;
	}
	return i;
}

function popNotification (notification) {
	$('#notifications').empty();
	$('#notifications').append(notification);
	$('#notifications').fadeIn("fast").delay(4000).fadeOut("slow");
	// Stop animation on hover and restart on exit
	/*$("#notifications").hover(
		function() {
			console.log('here3');
			$(this).stop().show();
		}, function() {
			console.log('here4');
			$(this).delay(4000).fadeOut("slow");
		}
	);*/
	if(true){
		$(document).mouseup(function (e)
				{
					console.log("How we dong ?");
				    var container = $("#notifications");

				    if (!container.is(e.target)) 
				    {
				    	container.hide();
				    }
				});
	}
}

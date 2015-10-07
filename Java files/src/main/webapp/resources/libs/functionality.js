function frontPageFunctionality() {

	var logInView, registerView, recoveryView;

	$('#toggle-login').click(function() {
		registerView = recoveryView = null;
		if (!logInView) {
			logInView = new LogInView({
				model : new LogInModel({})
			});
			$('#frontPageContainer').replaceWith(logInView.render().el);
			handleErrorStyle();
		} else {
			$('#frontPageContainer').empty();
			logInView = null;
		}
		;
	});

	$('#toggle-signup').click(function() {
		logInView = recoveryView = null;
		if (!registerView) {
			registerView = new RegisterView({
				model : new RegisterModel({})
			});
			$('#frontPageContainer').replaceWith(registerView.render().el);
			handleErrorStyle();
		} else {
			$('#frontPageContainer').empty();
			registerView = null;
		}
		;
	});

	$('#toggle-recovery').click(function() {
		logInView = registerView = null;
		if (!recoveryView) {
			recoveryView = new RecoveryView({
				model : new RecoveryModel({})
			});
			$('#frontPageContainer').replaceWith(recoveryView.render().el);
			handleErrorStyle();
		} else {
			$('#frontPageContainer').empty();
			recoveryView = null;
		}
		;
	});
}

var assignedTicketsView, userTicketsView, manageUsersView;

var createTicketPageView, respondToTicketPageView;
	
var manageCategoriesView, userPropertiesPageView;

function userPanelPageFunctionality() {

	$('#userInfo').append(sessionStorage.loggedUserName);
	
	if(sessionStorage.loggedUserRights == "Admin" || sessionStorage.loggedUserRights == "User"){
		$('#manageCategoriesButton').remove();
		$('#manageUsersButton').remove();
	}

	if (sessionStorage.loggedUserRights == "Admin" || sessionStorage.loggedUserRights == "SysAdmin") {
		$('#btn-mngTk').click(
				function() {
					userTicketsView = createTicketPageView = manageCategoriesView = manageUsersView = userPropertiesPageView = null;
					if (!assignedTicketsView) {
						assignedTicketsView = new AssignedTicketsView({
							model : new ViewTicketsModel({})
						});
						$('#userPanelPageContainer').replaceWith(
								assignedTicketsView.render().el);
						handleErrorStyle();
					}

					$('#mus').hide();
					$('#mcs').hide();
					$('#ats').show();
					$('#mts').hide();
					$('#sts').hide();
					$('#ups').hide();

					$('#atn').hide();
					$('#mtn').show();
					$('#stn').show();
					$('#upn').show();
					$('#mun').show();
					$('#mcn').show();
				});
		
		if(sessionStorage.loggedUserRights == "SysAdmin"){
			$('#manageCategoriesButton').click(
					function() {
						assignedTicketsView = userTicketsView = createTicketPageView = manageUsersView = userPropertiesPageView = null;
						if (!manageCategoriesView) {
							manageCategoriesView = new ManageCategoriesView({});
							$('#userPanelPageContainer').replaceWith(manageCategoriesView.render().el);
							handleErrorStyle();
						}
						$('#mus').hide();
						$('#mcs').show();
						$('#ats').hide();
						$('#mts').hide();
						$('#sts').hide();
						$('#ups').hide();

						$('#mun').show();
						$('#mcn').hide();
						$('#atn').show();
						$('#mtn').show();
						$('#stn').show();
						$('#upn').show();
					});
			
			$('#manageUsersButton').click(
					function(){
						assignedTicketsView = userTicketsView = createTicketPageView = manageCategoriesView = userPropertiesPageView =  null;
						if(!manageUsersView){
							manageUsersView = new ManageUsersView({
								model: new GetUsersModel({})
							});
							$('#userPanelPageContainer').replaceWith(manageUsersView.render().el);
							handleErrorStyle();
						}
						$('#mus').show();
						$('#mcs').hide();
						$('#ats').hide();
						$('#mts').hide();
						$('#sts').hide();
						$('#ups').hide();

						$('#mun').hide();
						$('#mcn').show();
						$('#atn').show();
						$('#mtn').show();
						$('#stn').show();
						$('#upn').show();
				});
		}
		
	} else {
		$('#btn-mngTk').remove();
	}

	$('#btn-userTk').click(
			function() {
				assignedTicketsView = createTicketPageView = manageCategoriesView = manageUsersView = userPropertiesPageView = null;
				if (!userTicketsView) {
					userTicketsView = new UserTicketsView({
						model : new ViewTicketsModel({})
						 
					});
					$('#userPanelPageContainer').replaceWith(
							userTicketsView.render().el);
					handleErrorStyle();
				}

				$('#ats').hide();
				$('#mts').show();
				$('#sts').hide();
				$('#ups').hide();
				$('#mus').hide();
				$('#mcs').hide();

				$('#atn').show();
				$('#mtn').hide();
				$('#stn').show();
				$('#upn').show();
				$('#mun').show();
				$('#mcn').show();
			});

	$('#btn-subTk').click(
			function() {
				assignedTicketsView = userTicketsView = manageCategoriesView = manageUsersView = userPropertiesPageView = null;
				if (!createTicketPageView) {
					createTicketPageView = new CreateTicketPageView({
						model : new CreateTicketModel({})
					});
					$('#userPanelPageContainer').replaceWith(
							createTicketPageView.render().el);
					handleErrorStyle();
				}

				$('#ats').hide();
				$('#mts').hide();
				$('#sts').show();
				$('#ups').hide();
				$('#mus').hide();
				$('#mcs').hide();

				$('#atn').show();
				$('#mtn').show();
				$('#stn').hide();
				$('#upn').show();
				$('#mun').show();
				$('#mcn').show();
			});

	$('#userInfo')
			.click(
					function() {
						assignedTicketsView = userTicketsView = createTicketPageView = manageCategoriesView = manageUsersView = null;

						// TODO to create view for this & change from here
						if (!userPropertiesPageView) {
							userPropertiesPageView = new UserPropertiesPageView({});
							$('#userPanelPageContainer').replaceWith(
									userPropertiesPageView.render().el);
							handleErrorStyle();
						}
						$('#ats').hide();
						$('#mts').hide();
						$('#sts').hide();
						$('#ups').show();
						$('#mus').hide();
						$('#mcs').hide();

						$('#atn').show();
						$('#mtn').show();
						$('#stn').show();
						$('#upn').hide();
						$('#mun').show();
						$('#mcn').show();
					});

	$('#btn-logOut').click(
			function() {
				sessionStorage.clear();
				
				assignedTicketsView = userTicketsView = createTicketPageView = manageCategoriesView = manageUsersView = userPropertiesPageView = null;
				
				$('#mainContainer').replaceWith(
						_.template($('#frontPageTemplate').html()));
				frontPageFunctionality();

				$('#ats').hide();
				$('#mts').hide();
				$('#sts').hide();
				$('#ups').hide();
				$('#mus').hide();
				$('#mcs').hide();

				$('#atn').show();
				$('#mtn').show();
				$('#stn').show();
				$('#upn').show();
				$('#mun').show();
				$('#mcn').show();
			});

}

function setAssignedTicketsViewToNull() {
	assignedTicketsView = null;
}

function setUserTicketsViewToNull() {
	userTicketsView = null;
}

function setManageUsersViewToNull(){
	manageUsersView = null;
}

function setCreateTicketPageToNull(){
	createTicketPageView = null;
}
function addZero(i) {
	if (i < 10) {
		i = "0" + i;
	}
	return i;
}
function createUserTicketPage(){
	assignedTicketsView = createTicketPageView = manageCategoriesView = manageUsersView = null;
	if (!userTicketsView) {
		userTicketsView = new UserTicketsView({
			model : new ViewTicketsModel({})
		});
		$('#userPanelPageContainer').replaceWith(
				userTicketsView.render().el);
		handleErrorStyle();
	}

	$('#ats').hide();
	$('#mts').show();
	$('#sts').hide();
	$('#ups').hide();
	$('#mus').hide();
	$('#mcs').hide();

	$('#atn').show();
	$('#mtn').hide();
	$('#stn').show();
	$('#upn').show();
	$('#mun').show();
	$('#mcn').show();
}
var addCategoryView,userPropertiesPageView;


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

var assignedTicketsView, userTicketsView;

function userPanelPageFunctionality() {

	var createTicketPageView, respondToTicketPageView;

	if (sessionStorage.loggedUserRights == "Admin") {
		$('#btn-mngTk').click(
				function() {
					userTicketsView = createTicketPageView = null;
					if (!assignedTicketsView) {
						assignedTicketsView = new AssignedTicketsView({
							model : new ViewTicketsModel({})
						});
						$('#userPanelPageContainer').replaceWith(
								assignedTicketsView.render().el);
						handleErrorStyle();
					}

					$('#ats').show();
					$('#mts').hide();
					$('#sts').hide();
					$('#ups').hide();

					$('#atn').hide();
					$('#mtn').show();
					$('#stn').show();
					$('#upn').show();
				});
	} else {
		$('#btn-mngTk').remove();
	}

	$('#btn-userTk').click(
			function() {
				assignedTicketsView = createTicketPageView = null;
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

				$('#atn').show();
				$('#mtn').hide();
				$('#stn').show();
				$('#upn').show();
			});

	$('#btn-subTk').click(
			function() {
				assignedTicketsView = userTicketsView = null;
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

				$('#atn').show();
				$('#mtn').show();
				$('#stn').hide();
				$('#upn').show();
			});

	$('#btn-prop')
			.click(
					function() {
						assignedTicketsView = userTicketsView = createTicketPageView = null;

						// TODO to create view for this & change from here

						$('#userPanelPageContainer').replaceWith(
								"<div id='userPanelPageContainer'></div>");

						$('#ats').hide();
						$('#mts').hide();
						$('#sts').hide();
						$('#ups').show();

						$('#atn').show();
						$('#mtn').show();
						$('#stn').show();
						$('#upn').hide();
					});

	$('#btn-logOut').click(
			function() {
				sessionStorage.clear();
				
				assignedTicketsView = userTicketsView = createTicketPageView = null;
				
				$('#mainContainer').replaceWith(
						_.template($('#frontPageTemplate').html()));
				frontPageFunctionality();

				$('#ats').hide();
				$('#mts').hide();
				$('#sts').hide();
				$('#ups').hide();

				$('#atn').show();
				$('#mtn').show();
				$('#stn').show();
				$('#upn').show();
			});

}

function setAssignedTicketsViewToNull() {
	assignedTicketsView = null;
}

function setUserTicketsViewToNull() {
	userTicketsView = null;
}

function addZero(i) {
	if (i < 10) {
		i = "0" + i;
	}
	return i;
}
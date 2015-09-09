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

function userPanelPageFunctionality() {
	
	var assignedTicketsView, userTicketsView, createTicketPageView, respondToTicketPageView;

	$('#btn-mngTk').click(
			function() {

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
}
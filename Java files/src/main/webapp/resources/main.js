$(document).ready((function() {
	(function() {
		
		if(typeof(Storage) !== "undefined") {
			if (sessionStorage.loggedUserId) {
				$('#frontPage').hide();
				$('#userPanelPage').show();
			} 
			else {
				$('#frontPage').show();
				$('#userPanelPage').hide();
			}
		}
		else{
			alert("To load this site you need a browser not a dumpster !")
		}
		
		$('#logoWrapper').append("<img id='logo' src='/cts/resources/img/logo.png' align='middle'>");
		
		var frontPageView = new GenericFrontPageView({});
		$('#frontPage').append(frontPageView.render().el);
		
		var userPanelPageView = new GenericUserPanelPageView({});
		$('#userPanelPage').append(userPanelPageView.render().el);

		var logInView, registerView, recoveryView, assignedTicketsView, userTicketsView, createTicketPageView, respondToTicketPageView;
		
		$('#toggle-login').click(function() {
			if(!logInView){
				logInView = new LogInView({
					model : new LogInModel({})
				});
				$('#logIn').append(logInView.render().el);
				handleErrorStyle();
			};
			$('#logIn').toggle();
			$('#register').hide();
			$('#recovery').hide();
		});

		$('#toggle-signup').click(function() {
			if(!registerView){
				registerView = new RegisterView({
					model : new RegisterModel({})
				});
				$('#register').append(registerView.render().el);
				handleErrorStyle();
			};
			$('#logIn').hide();
			$('#register').toggle();
			$('#recovery').hide();
		});

		$('#toggle-recovery').click(function() {
			if(!recoveryView){
				recoveryView = new RecoveryView({
					model : new RecoveryModel({})
				});
				$('#recovery').append(recoveryView.render().el);
				handleErrorStyle();
			}
			$('#logIn').hide();
			$('#register').hide();
			$('#recovery').toggle();
		});
		
		$('#btn-mngTk').click(function() {
			if(!assignedTicketsView){
				assignedTicketsView = new AssignedTicketsView({
					model : new ViewTicketsModel({})
				});
				$('div#assignedTickets').append(assignedTicketsView.render().el);
				$('#assignedTickets').append(assignedTicketsView.render().el);
				handleErrorStyle();
			} else {
				assignedTicketsView.remove();
				assignedTicketsView = new AssignedTicketsView({
					model : new ViewTicketsModel({})
				});
				$('div#assignedTickets').append(assignedTicketsView.render().el);
				$('#assignedTickets').append(assignedTicketsView.render().el);
				handleErrorStyle();
			};
			$('#welcomePage').hide();
			$('#myTickets').hide();
			$('#createTicket').hide();
			$('#userProperties').hide();
			$('#selectedTicket').hide();
			$('#assignedTickets').show();
			
			$('#ats').show();
			$('#mts').hide();
			$('#sts').hide();
			$('#ups').hide();
			
			$('#atn').hide();
			$('#mtn').show();
			$('#stn').show();
			$('#upn').show();
		});
		
		$('#btn-userTk').click(function() {
			if(!userTicketsView){
				userTicketsView = new UserTicketsView({
					model : new ViewTicketsModel({})
				});
				$('#myTickets').append(userTicketsView.render().el);
				handleErrorStyle();
			} else {
				userTicketsView.remove();
				userTicketsView = new UserTicketsView({
					model : new ViewTicketsModel({})
				});
				$('#myTickets').append(userTicketsView.render().el);
				handleErrorStyle();
			};
			$('#welcomePage').hide();
			$('#assignedTickets').hide();
			$('#createTicket').hide();
			$('#userProperties').hide();
			$('#selectedTicket').hide();
			$('#myTickets').show();
			
			$('#ats').hide();
			$('#mts').show();
			$('#sts').hide();
			$('#ups').hide();
			
			$('#atn').show();
			$('#mtn').hide();
			$('#stn').show();
			$('#upn').show();
		});
		
		$('#btn-subTk').click(function() {
			if(!createTicketPageView){
				createTicketPageView = new CreateTicketPageView({
					model : new CreateTicketModel({})
				});
				$('#createTicket').append(createTicketPageView.render().el);
				handleErrorStyle();
			};
			$('#welcomePage').hide();
			$('#assignedTickets').hide();
			$('#myTickets').hide();
			$('#userProperties').hide();
			$('#selectedTicket').hide();
			$('#createTicket').show();
			
			$('#ats').hide();
			$('#mts').hide();
			$('#sts').show();
			$('#ups').hide();
			
			$('#atn').show();
			$('#mtn').show();
			$('#stn').hide();
			$('#upn').show();
		});
		
		$('#btn-prop').click(function() {
			$('#welcomePage').hide();
			$('#assignedTickets').hide();
			$('#myTickets').hide();
			$('#createTicket').hide();
			$('#selectedTicket').hide();
			$('#userProperties').show();
			
			$('#ats').hide();
			$('#mts').hide();
			$('#sts').hide();
			$('#ups').show();
			
			$('#atn').show();
			$('#mtn').show();
			$('#stn').show();
			$('#upn').hide();
		});
		
		$('#btn-logOut').click(function(){
			sessionStorage.clear();
			$(document).find('#frontPage').show();
			$(document).find('#userPanelPage').hide();
			
			$('#assignedTickets').hide();
			$('#myTickets').hide();
			$('#createTicket').hide();
			$('#userProperties').hide();
			$('#selectedTicket').hide();
			$('#welcomePage').show();
			
			$('#ats').hide();
			$('#mts').hide();
			$('#sts').hide();
			$('#ups').hide();
			
			$('#atn').show();
			$('#mtn').show();
			$('#stn').show();
			$('#upn').show();
		});

	})()
}));

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
		
		$('div#logoWrapper').append("<img id='logo' src='/cts/resources/img/logo.png' align='middle'>");
		
		var frontPageView = new GenericFrontPageView({});
		$('div#frontPage').append(frontPageView.render().el);
		
		var userPanelPageView = new GenericUserPanelPageView({});
		$('div#userPanelPage').append(userPanelPageView.render().el);

		var logInView, registerView, recoverView, assignedTicketsView, userTicketsView, createTicketPageView;
		
		$('#toggle-login').click(function() {
			if(!logInView){
				logInView = new LogInView({
					model : new LogInModel({})
				});
				$('div#logIn').append(logInView.render().el);
			};
			$('#logIn').toggle();
			$('#register').hide();
			$('#recover').hide();
		});

		$('#toggle-signup').click(function() {
			if(!registerView){
				registerView = new RegisterView({
					model : new RegisterModel({})
				});
				$('div#register').append(registerView.render().el);
			};
			$('#logIn').hide();
			$('#register').toggle();
			$('#recover').hide();
		});

		$('#toggle-recover').click(function() {
			if(!recoverView){
				recoverView = new RecoverView({
					model : new RecoverModel({})
				});
				$('div#recover').append(recoverView.render().el);
			}
			$('#logIn').hide();
			$('#register').hide();
			$('#recover').toggle();
		});
		
		$('#btn-mngTk').click(function() {
			if(!assignedTicketsView){
				assignedTicketsView = new AssignedTicketsView({
					model : new ViewTicketsModel({})
				});
				$('div#assignedTickets').append(assignedTicketsView.render().el);
			};
			$('#welcomePage').hide();
			$('#assignedTickets').show();
			$('#myTickets').hide();
			$('#createTicket').hide();
			$('#userProperties').hide();
			
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
				$('div#myTickets').append(userTicketsView.render().el);
			};
			$('#welcomePage').hide();
			$('#assignedTickets').hide();
			$('#myTickets').show();
			$('#createTicket').hide();
			$('#userProperties').hide();
			
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
				$('div#createTicket').append(createTicketPageView.render().el);
			};
			$('#welcomePage').hide();
			$('#assignedTickets').hide();
			$('#myTickets').hide();
			$('#createTicket').show();
			$('#userProperties').hide();
			
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
			
			$('#welcomePage').show();
			$('#assignedTickets').hide();
			$('#myTickets').hide();
			$('#createTicket').hide();
			$('#userProperties').hide();
			
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

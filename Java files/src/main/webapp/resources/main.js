$(document).ready((function() {
	(function() {
		
		$('div#logoWrapper').append("<img id='logo' src='/cts/resources/img/logo.png' align='middle'>");
		
		var frontPageView = new GenericFrontPageView({});
		$('div#frontPage').append(frontPageView.render().el);
		
		var userPanelPageView = new GenericUserPanelPageView({});
		$('div#userPanelPage').append(userPanelPageView.render().el);
		
		var logInModel = new LogInModel({});
		var logInView = new LogInView({
			model : logInModel
		});
		$('div#logIn').append(logInView.render().el);

		var registerModel = new RegisterModel({});
		var registerView = new RegisterView({
			model : registerModel
		});
		$('div#register').append(registerView.render().el);

		var recoverModel = new RecoverModel({});
		var recoverView = new RecoverView({
			model : recoverModel
		});
		$('div#recover').append(recoverView.render().el);
		
		var assignedTicketsModel = new AssignedTicketsModel({});
		var assignedTicketsView = new AssignedTicketsView({
			model : assignedTicketsModel
		});
		$('div#assignedTickets').append(assignedTicketsView.render().el);
		
		var userTicketsModel = new UserTicketsModel({});
		var userTicketsView = new UserTicketsView({
			model : userTicketsModel
		});
		$('div#myTickets').append(userTicketsView.render().el);
		
		var createTicketModel = new CreateTicketModel({});
		var createTicketPageView = new CreateTicketPageView({
			model : createTicketModel
		});
		$('div#createTicket').append(createTicketPageView.render().el);

		$('#toggle-login').click(function() {
			$('#logIn').toggle();
			$('#register').hide();
			$('#recover').hide();
		});

		$('#toggle-signup').click(function() {
			$('#logIn').hide();
			$('#register').toggle();
			$('#recover').hide();
		});

		$('#toggle-recover').click(function() {
			$('#logIn').hide();
			$('#register').hide();
			$('#recover').toggle();
		});
		
		$('#btn-asigTk').click(function() {
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

	})()
}));

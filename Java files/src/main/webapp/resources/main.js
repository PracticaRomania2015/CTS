$(document).ready((function() {
	
	// ========================= Generate page by current state =========================
	
		if(typeof(Storage) !== "undefined") {
			if (sessionStorage.loggedUserId) {
				$('#mainContainer').replaceWith(_.template($('#userPanelPageTemplate').html()));
				userPanelPageFunctionality();
			} 
			else {
				$('#mainContainer').replaceWith(_.template($('#frontPageTemplate').html()));
				frontPageFunctionality();
			}
		}
		else{
			alert("To load this site you need a browser not a dumpster !")
		}
		
		$('#logoWrapper').append("<img id='logo' src='/cts/resources/img/logo.png' align='middle'>");

	// ========================= Generate log in page child views based on button clicks =========================
		
//		function frontPageFunctionality(){
//		
//			var logInView, registerView, recoveryView;
//			
//			$('#toggle-login').click(function() {
//				registerView = recoveryView = null;
//				if(!logInView){
//					logInView = new LogInView({
//						model : new LogInModel({})
//					});	
//					$('#frontPageContainer').replaceWith(logInView.render().el);
//					handleErrorStyle();
//				}else{
//					$('#frontPageContainer').empty();
//					logInView = null;
//				};
//			});
//	
//			$('#toggle-signup').click(function() {
//				logInView = recoveryView = null;
//				if(!registerView){
//					registerView = new RegisterView({
//						model : new RegisterModel({})
//					});
//					$('#frontPageContainer').replaceWith(registerView.render().el);
//					handleErrorStyle();
//				}else{
//					$('#frontPageContainer').empty();
//					registerView = null;
//				};
//			});
//	
//			$('#toggle-recovery').click(function() {
//				logInView = registerView = null;
//				if(!recoveryView){
//					recoveryView = new RecoveryView({
//						model : new RecoveryModel({})
//					});
//					$('#frontPageContainer').replaceWith(recoveryView.render().el);
//					handleErrorStyle();
//				}else{
//					$('#frontPageContainer').empty();
//					recoveryView = null;
//				};
//			});
//		}
	// ========================= Generate user panel page based on button clicks =========================
//		function userPanelPageFunctionality(){
//			var assignedTicketsView, userTicketsView, createTicketPageView, respondToTicketPageView;
//			
//			$('#btn-mngTk').click(function() {
//	
//				if(!assignedTicketsView){
//					assignedTicketsView = new AssignedTicketsView({
//						model: new ViewTicketsModel({})
//					});
//					$('#userPanelPageContainer').replaceWith(assignedTicketsView.render().el);
//					handleErrorStyle();
//				}
//				
//				$('#ats').show();
//				$('#mts').hide();
//				$('#sts').hide();
//				$('#ups').hide();
//				
//				$('#atn').hide();
//				$('#mtn').show();
//				$('#stn').show();
//				$('#upn').show();
//			});
//		}
		/*$('#btn-userTk').click(function() {
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
		});*/
}));

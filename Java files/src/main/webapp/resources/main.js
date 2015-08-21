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

	})()
}));

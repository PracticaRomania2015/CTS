/*! CTS Login View */

var LoginView = Backbone.View.extend({
	
	viewState: false,
	
	events: {
		'click #login': 'submit',
		'keydown input': 'submit',
		'mouseup ' : function(e){hideNotification(e)}
	},
	
	render: function () {
		preloadView();
		this.$el.empty();
		var header = createElem('h1',{class:'mainMenuPage'},'Log In');
		var inputEmail = createElem('input',{id:'email',class:'validationTooltip',type:'text',placeholder:'E-mail'});
		var inputPassword = createElem('input',{id:'password',class:'validationTooltip',type:'password',placeholder:'Password'});
		var buttonLogin = createElem('div',{id:'login',class:'mainMenuPageButton'},'Log In');
		var form = createElem('form',{class: 'mainMenuPageForm'},[inputEmail,inputPassword,buttonLogin]);
		this.$el.attr('id','loginTemplate');
		this.$el.append(header,form);
		this.setElement(this.$el);
		return this;
	},
	
	submit: function (e) {
		if (e.keyCode == 13 || e.type == 'click') {
			var user = new LoginModel({
				email: $('#email').val(),
				password: $('#password').val()
			});
			var errors = user.check();
			loadValidationErrors(errors);
			if (hasValues(errors)) {
				handleErrorStyle();
			} else {
				user.save({}, {
					success: function (model,response) {
						if (response.type == 'success') {
							sessionStorage.loggedUserId = response.data.userId;
							sessionStorage.loggedUserFirstName = response.data.firstName;
							sessionStorage.loggedUserLastName = response.data.lastName;
							sessionStorage.loggedUserTitle = response.data.title;
							sessionStorage.loggedUserEmail = response.data.email;
							sessionStorage.loggedUserRights = response.data.role.roleName;
							sessionStorage.loggedUserName = response.data.firstName + " " +response.data.lastName;
							clearMainMenu();
							initUserMenu();
							$('#mainContainer').append(userMenu.render().el,welcome.render().el);
						} else if (response.type == 'error') {
							popNotification(response.description);
						} else {
							popNotification('Unknown error!');
						};
					},
					error: function (model,response) {
						console.log('Server error!');
					}
				});
			};
		}
	}
});

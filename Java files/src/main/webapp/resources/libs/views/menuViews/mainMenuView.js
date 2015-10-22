/*! CTS Main Menu View */

var MainMenuView = Backbone.View.extend({
	
	events: {
		'click #toggleLogin': 'login',
		'click #toggleRegister': 'register',
		'click #toggleRecovery': 'recovery'
	},
	
	tagName: 'nav',

	render: function () {
		preloadView();
		this.$el.empty();
		var toggleLogin = createElem('div',{id:'toggleLogin',class:'mainMenuButton'},'Log In');
		var toggleRegister = createElem('div',{id:'toggleRegister',class:'mainMenuButton'},'Register');
		var toggleRecovery = createElem('div',{id:'toggleRecovery',class:'mainMenuButton'},'Recovery');
		this.$el.attr('id', 'mainMenuTemplate');
		this.$el.append(toggleLogin,toggleRegister,toggleRecovery);
		this.setElement(this.$el);
		return this;
	},
  
	login: function () {
		if (!login.viewState) {
			register.close();
			recovery.close();
			$('#mainContainer').append(login.render().el);
			$('#email').focus();
			login.viewState = true;
		} else {
			login.close();
		};
	},
	
	register: function () {
		if (!register.viewState) {
			login.close();
			recovery.close();
			$('#mainContainer').append(register.render().el);
			register.viewState = true;
		} else {
			register.close();
		};
	},
	
	recovery: function() {
		if (!recovery.viewState) {
			login.close();
			register.close();
			$('#mainContainer').append(recovery.render().el);
			recovery.viewState = true;
		} else {
			recovery.close();
		};
	}
});
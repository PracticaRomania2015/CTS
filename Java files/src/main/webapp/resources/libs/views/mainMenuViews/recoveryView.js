/*! CTS Recovery View */

var RecoveryView = Backbone.View.extend({
	
	viewState: false,

	events: {
		'click #recover': 'submit',
		'keydown input': 'submit'
	},
	
	render: function () {
		this.$el.empty();
		var header = createElem('h1',{class:'mainMenuPage'},'Password Recovery');
		var inputEmail = createElem('input',{id:'email',class:'validationTooltip',type:'text', placeholder:'E-mail'});
		var buttonRecovery = createElem('div',{id:'recover',class:'mainMenuPageButton'},'Send Recovery Mail');
		var form = createElem('form',{class: 'mainMenuPageForm'}, [inputEmail,buttonRecovery]);
		this.$el.attr('id', 'recoveryTemplate');
		this.$el.append(header,form);
		this.setElement(this.$el);
		return this;
	},
	
	submit : function (e) {
		if (e.keyCode == 13 || e.type =='click') { 
			var user = new RecoveryModel({
				email: $('#email').val()
			});
			var errors = user.check();
			loadValidationErrors(errors);
			if (hasValues(errors)) {
				handleErrorStyle();
			} else {
				user.save({}, {
					success : function(model,response) {
						if (response.type == 'success'){
							popNotification(response.description);
						} else if (response.type == 'error') {
							popNotification(response.description);
						} else {
							popNotification('Unknown error!');
						};
					},
					error : function(model,response) {
						console.log('Server error!');
					}
				});
			};
		};
	}
});
/*! CTS Register View */

var RegisterView = Backbone.View.extend({

	viewState: false,
	
	events: {
		'click #register': 'submit',
		'keydown input': 'submit'
	},
	
	render: function () {
		this.$el.empty();
		var header = createElem('h1',{class:'mainMenuPage'},'Register');
		var firstOption = createElem('option',{selected:'',value:'Mr'},'Mr.');
		var secondOption = createElem('option',{value:'Mrs'},'Mrs.');
		var thirdOption = createElem('option',{value:'Ms'},'Ms.');
		var select = createElem('select',{id:'title'},[firstOption,secondOption,thirdOption]);
		var inputFirstName = createElem('input',{id:'firstName',class:'validationTooltip',type:'text', placeholder:'First Name'});
		var inputLastName = createElem('input',{id:'lastName',class:'validationTooltip',type:'text', placeholder:'Last Name'});
		var inputEmail = createElem('input',{id:'email',class:'validationTooltip',type:'text', placeholder:'E-mail'});
		var inputPassword = createElem('input',{id:'password',class:'validationTooltip',type:'password', placeholder:'Password'});
		var inputConfirmPassword = createElem('input',{id:'confirmPassword',class:'validationTooltip',type:'password', placeholder:'Confirm Password'});
		
		var firstQuestionDefaultOption = createElem('option',{selected:'', disabled:'', value:'', style:'display: none;'},'Select the first question...');
		var firstQuestion = createElem('select',{id:'firstQuestionsDropbox', class: 'validationTooltip'}, [firstQuestionDefaultOption]);
		var firstAnswer = createElem('input',{id:'firstAnswer', class:'validationTooltip',type:'text', placeholder:'Your Answer'});
		var secondQuestionDefaultOption = createElem('option',{selected:'', disabled:'', value:'', style:'display: none;'},'Select the second question...');
		var secondQuestion = createElem('select',{id:'secondQuestionsDropbox', class: 'validationTooltip'}, [secondQuestionDefaultOption]);
		var secondAnswer = createElem('input',{id:'secondAnswer', class:'validationTooltip',type:'text', placeholder:'Your Answer'});
		
		var buttonRegister = createElem('div',{id:'register',class:'mainMenuPageButton'},'Register');
		var form = createElem('form',{class: 'mainMenuPageForm'}, [select,inputFirstName,inputLastName,inputEmail,inputPassword,inputConfirmPassword,firstQuestion, firstAnswer, secondQuestion, secondAnswer, buttonRegister]);
		this.$el.attr('id', 'registerTemplate');
		this.$el.append(header,form);
		this.setElement(this.$el);
		this.loadPageData();
		return this;
	},
	
	loadPageData: function(){
		var currentView = this;
		var questions = new GetQuestionsModel({});
		
		questions.save({}, {
			success: function(model, response){
				if(response.type == 'success'){
					_.each(response.data, function(e){
						$(currentView.$el).find('#firstQuestionsDropbox').append(createElem('option',{value:e.questionId},e.question));
						$(currentView.$el).find('#secondQuestionsDropbox').append(createElem('option',{value:e.questionId},e.question));
					});
				}else if(response.type=='error'){
					popNotification(response.description);
				}else{
					popNotification('Unknown error!');
				};
			},
			error: function(model, response){
				console.log('Server error!');
			}
		})
	},

	submit : function (e) {
		if ( e.keyCode  === 13 || e.type =='click') {
			var question_1 = new Backbone.Model({
				questionId: $('#firstQuestionsDropbox').val(),
				question: $('#firstQuestionsDropbox').text()
			});
			var question_2 = new Backbone.Model({
				questionId: $('#secondQuestionsDropbox').val(),
				question: $('#secondQuestionsDropbox').text()
			});
			var user = new RegisterModel({
				title : $('#title').attr('selected', 'selected').val(),
				firstName : $('#firstName').val(),
				lastName : $('#lastName').val(),
				email : $('#email').val(),
				password : $('#password').val(),
				question_1 : question_1,
				questionAnswer_1 : $('#firstAnswer').val(),
				question_2 : question_2,
				questionAnswer_2 : $('#secondAnswer').val()
			});
			var errors = user.check();	
			if ($('#password').val() != $('#confirmPassword').val()) {
				errors['#confirmPassword'] = getValue('registerIncorrectNewPassword');
			} else {
				errors['#confirmPassword'] = '';
			}
			loadValidationErrors(errors);
			if (hasValues(errors)) {
				handleErrorStyle();
			} else {
				user.save({}, {
					success : function(model, response) {
						if (response.type == 'success') {
							register.close();
							$('#mainContainer').append(login.render().el);
							$('#email').val(user.get('email'));
							$('#password').focus();
							popNotification('Account created!');
						} else if (response.type == 'error') {
							popNotification(response.description);
						} else {
							popNotification('Unknown error!');
						};
					},
					error : function(model, response) {
						console.log('Server error!');
					}
				});
			};
		}
	}
});
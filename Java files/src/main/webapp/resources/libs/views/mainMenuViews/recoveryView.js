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
		

		var firstQuestionDefaultOption = createElem('option',{selected:'', disabled:'', value:'', style:'display: none;'},'Select the first question...');
		var firstQuestion = createElem('select',{id:'firstQuestionsDropbox', class: 'validationTooltip'}, [firstQuestionDefaultOption]);
		var firstAnswer = createElem('input',{id:'firstAnswer', class:'validationTooltip',type:'text', placeholder:'Your Answer'});
		var secondQuestionDefaultOption = createElem('option',{selected:'', disabled:'', value:'', style:'display: none;'},'Select the second question...');
		var secondQuestion = createElem('select',{id:'secondQuestionsDropbox', class: 'validationTooltip'}, [secondQuestionDefaultOption]);
		var secondAnswer = createElem('input',{id:'secondAnswer', class:'validationTooltip',type:'text', placeholder:'Your Answer'});
		
		var buttonRecovery = createElem('div',{id:'recover',class:'mainMenuPageButton'},'Send Recovery Mail');
		var form = createElem('form',{class: 'mainMenuPageForm'}, [inputEmail,firstQuestion, firstAnswer, secondQuestion, secondAnswer, buttonRecovery]);
		this.$el.attr('id', 'recoveryTemplate');
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
		if (e.keyCode == 13 || e.type =='click') { 
			var question_1 = new Backbone.Model({
				questionId: $('#firstQuestionsDropbox').val(),
				question: $('#firstQuestionsDropbox').text()
			});
			var question_2 = new Backbone.Model({
				questionId: $('#secondQuestionsDropbox').val(),
				question: $('#secondQuestionsDropbox').text()
			});
			var user = new RecoveryModel({
				email: $('#email').val(),
				question_1 : question_1,
				questionAnswer_1 : $('#firstAnswer').val(),
				question_2 : question_2,
				questionAnswer_2 : $('#secondAnswer').val()
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
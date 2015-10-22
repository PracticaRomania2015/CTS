/*! CTS User Properties View */

var UserPropertiesView = Backbone.View.extend({
	
	viewState: false,
	
	events:{
		'click #changePasswordButton' : 'submitNewPassword',
		'click #updateUserNotificationsButton' : 'submitUserNotificationsSettings',
		'mouseup ' : function(e){hideNotification(e)}
	},
	
	render: function () {
		preloadView();
		this.$el.empty();
		var header = createElem('h1',{class:'userPage'},sessionStorage.loggedUserName + ' properties page');
		
		// Horizontal separator
		var horizontalSeparatorGradient = createElem('div',{id:'gradient-horizontalLine'});
		var horizontalSeparatorContentForm = createElem('div',{class:'contentFormHorizontalSeparator'},[horizontalSeparatorGradient]);
		var horizontalSeparatorWrapper = createElem('div',{class:'horizontalSeparatorWrapper'},[horizontalSeparatorContentForm]);
		
		// Manage notifications
		var notificationsTableHeadElem1 = createElem('th',{class:'wide-col rounded-tl'},'Category');
		var notificationsTableHeadElem2 = createElem('th',{id:'', class:'wide-col'},'Get emails for new tickets');
		var notificationsTableHeadElem3 = createElem('th',{id:'', class:'wide-col rounded-tr'},'Get emails for new comments');
		var notificationsTableHeadRow = createElem('tr',{},[notificationsTableHeadElem1,notificationsTableHeadElem2,notificationsTableHeadElem3]);
		var notificationsTableHeader = createElem('thead',{},[notificationsTableHeadRow]);
		var notificationsTableBody = createElem('tbody',{id:'userNotificationsTableBody'});
		var getEmailForTicketResponseDiv = createElem('div',{id:'getEmailForTicketResponseDiv'});
		var notificationsTableFootColumn = createElem('th',{id:'rounded-bot',colspan:'3'},[getEmailForTicketResponseDiv]);
		var notificationsTableFootRow = createElem('tr',{},[notificationsTableFootColumn]);
		var notificationsTableFooter = createElem('tfoot',{},[notificationsTableFootRow]);
		var notificationsViewTable = createElem('table',{class:'userPropertiesNotifications'},[notificationsTableHeader,notificationsTableBody,notificationsTableFooter]);
		var updateUserNotificationsButton = createElem('div',{id:'updateUserNotificationsButton'},'Save');
		
		var subtitle = createElem('h3',{},'Update security');
		var oldPasswordInput = createElem('input',{id:'oldPassword',class:'validationTooltip savePassword fieldPass',type:'password',placeholder:'Old Password'});
		var newPasswordInput = createElem('input',{id:'newPassword',class:'validationTooltip savePassword fieldPass',type:'password',placeholder:'New Password'});
		var confirmNewPasswordInput = createElem('input',{id:'confirmNewPassword',class:'validationTooltip savePassword fieldPass',type:'password',placeholder:'Confirm New Password'});
		var changePasswordButton = createElem('div',{id:'changePasswordButton'},'Save');
		var changePasswordContainer = createElem('div',{id:'changePasswordContainer'},[oldPasswordInput,newPasswordInput,confirmNewPasswordInput,changePasswordButton]);
		var userPropertiesForm = createElem('form',{class:'propertiesPanelPage'},[changePasswordContainer]);
		this.$el.attr('id','userPropertiesTemplate').attr('class','context');
		this.$el.append(header,notificationsViewTable,updateUserNotificationsButton,"<br>",horizontalSeparatorWrapper,subtitle,userPropertiesForm);
		this.setElement(this.$el);
		this.loadPageData();
		return this;
	},
	
	loadPageData: function () {
		var currentView = this;
		var user = new Backbone.Model({
			userId : sessionStorage.loggedUserId
		});
		loadUserNotificationsSettingsModel = new LoadUserNotificationsSettings ({ user : user });
		loadUserNotificationsSettingsModel.save({},{
			async: true,
			success: function (model, response) {
				if (response.type == 'success') {
					var checked = response.data.getEmailForTicketResponse ? "checked" : "";
					$('#getEmailForTicketResponseDiv').empty().append("<input type='checkbox' id='getEmailForTicketResponseCheckBox' value='getEmailForTicketResponse' " + checked + ">Get email for ticket response</input>");
					$('#getEmailForTicketResponseCheckBox').click(function(){
						loadUserNotificationsSettingsModel.attributes.data.getEmailForTicketResponse = !loadUserNotificationsSettingsModel.attributes.data.getEmailForTicketResponse;
					});
					
					$('#userNotificationsTableBody').empty();
					_.each(response.data.categoriesNotificationsSettings, function (e) {
						
						currentView.addTableElement(e.category.categoryId, e.category.categoryName, e.getEmailForNewTicket, e.getEmailForNewComment);
						
						$("#" + e.category.categoryId).click(function(){
							e.getEmailForNewTicket = !e.getEmailForNewTicket;
						});
						$("#" + -(e.category.categoryId)).click(function(){
							e.getEmailForNewComment = !e.getEmailForNewComment;
						});
					})
				} else if (response.type == 'error') {
					popNotification(response.description);
				} else {
					popNotification('Unknown error!');
				};
			},
			error: function (model, response) {
				console.log('Server error!');
			}
		});
	},
	
	addTableElement: function(categoryId, categoryName, getEmailForNewTicket, getEmailForNewComment){
		var isChecked1 = getEmailForNewTicket ? true : false;
		var isChecked2 = getEmailForNewComment ? true : false;
		var categoryNameElem = createElem('div',{class:'columnOverflow'},categoryName);
		var tableCategoryName = createElem('td',{},[categoryNameElem]);
		var checkBox1 = createElem('input',{id:categoryId,class:'checkboxClassId',type:'checkbox',checked:isChecked1,});
		var checkBoxWrapper1 = createElem('div',{class:'columnOverflow'},checkBox1);
		var tableCheckbox1 = createElem('td',{},[checkBoxWrapper1]);
		var checkBox2 = createElem('input',{id:-categoryId,class:'checkboxClassId',type:'checkbox',checked:isChecked2,});
		var checkBoxWrapper2 = createElem('div',{class:'columnOverflow'},checkBox2);
		var tableCheckbox2 = createElem('td',{},[checkBoxWrapper2]);
		var tableRow = createElem('tr',{},[tableCategoryName,tableCheckbox1,tableCheckbox2]);
		$('#userNotificationsTableBody').append(tableRow);
	},
	
	submitNewPassword: function () {
		var newPassword = $('#newPassword').val();
		var confirmedNewPassword = $('#confirmNewPassword').val();
		//TODO change this to model confirmation
		//TODO alerts
		if (newPassword != confirmedNewPassword) {
			popNotification("New password doesn't match!");
		} else {
			var userForUpdate = new UserPropertiesModel({
				userId: sessionStorage.loggedUserId,
				title: sessionStorage.loggedUserTitle,
				firstName: sessionStorage.loggedUserFirstName,
				lastName: sessionStorage.loggedUserLastName,
				email: sessionStorage.loggedUserEmail,
				oldPassword: $('#oldPassword').val(),
				password: newPassword
			});
			userForUpdate.save({},{
				success: function (model,response) {
					if (response.type == "success") {
						popNotification("Your password was updated successfully!");
					} else if (response.type == "error") {
						popNotification(response.description);
					} else {
						popNotification("Unknown error!");
					};
				},
				error: function(model, response){
					console.log('Server error!');
				}
			});
		}
	},
	
	submitUserNotificationsSettings: function(){
		var currentModel = this.model;
		var user = new Backbone.Model({
			userId : sessionStorage.loggedUserId
		});
		var userNotificationsSettings = new UserNotificationsSettingsModel ({
			user : user,
			getEmailForTicketResponse : loadUserNotificationsSettingsModel.get("data").getEmailForTicketResponse,
			categoriesNotificationsSettings : loadUserNotificationsSettingsModel.get("data").categoriesNotificationsSettings
		});
		userNotificationsSettings.save({},{
			success: function (model,response) {
				if (response.type == "success") {
					popNotification("Your notifications settings were successfully updated!");
				} else if (response.type == "error"){
					popNotification(response.description);
				} else {
					popNotification("Unknown error!");
				};
			},
			error: function(model, response){
				console.log('Server error!');
			}
		});
	}
});

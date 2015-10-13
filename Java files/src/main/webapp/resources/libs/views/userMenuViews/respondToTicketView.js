/*! CTS Respond To Ticket View */

var RespondToTicketView = Backbone.View.extend({
	
	viewState: false,

	events: {
		'click #respondToTicketButton': 'submit',
		'click #closeTheTicketButton': 'closeTicket',
		'click #assignUserToTicketButton': 'reassignAdmin',
		'click #reassignPriorityToTicketButton': 'reassignPriority',
		'click #reassignCategoryToTicketButton' : 'reassignCategory',
		'change #ticketCategoryDropbox' : 'populateSubcategories',
		'click #reopenTicketButton' : 'reopenTicket'
	},

	render: function() {
		this.$el.empty();
		/* Ticket Title */
		var ticketName = createElem('div',{id:'ticketName'});
		var ticketTitle = createElem('h1',{id:'ticketTitle',class:'userPage'},[ticketName]);
		/* Ticket Comments */
		var ticketCommentWrapper = createElem('div',{id:'ticketCommentsWrapper',class:'ticketInput'});
		/* Ticket Response*/
		var ticketResponse = createElem('textarea',{id:'ticketResponse',class:'validationTooltip ticketInput',rows:'15',maxlength:'2000',placeholder:'Type your response here.'});
		var responseText = createElem('div',{id:'responseText'},[ticketResponse]);
		/*Ticket Properties & Buttons */
		var ticketAdminsDropbox = createElem('select',{id:'ticketAdminsDropBox',class:'reassignTicketAdmin',style:'display: none;'});
		var assignAdminToTicketButton = createElem('div',{id:'assignUserToTicketButton',class:'ticketActionButton',style:'display: none;'},'Assign user');
		var ticketPrioritiesDropbox = createElem('select',{id:'ticketPrioritiesDropBox',class:'reassignTicketPriorities',style:'display: none;'});
		var changePriorityButton = createElem('div',{id:'reassignPriorityToTicketButton',class:'ticketActionButton',style:'display: none;'},'Set priority');
		var changeCategoryDefaultOption = createElem('option',{selected:'',disabled:'',value:'',style:'display: none;'},'Select your category');
		var changeCategorySelect = createElem('select',{id:'ticketCategoryDropbox',class:'reassignTicketCategory'},[changeCategoryDefaultOption]);
		var changeSubcategoryDefaultOption = createElem('option',{selected:'',disabled:'',value:'',style:'display: none;'},'Select your subcategory');
		var changeSubcategorySelect = createElem('select',{id:'ticketSubcategoryDropbox',class:'reassignTicketSubcategory',style:'color: #808080'},[changeSubcategoryDefaultOption]);
		var changeCategory = createElem('div',{id:'reassignCategoryToTicketButton',class:'ticketActionButton'},'Set category');
		var submitTicketButton = createElem('div',{id:'respondToTicketButton',class:'ticketActionButton'},'Submit');
		var closeTicketButton = createElem('div',{id:'closeTheTicketButton',class:'ticketActionButton'},'Close Ticket');
		var submitButtonsWrapper = createElem('div',{id:'submitButtonsWrapper'},[submitTicketButton,closeTicketButton]);
		var responseButtons = createElem('div',{id:'responseButtons'},[ticketAdminsDropbox,assignAdminToTicketButton,ticketPrioritiesDropbox,changePriorityButton,changeCategorySelect,changeSubcategorySelect,changeCategory,submitButtonsWrapper]);
		var ticketResponseWrapper = createElem('div',{id:'ticketResponseWrapper'},[responseText,responseButtons]);
		var reopenTicketButton = createElem('div',{id:'reopenTicketButton',class:'ticketActionButton',style:'display: none;'},'Reopen Ticket');
		this.$el.attr('id','respondToTicketTemplate').attr('class','context');
		this.$el.append(ticketTitle,ticketName,ticketCommentWrapper,ticketResponseWrapper,reopenTicketButton);
		this.setElement(this.$el);
		this.loadPageData();
		return this;
	},
	
	loadPageData : function() {
		var currentModel = this.model;
		var ticket = new GetTicketContentModel({ ticketId : currentModel.get('ticketId') });
		var categories = new GetCategoriesModel({ userId : sessionStorage.loggedUserId });
		categories.save({}, {
			success : function (model, response) {
				if (response.type == 'success'){
					_.each(response.data, function(e) {
						$('#ticketCategoryDropbox').append(createElem('option',{value:e.categoryId,text:e.categoryName}));					
					});
				} else if (response.type == 'error'){
					popNotification(response.description);
				} else {
					popNotification('Unknown error!');
				};
			},
			error : function (model, response) {
				console.log('Server error!');
			}
		});
		var lastLeftCommentUserId = 0;
		var lastRightCommentUserId = 0;
		var loggedUserId = Number(sessionStorage.loggedUserId);
		ticket.save({}, {
			success : function(model, response) {
					if (response.type == 'success') {
					$('#ticketCommentsWrapper').remove().end();
					_.each(response.data.comments, function(e) {
						var ticketPriority = response.data.priority.priorityId;
						var ticketCategory = response.data.category.categoryId;
						var parentTicketCategory = response.data.category.parentCategoryId;
						
						var priorities =  new GetPrioritiesModel({ userId : sessionStorage.loggedUserId });
						priorities.save({}, {
							success : function (model, response) {
								if (response.type == 'success') {
									$('#ticketPrioritiesDropBox').find('option').remove().end();
									_.each(response.data, function(e) {
										if (ticketPriority == e.priorityId) {
											$('#ticketPrioritiesDropBox').append(createElem('option',{selected:'',value:e.priorityId},e.priorityName));
										} else {
											$('#ticketPrioritiesDropBox').append(createElem('option',{value:e.priorityId},e.priorityName));
										};
									});
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
						var categories = new GetCategoriesModel({ userId : sessionStorage.loggedUserId });
						categories.save({}, {
							success : function (model, response) {
								$('#ticketCategoryDropbox').find('option').remove().end();
								if (response.type == 'success'){
									_.each(response.data, function(e) {
										if (parentTicketCategory!=0) {
											if (parentTicketCategory == e.categoryId) {
												$('#ticketCategoryDropbox').append(createElem('option',{value:e.categoryId,selected:''},e.categoryName));
											} else {
												$('#ticketCategoryDropbox').append(createElem('option',{value:e.categoryId},e.categoryName));
											}
											
											var subCategories = new GetSubcategoriesModel({
												categoryId: parentTicketCategory,
												//categoryName : $("#ticketCategoryDropbox option:selected").text()
											});
											
											subCategories.save({}, {
												success : function (model, response) {
													if (response.type == 'success'){
														var selectedCategory = $('#ticketCategoryDropbox').val();
														$('#ticketSubcategoryDropbox').find('option').remove().end().append(createElem('option',{selected:'',style:'display: none;',value:''},'Select your subcategory'));
														if ($.isEmptyObject(response.data)) {
															$('#ticketSubcategoryDropbox').attr('disabled', true);
															$('#ticketSubcategoryDropbox').css('color', '#808080');
														} else {
															_.each(response.data, function(e) {
																if (ticketCategory==e.categoryId) {
																	$('#ticketSubcategoryDropbox').append(createElem('option',{selected:'',value:e.categoryId},e.categoryName));
																} else {
																	$('#ticketSubcategoryDropbox').append(createElem('option',{value:'e.categoryId'},e.categoryName));
																}
															});
															$('#ticketSubcategoryDropbox').attr('disabled', false);
															$('#ticketSubcategoryDropbox').removeAttr('style')
														}
													} else if (response.type == 'error'){
														popNotification(response.description);
													} else {
														popNotification('Unknown error!');
													};
												},
												error : function(model, response) {
													console.log('Server error!');
												}
											});
										}
										else if(ticketCategory==e.categoryId){
											$('#ticketCategoryDropbox').append(createElem('option',{selected:'',value:e.categoryId},e.categoryName));
										}
										else{
											$('#ticketCategoryDropbox').append(createElem('option',{value:e.categoryId},e.categoryName));
										};
									});
								} else if (response.type == "error"){
									popNotification(response.description);
								} else {
									popNotification("Unknown error!");
								};
							},
							error : function(model, response) {
								console.log('Server error!');
							}
						});
						if(response.data.state.stateName == 'Closed') {
							$('#ticketResponseWrapper').remove();
							$('#reopenTicketButton').show();
						};
						var date = new Date(e.dateTime);
						var hour = addZero(date.getHours());
						var minute = addZero(date.getMinutes());
						var dateString = date.toLocaleDateString() + ' ' + hour + ':' + minute;
						if (loggedUserId == e.user.userId) {
							if (lastLeftCommentUserId == loggedUserId){
								var commentDate = createElem('div',{class:'commentDateLeft'},dateString);
								var commentBlock = createElem('div',{class:'commentBlockLeft'},[e.comment,commentDate]);
								var commentWrapper = createElem('div',{class:'commentWrapper'},[commentBlock]);
								$('#ticketCommentsWrapper').append(commentWrapper);
								lastRightCommentUserId = 0;
							} else {
								var commentDate = createElem('div',{class:'commentDateLeft'},dateString);
								var commentUserName = createElem('div',{class:'userNameLeft'},e.user.firstName);
								var commentBlock = createElem('div',{class:'commentBlockLeft'},[e.comment,commentDate]);
								var commentWrapper = createElem('div',{class:'commentWrapper'},[commentUserName,commentBlock]);
								$('#ticketCommentsWrapper').append(commentWrapper);
								lastLeftCommentUserId = loggedUserId;
								lastRightCommentUserId = 0;
							}
						} else if (lastRightCommentUserId !=0 && lastRightCommentUserId == e.user.userId){
							var commentDate = createElem('div',{class:'commentDateRight'},dateString);
							var commentBlock = createElem('div',{class:'commentBlockRight'},[e.comment,commentDate]);
							var commentWrapper = createElem('div',{class:'commentWrapper'},[commentBlock]);
							$('#ticketCommentsWrapper').append(commentWrapper);
							lastLeftCommentUserId =0;
						} else if (lastRightCommentUserId==0){
							var commentDate = createElem('div',{class:'commentDateRight'},dateString);
							var commentUserName = createElem('div',{class:'userNameRight'},e.user.firstName);
							var commentBlock = createElem('div',{class:'commentBlockRight'},[e.comment,commentDate]);
							var commentWrapper = createElem('div',{class:'commentWrapper'},[commentUserName,commentBlock]);
							$('#ticketCommentsWrapper').append(commentWrapper);
						} else {
							var commentDate = createElem('div',{class:'commentDateRight'},dateString);
							var commentUserName = createElem('div',{class:'secondUserNameRight'},e.user.firstName);
							var commentBlock = createElem('div',{class:'commentBlockRight'},[e.comment,commentDate]);
							var commentWrapper = createElem('div',{class:'commentWrapper'},[commentUserName,commentBlock]);
							$('#ticketCommentsWrapper').append(commentWrapper);
						};
						lastRightCommentUserId = e.user.userId;
						lastLeftCommentUserId = 0;
					});
					$('#ticketTitle').empty().append('"' + response.data.subject + '"' + ' is ');
					var assignedToId = response.data.assignedToUser.userId;
					var ticketPriority = response.data.priorityId;
					var categoryAdmins = new getAdminForCategory({
						categoryId : response.data.category.categoryId
					});
					var ticketSubject = response.data.subject;
					var ticketState = response.data.state.stateName;
					var ticketOwnerId = response.data.comments[0].user.userId;
					categoryAdmins.save({},{
						success : function (model, response) {
							if (response.type == 'success'){
								var assignedUserName;
								$('#ticketAdminsDropBox').find('option').remove().end();
								_.each(response.data,function(e) {
									if (assignedToId == e.userId) {
										assignedUserName = e.firstName + ' ' + e.lastName;
									};
								});
								$('#ticketPrioritiesDropBox').show();
								$('#reassignPriorityToTicketButton').show();
								if (sessionStorage.loggedUserRights == 'Admin' || sessionStorage.loggedUserRights == 'SysAdmin') {
									if (currentModel.get('provenience') == 'assignedTickets') {
										$('#ticketAdminsDropBox').show();
										$('#assignUserToTicketButton').show();
										//TODO: why set rows to 15?
										$('#ticketResponse').attr('rows',15);
									} else if (currentModel.get('provenience') == 'userTickets') {
										$('#ticketAdminsDropBox').hide();
										$('#assignUserToTicketButton').hide();
										//TODO: and here to 12?
										$('#ticketResponse').attr('rows',12);
									} else {
										console.log('Invalid Session Storage Data!');
									};
									if (assignedToId == 0) {
										$('#ticketAdminsDropBox').append(createElem('option',{selected:'',value:'0'},'Set ticket as unassigned'));
										$('#ticketTitle').append('currently unassigned');
									} else {
										$('#ticketAdminsDropBox').append(createElem('option',{value:'0'},'Set ticket as unassigned'));
										$('#ticketTitle').append('assigned to ' + assignedUserName);
									}
									_.each(response.data, function (e) {
										if (assignedToId == e.userId) {
											$('#ticketAdminsDropBox').append(createElem('option',{selected:'',value:e.userId},e.firstName + ' ' + e.lastName));
										} else {
											$('#ticketAdminsDropBox').append(createElem('option',{value:e.userId},e.firstName + ' ' + e.lastName));
										}
									});
								} else if (assignedToId == 0) {
									$('#ticketTitle').append('currently unassigned');
								} else {
									$('#ticketTitle').append('assigned to ' + assignedUserName);
								};
								if (ticketState == 'Closed') {
									$('#ticketTitle').empty().append('"' + ticketSubject + '"' + ' is closed');
								}
							} else if (response.type == 'error') {
								popNotification(response.description);
							} else {
								popNotification('Unknown error!');
							};
						},
						error : function (model,response) {
							console.log('Server error!');
						}
					});
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
	},
	
	reassignAdmin: function () {
		var selectedUser = new Backbone.Model({
			userId : $('#ticketAdminsDropBox option:selected').val()
		});
		var reassignAdminToTicket = new AssignAdminToTicket({
			assignedToUser : selectedUser,
			ticketId : this.model.get('ticketId')
		});
		reassignAdminToTicket.save({}, {
			async: false,
			success : function(model, response) {
				if (response.type == 'success'){
					popNotification('Ticket reassigned!');
				} else if (response.type == 'error'){
					popNotification(response.description);
				} else {
					popNotification('Unknown error!');
				};
			},
			error : function(model, response) {
				console.log('Server error!');
			}
		});
		this.loadPageData();
	},
	
	reassignPriority: function () {
		var selectedPriority = new Backbone.Model({
			priorityId : $('#ticketPrioritiesDropBox  option:selected').val()
		});
		var reassignPriorityToTicket = new AssignPriorityToTicket({
			priority : selectedPriority,
			ticketId : this.model.get('ticketId')
		});
		reassignPriorityToTicket.save({}, {
			success : function(model, response) {
				if (response.type == 'success'){
					popNotification('Priority changed successfully!');
				} else if (response.type == 'error'){
					popNotification(response.description);
				} else {
					popNotification('Unknown error!');
				};
			},
			error : function(model, response) {
				console.log('Server error!');
			}
		});
	},
	
	reassignCategory : function () {
		var selectedCategoryId;
		if( $('#ticketSubcategoryDropbox').is(':enabled') && $('#ticketSubcategoryDropbox').val()!=0 ){
			selectedCategoryId= $('#ticketSubcategoryDropbox  option:selected').val()
		} else{
			selectedCategoryId = $('#ticketCategoryDropbox  option:selected').val()
		};
		var selectedCategory = new Backbone.Model({
			categoryId : selectedCategoryId
		});
		var reassignCategoryToTicket = new AssignCategoryToTicket({
			category : selectedCategory,
			ticketId : this.model.get('ticketId')
		});
		reassignCategoryToTicket.save({}, {
			success : function(model, response) {
				if (response.type == 'success'){
					popNotification('Category changed successfully!');
				} else if (response.type == "error"){
					popNotification(response.description);
				} else {
					popNotification('Unknown error!');
				};
			},
			error : function(model, response) {
				console.log('Server error!');
			}
		});
	},
	
	populateSubcategories : function() {
		var categories = new GetSubcategoriesModel({
			categoryId : $('#ticketCategoryDropbox option:selected').val(),
			categoryName : $('#ticketCategoryDropbox option:selected').text()
		});
		categories.save({}, {
			success : function(model, response) {
				if (response.type == 'success') {
					var selectedCategory = $('#ticketCategoryDropbox').val();
					$('#ticketSubcategoryDropbox').find('option').remove().end().append(createElem('option',{selected:'',value:'',style:'display: none;'},'Select your subcategory'));
					if ($.isEmptyObject(response.data)) {
						$('#ticketSubcategoryDropbox').attr('disabled', true);
						$('#ticketSubcategoryDropbox').css('color', '#808080');
					} else {
						_.each(response.data, function(e) {
							$('#ticketSubcategoryDropbox').append($(createElem('option',{value:e.categoryId},e.categoryName)));
						});
						$('#ticketSubcategoryDropbox').attr('disabled', false);
						$('#ticketSubcategoryDropbox').removeAttr('style')
					}
				} else if (response.type == 'error') {
					popNotification(response.description);
				} else {
					popNotification('Unknown error!');
				};
			},
			error : function (model, response) {
				console.log('Server error!');
			}
		});
	},
	
	reopenTicket : function(){
		var reopenTicket = new ReopenTicketModel({
			ticketId : this.model.get('ticketId')
		});
		reopenTicket.save({},{
			async: false,
			success : function (model, response) {
				if (response.type == 'success') {
					popNotification('Ticket reopened!');
				} else if (response.type == 'error') {
					popNotification(response.description);
				} else {
					popNotification('Unknown error!');
				};
			},
			error : function (model, response) {
				console.log('Server error!');
			}
		});
		userMenu.closeContext();
		$('#mainContainer').append(respondToTicket.render().el);
	},
	
	closeTicket: function(){
		var close = new CloseTicketModel({
			ticketId : this.model.get('ticketId')
		});
		if ($('#ticketResponse').val() != '') {
			this.submit();
		};
		close.save({},{
			async: false,
			success: function (model, response) {
				if (response.type == 'success') {
					popNotification('Ticket closed!');
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
		this.loadPageData();
	},

	submit: function () {
		var currentUser = new Backbone.Model({
			userId: sessionStorage.loggedUserId,
		});
		var ticketComment = new TicketComment({
			user: currentUser,
			dateTime: new Date().getTime(),
			comment: $('#ticketResponse').val()
		});
		var resp = new RespondToTicketModel({
			ticketId: this.model.get('ticketId'),
			comments: [ ticketComment ]
		});
		resp.save({}, {
			async: false,
			success : function(model, response) {
				if (response.type == 'success'){
				} else if (response.type == 'error'){
					popNotification(response.description);
				} else {
					popNotification('Unknown error!');
				};
			},
			error: function(model, response) {
				console.log('Server error!');
			}
		});
		this.loadPageData();
		$('#ticketResponse').val('');
	}
});

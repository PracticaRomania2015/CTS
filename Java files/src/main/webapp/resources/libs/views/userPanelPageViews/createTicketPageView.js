/* ================================================= */
/* Create ticket page view */

var CreateTicketPageView = GenericUserPanelPageView.extend({

	events : {
		'click #submitTicketButton' : 'submit',
		'change #ticketCategoryDropbox' : 'populateSubcategories'
	},
	
	appendData : function() {
		this.$el.append(_.template($('#createTicketTemplate').html()));
		return this;
	},
	
	initialize : function() {
		
		var categories = new GetCategoriesModel({
			userId : sessionStorage.loggedUserId
		});
		
		var priorities =  new GetPrioritiesModel({
			userId : sessionStorage.loggedUserId
		});
		
		categories.save({}, {
			success : function(model, response) {
				if (response.type == "success"){
					_.each(response.data, function(e) {
						$('#ticketCategoryDropbox').append($("<option></option>").attr("value", e.categoryId).text(e.categoryName));
						
					});
				} else {
					if (response.type == "error"){
						popNotification(response.description);
					} else {
						popNotification("Unknown error!");
					}
				}
			},
			error : function(model, response) {
				console.log(response);
			}
		});
		
		priorities.save({}, {
			success : function(model, response) {
				if (response.type == "success"){
					_.each(response.data, function(e) {
						$('#ticketPriorityDropbox').append($("<option></option>").attr("value", e.priorityId).text(e.priorityName));
						
					});
				} else {
					if (response.type == "error"){
						popNotification(response.description);
					} else {
						popNotification("Unknown error!");
					}
				}
			},
			error : function(model, response) {
				console.log(response);
			}
		});
		
	},
	
	populateSubcategories : function() {
		
		var categories = new GetSubcategoriesModel({
			categoryId : $("#ticketCategoryDropbox option:selected").val(),
			categoryName : $("#ticketCategoryDropbox option:selected").text()
		});
		
		categories.save({}, {
			success : function(model, response) {
				if (response.type == "success"){
					var selectedCategory = $('#ticketCategoryDropbox').val();
					$('#ticketSubcategoryDropbox').find('option').remove().end().append('<option selected style="display:none;">Select your subcategory</option>').val('');
					if ($.isEmptyObject(response.data)) {
						$('#ticketSubcategoryDropbox').attr("disabled", true);
						$('#ticketSubcategoryDropbox').css("color", "#808080");
					} else {
						_.each(response.data, function(e) {
							$('#ticketSubcategoryDropbox').append($("<option></option>").attr("value", e.categoryId).text(e.categoryName));
						});
						$('#ticketSubcategoryDropbox').attr("disabled", false);
						$('#ticketSubcategoryDropbox').removeAttr("style")
					}
				} else {
					if (response.type == "error"){
						popNotification(response.description);
					} else {
						popNotification("Unknown error!");
					}
				}
			},
			error : function(model, response) {
				console.log(response);
			}
		});
		
	},
	
	submit : function() {
		
		var ticketCategoryName, ticketCategoryId,ticketPriorityName,ticketPriorityId;
		if ($("#ticketSubcategoryDropbox option:selected").val() != "Select your subcategory"){
			ticketCategoryName = $("#ticketSubcategoryDropbox option:selected").text();
			ticketCategoryId = $("#ticketSubcategoryDropbox option:selected").val();
		} else {
			ticketCategoryName= $("#ticketCategoryDropbox option:selected").text();
			ticketCategoryId = $("#ticketCategoryDropbox option:selected").val();
		};
		ticketPriorityName = $("#ticketPriorityDropbox option:selected").text();
		ticketPriorityId = $("#ticketPriorityDropbox option:selected").val();
		

		
		
		var ticketCategory = new ValidateCategory({
			categoryName: ticketCategoryName,
			categoryId: ticketCategoryId
		}, {validate: true});
		
		var ticketPriority = new ValidatePriority({
			priorityName: ticketPriorityName,
			priorityId: ticketPriorityId
		}, {validate: true});
		
		var tmpUser = new Backbone.Model({
			userId: sessionStorage.loggedUserId,
		})
		
		var ticketComment = new TicketComment({
			user: tmpUser,
			dateTime: new Date().getTime(),
			comment: $("#ticketContent").val()
		}, {validate: true});
		
		var ticket = new CreateTicketModel({
			subject : $("#ticketSubject").val(),
			category : ticketCategory,
			priority : ticketPriority,
			comments : [ticketComment]
		});
		console.log(ticket.toJSON());
		ticket.save({}, {
			success : function(model, response) {
				if (response.type == "success"){
					popNotification("Ticket submitted!");
					createUserTicketPage();
				} else {
					if (response.type == "error"){
						popNotification(response.description);
					} else {
						popNotification("Unknown error!");
					}
				}
			},
			error : function(model, response) {
				console.log(response);
			}
		});
		
	}
	
});

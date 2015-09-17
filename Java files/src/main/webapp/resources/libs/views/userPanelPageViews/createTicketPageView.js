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
		
		categories.save({}, {
			success : function(model, response) {
				console.log(response);
				_.each(response, function(e) {
						$('#ticketCategoryDropbox').append($("<option></option>").attr("value", e.categoryId).text(e.categoryName));
					
				});
				
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
				var selectedCategory = $('#ticketCategoryDropbox').val();
				$('#ticketSubcategoryDropbox').find('option').remove().end().append('<option selected style="display:none;">Select your subcategory</option>').val('');
				if ($.isEmptyObject(response)) {
					$('#ticketSubcategoryDropbox').attr("disabled", true);
					$('#ticketSubcategoryDropbox').css("color", "#808080");
				} else {
					_.each(response, function(e) {
						$('#ticketSubcategoryDropbox').append($("<option></option>").attr("value", e.categoryId).text(e.categoryName));
					});
					$('#ticketSubcategoryDropbox').attr("disabled", false);
					$('#ticketSubcategoryDropbox').removeAttr("style")
				}
			},
			error : function(model, response) {
				console.log(response);
			}
		});
		
	},
	
	submit : function() {
		
		var ticketCategoryName, ticketCategoryId;
		if ($("#ticketSubcategoryDropbox option:selected").val() != "Select your subcategory"){
			ticketCategoryName = $("#ticketSubcategoryDropbox option:selected").text();
			ticketCategoryId = $("#ticketSubcategoryDropbox option:selected").val();
		} else {
			ticketCategoryName= $("#ticketCategoryDropbox option:selected").text();
			ticketCategoryId = $("#ticketCategoryDropbox option:selected").val();
		};
		
		var ticketCategory = new ValidateCategory({
			categoryName: ticketCategoryName,
			categoryId: ticketCategoryId
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
			comments : [ticketComment]
		});
		console.log(ticket.toJSON());
		
		ticket.save({}, {
			success : function(model, response) {
				console.log(response);
				assignedTicketsView = createTicketPageView = null;
				if (!userTicketsView) {
					userTicketsView = new UserTicketsView({
						model : new ViewTicketsModel({})
					});
					$('#userPanelPageContainer').replaceWith(
							userTicketsView.render().el);
					handleErrorStyle();
				}

				$('#ats').hide();
				$('#mts').show();
				$('#sts').hide();
				$('#ups').hide();

				$('#atn').show();
				$('#mtn').hide();
				$('#stn').show();
				$('#upn').show();
				alert("Ticket submitted!");
			},
			error : function(model, response) {
				console.log(response);
			}
		});
		
	}
	
});

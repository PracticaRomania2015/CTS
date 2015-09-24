/* ================================================= */
/* User tickets page view */

var UserTicketsView = GenericUserPanelPageView.extend({

	events : {
		'change #ticketsPerPage' : function() {
			this.viewData(1, $('#ticketSearchBox').val(), $('#ticketSearchDropBox').val());
		},
		'click #firstPageReqBtn' : function() {
			this.viewData(1, $('#ticketSearchBox').val(), $('#ticketSearchDropBox').val());
		},
		'click #ticketSearchButton' : function() {
			this.viewData(1, $('#ticketSearchBox').val(), $(
					'#ticketSearchDropBox').val());
		},
		'click #lastPageReqBtn' : function() {
			this.viewData(this.model.get("totalNumberOfPages"), $('#ticketSearchBox').val(), $('#ticketSearchDropBox').val());
		},
		'click #nextPageReqBtn' : function() {
			this.viewData(this.model.get("requestedPageNumber") + 1, $('#ticketSearchBox').val(), $('#ticketSearchDropBox').val());
		},
		'click #prevPageReqBtn' : function() {
			this.viewData(this.model.get("requestedPageNumber") - 1, $('#ticketSearchBox').val(), $('#ticketSearchDropBox').val());
		},
		'click .openTicketComments' : 'viewTicketComments'
	},

	appendData : function() {
		this.$el.append("<h1 class='userPage'> Tickets submitted by me </h1>");
		this.viewData(1, "", "");
		this.$el.append(_.template($('#userTicketsTemplate').html()));
		return this;
	},

	viewTicketComments : function(clicked) {

		var respondToTicketPageView = new RespondToTicketPageView({
			model : new RespondToTicketModel({
				ticketId : $(clicked.currentTarget).attr('id')
			})
		});
		
		$('#userPanelPageContainer').replaceWith(respondToTicketPageView.render().el);
		setUserTicketsViewToNull();
	},

	viewData : function(pgNr, srcTxt, srcTp) {
		
		var self = this.model;
		
		if (pgNr > this.model.get("totalNumberOfPages")) {
			pgNr = this.model.get("totalNumberOfPages");
		} else if (pgNr < 1) {
			pgNr = 1;
		}

		this.model.unset('tickets');
		this.model.unset('totalNumberOfPages');

		this.model.set("user", {
			"userId" : Number(sessionStorage.loggedUserId)
		});
		this.model.set("typeOfRequest", 0);
		this.model.set("requestedPageNumber", pgNr);
		if (!this.$el.find('#ticketsPerPage').val()) {
			this.model.set("ticketsPerPage", 10);
		} else {
			this.model.set("ticketsPerPage", this.$el.find('#ticketsPerPage')
					.val());
		}
		this.model.set("textToSearch", srcTxt);
		this.model.set("searchType", srcTp);
		var currentView = this;
		
		this.model.save({}, {
			success : function(model, response) {
				if (response.type == "success"){
					self.set("totalNumberOfPages", response.data[0]);
					self.set("tickets", response.data[1]);
					currentView.populateData(pgNr, response.data[0], response.data[1]);
					self.unset("data");
					self.unset("type");
					self.unset("description");
				} else {
					if (response.type == "error"){
						alert(response.description);
					} else {
						alert("Unknown error!");
					}
				}
			},
			error : function(model, response) {
				console.log(response);
			}
		});
		
	},

	addTicket : function(id, subj, categ, status, userName, ansDate, subDate) {
		this.$el.find('tbody').append(
				"<tr class='openTicketComments' id='" + id
						+ "'><td><div class='columnOverflow'>" + id
						+ "</div></td><td><div class='columnOverflow'>" + subj
						+ "</div></td><td><div class='columnOverflow'>" + categ
						+ "</div></td><td><div class='columnOverflow'>" + "priority"
						+ "</div></td><td><div class='columnOverflow'>"
						+ status + " " + userName
						+ "</div></td><td><div class='columnOverflow'>"
						+ ansDate
						+ "</div></td><td><div class='columnOverflow'>"
						+ subDate + "</div></td></tr>");
	},

	populateData : function(currentPage, totalPages, tkArray) {
		var currentView = this;
		this.$el.find('tbody').empty();
		_.each(tkArray,
				function(e) {
					var answerDate;
					var currentSubmitTkDate = new Date(e.comments[0].dateTime);
					var submitDate = currentSubmitTkDate.toLocaleDateString()
							+ " " + addZero(currentSubmitTkDate.getHours())
							+ ":" + addZero(currentSubmitTkDate.getMinutes());

					if (e.comments[0].dateTime == e.comments[1].dateTime) {
						answerDate = "Not yet answered!";
					} else {
						var currentAnswerTkDate = new Date(
								e.comments[1].dateTime);
						answerDate = currentAnswerTkDate.toLocaleDateString()
								+ " " + addZero(currentAnswerTkDate.getHours())
								+ ":"
								+ addZero(currentAnswerTkDate.getMinutes());
					}
					if (e.assignedToUser.firstName == null || e.assignedToUser.lastName == null){
						assignedToUser = " - Unassigned";
					} else {
						assignedToUser = " - Assigned to " + e.assignedToUser.firstName;
					}
					
					currentView.addTicket(e.ticketId, e.subject,
							e.category.categoryName, e.state.stateName,
							e.state.stateName == "Closed" ? "" : assignedToUser, answerDate, submitDate);
				});
		this.$el.find('#ticketPagingNumbering').empty().append(
				currentPage + "/" + totalPages);
	}

})
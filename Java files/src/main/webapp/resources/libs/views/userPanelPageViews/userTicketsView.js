/* ================================================= */
/* User tickets page view */

var UserTicketsView = GenericUserPanelPageView.extend({
	
	
	initialize : function() {
		
		var isAsc ;
		var lastSortType;
	},
	
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
		'click .openTicketComments' : 'viewTicketComments',
		'click #id-col, #subject-col, #category-col, #priority-col, #status-col, #lastComment-col, #submitDate-col' : 'orderBy'  
	},

	appendData : function() {
		this.$el.append("<h1 class='userPage'> Tickets submitted by me </h1>");
		this.viewData(1, "", "");
		this.$el.append(_.template($('#userTicketsTemplate').html()));
		return this;
	},
	
	setSortType : function(clickedId){
		switch (clickedId) {
		case "id-col":
		case "idTh":
		case "idArrUp":
		case "idArrDown":
		case "idArrAsc":	
		case "idArrDesc":	
			return "TicketId"
			break;
		case "subject-col":
		case "subjectTh":
		case "subjectArrUp":
		case "subjectArrDown":
		case "subjectArrAsc":	
		case "subjectArrDesc":
			return "Subject"
			break;
		case "category-col":
		case "categoryTh":
		case "categoryArrUp":
		case "categoryArrDown":
		case "categoryArrAsc":	
		case "categoryArrDesc":
			return "Category"
			break;
		case "priority-col":
		case "priorityTh":
		case "priorityArrUp":
		case "priorityArrDown":
		case "priorityArrAsc":	
		case "priorityArrDesc":
			return "Priority"
			break;
		case "status-col":
		case "statusTh":
		case "statusArrUp":
		case "statusArrDown":
		case "statusArrAsc":	
		case "statusArrDesc":
			return "Status"
			break;
		case "lastComment-col":
		case "lastCommentTh":
		case "lastCommentArrUp":
		case "lastCommentArrDown":
		case "lastCommentArrAsc":	
		case "lastCommentArrDesc":
			return "LastDateTime"
			break;
		case "submitDate-col":
		case "submitDateTh":
		case "submitDateArrUp":
		case "submitDateArrDown":
		case "submitDateArrAsc":	
		case "submitDateArrDesc":
			return "SubmitDate"
			break;
		default:
			break;
		}
	},
	
	getColumName : function(clickedId){
		switch (clickedId) {
		case "id-col":
		case "idTh":
		case "idArrUp":
		case "idArrDown":
		case "idArrAsc":	
		case "idArrDesc":
		case "TicketId":
			return "id"
			break;
		case "subject-col":
		case "subjectTh":
		case "subjectArrUp":
		case "subjectArrDown":
		case "subjectArrAsc":	
		case "subjectArrDesc":
		case "Subject":
			return "subject"
			break;
		case "category-col":
		case "categoryTh":
		case "categoryArrUp":
		case "categoryArrDown":
		case "categoryArrAsc":	
		case "categoryArrDesc":
		case "Category":
			return "category"
			break;
		case "priority-col":
		case "priorityTh":
		case "priorityArrUp":
		case "priorityArrDown":
		case "priorityArrAsc":	
		case "priorityArrDesc":
		case "Priority":
			return "priority"
			break;
		case "status-col":
		case "statusTh":
		case "statusArrUp":
		case "statusArrDown":
		case "statusArrAsc":	
		case "statusArrDesc":
		case "Status":
			return "status"
			break;
		case "lastComment-col":
		case "lastCommentTh":
		case "lastCommentArrUp":
		case "lastCommentArrDown":
		case "lastCommentArrAsc":	
		case "lastCommentArrDesc":
		case "LastDateTime":
			return "lastComment"
			break;
		case "submitDate-col":
		case "submitDateTh":
		case "submitDateArrUp":
		case "submitDateArrDown":
		case "submitDateArrAsc":	
		case "submitDateArrDesc":
		case "SubmitDate":
			return "submitDate"
			break;
		default:
			break;
		}
	},
	
	
orderBy : function(clicked){
		
		var clickedId = clicked.srcElement.id;
		
		if( typeof this.isAsc == 'undefined'){
			this.isAsc = false;
			this.lastSortType="";
			console.log("Been here");
		}
	
		
		if(this.lastSortType==this.setSortType(clickedId)){
			if(this.isAsc==false){
				this.isAsc=true;
				this.model.set("sortType",this.setSortType(clickedId));
				this.model.set("isSearchASC",this.isAsc);
				this.$el.find('#' + this.getColumName(clickedId) + 'ArrAsc').show();
				this.$el.find('#' + this.getColumName(clickedId) + 'ArrDesc').hide();
				this.$el.find('#' + this.getColumName(clickedId) + 'ArrUp').hide();
				this.$el.find('#' + this.getColumName(clickedId) + 'ArrDown').hide();
				
			}
			else{
				this.isAsc=false;
				this.model.set("sortType",this.setSortType(clickedId));
				this.model.set("isSearchASC",this.isAsc);
				this.$el.find('#' + this.getColumName(clickedId) + 'ArrAsc').hide();
				this.$el.find('#' + this.getColumName(clickedId) + 'ArrDesc').show();
				this.$el.find('#' + this.getColumName(clickedId) + 'ArrUp').hide();
				this.$el.find('#' + this.getColumName(clickedId) + 'ArrDown').hide();
			}
		}
		else{
			this.isAsc=true;
			this.model.set("sortType",this.setSortType(clickedId));
			this.model.set("isSearchASC",this.isAsc);
			this.$el.find('#' + this.getColumName(clickedId) + 'ArrAsc').show();
			this.$el.find('#' + this.getColumName(clickedId) + 'ArrUp').hide();
			this.$el.find('#' + this.getColumName(clickedId) + 'ArrDown').hide();
			this.$el.find('#' + this.getColumName(this.lastSortType) + 'ArrAsc').hide();
			this.$el.find('#' + this.getColumName(this.lastSortType) + 'ArrDesc').hide();
			this.$el.find('#' + this.getColumName(this.lastSortType) + 'ArrUp').show();
			this.$el.find('#' + this.getColumName(this.lastSortType) + 'ArrDown').show();
			
		}
		
		this.lastSortType=this.setSortType(clickedId);
		
		this.viewData(1, "", "");
	},

	viewTicketComments : function(clicked) {

		var respondToTicketPageView = new RespondToTicketPageView({
			model : new RespondToTicketModel({
				ticketId : $(clicked.currentTarget).attr('id'),
				provenience: "userTickets"
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

	addTicket : function(id, subj, categ, status, userName, ansDate, subDate, priority) {
		var statusClassColor;
		if(status == "Closed"){
			statusClassColor = "classClosed";
		}else if(status == "Answered"){
			statusClassColor = "classAnswered";
		}else if(status == "Active"){
			statusClassColor = "classActive";
		}
		this.$el.find('tbody').append(
				"<tr class='openTicketComments' id='" + id
						+ "'><td><div class='columnOverflow'>" + id
						+ "</div></td><td><div class='columnOverflow'>" + subj
						+ "</div></td><td><div class='columnOverflow'>" + categ
						+ "</div></td><td><div class='columnOverflow'>" + priority
						+ "</div></td><td><div class='columnOverflow'><div class='" + statusClassColor + "'/>"
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

					var currentAnswerTkDate = new Date(
								e.comments[1].dateTime);
						answerDate = currentAnswerTkDate.toLocaleDateString()
								+ " " + addZero(currentAnswerTkDate.getHours())
								+ ":"
								+ addZero(currentAnswerTkDate.getMinutes());
					
					if (e.assignedToUser.firstName == null || e.assignedToUser.lastName == null){
						assignedToUser = " - Unassigned";
					} else {
						assignedToUser = " - Assigned to " + e.assignedToUser.firstName;
					}
					
					currentView.addTicket(e.ticketId, e.subject,
							e.category.categoryName, e.state.stateName,
							e.state.stateName == "Closed" ? "" : assignedToUser, answerDate, submitDate, e.priority.priorityName);
				});
		this.$el.find('#ticketPagingNumbering').empty().append(
				currentPage + "/" + totalPages);
	}

})
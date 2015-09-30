/* ================================================= */
/* Assigned tickets page view */

var AssignedTicketsView = GenericUserPanelPageView.extend({

	initialize : function() {
		
		var isAsc ;
		var lastSortType;
		var lastHovered;
	},
	
	events : {
		'change #ticketsPerPage' : function() {
			this.viewData(1, $('#ticketSearchBox').val(), $('#ticketSearchDropBox').val());
		},
		'click #firstPageReqBtn' : function() {
			this.viewData(1, $('#ticketSearchBox').val(), $('#ticketSearchDropBox').val());
		},
		'click #ticketSearchButton' : function() {
			this.viewData(1, $('#ticketSearchBox').val(), $('#ticketSearchDropBox').val());
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
		'click th' : 'orderBy'  ,
		'mouseover th' :  'mouseoverTh',
		'mouseout th' :  'mouseoutTh'
	},

	appendData : function() {
		this.$el.append("<h1 class='userPage'> Tickets managed by me </h1>");
		this.viewData(1, "", "");
		this.$el.append(_.template($('#userTicketsTemplate').html()));
		return this;
	},
	
	setSortType : function(clickedHeaderColumn){
		switch (clickedHeaderColumn) {
		case "id-col":
			return "TicketId"
			break;
		case "subject-col":
			return "Subject"
			break;
		case "category-col":
			return "Category"
			break;
		case "priority-col":
			return "Priority"
			break;
		case "status-col":
			return "Status"
			break;
		case "lastComment-col":
			return "LastDateTime"
			break;
		case "submitDate-col":
			return "SubmitDate"
			break;
		default:
			break;
		}
	},
	
	getColumName : function(clickedHeaderColumn){
		switch (clickedHeaderColumn) {
		case "id-col":
			return "id"
			break;
		case "subject-col":
			return "subject"
			break;
		case "category-col":
			return "category"
			break;
		case "priority-col":
			return "priority"
			break;
		case "status-col":
			return "status"
			break;
		case "lastComment-col":
			return "lastComment"
			break;
		case "submitDate-col":
			return "submitDate"
			break;
		default:
			break;
		}
	},
	
	
	orderBy : function(clicked){ // Setting isSearchASC into model aint working
	
		
		var clickedHeaderColumn = clicked.srcElement.id;
		
		if( typeof this.isAsc == 'undefined'){
			this.isAsc = false;
			this.lastSortType="";
			console.log("Been here");
		}
		
		if(typeof this.lastHovered != 'undefined'){
			this.$el.find('#' + this.getColumName(this.lastHovered ) + 'ArrHover').hide();
		}
		
		if(this.lastSortType==clickedHeaderColumn){
			if(this.isAsc==false){
				console.log("1");
				this.isAsc=true;
				this.model.set("sortType",this.setSortType(clickedHeaderColumn));
				//this.model.set("isSearchASC",this.isAsc);
				this.$el.find('#' + this.getColumName(clickedHeaderColumn) + 'ArrAsc').show();
				this.$el.find('#' + this.getColumName(clickedHeaderColumn) + 'ArrDesc').hide();
				
			}
			else{
				console.log("2");
				this.isAsc=false;
				this.model.set("sortType",this.setSortType(clickedHeaderColumn));
				//this.model.set("isSearchASC",this.isAsc);
				this.$el.find('#' + this.getColumName(clickedHeaderColumn) + 'ArrAsc').hide();
				this.$el.find('#' + this.getColumName(clickedHeaderColumn) + 'ArrDesc').show();
			}
		}
		else{
			console.log("3");
			this.isAsc=true;
			this.model.set("sortType",this.setSortType(clickedHeaderColumn));
			//this.model.set("isSearchASC",this.isAsc);
			this.$el.find('#' + this.getColumName(clickedHeaderColumn) + 'ArrAsc').show();
			this.$el.find('#' + this.getColumName(clickedHeaderColumn) + 'ArrDesc').hide();
			this.$el.find('#' + this.getColumName(this.lastSortType) + 'ArrAsc').hide();
			this.$el.find('#' + this.getColumName(this.lastSortType) + 'ArrDesc').hide();
			this.lastSortType=clickedHeaderColumn;
		}
		
		console.log("thisIsAsc : "+this.isAsc);
		console.log("modelIsAsc : "+this.model.get("isSearchASC"));
		console.log("lastSortType : "+this.lastSortType);
		console.log("thisSortType : "+this.getColumName(clickedHeaderColumn));
		console.log("modelSortType : "+this.model.get("sortType"));
		
		this.viewData(1, this.$el.find('#ticketSearchBox').val(), this.$el.find('#ticketSearchDropBox').val());
	},
	
	
	
	mouseoverTh : function(ev) {
		var hoveredHeaderColumn = $(ev.target).attr('id');
		if(typeof this.lastHovered != 'undefined'){
			this.$el.find('#' + this.getColumName(this.lastHovered ) + 'ArrHover').hide();
		}
		if( this.model.get("sortType")!=this.setSortType(hoveredHeaderColumn)){
			this.$el.find('#' + this.getColumName(hoveredHeaderColumn) + 'ArrHover').show();
			this.lastHovered = hoveredHeaderColumn;
		}
	
	},
	
	mouseoutTh : function(){
		this.$el.find('#' + this.getColumName(this.lastHovered ) + 'ArrHover').hide();
	},

	viewTicketComments : function(clicked) {
		var respondToTicketPageView = new RespondToTicketPageView({
			model : new RespondToTicketModel({
				ticketId : $(clicked.currentTarget).attr('id'),
				provenience: "assignedTickets"
			})
		});
	
		$('#userPanelPageContainer').replaceWith(respondToTicketPageView.render().el);
		setAssignedTicketsViewToNull();
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
		this.model.set("typeOfRequest", 1);
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
						+ "</div></td><td><div class='columnOverflow " + statusClassColor + "'>"
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

					var currentAnswerTkDate = new Date(e.comments[1].dateTime);
					var answerDate = currentAnswerTkDate
								.toLocaleDateString()
								+ " "
								+ addZero(currentAnswerTkDate.getHours())
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
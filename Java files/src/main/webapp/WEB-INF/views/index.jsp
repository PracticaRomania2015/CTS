<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>CTS</title>
<spring:url value="/resources" var="res" />
<link rel="stylesheet" href="${res}/css/style.css" />
<link rel="shortcut icon" type="image/x-icon" href="${res}/img/favicon.ico"/> 
<script type="text/javascript" src="${res}/libs/jquery.js"></script>
<script type="text/javascript" src="${res}/libs/underscore.js"></script>
<script type="text/javascript" src="${res}/libs/backbone.js"></script>
<script type="text/javascript" src="${res}/libs/utils.js"></script>
<!-- <script type="text/javascript" src="${res}/libs/models.js"></script> -->

<script type="text/javascript" src="${res}/libs/functionality.js"></script>

<script type="text/javascript" src="${res}/libs/models/frontPageModels/logInModel.js"></script>
<script type="text/javascript" src="${res}/libs/models/frontPageModels/registerModel.js"></script>
<script type="text/javascript" src="${res}/libs/models/frontPageModels/recoveryModel.js"></script>

<script type="text/javascript" src="${res}/libs/models/userPanelPageModels/viewTicketsModel.js"></script>
<script type="text/javascript" src="${res}/libs/models/userPanelPageModels/getCategoriesModel.js"></script>
<script type="text/javascript" src="${res}/libs/models/userPanelPageModels/getSubcategoriesModel.js"></script>
<script type="text/javascript" src="${res}/libs/models/userPanelPageModels/createTicketModel.js"></script>
<script type="text/javascript" src="${res}/libs/models/userPanelPageModels/validateCategory.js"></script>
<script type="text/javascript" src="${res}/libs/models/userPanelPageModels/ticketComment.js"></script>
<script type="text/javascript" src="${res}/libs/models/userPanelPageModels/respondToTicketModel.js"></script>
<script type="text/javascript" src="${res}/libs/models/userPanelPageModels/getTicketContentModel.js"></script>
<script type="text/javascript" src="${res}/libs/models/userPanelPageModels/getAdminForCategory.js"></script>
<script type="text/javascript" src="${res}/libs/models/userPanelPageModels/assignAdminToTicketModel.js"></script>
<script type="text/javascript" src="${res}/libs/models/userPanelPageModels/closeTicketModel.js"></script>

<script type="text/javascript" src="${res}/libs/views/frontPageViews/genericFrontPageChildView.js"></script>
<script type="text/javascript" src="${res}/libs/views/frontPageViews/logInView.js"></script>
<script type="text/javascript" src="${res}/libs/views/frontPageViews/registerView.js"></script>
<script type="text/javascript" src="${res}/libs/views/frontPageViews/recoveryView.js"></script>

<script type="text/javascript" src="${res}/libs/views/userPanelPageViews/genericUserPanelPageView.js"></script>
<script type="text/javascript" src="${res}/libs/views/userPanelPageViews/assignedTicketsView.js"></script>
<script type="text/javascript" src="${res}/libs/views/userPanelPageViews/userTicketsView.js"></script>
<script type="text/javascript" src="${res}/libs/views/userPanelPageViews/createTicketPageView.js"></script>
<script type="text/javascript" src="${res}/libs/views/userPanelPageViews/respondToTicketPageView.js"></script>
<script type="text/javascript" src="${res}/libs/views/userPanelPageViews/assignedTicketsView.js"></script>

<script type="text/javascript" src="${res}/main.js"></script>


<script type="text/template" id="frontPageTemplate">
			<div id='mainContainer'>
			<div id='logInWrapper'>
				<span class='button' id='toggle-login'>Log In</span>
				<span class='button' id='toggle-signup'>Register</span>
				<span class='button' id='toggle-recovery'>Recovery</span>
			</div>
			<div id='frontPageContainer' style='display: none;'></div>
			</div>
	</script>
	<script type="text/template" id="logInTemplate">
			<h1 class="frontPage">Log In</h1>
			<form class='frontPageForms'>
				<input id='logInMail' type='text' placeholder='E-mail' class="masterTooltip"/>
				<input id='logInPass' type='password' placeholder='Password' class="masterTooltip"/>
				<span href="#" class="button" id="logInButton">Log In</span>
			</form>
	</script>
	<script type="text/template" id="registerTemplate">
			<h1 class="frontPage">Register</h1>
			<form class='frontPageForms'>
				<select id="regTitle">
					<option selected value="Mr"> Mr.</option>
					<option value="Mrs">Mrs.</option>
					<option value="Ms">Ms.</option>
				</select>
				<input id='regFirstName' type='text' placeholder='First Name' class="masterTooltip"/>
				<input id='regLastName' type='text' placeholder='Last Name' class="masterTooltip"/>
				<input id='regMail' type='text' placeholder='E-mail' class="masterTooltip"/>
				<input id='regPass' type='password' placeholder='Password' class="masterTooltip"/>
				<input id='regConfPass' type='password' placeholder='Confirm Password' class="masterTooltip"/>
				<span href="#" class="button" id="registerButton">Register</span>
			</form>
	</script>
	<script type="text/template" id="recoveryTemplate">
			<div id='frontPageContainer'>
			<h1 class="frontPage">Password Recovery</h1>
			<form class='frontPageForms'>
				<input id='recoveryMail' type='text' placeholder='E-mail' class="masterTooltip"/>
				<span href="#" class="button" id="recoveryButton">Send Recovery Mail</span>
			</form>
			</div>
	</script>
	<script type="text/template" id="userPanelPageTemplate">
			<div id='mainContainer'>
			<div id='menuWrapper'>
				<span href='#' class='button' id='btn-mngTk'>Manage tickets
					<div id="atn" class="menuRightArrow"></div>
					<div id="ats" class="menuRightArrowSelected" style="display: none;"></div></span>
				<span href='#' class='button' id='btn-userTk'>My tickets
					<div id="mtn" class="menuRightArrow"></div>
					<div id="mts" class="menuRightArrowSelected" style="display: none;"></div></span>
				<span href='#' class='button' id='btn-subTk'>Submit a ticket
					<div id="stn" class="menuRightArrow"></div>
					<div id="sts" class="menuRightArrowSelected" style="display: none;"></div></span>
				<span href='#' class='button' id='btn-prop'>User properties
					<div id="upn" class="menuRightArrow"></div>
					<div id="ups" class="menuRightArrowSelected" style="display: none;"></div></span>
				<span href='#' class='button' id='btn-logOut'>Log out</span>
			</div>
			<div id="contextWrapper">
				<div id='userPanelPageContainer'>
					<h1 class="userPage">Welcome !</h1>
					<h2 class="userPage">CTS Version: 0.01_ALPHA</h1>
				</div>
			</div>
			</div>
	</script>
	<script type="text/template" id="userTicketsTemplate">
			<input class="searchTickets" id='ticketSearchBox' type='text' placeholder='Search'/>
			<select class="searchTickets" id="ticketSearchDropBox">
				<option selected value="Subject">Subject</option>
				<option value="Category">Category</option>
				<option value="Status">Status</option>
			</select>
			<span href="#" class="button" id="ticketSearchButton">Search</span>
			
			<table class="ticketView">
    			<thead>
					<tr>
						<th id="rounded-tl" class="wide-col">Subject</th>
						<th class="slim-col">Category</th>
						<th class="wide-col">Status</th>
						<th class="slim-col">Last Comment</th>
						<th id="rounded-tr" class="slim-col">Submit Date</th>
     	 			</tr>
    			</thead>
    			<tfoot>
      				<tr>
        				<th colspan="5" id="rounded-bot">
							<div class="table-surf" id="firstPageReqBtn">&#60;&#60;</div>
							&emsp;
							<div class="table-surf" id="prevPageReqBtn">&#60;</div>
							&emsp;
							<div id="ticketPagingNumbering" style="display: inline-block;"></div>
							&emsp;
							<div class="table-surf" id="nextPageReqBtn">&#62;</div>
							&emsp;
							<div class="table-surf" id="lastPageReqBtn">&#62;&#62;</div>
							<div id="tkPerPgDiv">
								Tickets/Page
								<select id="ticketsPerPage">
									<option selected disabled value="10">10</option>
									<option value="10">10</option>
									<option value="25">25</option>
									<option value="50">50</option>
								</select>
							</div>
						</th>
      				</tr>
    			</tfoot>
    			<tbody id="ticketViewBody"></tbody>
  			</table>
	</script>
	<script type="text/template" id="createTicketTemplate">	
			<h1 class="userPage">Create a new ticket</h1>
			<input id='ticketSubject' type='text' placeholder='Subject' class='masterTooltip ticketInput'/>
			<select id="ticketCategoryDropbox" class="ticketCategories">
				<option value="" disabled selected style='display:none;'>Select your category</option>
			</select>
			<select disabled id="ticketSubcategoryDropbox" class="ticketCategories" style="color: #808080">
				<option value="" disabled selected style='display:none;'>Select your subcategory</option>
			</select>
			<textarea id='ticketContent' rows='10' maxlength='500' placeholder='Describe your problem.' class='masterTooltip ticketInput'></textarea>
			<span href="#" id="submitTicketButton" class="button">Submit</span>
	</script>
	<script type="text/template" id="respondToTicketTemplate">
			<h1 id="ticketTitle" class="userPage">
			<div id="ticketName"></div>
			</h1>
			<div id="ticketCommentsWrapper" class="ticketInput"></div>		
			<div id="ticketResponseWrapper">
				<div id="responseText">
					<textarea id="ticketResponse" rows='10' maxlength='500' placeholder='Type your response here.' class='masterTooltip ticketInput'></textarea>
					<span href="#" class="button" id="respondToTicketButton">Submit</span>
					<span href="#" class="button" id="closeTheTicketButton">Close Ticket</span>
					<input type="file" class="browseFile" />
					<select id="ticketAdminsDropBox"></select>
					<span href="#" class="button" id="assignUserToTicketButton" >Assign user</span>
				</div>
				<div id="responseButtons">
					
					
				</div>
			</div>
	</script>
</head>
<body>
	<div id="logoWrapper"></div>
	<div id="mainContainer"></div>
</body>
</html>

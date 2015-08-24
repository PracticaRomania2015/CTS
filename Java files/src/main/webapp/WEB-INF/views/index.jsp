<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>CTS</title>
<spring:url value="/resources" var="res" />
<link rel="stylesheet" href="${res}/css/style.css" />
<script type="text/javascript" src="${res}/libs/jquery.js"></script>
<script type="text/javascript" src="${res}/libs/underscore.js"></script>
<script type="text/javascript" src="${res}/libs/backbone.js"></script>
<script type="text/javascript" src="${res}/libs/error.js"></script>
<script type="text/javascript" src="${res}/libs/models.js"></script>
<script type="text/javascript" src="${res}/libs/views.js"></script>
<script type="text/javascript" src="${res}/main.js"></script>
<script type="text/javascript" src="${res}/libs/formError.js"></script>
<script type="text/template" id="frontPageTemplate">
			<div id='logInWrapper'>
				<span class='button' id='toggle-login'>Log In</span>
				<span class='button' id='toggle-signup'>Register</span>
				<span class='button' id='toggle-recover'>Recovery</span>
			</div>
			<div id='logIn' style='display: none;'></div>
			<div id='register' style='display: none;'></div>
			<div id='recover' style='display: none;'></div>
	</script>
	<script type="text/template" id="userPanelPageTemplate">
			<div id='menuWrapper'>
				<span href='#' class='button' id='btn-asigTk'>Assigned tickets
					<div class="menuRightArrow"></div></span>
				<span href='#' class='button' id='btn-userTk'>My tickets
					<div class="menuRightArrow"></div></span>
				<span href='#' class='button' id='btn-subTk'>Submit a ticket
					<div class="menuRightArrow"></div></span>
				<span href='#' class='button' id='btn-prop'>User properties
					<div class="menuRightArrow"></div></span>
			</div>
			<div id="contextWrapper">
				<div id='assignedTickets' style='display: none;'></div>
				<div id='userTickets' style='display: none;'></div>
				<div id='createTicket' style='display: none;'></div>
			</div>
	</script>
	<script type="text/template" id="logInTemplate">
			<h1 class="frontPage">Log In</h1>
			<form>
				<input id='logInMail' type='text' placeholder='E-mail' class="masterTooltip"/>
				<input id='logInPass' type='password' placeholder='Password' class="masterTooltip"/>
				<span href="#" class="button" id="logInButton">Log In</span>
			</form>
	</script>
	<script type="text/template" id="registerTemplate">
			<h1 class="frontPage">Register</h1>
			<form>
				<select id="regTitle">
					<option selected value="Mr"> Mr.</option>
					<option value="Mrs">Mrs.</option>
					<option value="Ms">Ms.</option>
				</select>
				<input id='regFirstName' type='text' placeholder='First Name' value='Test' class="masterTooltip"/>
				<input id='regLastName' type='text' placeholder='Last Name' value='Test' class="masterTooltip"/>
				<input id='regMail' type='text' placeholder='E-mail' value='test@gmail.com' class="masterTooltip"/>
				<input id='regPass' type='password' placeholder='Password' value='testpassword' class="masterTooltip"/>
				<input id='regConfPass' type='password' placeholder='Confirm Password' value='testpassword' class="masterTooltip"/>
				<span href="#" class="button" id="registerButton">Register</span>
			</form>
	</script>
	<script type="text/template" id="recoverTemplate">
			<h1 class="frontPage">Password Recovery</h1>
			<form>
				<input id='recoverMail' type='text' placeholder='E-mail' class="masterTooltip"/>
				<span href="#" class="button" id="recoverButton">Send Recovery Mail</span>
			</form>
	</script>
	<script type="text/template" id="userTicketsTemplate">
			<h1 class="userPage">--- a template variable set on load ---</h1>
			<input class="searchTickets" id='ticketSearchBox' type='text' placeholder='Search'/>
			<select class="searchTickets" id="ticketSearchDropBox">
				<option selected value="Subject">Subject</option>
				<option value="Category">Category</option>
				<option value="Date">Date</option>
			</select>
			<span href="#" class="button" id="ticketSearchButton">Search</span>
			
			<table class="ticketView">
    			<thead>
					<tr>
						<th id="rounded-tl" class="wide-col">Subject</th>
						<th class="wide-col">Category</th>
						<th id="rounded-tr" class="slim-col">Date</th>
     	 			</tr>
    			</thead>
    			<tfoot>
      				<tr>
        				<th colspan="3" id="rounded-bot">
							<div class="table-surf">&#60;&#60;</div>
							&emsp;
							<div class="table-surf">&#60;</div>
							&emsp;
							1/1
							&emsp;
							<div class="table-surf">&#62;</div>
							&emsp;
							<div class="table-surf">&#62;&#62;</div>
						</th>
      				</tr>
    			</tfoot>
    			<tbody>
      				<tr>
        				<td>TestSubj</td>
						<td>TestCateg</td> 
						<td>DD-MM-YYYY</td>
    	  			</tr>
      				<tr>
        				<td>TestSubj</td>
						<td>TestCateg</td> 
						<td>DD-MM-YYYY</td>
    	  			</tr>
      				<tr>
        				<td>This should defenetly be taken</td>
						<td>from the data set received from</td> 
						<td>the database</td>
    	  			</tr>
      				<tr>
        				<td>TestSubj</td>
						<td>TestCateg</td> 
						<td>DD-MM-YYYY</td>
    	  			</tr>
      				<tr>
        				<td>TestSubj</td>
						<td>TestCateg</td> 
						<td>DD-MM-YYYY</td>
    	  			</tr>
	    		</tbody>
  			</table>
	</script>
	<script type="text/template" id="createTicketTemplate">	
			<h1 class="userPage">Create a new ticket</h1>
			<input id='ticketSubject' type='text' placeholder='Subject' class='masterTooltip'/>
			<select class="ticketCategories" id="ticketCategotyDropBox">
				<option value="" disabled selected style='display:none;'>Select your category</option>
				<option value="IT">Information Technology</option>
				<option value="HR">Human Resources</option>
			</select>
			<select class="ticketCategories" id="ticketCategotyDropBox">
				<option value="" disabled selected style='display:none;'>Select your subcategory</option>
				<option value="1">Subcateg 1</option>
				<option value="2">Subcateg 2</option>
			</select>
			<textarea id='ticketContent' rows='10' maxlength='500' placeholder='Describe your problem.' class='masterTooltip'></textarea>
			<span href="#" class="button" id="submitTicketButton">Submit</span>
	</script>
</head>
<body>
	<div id="logoWrapper"></div>
	
	<div id="frontPage"></div>
	<div id="userPanelPage"></div>
</body>
</html>

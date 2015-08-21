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
				<span href='#' class='button' id='btn-asigTk'>Assigned tickets</span>
				<span href='#' class='button' id='btn-myTk'>My tickets</span>
				<span href='#' class='button' id='btn-subTk'>Submit a ticket</span>
				<span href='#' class='button' id='btn-prop'>User properties</span>
			</div>
			<div id='contextWrapper'></div>
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
				<input id='recoveryMail' type='text' placeholder='E-mail' class="masterTooltip"/>
				<span href="#" class="button" id="recoveryButton">Send Recovery Mail</span>
			</form>
	</script>
<script type="text/template" id="loggedInUserTicketView">
			<input class="searchTickets" id='ticketSearchBox' type='text' placeholder='Search'/>
			<select class="searchTickets" id="ticketSearchDropBox">
				<option selected value="Subject">Subject</option>
				<option value="Category">Category</option>
				<option value="Date">Date</option>
			</select>
			<span href="#" class="button" id="ticketSearchButton">Search</span>
			
			<table id="ticketView">
				<tr class="head">
				   	<th class="first">Subject</th>
				   	<th>Category</th> 
			    	<th class="last">Date</th>
			  	</tr>
			  	<tr class="tickets">
			    	<td class="first">TestSubj</td>
				    <td>TestCateg</td> 
			    	<td class="last">DD-MM-YYYY</td>
			  	</tr>
			  	<tr class="head">
			    	<th class="first">Subject</th>
			    	<th>Category</th> 
			    	<th class="last">Date</th>
			  	</tr>
			</table>

			<span href="#" class="button" id="firstPageButton">First</span>
			<span href="#" class="button" id="previousPageButton">Previous</span>
			<span href="#" class="button" id="nextPageButton">Next</span>
			<span href="#" class="button" id="lastPageButton">Last</span>
	</script>
</head>
<body>
	<div id="logoWrapper"></div>
	
	<div id="frontPage"></div>
	<div id="userPanelPage"></div>
</body>
</html>

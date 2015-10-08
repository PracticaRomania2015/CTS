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
<script type="text/javascript" src="${res}/libs/models/userPanelPageModels/userPropertiesModel.js"></script>
<script type="text/javascript" src="${res}/libs/models/userPanelPageModels/getPrioritiesModel.js"></script>
<script type="text/javascript" src="${res}/libs/models/userPanelPageModels/validatePriority.js"></script>
<script type="text/javascript" src="${res}/libs/models/userPanelPageModels/assignPriorityToTicketModel.js"></script>
 

<script type="text/javascript" src="${res}/libs/models/userPanelPageModels/assignCategoryToTicketModel.js"></script>
<script type="text/javascript" src="${res}/libs/models/userPanelPageModels/reopenTicketModel.js"></script>
<script type="text/javascript" src="${res}/libs/models/sysAdminPanelPageModels/addCategoryModel.js"></script>
<script type="text/javascript" src="${res}/libs/models/sysAdminPanelPageModels/addSubcategoryModel.js"></script>
<script type="text/javascript" src="${res}/libs/models/sysAdminPanelPageModels/editCategoryModel.js"></script>
<script type="text/javascript" src="${res}/libs/models/sysAdminPanelPageModels/removeCategoryModel.js"></script>
<script type="text/javascript" src="${res}/libs/models/sysAdminPanelPageModels/getUsersModel.js"></script>
<script type="text/javascript" src="${res}/libs/models/sysAdminPanelPageModels/manageUserRoleModel.js"></script>
<script type="text/javascript" src="${res}/libs/models/sysAdminPanelPageModels/updateUserStatusModel.js"></script>
<script type="text/javascript" src="${res}/libs/models/userPropertiesPageModels/userPropertiesModel.js"></script>

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
<script type="text/javascript" src="${res}/libs/views/userPanelPageViews/userPropertiesPageView.js"></script>
<script type="text/javascript" src="${res}/libs/views/userPropertiesPageViews/userPropertiesPageView.js"></script>

<script type="text/javascript" src="${res}/libs/views/sysAdminPanelPageViews/genericSystemAdminPanelPageView.js"></script>
<script type="text/javascript" src="${res}/libs/views/sysAdminPanelPageViews/manageCategoriesView.js"></script>
<script type="text/javascript" src="${res}/libs/views/sysAdminPanelPageViews/manageUsersView.js"></script>
<script type="text/javascript" src="${res}/libs/views/sysAdminPanelPageViews/manageUserRoleView.js"></script>
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
				<div id='userInfo'>
					<div id="upn" class="menuRightArrow userPropertiesMenuButton"></div>
					<div id="ups" class="menuRightArrowSelected userPropertiesMenuButton" style="display: none;"></div></span>
				</div>
				<span href='#' class='button' id='btn-mngTk'>Manage tickets
					<div id="atn" class="menuRightArrow"></div>
					<div id="ats" class="menuRightArrowSelected" style="display: none;"></div></span>
				<span href='#' class='button' id='btn-userTk'>My tickets
					<div id="mtn" class="menuRightArrow"></div>
					<div id="mts" class="menuRightArrowSelected" style="display: none;"></div></span>
				<span href='#' class='button' id='btn-subTk'>Submit a ticket
					<div id="stn" class="menuRightArrow"></div>
					<div id="sts" class="menuRightArrowSelected" style="display: none;"></div></span>
				<span href='#' class='button' id='manageCategoriesButton'>Manage Categories		
					<div id="mcn" class="menuRightArrow"></div>
					<div id="mcs" class="menuRightArrowSelected" style="display: none;"></div></span>
				<span href='#' class='button' id='manageUsersButton'>Manage Users
					<div id="mun" class="menuRightArrow"></div>
					<div id="mus" class="menuRightArrowSelected" style="display: none;"></div></span>
				<span href='#' class='button' id='btn-logOut'>Log out</span>
			</div>
			<div id="contextWrapper">
				<div id='userPanelPageContainer'>
					<h1 class="userPage">Welcome !</h1>
					<h2 class="userPage">CTS Version: 1.00_BETA</h1>
				</div>
			</div>
			</div>
	</script>
	<script type="text/template" id="manageCategoriesTemplate">
		<div id="manageCategoriesContainer">
			<form class='sysAdminPageForms'>
				<div id="manageCategoriesDiv">
				<div id="addCategoryContainer">
					<h3 >Add Category</h3>
					<input id='categoryName' type='text' placeholder='Category name' class="masterTooltip categoryManagment fieldCateg"/>
				<span href="#" class="button" id="addCategoryButton">Add</span>
				</div>


				<div class="verticalSeparatorWrapper">
				<div class="contentFormVerticalSeparator">
					<div id="gradient-verticalLine"></div>
				</div> 
				</div>





				<div id = "editCategoryContainer">
					<h3>Edit Category</h3>
					<div id="categoryListDivEditCategory">
					<select id="categoryListDropboxEditCategory" class="categoryManagment dropDownCateg">
						<option selected disabled value="">Select the category...</option>
					</select>
					</div>

					<input id='categoryNewName' type='text' placeholder='New category name' class="masterTooltip categoryManagment fieldCateg"/>
					<span href="#" class="button" id="editCategoryButton">Change</span>
				</div>

				<div class="verticalSeparatorWrapper">
				<div class="contentFormVerticalSeparator">
					<div id="gradient-verticalLine"></div>
				</div> 
				</div>

				<div id="removeCategoryContainer">
					<h3 class="userPage">Remove Category</h3>
					<div id="categoryListDivRemove">
					<select id="categoryListDropboxRemove" class="categoryManagment dropDownCateg">
						<option selected disabled value="">Select the category...</option>
					</select>
					<span href="#" class="button" id="removeCategoryButton">Remove</span>
				</div>
				</div>

				<div class="horizontalSeparatorWrapper">
				<div class="contentFormHorizontalSeparator">
					<div id="gradient-horizontalLine"></div>
				</div> 
				</div>

				<div id="manageSubcategoriesDiv">
				<div id="addSubcategoryContainer">
					<h3 >Add Subcategory</h3>
					<div id="categoryListDivSubcategory">
					<select id="categoryListDropboxSubcategory" class="categoryManagment dropDownCateg">
						<option selected disabled value="">Select the category...</option>
					</select>
					</div>

					<input id='subcategoryName' type='text' placeholder='Subcategory name' class="masterTooltip categoryManagment fieldCateg"/>
					<span href="#" class="button" id="addSubcategoryButton">Add</span>
				</div>

				<div class="verticalSeparatorWrapper">
				<div class="contentFormVerticalSeparator">
					<div id="gradient-verticalLine"></div>
				</div> 
				</div>

				<div id = "editSubcategoryContainer">
				<h3>Edit Subcategory</h3>
					<div id="categoryListDivEditSubcategory">
					<select id="categoryListDropboxEditSubcategory" class="categoryManagment dropDownCateg">
						<option selected disabled value="">Select the category...</option>
					</select>
					</div>
					<div id="subcategoryListDivEditSubcategory">
						<select id="subcategoryListDropboxEditSubcategory" disabled=true class="categoryManagment dropDownCateg" >
							<option selected disabled>Select the subcategory...</option>
						</select>
					</div>

					<input id='subcategoryNewName' type='text' placeholder='New subcategory name' class="masterTooltip categoryManagment fieldCateg"/>
					<span href="#" class="button" id="editSubcategoryButton">Change</span>
				</div>
		
				<div class="verticalSeparatorWrapper">
				<div class="contentFormVerticalSeparator">
					<div id="gradient-verticalLine"></div>
				</div> 
				</div>

				<div id="removeSubcategoryContainer">
					<h3 class="userPage">Remove Subcategory</h3>
					<div id="categoryListDivRemoveSubcategory">
					<select id="categoryListDropboxRemoveSubcategory" class="categoryManagment dropDownCateg">
						<option selected disabled value="">Select the category...</option>
					</select>
					</div>
					<div id="subcategoryListDivRemoveSubcategory">
						<select id="subcategoryListDropboxRemoveSubcategory" disabled=true class="categoryManagment dropDownCateg" >
							<option selected disabled>Select the subcategory...</option>
						</select>
					</div>
					<span href="#" class="button" id="removeSubcategoryButton">Remove</span>
				</div>
				</div>
			</form>
		</div>
	</script>
	<script type="text/template" id="manageUsersTemplate">
		<input class="searchUsers" id='usersSearchBox' type='text' placeholder='Search'/>
		<select class="searchUsers" id="usersSearchDropBox">
			<option selected value="UserId">User ID</option>
			<option value="FirstName">First Name</option>
			<option value="LastName">Last Name</option>
			<option value="Email">E-mail</option>
			<option value="RoleId">Role</option>
		</select>
		<span href="#" class="button" id="usersSearchButton">Search</span>

		<table class="usersView">
			<thead>
				<tr>
					<th id="" class="slim-col rounded-tl">User ID</th>
					<th class="wide-col">First Name</th>
					<th class="wide-col">Last Name</th>
					<th class="wide-col">E-mail</th>
					<th id="" class="slim-col rounded-tr">Role</th>
				<tr>
			</thead>
			<tfoot>
      				<tr>
        				<th colspan="5" id="rounded-bot">
							<div class="table-surf" id="firstPageReqBtn">&#60;&#60;</div>
							&emsp;
							<div class="table-surf" id="prevPageReqBtn">&#60;</div>
							&emsp;
							<div id="usersPagingNumbering" style="display: inline-block;"></div>
							&emsp;
							<div class="table-surf" id="nextPageReqBtn">&#62;</div>
							&emsp;
							<div class="table-surf" id="lastPageReqBtn">&#62;&#62;</div>
							<div id="usersPerPgDiv">
								Users/Page
								<select id="usersPerPage">
									<option selected disabled value="10">10</option>
									<option value="10">10</option>
									<option value="25">25</option>
									<option value="50">50</option>
								</select>
							</div>
						</th>
      				</tr>
    			</tfoot>
    			<tbody id="usersViewBody"></tbody>
			</table>
	</script>
	<script type="text/template" id="manageUserRoleTemplate">
<div id="manageUserContainer">

		<table class = "userRoles">
			<thead>
				<tr>
					<th class="wide-col rounded-tl">Category Name</th>
					<th id="" class="wide-col rounded-tr">Check</th>				
				</tr>
			</thead>

			<tbody id="userRolesBody"></tbody>
			<tfoot>
      			<tr>
        			<th colspan="2" id="rounded-bot"><div id = "sysAdminDiv"></div></th>
      			</tr>
    		</tfoot>			
		</table>

		<span href="#" class="button" id="updateUserRoles">Submit</span>
		
	</div>
	</script>
	<script type="text/template" id="userTicketsTemplate">
			<input class="searchTickets" id='ticketSearchBox' type='text' placeholder='Search'/>
			<select class="searchTickets" id="ticketSearchDropBox">
				<option selected value="Subject">Subject</option>
				<option value="Category">Category</option>
				<option value="Status">Status</option>
				<option value="TicketId">TicketId</option>
			</select>
			<span href="#" class="button" id="ticketSearchButton">Search</span>
			
			<table class="ticketView">
    			<thead>
					<tr>
						<th id="id-col" class="id-col rounded-tl">
							<div id="idTh">Id</div>
							<div id="idArrUp" class="arrowUp" ></div>
							<div id="idArrDown" class="arrowDown" ></div>
							<div id="idArrAsc" class="arrowAsc"  style='display: none;'></div>
							<div id="idArrDesc" class="arrowDesc"  style='display: none;'></div>
						</th>
						<th id="subject-col" class="wide-col">
							<div id="subjectTh">Subject</div>
							<div id="subjectArrUp" class="arrowUp" ></div>
							<div id="subjectArrDown" class="arrowDown" ></div>
							<div id="subjectArrAsc" class="arrowAsc"  style='display: none;'></div>
							<div id="subjectArrDesc" class="arrowDesc"  style='display: none;'></div>
						</th>
						<th id="category-col" class="slim-col">
							<div id="categoryTh">Category</div>
							<div id="categoryArrUp" class="arrowUp" ></div>
							<div id="categoryArrDown" class="arrowDown" ></div>
							<div id="categoryArrAsc" class="arrowAsc"  style='display: none;'></div>
							<div id="categoryArrDesc" class="arrowDesc"  style='display: none;'></div>
						</th>
						<th id="priority-col" class="slim-col">
							<div id="priorityTh">Priority</div>
							<div id="priorityArrUp" class="arrowUp" ></div>
							<div id="priorityArrDown" class="arrowDown" ></div>
							<div id="priorityArrAsc" class="arrowAsc"  style='display: none;'></div>
							<div id="priorityArrDesc" class="arrowDesc"  style='display: none;'></div>
						</th>
						<th id="status-col" class="wide-col">
							<div id="statusTh">Status</div>
							<div id="statusArrUp" class="arrowUp" ></div>
							<div id="statusArrDown" class="arrowDown" ></div>
							<div id="statusArrAsc" class="arrowAsc"  style='display: none;'></div>
							<div id="statusArrDesc" class="arrowDesc"  style='display: none;'></div>
						</th>
						<th id="lastComment-col" class="slim-col"> 
							<div id="lastCommentTh">Last Comment</div>
							<div id="lastCommentArrUp" class="arrowUp twoRowsArrUp" ></div>
							<div id="lastCommentArrDown" class="arrowDown twoRowsArrDown" ></div>
							<div id="lastCommentArrAsc" class="arrowAsc twoRowsArrAsc"  style='display: none;'></div>
							<div id="lastCommentArrDesc" class="arrowDesc twoRowsArrDesc"  style='display: none;'></div>
						</th>
						<th id="submitDate-col" class="slim-col rounded-tr">
							<div id="submitDateTh">Submit On</div>
							<div id="submitDateArrUp" class="arrowUp " ></div>
							<div id="submitDateArrDown" class="arrowDown " ></div>
							<div id="submitDateArrAsc" class="arrowAsc "  style='display: none;'></div>
							<div id="submitDateArrDesc" class="arrowDesc "  style='display: none;'></div>
						</th>
     	 			</tr>
    			</thead>
    			<tfoot>
      				<tr>
        				<th colspan="7" id="rounded-bot">
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
			<select id="ticketPriorityDropbox" class="ticketPriorities" style="color: #808080">
				<option value="" disabled selected style='display:none;'>Set ticket priority</option>
			</select>
			<textarea id='ticketContent' rows='10' maxlength='2000' placeholder='Describe your problem.' class='masterTooltip ticketInput'></textarea>
			<span href="#" id="submitTicketButton" class="button">Submit</span>
	</script>
	<script type="text/template" id="respondToTicketTemplate">
			<h1 id="ticketTitle" class="userPage">
			<div id="ticketName"></div>
			</h1>
			<div id="ticketCommentsWrapper" class="ticketInput"></div>		
			<div id="ticketResponseWrapper">
				<div id="responseText">
					<textarea id="ticketResponse" rows='10' maxlength='2000' placeholder='Type your response here.' class='masterTooltip ticketInput'></textarea>
					<span href="#" class="button" id="respondToTicketButton">Submit</span>
					<span href="#" class="button" id="closeTheTicketButton">Close Ticket</span>
					<select id="ticketAdminsDropBox" style='display: none;'></select>
					<span href="#" class="button" id="assignUserToTicketButton"style='display: none;' >Assign user</span>
					<select id="ticketPrioritiesDropBox" class="reassignTicketPriorities" style='display: none;'></select>
					<span href="#" class="button" id="reassignPriorityToTicketButton"style='display: none;' >Set priority</span>
					<select id="ticketCategoryDropbox" class="reassignTicketCategory">
						<option value="" disabled selected style='display:none;'>Select your category</option>
					</select>
					<select disabled id="ticketSubcategoryDropbox" class="reassignTicketSubcategory" style="color: #808080">
						<option value="" disabled selected style='display:none;'>Select your subcategory</option>
					</select>
					<span href="#" class="button" id="reassignCategoryToTicketButton" >Set category</span>
				</div>
				<div id="responseButtons">
				</div>
			</div>
<span href="#" class="button" id="reopenTicketButton" style='display:none;' >Reopen Ticket</span>
	</script>
	<script type="text/template" id="userPropertiesTemplate">	
			<h3>Update security</h3>
			<form class='propertiesPanelPage'>
			<div id='changePassswordContainer'>
				<input id='oldPassword' type='password' placeholder='Old Password' class="masterTooltip savePassword fieldPass"/>
				<input id='newPassword' type='password' placeholder='New Password' class="masterTooltip savePassword fieldPass"/>
				<input id='confirmNewPassword' type='password' placeholder='Confirm New Password' class="masterTooltip savePassword fieldPass"/>
				<span href="#" class="button" id="changePasswordButton">Save</span>
			</div>

			<!--this horizontal line doesn't space as expected
				TODO other user properties related stuff
			<div class="horizontalSeparatorWrapper">
			<div class="contentFormHorizontalSeparator">
				<div id="gradient-horizontalLine"></div>
			</div> 
			</div>
			
			<div id='changeTODOContainer'>
			</div>-->
			</form>
	</script>
</head>
<body>
	<div id="header">
		<div id="logoWrapper" ></div>
	</div>	
	<div id="mainContainer"></div>
</body>
</html>

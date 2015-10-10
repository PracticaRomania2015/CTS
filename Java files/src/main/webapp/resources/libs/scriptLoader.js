// Call this after the DOM is ready for backbone to be able to access it
$(document).on('ready', function() {
	
	// Script loading function
	function require(script) {
		// Debug Mode enables advanced error messages
		// which may contain private information
		var debugMode = 1, scriptPath = '';
		$.ajax({
			url: script,
			dataType: 'script',
			async: false,
			timeout: 3000
		}).fail(function (jqXHR, textStatus, errorThrown) {
			if (debugMode == 1){
				scriptPath = ' Script path: ' + script;
			}
			if (textStatus == 'parsererror') {
				errorThrown += '!' + scriptPath;
				throw errorThrown;
			} else if (textStatus === 'timeout') { 
				throw new Error('Requested script timed out!' + scriptPath); 
			} else if (textStatus == 'notmodified') {
				throw new Error('Requested script was not modified, but could not be retrieved from the cache!' + scriptPath);
			} else if (textStatus == 'error') {
				throw new Error('Requested script could not be loaded!' + scriptPath);
			} else {
				if (debugMode == 1){
					console.log('jqXHR: ' + jqXHR);
					console.log('textStatus: ' + textStatus);
					console.log('errorThrown: ' + errorThrown);
				}
				throw new Error('Unknown error occured!')
			}
		});
	}
	
	// Scripts to be loaded (ordered)
	var resourcesLocation = '/cts/resources/';

	require(resourcesLocation + 'libs/underscore.js');
	require(resourcesLocation + 'libs/backbone.js');
	require(resourcesLocation + 'libs/utils.js');
	require(resourcesLocation + 'libs/functionality.js');
	
	require(resourcesLocation + 'libs/models/mainMenuModels/loginModel.js');
	require(resourcesLocation + 'libs/models/mainMenuModels/registerModel.js');
	require(resourcesLocation + 'libs/models/mainMenuModels/recoveryModel.js');
	
	require(resourcesLocation + 'libs/models/sysAdminModels/addCategoryModel.js');
	require(resourcesLocation + 'libs/models/sysAdminModels/addSubcategoryModel.js');
	require(resourcesLocation + 'libs/models/sysAdminModels/editCategoryModel.js');
	require(resourcesLocation + 'libs/models/sysAdminModels/removeCategoryModel.js');
	require(resourcesLocation + 'libs/models/sysAdminModels/getUsersModel.js');
	require(resourcesLocation + 'libs/models/sysAdminModels/manageUserRoleModel.js');
	require(resourcesLocation + 'libs/models/sysAdminModels/removeCategoryModel.js');
	require(resourcesLocation + 'libs/models/sysAdminModels/updateUserStatusModel.js');
	
	require(resourcesLocation + 'libs/models/userMenuModels/assignAdminToTicketModel.js');
	require(resourcesLocation + 'libs/models/userMenuModels/assignCategoryToTicketModel.js');
	require(resourcesLocation + 'libs/models/userMenuModels/assignPriorityToTicketModel.js');
	require(resourcesLocation + 'libs/models/userMenuModels/category.js');
	require(resourcesLocation + 'libs/models/userMenuModels/closeTicketModel.js');
	require(resourcesLocation + 'libs/models/userMenuModels/createTicketModel.js');
	require(resourcesLocation + 'libs/models/userMenuModels/getAdminForCategory.js');
	require(resourcesLocation + 'libs/models/userMenuModels/getCategoriesModel.js');
	require(resourcesLocation + 'libs/models/userMenuModels/getPrioritiesModel.js');
	require(resourcesLocation + 'libs/models/userMenuModels/getSubcategoriesModel.js');
	require(resourcesLocation + 'libs/models/userMenuModels/getTicketContentModel.js');
	require(resourcesLocation + 'libs/models/userMenuModels/getTicketsModel.js');
	require(resourcesLocation + 'libs/models/userMenuModels/priority.js');
	require(resourcesLocation + 'libs/models/userMenuModels/reopenTicketModel.js');
	require(resourcesLocation + 'libs/models/userMenuModels/respondToTicketModel.js');
	require(resourcesLocation + 'libs/models/userMenuModels/ticketComment.js');
	require(resourcesLocation + 'libs/models/userMenuModels/userPropertiesModel.js');
	
	require(resourcesLocation + 'libs/views/mainMenuViews/loginView.js');
	require(resourcesLocation + 'libs/views/mainMenuViews/registerView.js');
	require(resourcesLocation + 'libs/views/mainMenuViews/recoveryView.js');
	
	require(resourcesLocation + 'libs/views/menuViews/mainMenuView.js');
	require(resourcesLocation + 'libs/views/menuViews/userMenuView.js');

	require(resourcesLocation + 'libs/views/sysAdminViews/manageCategoriesView.js');
	require(resourcesLocation + 'libs/views/sysAdminViews/manageUsersView.js');
	require(resourcesLocation + 'libs/views/sysAdminViews/manageUserRoleView.js');
	
	require(resourcesLocation + 'libs/views/userMenuViews/assignedTicketsView.js');
	require(resourcesLocation + 'libs/views/userMenuViews/createTicketView.js');
	require(resourcesLocation + 'libs/views/userMenuViews/respondToTicketView.js');
	require(resourcesLocation + 'libs/views/userMenuViews/userPropertiesView.js');
	require(resourcesLocation + 'libs/views/userMenuViews/userTicketsView.js');
	require(resourcesLocation + 'libs/views/userMenuViews/welcomeView.js');
	
	require(resourcesLocation + 'main.js');
});
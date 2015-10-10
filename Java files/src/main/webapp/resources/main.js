if (typeof(Storage) !== 'undefined') {
	if(sessionStorage.loggedUserId) {
		initUserMenu();
		$('#mainContainer').append(userMenu.render().el,welcome.render().el);
	} else {
		initMainMenu();
		$('#mainContainer').append(mainMenu.render().el);
	}
} else {
	alert('To load this site you need a browser not a dumpster !');
}
$('#logoWrapper').append(createElem('img',{id:'logo',src:'/cts/resources/img/logo.png'}));
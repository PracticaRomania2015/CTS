$(document).ready((function() {

		if(typeof(Storage) !== "undefined") {
			if (sessionStorage.loggedUserId) {
				$('#mainContainer').replaceWith(_.template($('#userPanelPageTemplate').html()));
				userPanelPageFunctionality();
			} 
			else {
				$('#mainContainer').replaceWith(_.template($('#frontPageTemplate').html()));
				frontPageFunctionality();
			}
		}
		else{
			alert("To load this site you need a browser not a dumpster !")
		}
		$('#logoWrapper').append("<img id='logo' src='/cts/resources/img/logo.png' align='middle'>");

}));

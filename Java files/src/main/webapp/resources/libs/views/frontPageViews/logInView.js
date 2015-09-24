/* ================================================= */
/* Log in page view */

var LogInView = GenericFrontPageChildView.extend({
	

	events : {
		'click #logInButton' : 'submit',
		'keydown input' : 'submitOnEnter'
	},

	appendData : function() {
		this.$el.append(_.template($('#logInTemplate').html()));
	},

	submit : function() {

		var user = new LogInModel({
			email : $("#logInMail").val(),
			password : $("#logInPass").val()
		});
		
		user.save({}, {
			success : function(model, response) {
				if (response.type == "success") {
					sessionStorage.loggedUserId = response.data.userId;
					sessionStorage.loggedUserRights = response.data.role.roleName;
					sessionStorage.loggedUserName = response.data.firstName + " " +response.data.lastName;
					userPanelPageFunctionality();
					$('#mainContainer').replaceWith(_.template($('#userPanelPageTemplate').html()));
				} else {
					alert(response.description);

				};
			},
			error : function(model, response) {
				alert(response);
			}
		});

	},
	
	submitOnEnter: function(e){
	      if ( e.keyCode  === 13 ) { 
	    	  var user = new LogInModel({
	  			email : $("#logInMail").val(),
	  			password : $("#logInPass").val()
	  		});
	  		
	  		user.save({}, {
	  			success : function(model, response) {
	  				if (response.userId) {
	  					sessionStorage.loggedUserId = response.data.userId;
						sessionStorage.loggedUserRights = response.data.role.roleName;
						sessionStorage.loggedUserName = response.data.firstName + " " +response.data.lastName;
						$('#mainContainer').replaceWith(_.template($('#userPanelPageTemplate').html()));
						userPanelPageFunctionality();
					}else {
						alert(response.description);
					};
	  			},
	  			error : function(model, response) {
	  				alert(response);
	  			}
	  		});
	      }
	    }


});
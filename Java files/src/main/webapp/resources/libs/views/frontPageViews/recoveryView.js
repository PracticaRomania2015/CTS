/* ================================================= */
/* recovery page view */

var RecoveryView = GenericFrontPageChildView.extend({
	

	events : {
		'click #recoveryButton' : 'submit',
		'keydown input' : 'submit'
	},

	appendData : function() {
		this.$el.append(_.template($('#recoveryTemplate').html()));
	},

	submit : function(e) {
		if ( e.keyCode  === 13 || e.type =="click") { 
			var user = new RecoveryModel({
				email : $("#recoveryMail").val()
			});
			
			user.save({}, {
				success : function(model, response) {
					if (response.type == "success"){
						alert(response.description);
					} else {
						if (response.type == "error"){
							alert(response.description);
						} else {
							alert("Unknown error!");
						}
					}
					alert(response.description);
				},
				error : function(model, response) {
					console.log(response);
				}
			});
		}
	}
});
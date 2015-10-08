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
						popNotification(response.description);
					} else {
						if (response.type == "error"){
							popNotification(response.description);
						} else {
							popNotification("Unknown error!");
						}
					}
				},
				error : function(model, response) {
					console.log(response);
				}
			});
		}
	}
});
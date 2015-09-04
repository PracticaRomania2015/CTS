/* ================================================= */
/* recovery page view */

var RecoveryView = GenericFrontPageChildView.extend({

	events : {
		'click #recoveryButton' : 'submit'
	},

	appendTextBox : function() {
		this.$el.append(_.template($('#recoveryTemplate').html()));
	},

	submit : function() {
		
		var user = new RecoveryModel({
			email : $("#recoveryMail").val()
		});
		
		user.save({}, {
			success : function(model, response) {
				alert(response.description);
			},
			error : function(model, response) {
				console.log(response);
			}
		});
		
	}
});
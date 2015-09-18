/* System Admin Panel Page*/
/* ================================================= */
/* Add Categories Page view */

var AddCategoryView = GenericSystemAdminPanelPageView.extend({
	
	events : {
		'click #addCategoryButton' : 'submit'
	},
	
	appendData : function(){
		this.$el.append(_.template($('#addCategoryTemplate').html()));
		return this;
	},
	
	submit: function(){
		var category = new AddCategoryModel({
			categoryName : $('#categoryName').val()
		});
		
		category.save({},{
			success: function(model,response){
				console.log(response);
				alert(response.description);
			},
			error: function(model,response){
				alert(response);
			}
		});
	}
	
})
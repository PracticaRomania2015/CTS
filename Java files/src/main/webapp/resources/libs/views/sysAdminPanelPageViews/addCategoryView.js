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
		category = new AddCategoryModel({
			categoryName : $('#categoryName').val()
		});
		
		category.save({},{
			success: function(model,response){
				//TODO showing message if added successfully
				console.log(response);
			},
			error: function(model,response){
				alert(response);
			}
		});
	}
	
})
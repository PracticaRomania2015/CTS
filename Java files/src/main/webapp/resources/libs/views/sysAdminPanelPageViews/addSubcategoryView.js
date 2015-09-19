/* System Admin Panel Page*/
/* ================================================= */
/* Add Subategories Page view */

var AddSubcategoryView = GenericSystemAdminPanelPageView.extend({
	
	events : {
		'click #addSubcategoryButton' : 'submit'
	},
	
	initialize: function(){
		var categories = new GetCategoriesModel({});
		
		categories.save({},{
			success: function(model, response){
				_.each(response,function(e){
					$('#categoryListDropbox').append($("<option></option>").attr("value", e.categoryId).text(e.categoryName))
				});
			},
			error: function(model, response){
				console.log(response);
			}
		})
	},
	
	appendData : function(){
		this.$el.append(_.template($('#addSubcategoryTemplate').html()));
		return this;
	},
	
	submit: function(){
		var categoryName = $("#categoryListDropbox option:selected").text();
		var categoryId = $("#categoryListDropbox option:selected").val();
		
		var subcategory = new AddSubcategoryModel({
			categoryName : $("#subcategoryName").val(),
			parentCategoryId : $("#categoryListDropbox option:selected").val()
		});
		
		subcategory.save({},{
			success: function(model, response){
				//TODO showing message if added successfully
				console.log(response);
			},
			error: function(model, response){
				alert(response);
			}
		});
		
	}
	
})
	
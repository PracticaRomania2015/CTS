/* System Admin Panel Page*/
/* ================================================= */
/* Remove Categories Page view */

var RemoveCategoryView = GenericSystemAdminPanelPageView.extend({
	
	events: {
		'click #removeCategoryButton' : 'submit',
		'change #categoryListDropbox' : 'populateSubcategories'
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
	
	populateSubcategories : function() {
		
		var categories = new GetSubcategoriesModel({
			categoryId : $("#categoryListDropbox option:selected").val(),
			categoryName : $("#categoryListDropbox option:selected").text()
		});
		
		categories.save({}, {
			success : function(model, response) {
				var selectedCategory = $('#categoryListDropbox').val();
				$('#subcategoryListDropbox').find('option').remove().end().append('<option selected style="display:none;">Select the subcategory...</option>').val('');
				if ($.isEmptyObject(response)) {
					$('#subcategoryListDropbox').attr("disabled", true);
				} else {
					_.each(response, function(e) {
						$('#subcategoryListDropbox').append($("<option></option>").attr("value", e.categoryId).text(e.categoryName));
					});
					$('#subcategoryListDropbox').attr("disabled", false);
				}
			},
			error : function(model, response) {
				console.log(response);
			}
		});
		
	},
	
	appendData: function(){
		this.$el.append(_.template($('#removeCategoryTemplate').html()));
		return this;
	},
	
	submit: function(){
		var categoryNameVar, categoryIdVar;
		if ($("#subcategoryListDropbox option:selected").val() != "Select the subcategory..."){
			categoryNameVar = $("#subcategoryListDropbox option:selected").text();
			categoryIdVar = $("#subcategoryListDropbox option:selected").val();
		} else {
			categoryNameVar = $("#categoryListDropbox option:selected").text();
			categoryIdVar = $("#categoryListDropbox option:selected").val();
		};
		
		var category = new RemoveCategoryModel({
			categoryName : categoryNameVar,
			categoryIdVar : categoryIdVar
		});
		
		console.log(category);
		category.save({},{
			success: function(model, response){
				console.log(response);
				alert(response.description);
			},
			error: function(model, response){
				alert(response);
			}
		});
		
	}
	
})
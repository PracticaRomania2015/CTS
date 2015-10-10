function getValue(key) {
	var out = '';
	$.ajaxSetup({async:false});
	$.get('/cts/resources/config.cfg', function(data) {
		lines = data.split('\n');
		$.each(lines, function() {
			if (key.trim() == this.split(',')[0].trim()){
				out =  this.substr(this.split(',')[0].trim().length + 1, this.length);
				return false;
			}
		});
	});
	return out;
};

//Function to create custom element
//tagName    : string | example : div, button, ..
//tagAttrs   : map    | example : {id:'elementId', class:'elementClass'}
//tagContent : string | the content in between tags <div>Content</div>
function createElem(tagName, tagAttrs, tagContent) {
	
	if ($.type(tagName) != 'string') {
		return false;
	};
	var elem = $(document.createElement(tagName));
	if (tagAttrs) {
		tagAttrs = $.map(tagAttrs, function(value, index) {
			elem.attr(index, value);
		});
	};
	if ($.type(tagContent) == 'object') {
		elem.append(tagContent);
	} else if ($.type(tagContent) == 'array') {
		$(tagContent).each(function(index, element) {
			elem.append(element);
		});
	} else if (tagContent) {
		elem.text(tagContent);
	};
	return elem;
};

function loadValidationErrors(errors) {
	$.each(errors, function(key, value) {
		$(key).removeAttr('title');
		$(key).removeClass('error');
		if (value != ''){
			$(key).attr('title', value);
			$(key).addClass('error');
		}
	});
};

function handleErrorStyle() {
	var errorMessage;
	$('.error').off();
	$('.error').on({
		mouseenter: function() {
			if ($(this).attr('title') !== undefined && $(this).hasClass('validationTooltip')) {
				errorTooltip.show();
			}
			errorMessage = $(this).attr('title');
			errorTooltip.html(errorMessage);
			$(this).removeAttr('title');
			$(this).mousemove(function(e) {
				var mousex = e.pageX + 20;
				var mousey = e.pageY + 10;
				errorTooltip.css({ top : mousey, left : mousex });
			});
		},
		mouseout: function() {
			errorTooltip.hide();
			errorTooltip.contents().remove();
			$(this).attr('title', errorMessage);
		}
	});
};

function hasValues(map){
	var values = false;
	$.each(map, function(key, value) {
		if (value != ''){
			values = true;
			return false;
		}
	});
	return values;
};

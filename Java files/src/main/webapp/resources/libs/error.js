function getError(type) {
	var out = "";
	$.ajaxSetup({async:false});
	$.get('/cts/resources/config.cfg', function(data) {
		lines = data.split('\n');
		$.each(lines, function() {
			if (type.trim() == this.split(',')[0].trim()){
				out =  this.split(',')[1];
			}
		});
	});
	return out;
}

function getValue(key) {
	var out = "";
	$.ajaxSetup({async:false});
	$.get('/cts/resources/config.cfg', function(data) {
		lines = data.split('\n');
		$.each(lines, function() {
			if (key.trim() == this.split(',')[0].trim()){
				out =  this.split(',')[1];
			}
		});
	});
	return out;
}

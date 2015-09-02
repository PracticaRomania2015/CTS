function getValue(key) {
	var out = "";
	$.ajaxSetup({async:false});
	$.get('/cts/resources/config.cfg', function(data) {
		lines = data.split('\n');
		$.each(lines, function() {
			if (key.trim() == this.split(',')[0].trim()){
				out =  this.substr(this.split(',')[0].trim().length + 1, this.length);
			}
		});
	});
	return out;
};

function handleErrorStyle() {
	$('.masterTooltip').hover(
		function() {
			var title = $(this).attr('title');
			if (title !== undefined) {
				$(this).data('tipText', title).removeAttr('title');
				$('<p class="tooltip"></p>').html(title).appendTo('body').fadeIn('slow');
			} else {
				$(this).removeData('tipText');
			};
		}, function() {
			$(this).attr('title', $(this).data('tipText'));
			$('.tooltip').remove();
		}
	).mousemove(function(e) {
		var mousex = e.pageX + 20;
		var mousey = e.pageY + 10;
		$('.tooltip').css({
			top : mousey,
			left : mousex
		});
	});
};
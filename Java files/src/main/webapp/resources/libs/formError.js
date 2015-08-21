$(document).ready(
		function() {
			$('.masterTooltip').hover(
					function() {
						// Hover over code
						var title = $(this).attr('title');
						if (title !== undefined) {
							$(this).data('tipText', title).removeAttr('title');
							$('<p class="tooltip"></p>').html(title).appendTo('body').fadeIn('slow');
						} else {
							$(this).removeData('tipText');
						}
					}, function() {
						// Hover out code
						$(this).attr('title', $(this).data('tipText'));
						$('.tooltip').remove();
					}).mousemove(function(e) {
				var mousex = e.pageX + 20;
				var mousey = e.pageY + 10;
				$('.tooltip').css({
					top : mousey,
					left : mousex
				})
			});
		});

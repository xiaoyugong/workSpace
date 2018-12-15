+(function($){
	var inputs = $('input');
	for (var i = inputs.length - 1; i >= 0; i--) {
		$(inputs[i]).on('focus', function(){
			focusAnimation(inputs);
			$(this).addClass('focus');
		})
	};
})(window.jQuery)

function focusAnimation(inputs){
	for (var i = inputs.length - 1; i >= 0; i--) {
		if($('inputs[i]').hasClass('focus')){
			$('inputs[i]').removeClass('focus');
		}
	};
}
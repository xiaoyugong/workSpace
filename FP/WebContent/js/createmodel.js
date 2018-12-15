+(function($){
	$('form').on('submit', function(){
		var $that = $(this);
		var result = fnvalidate($that);
		if(!result){
			// show the message
			fnAlarm('您还有空没有填写哦','alarm');
			return false;
		}
	})
})(window.jQuery)

function fnvalidate ($that) {
	var flag = true;
	var inputs = $that.find('.required');
	for (var i = inputs.length - 1; i >= 0; i--) {
		if(!inputs[i].value.length){
			// if the input is null 
			// return false
			flag = false;
			return flag;
		}
	};
	return flag;
}

function fnAlarm(text,elementid){
	$('#'+elementid).text(text).stop().fadeIn(200);
	setTimeout(function(){
		$('#'+elementid).fadeOut(200);
	},2000);
}
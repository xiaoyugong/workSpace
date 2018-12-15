+(function($){
	$('form').on('submit', function(){
		var inputs = $(this).find('input');
		if(!simpleValidate(inputs)){
			fnAlarm('您还有空没有填写哦', 'alarm');
			return false;
		}
	})
	
})(window.jQuery)

function simpleValidate(inputs) {
	var flag = true;
	for (var i = inputs.length - 1; i >= 0; i--) {
		if(inputs[i].value.length === 0){
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
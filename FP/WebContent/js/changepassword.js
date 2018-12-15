+(function($){
	$('form').on('submit', function(){
		var $that = $(this);
		var result = fnvalidate($that);
		switch(result){
			case 1:
				// show the massage
				fnAlarm('您还有空没有填写哦！！！','alarm');
				return false;
				break;
			case  2:
				// show the error message
				fnAlarm('新密码两次输入不一致，请您再检查看看', 'alarm');
				return false;
				break;
			case 0:
				return true;
				break;
			default:
				alert("请联系工程师");
				break;
		}
	})
})(window.jQuery)


function fnvalidate ($that) {
	var flag = 0;
	var inputs = $that.find('.required');
	for (var i = inputs.length - 1; i >= 0; i--) {
		if(!inputs[i].value.length){
			// if the input is null 
			// return flag = 1;
			flag = 1;
			return flag;
		}
	};

	var password = $that.find('#password').val();
	var confirmPassword = $that.find('#confirm').val();
	if (password !== confirmPassword) {
		// if the confirmPassword not equel the password 
		// return flag = 2
		flag = 0;
		return flag;
	};

	return flag;
}

function fnAlarm(text,elementid){
	$('#'+elementid).text(text).stop().fadeIn(200);
	setTimeout(function(){
		$('#'+elementid).fadeOut(200);
	},2000);
}
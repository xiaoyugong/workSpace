+(function($){

	// set the position of login/regster box 
	setBoxPosition($('#center'));

	// the form submit event
	$('form').on('submit',function(event){
		var $form = $(this);

		if(validate($form) == false){
			fnAlarm("您还有空没有输入哦", 'alarm');
			return false;
		}
	})

})(window.jQuery)

function validate($form){
	var inputs = $form.find('input');
	var flag = true;
	for (var i = inputs.length - 1; i >= 0; i--) {
		if (inputs[i].value.length == 0) {
			flag = false;
			return flag;
		};
	};

	return flag;
}

function fnAlarm(text,elementid){
	$('#'+elementid).text(text).stop().fadeIn(200);
	setTimeout(function(){
		$('#'+elementid).fadeOut(200);
	},2000);
}

function setBoxPosition($box){
	var windowHeight = $(window).height();
	var boxHeight = $box.height();
	var y =  parseInt((windowHeight - boxHeight)/2*0.7);
	$box.css('marginTop', y);
}
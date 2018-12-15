+(function($){
	
	var $fileInput = $('input[type="file"]')
	$('#file').on('click', function(){
		$fileInput.trigger('click');
	})

	$fileInput.on('change',function(){
		var path = $(this).val();
		$('#file').val(path);
	})

	// a simple validation for input
	$('form').on('submit', function(){
		$that = $(this);
		var path = $that.find('input[type="file"]').val();
		if(!path.length){
			// if the value is null 
			// return false]
			//fnAlarm('您还没有选择文件哦','alarm');
			return false;
		}
		return true;
	})
})(window.jQuery);

function fnAlarm(text,elementid){
	$('#'+elementid).text(text).stop().fadeIn(200);
	setTimeout(function(){
		$('#'+elementid).fadeOut(200);
	},2000);
}
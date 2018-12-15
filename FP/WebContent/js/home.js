+(function($){
	var $iframe = $("iframe");
	$("#sidenav").on('click',function(event){
		event.preventDefault();
		// get the node
		var url; 
		var o = {};
		var node = event.target;
		var $node = $(node);

		if(node.nodeName.toLocaleLowerCase() == "a"){
			temp = $node.parent().parent().prev()
			o.url = $node.attr("href");
			o.text1 = temp.text()
			o.text2 = $node.text();
			o.triggle = temp.find(".triggle");
			fnsideNavEvent(o)
		}else if(node.nodeName.toLocaleLowerCase() == "span"){
			if($node.hasClass("listname")){
				o.text1 = $node.text();
				o.text2 = "";
				o.triggle = $node.find(".triggle");
				o.url = "";	
				fnsideNavEvent(o)	
			}else{
				o.text1 = $node.parent().text();
				o.text2 = "";
				o.triggle = $node.parent().find(".triggle");
				o.url = "";
				fnsideNavEvent(o)
			}
		}
	})

	$("form").on("submit",function(){
		var that  = $(this);
		var inputs = that.find(".required");
		return fnvalidateInputs(inputs);
	})
})(window.jQuery);


// @o is and object
function fnsideNavEvent(o){
	// show the crumbs
	$("#firstcrumbs").text(o.text1);
	$("#seccrumbs").text(o.text2);
	// show the triggle
	$(".triggle").hide();
	o.triggle.show();
	// change the src of iframe
	$("iframe").attr("src", o.url);
}

function fnvalidateInputs(inputs){
	var flag = true;
	for (var i = inputs.length - 1; i >= 0; i--) {
		if(!inputs[i].value.length){
			fnAlarm('还有选项没有填写哦',"alarm");
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
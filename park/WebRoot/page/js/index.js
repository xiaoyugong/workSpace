window.onload=function(){
	var selectid=document.getElementById("searchClass");

if(selectid){
      if (document.getElementsByTagName) {
          var s = document.getElementsByTagName("select");
   
          if (s.length > 0) {
              window.select_current = new Array();
   
             for (var i=0, select; select = s[i]; i++) {
                  select.onfocus = function(){ window.select_current[this.id] = this.selectedIndex; }
                  select.onchange = function(){ restore(this); }
                  emulate(select);
              }
          }
      }
  }
  
  function restore(e) {
      if (e.options[e.selectedIndex].disabled) {
          e.selectedIndex = window.select_current[e.id];
      }
  }
   
  function emulate(e) {
      for (var i=0, option; option = e.options[i]; i++) {
          if (option.disabled) {
              option.style.color = "graytext";
          }
          else {
              option.style.color = "menutext";
          }
      }
}
	var leftbox=document.getElementById("left");
	var leftdl=leftbox.getElementsByTagName("dt");
	for(var i=0; i<leftdl.length; i++){
		leftdl[i].index=i;
		leftdl[i].onclick=function(){
			if(leftdl[this.index].parentNode.className=="over"){
				leftdl[this.index].parentNode.className=" ";
			}
			else{
				leftdl[this.index].parentNode.className="over";
			}
		}
	}
	var gs=$(".left2 dd").length;
	$(".left2 dd a").click(function(){
		//for(var i=0; i<gs.length; i++){ }
	           $(".left2 dd").attr("class"," ")
		$(this).parent().addClass("lhover")
	})
	function pd(){
		var wd=$(".zsy-nr").width()
		if(wd<900){
			$(".zsy-nr").addClass("tjmg")
		}
		if(wd>900){
			$(".zsy-nr").removeClass("tjmg")
		}
	}
	fHs=setInterval(pd,10)
	if(navigator.userAgent.indexOf("MSIE 6.0") > 0)
	{
	   clearInterval(fHs)
	}
	var bttext=$(".z-r-b-l a").eq(0).text();
	$(".content-left dt").each(function(){
	
		if($(this).text()==bttext){
			//$(this).find("a").attr({"class":"hover"})
			$(this).parent().attr({"class":""})
		}
	})
	var bttext2=$(".z-r-b-l a").eq(1).text();
	$(".content-left dd").each(function(){
		$(this).find("a").attr({"class":""})
		var str = $(this).text();
			str = str.replace(/\s+/g,""); 
		if(str==bttext2){
			$(this).find("a").attr({"class":"hover"})
		}
	})

}
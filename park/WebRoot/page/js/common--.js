$(document).ready(function(){
	$(".xlcds dl dd").click(function(){
		var bool=1;
		if($(this).attr("class")=="weidu"){
			$("#tab input:gt(0)").each(function(){
				if($(this).attr("checked")==true){
					$(this).parent().parent().addClass("wd");
				}
			})
		}
		var x=0;
		var ings=$("#tab input").length;
		$("#tab input").each(function(){
			if($(this).attr("checked")==false){
				x++;
				if(ings==x){
					boxsa()
				}
			}
			else{
				xx=999;
			}
		})
		function boxsa(){
			if(ings==x){
				alert("至少要勾选一项！")
			}
		}
		if($(this).attr("class")=="yidu"){
			$("#tab input:gt(0)").each(function(){
				if($(this).attr("checked")==true){
					$(this).parent().parent().removeClass("wd");
				}
			})
		}
		$("#tab input").each(function(){
			$(this).attr({"checked":false});
			$(this).parent().parent().css({background:"none"});
		})
	})
	$("#bt").focus(function(){
	 	if($("#bt").val()=="请填写标题"){
			$("#bt").val("");
	 	}
	 	if($("#bt").val()=="标题名称不能为空 (必填)"){
			$("#bt").val("");
			$("#bt").css({background:"#fff"})
	 	}
	 })
	$("#bt").blur(function(){
		 var v = $("#bt").val();
		 var ss=v.replace(/\s/g,"")
		 var bb=ss.replace(/请填写标题/g,"")
	  	if(bb==""){
			$("#bt").val("标题名称不能为空 (必填)");
			$("#bt").css({background:"#F8C8C8"})
	 	}
	})
	$("#tijiao").click(function(){
		var v = $("#bt").val();
		 var ss=v.replace(/\s/g,"")
		 var bb=ss.replace(/请填写标题/g,"")
		if(bb==""){
		    alert("标题名称不能为空");
		    return false;
		}
		if(v=="标题名称不能为空 (必填)"){
		    alert("标题名称不能为空");
		     return false;
		}
	})
	$(".xxyi,.xlcd dl dd").click(function(evet){
		var vat=evet||event;
		vat.stopPropagation();
			if($(".xlcd dl").css("display")=="none"){
			       $(".xlcd dl").css({display:"block"});
			       $(".xxyi samp").attr({"class":"bj2"});
			}
			else{
			       $(".xlcd dl").css({display:"none"})
			       $(".xxyi samp").attr({"class":" "})
			}
	})
	$(document).click(function(){
		$(".xlcd dl").css({display:"none"});
		$(".xxyi samp").attr({"class":" "})

	})
	//审核
	$(".xlcd dl dd").click(function(){
		var dqwb=$(this).text();
		var dy=$("#bb11").text();
		var tcl=$(this).attr("class");
		var bcl=$("#bb11").attr("class");
		$("#bb11").text(dqwb);
		$("#bb11").attr("class",tcl)
		$(this).attr("class",bcl);
	})
	$(".xxyi2,.xlcd2 dl dd,.xlcd2 dl dt").click(function(evet){
		var vat=evet||event;
		vat.stopPropagation();
		if($(this).attr("class")=="lanmuk"){
			 return false;
		}
			if($(".xlcd2 dl").css("display")=="none"){
			       $(".xlcd2 dl").css({display:"block"});
			       $(".xxyi2 samp").attr({"class":"bj2"});
			}
			else{
			       $(".xlcd2 dl").css({display:"none"})
			       $(".xxyi2 samp").attr({"class":" "})
			}
	})
	if($(".xlcd2 dl dd").length<10){
		$(".xlcd2 dl").css({height:"auto"})
	}
	$(document).click(function(){
		$(".xlcd2 dl").css({display:"none"})
		$(".xxyi2 samp").attr({"class":" "});
	})
	//是否锁定
	$(".xlcd2 dl dd").click(function(){
		if($(this).attr("class")=="lanmuk"){
			 return false;
		}
		var dqwb=$(this).text();
		var tcl=$(this).attr("class");
		var bcl=$("#aa11").attr("class");
		$("#aa11").text(dqwb);
		$("#aa11").attr("class",tcl)
		$(this).attr("class",bcl);
	})
	if(document.getElementById("tab")){
		var tab=document.getElementById("tab");
		var tabinp=tab.getElementsByTagName("input");
		var tabinpy=tabinp[0];
		
		for(var s=1; s<tabinp.length; s++){
			tabinp[s].onclick=function(){
				if(this.checked==true){
					this.parentNode.parentNode.style.background="#f6fdea";
				}
				else{
					this.parentNode.parentNode.style.background="#fff";
				}
			}
		}
	}
	$(".xxyis,.xlcds dl dd").click(function(evet){
		var vat=evet||event;
		vat.stopPropagation();
		if($(this).attr("class")=="lanmuk"){
			 return false;
		}
			if($(".xlcds dl").css("display")=="none"){
			       $(".xlcds dl").css({display:"block"});
			       $(".xxyis samp").attr({"class":"bj2"});
			}
			else{
			       $(".xlcds dl").css({display:"none"})
			       $(".xxyis samp").attr({"class":" "})
			}
	})
	if($(".xlcds dl dd").length<10){
		$(".xlcds dl").css({height:"auto"})
	}
	$(document).click(function(){
		$(".xlcds dl").css({display:"none"})
		$(".xxyis samp").attr({"class":" "});
	})
	$(".yidu,.weidu").click(function(){
		var dqwb=$(this).text();
		var dy=$("#bb11").text();
		var tcl=$(this).attr("class");
		var bcl=$("#bb11").attr("class");
		$("#bb11").text(dqwb);
		$("#bb11").attr("class",tcl)
	});
	$(".tab2 tr").hover(function(){
		if($(this).attr("class")=="tab2yt"){
			return false;
		}
		if($(this).find("td input").attr("checked")==false){
		        $(this).css({background:"#e8f0f7"});
		}
		else{
		        $(this).css({background:"#f6fdea"});
			
		}
	},function(){
		if($(this).attr("class")=="tab2yt"){
			return false;
		}
		if($(this).find("td input").attr("checked")==false){
		        $(this).css({background:"#fff"});
		}
		else{
		        $(this).css({background:"#fff"});
		}
	}
	)
/*	if(document.getElementById("yc")){
		var oYc=document.getElementById("yc")
		var oXiala=document.getElementById("xiala")
		var oXialadd=oXiala.getElementsByTagName("dd");
		for(var i=0; i<oXialadd.length; i++){
			oXialadd[i].index=i;
			oXialadd[i].onclick=function(){
				oYc.setAttribute("value",this.index)
			}
		}
	}
	if(document.getElementById("yc2")){
		var oYc2=document.getElementById("yc2")
		var oXiala2=document.getElementById("xiala2")
		var oXialadd2=oXiala2.getElementsByTagName("dd");
		for(var i=0; i<oXialadd2.length; i++){
			oXialadd2[i].index=i;
			oXialadd2[i].onclick=function(){
				oYc2.setAttribute("value",this.index)
			}
		}
	}*/
})
function changeColor1(){
	document.getElementById("corpus").style.backgroundColor="#9D9D9D";
	document.getElementById("corpus").style.borderLeft="solid";
	document.getElementById("corpus").style.borderLeftWidth="4px"
	document.getElementById("corpus").style.borderLeftColor="#0099CC";
}
function replyColor1(){
	document.getElementById("corpus").style.borderLeftWidth="0px"
	document.getElementById("corpus").style.backgroundColor="#555555"
}
function changeColor2(){
	document.getElementById("alreadyArticle").style.backgroundColor="#9D9D9D";
	document.getElementById("alreadyArticle").style.borderLeft="solid";
	document.getElementById("alreadyArticle").style.borderLeftWidth="4px"
	document.getElementById("alreadyArticle").style.borderLeftColor="#0099CC";
}
function replyColor2(){
	document.getElementById("alreadyArticle").style.backgroundColor="#555555"
	document.getElementById("alreadyArticle").style.borderLeftWidth="0px"
}
function changeColor3(){
	document.getElementById("article").style.borderLeft="solid";
	document.getElementById("article").style.borderLeftWidth="4px"
	document.getElementById("article").style.borderLeftColor="#0099CC";
	document.getElementById("article").style.backgroundColor="#9D9D9D";
}
function replyColor3(){
	document.getElementById("article").style.borderLeftWidth="0px"
	document.getElementById("article").style.backgroundColor="#555555"
}
function delInput(that){
   $($(that).parent()).parent().remove();    
}
var change=0;
var imgPath;
var elem=0;
$(window).resize(function()
{
	$("#leftMenu").css("height",$(window).height());$("#midMenu").css("height",$(window).height());$("#writeArea").css("height",$(window).height());
})
$(document).ready(function(){
		$("#writeArticle").css("height",$(window).height());
	if($(window).width()>=768){
		$("#leftMenu").css("height",$(window).height());$("#midMenu").css("height",$(window).height());$("#writeArea").css("height",$(window).height());
	}
	
	else {
		$("#myModal_box").css("width",$(window).width());
		//$("#cut_in_img").attr("href","#")
	}
	$("#creatNewArticleWord").click(function(){
		if(change<6){
		$("#creatNewArticle").after("<div class='creatNewArticle1'><p class='creatNewArticleWord1'><a href=''>无标题文章</a><a onclick='delInput(this)'href='#' style='float:right; margin-right:3%;'>x</a></p></div>");
		change++;
		}
	})
	$("#submitButton").click(function(){
		imgPath=$('#imageUrlArea').val();
		$('#articleArea').append("<button onclick='window.open(address)'></button>");
		})
})
//下面是图片插入的模态框
function on_click_register(){
	document.getElementById("myModal").style.visibility="visible";
	document.getElementById("myModal").style.opacity="1";
	document.getElementById("myModal_box").style.visibility="visible";
}
function closeModal(){
	document.getElementById("myModal").style.visibility="hidden";
	document.getElementById("myModal_box").style.visibility="hidden";
}
window.onload = function(){
	document.getElementById("writeArticle").focus();   
}  
//cut in 

$(document).ready(function(){
	

	
	
	$("#commentCancel").bind("click",function(){
		$("#commentHandleBox").hide(200)
		
	
	})
	
	$("#writeAreaBox").bind("click",function(){
		$("#commentHandleBox").show(200)
	})
	
	
	
})


function commentSend(e){
	var parent=$(e).parent().parent().parent().parent().parent().parent()
	var target=parent.find(".write-area")
	var tex=$(target).val()
	
	var targets=parent.find(".comment-list")
	var tg=$(targets).append("<div class='comment-border'></div>")
	var par=$(".comment-border:last")
	$(par).load("commentContent.html",function(){
		var t=par.find(".comment-content-p")
		$(t).html(tex)
	})
	
}

function childCommentSend(e){
	var paren=$(e).parent().parent().parent().parent().parent().parent();
	var tar=paren.find("#toReplyButton")
	$(tar).attr("onclick","replyChildadd(this);")
	var par=paren.find(".Childcomment")
	var target=paren.find(".write-area");
	var tex=$(target).val()
	$(par).load("ChildCommentContent.html",function(){	
	var tp=par.find(".childCommentContents");
	     tp.html(tex)
	
	})

}



function childCommentSends(a){
	var paren=$(e).parent().parent().parent().parent().parent().parent();
	var tar=paren.find("#toReplyButton")
	$(tar).attr("onclick","replyChildaddc(this);")
	var par=paren.find(".Childcomment")
	var target=paren.find(".write-area");
	var tex=$(target).val()
	$(par).load("ChildCommentContent.html",function(){	
	var tp=par.find(".childCommentContents");
	     tp.html(tex)
	
	})
	
}


function childCommentSendToTrail(e){
	var paren=$(e).parent().parent().parent().parent().parent().parent().parent();
	var texttar=$(e).parent().parent().parent().parent()
	var tar=paren.find("#toReplyButton")
	$(tar).attr("onclick","replyChildadd(this);")
	var par=paren.find(".comment-child-list")
	var rmoveat =paren.find(".ChildCommentTrail");
	$(rmoveat).remove()
	var tag=$(par).append("<div class='child-comment'></div>");
	var tg=tag.find(".child-comment:last")
	var pn=paren.parent().parent()
	var replyChildadd =pn.find("#toReplyButton");
	replyChildadd.attr("onclick","replyChildadd(this);")
	var target=texttar.find(".write-area");
	var tex=$(target).val()
	$(tg).load("ChildCommentContents.html",function(){	
	var tp=tg.find(".childCommentContents");
	     tp.html(tex)
	
	})
}


function addComment(e,txt){
	var paren=$(e).parent().parent().parent().parent();
	var par=paren
		$(paren).load("ChildCommentContent.html")
		var target=par.parent();
		target.find(".childCommentContents");
		alert(target.find(".childCommentContents").html())
}


function fabulousAdd(e){
     var pa=$(e).find(".fabulousChildCount");
     var count=pa.text();
     count++;
     pa.text(count)
	 $(e).removeAttr("onclick");
}




function replyChildAdd(e){
	
	var paren=$(e).parent().parent();
	var rem=$(paren).parent()
	rem.find(".ChildCommentTrail").remove()
	var target=paren.find(".Childcomment");
	paren.find(".ChildCommentTrail").remove()
	$(target).append("<div class='ChildCommentTrail' ></div>")
	var targets=target.find(".ChildCommentTrail")
	$(targets).hide(100)
	$(targets).load("ChildCommentBox.html")
	$(targets).show(200)
}

function replyChildadd(e){
	var paren=$(e).parent().parent();
	var rem=$(paren).parent()
	rem.find(".ChildCommentTrail").remove()
	var target=paren.find(".Childcomment");
	$(target).append("<div class='ChildCommentTrail' ></div>");
		var targets=paren.find(".ChildCommentTrail");
	$(targets).show(1)
	$(targets).hide(1)
		$(targets).load("ChildCommentBox.html",function(){
			var tas=paren.find(".comment-send");
	        var ts=$(tas).attr("onclick","childCommentSendToTrail(this);")
			$(targets).show(200)
			
		})
	
}	

	
function replyChildAddc(e){

	var paren=$(e).parent().parent().parent().parent().parent();
	var pt=$(e).parent().parent()
   var  commentbox=paren.find(".ChildCommentTrail").remove()
	$(pt).append("<div class='ChildCommentTrail' ></div>");
	var targets=paren.find(".ChildCommentTrail");
	targets.css("display","none")
		$(targets).load("ChildCommentBoxs.html",function(){
		
			$(targets).show(200)
		})

}

function cancelChildComment(a){
	var paren=$(a).parent().parent().parent().parent().parent();
	var target=paren.find(".Childcomment")
    var tg=paren.find(".ChildCommentTrail")
    $(tg).hide(200,function(){
    	tg.remove()
    })
	var targets=paren.find(".ChildCommentTrail");
	var target2=paren.find("#toReplyButton")
	target2.attr("onclick","replyChildadd(this);")
}

function cancelChildComments(a){
	var parent=$(a).parent().parent().parent().parent().parent()
	var paren=$(a).parent().parent().parent().parent().parent().parent().parent();
	var target=paren.find(".ChildCommentTrail")
	var target2=parent.find("#toReplyButton")
	$(target).hide(200,function(){
			$(target).remove()
	})
	
	target2.attr("onclick","replyChildAddc(this);")
	
}


function commentChildShow(a){
		console.log("start")
	var parent=$(a).parent().parent()
	var tag=parent.find(".comment-child-list")
	tag.show(200)
	var tags=parent.find("#replyButton")
		//$(tag).show(200)
		console.log("show"+tag.length)
		tags.attr("onclick","commentChildHide(this)")
}

function commentChildHide(a){
		console.log("start")
	var parent=$(a).parent().parent()
	var tag=parent.find(".comment-child-list")
	var tags=parent.find("#replyButton")
	console.log("hide")	
	tag.hide(200)
		//$(tag).hide(200)
     tags.attr("onclick","commentChildShow(this)")
	
}

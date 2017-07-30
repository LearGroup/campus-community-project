var statusTag = 0

$(document).ready(function() {

	$.post('/Uncom/login_checkStatus.action', {}, function(response) {
		console.log(response)
		if(response != "000") {
			statusTag = 1;

		} else {

			return null
		}

	})

	$("#commentCancel").bind("click", function() {
		$("#commentHandleBox").hide(200)

	})

	$("#writeAreaBox").bind("click", function() {
		$("#commentHandleBox").show(200)
	})

})

function commentSend(e) {
	var parent = $(e).parent().parent().parent().parent().parent().parent()
	var target = parent.find(".write-area")
	var tex = $.trim($(target).val())
	/*var targets = parent.find(".comment-list")
	var tg = $(targets).append("<div class='comment-border'></div>")
	var par = $(".comment-border:last")
	$(par).load("commentContent.html", function() {
		var t = par.find(".comment-content-p")
		$(t).html(tex)
	})
*/
	if(tex == "") {
		showMsg('评论不能为空~', 'center');
	} else {

		if(statusTag == 0) {
			/*判断用户状态 若离线则需要登陆*/
			$.post('/Uncom/login_checkStatus.action', {}, function(response) {
				/*console.log(response)*/
				if(response != "000") {
					statusTag = 1;
					/*console.log("ok")*/
					$.post("/Uncom/saveArticle/pushComment.action", {
						comment: tex,
						commentLevel: 1
					}, function(date) {
						if(date != "0") {
							var targets = parent.find(".comment-list")
							var tg = $(targets).append("<div class='comment-border'></div>")
							var par = $(".comment-border:last")
							$(par).data("id", date)
							$(par).load("commentContent.html", function() {
								var t = par.find(".comment-content-p")
								$(t).html(tex)
								$(target).val("")
								showMsg('评论成功', 'center');
							})
						} else {
							showMsg('评论出错', 'center');
							console.log("pushComment Error")
						}
					})
				} else {
					$("#myModalLabel").text("请登陆")
					$(".modal-body").load("../common/login_copy.html")
					$('#myModal').modal('show')
				}

			})
		}
		/*console.log("statusTag" + statusTag)*/
		if(statusTag == 1) {
			/*console.log("statusTag" + "START")*/
			/*进行pushComment 判断存储状态*/
			$.post("/Uncom/saveArticle/pushComment.action", {
				comment: tex,
				commentLevel: 1
			}, function(date) {
				if(date != "0") {

					var targets = parent.find(".comment-list")
					var tg = $(targets).append("<div class='comment-border'></div>")
					var par = $(".comment-border:last")
					$(par).data("id", date)
					$(par).load("commentContent.html", function() {
						var t = par.find(".comment-content-p")
						$(t).html(tex) 
						$(target).val("")
						showMsg('评论成功', 'center');
					})
				} else {
					showMsg('评论出错', 'center');
					console.log("pushComment Error")
				}
			})

		} else {

		}
	}

}

function childCommentSend(e) {
	var paren = $(e).parent().parent().parent().parent().parent().parent();
	var tar = paren.find("#toReplyButton")
	$(tar).attr("onclick", "replyChildadd(this);")
	var par = paren.find(".Childcomment")
	var target = paren.find(".write-area");
	var tex = $(target).val()
	$(par).load("ChildCommentContent.html", function() {
		var tp = par.find(".childCommentContents");
		tp.html(tex)

	})

}

function childCommentSends(a) {
	var paren = $(e).parent().parent().parent().parent().parent().parent();
	var tar = paren.find("#toReplyButton")
	$(tar).attr("onclick", "replyChildaddc(this);")
	var par = paren.find(".Childcomment")
	var target = paren.find(".write-area");
	var tex = $(target).val()
	$(par).load("ChildCommentContent.html", function() {
		var tp = par.find(".childCommentContents");
		tp.html(tex)

	})

}

function childCommentSendToTrail(e) {
	var paren = $(e).parent().parent().parent().parent().parent().parent().parent();
	var texttar = $(e).parent().parent().parent().parent()
	var tar = paren.find("#toReplyButton")
	$(tar).attr("onclick", "replyChildadd(this);")
	var par = paren.find(".comment-child-list")
	var rmoveat = paren.find(".ChildCommentTrail");
	$(rmoveat).remove()
	var tag = $(par).append("<div class='child-comment'></div>");
	var tg = tag.find(".child-comment:last")
	var pn = paren.parent().parent()
	var replyChildadd = pn.find("#toReplyButton");
	replyChildadd.attr("onclick", "replyChildadd(this);")
	var target = texttar.find(".write-area");
	var tex = $(target).val()
	$(tg).load("ChildCommentContents.html", function() {
		var tp = tg.find(".childCommentContents");
		tp.html(tex)

	})
}

function addComment(e, txt) {
	var paren = $(e).parent().parent().parent().parent();
	var par = paren
	$(paren).load("ChildCommentContent.html")
	var target = par.parent();
	target.find(".childCommentContents");
	alert(target.find(".childCommentContents").html())
}

function fabulousAdd(e) {
	var pa = $(e).find(".fabulousChildCount");
	var count = pa.text();
	count++;
	pa.text(count)
	$(e).removeAttr("onclick");
}

function replyChildAdd(e) {

	var paren = $(e).parent().parent();
	var rem = $(paren).parent()
	rem.find(".ChildCommentTrail").remove()
	var target = paren.find(".Childcomment");
	paren.find(".ChildCommentTrail").remove()
	$(target).prepend("<div class='ChildCommentTrail' ></div>")
	var targets = target.find(".ChildCommentTrail")
	$(targets).hide(100)
	$(targets).load("ChildCommentBox.html")
	$(targets).show(200)
}

function replyChildadd(e) {
	var paren = $(e).parent().parent();
	var rem = $(paren).parent()
	rem.find(".ChildCommentTrail").remove()
	var target = paren.find(".Childcomment");
	$(target).prepend("<div class='ChildCommentTrail' ></div>");
	var targets = paren.find(".ChildCommentTrail");
	$(targets).show(1)
	$(targets).hide(1)
	$(targets).load("ChildCommentBox.html", function() {
		var tas = paren.find(".comment-send");
		var ts = $(tas).attr("onclick", "childCommentSendToTrail(this);")
		$(targets).show(200)

	})

}

function replyChildAddc(e) {

	var paren = $(e).parent().parent().parent().parent().parent();
	var pt = $(e).parent().parent()
	var commentbox = paren.find(".ChildCommentTrail").remove()
	$(pt).append("<div class='ChildCommentTrail' ></div>");
	var targets = paren.find(".ChildCommentTrail");
	targets.css("display", "none")
	$(targets).load("ChildCommentBoxs.html", function() {

		$(targets).show(200)
	})

}

function cancelChildComment(a) {
	var paren = $(a).parent().parent().parent().parent().parent();
	var target = paren.find(".Childcomment")
	var tg = paren.find(".ChildCommentTrail")
	$(tg).hide(200, function() {
		tg.remove()
	})
	var targets = paren.find(".ChildCommentTrail");
	var target2 = paren.find("#toReplyButton")
	/*target2.attr("onclick","replyChildadd(this);")*/
}

function cancelChildComments(a) {
	var parent = $(a).parent().parent().parent().parent().parent()
	var paren = $(a).parent().parent().parent().parent().parent().parent().parent();
	var target = paren.find(".ChildCommentTrail")
	var target2 = parent.find("#toReplyButton")
	$(target).hide(200, function() {
		$(target).remove()
	})

	target2.attr("onclick", "replyChildAddc(this);")

}

function commentChildShow(a) {
	console.log("start")
	var parent = $(a).parent().parent()
	var tag = parent.find(".comment-child-list")
	tag.show(200)
	var tags = parent.find("#replyButton")
	//$(tag).show(200)
	console.log("show" + tag.length)
	tags.attr("onclick", "commentChildHide(this)")
}

function commentChildHide(a) {
	console.log("start")
	var parent = $(a).parent().parent()
	var tag = parent.find(".comment-child-list")
	var tags = parent.find("#replyButton")
	console.log("hide")
	tag.hide(200)
	//$(tag).hide(200)
	tags.attr("onclick", "commentChildShow(this)")

}

function showMsg(text, position) {
	var show = $('.show_msg').length
	if(show > 0) {

	} else {
		var div = $('<div></div>');
		div.addClass('show_msg');
		var span = $('<span></span>');
		span.addClass('show_span');
		span.appendTo(div);
		span.text(text);
		$('body').append(div);
	}
	$(".show_span").text(text);
	if(position == 'bottom') {
		$(".show_msg").css('bottom', '5%');
	} else if(position == 'center') {
		$(".show_msg").css('top', '');
		$(".show_msg").css('bottom', '50%');
	} else {
		$(".show_msg").css('bottom', '95%');
	}
	$('.show_msg').hide();
	$('.show_msg').fadeIn(1000);
	$('.show_msg').fadeOut(1000);
}
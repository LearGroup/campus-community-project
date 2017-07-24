var collactionList = null;
var statusTag = 0;
var collactionId = null;
var collactionTeg = 0;
var tag
var articlecontent
var title
$(function() {
	
	$(".send-btn").popover({
		trigger: 'click',
		title: "",
		placement: 'bottom',
		content: "不能发送空文章哦~"
	});
	$(".close-model-btn").bind("click", function() {
		$(".modal-body").html(" ")
		console.log("end")
	})

	/*为模态框提交更改按钮增加事件：执行登陆逻辑，当返回值为1时，则登陆成功关闭模态框*/
	$(".send-change").bind("click", function() {
		var status = login(0)
		statusTag = status[0]
		console.log("status:" + status[0])
		if(statusTag == 1) {
			$('#myModal').modal('hide')
		} else {
			return null
		}
	})

	/*页面初始化进行一次状态验证操作 保证登陆状态*/
	$.post("/Uncom/saveArticle/getcollactionTag.action", {
		"status": "collactionTag"
	}, function(data) {
		if(data == "0") {
			$("#myModalLabel").text("请登陆")
			$(".modal-body").load("../common/login_copy.html")
			$('#myModal').modal('show')
			statusTag = 0;
		} else {
			collactionList = data
			statusTag = 1;
			collactionTeg = 1;
			console.log(data)
		}
	})

	$(".collaction-p").bind("click", function() {
		collactionSet(collactionList)
		$('#myModal').modal('show')

	})

	$(".article-page").hide()

	$(".send-btn").bind("click", articleStautsCheck)

})

function articleStautsCheck() {

	if(statusTag == "0") {
		$.post('/Uncom/login_checkStatus.action', {}, function(response) {
			console.log(response)
			if(response != "000") {
				statusTag = 1;

			} else {
				$("#myModalLabel").text("请登陆")
				$(".modal-body").load("../common/login_copy.html")
				$('#myModal').modal('show')
				statusTag = 0;
				return null
			}

		})

	} else {
		var article = editor.txt.html()
		var collectionTagid = collactionId;
		$(".article-page").html(article)
		/*如果文章为空，则不能进行存储*/

		if($(".article-page").find("P").html() == "<br>") {
			console.log("start")
			$("[data-toggle='popover']").popover();

			return null

		} else {

			$("[data-toggle='popover']").popover("hide");

			tag = $(".article-page").find("p:first-child").remove()
			articlecontent = $(".article-page").html()
			title = tag.html();
			var test = $(".article-page").find("p").remove()

			if(test.html() == "" || typeof(test.html()) == 'undefined' || test.html() == "<br>") {
				var str = "文章不能只有标题哦~"
				$(".send-btn").popover("destroy");
				$(".send-btn").popover({

					title: "",
					placement: 'bottom',
					content: str
				});

				$(".send-btn").popover("show");

			} else {

				$(".send-btn").popover("destroy");
				console.log("destroy popover  ")
				console.log($(".collaction-p").html() == "点击设置文章类型")
				if($(".collaction-p").html() == "点击设置文章类型") {
					if(collactionTeg == 0) {
						console.log("repost getcollactionTag")
						$.post("/Uncom/saveArticle/getcollactionTag.action", {
							"status": "collactionTag"
						}, function(data) {
							if(data == "0") {
								$("#myModalLabel").text("请登陆")
								$(".modxal-body").load("../common/login_copy.html")
								$('#myModal').modal('show')
								statusTag = 0;
							} else {
								console.log("begin setcollactionTag")
								collactionList = data
								statusTag = 1;
								collactionTeg = 1;
								console.log(data)
								$('#myModal').modal('show')

								collactionSet(collactionList)
								var collectionTagid = collactionId;
							}
						})
					} else {
						$('#myModal').modal('show')
						collactionSet(collactionList)
					}
					return null;
				} else {
					$(".send-btn").attr("disabled", true)
					console.log(articlecontent + title, collectionTagid)
					$.post("/Uncom/saveArticle/saveArticle.action", {
						'articleContent': articlecontent,
						'articleName': title,
						'isPublished': 1,
						"articlePictureUrl": null,
						'collectionTagId': collectionTagid
					}, function(data) {
						if(data == "1") {
						
								showMsg('发表成功', 'center');
						
						}
						console.log("articleSaveResponse:" + data)
						$(".send-btn").attr("disabled", false)
					})
				}

			}

		}

	}

}

function collactionSet(data) {
	console.log("paser collactionTag")
	$(".modal-body").html(" ")
	var collactionList = '[{"collectionName":"软件开发","id":1},{"collectionName":"设计模式","id":2},{"collectionName":"Git","id":3}]'
	var coll = eval(data)
	var len = 0;
	for(var t in coll) {
		len += 1;
	}

	$("#myModalLabel").text("设置文章类型")
	$(".modal-body").innerHTML = ""
	var parent = $(".modal-body")

	for(var i = 0; i < len; i++) {
		(function() {
			var temp2 = document.createElement("button");
			temp2.style.position = 'relative';
			temp2.style.margin = '5px';
			temp2.setAttribute('class', 'btn btn-group-lg');
			temp2.innerHTML = coll[i].collectionName
			var id = coll[i].id
			parent.append(temp2)
			$(temp2).bind("click", function(e) {

				$(".collaction-p").text(temp2.innerHTML)
				$('#myModal').modal('hide')
				collactionId = id

			})
		})()
	}

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
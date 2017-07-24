$(function() {

	var collactionList = null;
	var statusTag = 1;
	var collactionId = null;
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
			console.log(data)
		}
	})

	$(".collaction-p").bind("click", function() {
		collactionSet(collactionList)
		$('#myModal').modal('show')

	})

	$(".article-page").hide()
	$(".send-btn").bind("click", function() {
		/*$.post("/Uncom/saveArticle/getcollactionTag.action", {
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
				console.log(data)
			}
		})*/

		if(statusTag == "0") {
			$("#myModalLabel").text("请登陆")
			$(".modal-body").load("../common/login_copy.html")
			$('#myModal').modal('show')
			return null;
		} else {
			var article = editor.txt.html()
			var collectionTagid = collactionId;
			$(".article-page").html(article)
			/*如果文章为空，则不能进行存储*/
			if($(".article-pdage").find("P").html() == "<br>") {
				
				$("[data-toggle='popover']").popover();

			} else {
				if($(".collaction-p").text() == "点击设置文章类型") {
					collactionSet(collactionList)
					var collectionTagid = collactionId;
				}
			}
			var tag = $(".article-page").find("p:first-child").remove()
			var articlecontent = $(".article-page").html()
			var title = tag;

			$.post("/Uncom/saveArticle/saveArticle.action", {
				articleContent: articlecontent,
				articleName: title,
				isPublished: 1,
				collectionTagId: collectionTagid
			}, function(data) {
				console.log("articleSaveResponse:" + data)
			})

		}

	})

})

function collactionSet(data) {

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
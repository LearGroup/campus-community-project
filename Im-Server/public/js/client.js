$(function() {

	$(".send-btn").bind("click", function() {
		send();
		addResponse()

	})
})

function send() {
	var tag = $(".list-content")
	tag.append("<div class='row content-box'></div>")
	var target = $(".list-content").find(".content-box:last")
	target.load("/html/message-item.html", function() {
		target.find(".message-content").val("Hello World")
		target.find(".message-content").css("margin-left","40px")
		$(".list-view").animate({
			'scrollTop': $(".list-content").height() + 'px'
		}, 500)
	})

}

function addResponse() {

	var tag = $(".list-content")
	tag.append("<div class='row content-box'></div>")
	var target = $(".list-content").find(".content-box:last")
	target.load("/html/message-response.html", function() {
		target.find(".message-content").val("Hello World")
		target.find(".message-box").css("text-align", 'left')
		target.find(".message-content").css("margin-left","-40px")
		$(".list-view").animate({
			'scrollTop': $(".list-content").height() + 'px'
		}, 500)
	})

}
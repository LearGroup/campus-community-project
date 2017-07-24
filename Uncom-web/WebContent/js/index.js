$(function() {
	$('#home-page').on('click', function() {
		var html_obj = $.post('/Uncom/register_Page.action', {
			request: 'home_page'
		}, function(response) {
			$("#content-row").show(200, function() {
				document.getElementById("content_row").innerHTML = response;
			})
		})
	})

	$('#login_btn').on('click', function() {
		$.post('/Uncom/login_Page.action', {
			request: 'login_page'
		}, function(response) {
			$("#login_page_row").hide(0, function() {
				if(response == "success") {
					$("#login_page_row").load("common/login_page.html", function() {
						$("#login_page_row").slideDown(200)
					})
				}
			})
		})
	})

	$('#register_btn').on('click', function() {

		$.post('/Uncom/register_Page.action', {
			request: 'register_page'
		}, function(response) {
			$("#login_page_row").hide(0, function() {
				if(response == "success") {
					$("#login_page_row").load("common/register_page.html", function() {
						$("#login_page_row").slideDown(200)
					})
				}

			})

		})
	})
	initPage()
})

function articlePage(tht) {
	alert($(tht).attr("data-article"))
}

function initPage() {
	$(".navbar-collapse").bind("click", function() {
		$(".collapse").collapse('hide')
	})

	var tag = statusTest()
	fadeIconSet()
	$(".loader").load("article/load.html", function() {
		$.post("/Uncom/article/article_getArticleSet.action", {
			username: "劳谦"
		}, function(data) {
			var article = eval(data)
			console.log("response:" + article.length)
			articleCardPaser(article)
			$(".loader").remove();
		})
	})
}

function statusTest() {
	$.post('/Uncom/login_checkStatus.action', {}, function(response) {
		console.log(response)
		if(response != "000") {
			$("#login_btn").attr("id", "user_btn")
			$("#register_btn").attr("id", "exit_btn")
			$("#exit_btn").unbind("click")
			$("#user_btn").unbind("click")
			$("#user_btn").text(response)
			$("#user_btn").attr("href", "user/PersonCenter.html")
			$("#user_btn").attr("target", "_self")
			$("#exit_btn").html("登出")
			$("#exit_btn").attr("href", '/Uncom/login_Logout.action')
			return "1"
		} else {
			return "0"
		}

	})
}

function articleLike(e) {
	if($("#login_btn").text() == "登陆") {
		$.post('/Uncom/login_Page.action', {
			request: 'login_page'
		}, function(response) {
			if(response == "success") {
				$("#login_page_row").load("common/login_page.html", function() {
					$("#login_page_row").slideDown(200)
				})
			} else {
				alert("已登录")
			}
		})
	}
}

function fadeIconSet() {
	$(".fade-icon2").hide()
	$(".fade-icon3").hide()

	$(".fade-icon2").bind("click", function() {
		window.location.href = "article/articleInput.html"
	})

	$(".fade-icon").bind("click", function() {

		if($(".fade-icon2").is(":hidden")) {
			$(".fade-icon").rotate({
				angle: 0,
				animateTo: 360,
				easing: $.easing.easeInOutExpo
			})
			$(".fade-icon2").slideDown(200)
			$(".fade-icon3").slideDown(200)

		} else {
			$(".fade-icon2").slideUp(200)
			$(".fade-icon3").slideUp(200)
			$(".fade-icon").rotate({
				angle: 0,
				animateTo: 360,
				easing: $.easing.easeInOutExpo
			})
		}

	})
}
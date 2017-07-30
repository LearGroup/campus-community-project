var loadDataMode = new loadDataModel()

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

	$(".setPage").bind("click", function(event) {

		$(".navbar-nav li").removeClass("active")
		$(".setPage").attr("class", "setPage active")
		getContent("/Uncom/article/article_getSetPage.action")
		event.stopPropagation();
	})

	$(".myWorkLog").bind("click", function(event) {
		$(".navbar-nav li").removeClass("active")
		$(".myWorkLog").attr("class", "myWorkLog active")
		$.post('/Uncom/login_checkStatus.action', {}, function(response) {
			console.log(response)
			if(response != "000") {
				getContent("/Uncom/article/article_getMyWorkPage.action")
			} else {
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
				return null
			}

		})

		event.stopPropagation();
	})

	$(".articlePage").bind("click", function() {
		$(".navbar-nav li").removeClass("active")
		$(".articlePage").attr("class", "articlePage active")
		getContent("/Uncom/article/article_getArticlePage.action")
	})

	$(".nav-group").bind("click", function() {
		$(".navbar-nav li").removeClass("active")
		$(".nav-group").attr("class", "nav-group active")

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
	$(".navbar-nav li:not(.dropdown)").on("click", function() {
		if($("#nav-button-toggle").is(":hidden")) {

		} else {
			$(".collapse").collapse('hide')
		}

	})

	var tag = statusTest()
	fadeIconSet()
	getContent("/Uncom/article/article_getSetPage.action")

}

function getContent(url) {

	$(".list-container").slideUp(function() {
		$(".list-container").empty()
		$(".loader").load("article/load.html", function() {
			$.post(url, {
				username: "劳谦"
			}, function(data) {
				var article = eval(data)
				console.log("response:" + article.length)
				articleCardPaser(article)
				$(".loader").empty();
			})
		})
		$(".list-container").slideDown()

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
/*$(".content-box").bind("click", function() {
					var maxHeight = $(".content-box").css("max-height");
					if(maxHeight == "160px") {
						$(".content-box").animate({
							'max-height': "1000px"
						})
						$(".abstract").animate({
							'max-height': "1000px"
						})
					} else {
						$(".abstract").animate({
							'max-height': "60px"
						})
						$(".content-box").animate({
							'max-height': "160px"
						})

					}

					/*alert("ok")*/
/*})*/
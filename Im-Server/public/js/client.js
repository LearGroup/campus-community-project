$(function() {

	$(".send-btn").bind("click", function() {
		send();
		addResponse()

	})

	$('.refresh').bind('click', function() {
		$.post("/getFrendList", {
			id: "46e0a594-a062-497f-8dd8-5d2bac3faf8a"
		}, function(data) {
			console.log(data)
			var dataList = eval(data)
			var listview = $('.frend-list')
			listview.empty()
			for(var i = 0; i < dataList.length; i++) {
				(function(data) {
					listview.append("<div class='col-xs-12 frend-item'></div>")
					var target = listview.find(".frend-item:last").load("/html/parent-item.html", function() {
						target.find(".frend-head").attr("src", data.header_pic)
						target.find(".username").html(data.username)
						target.bind("click", function() {
							$('.list-content').data("target-id", data.minor_user)
							
						})
					})

				})(dataList[i])

			}

		})
	})
})

function send() {
	/*var cureentTimeStamp = Date.parse(new Date()) / 1000;
	var timediffs = timediff(article[0].time / 1000, cureentTimeStamp)
	var pTime = printTime(timediffs)*/
	var str = $(".send-input").val()
	var tag = $(".list-content")
	tag.append("<div class='row content-box'></div>")
	var target = $(".list-content").find(".content-box:last")
	target.load("/html/message-item.html", function() {
		target.find(".message-content").text(str)
		target.find(".message-content").css("margin-left", "40px")
		$(".list-view").animate({
			'scrollTop': $(".list-content").height() + 'px'
		}, 500)
	})

}

function addResponse() {
	var str = $(".send-input").val()
	var tag = $(".list-content")
	tag.append("<div class='row content-box'></div>")
	var target = $(".list-content").find(".content-box:last")
	target.load("/html/message-response.html", function() {
		target.find(".message-content").text(str)
		target.find(".message-box").css("text-align", 'left')
		target.find(".message-content").css("margin-left", "-40px")
		$(".list-view").animate({
			'scrollTop': $(".list-content").height() + 'px'
		}, 500)
	})

}

function getTime(time) {
	var str = {
		"articleCreateTime": {
			"date": 13,
			"day": 4,
			"hours": 20,
			"minutes": 25,
			"month": 6,
			"nanos": 0,
			"seconds": 11,
			"time": 1499948711000,
			"timezoneOffset": -480,
			"year": 117
		}
	}
	var unixTimeStamp = str.articleCreateTime.time / 1000
	//功能：把unix时间戳转成Y-m-d H:i:s格式的日期
	var now = new Date(unixTimeStamp * 1000);
	var year = now.getFullYear();
	var month = now.getMonth() + 1;
	month = month < 10 ? "0" + month : month;
	var day = now.getDate();
	day = day < 10 ? "0" + day : day;
	var hour = now.getHours();
	hour = hour < 10 ? "0" + hour : hour;
	var minute = now.getMinutes();
	minute = minute < 10 ? "0" + minute : minute;
	var second = now.getSeconds();
	second = second < 10 ? "0" + second : second;
	return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;

}

function timediff(begin_time, end_time) {

	var startTime = "2017-04-04"
	var s1 = new Date(startTime.replace(/-/g, "/"))
	s2 = new Date()
	runTime = parseInt(end_time - begin_time)
	var year = Math.floor(runTime / 86400 / 365)

	runTime = runTime % (86400 * 365)
	var month = Math.floor(runTime / 86400 / 30)
	runTime = runTime % (86400 * 30)
	var day = Math.floor(runTime / 86400)
	runTime = runTime % 86400
	var hour = Math.floor(runTime / 3600)
	runTime = runTime % 3600
	var minute = Math.floor(runTime / 60)
	runTime = runTime % 60
	var second = runTime
	return [year, month, day, hour, minute, second]

	/*var tag=end_time-begin_time
    var days=tag*60*60*1000
    alert(tag/ 86400 /30)*/
	/*  res =[days,hours,mins,secs]*/

}
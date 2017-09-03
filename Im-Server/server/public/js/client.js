
$(function() {
	init()
	$(".send-btn").bind("click", function() {
		send();
		
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
var socket

function init(){

	$.post("/checkStatus",function(data){
	    if(data=='null'){
	    	$("#myModalLabel").text("请登陆")
				$(".modal-body").load("/html/login_copy.html")
				$('#myModal').modal('show')
	    }else{
	    		$('.list-content').data("userName", data[0].username)
	    	    console.log('start')
	    	    socket=io()
	    	    socket.on("message",function(res){
	    		console.log('res:'+res)
	    		if(res!=undefined){
	    			addResponse(res)
		        }
		    })
	    	socket.emit('login',{userName:data[0].username,userId:data[0].id})
	    	socket.on('hello',function(data){
	    	console.log(data)	
	    	})
	    }
	})
}
var startTime = null
var currentTime = null;

function send() {
	var pTime
	currentTime = Date.parse(new Date()) / 1000;
	if(startTime == null) {
		startTime = Date.parse(new Date()) / 1000;
		pTime = getTime(startTime)
	} else {
		var timediffs = timediff(startTime, currentTime)
		pTime = printTime(timediffs, currentTime)
		console.log(pTime)
	}
	var targetId= $(".list-content").data("target-id")
	var str = $(".send-input").val()
	var tag = $(".list-content")
	tag.append("<div class='row content-box'></div>")
	var target = $(".list-content").find(".content-box:last")
	var myName=$(".list-content").data("userName")
	socket.emit('message',{time:currentTime,targetId:targetId,content:str,myName:myName})
	console.log({time:currentTime,targetId:targetId,content:str,myName:myName})
	socket.on("messageResponse",function(res){
		console.log('res:'+res)
		if(res!=undefined){
			target.load("/html/message-item.html", function() {
	    	target.find(".message-content").text(str)
	    	target.find(".user-name").text(myName)
		    target.find(".message-content").css("margin-left", "40px")
		    target.find(".message-time").text(pTime)
		    $(".lis t-view").animate({
			    'scrollTop': $(".list-content").height() + 'px'
			}, 500)
		})
		}
	})

}

function addResponse(res) {
	var tag = $(".list-content")
	tag.append("<div class='row content-box'></div>")
	var target = $(".list-content").find(".content-box:last")
	target.load("/html/message-response.html", function() {
		target.find(".message-content").text(res.content)
		target.find(".user-name").text(res.myName)
		target.find(".message-time").text(res.time)
		target.find(".message-box").css("text-align", 'left')
		target.find(".message-content").css("margin-left", "-40px")
		$(".list-view").animate({
			'scrollTop': $(".list-content").height() + 'px'
		}, 500)
	})

}

function getTime(time) {
	var unixTimeStamp = time
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
	return hour + ":" + minute + ":" + second;

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
}

function printTime(timeList, cureentTime) {

	var unixTimeStamp = cureentTime
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

	if(timeList[0] != 0) {
		return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
	} else {
		if(timeList[1] != 0) {
			return month + "-" + day + " " + hour + ":" + minute + ":" + second;
		} else {
			if(timeList[2] != 0) {
				return day + " " + hour + ":" + minute + ":" + second;
			} else {
				if(timeList[3] != 0) {
					return hour + ":" + minute + ":" + second;
				} else {
					if(timeList[4] == 3) {
						return hour + ":" + minute + ":" + second;
					} else {
						if(timeList[5] != 0) {
							return " "
						} else {
							return " "
						}
					}
				}
			}
		}
	}

}
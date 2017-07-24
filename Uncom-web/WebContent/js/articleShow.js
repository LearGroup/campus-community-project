
function articleCardPaser(data){
	console.log("articlePaser")
	var article="name"
	var articleList;
	for (var i=0;i<data.length;i++) {
		article=data[i]
		createArticleCard(article)
	}
}


function createArticleCard(article){
	console.log("createArticleCard")
	$(".list-container").append("<div class='col-lg-12 contentBox' style='padding: 0px;'></div> ")
	var par=$(".contentBox:last")
	
	/*$(".list-container").append("<div class='panel panel-default col-lg-push-3 col-xs-12 col-lg-6 content-box shadow '></div>")
	var par=$(".content-box:last")*/
	par.hide(0,function(){
	
		par.load("article/articleCard.html",function(){
		par.find(".username").text(article[6])
		par.data("id",article[3])
		var cureentTimeStamp= Date.parse(new Date())/1000;
		var timediffs=timediff(article[0].time/1000,cureentTimeStamp)
		var pTime=printTime(timediffs)
		par.bind('click',function(){
			var id=par.data("id")
	/*		console.log(id)*/
	      	$("#hidden_submit_info").attr("action","/Uncom/article/"+id+".action")
	      	$('#hidden_submit_info').submit();
			/*window.open("/Uncom/article/"+article[4]+".action")*/
		  /*  window.location.reload("/Uncom/article/"+id+".action")*/
			
		})
	    par.find(".time").text(pTime)
	    par.find(".title").text(article[5])
	    par.find(".abstract").text(article[4])
	    par.find(".collection-tag>small").html(article[13])
	    par.find(".like-count").html(article[10])
	    par.find(".read-count").html(article[11])
	    par.find(".comment-count").html(article[12])
	    par.find(".comment-view").bind('click',function(){
	    	alert("comment-view")
	    	return false
	    })
	      par.slideDown(240)
	})
	    
	})
}


function printTime(timeList){
	if(timeList[0]!=0){
		return timeList[0]+"年前"
	}else{
		if(timeList[1]!=0){
			return timeList[1]+"月前"
		}else{
			if(timeList[2]!=0){
				return timeList[2]+"天前"
			}else{
				if(timeList[3]!=0){
					return timeList[3]+"小时前"
				}else{
					if(timeList[4]!=0){
						return timeList[4]+"分钟前"
					}else{
						if(timeList[5]!=0){
							return timeList[5]+"秒前"
						}else{
							return "刚刚"
						}
					}
				}
			}
		}
	}
	
}


function getTime(time){
	var str={"articleCreateTime":{"date":13,"day":4,"hours":20,"minutes":25,"month":6,"nanos":0,"seconds":11,"time":1499948711000,"timezoneOffset":-480,"year":117}}
	var unixTimeStamp=str.articleCreateTime.time/1000
	  //功能：把unix时间戳转成Y-m-d H:i:s格式的日期
    var now=new Date(unixTimeStamp*1000);
    var year=now.getFullYear();
    var month=now.getMonth()+1;
    month=month<10?"0"+month:month;
    var day=now.getDate();
    day=day<10?"0"+day:day;
    var hour=now.getHours();
    hour=hour<10?"0"+hour:hour;
    var minute=now.getMinutes();
    minute=minute<10?"0"+minute:minute;
    var second=now.getSeconds();
    second=second<10?"0"+second:second;
    return year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
    
}




function timediff(begin_time,end_time)
{
	
	
	
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
    return [year,month,day,hour,minute,second]

	/*var tag=end_time-begin_time
    var days=tag*60*60*1000
    alert(tag/ 86400 /30)*/
    /*  res =[days,hours,mins,secs]*/

}






var js=[["随着互联网+时代的到来，社交领域也迎来了变革的机会，借助各类传感器，社交网络已将触手伸到虚拟与现实交汇的领域。未来，我们将能够搭建一个融合虚拟和现实的社交应用，人与人之间的连结变得更为便捷有效，很多传统社交成本将一去不复返。鉴于融合虚拟现实社交带来的令人兴奋的前景，故计划率先在校园中打造一个践行该理念的社交软件，基于真实地理位置，为用户提供不同的活动功能整合，简化活动成本，提供沉浸式的活动体验。并提供高质量的活动配对，为高质量新关系的产生创造更多可能。 我们将能够通过活动的全程控制，帮助用户留住日常活动丢失的价值，并通过这些信息，帮助用户寻找到更多有趣的活动和特质相似的个体，增加大学生活的乐趣，并创造更多的价值，充分锻炼自身。",{"date":13,"day":4,"hours":20,"minutes":25,"month":6,"nanos":0,"seconds":11,"time":1499948711000,"timezoneOffset":-480,"year":117},0,1,"ea435dfa-5676-4070-8e45-06e4253b324b","随着互联网+时代的到来，社交领域也迎来了变革的机会，借助各类传感器，社交网络已将触手伸到虚拟与现实交汇的领域。未来，我们将能够搭建一个融合虚拟和现实的社交应用，人与人之间的连结变得更为便捷有效，很多传...","社区开发的出发点 ","劳谦","46e0a594-a062-497f-8dd8-5d2bac3faf8a",null,"img/back-img2.jpg",0,0,0,"软件开发"],["“release ”通常负责“短期的发布前准备工作”、“小bug的修复工作”、“版本号等元信息的准备工作”。与此同时，“develop”分支又可以承接下一个新功能的开发工作了。 团队成员从主分支(master)获得的都是处于可发布状态的代码，而从开发分支(develop)应该总能够获得最新开发进展的代码。",{"date":13,"day":4,"hours":20,"minutes":42,"month":6,"nanos":0,"seconds":10,"time":1499949730000,"timezoneOffset":-480,"year":117},0,1,"b17f1c62-9dba-4352-9f57-3eaffdbbf735","“release ”通常负责“短期的发布前准备工作”、“小bug的修复工作”、“版本号等元信息的准备工作”。与此同时，“develop”分支又可以承接下一个新功能的开发工作了。 团队成员从主分支(ma...","Git分支管理策略 ","劳谦","46e0a594-a062-497f-8dd8-5d2bac3faf8a",null,"img/back-img2.jpg",0,0,0,"软件开发"]]

$(function(){
	 articlePaser(js)
})

function articlePaser(data){
	var article="name"
	var articleList;
	for (var i=0;i<data.length;i++) {
		article=data[i]
		createArticleCard(article)
	}
}


function createArticleCard(article){

	$(".list-container").append("<div class='col-lg-push-3 col-xs-12 col-lg-6 content-box shadow '></div>")
	var par=$(".content-box:last")
	par.hide(0,function(){
		par.load("article/articleCard.html",function(){
		par.find(".username").text(article[7])
		var cureentTimeStamp= Date.parse(new Date())/1000;
		var timediffs=timediff(article[1].time/1000,cureentTimeStamp)
		var pTime=printTime(timediffs)
		par.bind('click',function(){
			window.open("/Uncom/article/"+article[4]+".action")
		/*	window.location.href="article/article.html"*/
			localStorage.setItem("id",article[4])
		})
	    par.find(".time").text(pTime)
	    par.find(".title").text(article[6])
	    par.find(".abstract").text(article[5])
	    par.find(".collection-tag>small").html(article[14])
	    par.find(".like-count").html(article[11])
	    par.find(".read-count").html(article[12])
	    par.find(".comment-count").html(article[13])
	    par.find(".comment-view").bind('click',function(){
	    	alert("comment-view")
	    	return false
	    })
	    
	})
	    par.slideDown(240)
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
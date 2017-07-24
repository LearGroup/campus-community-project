
$(function(){
	$.post("/Uncom/article/getArticle.action",function(data){
		/*console.log(data)*/
		var article=eval(data)
		articlePaser(article)
		  		
	})
	
})



function  articlePaser(article){
	
	$(".title").text(article[0][5])
	$(".author").find(".author_name").text(article[0][6])
	var time=getTime(article[0][1])
	$(".publish-time").text(time)
	$(".read-count").text("阅读 "+article[0][11])
	$(".like-count").text("喜欢 "+article[0][10])
	$(".commments-count").text("评论 "+article[0][12])
	$(".articleContent").html("<p>"+article[0][0]+"</p>")
	$(".commet-count").text(article[0][12]+" 条评论")
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

mui.plusReady(function(){

//	var imgHeight = document.querySelector("img.hotListItem").style.width;
//	document.querySelector("img.hotListItem").style.height=imgHeight;
	$('.hotListItem img').height($('.hotListItem img').width());
})
window.onload = function(){
		mui('.mui-scroll-wrapper').scroll({
		deceleration:0.0005
	});
}

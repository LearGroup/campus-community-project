mui.plusReady(function(){
	var grelly = mui('.mui-slider');
	grelly.slider({
		interval: '2000'
	})
//	var imgHeight = document.querySelector("img.hotListItem").style.width;
//	document.querySelector("img.hotListItem").style.height=imgHeight;
	$('.hotListItem img').height($('.hotListItem img').width())
})
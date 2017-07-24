
window.onload=function(){

	document.querySelector('#personalButton').addEventListener('tap',function(e){
		mui.openWindow({
			url:'personalINF.html',
			id:'personalINF'
		})
	})
}
$(document).ready(function(){
	$('iframe').addClass('mui-scroll-wrapper');
})
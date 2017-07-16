mui.ready(function(){
	document.querySelector('#personalButton').addEventListener('click',function(e){
		mui.openWindowWithTitle({
			url:'personalINF.html',
			id:'personalINF.html'
		})
	})
})
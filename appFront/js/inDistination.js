/**用于在其他页面建立打开destinationIndex.html的js文件。
 * by：王亚宁
 * */
mui.ready(function(){
	document.querySelector('#distination').addEventListener('click',function(e){
		mui.openWindowWithTitle({
			url:'distination.html',
			id:'distination.html'
		})
	})
	
})
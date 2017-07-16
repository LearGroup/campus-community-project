//mui.init([
//	preloadPages:[{
//  	url:'index.html',
//  	id:'index.html',
//  	style:{}
//  }],
//  preliadLimit:5
//])
mui.ready(function(){
	document.querySelector('#indexButton').addEventListener('tap',function(){
		mui.back()
	})
})
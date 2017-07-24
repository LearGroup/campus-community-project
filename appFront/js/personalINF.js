mui.init({
	preloadPages: [{
			url: 'index.html',
			id: 'index.html',

	}],
	preliadLimit: 5
})
mui.ready(function() {
	document.querySelector('#indexButton').addEventListener('tap', function() {
		mui.openWindow({
			url: 'index.html',
			id: 'index.html'
		})
		mui.back()
	})
})
mui.init({
	preloadPages: [{
			url: 'index.html',
			id: 'index.html',
					subpages: [{
			url: 'child.html',
			id: 'child.html',
			styles: {
				top: '55px',
				bottom: '60px'
			}
		}]
	}],
	preliadLimit: 5
})
mui.ready(function() {
	document.querySelector('#indexButton').addEventListener('tap', function() {
		mui.openWindow({
			url: 'index.html',
			id: 'index.html',
					subpages: [{
			url: 'child.html',
			id: 'child.html',
			styles: {
				top: '55px',
				bottom: '60px'
			}
		}]
		})
		mui.back()
	})
})
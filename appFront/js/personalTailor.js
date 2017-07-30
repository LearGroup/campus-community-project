mui.init()

$('#carousel').Carousel({
	carouselId: '.itemBox', //轮播项目外层盒子的class或者ID
	carouselItem: '.carouselItem', //轮播项目的class
	autoCarousel: false, //是否自动轮播
	tabCarouselItemTime: 1000, //切换动画的时间
})
$('.point').click(function() {
	if($(this).css('background-color') == 'rgb(51, 122, 183)') {
		$(this).css('background-color', '#87CEFF')
		$(this).css('color', '#222222')
	} else {
		$(this).css('background-color', '#337AB7')
		$(this).css('color', '#FFFFFF')
	}

})
$('#money').blur(function(){
	postData.money = $('#money').attr('value');
})
//普通示例
var postData ={
	date:null,
	dayOfNumbre:null,
	people:null,
	place:null,
	money:null
};
mui.ready(function() {
	var dayPicker = new mui.PopPicker();
	var peoplePicker = new mui.PopPicker();
	var placePicker = new mui.PopPicker();
	dayPicker.setData([{
		value: '1',
		text: '1天'
	}, {
		value: '2',
		text: '2天'
	}, {
		value: '3',
		text: '3天'
	}, {
		value: '4',
		text: '4天'
	}, {
		value: '5',
		text: '5天'
	}, {
		value: '6',
		text: '6天'
	}, {
		value: '7',
		text: '7天'
	}]);
	peoplePicker.setData([{
			value: '1',
			text: '1人'
		},
		{
			value: '2',
			text: '2人'
		},
		{
			value: '3',
			text: '3人'
		},
		{
			value: '4',
			text: '4人'
		},
		{
			value: '5',
			text: '5人'
		},
		{
			value: '6',
			text: '6人'
		},
		{
			value: '7',
			text: '7人'
		},
	])
	placePicker.setData([{
		value: 'bt',
		text: '包头'
	}, {
		value: 'nh',
		text: '南湖湿地'
	}, {
		value: 'eeds',
		text: '鄂尔多斯'
	}, {
		value: 'xlglm',
		text: '锡林郭勒盟'
	}])
	var showUserPickerButton = document.getElementById('showUserPicker');
	showUserPickerButton.addEventListener('tap', function(event) {
		dayPicker.show(function(items) {
			var val = JSON.stringify(items[0]);
			postData.dayOfNumbre = JSON.parse(val).value;
			val = JSON.parse(val).text
			showUserPickerButton.value = val
			//返回 false 可以阻止选择框的关闭
			//return false;
		});
	}, false);
	var peopleInput = document.getElementById('peopleInput');
	peopleInput.addEventListener('tap', function(e) {
		peoplePicker.show(function(items) {
			var val = JSON.stringify(items[0]);
			postData.people = JSON.parse(val).value;
			val = JSON.parse(val).text
			peopleInput.value = val
		})
	})
	var place = document.getElementById('place');
	place.addEventListener('tap', function(e) {
		placePicker.show(function(items) {
			var val = JSON.stringify(items[0]);
			postData.place = JSON.parse(val).value;
			val = JSON.parse(val).text
			place.value = val
		})
	})
})
mui.plusReady(function() {
	var info = document.getElementById("pickDateBtn");
	document.getElementById("pickDateBtn").addEventListener('tap', function() {
		var dDate = new Date();
		var month = dDate.getMonth() + 4;
		var year = dDate.getFullYear();
		dDate.setFullYear(year, month - 4, 1);
		var minDate = new Date();
		minDate.setFullYear(year, month - 4, 1);
		var maxDate = new Date();
		maxDate.setFullYear(year, month, 31);
		plus.nativeUI.pickDate(function(e) {
			var d = e.date;
			postData.date=d.getFullYear()+'-'+(d.getMonth() + 1)+'-'+d.getDate();
			info.value = '您选择的日期是:' + d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate();
		}, function(e) {
			info.value = "您没有选择日期";
		}, {
			title: "请选择日期",
			date: dDate,
			minDate: minDate,
			maxDate: maxDate
		});
	})
	mui.post("http://47.95.0.73:8080/Uncom/login_Login.action",{
		date:postData.date,
		day:postData.dayOfNumbre,
		peopel:postData.people,
		place:postData.place,
		money:postData.money
	},function(data){
		console.log(data);
	})
})


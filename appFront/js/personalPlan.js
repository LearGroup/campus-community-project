var height = $(window).height();
var width = $(window).width();
$(document).ready(function() {
	var myDate = new Date()
	var month = myDate.getMonth() + 4;
	var year = myDate.getFullYear();
	$("#stratDayInput").calendar({
		maxDate: year + '-' + month + '-' + '1'
	});
	$('.form-control').css('border-radius', '5px');
	var carouselCaptionHeight = $('.carouselCaption > div').css('height')
	carouselCaptionHeight = parseInt(carouselCaptionHeight)
	var carouselHeight = parseInt(height);
	var carouselMargin = parseInt(width)
	carouselMargin *= 0.05;
	carouselHeight = carouselHeight - 53 - carouselCaptionHeight
	$('hr').css('top',carouselCaptionHeight*0.25)
	$('#myCarousel').css('height', carouselHeight)
	$('.item').css('height', carouselHeight)
	$('#footer').css('height', '53px')
	$('.carouselCaption').css('margin-bottom', '0px')
	$('.carouselCaption').css('height', $('.carouselCaption > div').css('height'))
	
	var carouselCaptionListWidth = $('.carouselCaption > div').css('width');
	carouselCaptionListWidth = parseInt(carouselCaptionListWidth);
	carouselCaptionListWidth *= 5;
	carouselCaptionListWidth += carouselMargin
	$('.carouselCaption').css('width',carouselCaptionListWidth)
//	var carouselCaptionUlWidth = $('.carouselCaption').css('width');
//	carouselCaptionUlWidth = parseInt(carouselCaptionUlWidth)
//	var carouselCaptionUlpadding = (carouselCaptionUlWidth - carouselCaptionListWidth) * 0.15
//	$('.carouselCaption').css('padding-left', carouselCaptionUlpadding)
//	$('.carouselCaption').css('padding-right', carouselCaptionUlpadding)
	$('#myCarousel').carousel('pause')
	$('.item').css('overflow', 'scroll')
	$("#pickerPeople").picker({
		title: "请选择团队人数",
		cols: [{
			textAlign: 'center',
			values: ['5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24']
		}]
	});
	$("#pickerPersonal").picker({
		title: "请选择团队人数",
		cols: [{
			textAlign: 'center',
			values: ['1','2','3','4','5',]
		}]
	});
	$("#dayInput").picker({
		title: "选择天数",
		cols: [{
			textAlign: 'center',
			values: ['1','2','3','4','5',]
		}]
	});
		
	$('#team').click(function(){
		$("#pickerPeopleBox").css('display','block')
		$("#pickerPersonalBox").css('display','none')
	})
	$('#personal').click(function(){
		$("#pickerPeopleBox").css('display','none')
		$("#pickerPersonalBox").css('display','block')
	})
	function openPickerCity(){
		$('#pickerCity').css('display','block')
	}
	$("#pickerPlace").picker({
		title: "请选择目的地",
		cols: [{
			textAlign: 'center',
			values: ['呼和浩特','包头','鄂尔多斯','呼伦贝尔','希拉穆仁',]
		
		}]
	});
	$("#pickerPlace").click(function(){
		if($('#pickerPlace').val()!=null)
		{
			$('#pickerCity').picker('setValue','呼和浩特')
			$('#pickerCityBox').css('display','block')
		}
	})
	
	$('#pickerCity').picker({
		title: "请选择目的地",
		cols: [{
			textAlign: 'center',
			values: ['呼和浩特','包头','鄂尔多斯','呼伦贝尔','希拉穆仁',]
		}]
	});
//	$("#pickerPlace").focus(function(){
//		if($("#pickerPlace").val()!=null){
//			
//		}
//	})
	$('.point').click(function(){
		if($(this).css('background-color')=='rgb(51, 122, 183)'){
			$(this).css('background-color','#87CEFF')
			$(this).css('color','#222222')
		}
		else{
			$(this).css('background-color','#337AB7')
			$(this).css('color','#FFFFFF')
		}

	})
	$("#pickerPrice").picker({
		title: "预算区间",
		cols: [{
			textAlign: 'center',
			values: ['1000~2000','2000~5000','5000~8000','8000~10000','10000~20000','20000~50000','50000以上']
		}]
	});
	$("#pickerChild").picker({
		title: "儿童人数",
		cols: [{
			textAlign: 'center',
			values: ['1','2','3','4','5','6', '7', '8', '9', '10', '11', '12', '13', '14', '15']
		}]
	});
	$('.weui-picker-modal-visible').css('height',height*0.5)
	
})
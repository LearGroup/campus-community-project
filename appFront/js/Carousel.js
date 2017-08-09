(function($) {//创建一个闭包
	  
	$.fn.extend({
		
		Carousel: function(options) {//插件定义

			var defaultsSetSpace = {//命名空间
				carouselId: '.itemBox',
				carouselItem: '.carouselItem',
				autoCarousel: false,
				autoCarouselTime: 3000,
				tabCarouselItemTime: 1500,
			};
			var opts = $.extend(defaultsSetSpace, options);//迭代和格式化每个匹配的元素 
			var carouselId = defaultsSetSpace.carouselId;
			var carouselItem = defaultsSetSpace.carouselItem;
			var autoCarousel = defaultsSetSpace.autoCarousel;
			var autoCarouselTime = defaultsSetSpace.autoCarouselTime;
			var tabCarouselItemTime = defaultsSetSpace.tabCarouselItemTime;
			var carouselHeight = defaultsSetSpace.carouselHeight;

			var i = 0;
			//轮播控制
			if(autoCarousel) {//如果autoCarousel为ture执行自动轮播
				setInterval(function() {
					if(i + 1 < $(carouselId).children().length) {//轮播盒子的子元素个数
						if(i == 0) {
							$(carouselId).find('div').eq(0).css('display', 'none');//轮播盒子中，为DIV的子元素被设置为不可见
							$(carouselId).find('div').eq(i + 1).fadeIn(tabCarouselItemTime);
							
						} else {
							$(carouselId).find('div').eq(i).css('display', 'none')
							$(carouselId).find('div').eq(i + 1).fadeIn(tabCarouselItemTime);
						}
						i++;
					} else {
						$(carouselId).find('div').eq(i).css('display', 'none');
						i = 0;
						$(carouselId).find('div').eq(i).fadeIn(tabCarouselItemTime);
					}
				}, autoCarouselTime)
			}

			$('.right').click(function(e) {
				if(i + 1 < $(carouselId).children().length) {
					if(i == 0) {
						$(carouselId).find('div').eq(0).css('display', 'none');
						$(carouselId).find('div').eq(i + 1).fadeIn(tabCarouselItemTime);
					
					} else {
						$(carouselId).find('div').eq(i).css('display', 'none')
						$(carouselId).find('div').eq(i + 1).fadeIn(tabCarouselItemTime);
					}
					i++;
				} else {
					$(carouselId).find('div').eq(i).css('display', 'none');
					i = 0;
					$(carouselId).find('div').eq(i).fadeIn(tabCarouselItemTime);
				}

			})
			$('.left').click(function(e) {
				if(i > 0) {
					$(carouselId).find('div').eq(i).css('display', 'none')
					$(carouselId).find('div').eq(i - 1).fadeIn(tabCarouselItemTime);
					i--;
				} else if(i == 0) {
					$(carouselId).find('div').eq(i).css('display', 'none');
					var end = $(carouselId).children().length;
					end -= 1;
					$(carouselId).find('div').eq(end).fadeIn(tabCarouselItemTime);
					i = end;
				}

			})
		}

	})

})(jQuery);
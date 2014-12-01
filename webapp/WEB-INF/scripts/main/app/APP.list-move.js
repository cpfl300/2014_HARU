window.addEventListener('load', list_move_init);
document.addEventListener('DOMContentLoaded', setPos);
function list_move_init(){
	MoveList.init($('.list-move-container'));
	
}

var MoveList = {
		init: function(target){
			this.fixedMarginTop = this.firstMargin;
			this.fixedListMarginTop = this.listImgMargin;
			this.id;
			this.datetimeOpacity;
			this.headerOpacity;
			this.headerColor;
			
			this.target = target[0];
			myScroll = new IScroll('.list-background', { probeType: 3, mouseWheel: false, click: true});
			
			myScroll.on('scrollStart', function(){
				MoveList.id = requestAnimationFrame(MoveList.setBlur);
				MoveList.fixedMarginTop = -this.y + MoveList.firstMargin;
				
				MoveList.fixedListMarginTop = -this.y + MoveList.listImgMargin;
				
				if(this.y >= 0){
					MoveList.datetimeOpacity = 1;
				}else if(this.y < -65){
					MoveList.datetimeOpacity = 0;
				}else{
					MoveList.datetimeOpacity = 1-Math.abs(this.y)/65;
				}
				
				if(this.y > -(MoveList.datetimeContainerSize - 44 - 65)){
					MoveList.headerOpacity = 0;
					MoveList.headerColor = 255;
				}else if(this.y <= -(MoveList.datetimeContainerSize - 44)){
					MoveList.headerOpacity = 1;
					MoveList.headerColor = 0;
				}else{
					MoveList.headerOpacity = (Math.abs(this.y)-(MoveList.datetimeContainerSize - 44 - 65))/65;
					MoveList.headerColor = parseInt(255-((Math.abs(this.y)-(MoveList.datetimeContainerSize - 44 - 65))/65)*255);
				}
			});
			
			myScroll.on('scroll', function(){
				MoveList.fixedMarginTop = -this.y + MoveList.firstMargin;
				
				MoveList.fixedListMarginTop = -this.y + MoveList.listImgMargin;
				
				if(this.y >= 0){
					MoveList.datetimeOpacity = 1;
				}else if(this.y < -65){
					MoveList.datetimeOpacity = 0;
				}else{
					MoveList.datetimeOpacity = 1-Math.abs(this.y)/65;
				}
				
				if(this.y > -(MoveList.datetimeContainerSize - 44 - 65)){
					MoveList.headerOpacity = 0;
					MoveList.headerColor = 255;
				}else if(this.y <= -(MoveList.datetimeContainerSize - 44)){
					MoveList.headerOpacity = 1;
					MoveList.headerColor = 0;
				}else{
					MoveList.headerOpacity = (Math.abs(this.y)-(MoveList.datetimeContainerSize - 44 - 65))/65;
					MoveList.headerColor = parseInt(255-((Math.abs(this.y)-(MoveList.datetimeContainerSize - 44 - 65))/65)*255);
				}
			});
			
			myScroll.on('scrollEnd', function(){
				MoveList.fixedMarginTop = -this.y + MoveList.firstMargin;
				
				MoveList.fixedListMarginTop = -this.y + MoveList.listImgMargin;
				
				if(this.y >= 0){
					MoveList.datetimeOpacity = 1;
				}else if(this.y < -65){
					MoveList.datetimeOpacity = 0;
				}else{
					MoveList.datetimeOpacity = 1-Math.abs(this.y)/65;
				}
				
				if(this.y > -(MoveList.datetimeContainerSize - 44 - 65)){
					MoveList.headerOpacity = 0;
					MoveList.headerColor = 255;
				}else if(this.y <= -(MoveList.datetimeContainerSize - 44)){
					MoveList.headerOpacity = 1;
					MoveList.headerColor = 0;
				}else{
					MoveList.headerOpacity = (Math.abs(this.y)-(MoveList.datetimeContainerSize - 44 - 65))/65;
					MoveList.headerColor = parseInt(255-((Math.abs(this.y)-(MoveList.datetimeContainerSize - 44 - 65))/65)*255);
				}
				
				cancelAnimationFrame(MoveList.id);
			});
			
			this.eventBind();
		},
		eventBind: function(){
			this.target.addEventListener('touchstart', this.touchstart.bind(this));
			this.target.addEventListener('touchmove', this.touchmove.bind(this));
			this.target.addEventListener('touchend', this.touchend.bind(this));
		},
		touchstart: function(e){
			e.preventDefault();
		},
		touchmove: function(e){
			e.preventDefault();
		},
		touchend: function(e){
			e.preventDefault();
		},
		setBlur: function(){
			$('.datetime img').css('margin-top', MoveList.fixedMarginTop+'px');
			
			$('.list-lists img').css('margin-top', MoveList.fixedListMarginTop+'px');
			
			$('.datetime').css('opacity', MoveList.datetimeOpacity);
			
			$('#header').css('background-color', 'rgba(255, 255, 255, '+MoveList.headerOpacity+')');
			
			$('.header-logo span').css('color', 'rgb('+MoveList.headerColor+', '+MoveList.headerColor+', '+MoveList.headerColor+')');
			this.id = requestAnimationFrame(MoveList.setBlur.bind(MoveList));
		},
		boundaryCheck: function(){
//			스크롤 개선때 사용할 것. 세부 내용은 수정 예정
			var minimumTop = -$('.issue-container').length*65 + $(window).height()*0.4;
			var curTop = parseFloat($('.list-move-container')[0].style.top);
			if(curTop < minimumTop){
				$('.list-move-container')[0].style.top = minimumTop + 'px';
			}
			if(curTop > 0){
				$('.list-move-container')[0].style.top = '0px';
			}
		},
};

function setPos(){
	var datetimeContainerSize = $(window).height()*0.6;
	MoveList.datetimeContainerSize = datetimeContainerSize;
	var datetimeHeight = 80;
	$('.list-date-container')[0].style.height = datetimeContainerSize + 'px';
	
	$('.datetime img')[0].style.marginTop= -(datetimeContainerSize - datetimeHeight +1)+'px';
	MoveList.firstMargin = -(datetimeContainerSize - datetimeHeight +1);
	
	$('.list-lists img')[0].style.marginTop = -datetimeContainerSize +'px';
	MoveList.listImgMargin = -datetimeContainerSize;
}
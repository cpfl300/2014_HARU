window.addEventListener('load', list_move_init);
document.addEventListener('DOMContentLoaded', setPos);
function list_move_init(){
	MoveList.init($('.list-move-container'));
	
}

var MoveList = {
		init: function(target){
			this.fixedMarginTop = this.firstMargin;
			this.id;
			
			this.target = target[0];
			myScroll = new IScroll('.list-background', { probeType: 3, mouseWheel: true });
			
			myScroll.on('scrollStart', function(){
				MoveList.id = requestAnimationFrame(MoveList.setBlur);
				MoveList.fixedMarginTop = -this.y + MoveList.firstMargin;
			});
			
			myScroll.on('scroll', function(){
				MoveList.fixedMarginTop = -this.y + MoveList.firstMargin;
			});
			
			myScroll.on('scrollEnd', function(){
				MoveList.fixedMarginTop = -this.y + MoveList.firstMargin;
				cancelAnimationFrame(MoveList.id);
			});
			
			
//			myScroll.on('scroll', function(){
//				console.log("SCROLL이벤트 먹기는 함" + this.y);
////				var matrix = $('.list-move-container').css('-webkit-transform');
////				var splitMatrix = matrix.split(", ");
////				var curPos = parseFloat(splitMatrix[splitMatrix.length-1].split(")")[0]);
////				
////				var fixedMarginTop = -curPos - 305 + 'px';
////				console.log("curPos: " + curPos);
////				console.log("fixedMarginTop: " + fixedMarginTop);
////				
////				$('.datetime img').css('margin-top', fixedMarginTop);
////				console.log("fixed");
//			});
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
		animate: function(){
			this.clock.style.opacity = this.clockOpacity;
			this.header.style.backgroundColor = "rgba(255, 255, 255, "+this.headerOpacity+")";
			this.id = requestAnimationFrame(this.animate.bind(this));
		},
		setBlur: function(){
			$('.datetime img').css('margin-top', MoveList.fixedMarginTop+'px');
			this.id = requestAnimationFrame(MoveList.setBlur.bind(MoveList));
		},
		boundaryCheck: function(){
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
	var datetimeHeight = 80;
	$('.list-date-container')[0].style.height = datetimeContainerSize + 'px';
	
//	var datetimePos = $('.datetime').offset().top;
	$('.datetime img')[0].style.marginTop= -(datetimeContainerSize - datetimeHeight +1)+'px';
	MoveList.firstMargin = -(datetimeContainerSize - datetimeHeight +1);
}
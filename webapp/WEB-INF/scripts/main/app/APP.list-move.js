window.addEventListener('load', list_move_init);
document.addEventListener('DOMContentLoaded', setPos);
function list_move_init(){
	MoveList.init($('.list-move-container'));
	
}

var MoveList = {
		init: function(target){
			this.target = target[0];
			var myScroll = new IScroll('.list-background', { mouseWheel: true });
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
	$('.list-date-container')[0].style.height = $(window).height()*0.6 + 'px';
	
	var datetimePos = $('.datetime').offset().top;
	$('.datetime img')[0].style.marginTop= -datetimePos+'px';
}
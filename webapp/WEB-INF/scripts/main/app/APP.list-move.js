window.addEventListener('load', list_move_init);

function list_move_init(){
	var willMoved = document.querySelector('.list-move-container');
	MoveList.init(willMoved);
}

var MoveList = {
		init: function(target){
			this.target = target;
			this.curMarginTop = 0;
			this.target.style.webkitTransform = 'translate3d(0, '+ this.curMarginTop +'px, 0)';
			this.eventBind();
		},
		eventBind: function(){
			this.target.addEventListener('touchstart', this.touchstart.bind(this));
			this.target.addEventListener('touchmove', this.touchmove.bind(this));
			this.target.addEventListener('touchend', this.touchend.bind(this));
		},
		touchstart: function(e){
			e.preventDefault();
			this.curPoint = e.changedTouches[0].pageY;
			this.id = requestAnimationFrame(this.animate.bind(this));
		},
		touchmove: function(e){
			e.preventDefault();
			var moveDistance = this.curPoint - e.changedTouches[0].pageY;
			this.curMarginTop -= moveDistance;
			this.curPoint = e.changedTouches[0].pageY;
		},
		touchend: function(e){
			e.preventDefault();
			cancelAnimationFrame(this.id);
		},
		animate: function(){
			this.target.style.webkitTransform = 'translate3d(0, '+ this.curMarginTop +'px, 0)';
			this.id = requestAnimationFrame(this.animate.bind(this));
		}
}
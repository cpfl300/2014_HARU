window.addEventListener('load', list_move_init);

function list_move_init(){
	var willMoved = document.querySelector('.list-move-container');
	MoveList.init(willMoved);
}

var MoveList = {
		init: function(target){
			this.target = target;
			this.curTop = 0;
			this.target.style.webkitTransform = 'translate3d(0, '+ this.curTop +'px, 0)';
			this.eventBind();
			
//			clock
			this.clock = document.querySelector('.datetime');
			this.clockOpacity = 1;
			this.clock.style.opacity = 1;
			
//			header background
			this.header = document.querySelector('#header');
			this.header.style.backgroundColor = "rgba(255, 255, 255, 0)";
			this.headerOpacity = parseInt(this.header.style.backgroundColor.split(", ")[3].split(")")[0]);
			
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
			this.curTop -= moveDistance;
			this.curPoint = e.changedTouches[0].pageY;
			
			this.clockOpacity -= moveDistance/100;
			
			if(-320 <= parseInt(this.curTop) && parseInt(this.curTop) <= -240){
				this.headerOpacity += moveDistance*0.01089;
			}
			
			
		},
		touchend: function(e){
			e.preventDefault();
			cancelAnimationFrame(this.id);

			var minimumMargin = -(document.querySelector('.list-lists').children.length * 65) + (65*3);
			if(this.curTop > 0){
				this.curTop = 0;
				this.clockOpacity = 1;
				
				this.headerOpacity = 0;
			}
			if(this.curTop < minimumMargin){
				this.curTop = minimumMargin;
				this.clockOpacity = 1 + (minimumMargin/100);
				
				this.headerOpacity = 0.894118;
			}
			
			this.target.style.webkitTransform = 'translate3d(0, '+ this.curTop +'px, 0)';
			this.clock.style.opacity = this.clockOpacity;
			
		},
		animate: function(){
			this.target.style.webkitTransform = 'translate3d(0, '+ this.curTop +'px, 0)';
			this.clock.style.opacity = this.clockOpacity;
			
			this.header.style.backgroundColor = "rgba(255, 255, 255, "+this.headerOpacity+")";
			this.id = requestAnimationFrame(this.animate.bind(this));
		}
}
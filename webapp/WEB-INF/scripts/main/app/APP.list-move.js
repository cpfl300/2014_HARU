window.addEventListener('DOMContentLoaded', list_move_init);

function list_move_init(){
	document.addEventListener('touchmove', function (e) { e.preventDefault();}, false);

//	cell단위로 움직일 수 있게 함
	$('.movable').pep({
		useCSSTranslation: false,
		axis: 'y'
	});
//	issue의 첫번째 cell 위치 조정(library적용으로 position: absolute가 되면서 생기는 문제)
	$('.issue-container')[0].style.top = '384px';
	
//	초기화면 정렬(issue 첫번째 cell을 기준으로 정렬)
	arrange($('.issue-container')[0]);
	
}

function arrange(target){
	
	var below = target;
	var targetTop = parseInt(target.style.top);
	var i = 1;
	while(below.nextElementSibling){
		below.nextElementSibling.style.top = targetTop + 65*i + 'px';
		i++;
		below = below.nextElementSibling;
	}
	
	var j = -1;
	while(target.previousElementSibling){
		target.previousElementSibling.style.top = targetTop + 65*j +'px';
		j--;
		target = target.previousElementSibling;
	}
	
//	684는 초기에 list첫번째에 부여한 top값
	$('.movable')[0].style.top = targetTop-384 +'px';
}

/*
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
			
//			logo font-color
			this.logo = document.querySelector('.logo');
			this.logo.style.color = "rgb(255, 255, 255)";
			this.logoColor = 255;
			
			
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
				this.logoColor -= moveDistance*3.17;
				console.log(this.logoColor);
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
				this.logoColor = 255;
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
			
			this.logo.style.color = "rgb("+parseInt(this.logoColor)+", "+parseInt(this.logoColor)+", "+parseInt(this.logoColor)+")";
			this.id = requestAnimationFrame(this.animate.bind(this));
		}
}
*/
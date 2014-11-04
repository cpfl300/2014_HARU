/*
 * 사용법: Flick.flicking(target, flickLength, toRightCallback, toLeftCallback);
 * 
 * param: 
 * 
 * target(flicking 이벤트를 발생하게 만들 element)
 * flickLength(flicking 이벤트를 발생시킬 최소 거)
 * toRightCallback(오른쪽으로 넘기려는 flicking 이벤트 발생 시 적용시킬 콜백함수)
 * toLeftCallback(왼쪽으로 넘기려는 flicking 이벤트 발생 시 적용시킬 콜백함수)
 * 
 */

var Flick = {
	flicking : function(target, flickLength, toRightCallback, toLeftCallback){
		this.startPoint = null;
		this.flickLength = flickLength;

		this.toRightCallback = toRightCallback;
		this.toLeftCallback = toLeftCallback;
		
		target.addEventListener('touchstart', this.touchstart.bind(this));
		target.addEventListener('touchend', this.touchend.bind(this));
	},

	checkFlick : function(string){
		if(string == "right"){
			if(this.toRightCallback != null){
				this.toRightCallback();
			}
		} else{
			if(this.toLeftCallback != null){
				this.toLeftCallback();
			}
		}
	},
	
	touchstart : function(e){
		this.startPoint = e.changedTouches[0].pageX;
	},
	
	touchend : function(e){
		var endPoint = e.changedTouches[0].pageX;
		if(Math.abs(this.startPoint-endPoint) > this.flickLength){
			// flick인 경우
			if(this.startPoint > endPoint){
				this.checkFlick("right");
			} else {
				this.checkFlick("left");
			}
		}

	}

}
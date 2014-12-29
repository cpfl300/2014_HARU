$(window).bind('pageshow', list_move_init);
document.addEventListener('DOMContentLoaded', setPos);

function list_move_init(){
	MoveList.init($('.list-move-container'));
	LocalStorage.run();
	Timer.init();
}

var MoveList = {
		init: function(target){
			this.fixedMarginTop = this.firstMargin;
			this.fixedListMarginTop = this.listImgMargin;
			this.id;
			this.datetimeOpacity;
			this.headerOpacity;
			this.headerColor;
			this.top;
			
			this.target = target[0];
			myScroll = new IScroll('.list-background', { probeType: 3, mouseWheel: false, click: true});
			
			myScroll.on('scrollStart', function(){
				MoveList.id = requestAnimationFrame(MoveList.setBlur);
				MoveList.setImgMargin(this.y);
				
				MoveList.setDatetime(this.y);
				MoveList.setHeader(this.y);
			});
			
			myScroll.on('scroll', function(){
				MoveList.setImgMargin(this.y);
				
				MoveList.setDatetime(this.y);
				MoveList.setHeader(this.y);
			});
			
			myScroll.on('scrollEnd', function(){
				MoveList.setImgMargin(this.y);
				
				MoveList.setDatetime(this.y);
				MoveList.setHeader(this.y);
				
				cancelAnimationFrame(MoveList.id);
			});
			
		},
		setBlur: function(){
//			 $('.datetime img').css('margin-top', MoveList.fixedMarginTop+'px');
//			 $('.list-lists img').css('margin-top', MoveList.fixedListMarginTop+'px');
			
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
		setDatetime: function(yPos){
			if(yPos >= 0){
				MoveList.datetimeOpacity = 1;
			}else if(yPos < -65){
				MoveList.datetimeOpacity = 0;
			}else{
				MoveList.datetimeOpacity = 1-Math.abs(yPos)/65;
			}
		},
		setHeader: function(yPos){
			if(yPos > -(MoveList.datetimeContainerSize - 44 - 65)){
				MoveList.headerOpacity = 0;
				MoveList.headerColor = 255;
			}else if(yPos <= -(MoveList.datetimeContainerSize - 44)){
				MoveList.headerOpacity = 1;
				MoveList.headerColor = 0;
			}else{
				MoveList.headerOpacity = (Math.abs(yPos)-(MoveList.datetimeContainerSize - 44 - 65))/65;
				MoveList.headerColor = parseInt(255-((Math.abs(yPos)-(MoveList.datetimeContainerSize - 44 - 65))/65)*255);
			}
		},
		setImgMargin: function(yPos){
			var reverseY = -yPos;
//			MoveList.fixedMarginTop = -yPos + MoveList.firstMargin;
			//$('.datetime img')[0].style.transform = 'translate(' + 0 + 'px,' + reverseY + 'px) translateZ(0)';
			$('.datetime img')[0].style.webkitTransform = 'translate(' + 0 + 'px,' + reverseY + 'px) translateZ(0)';
//			MoveList.fixedListMarginTop = -yPos + MoveList.listImgMargin;
//			$('.list-lists img')[0].style.transform = 'translate(' + 0 + 'px,' + reverseY + 'px) translateZ(0)';
			$('.list-lists img')[0].style.webkitTransform = 'translate(' + 0 + 'px,' + reverseY + 'px) translateZ(0)';
		}
};


var LocalStorage = {
	run: function(){
		this.init();
		this.setScrollPos();
		$('a').bind('click', this.setCurPos.bind(this));
	},
	init: function(){
		if(localStorage.getItem("haru") == null){
			localStorage.setItem('pos', 0);
			var tmpDic = {};
			for(var i = 0 ; i < $('.issue-container').length; i++){
				var key = $('.issue-container')[i].dataset.date;
				tmpDic[key] = 0;
			}
			localStorage.setItem("haru", JSON.stringify(tmpDic));
		}
		
		this.applyList();
	},
	applyList: function(){
		var savedData = JSON.parse(localStorage.getItem("haru"));
		var containKeys = [];
		for(var i = 0 ; i < $('.issue-container').length; i++){
			var target = $('.issue-container')[i];
			var key = target.dataset.date;
			containKeys.push(key);
			if(typeof savedData[key] == 'undefined'){
				savedData[key] = 0;
			}else if(savedData[key] == 2){
//				애니메이션 div를 화면에 그려놓음
				var copy = target.cloneNode();
				copy.className = "animation-temp-div";
				var divWidth = $(window).width() + 65;
				copy.style.cssText = "left:"+(-divWidth)+"px; width:" +divWidth+"px;";
				target.insertAdjacentElement('beforebegin', copy);
				
				setTimeout(function(target){
					copy.style.left = "0px";
					copy.addEventListener("transitionend", function(target){
						$(target).removeClass('just-read');
						$(target).addClass('tran');
						copy.nextElementSibling.style.backgroundColor = "rgba(207, 255, 149, 0.6)";
						copy.parentNode.removeChild(copy);
						$(target).on('transitionend', function(target){
							$(target).removeClass('tran');
						}.bind(this, target))
					}.bind(this, target), false);
				}.bind(this, target), 500);
//				key를 1로 변경
				savedData[key] = 1;
			}else if (savedData[key] == 1){
				$(target).removeClass('just-read');
				target.style.backgroundColor = "rgba(207, 255, 149, 0.6)";
				
			}else if (savedData[key] == 3){
				var t = $(target);
				if(t.hasClass('tran')){
					t.toggleClass('tran');
				}
				t.toggleClass('just-read');
			}else if(savedData[key] == 4){
				var t = $(target);
				t.addClass('tran');
				t.addClass('just-read');
				setTimeout(function(key){
					savedData[key] = 3;
					localStorage.setItem("haru", JSON.stringify(savedData));
				}.bind(this, key), 300);
			}else if(savedData[key] == 5){
				var t = $(target);
				t.toggleClass('just-read');
				//2번인 경우
//				애니메이션 div를 화면에 그려놓음
				var copy = target.cloneNode();
				copy.className = "animation-temp-div";
				var divWidth = $(window).width() + 65;
				copy.style.cssText = "left:"+(-divWidth)+"px; width:" +divWidth+"px;";
				target.insertAdjacentElement('beforebegin', copy);
				
				setTimeout(function(target){
					copy.style.left = "0px";
					copy.addEventListener("transitionend", function(target){
						$(target).removeClass('just-read');
						$(target).addClass('tran');
						copy.nextElementSibling.style.backgroundColor = "rgba(207, 255, 149, 0.6)";
						copy.parentNode.removeChild(copy);
						$(target).on('transitionend', function(target){
							$(target).removeClass('tran');
						}.bind(this, target))
					}.bind(this, target), false);
				}.bind(this, target), 500);
//				key를 1로 변경
				savedData[key] = 1;
			}
		}
		
		var allKesy = Object.keys(savedData);
		var deleteKeys = allKesy.filter(function(el){
			return containKeys.indexOf( el ) < 0;
		});
		
		for(var i = 0; i < deleteKeys.length; i++){
			delete savedData[deleteKeys[i]];
		}
		
		localStorage.setItem("haru", JSON.stringify(savedData));
	},
	setScrollPos: function(){
		var pos = localStorage.getItem('pos');
		if(pos == null){
		}else{
			pos = parseInt(pos);
		}
		
		myScroll.y = pos;
		$('.list-move-container').css('transform','matrix(1, 0, 0, 1, 0, '+pos+')');
		
		MoveList.setDatetime(pos);
		MoveList.setHeader(pos);
		MoveList.setImgMargin(pos);
		
		MoveList.setBlur();
	},
	setCurPos: function(){
		var matrixData = $('.list-move-container').css('transform').split(",");
		var curPos = parseInt(matrixData[matrixData.length-1]);
		localStorage.setItem('pos', curPos);
	}
}


var Timer = {
	init: function(){
		var date = new Date();
		this.today = date.getFullYear()+"."+(date.getMonth()+1)+"."+date.getDate();
		$('.datetime p:last-child')[0].textContent = this.today;
		$('.datetime').bind('click', this.clickFunc.bind(this));
	},
	clickFunc: function(e){
		$('.datetime').unbind('click');
		if(e.target.textContent != this.today) return;
		this.controlTimer();
	},
	controlTimer: function(){
		var time = this.getRemainTime();
		this.saveTimes(time);
		setTimeout(this.drawClock.bind(this,5), 1000);
	},
	getRemainTime: function(){
		var curDT = new Date();
		var nextDT = new Date(curDT);
		nextDT.setMinutes(0);
		nextDT.setSeconds(0);

		if(6 <= curDT.getHours() && curDT.getHours() < 18){
			nextDT.setHours(18);
		} else if(18 <= curDT.getHours()){
			nextDT.setDate(curDT.getDate()+1);
			nextDT.setHours(6);
		} else {
			nextDT.setHours(6);
		}

		return (nextDT - curDT)/1000;
	},
	saveTimes: function(time){
		this.sec = time%60;
		time -= this.sec;
		time /= 60;
		
		this.min = time%60;
		time -= this.min;
		time /= 60;
		
		this.hour = time;
	},
	drawClock: function(num){
		if(num == 0) {
			$('.datetime p:last-child')[0].textContent = this.today;
			$('.datetime').bind('click', this.clickFunc.bind(Timer));
			return;
		}
		var hour = this.hour;
		var min = this.min;
		var sec = this.sec;
		if(hour < 10){
			hour = "0" + hour;
		}
		if(min < 10){
			min = "0" + min;
		}
		if(sec < 10){
			sec = "0" + sec;
		}
		var timer = hour+":"+min+":"+sec;
		$('.datetime p:last-child')[0].textContent = timer;
		this.reduceSec();
		num--;
		setTimeout(this.drawClock.bind(this, num), 1000);
	},
	reduceSec: function(){
		var time = this.hour*3600 + this.min*60 + this.sec -1;
		this.saveTimes(time);
	}
};

function setPos(fm){
	var datetimeContainerSize = $(window).height()*0.6;
	MoveList.datetimeContainerSize = datetimeContainerSize;
	var datetimeHeight = 80;
	$('.list-date-container')[0].style.height = datetimeContainerSize + 'px';
	
	$('.datetime img')[0].style.marginTop= -(datetimeContainerSize - datetimeHeight +1)+'px';
	MoveList.firstMargin = -(datetimeContainerSize - datetimeHeight +1);
	
	$('.list-lists img')[0].style.marginTop = -datetimeContainerSize +'px';
	MoveList.listImgMargin = -datetimeContainerSize;
}


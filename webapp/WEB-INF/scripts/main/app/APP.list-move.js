//window.addEventListener('load', list_move_init);
$(window).bind('pageshow', list_move_init);
//$(window).bind('beforeunload', function(){
//	localStorage.setItem('pos', 0);
//});
document.addEventListener('DOMContentLoaded', setPos);

function list_move_init(){
	MoveList.init($('.list-move-container'));
	LocalStorage.run();
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
			MoveList.fixedMarginTop = -yPos + MoveList.firstMargin;
			MoveList.fixedListMarginTop = -yPos + MoveList.listImgMargin;
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
//				animation effect
//				key를 1로 변경
				savedData[key] = 1;
			}else if (savedData[key] == 1){
				target.style.backgroundColor = "rgba(207, 255, 149, 0.6)";
			}
		}
		
//		localStorage에서 지워야할 것 들의 key들을 찾아 냄
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
			pos = 0;
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


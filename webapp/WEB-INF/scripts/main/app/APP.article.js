
window.addEventListener('DOMContentLoaded', init);

function init(){
	ScrollCheck.init();
}

var ScrollCheck = {
	init: function(){
		this.maxScrollPos = ($(document).height() - $(window).height()) - 150;
		this.eventBind();
	},
	eventBind: function(){
		
		$('body')[0].addEventListener('touchmove', this.checkScrollPos.bind(this));
		$('body')[0].addEventListener('touchend', function(){
			setTimeout(this.delayDraw.bind(this, 5), 200);
		}.bind(this));
	},
	delayDraw: function(num){
		if(num >= 1){
			this.checkScrollPos();
			this.delayDraw(--num);
		}
	},
	checkScrollPos: function(){
		if($(document).scrollTop() >= this.maxScrollPos){
			var localData = JSON.parse(localStorage.getItem('haru'));
			
			//var Akey = document.URL.split("date/")[1].split("/article/");
			var Akey = document.URL.split("articles/")[1].split("/");
			var key = Akey.toString().replace(',', '');
			localData[key] = 2;
			
			localStorage.setItem('haru', JSON.stringify(localData));
		}
	}
};
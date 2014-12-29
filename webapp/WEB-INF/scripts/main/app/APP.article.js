
window.addEventListener('DOMContentLoaded', init);

function init(){
	ScrollCheck.init();
}

var ScrollCheck = {
	init: function(){
		this.maxScrollPos = ($(document).height() - $(window).height()) - 150;
		this.eventBind();
		
		var Akey = document.URL.split("articles/")[1].split("/");
		var key = Akey.toString().replace(',', '');
		var localData = JSON.parse(localStorage.getItem('haru'));
		if(localData[key] == 0){
			localData[key] = 4;
		}
		localStorage.setItem('haru', JSON.stringify(localData));
	},
	eventBind: function(){
		
		$('.article-content').on('touchmove', this.checkScrollPos.bind(this));
		$('.article-content').on('touchend', function(){
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

			if(localData[key] == 4){
				localData[key] = 2;
			}
			
			if(localData[key] == 3){
				localData[key] = 5;
			}
			
			localStorage.setItem('haru', JSON.stringify(localData));
		}
	}
};

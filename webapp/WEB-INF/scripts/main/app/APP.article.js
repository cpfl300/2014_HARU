
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
		$('body')[0].addEventListener('touchend', this.checkScrollPos.bind(this));
	},
	checkScrollPos: function(){
		if($(document).scrollTop() >= this.maxScrollPos){
			var localData = JSON.parse(localStorage.getItem('haru'));

			var Akey = document.URL.split("date/")[1].split("/article/");
			var key = Akey.toString().replace(',', '');
			localData[key] = 2;
			
			localStorage.setItem('haru', JSON.stringify(localData));
		}
	}
};
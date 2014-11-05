
window.addEventListener('DOMContentLoaded', init);

function init(){
	var container = document.querySelector('#container');
	Flick.flicking(container, 80, toggleClass.bind(this, "article-container", "flipped"), null);
	
}

//flicking 인식모듈 test 
function changeLeftPos(ele, movePos){
	var curPos = parseInt(ele.style.left);
	if(isNaN(curPos)) curPos = 0;
	ele.style.left = (curPos + movePos) + "px";
}

function toggleClass(className, toggleClass){
	$('.'+className).toggleClass(toggleClass);
}
//flicking 인식모듈 test

window.addEventListener('DOMContentLoaded', init);

function init(){
	var container = document.querySelector('#container');
	Flick.flicking(container, 80, changeLeftPos.bind(this, container.firstElementChild, -window.innerWidth), changeLeftPos.bind(this, container.firstElementChild, window.innerWidth));

}

function changeLeftPos(ele, movePos){
	var curPos = parseInt(ele.style.left);
	if(isNaN(curPos)) curPos = 0;
	ele.style.left = (curPos + movePos) + "px";
}

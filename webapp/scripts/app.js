window.addEventListener('DOMContentLoaded', init);

function init(){
	var sections = document.querySelectorAll('.section');
	var lastSection = sections[sections.length-1];
	lastSection.style.marginBottom = "50px";
	var firstSection = sections[0];
	firstSection.style.marginTop = "50px";
}
(function(window) {
	'use strict';

	var document = window.document;
	var console = window.console;

	var MAYFLY = window.CAGE || {};
	MAYFLY.highlight = MAYFLY.highlight || {};
	
	function Highlight() {
		
	}
	
	Highlight.prototype
	
	
	MAYFLY.highlight = Highlight;
	
	if (typeof module !== 'undefined' && module.exports) {
		module.exports = MAYFLY;
	} else {
		window.MAYFLY = MAYFLY;
	}    	

}(this));
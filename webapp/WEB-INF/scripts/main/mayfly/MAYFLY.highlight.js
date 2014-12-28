(function(window) {
	'use strict';

	var document = window.document;
	var console = window.console;

	var MAYFLY = window.CAGE || {};
	MAYFLY.highlight = MAYFLY.highlight || {};
	
	/*
	 * @Param
	 *   obj.el = targetEl
	 */
	function Highlight(obj) {
		
	}
	
	Highlight.prototype
	
	
	MAYFLY.highlight = Highlight;
	
	if (typeof module !== 'undefined' && module.exports) {
		module.exports = MAYFLY;
	} else {
		window.MAYFLY = MAYFLY;
	}    	

}(this));
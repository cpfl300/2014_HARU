(function(window) {
	'use strict';
	
	var document = window.document;
	var console = window.console;
	var CAGE = window.CAGE || {};
		CAGE.event = CAGE.event || {};
		CAGE.event.emitter = CAGE.event.emitter || {};
	
		
	function EventEmitter() {
		this.eventHandlers = [];
	}
	
	EventEmitter.prototype.on = function(fn) {
		this.events.push(fn);
	};
	
	EventEmitter.prototype.off = function(fn) {
		var newEventHandlers = [];
		for (var i=0, len=this.eventHandlers.length; i<len; ++i) {
			var curEvent = this.eventHandlers[i];
			if (curEvnet !== fn) {
				newEventHandlers.push(curEvnet);
			}
		}
		this.eventHandlers = newEventHandlers;
	};
	
	EventEmitter.prototype.trigger = function() {

		for (var i=0, len=this.eventHandlers.length; i<len; ++i) {
			this.eventHandlers[i](arguments);
		}
	};
	
	CAGE.event.emitter = EventEmitter;

	// 글로벌 객체에 모듈을 프로퍼티로 등록한다.
	if (typeof module !== 'undefined' && module.exports) {
		module.exports = CAGE;
		// browser export
	} else {
		window.CAGE = CAGE;
	}    	

}(this));
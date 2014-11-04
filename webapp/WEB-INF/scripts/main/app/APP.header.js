(function() {	
	'use strict';
	// 자주 사용하는 글로벌 객체 레퍼런스 확보
	var document = window.document;
	var console = window.console;

	// 사용할 네임 스페이스 확보	
	var APP = window.APP || {};
	APP.header = APP.header || {};
	
	// jquery
	var $ = window.$;	
	
	var Header = {
		init: function() {
			this._changeStatus();
		},
		
		_changeStatus: function() {
			var doc = $(document);
			doc.scroll(function(evt) {
				evt.preventDefault();
				
				var scrollTop = doc.scrollTop();
				var issueHeight = $('.issue-container').height();
				var headerLogo = $('#header span');
				
				if (scrollTop >= issueHeight) {
					headerLogo.addClass('float');
				} else {
					headerLogo.removeClass('float');
				}
			});
		}
	};

	APP.header = Header;
	
	// 글로벌 객체에 모듈을 프로퍼티로 등록한다.
	if (typeof module !== 'undefined' && module.exports) {
		module.exports = APP;
	} else {
		window.APP = APP;
	}
})(this);
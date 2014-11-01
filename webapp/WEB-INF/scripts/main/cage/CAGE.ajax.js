(function(window) {
	'use strict';

	var document = window.document;
	var console = window.console;

	var CAGE = window.CAGE || {};
	CAGE.ajax = CAGE.ajax || {};
		
	// xmlHttp 객체 생성
	function _createRequest() {
		try {
			var request = new XMLHttpRequest();
		} catch (tryMS) {
			try {
				request = new ActiveXObject("Msxml2.XMLHTTP");
			} catch (otherMS) {
				try {
					request = new ActiveXObject("Microsoft.XMLHTTP");
				} catch (failed) {
					request = null;
				}
			}
		}
		return request;
	}
	
	// responseText의 마지막에 포함된 개행문자 제거
	function _removeNewLine(str) {
		if(str.substring(str.length - 1) === "\n") {
			return str.substring(0, str.length - 1);					
		}
		return str;
	}
	
	
	// error & success
	function _jsonHandler(evt, request, callback) {
		var responseText = _removeNewLine(request.responseText);
		var responseObj = JSON.parse(request.responseText);
		
		callback(responseObj);
	}
	
	// progress
	function _textHandler(evt, request, progress) {
		progress(request.responseText);
	}
	
	
	// request
	function _exec(config){
		if (url === undefined) {
			console.err("[XmlHttp] Unable to request - Undefined URL");
			return;
		}
		
		var method = config.method,
		headerObj = config.headers,
		url = config.url,
		
		data = config.data,
		isAsync = (config.isAsync === undefined)?true:config.isAsync,
		
		success = config.success,
		failure = config.failure,
		progress = config.progress,
		
		// create request
		var request = _createRequest();
		if (request == undefined) {
			console.err("[XmlHttp] Unable to create request");
			return;
		}
		
		// set up to request
		request.open(method, url, isAsync);
		if (headerObj !== undefined) {
			for (var header in headerObj) {
				var content = headerObj[header];
				request.setRequestHeader(header, content);
			}
		}
		
		// success
		if (success !== undefined){ 
			request.addEventListener("load", function(evt){
				_jsonHandler(evt, request, success);
			}, false);
		}
		
		// failure
		if (failure !== undefined) {
			request.addEventListener("error", function(evt){
				_jsonHandler(evt, request, failure);
			}, false);
		}
		
		// progress
		if (progress !== undefined) {
			request.addEventListener("progress", function(evt){
				_textHandler(evt, request, progress);
			}, false);
		}
		
		// send
		request.send(data);
	}

	// 공개 메서드 선언	
	function GET(config) {
		config.method = "GET";
		_exec(config);
	}
		
	function POST(config) {
		config.method = "POST";
		config.headers = {
			"Content-Type": "application/x-www-form-urlencoded"
		};
		_exec(config);
	}

	function PUT(config) {
		config.method = "PUT";
		config.headers = {
			"Content-Type": "application/x-www-form-urlencoded"
		};
		_exec(config);
	}
		
	function DELETE(config) {
		config.method = "DELETE";
		_exec(config);
	}
	
	// 공개 메서드 노출
	CAGE.ajax = {
		GET: GET,
		POST: POST,
		PUT: PUT,
		DELETE: DELETE
	};
	
	if (typeof module !== 'undefined' && module.exports) {
		module.exports = CAGE;
	} else {
		window.CAGE = CAGE;
	}    	

}(this));
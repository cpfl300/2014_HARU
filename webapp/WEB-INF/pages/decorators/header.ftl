<!DOCTYPE html>
<html>
<head>
	<title><sitemesh:write property='title' /></title>
	<sitemesh:write property='head' />
	
	<link rel="stylesheet" type="text/css" href="/stylesheets/header.css" />
</head>
<body>
	<div id="header">
		<h1 class="header-logo">
			<a href="/"><span  class="">HARU</span></a>
		</h1>
	</div>
	
	<div id="container">
		<sitemesh:write property='body' />
	</div>
	
	
	<#if Application.debugger == "on">
		<script type="text/javascript" src="/scripts/main/app/APP.header.js"></script>
	</#if>
	<script>
		window.addEventListener("DOMContentLoaded", function(evt){
			APP.header.init();
		}, false);
	</script>
</body>

</html>

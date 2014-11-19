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
			<a href="/"><span  class="logo">HARU</span></a>
		</h1>
	</div>
	
	<div id="container">
		<sitemesh:write property='body' />
	</div>
	
	<script type="text/javascript" src="/scripts/main/app/APP.header.js"></script>
<!-- 	<script>
		window.addEventListener("DOMContentLoaded", function(evt){
			APP.header.init();
		}, false);
	</script> -->
</body>

</html>
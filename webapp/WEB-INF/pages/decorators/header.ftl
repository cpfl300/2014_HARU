<!DOCTYPE html>
<html>
<head>
	<title><sitemesh:write property='title' /></title>
	<sitemesh:write property='head' />
	
	<link rel="stylesheet/less" type="text/css" href="/stylesheets/header.less" />
</head>
<body>
	<div id="header">
		<div class="header-back">
			<a href="#"><div class="header-back-img">
				<img src="/images/back-icon.png" alt="back"/>	
			</div></a>
		</div>
		<div class="header-logo">
			<a href="/"><div class="header-logo-img">
				<img src="/images/logo33.png" alt="haru"/>
			</div></a>
		</div>
		<div class="header-userinfo">
			<a href="#"><div class="header-userinfo-img">
				<img src="/images/flower.png" alt="anonymous"/>	
			</div></a>
		</div>
	</div>
	
	<div id="container">
		<sitemesh:write property='body' />
	</div>
</body>

</html>

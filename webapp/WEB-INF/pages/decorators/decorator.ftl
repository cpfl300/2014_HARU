<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0">
	
	<link rel="stylesheet/less" type="text/css" href="/stylesheets/index.less" />
	<title>haru: <sitemesh:write property='title' /></title>
	<sitemesh:write property='head' />
</head>

<body>
	<div id="container">
		<sitemesh:write property='body' />
	</div>
	
	<!-- angular.js -->
	<script type="text/javascript" src="/scripts/lib/angularjs/angular.min.js"></script>
	<!-- less -->
	<script src="//cdnjs.cloudflare.com/ajax/libs/less.js/2.0.0-b2/less.min.js"></script>
</body>
</html>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0">
	<title>haru: <sitemesh:write property='title' /></title>
	
	<!-- style -->
	<link rel="stylesheet/less" type="text/css" href="/stylesheets/base.less" />
	<link rel="stylesheet/less" type="text/css" href="/stylesheets/layout.less" />
	
	<sitemesh:write property='head' />
	
	<!-- less -->
	<!-- Dec7 14. 11. 1, less에서 css로 점차적 변경 후 less가 필요없어지면 아래 라이브러리 삭제예정 -->
	<script src="//cdnjs.cloudflare.com/ajax/libs/less.js/1.7.4/less.min.js"></script>
</head>

<body>
	<div class="wrapper">
		<sitemesh:write property='body' />
	</div>
</body>
</html>
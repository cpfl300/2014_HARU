<!DOCTYPE html>
<html>
<head>
<title>main view</title>
<link rel="stylesheet" type="text/css" href="/stylesheets/main.css" />
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="/scripts/main/cage/CAGE.iscroll.js"></script>
<script type="text/javascript" src="/scripts/main/app/APP.list-move.js"></script>
</head>
<body>
	<div class="list-background">
		<div class="list-move-container">
			<div class="list-date-container">
				<div class="datetime">
					<img src="http://cdn.wallwuzz.com/uploads/tokyo-wallpaper-photography-city-scape-cityscape-grayscale-styles-cityscapes-wallwuzz-hd-wallpaper-20342.jpeg"/>
					<p class="whiten"></p>
					<p>2014.11.11</p>
				</div>
			</div>
			<div class="list-lists">
				<#list haruHotissues as article>
				<div class="issue-container">
					<div class="issue">${article.hotissue}</div>
				</div>
				</#list>
			</div>
		</div>
	</div>
	<img
		src="http://cdn.wallwuzz.com/uploads/tokyo-wallpaper-photography-city-scape-cityscape-grayscale-styles-cityscapes-wallwuzz-hd-wallpaper-20342.jpeg" />
</body>
</html>
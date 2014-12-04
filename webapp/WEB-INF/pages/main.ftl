<!DOCTYPE html>
<html>
<head>
<title>main view</title>
<link rel="stylesheet" type="text/css" href="/stylesheets/main.css" />
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="/scripts/main/cage/CAGE.iscroll-prob.js"></script>
<script type="text/javascript" src="/scripts/main/app/APP.list-move.js"></script>
</head>
<body>
	<div class="list-background">
		<div class="list-move-container">
			<div class="list-date-container">
				<div class="datetime">
					<img src="http://1.bp.blogspot.com/-_eOqJw1i78U/T-nPui2ankI/AAAAAAAAAkk/vzNLfVgUWw4/s1600/dust-dirt-and-leaves-inkbluesky.png"/>
					<p class="whiten"></p>
					<p>2014.11.11</p>
				</div>
			</div>
			<div class="list-lists">
				<img src="http://1.bp.blogspot.com/-_eOqJw1i78U/T-nPui2ankI/AAAAAAAAAkk/vzNLfVgUWw4/s1600/dust-dirt-and-leaves-inkbluesky.png"/>
				<#list hotissues as hoissue>
						<div class="issue-container" data-date=${date}${hoissue.sequence}>
							<a href="/date/${date}/article/${hoissue.sequence}"><div class="issue">${hoissue.name}</a></div>
						</div>
				</#list>
			</div>
		</div>
	</div>
	<img
		src="http://1.bp.blogspot.com/-_eOqJw1i78U/T-nPui2ankI/AAAAAAAAAkk/vzNLfVgUWw4/s1600/dust-dirt-and-leaves-inkbluesky.png" />		
</body>
</html>
<!DOCTYPE html>
<html>
<head>
	<title>main view</title>
	<link rel="stylesheet" type="text/css" href="/stylesheets/main.css" />
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
	<script type="text/javascript" src="/scripts/main/cage/PEP.js"></script>
	<script type="text/javascript" src="/scripts/main/app/APP.list-move.js"></script>
</head>
	<body>
		<div class="list-background">
			<div class="list-move-container">
				<div class="list-date-container">
					<div class="datetime movable"> 2014.11.11 </div>
				</div>
				<div class="list-lists">
					<#list haruHotissues as article>
						<div class="issue-container movable">
						 	<div class="issue">
						 		${article.hotissue}
						 	</div>
						 </div>
					</#list>
				</div>
			</div>
		</div>
	</body>
</html>
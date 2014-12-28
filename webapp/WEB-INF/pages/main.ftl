<!DOCTYPE html>
<html>
<head>
	<title>를 보다</title>
	<link rel="stylesheet" type="text/css" href="/stylesheets/main.css" />
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
	<#if Application.debugger == "off">
		<script type="text/javascript" src="/scripts/min/cage/CAGE.min.js"></script>
		<script type="text/javascript" src="/scripts/min/app/APP.min.js"></script>
	<#elseif Application.debugger == "on">
		<script type="text/javascript" src="/scripts/main/cage/CAGE.iscroll-prob.js"></script>
		<script type="text/javascript" src="/scripts/main/app/APP.list-move.js"></script>
	</#if>

</head>
<body>
	<div class="list-background">
		<div class="list-move-container">
			<div class="list-date-container">
				<div class="datetime">
					<img src="${blurImgUrl}"/>
					<p class="whiten"></p>
					<p>날짜</p>
				</div>
			</div>
			<div class="list-lists">
				<img src="${blurImgUrl}"/>
				<#list hotissues as hoissue>
						<a href="/articles/${date}/${hoissue.sequence}">
							<div class="issue-container" data-date=${date}${hoissue.sequence}>
								<div class="issue">${hoissue.name}</div>
							</div>
						</a>
				</#list>
			</div>
		</div>
	</div>
	<img class="backImg" src="${imageUrl}" />		
</body>
</html>
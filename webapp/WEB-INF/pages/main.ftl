<!DOCTYPE html>
<html>
<head>
<title>main view</title>

</head>
	<body>
		<div class="list-background">
			<div class="list-move-container">
				<div class="list-date-container">
					<div class="datetime"></div>
				</div>
				<div class="list-lists">
					<#list haruHotissues as article>
						<div class="issue"> ${article.hotissue}</div>
					</#list>
				</div>
			</div>
		</div>
	</body>
</html>
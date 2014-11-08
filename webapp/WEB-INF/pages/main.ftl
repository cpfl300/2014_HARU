<!DOCTYPE html>
<html>
<head>
<title>main view</title>

</head>
<body>
	<div style="height:60px;"></div>
	<h1>main page issue</h1>
	<div class="issue-image">
		<img src="${imageUrl}" />
	</div>
	<div>
		<#list haruHotissues as article>
			${article.hotissue} <br>
		</#list>
	</div>
	
</body>

</html>
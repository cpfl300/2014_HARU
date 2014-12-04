<!DOCTYPE html>
<html>
<head>
	<title><sitemesh:write property='title' /></title>
	<sitemesh:write property='head' />
	
	<link rel="stylesheet" type="text/css" href="/stylesheets/article-format.css">
	
	<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
	<script type="text/javascript" src="/scripts/main/app/APP.article.js"></script>
</head>

<body>
	<div class="article-container">
		<div class="imgContainer">
			<img src="${imgUrl}" />
			<div class="blured-div">
				<img class="blur-img" src="${imgUrl}">
			</div>
			<div class="issue-container">
				<div class="section">${article.section.minor}</div>
				<div class="hot-issue">${article.hotissue.name}</div>
			</div>
		</div>
		<div class="meta-data">
			<!-- <div class="issue-container">
				<div class="section">정치</div>
				<div class="hot-issue">여당, 공무원연금법 개정안 제출</div>
			</div> -->
			<div class="subject-container">
				<div class="subject">
					<p>${article.title}</p>
				</div>
				<div class="write-time">
					<p>${article.date}</p>
				</div>
				<div class="publisher">
					<p>${article.journal.name}</p>
				</div>
			</div>
		</div>
		<div class="article-content">
			${article.content}
		</div>
	</div>
</body>

</html>
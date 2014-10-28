<!DOCTYPE html>
<html>
<head>
	<title>issue view</title>
	<link rel="stylesheet/less" type="text/css" href="/stylesheets/issue.less" />
</head>

<body>
<script type="text/template" id="article-card-template">
<div class="article-card">
	<div class="article-title-text">
	</div>
</div>
</script>

<script type="text/template" id="rotated-arficle-card-template">
<div class="article-card">
	<div class="article-source-text">
	</div>
</div>
</script>

<script type="text/template" id="issue-header-template">
<div class="issue-header">
	<div class="issue-header-photo">
		<div class="issue-header-title-blur">
			<div class="blured-image"></div>
		</div>
		<div class="issue-header-title-container">
			<div class="issue-read-complete-icon"></div>
			<div class="issue-header-title"><%= subject %></div>
		</div>
	</div>
</div>
</script>
	<div class="issue">
		<!-- header -->
		<div class="issue-header">
			<div class="issue-header-photo">
				<div class="issue-header-title-blur">
					<div class="blured-image"></div>
				</div>
				<div class="issue-header-title-container">
					<div class="issue-read-complete-icon"></div>
					<div class="issue-header-title">이슈 제목</div>
				</div>
			</div>
		</div>
		
		<!-- comment -->
		<div class="issue-comment">
			<div class="issue-comment-list">comment list</div>
			<div class="issue-comment-controller">
				<button class="left">왼쪽</button>
				<button class="right">오른쪽</button>
			</div>
			<div class="issue-comment-input">
				<form>
					<input type="text" />
					<button class="submit">입력</button>
				</form>
			</div>
		</div>
		
		<!-- articles -->
		<div class="issue-content">
			
		</div>
	</div>
</body>

</html>
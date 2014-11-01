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
			<div class="issue-header-photo"></div>
			<div class="issue-header-title">
				<div class="background">
					<div class="img blured-img"></div>
				</div>
				<div class="content">
					<div class="icon icon-read-complete"></div>
					<div class="title">에볼라 바이러스 확산</div>
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
			<!-- first -->
			<div class="article-card front">
				<div class="article-card-state article-card-front">
					<div class="container">
						<div class="title"><span>유엔 에볼라 대응단장, 발병지역 '정보부족' 호소</span></div>
					</div>
					
				</div>
				<div class="article-card-state article-card-back">
					<div class="container">
						<div class="publisher"><span>연합뉴스</span></div>
						<div class="section"><span>세계-미국/중남미</span></div>
					</div>
				</div>
			</div>
			
			<!-- second -->
			<div class="article-card front red">
				<div class="article-card-state article-card-front">
					<div class="container">
						<div class="title">
							<span>美 에볼라 ‘의무격리’ 주마다 제각각… 혼란 가중美 에볼라 ‘의무격리’ 주마다 제각각… 혼란 가중</span>
						</div>
					</div>
				</div>
				<div class="article-card-state article-card-back">
					<div class="container">
						<div class="publisher"><span>서울신문</span></div>
						<div class="section"><span>세계-미국/중남미</span></div>
					</div>
				</div>
			</div>
			
			<!-- third -->
			<div class="article-card back">
				<div class="article-card-state article-card-front">
					<div class="container">
						<div class="title"><span>“에볼라 구호 의료진은 인류애 위해 헌신한 사람들”… 반기문 총장 “의무격리 반대”</span></div>
					</div>
				</div>
				<div class="article-card-state article-card-back">
					<div class="container">
						<div class="publisher"><span>국민일보</span></div>
						<div class="section"><span>세계-중동/아프리카</span></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

</html>
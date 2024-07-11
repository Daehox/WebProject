<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>헤더부분</title>
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<style>

*{
 	font-family: "Noto Sans KR", sans-serif;
	list-style: none;
	text-decoration: none;
	border-collapse: collapse;
	margin: 0px;
	padding: 0px;
	color: black;
}

.intro_bg {
    background-image: url("resources/img/intro2.png");
    width: 100%;
    height: 699px;
    background-repeat: no-repeat;
    background-size: cover; 
    background-position: center; 
}


.title {
	text-align: center;
	display: flex;
	width: 1280px;
	margin: auto;
	height: 86px;
}

.title>h1 {
	display: flex;
	margin: auto;
	color: #fff;
	text-align: center;
	font-size: 30px;
}

.nav {
	display: flex;
	line-height: 60px;
    justify-content: flex-end; /* 오른쪽 정렬 */
	margin-right: 30px;
	
}

.nav li {
	margin-left: 50px;
}

.nav li a,
.nav li span {
	color: #fff;
	font-size: 18px;
	text-decoration: none;
	
}

.nav li form {
	display: inline-block;
	margin-left: 18px;
}

.nav li form input {
	background-color: transparent;
	color: #fff;
	border: none;
	font-size: 18px;
	cursor: pointer;
}
.intro_text{
  width:100%;
  margin:231px auto 231px auto;
  text-align: center;  
}

.intro_text > h1,
.intro_text > h4{
  color:#fff;
}
.contents{
  font-size: 20px;
  font-weight: lighter;
}

.main_text0{
    width: 100%;
    height: 100px;
    text-align: center;
	background-color: #fff;
    
}

.main_text0 > h1 {
    text-align: center;
    padding-top: 15px;
    font-weight: lighter;
    
}

.main_text0 > h4 {
    text-align: center;
    font-size: 15px;    
    font-weight: lighter;
    
}

</style>
</head>
<body>
	<div class="wrap">
		<div class="intro_bg">
			<div class="title">
				<h1>SAFeRain</h1>
			</div>
			<ul class="nav">
				<li><a href="#" title="About Us">About Us</a></li>
				<li><a href="#" title="Archiving">Archiving</a></li>
				<c:choose>
					<c:when test="${not empty sessionScope.loggedInUser}">
						<li><span>안녕하세요. ${sessionScope.loggedInUser}님</span>
							<form action="logout" method="post" class="logout-form">
								<input type="submit" value="로그아웃">
							</form>
						</li>
					</c:when>
					<c:otherwise>
						<li><a href="/project/login" title="로그인">로그인</a></li>
						<li><a href="/project/insert" title="회원가입">회원가입</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
			<div class ="intro_text">
			<h1>"침수 위험을 미리 예측하고 대비하세요"</h1>
			<h4 class = "contents">안전을 위해 지금 바로 침수 위험을 확인해보세요.</h4>
			</div>
		</div>
	</div>
	
	<div class = "main_text0">
	<h1>날씨 정보</h1>
	<h4 class = "cntents">매일 매일의 날씨 정보를 확인해보세요.</h4>
	</div>
</body>

</html>

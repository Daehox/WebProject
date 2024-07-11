<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>게시판</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
<style>
body {
	font-family: "Noto Sans KR", sans-serif;
	margin: 0;
	padding: 0;
}

.main-content {
	display: flex; 
	flex-direction: column;
	align-items: center;
    background: #F1F2F3 0% 0% no-repeat padding-box !important;
}

.board-search {
	margin: 20px auto;
	text-align: center;
}

.search-wrap {
	display: flex;
	justify-content: center;
}

.search-wrap input {
	border: 1px solid #dee2e6;
	border-radius: 1px 0 0 1px;
	padding: 5px;
	width: 250px;
}

.search-wrap button {
	border: 1px solid #dee2e6;
	border-radius: 0 1px 1px 0;
	background-color: #000;
	color: #fff;
	padding: 5px 10px;
	cursor: pointer;
	font-weight: lighter;
	
}

.search-wrap button:hover {
	background-color: #343a40;
}

.pagination {
	text-align: center;
	margin: 5px auto;
	display: flex;
	justify-content: center;
}

.pagination a, .pagination span {
	display: inline-block;
	padding: 3px 5px;
	margin: 0 2px;
	border: 1px solid #dee2e6;
	border-radius: 3px;
	background-color: white;
	color: #000;
	text-decoration: none;
}

.pagination a:hover {
	background-color: #e9ecef;
}

.pagination .current {
	background-color: #000;
	color: white;
	cursor: default;
} 

.new-post {
	text-align: center;
	margin: 5px auto;
}

.new-post a {
	padding: 7px 10px;
	background-color: #000;
	color: white;
	text-decoration: none;
	border-radius: 3px;
	display: inline-block;
	font-size: 12px;
	font-weight: lighter;
	
}

.new-post a:hover {
	background-color: #343a40;
}

.date {
	font-size: 12px; 
	color: #6c757d; 
}
</style>
</head>
<body>
	<div class="main-content">
		<div id="board-search">
			<form name="searchForm" action="main" method="get"
				class="search-wrap" onsubmit="return validateForm()">
				<input type="text" name="query" placeholder="검색어를 입력하세요">
				<button type="submit" class="btn btn-dark">검색</button>
			</form>
		</div>
		<c:import url="posts.jsp" />
		 <div class="pagination">
			<c:forEach var="pageNumber" items="${pageNumbers}">
				<c:choose>
					<c:when test="${pageNumber == currentPage}">
						<span class="current">${pageNumber}</span>
					</c:when>
					<c:otherwise>
						<a href="?page=${pageNumber}">${pageNumber}</a>
					</c:otherwise>
				</c:choose>
			</c:forEach> 
		</div>
		<!-- sessionScope 을 활용해 사용자가 로그인 되어있는지 아닌지 확인 --> 
		<c:if test="${not empty sessionScope.loggedInUser}">
			<div class="new-post">
				<a href="/project/write">새 글쓰기</a>
			</div>
		</c:if>
	</div>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
	<script>
		    function validateForm() {
        var x = document.forms["searchForm"]["query"].value;
        if (x == "") {
            alert("검색어를 입력하세요");
            return false;
        }
        // 검색 결과가 없을 때 메시지를 표시하는 함수
        function showNoResultsMessage() {
            var noResultsMsg = document.getElementById('noResultsMsg');
            if (noResultsMsg) {
                noResultsMsg.style.display = 'block';
            }
        }
    }</script>
</body>
</html>

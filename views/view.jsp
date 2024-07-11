<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 상세보기</title>
<style>
body {
	font-family: Arial, sans-serif; 
	line-height: 1.6; 
	margin: 30px; 
}

.container {
	width: 80%; 
	margin: 0 auto;
}

.post {
	padding: 20px; 
	border: 1px solid #ccc; 
	border-radius: 5px; 
	background-color: #f9f9f9; 
	margin-bottom: 20px; 
}

.post-title {
	font-size: 30px; 
	font-weight: bold; 
}

.post-date, .post-author {
	font-size: 14px; 
	color: #888; 
	margin-bottom: 10px; 
}

.post-content {
	margin: 20px 0; 
}

.actions {
	display: flex; 
	justify-content: flex-end; 
	gap: 5px; 
}

.actions a {
	text-decoration: none;
	padding: 8px 12px; 
	background-color: #000000; 
	color: white; 
	border-radius: 5px; 
	transition: background-color 0.3s;
}

.actions a:hover {
	background-color: #444444; 
}


.comment {
	padding: 15px; 
	border-bottom: 1px solid #ddd; 
}

.comment:last-child {
	border-bottom: none; 
}

.comment-date, .comment-author {
	display: flex; 
	font-size: 10px; 
	color: #888;
	margin-top: 10px; 
}

.pagination {
	text-align: center; 
	margin: 20px 0; 
}

.pagination a, .current-page {
	margin: 0 5px; 
	text-decoration: none; 
	padding: 8px 12px;
	border: 1px solid #ddd; 
	color: #555; 
}

.pagination a:hover {
	background-color: #ddd; 
}

.current-page {
	font-weight: bold; 
	background-color: #eee; 
}

.comment-submit {
	padding: 7px 7px; 
	background-color: #000000; 
	color: white; 
	border: none;
	border-radius: 5px; 
	cursor: pointer; 
	transition: background-color 0.3s; 

.comment-submit:hover {
	background-color: #000000; 
</style>
</head>
<body>
	<div class="container">
		<div class="post">
			<div class="post-title">${boardDto.title}</div>
			<div class="post-date">${boardDto.date}</div>
						<!-- 이미지 표시 -->
			<c:if test="${not empty boardDto.imagePath}">
				<div class="post-image">
            <img src="${pageContext.request.contextPath}/uploads/${boardDto.imagePath}" alt="게시물 이미지" style="max-width:70%;">
				</div>
			</c:if>
			<div class="post-content">${boardDto.content}</div>		
			<div class="post-author">작성자: ${boardDto.authorId}</div>
			<div class="actions">			
				<c:if
					test="${not empty sessionScope.loggedInUser && sessionScope.loggedInUser eq boardDto.authorId}">
					<a href="/project/edit/${boardDto.id}">수정</a>
					<a href="/project/delete/${boardDto.id}"
						onclick="return confirm('정말 삭제하시겠습니까?')">삭제</a>
				</c:if>
				<a href="/project/main">목록으로</a>
			</div>
		</div>
		<!-- 댓글 입력 폼 -->
		<form action="/project/comment/${boardDto.id}" method="post">
			<textarea name="comment" rows="4" cols="70"
				placeholder="댓글을 입력하세요..."></textarea>
			<br> <input type="submit" value="댓글 등록" class="comment-submit">
		</form>
		<!-- 댓글 목록 -->
		<div>
			<c:forEach var="comment" items="${comments}">
				<div class="comment">
					<p>${comment.content}</p>
					<p>
						<span class="comment-date">작성 시간: ${comment.date}</span>
					</p>
					<p>
						<span class="comment-author">작성자: ${comment.userId}</span>
					</p>
					<c:if
						test="${not empty sessionScope.loggedInUser && sessionScope.loggedInUser eq comment.userId}">
					</c:if>
				</div>
			</c:forEach>
		</div>
		<div class="pagination">
			<c:if test="${totalPages > 1}">
				<c:forEach var="pageNum" begin="1" end="${totalPages}">
					<c:choose>
						<c:when test="${pageNum == currentPage}">
							<span class="current-page">${pageNum}</span>
						</c:when>
						<c:otherwise>
							<a href="/project/view/${boardDto.id}?page=${pageNum}">${pageNum}</a>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</c:if>
		</div>
	</div>
</body>
</html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
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
.board-table > table {
	width: 100%;
	max-width: 700px;
	margin: 10px auto;
	border-collapse: collapse;	
}

.board-table a {
	color: #000;
	text-decoration: none;
	display: flex;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
	padding: 2px; 
	
}

.board-table a:hover {
	text-decoration: underline;
}

.board-table .inner-table td {
	border-bottom: 1px solid #dee2e6;
}

.board-table .inner-table tr:last-child td {
	border-bottom: none;
}
.inner-table {
	width: 100%;
	border-collapse: collapse;
	text-align: left;
	margin: 5px 60px; 
	display: inline-block;
	
}

.inner-table a {
	display: block; 
	text-align: left; 
	text-decoration: none; 
}

.board-table th, .board-table td {
	padding: 5px;
	font-size: 13px;
	text-align: center;
}
</style>
</head>
<div class="board-table">
	<c:choose>
		<c:when test="${empty posts}">
			<div class="no-results">검색 결과가 없습니다.</div>
		</c:when>
		<c:otherwise>
			<table>
				<tbody>
					<tr>
						<td>
							<table class="inner-table">
								<tbody>
									<c:forEach var="i" begin="0" end="4">
										<tr>
											<c:if test="${i < posts.size()}">
												<td><a href="/project/view/${posts[i].id}">
														${posts[i].title} <span
														class="date">  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatDate
																value="${posts[i].date}" pattern="yyyy.MM.dd" />
													</span>
												</a></td>
											</c:if>
											<c:if test="${i >= posts.size()}">
												<td>&nbsp;</td>
											</c:if>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</td>
						<td>
							<table class="inner-table">
								<tbody>
									<c:forEach var="i" begin="5" end="9">
										<tr>
											<c:if test="${i < posts.size()}">
												<td><a href="/project/view/${posts[i].id}">
														${posts[i].title} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span
														class="date"> <fmt:formatDate
																value="${posts[i].date}" pattern="yyyy.MM.dd" />
													</span>
												</a></td>
											</c:if>
											<c:if test="${i >= posts.size()}">
												<td>&nbsp;</td>
											</c:if>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</td>
					</tr>
				</tbody>
			</table>
		</c:otherwise>
	</c:choose>
</div>
</html>

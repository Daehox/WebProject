<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시판</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">

    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            margin: 0;
            padding: 0;
        }

        .main-content {
            width: 90%;
            max-width: 1200px;
            margin: 20px auto;
            padding: 20px;
            background-color: #fff;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
        }

        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }

        .post {
            border-bottom: 1px solid #ddd;
            padding: 10px 0;
        }

        .post:last-child {
            border-bottom: none;
        }

        .post-title {
            font-size: 18px;
            font-weight: bold;
            color: #333;
            margin-bottom: 5px;
            cursor: pointer;
        }

        .post-title:hover {
            color: #007bff;
        }

        .post-date {
            font-size: 12px;
            color: #888;
        }

        .post-content {
            color: #666;
            margin-top: 10px;
        }

        .pagination {
            text-align: center;
            margin-top: 20px;
        }

        .pagination a {
            color: #333;
            text-decoration: none;
            padding: 5px 10px;
            margin: 0 5px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        .pagination a:hover {
            background-color: #f0f0f0;
        }

        .pagination .current {
            font-weight: bold;
            background-color: #333;
            color: #fff;
            padding: 5px 10px;
            border-radius: 4px;
        }

        .new-post {
            text-align: center;
            margin-top: 20px;
        }

        .new-post a {
            display: inline-block;
            padding: 10px 20px;
            background-color: #333;
            color: #fff;
            text-decoration: none;
            border-radius: 4px;
        }

        .new-post a:hover {
            background-color: #555;
        }

        .alert-secondary {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .alert-danger {
            margin-top: 20px;
        }

        .table {
            margin-top: 20px;
        }

        .table th, .table td {
            text-align: center;
            vertical-align: middle;
        }

        .table td a {
            text-decoration: none;
            color: inherit;
        }

        .page-item.disabled .page-link {
            pointer-events: none;
            cursor: not-allowed;
        }

        .page-item.active .page-link {
            background-color: #0d6efd;
            border-color: #0d6efd;
        }
    </style>
</head>
<body>
    <div class="main-content">
        <h2>게시판</h2>
        
        <c:forEach var="post" items="${posts}" varStatus="loop">
            <div class="post">
                <div class="post-title" onclick="window.parent.location.href='/project/view/${post.id}'">${post.title}</div>
                <div class="post-date">${post.date}</div>
                <div class="post-content">${post.content}</div>
            </div>
        </c:forEach>
        
        <c:forEach begin="${posts.size() + 1}" end="5">
            <div class="post">
                <div class="post-title">&nbsp;</div>
                <div class="post-date">&nbsp;</div>
                <div class="post-content">&nbsp;</div>
            </div>
        </c:forEach>
        
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
        
        <c:if test="${not empty sessionScope.loggedInUser}">
            <div class="new-post">
                <a href="/project/write" target="_blank">새 글 쓰기</a>
            </div>
        </c:if>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>
<style>
    /* Reset CSS */
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }

    body {
        font-family: Arial, sans-serif;
        background-color: #f9f9f9;
        padding: 20px;
    }

    .container {
        max-width: 600px;
        margin: 50px auto;
        padding: 20px;
        background-color: #fff;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        border-radius: 10px;
    }

    h2 {
        text-align: center;
        color: #333;
        margin-bottom: 20px;
    }

    label {
        display: block;
        margin-bottom: 8px;
        font-weight: bold;
        color: #555;
    }

    input[type="text"], textarea {
        width: 100%;
        padding: 10px;
        margin-bottom: 20px;
        border: 1px solid #ddd;
        border-radius: 4px;
    }

    textarea {
        height: 200px;
        resize: vertical;
    }

    .button-group {
        display: flex;
        justify-content: space-between;
    }

    input[type="submit"], .button-group a {
        width: 48%;
        padding: 10px;
        background-color: #333;
        color: #fff;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        text-align: center;
        text-decoration: none;
        font-size: 16px;
    }

    input[type="submit"]:hover, .button-group a:hover {
        background-color: #555;
    }
</style>
</head>
<body>
    <div class="container">
        <h2>새 글 작성</h2>
        <form action="/project/write" method="post">
            <label for="title">제목:</label>
            <input type="text" id="title" name="title">
            <label for="content">내용:</label>
            <textarea id="content" name="content"></textarea>
            <div class="button-group">
                <input type="submit" value="작성">
                <a href="/project/main">목록으로</a>
            </div>
        </form>
    </div>
</body>
</html>

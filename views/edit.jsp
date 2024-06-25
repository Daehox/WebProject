<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 수정하기</title>
<style>
/* 스타일 정의 */
.container {
    max-width: 800px;
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

form {
    display: flex;
    flex-direction: column;
}

label {
    font-size: 14px;
    color: #333;
    margin-bottom: 5px;
}

input[type="text"], textarea {
    padding: 10px;
    margin-bottom: 20px;
    border: 1px solid #ddd;
    border-radius: 4px;
}

textarea {
    height: 200px;
    resize: none;
}

button {
    padding: 10px 20px;
    background-color: #333;
    color: #fff;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

button:hover {
    background-color: #555;
}
</style>
</head>
<body>
    <div class="container">
        <h2>게시물 수정하기</h2>
<form action="/project/edit/${boardDto.id}" method="post">
    <input type="hidden" name="id" value="${boardDto.id}">

    <label for="title">제목</label>
    <input type="text" id="title" name="title" value="${boardDto.title}" required>
    
    <label for="content">내용</label>
    <textarea id="content" name="content" required>${boardDto.content}</textarea>
    
    <button type="submit">수정하기</button>
</form>

    </div>
</body>
</html>

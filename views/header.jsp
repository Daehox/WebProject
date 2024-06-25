<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>StormSafe</title>
    <!-- Include Bootstrap CSS for styling -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <!-- Include Google Fonts -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@700&display=swap">
    <!-- Custom CSS for additional styling -->
    <style>
        .header {
            background-color: #f8f9fa;
            padding: 10px 0;
        }

        .logo_area {
            text-align: center;
        }

        .logo_area h1 {
            font-size: 3em; /* Increase font size */
            margin: 0;
            font-family: 'Roboto', sans-serif; /* Apply Roboto font */
        }

        .gnbMenu {
            list-style: none;
            padding: 0;
            margin: 0;
            display: flex;
            justify-content: flex-end;
            margin-left: 35px;
        }

        .gnbMenu li {
            margin-left: 35px;
        }

        .gnbMenu a {
            text-decoration: none;
            color: #000;
        }

        .gnbMenu a:hover {
            text-decoration: underline;
        }

        .d-flex {
            display: flex;
            align-items: center;
        }

        .logout-form {
            margin: 0;
        }

        .header-content {
            display: flex;
            flex-direction: column;
            align-items: center;
            width: 100%;
        }

        .nav-container {
            width: 100%;
            display: flex;
            justify-content: flex-end;
        }
    </style>
</head>
<body>
    <!-- Header -->
    <header class="header" id="header">
        <div class="header-content">
            <div class="logo_area">
                <h1>StormSafe</h1>
            </div>
            <div class="nav-container">
                <nav>
                    <ul class="gnbMenu">
                        <li><a href="#" title="About Us">About Us</a></li>
                        <li><a href="#" title="Archiving">Archiving</a></li>
                        <li><a href="#" title="News">News</a></li>
                        <c:choose>
                            <c:when test="${not empty sessionScope.loggedInUser}">
                                <li class="d-flex align-items-center">
                                    <span style="margin-right: 10px;"><b><i class="bi bi-person-fill"></i> 안녕하세요. ${sessionScope.loggedInUser}님</b></span>
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
                </nav>
            </div>
        </div>
    </header>

    <!-- Include Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>

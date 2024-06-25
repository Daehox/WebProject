<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Insert</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            margin: 0;
            padding: 0;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
            background-color: #F8F8F8;
        }

        .container {
            display: flex;
            flex-direction: column;
            align-items: center;
            width: 550px;
            height: 800px;
            margin-top: 10px;
            margin-bottom: 0px;
            background: #ffffff;
            border: none;
            border-radius: 20px;
        }

        .member-container {
            display: flex;
            flex-direction: column;
            align-items: center;
            width: 370px;
            height: 800px;
            margin-top: 72px;
            margin-bottom: 70px;
        }

        .header {
            width: 440px;
            height: 150px;
            font-weight: 1000;
            font-size: 30px;
            line-height: 42px;
            color: #000000;
            margin-top: 20px;
        }

        .agree-check {
            width: 460px;
            height: 21.06px;
            margin-top: 52.05px;
            font-weight: 300;
            font-size: 14px;
            line-height: 20px;
            color: #000000;
            margin-top: 10px;
        }

        input[type="submit"],
        input[type="reset"] {
            background-color: #000000;
            color: #fff;
            cursor: pointer;
            width: 100%;
            margin-top: 10px;
            height: 50px;
            font-size: 15px;
            border-radius: 10px;
            border: none;
        }

        .error-message {
            color: red;
            margin-top: 10px;
        }

        .address-section {
            width: 100%;
            margin-bottom: 20px;
        }

        .address-section input {
            width: calc(100% - 120px);
            margin-right: 10px;
        }

        .address-section button {
            width: 110px;
            height: 50px;
            font-size: 14px;
            border-radius: 10px;
            border: none;
        }
    </style>
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <script>
        function sample6_execDaumPostcode() {
            new daum.Postcode({
                oncomplete: function(data) {
                    var addr = ''; // 주소 변수
                    var extraAddr = ''; // 참고항목 변수

                    if (data.userSelectedType === 'R') {
                        addr = data.roadAddress;
                    } else {
                        addr = data.jibunAddress;
                    }

                    if (data.userSelectedType === 'R') {
                        if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                            extraAddr += data.bname;
                        }
                        if (data.buildingName !== '' && data.apartment === 'Y') {
                            extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                        }
                        if (extraAddr !== '') {
                            extraAddr = ' (' + extraAddr + ')';
                        }
                        document.getElementById("sample6_extraAddress").value = extraAddr;
                    } else {
                        document.getElementById("sample6_extraAddress").value = '';
                    }

                    document.getElementById('sample6_postcode').value = data.zonecode;
                    document.getElementById("sample6_address").value = addr;
                    document.getElementById("sample6_detailAddress").focus();
                }
            }).open();
        }

        function validateForm() {
            var id = document.getElementById('id').value;
            var passwd = document.getElementById('passwd').value;
            var email = document.getElementById('email').value;
            var name = document.getElementById('name').value;
            var agreeCheckbox = document.getElementById('agreeCheckbox').checked;

            if (!id || !passwd || !email || !name) {
                alert('모든 필수 항목을 입력해주세요.');
                return false;
            }

            if (!agreeCheckbox) {
                alert('약관에 동의해주세요.');
                return false;
            }

            return true;
        }

        function checkId() {
            var id = document.getElementById('id').value;
            if (id) {
                var xhr = new XMLHttpRequest();
                xhr.open('GET', '/checkId?id=' + id, true);
                xhr.onreadystatechange = function () {
                    if (xhr.readyState == 4 && xhr.status == 200) {
                        if (xhr.responseText == 'true') {
                            alert('아이디가 이미 존재합니다.');
                        }
                    }
                };
                xhr.send(null);
            }
        }
    </script>
</head>
<body>
<div class="container">
    <div class="member-container">
        <div class="header">
            <div>회원 가입을 위해</div>
            <div>정보를 입력해주세요</div>
        </div>

        <form name="frm1" method="post" action="insert" accept-charset="UTF-8" onsubmit="return validateForm()">
            <div class="form-floating mb-3">
                <input type="text" class="form-control" name="id" id="id" placeholder="" onblur="checkId()">
                <label for="id">* ID</label>
            </div>

            <div class="form-floating mb-3">
                <input type="password" class="form-control" name="passwd" id="passwd" placeholder=" ">
                <label for="passwd">* Password</label>
            </div>

            <div class="form-floating mb-3">
                <input type="text" class="form-control" name="email" id="email" placeholder=" ">
                <label for="email">* Email</label>
            </div>

            <div class="form-floating mb-3">
                <input type="text" class="form-control" name="name" id="name" placeholder=" ">
                <label for="name">* Name</label>
            </div>

            <div class="address-section">
                <input type="text" class="form-control" id="sample6_postcode" name="postcode" placeholder="우편번호">
                <button type="button" onclick="sample6_execDaumPostcode()">우편번호 찾기</button><br>
                <input type="text" class="form-control" id="sample6_address" name="address" placeholder="주소"><br>
                <input type="text" class="form-control" id="sample6_detailAddress" name="detailAddress" placeholder="상세주소">
                <input type="text" class="form-control" id="sample6_extraAddress" name="extraAddress" placeholder="참고항목">
            </div>

            <div class="agree-check">
                <input type="checkbox" id="agreeCheckbox" /> 이용약관 개인정보 수집 및 이용, 마케팅 활용 선택에 모두 동의합니다.
            </div>

            <div class="error-message" id="errorMessage"></div>

            <div class="button">
                <input type="reset" value="다시입력">
            </div>
            <input type="submit" value="가입하기">
        </form>
    </div>
</div>
</body>
</html>

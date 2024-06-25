<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.json.JSONObject" %>
<%@ page import="com.yeonsung.service.WeatherService" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Weather Info</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            text-align: center;
            margin: 0;
            padding: 0;
        }
        .container {
            margin: 3px auto;
            padding: 10px;
            max-width: 600px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .city {
            font-size: 24px;
            font-weight: bold;
            margin-bottom: 10px;
        }
        .temp {
            font-size: 48px;
            margin-bottom: 10px;
        }
        .description {
            font-size: 18px;
            margin-bottom: 20px;
        }
        .icon {
            width: 100px;
            height: 100px;
        }
    </style>
</head>
<body>
    <div class="container">
 <%
    // 날씨 정보를 가져올 도시 이름 설정
    //원랜 사용자 위치를 추적 하여 도시를 설정할려는 계획이였음
    String city = "Seoul"; 

    // WeatherService를 통해 해당 도시의 날씨 정보를 JSON 객체로 가져옴
    JSONObject weatherJson = WeatherService.getWeatherJson(city);
    String cityName = weatherJson.getString("name");

    // JSON 객체에서 메인 객체를 가져옴
    JSONObject main = weatherJson.getJSONObject("main");
    double temperature = main.getDouble("temp");

    //아이콘 추출
    String description = weatherJson.getJSONArray("weather").getJSONObject(0).getString("description");
    String icon = weatherJson.getJSONArray("weather").getJSONObject(0).getString("icon");

    String iconUrl = "https://openweathermap.org/img/wn/" + icon + "@2x.png";

    // 날씨 설명을 한국어로 번역하기 위한 맵 생성
    java.util.Map<String, String> translationMap = new java.util.HashMap<>();
    translationMap.put("clear sky", "맑은 하늘");
    translationMap.put("few clouds", "구름 조금");
    translationMap.put("scattered clouds", "흩어진 구름");
    translationMap.put("broken clouds", "부서진 구름");
    translationMap.put("shower rain", "소나기");
    translationMap.put("rain", "비");
    translationMap.put("thunderstorm", "뇌우");
    translationMap.put("snow", "눈");
    translationMap.put("mist", "안개");

    // 영어 설명을 한국어 번역
    String descriptionKorean = translationMap.getOrDefault(description, description);
%>
        <div class="city"><%= cityName %></div>
        <div class="temp"><%= temperature %>°C</div>
        <div class="description"><%= descriptionKorean %></div>
        <img class="icon" src="<%= iconUrl %>" alt="Weather Icon">
    </div>
</body>
</html>

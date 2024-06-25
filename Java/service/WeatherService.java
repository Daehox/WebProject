package com.yeonsung.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONObject;

public class WeatherService {
    private static final String API_KEY = "fa8255129f3e86789441b7b962f2ddd7";
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";

    // 도시 이름을 받아 날씨 정보를 JSON 객체로 반환하는 메서드
    public static JSONObject getWeatherJson(String city) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            // 도시 이름을 UTF-8로 인코딩
            String encodedCity = URLEncoder.encode(city, "UTF-8");
            // 완전한 API 요청 URL 생성
            String urlString = BASE_URL + "?q=" + encodedCity + "&appid=" + API_KEY + "&units=metric";
            URL url = new URL(urlString);
            // HttpURLConnection 객체 생성 및 설정
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            // 응답 스트림을 읽기 위한 BufferedReader 생성
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            StringBuilder jsonString = new StringBuilder();
            String line;
            // 응답 스트림을 한 줄씩 읽어서 StringBuilder에 추가
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
            // JSON 객체로 변환하여 반환
            return new JSONObject(jsonString.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null; // 예외 발생 시 null 반환
        } finally {
            try {
                // BufferedReader 닫기
                if (reader != null) reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // HttpURLConnection 연결 해제
            if (connection != null) connection.disconnect();
        }
    }
}

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<title>Main</title>
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap"
	rel="stylesheet">
<style>
* {
	font-family: "Noto Sans KR", sans-serif;
	list-style: none;
	text-decoration: none;
	border-collapse: collapse;
	color: black;
}

.main-content {
	padding: 10px;
	border-radius: 5px;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
	min-width: 700px;
	display: flex;
}

.main-content #map {
	width: 100%;
	height: 400px;
}

.text1 {
	font-size: 15px;
	font-weight: lighter;
	margin-bottom: 5px;
}

.text2>span {
	font-size: 15px;
}

.weather-info {
	margin-bottom: 20px;
	margin-top: 20px;
	width: 750px;
	display: flex;
	box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
	background-color: #e9ecef !important;
}

.weather-info>li {
	margin-bottom: 50px;
	flex: 1;
	height: 50px;
}

.weather-info>li>div {
	text-align: center;
	margin-top: 20px;
}

.weather-info>li:not(:last-child)>div {
	border-right: 1px solid #adb5bd;
}

.weather-icon {
	width: 100px;
	height: 100px;
	margin-top: -30px;
}

.image {
	display: flex;
	width:90%;
	height: 400px; 
	margin-top: 30px;
}

.image>.main1, .image>.main2 {
	flex: 1;
	background-size: cover; 
	background-position: center; 
	background-repeat: no-repeat; 
}

.image>.main1 {
	background-image: url("resources/img/main.png");
}

.image>.main2 {
	background-image: url("resources/img/natural.png");
}

.image > .main1 a, .image > .main2 a {
    display: block;
    width: 100%;
   	height: 100%;
}


</style>
</head>
<body>
	<div class="main-content">
		<div id="map"></div>
	</div>

	<ul class="weather-info">
		<li>
			<div>
				<div class="text1">
					<strong>위치<img src="resources/img/location.png"
						width="30px" height="30px"></strong>
				</div>
				<div class="text2">
					<span class="place"></span>
				</div>
			</div>
		</li>
		<li>
			<div>
				<div class="text1">
					<strong>현재 온도<img src="resources/img/temperatura.png"
						width="30px" height="30px"></strong>
				</div>
				<div class="text2">
					<span class="temperature"></span>
				</div>
			</div>
		</li>
		<li>
			<div>
				<div class="text1">
					<strong>강수량<img src="resources/img/rain.png" width="30px"
						height="30px"></strong>
				</div>
				<div class="text2">
					<span class="precipitation"></span>
				</div>
			</div>
		</li>
		<li>
			<div>
				<div class="text1">
					<strong>날씨</strong>
				</div>
				<div class="text2">
					<span class="weather-icon-container"> <img
						class="weather-icon" src="" alt="Weather Icon"></span>
				</div>
			</div>
		</li>
	</ul>

	<div class="image">
		<div class="main1"><a href="https://www.youtube.com/watch?v=_HwfID8W_zo" target="_blank"></a></div>
		<div class="main2"><a
			href="https://www.safekorea.go.kr/idsiSFK/neo/sfk/cs/pan/cdr/cdreaiBefore.jsp?menuSeq=157"
			target="_blank"></a></div>
	</div>

</body>




<script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=8e8eb2e4347c6f88bb766df9ae57b744&libraries=services"></script>
<script>   
    var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = { 
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
       	level : 8 // 지도의 확대 레벨 
    }; 

var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성

//지도에 지형정보를 표시하도록 지도타입을 추가
map.addOverlayMapTypeId(kakao.maps.MapTypeId.TERRAIN);    


//geolocation 사용할 수 있는지 확인
if (navigator.geolocation) {
    
    // GeoLocation 접속 위치 얻어옴
    navigator.geolocation.getCurrentPosition(function(position) {
        
        var lat = position.coords.latitude, // 위도
            lon = position.coords.longitude; // 경도
        
        var locPosition = new kakao.maps.LatLng(lat, lon), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성
            message = '<div style="padding:5px;">현재 위치</div>'; 
        
        // 마커와 인포윈도우를 표시합니다
        displayMarker(locPosition, message);
            
      });
    
} else { // HTML5의 GeoLocation을 사용할 수 없는 경우
    
    var locPosition = new kakao.maps.LatLng(37.63312, 127.051649),    
        message = 'geolocation을 사용할수 없어요..'
        
    displayMarker(locPosition, message);
}

// 지도에 마커와 인포윈도우를 표시하는 함수입니다
function displayMarker(locPosition, message) {

    // 마커를 생성합니다
    var marker = new kakao.maps.Marker({  
        map: map, 
        position: locPosition
    }); 
    
    var iwContent = message, // 인포윈도우에 표시할 내용
        iwRemoveable = true;

    // 인포윈도우를 생성
    var infowindow = new kakao.maps.InfoWindow({
        content : iwContent,
        removable : iwRemoveable
    });
    
    // 인포윈도우를 마커위에 표시
    infowindow.open(map, marker);
    
    // 지도 중심좌표를 접속위치로 변경합니다
    map.setCenter(locPosition);      
}
const GOOGLE_MAPS_API_KEY = 'AIzaSyA3nBAeCJ0ShgqnwjFMAc6gFJLPQYn0vws'; // Google Maps API 키
const OPENWEATHER_API_KEY = '875a702654c45a475ae5387d8e914e60'; // OpenWeatherMap API 키

window.addEventListener('load', () => {
  console.log('페이지 로드됨');
  navigator.geolocation.getCurrentPosition(success, fail);
});

const success = (position) => {
  const lat = position.coords.latitude;
  const lon = position.coords.longitude;
  console.log('위도:', lat, '경도:', lon); // 디버그를 위한 로그 추가
  if (lat && lon) {
    getCityName(lat, lon);
  } else {
    console.error('위도와 경도가 유효하지 않습니다.', lat, lon);
  }
};

const fail = () => {
  alert("좌표를 받아올 수 없습니다.");
};

const getCityName = (lat, lon) => {
  console.log('getCityName 호출됨', lat, lon);
  const url = 'https://maps.googleapis.com/maps/api/geocode/json?latlng=' + lat + ',' + lon + '&language=ko&key=' + GOOGLE_MAPS_API_KEY;
  console.log('Geocode 요청 URL:', url); // 디버그를 위한 로그 추가
  
  fetch(url)
  .then(response => response.json())
  .then(data => {
    console.log('Geocode data:', data); // 디버그를 위한 로그 추가
    if (data.plus_code && data.plus_code.compound_code) {
      let cityName = '';
      let cityEnglishName = '';

      // compound_code에서 한국어 도시 이름 추출
      const compoundCode = data.plus_code.compound_code;
      const parts = compoundCode.split(' ');
      const cityNameKorean = parts[parts.length - 1]; // 마지막 부분이 도시 이름

	  // 도시 이름 설정
      cityName = cityNameKorean;
      cityEnglishName = cityNameKorean;

      console.log('도시 이름:', cityName);
      console.log('도시 이름(영어):', cityEnglishName);

      document.querySelector('.place').innerText = cityName;
      getWeather(cityEnglishName);
    } else {
      alert("도시 이름을 찾을 수 없습니다.");
    }
  })
  .catch(error => console.error('위치를 가져오는 중 오류 발생:', error));
};

const getWeather = (cityName) => {
  console.log('getWeather 호출됨', cityName);
  const url = 'https://api.openweathermap.org/data/2.5/weather?q=' + cityName + '&appid=' + OPENWEATHER_API_KEY + '&units=metric&lang=kr';
  console.log('Weather 요청 URL:', url); // 디버그를 위한 로그 추가

  fetch(url)
    .then(response => response.json())
    .then(data => {
      console.log('Weather data:', data); // 디버그를 위한 로그 추가
      if (data.main && data.weather) {
        const temperature = data.main.temp;
        const precipitation = data.rain ? data.rain['1h'] : '0';
        const weatherIcon = data.weather[0].icon;
        
        document.querySelector('.temperature').innerText = temperature + '°C';
        document.querySelector('.precipitation').innerText = precipitation + ' mm';
        document.querySelector('.weather-icon').src = 'http://openweathermap.org/img/wn/' + weatherIcon + '@2x.png';
      } else {
        alert("날씨 정보를 가져올 수 없습니다.");
      }
    })
    .catch(error => console.error('날씨 정보를 가져오는 중 오류 발생:', error));
};

    </script>
</body>
</html>

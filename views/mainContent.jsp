<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<title>Main</title>
<style>
.main-container {
	display: flex;
	flex-direction: row;
	justify-content: center;
	align-items: flex-start;
	max-width: 1000px;
	margin: 0 auto;
	gap: 20px;
}

.list, .main-content {
	flex: 1;
	padding: 20px;
	background-color: #fff;
	border-radius: 8px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	min-width: 300px; 
}

.list iframe {
	width: 100%;
	height: 600px;
	border: none;
}

.main-content #map {
	width: 100%;
	height: 300px; 
}
</style>
</head>
<body>
<body>
    <div class="main-container">
        <div class="main-content">
            <div id="map"></div>
            <jsp:include page="weather.jsp" />
        </div>
        <div class="list">
            <iframe name="listFrame" src="/project/list"></iframe>
        </div>
    </div>


	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=b4bdaad90a715f537eb949a5cc1c95b2"></script>
	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=APIKEY&libraries=LIBRARY"></script>
	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=APIKEY&libraries=services,clusterer,drawing"></script>
	<script>
        var mapContainer = document.getElementById('map'), 
            mapOption = { 
                center: new kakao.maps.LatLng(33.450701, 126.570667), 
                level: 10 
            }; 

        var map = new kakao.maps.Map(mapContainer, mapOption); 

        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function(position) {
                var lat = position.coords.latitude, 
                    lon = position.coords.longitude; 
                var locPosition = new kakao.maps.LatLng(lat, lon), 
                    message = '<div style="padding:5px;">여기에 계신가요?!</div>'; 
                displayMarker(locPosition, message);
            });
        } else {
            var locPosition = new kakao.maps.LatLng(33.450701, 126.570667),    
                message = 'geolocation을 사용할 수 없어요..';
            displayMarker(locPosition, message);
        }

        function displayMarker(locPosition, message) {
            var marker = new kakao.maps.Marker({  
                map: map, 
                position: locPosition
            }); 
            var iwContent = message, 
                iwRemoveable = true;
            var infowindow = new kakao.maps.InfoWindow({
                content : iwContent,
                removable : iwRemoveable
            });
            infowindow.open(map, marker);
            map.setCenter(locPosition);      
        }    
    </script>
</body>
</html>

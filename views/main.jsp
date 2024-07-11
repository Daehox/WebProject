<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Main Page</title>
</head>
<body>
	<div class="header">
		<jsp:include page="header.jsp" />
	</div>

		<jsp:include page="list.jsp" />
		
	<div class="main-content">
		<jsp:include page="mainContent.jsp" />
	</div>

	<div class="footer">
		<jsp:include page="footer.jsp" />
	</div>
</body>
</html>

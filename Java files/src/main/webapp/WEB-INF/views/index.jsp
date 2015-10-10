<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>CTS</title>
<spring:url value="/resources" var="res" />
<link rel="stylesheet" href="${res}/css/style.css" />
<link rel="shortcut icon" type="image/x-icon" href="${res}/img/favicon.ico" />
<script type="text/javascript" src="${res}/libs/jquery.js"></script>
<script type="text/javascript" src="${res}/libs/scriptLoader.js"></script>
</head>
<body>
	<header>
		<div id="logoWrapper"></div>
	</header>
	<div id="notifications" class="notifications"></div>
	<div id="mainContainer"></div>
</body>
</html>

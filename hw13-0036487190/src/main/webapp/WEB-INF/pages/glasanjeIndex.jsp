<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Glasanje</title>
</head>

<body bgcolor="${pickedBgCol}">

	<a href="index.jsp">Home</a>
	<br>

	<h1>Glasanje za omiljeni bend</h1>

	<p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na
		link kako biste glasali!</p>

	<ol>
		<c:forEach var="entry" items="${bandsList}">
			<li><a href='glasanje-glasaj?id=${entry.id}'>${entry.bandName}</a></li>
		</c:forEach>
	</ol>

</body>
</html>
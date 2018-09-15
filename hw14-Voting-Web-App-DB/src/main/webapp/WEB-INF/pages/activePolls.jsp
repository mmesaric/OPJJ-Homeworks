<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Available polls</title>
</head>

<body>

	<a href="index.html"></a>
	<br>

	<h1>Dostupne ankete</h1>

	<p>Kliknite na anketu kako bi mogli glasati i vidjeti rezultate.</p>

	<ol>
		<c:forEach var="entry" items="${polls}">
			<li><a href="./glasanje?pollID=${entry.id}">${entry.title}
					- ${entry.message}</a></li>
		</c:forEach>
	</ol>

</body>
</html>
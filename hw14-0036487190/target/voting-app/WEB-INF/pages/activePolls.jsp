<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Available polls</title>
</head>

<body>

	<a href="index.html">Home</a>
	<br>

	<h1>Available polls</h1>

	<p>Click the poll in order to participate in it and leave a vote.</p>

	<ol>
		<c:forEach var="entry" items="${polls}">
			<li><a href="./glasanje?pollID=${entry.id}">${entry.title}
					- ${entry.message}</a></li>
		</c:forEach>
	</ol>

</body>
</html>
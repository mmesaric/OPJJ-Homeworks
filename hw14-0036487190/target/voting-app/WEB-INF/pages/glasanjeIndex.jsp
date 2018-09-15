<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Voting</title>
</head>

<body>

	<a href="../index.html">Home</a>
	<br>

	<h1>${title}</h1>

	<p>${message}</p>

	<ol>
		<c:forEach var="entry" items="${pollOptions}">
			<li><a href='glasanje-glasaj?id=${entry.id}'>${entry.optionTitle}</a></li>
		</c:forEach>
	</ol>

</body>
</html>
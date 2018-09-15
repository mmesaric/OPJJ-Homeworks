<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>

<body bgcolor="${pickedBgCol}">
	<h1>sin and cos values of given angles passed as parameters in
		range [a,b]</h1>

	<a href="index.jsp">Home</a>

	<br>
	<br>

	<table border=1>
		<thead>
			<tr>
				<th>x</th>
				<th>sinx</th>
				<th>cosx</th>

			</tr>
		</thead>
		<tbody>
			<c:forEach var="entry" items="${trigonometricValues}">
				<tr>
					<td>${entry.x}</td>
					<td>${entry.sinx}</td>
					<td>${entry.cosx}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>



</body>
</html>
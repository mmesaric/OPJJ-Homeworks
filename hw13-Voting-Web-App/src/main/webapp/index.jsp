<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Starting page</title>
</head>

<body bgcolor="${pickedBgCol}">
	<h1>Welcome</h1>
	<a href="colors.jsp">Background color chooser</a>
	<br>
	<br>

	<a href="trigonometric?a=0&b=90">Display sinx and cosx for x =
		[0,90] </a>
	<br>
	<br>

	<form action="trigonometric" method="GET">
		Starting angle:<br> <input type="number" name="a" min="0"
			max="360" step="1" value="0"><br> Finishing angle:<br>
		<input type="number" name="b" min="0" max="360" step="1" value="360"><br>
		<input type="submit" value="Tabeliraj"><input type="reset"
			value="Reset">
	</form>

	<br>
	<br>


	<a href="stories/funny.jsp">Click me for a funny story.</a>
	<br>
	<br>

	<a href="powers?a=1&b=100&n=3">Generate an xls table of powers.</a>
	<br>
	<br>
	
	<a href="appinfo.jsp">Total running time of the server</a>
	<br>
	<br>

	<a href="glasanje">Click to vote for you favorite band.</a>
	<br>
	<br>
</body>
</html>
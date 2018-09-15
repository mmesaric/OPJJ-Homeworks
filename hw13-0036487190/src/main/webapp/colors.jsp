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

	<a href="index.jsp">Home</a>


	<p>Pick a color</p>

	<a href="setcolor?color=white">WHITE</a>
	<br>
	<a href="setcolor?color=red">RED</a>
	<br>
	<a href="setcolor?color=green">GREEN</a>
	<br>
	<a href="setcolor?color=cyan">CYAN</a>
	<br>
</body>
</html>
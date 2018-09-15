<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true" import="java.awt.Color,java.util.Random"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>

<body bgcolor="${pickedBgCol}">
	
	<a href="../index.jsp">Home</a>

	<h1>Funny story</h1>

	<%
		Random rand = new Random();
		int rComp = rand.nextInt((255 - 0) + 1) + 0;
		int gComp = rand.nextInt((255 - 0) + 1) + 0;
		int bComp = rand.nextInt((255 - 0) + 1) + 0;

	%>


	<p style="color:rgb(<%=rComp%>,<%=gComp%>,<%=bComp%>);">The class teacher
		asks students to name an animal that begins with an “E”. One boy says,
		“Elephant.” Then the teacher asks for an animal that begins with a
		“T”. The same boy says, “Two elephants.” The teacher sends the boy out
		of the class for bad behavior. After that she asks for an animal
		beginning with “M”. The boy shouts from the other side of the wall:
		“Maybe an elephant!”</p>

</body>
</html>
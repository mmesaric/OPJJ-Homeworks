<%@page import="java.util.concurrent.TimeUnit"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>App info</title>
</head>

<body bgcolor="${pickedBgCol}">

	<a href="index.jsp">Home</a>


	<%
		ServletContext context = request.getServletContext();

		long startTime = (long) context.getAttribute("startTime");
		long currentTime = System.currentTimeMillis();
		long runningTime = currentTime - startTime;

		long seconds = runningTime / 1000;
		long minutes = seconds / 60;
		long hours = minutes / 60;
		long days = hours / 24;
		String timeString = days + " days " + hours % 24 + " hours " + minutes % 60 + " minutes " + seconds % 60 + " seconds " +  runningTime%1000 + " milliseconds";
	%>

	<p>
		This application is running
		<%=timeString%></p>
</body>
</html>
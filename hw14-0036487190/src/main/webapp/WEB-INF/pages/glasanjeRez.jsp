<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
table.rez td {
	text-align: center;
}
</style>
<title>Rezultati glasanja</title>
</head>
<body >

	<a href="../index.html">Home</a>
	<br>

	<h1>Rezultati glasanja</h1>

	<p>Ovo su rezultati glasanja!</p>

	<table border="1"  class="rez">
		<thead>
			<tr>
				<th>Sudionik</th>
				<th>Broj glasova</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="entry" items="${results}" varStatus="i">
				<tr>
					<td>${entry.optionTitle}</td>
					<td>${entry.votesCount}</td>
				</tr>
			</c:forEach>

		</tbody>
	</table>
	<br>

	<h2>Grafički prikaz rezultata</h2>
	<img src="glasanje-grafika?pollID=<%= request.getParameter("pollID") %>"
		alt="Nema glasova. Kako bi vidjeli graficki prikaz glasova, glasajte na anketi."
		width="600" height="600" />

	<br>
	<h2>Rezultati u XLS formatu</h2>
	<p>
		Rezultati u XLS formatu dostupni su <a href="glasanje-xls?pollID=<%= request.getParameter("pollID") %>">ovdje</a>
	</p>

	<h2>Razno</h2>
	<p>Linkovi za vise informacija o pobjedniku dostupni su tu:</p>
	<ul>
		<c:forEach var="entry" items="${winnerEntries}">
			<li><a href="${entry.optionLink}" target="_blank">${entry.optionTitle}</a></li>
		</c:forEach>
	</ul>
</body>

</html>
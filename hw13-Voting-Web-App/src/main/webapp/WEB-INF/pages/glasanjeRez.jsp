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
<body bgcolor="${pickedBgCol}">

	<a href="index.jsp">Home</a>
	<br>

	<h1>Rezultati glasanja</h1>

	<p>Ovo su rezultati glasanja!</p>

	<table border="1" cellspacing="0" class="rez">
		<thead>
			<tr>
				<th>Bend</th>
				<th>Broj glasova</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="entry" items="${voteEntries}" varStatus="i">
				<tr>
					<td>${entry.bandName}</td>
					<td>${entry.numberOfVotes}</td>
				</tr>
			</c:forEach>

		</tbody>
	</table>
	<br>

	<h2>Grafički prikaz rezultata</h2>
	<img src="glasanje-grafika"
		alt="Nema glasova. Kako bi vidjeli graficki prikaz glasova, glasajte za najdrazi bend."
		width="600" height="600" />

	<br>
	<h2>Rezultati u XLS formatu</h2>
	<p>
		Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a>
	</p>

	<h2>Razno</h2>
	<p>Primjeri pjesama pobjedničkih bendova:</p>
	<ul>
		<c:forEach var="entry" items="${winnerSongs}">
			<li><a href="${entry.songLink}" target="_blank">${entry.bandName}</a></li>
		</c:forEach>
	</ul>
</body>

</html>
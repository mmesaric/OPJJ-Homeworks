<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<body>

	<c:choose>
		<c:when test="${not empty sessionScope['current.user.nick']}">
			<p>
				You are currently logged in as ${sessionScope['current.user.fn']}
				${sessionScope['current.user.ln']}! <a href="../logout">Logout</a>
			</p>
		</c:when>
		<c:otherwise>

			<p>Not logged in</p>

		</c:otherwise>
	</c:choose>

	<c:choose>
		<c:when test="${blogEntries==null}">
      No blog entries for given author!
    </c:when>
		<c:otherwise>

			<h3>All blog entries by author "${nick}"</h3>


			<c:if test="${blogEntries.isEmpty()}">
						      No blog entries for given author!
						
						</c:if>

			<c:if test="${!blogEntries.isEmpty()}">
				<ul>
					<c:forEach var="entry" items="${blogEntries}">
						<li><a href="${nick}/${entry.id}">${entry.title} </a></li>
					</c:forEach>
				</ul>
			</c:if>

		</c:otherwise>
	</c:choose>

	<c:choose>

		<c:when test="${not empty sessionScope['current.user.nick']}">
			<br>
			<a href="${nick}/new">Add new blog entry</a>

		</c:when>
	</c:choose>




</body>
</html>

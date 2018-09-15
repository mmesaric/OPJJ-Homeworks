<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<body>

	<c:choose>
		<c:when test="${not empty sessionScope['current.user.nick']}">
			<p>
				You are currently logged in as ${sessionScope['current.user.fn']}
				${sessionScope['current.user.ln']}! <a href="../../logout">Logout</a>
			</p>
		</c:when>
		<c:otherwise>

			<p>Not logged in</p>

		</c:otherwise>
	</c:choose>

	<c:choose>
		<c:when test="${entry==null}">
      No blog entry was chosen!
    </c:when>
		<c:otherwise>

			<h2>
				<em>${entry.title}</em>
			</h2>

			<p>${entry.text}</p>

		</c:otherwise>
	</c:choose>

	<c:choose>

		<c:when test="${not empty sessionScope['current.user.nick']}">
			<br>
			<a href="edit/${entry.id}">Edit this entry</a>

		</c:when>
	</c:choose>

	<br>
	<br>

	<c:choose>
		<c:when test="${comments==null}">
      No comments on this blog post yet!
    </c:when>
		<c:otherwise>

			<c:forEach var="e" items="${comments}">
				<div style="font-weight: bold">
					[User=
					<c:out value="${e.usersEMail}" />
					]
					<c:out value="${e.postedOn}" />
				</div>
				<div style="padding-left: 10px;">
					<c:out value="${e.message}" />
				</div>


			</c:forEach>

		</c:otherwise>
	</c:choose>

	<h3>Leave a comment</h3>

	<form action="./${entry.id}" method="post">


		<label><b>Email:</b></label> <br> <input type="email"
			placeholder="Enter title" name="email" required> <br> <label><b>Comment:</b></label>
		<br>
		<textarea rows="4" cols="70" name="text" required></textarea>

		<br> <br> <input type="submit" name="submit" value="Submit">

	</form>

</body>
</html>

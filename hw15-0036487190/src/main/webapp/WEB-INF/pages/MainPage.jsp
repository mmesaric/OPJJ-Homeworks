<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<body>


	<c:choose>
		<c:when test="${not empty sessionScope['current.user.nick']}">
			<p>
				You are currently logged in as ${sessionScope['current.user.fn']}
				${sessionScope['current.user.ln']}! <a href="logout">Logout</a>
			</p>

		</c:when>
		<c:otherwise>
			<p>Not logged in</p>

			<h3>Login</h3>

			<form action="main" method="post">

				<c:choose>
					<c:when test="${nickInput != null}">
						<label><b>Nick:</b></label>
						<br>
						<input type="text" placeholder="Enter nick" name="nick"
							value="${nickInput}" required>

					</c:when>
					<c:otherwise>
						<label><b>Nick:</b></label>
						<br>
						<input type="text" placeholder="Enter nick" name="nick" required>

					</c:otherwise>
				</c:choose>



				<c:if test="${nickMessage != null}">
					<br>
					<font color="red"><label><b>${nickMessage}</b></label></font>
				</c:if>
				<br> <label><b>Password:</b></label><br> <input
					type="password" placeholder="Enter password" name="password"
					required>
				<c:if test="${passMessage != null}">
					<br>
					<font color="red"><label><b>${passMessage}</b></label></font>
				</c:if>

				<br> <br> <input type="submit" name="main" value="Submit">

			</form>

			<a href="register">Register an account.</a>


		</c:otherwise>
	</c:choose>


	<p>List of registered authors.</p>

	<ul>
		<c:forEach var="entry" items="${authors}">
			<li><a href="author/${entry.nick}">${entry.firstName}
					${entry.lastName}</a></li>
		</c:forEach>
	</ul>




</body>
</html>

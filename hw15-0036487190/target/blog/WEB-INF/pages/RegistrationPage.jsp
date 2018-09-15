<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<body>

	<c:choose>
		<c:when test="${not empty sessionScope['current.user.nick']}">
			<p>
				You are currently logged in as ${sessionScope['current.user.fn']}
				${sessionScope['current.user.ln']}! In order to register new
				account, <a href="logout">Logout</a> first.
			</p>
			<br />
		</c:when>
		<c:otherwise>
			<p>Not logged in</p>


			<h3>Registration</h3>

			<form action="register" method="post">

				<label><b>First name:</b></label><br> <input type="text"
					placeholder="Enter first name" name="firstName" required>
				<c:if test="${fnMessage != null}">
					<br>
					<font color="red"><label><b>${fnMessage}</b></label></font>
				</c:if>
				<br> <label><b>Last name:</b></label><br> <input
					type="text" placeholder="Enter last name" name="lastName" required>
				<c:if test="${lnMessage != null}">
					<br>
					<font color="red"><label><b>${lnMessage}</b></label></font>
				</c:if>
				<br> <label><b>Email:</b></label><br> <input type="email"
					placeholder="Enter email" name="email" required>
				<c:if test="${emailMessage != null}">
					<br>
					<font color="red"><label><b>${emailMessage}</b></label></font>
				</c:if>
				<br> <label><b>Nick:</b></label><br> <input type="text"
					placeholder="Enter nick" name="nick" required>
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

		</c:otherwise>
	</c:choose>



</body>
</html>

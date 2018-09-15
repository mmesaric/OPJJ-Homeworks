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
			<br>

		</c:otherwise>
	</c:choose>


	<c:choose>
		<c:when test="${entryID != null}">

			<form action="./${entryID}" method="post">

				<c:choose>
					<c:when test="${title != null}">
						<label><b>Blog entry title:</b></label>
						<br>
						<input type="text" placeholder="Enter title" name="title"
							value="${title}" required>

					</c:when>
					<c:otherwise>
						<label><b>Blog entry title:</b></label>
						<br>
						<input type="text" placeholder="Enter title" name="title" required>

					</c:otherwise>
				</c:choose>

				<br>

				<c:choose>
					<c:when test="${text != null}">
						<label><b>Blog entry text:</b></label>
						<br>
						<textarea rows="4" cols="70" name="text" required>${text}</textarea>

					</c:when>
					<c:otherwise>
						<label><b>Blog entry text:</b></label>
						<br>
						<textarea rows="4" cols="70" name="text" required></textarea>
					</c:otherwise>
				</c:choose>

				<br> <br> <input type="submit" name="submit"
					value="Submit">

			</form>

		</c:when>
		<c:otherwise>

			<form action="./new" method="post">

				<c:choose>
					<c:when test="${title != null}">
						<label><b>Blog entry title:</b></label>
						<br>
						<input type="text" placeholder="Enter title" name="title"
							value="${title}" required>

					</c:when>
					<c:otherwise>
						<label><b>Blog entry title:</b></label>
						<br>
						<input type="text" placeholder="Enter title" name="title" required>

					</c:otherwise>
				</c:choose>

				<br>

				<c:choose>
					<c:when test="${text != null}">
						<label><b>Blog entry text:</b></label>
						<br>
						<textarea rows="4" cols="70" name="text" required>${text}</textarea>

					</c:when>
					<c:otherwise>
						<label><b>Blog entry text:</b></label>
						<br>
						<textarea rows="4" cols="70" name="text" required></textarea>
					</c:otherwise>
				</c:choose>

				<br> <br> <input type="submit" name="submit"
					value="Submit">

			</form>


		</c:otherwise>
	</c:choose>

</body>
</html>

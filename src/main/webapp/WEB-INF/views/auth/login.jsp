<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>

	<!-- include pure.css -->
	<link rel="stylesheet" href="/myapp/resources/css/pure-min.css">
		
	<!-- include libraries(jQuery) -->
	<script src="/myapp/resources/js/jquery-3.3.1.min.js"></script>
</head>

<body>
	<div align="center">
		<h1>Login</h1>
		<h2><c:out value="${error}"></c:out></h2>
		<h2><c:out value="${logout}"></c:out></h2>
		
		<form method="post" action="login" class="pure-form pure-form-stacked">
			<fieldset>
				<label for="username">ID</label>
					<input id="username" type="text" name="username" value="admin">
				
				<label for="password">Password</label>
					<input id="password" type="password" name="password" value="admin">
				
				<button type="submit" class="pure-button button-success">Login</button>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"> 
			</fieldset>
		</form>
	</div>
</body>
</html>
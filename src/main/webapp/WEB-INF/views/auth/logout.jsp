<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">	
	<title>Insert title here</title>
	
	<!-- include pure.css -->
	<link rel="stylesheet" href="/myapp/resources/css/pure-min.css">

	<!-- include custom css-->
	<link rel="stylesheet" href="/myapp/resources/css/style.css">
</head>
<body align="center">
	<h1>Logout Page</h1>
	<form action="/myapp/logout" method="post" class="pure-form pure-form-stacked">
		<fieldset>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"> 
			<button type="submit" class="pure-button button-warning ">로그아웃</button>
		</fieldset>
	</form>
</body>
</html>
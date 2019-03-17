<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>boardList</title>
	<script src="/myapp/resources/js/jquery-3.3.1.min.js"></script>
</head>
	
<body>
	<div align="center" style="width:70%">
		<div>
			<div align="left">
				<form method="POST" action="">
					<input type="date" name="startDate"/>~
					<input type="date" name="endDate"/>
					<input type="search" name="search">
					<input type="radio" name="choose" value="title">제목
	 	 			<input type="radio" name="choose" value="contents">내용
					<input type="submit" value="검색">
				</form>
			</div>
			<div align="right">
				<a href="/myapp/board/create"><button>작성하기</button></a>
			</div>	
		</div>
		
		<table border="1" style="width:100%">
		  <tr>
		    <th>번호</th>
		    <th>제목</th> 
		    <th>일자</th>
		  </tr>
		  
		  <c:forEach var="board" items="${pageObj.contents}">
			  <tr>
			    <td><c:out value="${board.no}"/></td>
			    <td><a class="moveToBoardDetail" href="${board.no}" target="_blank"><c:out value="${board.title}"/></a></td> 
			    <td><c:out value="${board.regDate}"/></td>
			  </tr>
		  </c:forEach>
		</table>
	
		<jsp:include page="../pagination.jsp"></jsp:include>
	</div>
	
	<script src="/myapp/resources/js/script.js"></script>
</body>
</html>
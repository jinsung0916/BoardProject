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
	<div align="center" style="width:50%">
		<div align="right">
			<a href="/myapp/board/create"><button>작성하기</button></a>
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
	
		<c:if test="${pageObj.prev}">
			<a class="moveToOtherPage" href="${pageObj.startPage-1}">&lt&lt</a>
		</c:if>
		<c:forEach var="num" begin="${pageObj.startPage}" end="${pageObj.endPage}">
			<c:choose>
				<c:when test="${num == pageObj.currentPage}">
					<a id="currentPage">${num}</a>
				</c:when>
				<c:otherwise>
					<a class="moveToOtherPage" href="${num}">${num}</a>
				</c:otherwise>
			</c:choose>
		</c:forEach>	
		<c:if test="${pageObj.next}">
			<a class="moveToOtherPage" href="${pageObj.endPage+1}">&gt&gt</a>
		</c:if>
	</div>
	
	<script src="/myapp/resources/js/script.js"></script>
</body>
</html>
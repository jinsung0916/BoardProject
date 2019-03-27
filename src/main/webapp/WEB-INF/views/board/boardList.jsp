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

	<!-- include pure.css -->
	<link rel="stylesheet" href="/myapp/resources/css/pure-min.css">
		
	<!-- include libraries(jQuery) -->
	<script src="/myapp/resources/js/jquery-3.3.1.min.js"></script>

	<!-- include custom css-->
	<link rel="stylesheet" href="/myapp/resources/css/style.css">
</head>

<body>
	<jsp:include page="../header.jsp"></jsp:include>

	<div class="pure-g">
		<div class="pure-u-1-1">
			<form id="searchForm" method="POST" action="/myapp/board/list" class="pure-form">
				<input type="date" name="startDate" value="${pageObj.searchInfo.startDate}" />~
				<input type="date" name="endDate" value="${pageObj.searchInfo.endDate}" />
				<input type="search" name="search" value="${pageObj.searchInfo.search}">
				<c:choose>
					<c:when test="${pageObj.searchInfo.choose == 'contents'}">
						<input type="radio" name="choose" value="title">제목
						<input type="radio" name="choose" value="contents" checked>내용
					</c:when>
					<c:otherwise>
						<input type="radio" name="choose" value="title" checked>제목
						<input type="radio" name="choose" value="contents">내용
					</c:otherwise>
				</c:choose>
				<button id="searchBtn" type="button" class="pure-button">검색</button>
			</form>
		</div>

		<div class="pure-u-1-1">
			<div align="right">
				<a href="/myapp/board/create"><button type="button" class="pure-button">작성하기</button></a>
			</div>
			<table width="100%" class="pure-table pure-table-bordered">
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>일자</th>
				</tr>

				<c:forEach var="board" items="${pageObj.contents}">
					<tr>
						<td>
							<c:out value="${board.no}" />
						</td>
						<td><a class="moveToBoardDetail" href="${board.no}" target="_blank">
								<c:out value="${board.title}" /></a></td>
						<td>
							<c:out value="${board.regDate}" />
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		
		<jsp:include page="../pagination.jsp"></jsp:include>
		
	</div>

	<div>
		<form id="moveToBoardDetailForm" method="POST" action='/myapp/board/detail'>
			<input type='hidden' name='page' value="${pageObj.searchInfo.page}">
			<input type='hidden' name='choose' value="${pageObj.searchInfo.choose}">
			<input type='hidden' name='search' value="${pageObj.searchInfo.search}">
			<input type='hidden' name='startDate' value="${pageObj.searchInfo.startDate}">
			<input type='hidden' name='endDate' value="${pageObj.searchInfo.endDate}">
		</form>
	</div>

	<!-- include custom js-->
	<script src="/myapp/resources/js/script.js"></script>
</body>

</html>
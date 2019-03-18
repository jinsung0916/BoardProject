<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${pageObj.prev}">
	<a class="moveToOtherPage" href="${pageObj.startPage-1}">&lt&lt</a>
</c:if>
<c:forEach var="num" begin="${pageObj.startPage}" end="${pageObj.endPage}">
	<a class="moveToOtherPage" href="${num}">${num}</a>
</c:forEach>	
<c:if test="${pageObj.next}">
	<a class="moveToOtherPage" href="${pageObj.endPage+1}">&gt&gt</a>
</c:if>

<form id="moveToOtherPageForm" method="POST" action='/myapp/board/list' style="display:none" >
		<input type='hidden' name='choose' value="${pageObj.searchInfo.choose}">
		<input type='hidden' name='search' value="${pageObj.searchInfo.search}">
		<input type='hidden' name='startDate' value="${pageObj.searchInfo.startDate}">
		<input type='hidden' name='endDate' value="${pageObj.searchInfo.endDate}">
</form>
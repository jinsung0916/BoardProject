<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
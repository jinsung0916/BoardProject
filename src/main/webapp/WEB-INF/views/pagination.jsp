<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div align="center" class="pure-u-1-1">
	<div class="pure-button-group" role="group" aria-label="...">
		<c:if test="${pageObj.prev}">
			<a class="moveToOtherPage" href="${pageObj.startPage-1}"><button class="pure-button">&lt&lt</button></a>
		</c:if>
		<c:forEach var="num" begin="${pageObj.startPage}" end="${pageObj.endPage}">
			<c:choose>
				<c:when test="${pageObj.searchInfo.page == num}">
					<a href="#"><button type="button" class="pure-button button-secondary">${num}</button></a>
				</c:when>
				<c:otherwise>
					<a class="moveToOtherPage" href="${num}"><button class="pure-button">${num}</button></a>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<c:if test="${pageObj.next}">
			<a class="moveToOtherPage" href="${pageObj.endPage+1}"><button class="pure-button">&gt&gt</button></a>
		</c:if>
	</div>

	<div>
		<form id="moveToOtherPageForm" method="POST" action='/myapp/board/list'>
			<input type='hidden' name='flag' value="${pageObj.searchInfo.flag}">
			<input type='hidden' name='value' value="${pageObj.searchInfo.value}">
			<input type='hidden' name='startDate' value="${pageObj.searchInfo.startDate}">
			<input type='hidden' name='endDate' value="${pageObj.searchInfo.endDate}">
			<input type='hidden' name='${_csrf.parameterName}' value="${_csrf.token}">
		</form>
	</div>
</div>
<script>
	/*
	 * pagination.jsp 에서 페이지 링크를 클릭했을 때 POST 요청이 이루어지도록 이벤트 처리
	 */
	$(".moveToOtherPage").on("click", function (e) {
		e.preventDefault();
		var page = $(this).attr("href");
		$('<input>').attr({
			type: 'hidden',
			name: 'page',
			value: page
		}).appendTo('#moveToOtherPageForm');
		$("#moveToOtherPageForm").submit();
	});
</script>
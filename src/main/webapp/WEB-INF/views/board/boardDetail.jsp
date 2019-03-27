<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>BoardDetail</title>
	
	<!-- include libraries(jQuery) -->
	<script src="/myapp/resources/js/jquery-3.3.1.min.js"></script> 
	
	<!-- include summernote css/js -->
	<link href="/myapp/resources/summernote/summernote-lite.css" rel="stylesheet">
	<script src="/myapp/resources/summernote/summernote-lite.min.js"></script>

	<!-- include pure.css -->
	<link rel="stylesheet" href="/myapp/resources/css/pure-min.css">

	<!-- include custom css-->
	<link rel="stylesheet" href="/myapp/resources/css/style.css">
</head>

<body>
	<jsp:include page="../header.jsp"></jsp:include>

	<div class="pure-g">
		<div class="pure-u-1-1">
			<form id="boardDetailform" method="POST" enctype="multipart/form-data" class="pure-form pure-form-stacked">
				<fieldset>
		
					<input id="no" type="hidden" name="no" value="${board.no}" />
	
					<label for="title">제목</label>
					<input id="title" type="text" name="title" value="${board.title}" disabled />

					<label for="summernote">내용</label>
					<textarea id="summernote" name="contents">${board.contents}</textarea>
			

					<!-- 첨부파일 다운로드 -->
					<div>
						<div>첨부파일</div>
						<div class="pure-u-4-5 pure-menu custom-restricted">
							<ul class="pure-menu-list">
							<c:forEach var="file" items="${board.fileList}">
								<li class="pure-menu-item"><a class="fileDownload pure-menu-link" href="${file.uuid}">${file.fileName}</a></li>
							</c:forEach>
							</ul>
						</div>
					</div>

					<!-- 파일 업로드 폼-->
					<input id="file" type="file" name="uploadFile" multiple hidden />

					<div id="fileUploadDiv" style="display: none;">
						<div>업로드할 파일</div>
						<div class="pure-g">
							<div id="fileListDiv" class="pure-u-4-5 custom-restricted">
							</div>
							<div align="center" class="pure-g pure-u-1-5">
								<button id="plusBtn" type="button" class="pure-button">파일추가</button><br/>
								<button id="minusBtn" type="button" class="pure-button">파일삭제</button>
							</div>
						</div>
					</div>

					<!-- 수정버튼 -->
					<button id="modifyBtn" type="button" class="pure-button">modify</button>

					<!-- 전송버튼 -->
					<button id="uploadBtn" type="submit" class="pure-button button-success" style="display: none;">submit</button>

					<!-- 삭제버튼 -->
					<button id="deleteBtn" type="button" class="pure-button button-error" style="display: none;">delete</button>

				</fieldset>
			</form>
		</div>

		<div>
			<form id="moveToBoardListForm" method="POST" action='/myapp/board/list'>
				<input type='hidden' name='page' value="${searchInfo.page}">
				<input type='hidden' name='choose' value="${searchInfo.choose}">
				<input type='hidden' name='search' value="${searchInfo.search}">
				<input type='hidden' name='startDate' value="${searchInfo.startDate}">
				<input type='hidden' name='endDate' value="${searchInfo.endDate}">
			</form>
		</div>
	</div>

	<!-- include custom js-->
	<script src="/myapp/resources/js/script.js"></script>
	<script type="text/javascript">
		$('#summernote').summernote('disable');
	</script>
</body>

</html>
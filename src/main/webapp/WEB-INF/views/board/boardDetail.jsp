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
	
	<!-- include libraries(jQuery, bootstrap) -->
	<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
	<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script> 
	<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script> 
	
	<!-- include summernote css/js -->
	<link href="/myapp/resources/summernote/summernote.css" rel="stylesheet">
	<script src="/myapp/resources/summernote/summernote.js"></script>
</head>

<body>
	<form id="boardDetailform" method="POST" enctype="multipart/form-data" style="width: 750px;">
		<fieldset>
			<div class="hiddenFormGroup">
				<input id="no" type="hidden" name="no" value="${board.no}" />
			</div>

			<div class="formGroup">
				<label for="title">제목: </label>
				<input id="title" type="text" name="title" value="${board.title}" disabled />
			</div>
			
			<div class="formGroup">
				<textarea id="summernote" name="contents">${board.contents}</textarea>
			</div>

			<!-- 첨부파일 다운로드 -->
			<div style="height: 150px;">
				<div>첨부파일</div>
				<div style="border: solid 1px; height: 80%; overflow: auto">
					<c:forEach var="file" items="${board.fileList}">
						<div><a class="fileDownload" href="${file.uuid}">${file.fileName}</a></div>
					</c:forEach>
				</div>
			</div>

			<!-- 파일 업로드 폼-->
			<div style="display: none;">
				<input id="file" type="file" name="uploadFile" multiple />
			</div>
			<div id="fileUploadDiv" style="display: none; height: 150px;">
				<div>업로드할 파일</div>
				<div style="height: 80%;">
					<div id="fileListDiv"
						style="border: solid 1px; float: left; width: 85%; height:100%; overflow: auto">
					</div>
					<div style="float: right; width:14%;" align="center">
						<button id="plusBtn" type="button">파일추가</button><br />
						<button id="minusBtn" type="button">파일삭제</button><br />
					</div>
				</div>
			</div>

			<div>
				<!-- 수정버튼 -->
				<button id="modifyBtn" type="button">modify</button>

				<!-- 전송버튼 -->
				<input id="uploadBtn" type="hidden" value="submit">

				<!-- 삭제버튼 -->
				<button id="deleteBtn" type="button" style="display: none; background-color: red">delete</button>
			</div>

		</fieldset>
	</form>

	<form id="moveToBoardListForm" method="POST" action='/myapp/board/list'>
		<input type='hidden' name='page' value="${searchInfo.page}">
		<input type='hidden' name='choose' value="${searchInfo.choose}">
		<input type='hidden' name='search' value="${searchInfo.search}">
		<input type='hidden' name='startDate' value="${searchInfo.startDate}">
		<input type='hidden' name='endDate' value="${searchInfo.endDate}">
	</form>

	<script src="/myapp/resources/js/script.js"></script>
	<script type="text/javascript">
		$('#summernote').summernote('disable');
	</script>
</body>

</html>
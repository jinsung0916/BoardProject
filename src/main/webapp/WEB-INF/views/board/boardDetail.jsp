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
    <script src="/myapp/resources/js/jquery-3.3.1.min.js"></script>
</head>
<body>		
	<form id="form" method="POST" action="/myapp/board/update" enctype="multipart/form-data">
		<fieldset>
			<div class="hiddenFormGroup">
				<input id="no" type="hidden" name="no" value="${board.no}" />
				<input id="page" type="hidden" name="page" value="${page}" />
			</div>
			
			<div class="formGroup">
				<label for="title">제목: </label>
				<input id="title" type="text" name="title" value="${board.title}" disabled />
			</div>
			
			<div class="formGroup">
				<label for="contents">내용: </label>
				<textarea id="contents" name="contents" disabled>${board.contents}</textarea>
			</div>
			
			<div class="formGroup">
				<label for="file">첨부파일: </label>
				<input id="file" type="file" name="uploadFile" multiple disabled />
			</div>
			
			<div>
				<c:forEach var="file" items="${board.fileList}">
					<a href="/myapp/board/download?uuid=${file.uuid}">${file.fileName}</a></br>
				</c:forEach>
			</div>
			
			<!-- 수정버튼 -->
		    <input type="button" id="modifyBtn" value="modify">
		    
		    <!-- 전송버튼 -->
		    <input type="hidden" id="uploadBtn" value="submit">
		</fieldset>
	</form>
       
    <script src="/myapp/resources/js/script.js"></script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>boardCreate</title>
	<link rel="stylesheet" type="text/css" href="/myapp/resources/css/style.css">
	<script src="/myapp/resources/js/jquery-3.3.1.min.js"></script>
</head>

<body>
	<form id="boardCreateForm" method="POST" enctype="multipart/form-data">
		<fieldset>
			
			<div class="formGroup">
				<label>제목: </label>
				<input type="text" name="title" />
			</div>
			
			<div class="formGroup">
				<label>내용: </label>
				<textarea name="contents"></textarea>
			</div>
			
			<div style="display: none;">
				<input id="file" type="file" name="uploadFile" multiple/>
			</div>
			
			<div id="fileUploadDiv" style="height: 150px;">
				<div>업로드할 파일</div>	
				<div style="height: 80%;">
					<div id="fileListDiv" style="border: solid 1px; float: left; width: 85%; height:100%; overflow: auto">
					</div>
					<div style="float: right; width:14%;" align="center">
						<button id="plusBtn" type="button">파일추가</button><br/>
						<button id="minusBtn" type="button">파일삭제</button><br/>
					</div>
				</div>
			</div>
			
			<!-- 전송버튼 -->
		    <input type="submit" value="submit">
		</fieldset>
	</form>
	
	<script src="/myapp/resources/js/script.js"></script>
</body>
</html>
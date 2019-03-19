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
			<div class="formGroup">
				<label>첨부파일: </label>
				<input type="file" name="uploadFile" multiple/>
			</div>
			<!-- 전송버튼 -->
		    <input type="submit" value="submit">
		</fieldset>
	</form>
	
	<script src="/myapp/resources/js/script.js"></script>
</body>
</html>
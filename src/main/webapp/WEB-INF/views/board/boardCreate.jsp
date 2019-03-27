<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>boardCreate</title>
	
	<!-- include libraries(jQuery) -->
	<script src="/myapp/resources/js/jquery-3.3.1.min.js"></script> 
	
	<!-- include summernote css/js -->
	<link href="/myapp/resources/summernote/summernote-lite.css" rel="stylesheet">
	<script src="/myapp/resources/summernote/summernote-lite.min.js"></script>

	<!-- include pure.css -->
	<link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css" 
		integrity="sha384-nn4HPE8lTHyVtfCBi5yW9d20FjT8BJwUXyWZT9InLYax14RDjBj46LmSztkmNP9w" 
		crossorigin="anonymous">

	<!-- include custom css-->
	<link rel="stylesheet" href="/myapp/resources/css/style.css">

</head>

<body>
	<jsp:include page="../header.jsp"></jsp:include>

	<div class="pure-g">
		<div class="pure-u-1-1">
			<form id="boardCreateForm" method="POST" enctype="multipart/form-data" class="pure-form pure-form-stacked">
				<fieldset>
		
					<label for="title">제목</label>
					<input id="title" type="text" name="title" value="${board.title}" />

					<label for="summernote">내용</label>
					<textarea id="summernote" name="contents">${board.contents}</textarea>

					<!-- 파일 업로드 폼-->
					<input id="file" type="file" name="uploadFile" multiple hidden />

					<div id="fileUploadDiv">
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

					<!-- 전송버튼 -->
					<button id="uploadBtn" type="submit" class="pure-button pure-button-primary">submit</button>

				</fieldset>
			</form>
		</div>
	</div>

	<!-- include custom js-->
	<script src="/myapp/resources/js/script.js"></script>
	<script type="text/javascript">
		$('#summernote').summernote('enable');
	</script>
</body>

</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

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
					
					<label for="userId">작성자</label>
					<input id="userId" name="userId" value="${board.userId}" readonly />
					
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
					
					<sec:authentication property="principal" var="pinfo" /> 
					<sec:authorize access="isAuthenticated()">
						<c:if test="${pinfo.username == board.userId }">
							<!-- 수정버튼 -->
							<button id="modifyBtn" type="button" class="pure-button">modify</button>
		
							<!-- 전송버튼 -->
							<button id="uploadBtn" type="submit" class="pure-button button-success" style="display: none;">submit</button>
		
							<!-- 삭제버튼 -->
							<button id="deleteBtn" type="button" class="pure-button button-error" style="display: none;">delete</button>
						
						</c:if>
					</sec:authorize>
				</fieldset>
			</form>
		</div>

		<div>
			<form id="moveToBoardListForm" method="POST" action='/myapp/board/list'>
				<input type='hidden' name='page' value="${searchInfo.page}">
				<input type='hidden' name='flag' value="${searchInfo.flag}">
				<input type='hidden' name='value' value="${searchInfo.value}">
				<input type='hidden' name='startDate' value="${searchInfo.startDate}">
				<input type='hidden' name='endDate' value="${searchInfo.endDate}">
				<input type='hidden' name='${_csrf.parameterName}' value="${_csrf.token}">
			</form>
		</div>
	</div>

	
	<script type="text/javascript">
		/* 
		 * textarea를 비활성화한다.
		 */
		$('#summernote').summernote('disable');
		
		/* 
		 * modify 버튼을 클릭했을 때 수정 모드로 전환한다.
		 */
		$("#modifyBtn").click(function (e) {
			handleModifyBtn();
		});

		var isDisabled = true; // modify 버튼 초기 상태: 비활성화

		function handleModifyBtn() {
			var title = $("#title");
			var contents = $("#summernote");
			var fileUploadDiv = $("#fileUploadDiv");
			var uploadBtn = $("#uploadBtn");
			var deleteBtn = $("#deleteBtn");

			if (!isDisabled) {
				// modify 버튼이 활성화 되어 있을 때
				title.prop('disabled', true);
				contents.summernote('disable');
				fileUploadDiv.hide()
				uploadBtn.hide();
				deleteBtn.hide();
			} else {
				// modify 버튼이 비활성화 되어 있을 때
				title.prop('disabled', false);
				contents.summernote('enable');
				fileUploadDiv.show();
				uploadBtn.show();
				deleteBtn.show();
			}
			isDisabled = !isDisabled;
		}

		/*
		 * formData(multipart/form-data)를 받아 지정된 url에 ajax를 호출한다.
		 */
		$("#boardDetailform").submit(function (e) {
			e.preventDefault();
			var newFileList = new FileList(currentListOfFile);
			$("#file").off();
			$("#file")[0].files = newFileList;
			var formData = new FormData($("#boardDetailform")[0]);
			$.ajax({
				url: '/myapp/board/update',
				type: 'POST',
				data: formData,
				contentType: false,
				processData: false,
				beforeSend: function(xhr) {
					xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}")
				},
				success: function (data) {
					// DB에서 업데이트 성공 시 이전에 위치했던 boardList 페이지로 이동한다.
					$("#moveToBoardListForm").submit();
				},
				error: function (xhr) {
					alert(xhr.responseText);
				}
			});
		});

		/*
		 * 파일 이름을 클릭했을 때 POST 방식으로 다운로드 하도록 이벤트를 처리한다.
		 */
		$(".fileDownload").click(function (e) {
			e.preventDefault();
			var uuid = $(this).attr("href");
			var form = $('<form>').attr({
				method: 'POST',
				action: '/myapp/board/download'
			}).appendTo("body");
			$('<input>').attr({
				type: 'hidden',
				name: 'uuid',
				value: uuid
			}).appendTo(form);
			form.submit();
		});

		/* 
		 * 'delete' 버튼을 클릭하면 게시글 삭제 이벤트를 수행한다.
		 */
		$("#deleteBtn").click(function (e) {
			$.ajax({
				url: '/myapp/board/delete',
				type: 'POST',
				data: {
					no: $("#no").attr("value"),
					userId: $("#userId").attr("value")
				},
				beforeSend: function(xhr) {
					xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}")
				},
				success: function (data) {
					// DB에서 삭제 성공 시 이전에 위치했던 boardList 페이지로 이동한다.
					$("#moveToBoardListForm").submit();
				},
				error: function (xhr) {
					alert(xhr.responseText);
				}
			});
		});
	</script>
	<script src="/myapp/resources/js/script.js"></script>
</body>

</html>
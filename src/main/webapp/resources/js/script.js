/*
 * BoardList.jsp 에서 검색버튼을 클릭했을 때 AJAX요청을 수행한다.
 */
$("#searchBtn").on("click", function (e) {
	var formData = new FormData($("#searchForm")[0]);
	$.ajax({
		url: '/myapp/board/search',
		type: 'POST',
		data: formData,
		processData: false,
		contentType: false,
		success: function () {
			$("#searchForm").submit();
		},
		error: function (xhr) {
			alert(xhr.responseText);
		}
	});
});

/*
 * boardList.jsp 에서 게시글 링크를 클릭했을 때 POST 요청이 이루어지도록 이벤트를 처리한다.
 */
$(".moveToBoardDetail").on("click", function (e) {
	e.preventDefault();
	var no = $(this).attr("href");
	$('<input>').attr({
		type: 'hidden',
		name: 'no',
		value: no
	}).appendTo('#moveToBoardDetailForm');
	$("#moveToBoardDetailForm").submit();
});

/*
 * boardCreate.jsp, boardDetail.jsp 파일 업로드 폼 처리
 */
var currentListOfFile = []; // 현재 업로드 파일 목록

/*
 * 현재 업로드 파일 목록 Array를 fileList 객체로 변환한다.
 */
FileList = function (items) {
	const dataTransfer = new DataTransfer;
	for (let item of items) {
		dataTransfer.items.add(item);
	}
	return dataTransfer.files;
};

/*
 * '파일추가' 버튼을 클릭하면 file input을 활성화한다.
 */
$("#plusBtn").on("click", function (e) {
	$("#file").click();
});

/*
 * 파일을 업로드하면 현재 업로드 파일 목록에 파일을 추가한다.
 */
$("#file").change(function () {
	var fileList = $("#file")[0].files;
	for (var item of fileList) {
		// <input type="file"> 의 FileList를 순회한다.
		currentListOfFile.push(item);
	}
	modifyFileListDiv();
});

/*
 * fileList의 내용을 현재 업로드 파일 목록과 동기화한다.
 */
function modifyFileListDiv() {
	$("#fileListDiv").empty();
	for (var item of currentListOfFile) {
		// 현재 업로드 파일 목록를 순회한다.
		var fileName = item.name;
		$("#fileListDiv").append("<input type='checkbox' value='" + fileName + "'/>" + fileName + "<br/>");
	}
}

/*
 * '파일삭제' 버튼을 누르면 현재 업로드 파일 목록에서 체크된 파일을 삭제한다.
 */
$("#minusBtn").on("click", function (e) {
	var checkedList = $("#fileListDiv :checked");
	for (item of checkedList) {
		// 파일이 체크된 상태일 때
		currentListOfFile.splice($.inArray(item.value, currentListOfFile), 1);
	}
	modifyFileListDiv();
});

/*
 * boardCreate.jsp의 formData(multipart/form-data)를 받아 지정된 url에 ajax를 호출한다.
 */
$("#boardCreateForm").submit(function (e) {
	e.preventDefault();
	var newFileList = new FileList(currentListOfFile);
	currentListOfFile = []; // #file 의 change 이벤트에 영향을 미치지 않도록 현재 업로드 파일 목록을 비운다.
	$("#file")[0].files = newFileList;
	var formData = new FormData($("#boardCreateForm")[0]);
	$.ajax({
		url: '/myapp/board/create',
		type: 'POST',
		data: formData,
		contentType: false,
		processData: false,
		success: function (data) {
			// DB에서 생성 성공 시 boardList 페이지로 이동한다.
			window.location.href = '/myapp/board/list';
		},
		error: function (xhr) {
			alert(xhr.responseText);
		}
	});
});

/* 
 * boardDetail.jsp의 modify 버튼을 클릭했을 때 수정 모드로 전환한다.
 */
$("#modifyBtn").on("click", function (e) {
	handleModifyBtn();
});

var isDisabled = true; // modify 버튼 초기 상태: 비활성화

function handleModifyBtn() {
	var title = $("#title");
	var contents = $("#contents");
	var fileUploadDiv = $("#fileUploadDiv");
	var uploadBtn = $("#uploadBtn");
	var deleteBtn = $("#deleteBtn");

	if (!isDisabled) {
		// modify 버튼이 활성화 되어 있을 때
		title.prop('disabled', true);
		contents.prop('disabled', true);
		fileUploadDiv.css({
			'display': 'none'
		});
		uploadBtn.prop('type', 'hidden');
		deleteBtn.css({
			'display': 'none'
		});
	} else {
		// modify 버튼이 비활성화 되어 있을 때
		title.prop('disabled', false);
		contents.prop('disabled', false);
		fileUploadDiv.css({
			'display': ''
		});
		uploadBtn.prop('type', 'submit');
		deleteBtn.css({
			'display': ''
		});
	}
	isDisabled = !isDisabled;
}



/*
 * boardDetail.jsp의 formData(multipart/form-data)를 받아 지정된 url에 ajax를 호출한다.
 */
$("#boardDetailform").submit(function (e) {
	e.preventDefault();
	var newFileList = new FileList(currentListOfFile);
	currentListOfFile = []; // #file 의 change 이벤트에 영향을 미치지 않도록 현재 업로드 파일 목록을 비운다.
	$("#file")[0].files = newFileList;
	var formData = new FormData($("#boardDetailform")[0]);
	$.ajax({
		url: '/myapp/board/update',
		type: 'POST',
		data: formData,
		contentType: false,
		processData: false,
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
 * BoardDetail.jsp 에서 파일 이름을 클릭했을 때 POST 방식으로 다운로드 하도록 이벤트를 처리한다.
 */
$(".fileDownload").on("click", function (e) {
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
 * BoardDetail.jsp 에서 'delete'버튼을 클릭하면 게시글 삭제 이벤트를 수행한다.
 */
$("#deleteBtn").on("click", function (e) {
	$.ajax({
		url: '/myapp/board/delete',
		type: 'POST',
		data: {
			no: $("#no").attr("value")
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
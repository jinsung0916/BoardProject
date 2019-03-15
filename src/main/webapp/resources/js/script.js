/*
 * boardList.jsp 에서 페이지 링크를 클릭했을 때 POST 요청이 이루어지도록 이벤트 처리
 */
function moveToOtherPage(page){
	$("body").append("<form id='hiddenForm' method='POST' action='/myapp/board/list'>");
	$("#hiddenForm").append("<input type='hidden' name='page' value=" + page + ">");	
	$("#hiddenForm").submit();
}

$(".moveToOtherPage").on("click", function(e) {
	e.preventDefault();
	var page = $(this).attr("href")
	moveToOtherPage(page);
});

/*
 * boardList.jsp 에서 게시글 링크를 클릭했을 때 POST 요청이 이루어지도록 이벤트 처리
 */
function moveToBoardDetail(no, page){
	$("body").append("<form id='hiddenForm' method='POST' action='/myapp/board/detail'>");
	$("#hiddenForm").append("<input type='hidden' name='no' value=" + no + ">");
	$("#hiddenForm").append("<input type='hidden' name='page' value=" + page + ">");	
	$("#hiddenForm").submit();
}

$(".moveToBoardDetail").on("click", function(e) {
	e.preventDefault();
	var no = $(this).attr("href");
	var page = $("#currentPage").text();
	moveToBoardDetail(no, page);
});

/* 
 * boardDetail.jsp의 modify 버튼 처리
 */
var isDisabled = true; // modify 버튼 초기 상태: 비활성화

function handleModifyBtn(){
	 var title = $("#title"); 
     var contents = $("#contents");
     var uploadFile = $("#file");
     var uploadBtn = $("#uploadBtn");
     
     if(!isDisabled){
    	 // modify 버튼이 활성화 되어 있을 때
    	 title.prop('disabled', true);
         contents.prop('disabled', true);
         uploadFile.prop('disabled', true);
         uploadBtn.prop('type', 'hidden');
     }
     else{
    	 // modify 버튼이 비활성화 되어 있을 때
         title.prop('disabled', false);
         contents.prop('disabled', false);
         uploadFile.prop('disabled', false);
         uploadBtn.prop('type', 'submit');
     }
     isDisabled = !isDisabled;
}

$("#modifyBtn").on("click", function(e){
	e.preventDefault();
	handleModifyBtn();
});

/*
 * formData(multipart/form-data)를 받아 지정된 url에 ajax 요청
 */
function multipartFormAjaxCall(url, formData){
	  $.ajax({
	    url: url,
	    type: 'POST',
	    data: formData,
	    contentType: false,
	    processData: false,
	    success: function (data) {
	    	// DB단에서 업데이트 성공 시 이전에 위치했던 boardList 페이지로 이동한다.
	    	moveToOtherPage(data);
	    },
	    fail: function(data){
	    	// DB단에서 업데이트 실패 시 실패 메시지를 띄운다. 
	        alert('request failed');
	     }
	  });
}

$("#form").submit(function(e){
	  e.preventDefault();
	  var url = '/myapp/board/update';
	  var formData = new FormData($("#form")[0]);
	  multipartFormAjaxCall(url, formData);
});


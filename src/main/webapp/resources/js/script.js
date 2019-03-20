/*
 * boardList.jsp 에서 게시글 링크를 클릭했을 때 POST 요청이 이루어지도록 이벤트 처리
 */
$(".moveToBoardDetail").on("click", function(e) {
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
 * boardCreate.jsp의 formData(multipart/form-data)를 받아 지정된 url에 ajax 요청
 */
$("#boardCreateForm").submit(function(e){
	  e.preventDefault();
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
		    fail: function(data){
		    	// DB에서 업데이트 실패 시 실패 메시지를 띄운다. 
		        alert(data);
		    }
	  });

});

/* 
 * boardDetail.jsp의 modify 버튼 처리
 */
var isDisabled = true; // modify 버튼 초기 상태: 비활성화

function handleModifyBtn(){
	 var title = $("#title"); 
     var contents = $("#contents");
     var fileUploadDiv = $("#fileUploadDiv");
     var uploadBtn = $("#uploadBtn");
     
     if(!isDisabled){
    	 // modify 버튼이 활성화 되어 있을 때
    	 title.prop('disabled', true);
         contents.prop('disabled', true);
         fileUploadDiv.css({'display': 'none'});
         uploadBtn.prop('type', 'hidden');
     }
     else{
    	 // modify 버튼이 비활성화 되어 있을 때
         title.prop('disabled', false);
         contents.prop('disabled', false);
         fileUploadDiv.css({'display': ''});
         uploadBtn.prop('type', 'submit');
     }
     isDisabled = !isDisabled;
}

$("#modifyBtn").on("click", function(e){
	e.preventDefault();
	handleModifyBtn();
});

/*
 * boardDetail.jsp 파일 업로드 폼 처리
 */
var currentListOfFile = [];

$("#plusBtn").on("click", function(e) {
	$("#file").click();
});

$("#minusBtn").on("click", function(e){
	var minusList = $("#fileListDiv").children();
	for(item of minusList){
		if(item.checked) {
			currentListOfFile = removeFromCurrentListOfFile(currentListOfFile, item.value);
		}
	}
	modifyUploadFileListDiv();
});

$("#file").change(function(){
	var fileList = $("#file")[0].files;
	for(var i=0; i<fileList.length; i++) {
		currentListOfFile.push(fileList[i]);
	}
	modifyUploadFileListDiv();
});


function modifyUploadFileListDiv(){
	$("#fileListDiv").empty();
	for(var i=0; i < currentListOfFile.length; i++) {
		$("#fileListDiv").append("<input type='checkbox' value='" + currentListOfFile[i].name + "'/>" +  currentListOfFile[i].name + "<br/>");
	}
}

function removeFromCurrentListOfFile(arr, value) {
   return arr.filter(function(item) {
       return item.name != value;
   });
}

FileList = function(items) {
    const dataTransfer = new DataTransfer;
    for (let item of items) {
    	dataTransfer.items.add(item);
    }
    return dataTransfer.files;
};

/*
 * boardDetail.jsp의 formData(multipart/form-data)를 받아 지정된 url에 ajax 요청
 */
$("#boardDetailform").submit(function(e){
	  e.preventDefault();
	  $("#file")[0].files = new FileList(currentListOfFile);
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
		    fail: function(data){
		    	// DB에서 업데이트 실패 시 실패 메시지를 띄운다. 
		        alert(data);
		    }
	  });
});

/*
 *
 */
$(".fileDownload").on("click", function(e){
	e.preventDefault();
	
	var uuid = $(this).attr("href"); 
	
	var form = $('<form>').attr({
	    method: 'POST',
	    action: '/myapp/board/download'
	});

	var input = $('<input>').attr({
		type: 'hidden',
	    name: 'uuid',
	    value: uuid
	}).appendTo(form);
	
	$(document.body).append(form);
	form.submit();
});


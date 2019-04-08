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
$("#plusBtn").click(function (e) {
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
		var label = $("<label>").attr({
			for: fileName,
		}).addClass("pure-checkbox");
		
		var checkboxInput = $("<input>").attr({
			id: fileName,
			value: fileName,
			type: "checkbox",
		}).appendTo(label);

		label.append(fileName);	
		$("#fileListDiv").append(label);
	}
}

/*
 * '파일삭제' 버튼을 누르면 현재 업로드 파일 목록에서 체크된 파일을 삭제한다.
 */
$("#minusBtn").click(function (e) {
	var checkedList = $("#fileListDiv :checked");
	for (item of checkedList) {
		// 파일이 체크된 상태일 때
		var idx = -1;
		for(var i=0; i<currentListOfFile.length; i++){
			// 현재 업로드 파일 목록을 순회하며 파일 이름이 같은 객체를 찾는다.
			if(currentListOfFile[i].name == item.value){
				idx = i;
				break;	
			}
		}
		currentListOfFile.splice(idx, 1);
	}
	modifyFileListDiv();
});

$('#summernote').summernote({
	toolbar: [
		['style', ['bold', 'italic', 'underline', 'clear']],
		['font', ['strikethrough', 'superscript', 'subscript']],
		['fontsize', ['fontsize']],
		['color', ['color']],
		['para', ['ul', 'ol', 'paragraph']],
		['height', ['height']]
	]
});
package com.joins.myapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.joins.myapp.domain.BoardDTO;
import com.joins.myapp.domain.FileDTO;
import com.joins.myapp.service.BoardService;
import com.joins.myapp.util.FileUploadHandler;
import com.joins.myapp.util.FolderNameStrategy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BoardCreateAndUpdateTemplate {

    protected BoardService service;
    private String baseUploadDirectory;

    public BoardCreateAndUpdateTemplate(BoardService service, String baseUploadDirectory) {
	this.service = service;
	this.baseUploadDirectory = baseUploadDirectory;
    }

    protected abstract boolean isSuccessful(BoardDTO board);

    public ResponseEntity<String> execute(BoardDTO board, MultipartFile[] uploadFile) {
	if ("".equals(board.getTitle())) {
	    // 게시글 제목이 존재하지 않을 경우 400을 반환한다.
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("제목을 입력하세요.");
	}

	if (!isSuccessful(board)) {
	    // DB에서 UPDATE가 실패할 경우 500을 반환한다.
	    log.error("게시글 갱신 실패");
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 등록 중 문제가 발생했습니다.");
	}

	return processFileUpload(board, uploadFile);
    }

    private ResponseEntity<String> processFileUpload(BoardDTO board, MultipartFile[] uploadFile) {
	if (!FileUploadHandler.isEmpty(uploadFile)) {
	    // 업로드 파일이 존재할 때
	    try {
		List<FileDTO> fileList = FileUploadHandler.uploadFile(uploadFile, baseUploadDirectory,
			new FolderNameStrategy() {
			    @Override
			    public String getFolderName() {
				return String.valueOf(board.getNo());
			    }
			});
		setForeignKeyToFileDTO(fileList, board);
	    } catch (Exception e) {
		// 파일 업로드에 실패할 경우 500을 반환한다.
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 업로드 중 문제가 발생했습니다.");
	    }
	}
	return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    private void setForeignKeyToFileDTO(List<FileDTO> fileList, BoardDTO board) {
	for (FileDTO file : fileList) {
	    // FileDTO에 Foreign Key 할당 후 DB에 저장한다.
	    file.setBoardNo(board.getNo());
	    service.attachFile(file);
	}
    }

}

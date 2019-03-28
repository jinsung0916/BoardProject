package com.joins.myapp.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.joins.myapp.domain.BoardDTO;
import com.joins.myapp.domain.FileDTO;
import com.joins.myapp.domain.PageDTO;
import com.joins.myapp.domain.SearchInfoDTO;
import com.joins.myapp.exception.ResourceNotFoundException;
import com.joins.myapp.service.BoardService;
import com.joins.myapp.util.FileDeleteHandler;
import com.joins.myapp.util.FileDownloadHandler;
import com.joins.myapp.util.FileUploadHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * 게시판 CRUD 컨트롤러
 */
@Controller
@RequestMapping("/board")
@Slf4j
public class BoardController {
    @Value("#{file['baseUploadDirectory']}")
    private String baseUploadDirectory;
    @Value("#{pagination['defaultPage']}")
    private int defaultPage;
    @Value("#{pagination['defaultItemsPerPage']}")
    private int defaultItemsPerPage;
    @Value("#{pagination['defaultPagesPerOneLine']}")
    private int defaultPagesPerOneLine;
    
    @Autowired
    private BoardService service;
    
    /**
     * 1. 개요: POST /board/search
     * 2. 처리내용: 조회 결과가 존재하는지 확인한다.
     * 3. 입력 Data: 검색정보
     * 4. 출력 Data: 응답 헤더와 상태메시지
     */
    @PostMapping(value="/search", produces="text/plain;charset=UTF-8")
    public @ResponseBody ResponseEntity<String> hasSearchResult(SearchInfoDTO searchInfo){
	if("".equals(searchInfo.getSearch())) {
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("내용을 입력하세요.");
	} else if(service.hasSearchResult(searchInfo)) {
	    // 조회 결과가 존재할 때
	    return ResponseEntity.status(HttpStatus.OK).build();
	}
	else {
	    // 조회 결과가 존재하지 않을 때, 404를 반환한다.
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("검색 결과가 존재하지 않습니다.");
	}
    }
    
    /**
     * 1. 개요: GET|POST /board/list 
     * 2. 처리내용: 게시글 목록 페이지를 반환한다. 
     * 3. 입력 Data: 페이지 번호, 페이지 당 게시글 수
     * 4. 출력 Data: 
     */
    @RequestMapping("/list")
    public String boardList(SearchInfoDTO searchInfo, Model model) {
	if(searchInfo.getPage() <= 0) {
	    // GET으로 접근 시 기본 페이지를 반환한다. 
	    searchInfo.setPage(defaultPage);
	}
	if(searchInfo.getItemsPerPage() == 0) {
	    // 페이지 크기에 대한 요청이 없으면 기본 페이지 크기를 할당한다.
	    searchInfo.setItemsPerPage(defaultItemsPerPage);   
	}
	
	PageDTO<BoardDTO> pageObj = service.findPaginated( 
		defaultPagesPerOneLine, searchInfo);	
	if(pageObj.getSearchInfo().getPage() > pageObj.getEndPage()) {
	    // 잘못된 페이지를 요청했을 경우 예외를 발생시킨다.
	    throw new ResourceNotFoundException();
	}
	
	model.addAttribute("pageObj", pageObj);
	return "board/boardList";
    }

    /**
     * 1. 개요: POST /board/detail 
     * 2. 처리내용: 게시글 상세정보 페이지를 반환한다. 
     * 3. 입력 Data: 게시글 번호, URL을 호출한 페이지 번호 
     * 4. 출력 Data: 
     */
    @PostMapping("/detail")
    public String boardDetail(long no, SearchInfoDTO searchInfo, Model model) {
	BoardDTO board = service.findOne(no);
	if(board == null) {
	    // 해당 게시글이 존재하지 않을 경우 예외를 발생시킨다.
	    throw new ResourceNotFoundException();
	}
	
	model.addAttribute("board", board);
	model.addAttribute("searchInfo", searchInfo);
	return "board/boardDetail";
    }
    
    /**
     * 1. 개요: GET /board/create 
     * 2. 처리내용: 게시글 생성 페이지를 반환한다.
     * 3. 입력 Data:
     * 4. 출력 Data: 
     */
    @GetMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String boardCreate() {
	return "board/boardCreate";
    }
    
    /**
     * 1. 개요: POST /board/create 
     * 2. 처리내용: 게시글을 생성한다. 
     * 3. 입력 Data: 게시글 데이터와 업로드한 파일 
     * 4. 출력 Data: 응답 헤더와 상태메시지
     */
    @PostMapping(value="/create", produces="text/plain;charset=UTF-8")
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody ResponseEntity<String> boardCreate(BoardDTO board, MultipartFile[] uploadFile) {
	if("".equals(board.getTitle())) {
	    // 게시글 제목이 존재하지 않을 경우 400을 반환한다.
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("제목이 존재하지 않습니다.");
	}
	
	if(!service.create(board)) {
	    // DB에서 INSERT가 실패할 경우 500을 반환한다.
	    log.error("게시글 생성 실패");
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 등록 중 문제가 발생했습니다.");
	}
	    
	ResponseEntity<String> result = null;
	try {
	    fileUploadProcess(board, uploadFile);
	    result = ResponseEntity.status(HttpStatus.ACCEPTED).build();
	} catch (Exception e) {
	    // 파일 업로드에 실패할 경우 500을 반환한다.
	    log.error(e.getMessage());
	    result = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 업로드 중 문제가 발생했습니다.");
	}
	return result;
    }
    
    /**
     * 1. 개요: POST /board/update 
     * 2. 처리내용: ajax를 통해 게시글을 갱신한다.
     * 3. 입력 Data: 게시글 데이터, 첨부파일
     * 4. 출력 Data: 응답 헤더와 상태메시지
     */
    @PostMapping(value="/update", produces="text/plain;charset=UTF-8")
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody ResponseEntity<String> boardUpdate(BoardDTO board, MultipartFile[] uploadFile) {
	if("".equals(board.getTitle())) {
	    // 게시글 제목이 존재하지 않을 경우 400을 반환한다.
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("제목을 입력하세요.");
	}
	
	if(!service.update(board)) {
	    // DB에서 UPDATE가 실패할 경우 500을 반환한다.
	    log.error("게시글 갱신 실패");
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 등록 중 문제가 발생했습니다.");
	}	
	    
	ResponseEntity<String> result = null;
	try {
	    fileUploadProcess(board, uploadFile);
	    result = ResponseEntity.status(HttpStatus.ACCEPTED).build();
	} catch (Exception e) {
	    // 파일 업로드에 실패할 경우 500을 반환한다.
	    log.error(e.getMessage());
	    result = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 업로드 중 문제가 발생했습니다.");
	}
	return result;
    }
    
    /**
     * 1. 개요: POST board/delete
     * 2. 처리내용: 게시글을 삭제한다.
     * 3. 입력 Data: 게시글 번호
     * 4. 출력 Data: 응답 헤더와 상태메시지
     */
    @PostMapping("/delete")
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody ResponseEntity<String> deleteBoard(long no) {
	BoardDTO board = service.findOne(no);
	if(board == null) {
	    // 게시글이 존재하지 않을 때 404를 반환한다.
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 게시글이 존재하지 않습니다.");
	} else if(!service.deleteById(no)) {
	    // DB에서 게시글 삭제가 실패했을 때 500을 반환한다.
	    log.error("게시글 삭제 실패");
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 삭제 중 문제가 발생했습니다.");
	}
	List<FileDTO> deleteFileList = board.getFileList();
	if(!deleteFileList.isEmpty()) {
	    // 첨부파일이 존재할 경우 첨부파일 폴더를 삭제한다.
	    FileDeleteHandler.deleteFolder(deleteFileList.get(0).getFilePath());
	}
	return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    /**
     * 1. 개요: GET /board/download?uuid={uuid} 
     * 2. 처리내용: 첨부파일 다운로드를 처리한다. 
     * 3. 입력 Data: 첨부파일의 PK 
     * 4. 출력 Data: 첨부파일
     */
    @PostMapping("/download")
    public @ResponseBody ResponseEntity<Resource> downloadFiles(@RequestParam("uuid") String uuid) {
	FileDTO file = service.getFileByUUID(uuid);
	String absoluteFilePath = file.toString();

	Resource resource = null;
	HttpHeaders headers = new HttpHeaders();
	try {
	    resource = FileDownloadHandler.downloadFile(absoluteFilePath);
	    headers.add("Content-Disposition",
		    "attachment; filename=" + new String(resource.getFilename().getBytes("utf-8"), "ISO-8859-1"));
	} catch (ResourceNotFoundException e) {
	    // 경로에 파일이 존재하지 않으면 404를 반환한다.
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	} catch (UnsupportedEncodingException e) {
	    // 인코딩 에러가 발생했을 시 로그를 출력한다.
	    log.error(e.getMessage());
	}
	return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
    }
        
    /**
     * 1. 개요: 코드 중복 제거를 위한 private 매소드
     * 2. 처리내용: 파일DTO에 게시글 정보를 담아 DB에 저장한다.
     * 3. 입력 Data: 게시글DTO, 업로드할 파일
     * 4. 출력 Data: 
     * @throws IOException 
     * @throws IllegalStateException 
     */
    private void fileUploadProcess(BoardDTO board, MultipartFile[] uploadFile) throws IllegalStateException, IOException {
	if (!FileUploadHandler.isEmpty(uploadFile)) {
	    // 업로드 파일이 존재할 때
	    List<FileDTO> list = FileUploadHandler.uploadFile(uploadFile, baseUploadDirectory, String.valueOf(board.getNo()));
	    for(FileDTO file : list) {
		// FileDTO에 Foreign Key 할당 후 DB에 저장한다.
		file.setBoardNo(board.getNo());
		service.attachFile(file);
	    }
	}
    }
       
}
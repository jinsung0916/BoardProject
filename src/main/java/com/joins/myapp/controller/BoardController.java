package com.joins.myapp.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.joins.myapp.service.BoardService;
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
    @Value("#{file['baseUploadFolder']}")
    private String baseUploadFolder;
    @Value("#{pagination['defaultPage']}")
    private int defaultPage;
    @Value("#{pagination['defaultItemsPerPage']}")
    private int defaultItemsPerPage;
    @Value("#{pagination['defaultPagesPerOneLine']}")
    private int defaultPagesPerOneLine;
    
    @Autowired
    private BoardService service;
    
    /**
     * 1. 개요: GET|POST /board/list 
     * 2. 처리내용: 게시글 목록 페이지를 반환한다. 
     * 3. 입력 Data: 페이지 번호, 페이지 당 게시글 수
     * 4. 출력 Data: 
     */
    @RequestMapping("/list")
    public String BoardList(Integer page, SearchInfoDTO searchInfo, Model model) {
	if(page == null) {
	    // GET으로 접근 시 기본 페이지를 반환한다. 
	    page = defaultPage;
	}
	
	PageDTO<BoardDTO> pageObj = service.findPaginated(page, defaultItemsPerPage, 
		defaultItemsPerPage, searchInfo);
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
    public String boardDetail(long no, int page, Model model) {
	BoardDTO board = service.findOne(no);
	model.addAttribute("board", board);
	model.addAttribute("page", page);
	return "board/boardDetail";
    }

    /**
     * 1. 개요: GET|POST /board/create 
     * 2. 처리내용: get 요청 시 게시글 생성 페이지를 반환하고, post 요청 시 게시글을 생성한 후 목록 페이지로 리다이렉트한다. 
     * 3. 입력 Data: 게시글 데이터와 업로드한 파일(post 요청 시) 
     * 4. 출력 Data: 
     */
    @RequestMapping("/create")
    public String boardCreate(HttpServletRequest req, BoardDTO board, MultipartFile[] uploadFile) {
	if ("POST".equals(req.getMethod())) {
	    // POST 요청 처리
	    service.create(board);
	    fileUploadProcess(board, uploadFile);
	    return "redirect:/board/list";
	} else {
	    // GET 요청 처리
	    return "board/boardCreate";
	}
    }
    
    /**
     * 1. 개요: POST /board/update 
     * 2. 처리내용: ajax를 통해 게시글을 갱신한다.
     * 3. 입력 Data: 게시글 데이터, 첨부파일
     * 4. 출력 Data: 돌아갈 페이지 번호
     */
    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<Integer> boardUpdate(BoardDTO board, MultipartFile[] uploadFile, int page) {
	ResponseEntity<Integer> result = null;
	if (service.update(board)) {
	    // 업데이트가 성공할 때
	    fileUploadProcess(board, uploadFile);
	    result = ResponseEntity.status(HttpStatus.ACCEPTED).body(page);
	} else {
	    // 업데이트가 실패할 때
	    result = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	return result;
    }

    /**
     * 1. 개요: GET /board/download?uuid={uuid} 
     * 2. 처리내용: 첨부파일 다운로드를 처리한다. 
     * 3. 입력 Data: 첨부파일의 PK 
     * 4. 출력 Data: 첨부파일
     */
    @GetMapping("/download")
    @ResponseBody
    public ResponseEntity<Resource> downloadFiles(@RequestParam("uuid") String uuid) {
	// TODO POST 방식으로 파라미터를 읽어오도록 변경
	FileDTO file = service.getFileByUUID(uuid);
	String absoluteFilePath = file.toString();
	Resource resource = FileDownloadHandler.downloadFile(absoluteFilePath);

	HttpHeaders headers = new HttpHeaders();
	try {
	    // 한글 파일이름이 깨지지 않도록 인코딩 처리 후 header에 파일 이름을 명시한다.
	    headers.add("Content-Disposition",
		    "attachment; filename=" + new String(resource.getFilename().getBytes("utf-8"), "ISO-8859-1"));
	} catch (UnsupportedEncodingException e) {
	    log.error(e.getMessage());
	}

	return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
    }
    
    /**
     * 1. 개요: 코드 중복 제거를 위한 private 매소드
     * 2. 처리내용: 파일DTO에 게시글 정보를 담아 DB에 저장한다.
     * 3. 입력 Data: 게시글DTO, 업로드할 파일
     * 4. 출력 Data: 
     */
    private void fileUploadProcess(BoardDTO board, MultipartFile[] uploadFile) {
	if (!FileUploadHandler.isEmpty(uploadFile)) {
	    // 업로드 파일이 존재할 때
	    List<FileDTO> list = FileUploadHandler.uploadFile(uploadFile, baseUploadFolder);
	    for(FileDTO file : list) {
		// FileDTO에 Foreign Key 할당 후 DB에 저장한다.
		file.setBoardNo(board.getNo());
		service.attachFile(file);
	    }
	}
    }
    
}

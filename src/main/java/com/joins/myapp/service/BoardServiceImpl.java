package com.joins.myapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joins.myapp.domain.BoardDTO;
import com.joins.myapp.domain.FileDTO;
import com.joins.myapp.domain.PageDTO;
import com.joins.myapp.domain.SearchInfoDTO;
import com.joins.myapp.persistence.BoardMapper;
import com.joins.myapp.persistence.FileMapper;
import com.joins.myapp.util.PaginationHandler;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BoardServiceImpl implements BoardService {
    
    @Autowired
    private BoardMapper boardMapper;
    @Autowired
    private FileMapper fileMapper;
    
    /**
     * 1. 개요:
     * 2. 처리내용: 게시글 PK로 게시글 하나를 조회하여 반환한다.
     * 3. 입력 Data: 게시글 PK
     * 4. 출력 Data: 게시글
     */
    @Override
    public BoardDTO findOne(long id) {
	BoardDTO board = boardMapper.findByNo(id);
	return board;
    }
    
    /**
     * 1. 개요:
     * 2. 처리내용: 페이징된 게시글 리스트를 반환한다.
     * 3. 입력 Data: 게시글 조회 정보
     * 4. 출력 Data: 게시글 리스트
     */
    @Override
    public PageDTO<BoardDTO> findPaginated(int pagesPerOneLine, SearchInfoDTO searchInfo) {
	int page = searchInfo.getPage();
	int itemsPerPage = searchInfo.getItemsPerPage();
	
	// mariaDB LIMIT절 조건 설정
	int startIdx = (page - 1) * itemsPerPage;
	searchInfo.setStartIdx(startIdx);
	
	List<BoardDTO> boards = boardMapper.findPagenated(searchInfo);
	
	int totalSizeOfTable = boardMapper.countAll(searchInfo);
	PageDTO<BoardDTO> pageObj = PaginationHandler.generatePageDTO(searchInfo, pagesPerOneLine, totalSizeOfTable);
	pageObj.setContents(boards);	
	return pageObj;
    }
    
    /**
     * 1. 개요:
     * 2. 처리내용: 새로운 게시글을 생성한다.
     * 3. 입력 Data: 게시글 데이터
     * 4. 출력 Data: 성공시 true, 실패시 false
     */
    @Override
    public boolean create(BoardDTO board) {
	return boardMapper.insert(board) == 1;
    }
    
    /**
     * 1. 개요:
     * 2. 처리내용: 게시글 내용을 갱신한다.
     * 3. 입력 Data: 게시글 데이터
     * 4. 출력 Data: 성공시 true, 실패시 false
     */
    @Override
    public boolean update(BoardDTO board) {
	return boardMapper.update(board) == 1;
    }
    
    /**
     * 1. 개요:
     * 2. 처리내용: 게시글을 삭제한다.
     * 3. 입력 Data: 게시글 PK
     * 4. 출력 Data: 성공시 true, 실패시 false
     */
    @Override
    public boolean deleteById(long id) {
	return boardMapper.delete(id) == 1;
    }
    
    /**
     * 1. 개요:
     * 2. 처리내용: 새로운 첨부파일 정보를 생성한다.
     * 3. 입력 Data: 첨부파일 데이터
     * 4. 출력 Data: 성공시 true, 실패시 false
     */
    @Override
    public boolean attachFile(FileDTO file) {
	return fileMapper.insert(file) == 1;
    }
    
    /**
     * 1. 개요:
     * 2. 처리내용: 하나의 첨부파일 정보를 반환한다.
     * 3. 입력 Data: 첨부파일 PK
     * 4. 출력 Data: 첨부파일 데이터
     */
    @Override
    public FileDTO getFileByUUID(String uuid) {
	return fileMapper.findByUUID(uuid);
    }
}

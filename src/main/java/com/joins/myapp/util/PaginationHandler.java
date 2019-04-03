package com.joins.myapp.util;

import java.util.List;

import com.joins.myapp.domain.PageDTO;
import com.joins.myapp.domain.SearchInfoDTO;

public class PaginationHandler {

    private PaginationHandler() {
	throw new UnsupportedOperationException();
    }

    /**
     * 1. 개요: 
     * 2. 처리내용: Pagination.jsp에서 필요한 페이징 처리 객체를 반환한다. 
     * 3. 입력 Data: 검색 요청 정보, 한 줄에 표시되는 페이지 갯수, 레코드의 갯수 
     * 4. 출력 Data: 페이징 처리 객체
     */
    public static <T> PageDTO<T> generatePageDTO(List<T> contents, SearchInfoDTO searchInfo, int itemsPerPage,
	    int pagesPerOneLine, int totalSizeOfTable) {
	int totalPages = (int) Math.ceil((totalSizeOfTable * 1.0) / itemsPerPage);
	int endPage = (int) Math.ceil((searchInfo.getPage() * 1.0) / pagesPerOneLine) * pagesPerOneLine;
	int startPage = endPage - pagesPerOneLine + 1;
	boolean prev = startPage > 1;
	boolean next = endPage < totalPages;

	startPage = Math.max(startPage, 1);
	endPage = Math.min(endPage, totalPages);

	PageDTO<T> pageObj = new PageDTO<T>(searchInfo, contents, startPage, endPage, prev, next);
	return pageObj;
    }

}

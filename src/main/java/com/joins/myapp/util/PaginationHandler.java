package com.joins.myapp.util;

import com.joins.myapp.domain.PageDTO;
import com.joins.myapp.domain.SearchInfoDTO;

public class PaginationHandler {
    
    public static <T> PageDTO<T> generatePageDTO(SearchInfoDTO searchInfo, int pagesPerOneLine, int totalSizeOfTable){
	int totalPages = (int) Math.ceil((totalSizeOfTable * 1.0) / searchInfo.getItemsPerPage());
	int endPage = (int) Math.ceil((searchInfo.getPage() * 1.0) / pagesPerOneLine) * pagesPerOneLine;
	int startPage = endPage - pagesPerOneLine + 1;
	boolean prev = startPage > 1;
	boolean next = endPage < totalPages;

	startPage = Math.max(startPage, 1);
	endPage = Math.min(endPage, totalPages);
	
	PageDTO<T> pageObj = new PageDTO<T>(searchInfo, null, startPage, endPage, prev, next);
	return pageObj;
    }
    
}

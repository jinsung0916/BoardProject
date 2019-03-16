package com.joins.myapp.util;

import com.joins.myapp.domain.PageDTO;

public class PaginationHandler {
    
    public static <T> PageDTO<T> generatePageDTO(int page, int itemsPerPage, int pagesPerOneLine, int totalSizeOfTable){
	int totalPages = (int) Math.ceil((totalSizeOfTable * 1.0) / itemsPerPage);
	int endPage = (int) Math.ceil((page * 1.0) / pagesPerOneLine) * pagesPerOneLine;
	int startPage = endPage - pagesPerOneLine + 1;
	boolean prev = startPage > 1;
	boolean next = endPage < totalPages;

	startPage = Math.max(startPage, 1);
	endPage = Math.min(endPage, totalPages);
	
	PageDTO<T> pageObj = new PageDTO<T>(null, itemsPerPage, page, startPage, endPage, prev, next);
	return pageObj;
    }
    
    public static String generatePageJSP() {
	return null;
    }
}

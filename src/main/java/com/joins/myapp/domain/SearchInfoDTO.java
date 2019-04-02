package com.joins.myapp.domain;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("searchInfo")
public class SearchInfoDTO {
    // 게시판 검색 조건
    private String choose;
    private String search;
    private String startDate;
    private String endDate;

    // 페이징 처리 조건
    private int page;
    private int itemsPerPage;

    // MariaDB LIMIT절 조건
    private int startIdx;
}

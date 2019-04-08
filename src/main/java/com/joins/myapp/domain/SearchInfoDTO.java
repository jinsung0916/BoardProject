package com.joins.myapp.domain;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("searchInfo")
public class SearchInfoDTO {
    // 게시판 검색 조건
    private String flag;
    private String value;
    private String startDate;
    private String endDate;

    // 현재 페이지 정보 
    private int page;
}

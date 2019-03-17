package com.joins.myapp.domain;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("searchInfo")
public class SearchInfoDTO {
    // 게시판 검색 조건
    String choose;
    String search;
    String startDate;
    String endDate;
    
    // MariaDB LIMIT절 조건
    int startIdx;
}

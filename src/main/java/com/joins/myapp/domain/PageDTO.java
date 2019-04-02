package com.joins.myapp.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageDTO<T> {
    private SearchInfoDTO searchInfo;
    private List<T> contents;
    private int startPage;
    private int endPage;
    private boolean prev;
    private boolean next;
}

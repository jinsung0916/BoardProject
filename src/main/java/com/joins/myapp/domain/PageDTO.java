package com.joins.myapp.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class PageDTO<T> {
    private List<T> contents;
    private int pageSize;
    private int currentPage;
    private int startPage;
    private int endPage;
    private boolean prev;
    private boolean next;
}

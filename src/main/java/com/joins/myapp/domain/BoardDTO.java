package com.joins.myapp.domain;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("board")
public class BoardDTO {
    private long no;
    private String title;
    private String Contents;
    private Date regDate;
    private String userId;

    private List<FileDTO> fileList;
}

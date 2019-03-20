package com.joins.myapp.domain;

import java.io.File;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("file")
public class FileDTO {
    private String uuid;
    private String fileName;
    private String filePath;
    private long boardNo;

    @Override
    public String toString() {
	return filePath + File.separator +  fileName;
    };
}

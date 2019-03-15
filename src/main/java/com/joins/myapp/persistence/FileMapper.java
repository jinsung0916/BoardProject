package com.joins.myapp.persistence;

import java.util.List;

import com.joins.myapp.domain.FileDTO;

public interface FileMapper {

    public int insert(FileDTO file);

    public int delete(String uuid);

    public List<FileDTO> findByBoardNo(long boardNo);

    public FileDTO findByUUID(String uuid);
}

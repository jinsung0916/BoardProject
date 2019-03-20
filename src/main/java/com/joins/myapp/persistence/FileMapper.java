package com.joins.myapp.persistence;

import com.joins.myapp.domain.FileDTO;

public interface FileMapper {

    public int insert(FileDTO file);

    public int delete(String uuid);

    public FileDTO findByUUID(String uuid);
}

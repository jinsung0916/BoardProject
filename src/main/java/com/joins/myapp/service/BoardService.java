package com.joins.myapp.service;

import com.joins.myapp.domain.BoardDTO;
import com.joins.myapp.domain.FileDTO;
import com.joins.myapp.domain.PageDTO;

public interface BoardService {

    BoardDTO findOne(long id);

    PageDTO<BoardDTO> findPaginated(int page, int itemsPerPage, int pagesPerOneLine);

    boolean create(BoardDTO board);

    boolean update(BoardDTO board);

    boolean deleteById(long id);

    boolean attachFile(FileDTO file);

    FileDTO getFileByUUID(String uuid);
}

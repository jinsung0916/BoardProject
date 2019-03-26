package com.joins.myapp.persistence;

import java.util.List;

import com.joins.myapp.domain.BoardDTO;
import com.joins.myapp.domain.SearchInfoDTO;

public interface BoardMapper {
    int countAll(SearchInfoDTO searchInfo);

    BoardDTO findByNo(long no);

    List<BoardDTO> findPagenated(SearchInfoDTO searchInfo);
    
    int insert(BoardDTO board);

    int update(BoardDTO board);

    int delete(long no);
}

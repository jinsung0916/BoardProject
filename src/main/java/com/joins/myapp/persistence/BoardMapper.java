package com.joins.myapp.persistence;

import java.util.List;

import com.joins.myapp.domain.BoardDTO;
import com.joins.myapp.domain.SearchInfoDTO;

public interface BoardMapper {
    int countAll();

    BoardDTO findByNo(long id);

    List<BoardDTO> findPagenated(SearchInfoDTO searchInfo);
    
    List<BoardDTO> findPagenatedByDateAndTitle(SearchInfoDTO searchInfo);
    
    List<BoardDTO> findPagenatedByDateAndContents(SearchInfoDTO searchInfo);
    
    int insert(BoardDTO board);

    int update(BoardDTO board);

    int delete(long no);
}

package com.joins.myapp.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.joins.myapp.domain.BoardDTO;

public interface BoardMapper {
    int countAll();

    BoardDTO findByNo(long id);

    List<BoardDTO> findPagenated(@Param("startIndex") int startIndex, @Param("itemsPerPage") int itemsPerPage);

    int insert(BoardDTO board);

    int update(BoardDTO board);

    int delete(long no);
}

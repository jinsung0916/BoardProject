package com.joins.myapp.persistence;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.joins.myapp.domain.BoardDTO;

public interface BoardMapper {
    int countAll();

    BoardDTO findByNo(long id);

    List<BoardDTO> findPagenated(@Param("startIndex") int startIndex, @Param("itemsPerPage") int itemsPerPage);
    
    List<BoardDTO> findByDateAndTitle(@Param("startDate") Date startDate, @Param("endDate") Date endDate, 
	    @Param("title") String title);
    
    int insert(BoardDTO board);

    int update(BoardDTO board);

    int delete(long no);
}

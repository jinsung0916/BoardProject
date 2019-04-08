package com.joins.myapp.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.joins.myapp.domain.BoardDTO;
import com.joins.myapp.domain.SearchInfoDTO;

public interface BoardMapper {
    int countAll(SearchInfoDTO searchInfo);

    BoardDTO findByNo(long no);

    List<BoardDTO> findPagenated(@Param("offset") int offset, @Param("rowCount") int rowCount,
	    @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("flag") String flag,
	    @Param("value") String value);

    int insert(BoardDTO board);

    int update(BoardDTO board);

    int delete(long no);
}

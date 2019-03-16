package com.joins.myapp.persistence;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.joins.myapp.domain.BoardDTO;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Slf4j
public class BoardMapperTest {
    @Autowired
    private BoardMapper boardMapper;

//    @Test
//    public void testInsert() {
//	BoardDTO board = new BoardDTO();
//
//	board.setTitle("테스트");
//	board.setContents("테스트");
//	boardMapper.insert(board);
//	log.info(board.toString());
//
//    }

    @Test
    public void testFindByNo() {
	// 존재하는 게시물 번호로 확인해야 함.
	BoardDTO board = boardMapper.findByNo(110L);

	log.info(board.toString());
    }

    @Test
    public void testFindPaginated() {
	List<BoardDTO> list = boardMapper.findPagenated(0, 10);

	for (BoardDTO board : list)
	    log.info(board.toString());
    }

    @Test
    public void testUpdate() {
	// 존재하는 게시물 번호로 확인해야 함.
	BoardDTO board = boardMapper.findByNo(31L);
	board.setTitle("modified title");
	board.setContents("modified contents");
	board.setRegDate(new Date());

	log.info("update count: " + boardMapper.update(board));
    }

    @Test
    public void testDelete() {
	// 존재하는 게시물 번호로 확인해야 함.
	log.info("delete count: " + boardMapper.delete(30L));
    }

}

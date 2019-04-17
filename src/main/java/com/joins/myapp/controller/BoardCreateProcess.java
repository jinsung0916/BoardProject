package com.joins.myapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.joins.myapp.domain.BoardDTO;
import com.joins.myapp.service.BoardService;

@Component("boardCreateProcess")
public class BoardCreateProcess extends BoardCreateAndUpdateTemplate {

    @Autowired
    public BoardCreateProcess(BoardService service,
	    @Value("#{file['baseUploadDirectory']}") String baseUploadDirectory) {
	super(service, baseUploadDirectory);
    }

    @Override
    protected boolean isSuccessful(BoardDTO board) {
	return super.service.create(board);
    }

}

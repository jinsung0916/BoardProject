package com.joins.myapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class CommonExceptionAdvice {

    /**
     * 1. 개요: 404 NOT FOUND 예외 페이지를 반환한다.
     * 2. 처리내용:
     * 3. 입력 Data:
     * 4. 출력 Data: 
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String except404(Exception ex) {
	return "exception/notFound404";
    }
}

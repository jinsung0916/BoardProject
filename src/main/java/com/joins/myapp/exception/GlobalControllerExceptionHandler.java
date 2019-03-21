package com.joins.myapp.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalControllerExceptionHandler  {
    public static final String DEFAULT_ERROR_VIEW = "exception/error";

    /**
     * 1. 개요: 
     * 2. 처리내용: NoHandlerFoundException을 제외한 모든 예외를 처리한다.
     * 3. 입력 Data:  
     * 4. 출력 Data: ModelAndView
     */
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
	ModelAndView mav = new ModelAndView();
	mav.addObject("exception", e);
	mav.addObject("url", req.getRequestURL());
	mav.setViewName(DEFAULT_ERROR_VIEW);
	return mav;
    }
    
    /**
     * 1. 개요: URL이 존재하지 않을 경우 404 NOT FOUND 예외 페이지를 반환한다.
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

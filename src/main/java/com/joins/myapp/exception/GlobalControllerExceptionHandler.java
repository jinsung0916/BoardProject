package com.joins.myapp.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalControllerExceptionHandler  {
    public static final String DEFAULT_ERROR_VIEW = "exception/error";

    /**
     * 1. 개요: 
     * 2. 처리내용: 예외 발생 시 예외 처리 페이지로 리다이렉트한다.
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
       
}

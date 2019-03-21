package com.joins.myapp.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalControllerExceptionHandler  {
    public static final String DEFAULT_ERROR_VIEW = "exception/error";

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
      // If the exception is annotated with @ResponseStatus rethrow it and let
      // the framework handle it
      // at the start of this post.
      // AnnotationUtils is a Spring Framework utility class.
      if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
        throw e;

      // Otherwise setup and send the user to a default error-view.
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

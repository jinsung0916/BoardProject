package com.joins.myapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/*
 * web.xml에서 리다이렉트 된 에러를 처리하는 컨트롤러
 */
@Controller
public class ErrorController {

    /**
     * 1. 개요: GET /errors
     * 2. 처리내용: 예외를 처리한다. 
     * 3. 입력 Data:
     * 4. 출력 Data: 
     */
    @RequestMapping(value = "errors", method = RequestMethod.GET)
    public ModelAndView defaultErrorHandler(HttpServletRequest req) throws Exception {
	ModelAndView errorPage = new ModelAndView("exception/error");
	String errorMsg = "";
	int httpErrorCode = getErrorCode(req);
	switch (httpErrorCode) {
	case 400: {
	    errorMsg = "Http Error Code: 400. Bad Request";
	    break;
	}
	case 401: {
	    errorMsg = "Http Error Code: 401. Unauthorized";
	    break;
	}
	case 404: {
	    errorMsg = "Http Error Code: 404. Resource not found";
	    break;
	}
	case 500: {
	    errorMsg = "Http Error Code: 500. Internal Server Error";
	    break;
	}
	}
	errorPage.addObject("errorMsg", errorMsg);
	return errorPage;
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
	return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
    }
}

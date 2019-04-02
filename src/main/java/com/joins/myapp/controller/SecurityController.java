package com.joins.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

/*
 * Spring Security 관련 URL 처리를 위한 컨트롤러	
 */
@Controller
@Slf4j
public class SecurityController {

    /**
     * 1. 개요: GET /customLogin
     * 2. 처리내용: 로그인 페이지를 반환한다.
     * 3. 입력 Data:
     * 4. 출력 Data: 
     */
    @GetMapping("/customLogin")
    public String loginInput(String error, String logout, Model model) {
	log.info("error: {}", error);
	log.info("logout: {}", logout);

	if (error != null) {
	    model.addAttribute("error", "Login Error Check Your Account");
	}
	if (logout != null) {
	    model.addAttribute("logout", "Logout!!");
	}

	return "auth/login";
    }

    /**
     * 1. 개요: GET /logout
     * 2. 처리내용: 로그아웃 페이지를 반환한다.
     * 3. 입력 Data:
     * 4. 출력 Data: 
     */
    @GetMapping("/logout")
    public String logout() {
	return "auth/logout";
    }

}

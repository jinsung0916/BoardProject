package com.joins.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class SecurityController {

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

    @GetMapping("/logout")
    public String logout() {
	return "auth/logout";
    }

}

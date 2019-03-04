package com.radek.myownplengdictionary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	@GetMapping("/loginPage")
	public String showLoginPage() {
		return "login-form";
	}
	
	@GetMapping("/accessDenied")
	public String accessDenied() {
		return "access-denied";
	}
}

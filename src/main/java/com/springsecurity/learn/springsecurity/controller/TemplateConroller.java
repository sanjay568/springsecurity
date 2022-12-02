package com.springsecurity.learn.springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TemplateConroller {

	@GetMapping("/login")
	public String getLogin() {
		return "login";
	}
	
	@GetMapping("/courses")
	public String getCourses() {
		return "courses";
	}
	
	@GetMapping("/login?error")
	public String getErrorPage() {
		return "error";
	}
}

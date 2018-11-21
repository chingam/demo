package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping(path = "/")
	public String getIndex() {
		
		return "redirect:/menu?linkid=1";
	}
	
	@GetMapping(path = "/login")
	public String getLogin() {
		
		return "login/login";
	}
}

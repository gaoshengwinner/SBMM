package com.kou.sbmm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kou.sbmm.SbmmApplication;

@Controller
public class SBMMController {
	private static Logger logger = LoggerFactory.getLogger(SbmmApplication.class.getName());
	@RequestMapping(value = "/logininit")
	public String Logininit() {
		logger.trace("login int trace");
		return "/html/login";
		
	}
	
	@RequestMapping(value = "/signupinit")
	public String Signupinit() {
		logger.trace("login int trace");
		return "/html/signup";
		
	}

	@RequestMapping(value = "/login")
	public String Login() {
		logger.trace("login int trace");
		return "/html/index";
		
	}
}

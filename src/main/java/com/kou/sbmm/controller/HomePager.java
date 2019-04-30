package com.kou.sbmm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;

import com.kou.sbmm.SbmmApplication;

@Controller
public class HomePager {
	private static Logger logger = LoggerFactory.getLogger(SbmmApplication.class.getName());

	@RequestMapping(value = "/")
	public String Home() {
		logger.trace("日志输出 trace");
		return "/html/index";
		
	}
}

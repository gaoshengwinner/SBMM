package com.kou.sbmm.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kou.sbmm.SbmmApplication;
import com.kou.sbmm.entity.Spmmdb0001;
import com.kou.sbmm.form.AccForm;
import com.kou.sbmm.form.LoginForm;
import com.kou.sbmm.service.Spmmdb0001Service;
import org.springframework.validation.ObjectError;

@Controller
public class SBMMController {
	private static Logger logger = LoggerFactory.getLogger(SbmmApplication.class.getName());
	@Autowired
	private Spmmdb0001Service spmmdb0001Service;

	@Autowired
	private HttpSession httpSession;

	@RequestMapping(value = "/logininit")
	public String Logininit(ModelMap model) {
		logger.trace("login int trace");
		httpSession.setAttribute("loginInfo", null);
		model.put("loginForm", new LoginForm());
		return "/html/login";

	}

	@RequestMapping(value = "/signupinit")
	public String Signupinit(ModelMap model) {
		logger.trace("login int trace");
		model.put("accForm", new AccForm());
		return "/html/signup";

	}

	@RequestMapping(value = "/signup")
	public String Signup(@ModelAttribute("accForm") @Validated AccForm accForm, BindingResult rs) {
		boolean canSignup = true;
		if (rs.hasErrors()) {
			canSignup = false;
		}

		boolean t = spmmdb0001Service.checkSinuped(accForm.getAccmail());
		if (!t) {
			rs.addError(new FieldError("accForm", "accmail", "メール"));
			canSignup = false;
		}
		if (canSignup) {
			Spmmdb0001 spmmdb0001 = new Spmmdb0001();

			spmmdb0001.setAccauth('1');
			spmmdb0001.setAcccreatedatetime(new Date());
			spmmdb0001.setAccmail(accForm.getAccmail());
			spmmdb0001.setName(accForm.getName());
			spmmdb0001.setNameyoread(accForm.getNameyoread());
			spmmdb0001.setPasswd(accForm.getPasswd().getBytes());
			spmmdb0001Service.save(spmmdb0001);
			return "forward:/logininit";
		} else {
			return "/html/signup";
		}

	}

	@RequestMapping(value = "/login")
	public String Login(@ModelAttribute("loginForm") @Validated LoginForm old, BindingResult rs) {
		logger.trace("login int trace");
		Spmmdb0001 get = spmmdb0001Service.findByMail(old.getAccmail());
		String s = get == null || get.getPasswd() == null ? null : new String(get.getPasswd());
		if (old.getPasswd().equals(s)) {
			httpSession.setAttribute("loginInfo", get);
			return "forward:/home";
		} else {
			rs.addError(new FieldError("accForm", "accmail", "login error!!"));
			return "/html/login";
		}
	}

	@RequestMapping(value = "/home")
	public String Home(ModelMap model) {
		return "/html/index";
	}

}

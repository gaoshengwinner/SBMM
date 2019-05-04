package com.kou.sbmm.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.kou.sbmm.entity.Spmmdb0002;
import com.kou.sbmm.form.AccForm;
import com.kou.sbmm.form.LoginForm;
import com.kou.sbmm.form.MessageForm;
import com.kou.sbmm.form.ProfileForm;
import com.kou.sbmm.service.Spmmdb0001Service;
import com.kou.sbmm.service.Spmmdb0002Service;

@Controller
public class SBMMController {
	private static Logger logger = LoggerFactory.getLogger(SbmmApplication.class.getName());
	@Autowired
	private Spmmdb0001Service spmmdb0001Service;
	@Autowired
	private Spmmdb0002Service spmmdb0002Service;

	@Autowired
	private HttpSession httpSession;

	@RequestMapping(value = "/logininit")
	public String Logininit(ModelMap model, HttpServletResponse response) {
		logger.trace("login int trace");
		httpSession.setAttribute("loginInfo", null);
		Cookie cookie = new Cookie("userid", null);
		response.addCookie(cookie);
		model.put("loginForm", new LoginForm());
		return "html/login";

	}

	@RequestMapping(value = "/signupinit")
	public String Signupinit(ModelMap model) {
		logger.trace("login int trace");
		model.put("accForm", new AccForm());
		return "html/signup";

	}

	@RequestMapping(value = "/signup")
	public String Signup(@ModelAttribute("accForm") @Validated AccForm accForm, BindingResult rs) {
		boolean canSignup = true;
		if (rs.hasErrors()) {
			canSignup = false;
		}

		boolean t = spmmdb0001Service.checkSinuped(accForm.getAccmail());
		if (!t) {
			rs.addError(new FieldError("accForm", "accmail", "signup.signup.head"));
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
			return "forward:logininit";
		} else {
			return "html/signup";
		}

	}

	@RequestMapping(value = "/login")
	public String Login(@ModelAttribute("loginForm") @Validated LoginForm old, BindingResult rs,
			HttpServletResponse response) {
		logger.trace("login int trace");
		if (!rs.hasErrors()) {
			Spmmdb0001 get = spmmdb0001Service.findByMail(old.getAccmail());
			String s = get == null || get.getPasswd() == null ? null : new String(get.getPasswd());
			if (old.getPasswd().equals(s)) {

				httpSession.setAttribute("loginInfo", get);
				if ("1".equals(old.getRememberpassword())) {
					Cookie cookie = new Cookie("userid", String.valueOf(get.getAccid()));
					cookie.setMaxAge(60 * 60 * 24 * 30);
					response.addCookie(cookie);
				}
				return "forward:home";
			} else {
				rs.addError(new FieldError("accForm", "accmail", "login.accpass.err"));
				return "html/login";
			}
		} else {
			return "html/login";
		}
	}

	@RequestMapping(value = { "/", "/home" })
	public String Home(ModelMap model, HttpServletRequest request) {

		Spmmdb0001 loginInfo = (Spmmdb0001) httpSession.getAttribute("loginInfo");
		if (loginInfo == null) {
			AutoLogin(request);
		}
		loginInfo = (Spmmdb0001) httpSession.getAttribute("loginInfo");
		if (loginInfo == null) {
			return "forward:logininit";
		} else {
			List<Spmmdb0002> listspmmdb0002 = spmmdb0002Service.findList(loginInfo.getAccid());
			model.put("listspmmdb0002", listspmmdb0002);
			model.put("messageForm", new MessageForm());

			return "html/index";
		}
	}

	@RequestMapping(value = { "/profileinit" })
	public String Profileinit(ModelMap model, HttpServletRequest request) {
		AutoLogin(request);
		Spmmdb0001 loginInfo = (Spmmdb0001) httpSession.getAttribute("loginInfo");
		if (loginInfo == null) {
			return "forward:logininit";
		} else {
			ProfileForm profileForm = new ProfileForm();
			profileForm.setAccmail(loginInfo.getAccmail());
			profileForm.setName(loginInfo.getName());
			profileForm.setNameyoread(loginInfo.getNameyoread());
			model.put("profileForm", profileForm);
			return "html/profile";
		}
	}

	@RequestMapping(value = "/messagecreate")
	public String Messagecreate(ModelMap model, @ModelAttribute("messageForm") @Validated MessageForm messageForm,
			BindingResult rs, HttpServletResponse response) {
		Spmmdb0001 loginInfo = (Spmmdb0001) httpSession.getAttribute("loginInfo");
		if (!rs.hasErrors()) {
			
			Spmmdb0002 spmmdb0002 = new Spmmdb0002();

			spmmdb0002.setAccid(Integer.valueOf(loginInfo.getAccid()));
			spmmdb0002.setCreatedatetime(new Date());
			spmmdb0002.setMsgcont(messageForm.getMsgcont());
			if ("1".equals(messageForm.getBepublic())) {
				spmmdb0002.setMsgpublictype('1');
			}
			spmmdb0002.setMsgtitle(messageForm.getMsgtitle());
			spmmdb0002.setName(loginInfo.getName());

			spmmdb0002Service.save(spmmdb0002);

			model.put("messageForm", new MessageForm());
		}else {
			model.put("hasindexerr", "1");
		}
		List<Spmmdb0002> listspmmdb0002 = spmmdb0002Service.findList(loginInfo.getAccid());
		model.put("listspmmdb0002", listspmmdb0002);

		return "html/index";

	}

	@RequestMapping(value = "/profile")
	public String Signup(@ModelAttribute("profileForm") @Validated ProfileForm profileForm, BindingResult rs,
			HttpServletRequest request) {
		AutoLogin(request);
		Spmmdb0001 loginInfo = (Spmmdb0001) httpSession.getAttribute("loginInfo");
		if (loginInfo == null) {
			return "forward:logininit";
		}
		if (rs.hasErrors()) {
			return "html/profile";
		}
		String s = loginInfo == null || loginInfo.getPasswd() == null ? null : new String(loginInfo.getPasswd());
		if (!profileForm.getOldpasswd().equals(s)) {
			rs.addError(new FieldError("profileForm", "oldpasswd", "profile.password.err"));
			return "html/profile";
		}
		loginInfo.setAccauth('1');
		loginInfo.setName(profileForm.getName());
		loginInfo.setNameyoread(profileForm.getNameyoread());
		loginInfo.setPasswd(profileForm.getPasswd().getBytes());
		spmmdb0001Service.save(loginInfo);
		return "html/index";

	}

	private void AutoLogin(HttpServletRequest request) {
		Spmmdb0001 loginInfo = (Spmmdb0001) httpSession.getAttribute("loginInfo");
		if (loginInfo == null) {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("userid")) {
						String userid = cookie.getValue();
						if (userid != null && !"".equals(userid)) {
							Optional<Spmmdb0001> ologinInfo = spmmdb0001Service.findById(Integer.valueOf(userid));
							if (ologinInfo != null && ologinInfo.isPresent()) {
								httpSession.setAttribute("loginInfo", ologinInfo.get());
							}
						}
						return;
					}
				}
			}

		}
	}

}

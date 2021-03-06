package com.kou.sbmm.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
import com.kou.sbmm.form.MessageBean;
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
		logger.trace("/logininit start");
		httpSession.setAttribute("loginInfo", null);
		Cookie cookie = new Cookie("userid", null);
		response.addCookie(cookie);
		model.put("loginForm", new LoginForm());
		logger.trace("/logininit end");
		return "html/login";

	}

	@RequestMapping(value = "/signupinit")
	public String Signupinit(ModelMap model) {
		logger.trace("/signupinit start ");
		model.put("accForm", new AccForm());
		logger.trace("/signupinit end ");
		return "html/signup";

	}

	@RequestMapping(value = "/signup")
	public String Signup(@ModelAttribute("accForm") @Validated AccForm accForm, BindingResult rs) {
		logger.trace("/signup start ");
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
			logger.trace("/signup end ");
			return "forward:logininit";
		} else {
			logger.trace("/signup end ");
			return "html/signup";
		}

	}

	@RequestMapping(value = "/login")
	public String Login(@ModelAttribute("loginForm") @Validated LoginForm old, BindingResult rs,
			HttpServletResponse response) {
		logger.trace("/login start ");
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
				logger.trace("/login end ");
				return "forward:home";
			} else {
				rs.addError(new FieldError("accForm", "accmail", "login.accpass.err"));
				logger.trace("/login end ");
				return "html/login";
			}
		} else {
			logger.trace("/login end ");
			return "html/login";
		}
	}

	@RequestMapping(value = { "/", "/home" })
	public String Home(ModelMap model, String page, HttpServletRequest request) {
		logger.trace("/ /home start ");
		Spmmdb0001 loginInfo = (Spmmdb0001) httpSession.getAttribute("loginInfo");
		if (loginInfo == null) {
			AutoLogin(request);
		}
		loginInfo = (Spmmdb0001) httpSession.getAttribute("loginInfo");
		if (loginInfo == null) {
			logger.trace("/ /home end ");
			return "forward:logininit";
		} else {
			List<Spmmdb0002> listspmmdb0002 = spmmdb0002Service.findList(loginInfo.getAccid());
			List<MessageBean> beanlst = createMessList(loginInfo, listspmmdb0002, page, model);
			model.put("listspmmdb0002", beanlst);
			model.put("messageForm", new MessageForm());
			logger.trace("/ /home end ");
			return "html/index";
		}
	}

	@RequestMapping(value = { "/profileinit" })
	public String Profileinit(ModelMap model, HttpServletRequest request) {
		logger.trace("/profileinit start ");
		AutoLogin(request);
		Spmmdb0001 loginInfo = (Spmmdb0001) httpSession.getAttribute("loginInfo");
		if (loginInfo == null) {
			logger.trace("/profileinit end ");
			return "forward:logininit";
		} else {
			ProfileForm profileForm = new ProfileForm();
			profileForm.setAccmail(loginInfo.getAccmail());
			profileForm.setName(loginInfo.getName());
			profileForm.setNameyoread(loginInfo.getNameyoread());
			model.put("profileForm", profileForm);
			logger.trace("/profileinit end ");
			return "html/profile";
		}
	}

	@RequestMapping(value = "/messagecreate")
	public String Messagecreate(ModelMap model, String page,
			@ModelAttribute("messageForm") @Validated MessageForm messageForm, BindingResult rs,
			HttpServletResponse response) {
		logger.trace("/messagecreate start ");
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
		} else {
			model.put("hasindexerr", "1");
		}
		List<Spmmdb0002> listspmmdb0002 = spmmdb0002Service.findList(loginInfo.getAccid());
		List<MessageBean> beanlst = createMessList(loginInfo, listspmmdb0002, page, model);
		model.put("listspmmdb0002", beanlst);

		logger.trace("/messagecreate end ");
		return "html/index";

	}

	@RequestMapping(value = "/profile")
	public String Signup(@ModelAttribute("profileForm") @Validated ProfileForm profileForm, BindingResult rs,
			HttpServletRequest request) {
		logger.trace("/profile start ");
		AutoLogin(request);
		Spmmdb0001 loginInfo = (Spmmdb0001) httpSession.getAttribute("loginInfo");
		if (loginInfo == null) {
			logger.trace("/profile end ");
			return "forward:logininit";
		}
		if (rs.hasErrors()) {
			logger.trace("/profile end ");
			return "html/profile";
		}
		String s = loginInfo == null || loginInfo.getPasswd() == null ? null : new String(loginInfo.getPasswd());
		if (!profileForm.getOldpasswd().equals(s)) {
			rs.addError(new FieldError("profileForm", "oldpasswd", "profile.password.err"));
			logger.trace("/profile end ");
			return "html/profile";
		}
		loginInfo.setAccauth('1');
		loginInfo.setName(profileForm.getName());
		loginInfo.setNameyoread(profileForm.getNameyoread());
		loginInfo.setPasswd(profileForm.getPasswd().getBytes());
		spmmdb0001Service.save(loginInfo);
		logger.trace("/profile end ");
		return "html/index";

	}

	@RequestMapping(value = "/unlocktolock")
	public String unlocktolock(String msgid) {
		// String msgid = (String) model.get("msgid");

		Optional<Spmmdb0002> messageBean = spmmdb0002Service.findById(Integer.valueOf(msgid));
		if (messageBean.isPresent()) {
			Spmmdb0001 loginInfo = (Spmmdb0001) httpSession.getAttribute("loginInfo");
			if (loginInfo != null
					&& ('2' == loginInfo.getAccauth() || loginInfo.getAccid().equals(messageBean.get().getAccid()))
					&& (null != messageBean.get().getMsgpublictype() && '1' == messageBean.get().getMsgpublictype())) {
				messageBean.get().setMsgpublictype('0');
				spmmdb0002Service.save(messageBean.get());
			}
		}

		return "forward:/home";
	}

	@RequestMapping(value = "/locktounlock")
	public String locktounlock(String msgid) {
		// String msgid = (String) model.get("msgid");

		Optional<Spmmdb0002> messageBean = spmmdb0002Service.findById(Integer.valueOf(msgid));
		if (messageBean.isPresent()) {
			Spmmdb0001 loginInfo = (Spmmdb0001) httpSession.getAttribute("loginInfo");
			if (loginInfo != null
					&& ('2' == loginInfo.getAccauth() || loginInfo.getAccid().equals(messageBean.get().getAccid()))
					&& (null == messageBean.get().getMsgpublictype() || '1' != messageBean.get().getMsgpublictype())) {
				messageBean.get().setMsgpublictype('1');
				spmmdb0002Service.save(messageBean.get());
			}
		}

		return "forward:/home";
	}

	@RequestMapping(value = "/delete")
	public String delete(String msgid) {
		// String msgid = (String) model.get("msgid");

		Optional<Spmmdb0002> messageBean = spmmdb0002Service.findById(Integer.valueOf(msgid));
		if (messageBean.isPresent()) {
			Spmmdb0001 loginInfo = (Spmmdb0001) httpSession.getAttribute("loginInfo");
			if (loginInfo != null
					&& ('2' == loginInfo.getAccauth() || loginInfo.getAccid().equals(messageBean.get().getAccid()))) {
				spmmdb0002Service.delete(Integer.valueOf(msgid));
			}
		}

		return "forward:/home";
	}

	private List<MessageBean> createMessList(Spmmdb0001 loginInfo, List<Spmmdb0002> listspmmdb0002, String page,
			ModelMap model) {
		if (listspmmdb0002 == null) {
			listspmmdb0002 = new ArrayList<Spmmdb0002> ();
		}
		List<MessageBean> beanlst = new ArrayList<MessageBean>();
		int iPage = 0;
		if (page == null || page.equals("")) {

		} else {
			iPage = Integer.valueOf(page);
		}
		int pageline = 10;
		int maxPage = listspmmdb0002.size() / pageline + (listspmmdb0002.size() % pageline > 0 ? 1 : 0) - 1;
		if (iPage > maxPage) {
			iPage = maxPage;
		}
		int begLine = pageline * iPage;
		int endLine = pageline * (iPage + 1);
		if (endLine > listspmmdb0002.size()) {
			endLine = listspmmdb0002.size();
		}

		int prePage = 0;
		boolean havPrepage = false;
		if (iPage > 0) {
			prePage = iPage - 1;
			havPrepage = true;
		}

		int nextPage = 0;
		boolean havNexpage = false;
		if (iPage < maxPage) {
			nextPage = iPage + 1;
			havNexpage = true;
		}
		boolean haveChangePage = havPrepage | havNexpage;
		model.put("prePage",prePage);
		model.put("havPrepage",havPrepage);
		model.put("nextPage",nextPage);
		model.put("havNexpage",havNexpage);
		model.put("haveChangePage",haveChangePage);

		for (Spmmdb0002 spmmdb0002 : listspmmdb0002.subList(begLine, endLine)) {
			MessageBean messageBean = new MessageBean();
			BeanUtils.copyProperties(spmmdb0002, messageBean);
			boolean siDisopenging = false;
			if (('2' == loginInfo.getAccauth() || loginInfo.getAccid().equals(messageBean.getAccid()))
					&& (null != messageBean.getMsgpublictype() && '1' == messageBean.getMsgpublictype())) {
				siDisopenging = true;
			}
			messageBean.setDisopenging(siDisopenging);

			boolean dislocking = false;
			if (('2' == loginInfo.getAccauth() || loginInfo.getAccid().equals(messageBean.getAccid()))
					&& (null == messageBean.getMsgpublictype() || '1' != messageBean.getMsgpublictype())) {
				dislocking = true;
			}
			messageBean.setDislocking(dislocking);

			boolean disdelete = false;
			if ('2' == loginInfo.getAccauth() || loginInfo.getAccid().equals(messageBean.getAccid())) {
				disdelete = true;
			}
			messageBean.setDisdelete(disdelete);

			boolean dislock = false;
			if (('2' == loginInfo.getAccauth() || loginInfo.getAccid().equals(messageBean.getAccid()))
					&& (null != messageBean.getMsgpublictype() && '1' == messageBean.getMsgpublictype())) {
				dislock = true;
			}
			messageBean.setDislock(dislock);

			boolean disunlock = false;
			if (('2' == loginInfo.getAccauth() || loginInfo.getAccid().equals(messageBean.getAccid()))
					&& (null == messageBean.getMsgpublictype() || '1' != messageBean.getMsgpublictype())) {
				disunlock = true;
			}
			messageBean.setDisunlock(disunlock);

			beanlst.add(messageBean);
		}

		return beanlst;

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

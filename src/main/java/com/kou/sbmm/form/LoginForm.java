package com.kou.sbmm.form;

import javax.validation.constraints.*;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.kou.sbmm.form.validator.CheckFeildEqual;
@Configuration
public class LoginForm {
	
	public String getAccmail() {
		return accmail;
	}

	public void setAccmail(String accmail) {
		this.accmail = accmail;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	@NotEmpty(message = "common.empty")
	@Email(message = "common.mail.err")
	private String accmail;
	
	@NotEmpty(message = "common.empty")
	private String passwd;
	
	private String rememberpassword;

	public String getRememberpassword() {
		return rememberpassword;
	}

	public void setRememberpassword(String rememberpassword) {
		this.rememberpassword = rememberpassword;
	}
	

}

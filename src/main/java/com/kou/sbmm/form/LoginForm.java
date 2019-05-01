package com.kou.sbmm.form;

import javax.validation.constraints.*;

import javax.validation.constraints.NotEmpty;

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
	
	@NotEmpty
	@Email
	private String accmail;
	
	@Length(min=5,max=32)
	private String passwd;
	

}

package com.kou.sbmm.form;

import javax.validation.constraints.*;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.kou.sbmm.form.validator.CheckFeildEqual;
@Configuration
@CheckFeildEqual(feild1="passwd",feild2="passwordcon")
public class AccForm {
	
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

	public String getPasswordcon() {
		return passwordcon;
	}

	public void setPasswordcon(String passwordcon) {
		this.passwordcon = passwordcon;
	}
	
	@NotEmpty
	@Email
	private String accmail;
	
	@Length(min=5,max=32)
	private String passwd;
	
	@NotEmpty
	private String passwordcon;
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	private String nameyoread;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameyoread() {
		return nameyoread;
	}

	public void setNameyoread(String nameyoread) {
		this.nameyoread = nameyoread;
	}
	

}

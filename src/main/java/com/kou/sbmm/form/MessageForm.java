package com.kou.sbmm.form;

import javax.validation.constraints.*;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.kou.sbmm.form.validator.CheckFeildEqual;
@Configuration
public class MessageForm {
	
	public String getMsgcont() {
		return msgcont;
	}

	public void setMsgcont(String msgcont) {
		this.msgcont = msgcont;
	}

	public String getMsgtitle() {
		return msgtitle;
	}

	public void setMsgtitle(String msgtitle) {
		this.msgtitle = msgtitle;
	}

	@NotEmpty(message = "common.empty")
	private String msgcont;	
	
	@Length(min=2,max=100,message="common.length.message")
	private String msgtitle;
	
	private String bepublic;

	public String getBepublic() {
		return bepublic;
	}

	public void setBepublic(String bepublic) {
		this.bepublic = bepublic;
	}
		

}

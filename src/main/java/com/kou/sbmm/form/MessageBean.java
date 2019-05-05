package com.kou.sbmm.form;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.kou.sbmm.entity.Spmmdb0002;

public class MessageBean extends Spmmdb0002 {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1738178862422240919L;

	private boolean disopenging = false;
	private boolean dislocking = false;

	private boolean disdelete = false;
	private boolean dislock = false;
	private boolean disunlock = false;

	public boolean isDisopenging() {
		return disopenging;
	}

	public void setDisopenging(boolean disopenging) {
		this.disopenging = disopenging;
	}

	public boolean isDisdelete() {
		return disdelete;
	}

	public void setDisdelete(boolean disdelete) {
		this.disdelete = disdelete;
	}

	public boolean isDislock() {
		return dislock;
	}

	public void setDislock(boolean dislock) {
		this.dislock = dislock;
	}

	public boolean isDisunlock() {
		return disunlock;
	}

	public void setDisunlock(boolean disunlock) {
		this.disunlock = disunlock;
	}

	public boolean isDislocking() {
		return dislocking;
	}

	public void setDislocking(boolean dislocking) {
		this.dislocking = dislocking;
	}

	public String getMsgcontlink() {
		if (this.getMsgcont() == null) {
			return "";
		}
		
		return this.getMsgcont();
//		
//		String regexp = "((http[s]{0,1}|ftp)://[a-zA-Z0-9\\.\\-]+\\.([a-zA-Z]{2,4})(:\\d+)?(/[a-zA-Z0-9\\.\\-~!@#$%^&*+?:_/=<>]*)?)|(www.[a-zA-Z0-9\\.\\-]+\\.([a-zA-Z]{2,4})(:\\d+)?(/[a-zA-Z0-9\\.\\-~!@#$%^&*+?:_/=<>]*)?)"; // 结束条件
//		Pattern pattern = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE);
//		Matcher matcher = pattern.matcher(this.getMsgcont());
//
//		String resultText = "";
//		int lastEnd = 0;
//
//		while (matcher.find()) {
//			resultText += this.getMsgcont().substring(lastEnd, matcher.start() - 1);
//			resultText += "<a href=\"" + matcher.group() + "\">" + matcher.group() + "</a>";
//			lastEnd = matcher.end();
//		}
//		resultText += this.getMsgcont().substring(lastEnd);
//		return resultText;
	}

}

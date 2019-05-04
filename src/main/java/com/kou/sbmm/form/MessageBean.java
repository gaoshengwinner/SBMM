package com.kou.sbmm.form;

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
	
}

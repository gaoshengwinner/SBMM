package com.kou.sbmm.entity;
// Generated 2019-5-1 15:01:06 by Hibernate Tools 5.0.6.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Spmmdb0001 generated by hbm2java
 */
@Entity
@Table(name = "spmmdb0001")
public class Spmmdb0001 implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5775863875925726174L;
	private Integer accid;
	private String accmail;
	private String name;
	private String nameyoread;
	private Date acccreatedatetime;
	private byte[] passwd;
	private Character accauth;

	public Spmmdb0001() {
	}

	public Spmmdb0001(String accmail) {
		this.accmail = accmail;
	}

	public Spmmdb0001(String accmail, String name, String nameyoread, Date acccreatedatetime, byte[] passwd,
			Character accauth) {
		this.accmail = accmail;
		this.name = name;
		this.nameyoread = nameyoread;
		this.acccreatedatetime = acccreatedatetime;
		this.passwd = passwd;
		this.accauth = accauth;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ACCID", unique = true, nullable = false)
	public Integer getAccid() {
		return this.accid;
	}

	public void setAccid(Integer accid) {
		this.accid = accid;
	}

	@Column(name = "ACCMAIL", nullable = false, length = 200)
	public String getAccmail() {
		return this.accmail;
	}

	public void setAccmail(String accmail) {
		this.accmail = accmail;
	}

	@Column(name = "NAME", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "NAMEYOREAD", length = 50)
	public String getNameyoread() {
		return this.nameyoread;
	}

	public void setNameyoread(String nameyoread) {
		this.nameyoread = nameyoread;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ACCCREATEDATETIME", length = 26)
	public Date getAcccreatedatetime() {
		return this.acccreatedatetime;
	}

	public void setAcccreatedatetime(Date acccreatedatetime) {
		this.acccreatedatetime = acccreatedatetime;
	}

	@Column(name = "PASSWD")
	public byte[] getPasswd() {
		return this.passwd;
	}

	public void setPasswd(byte[] passwd) {
		this.passwd = passwd;
	}

	@Column(name = "ACCAUTH", length = 1)
	public Character getAccauth() {
		return this.accauth;
	}

	public void setAccauth(Character accauth) {
		this.accauth = accauth;
	}

}

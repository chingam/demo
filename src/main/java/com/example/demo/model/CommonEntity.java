package com.example.demo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@MappedSuperclass
public class CommonEntity implements Serializable {

	private static final long serialVersionUID = 98586286207203763L;

	@Column(name = "t_archive")
	private int archive = 0;

	@Column(name = "t_entry_user")
	private String T_ENTRY_USER;

	@Column(name = "t_entry_date")
	private Date T_ENTRY_DATE;

	@Column(name = "t_upd_user")
	private String T_UPD_USER;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "t_upd_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public Date T_UPD_DATE;

	public int getArchive() {
		return archive;
	}

	public void setArchive(int archive) {
		this.archive = archive;
	}

	public String getT_ENTRY_USER() {
		return T_ENTRY_USER;
	}

	public void setT_ENTRY_USER(String t_ENTRY_USER) {
		T_ENTRY_USER = t_ENTRY_USER;
	}

	public Date getT_ENTRY_DATE() {
		return T_ENTRY_DATE;
	}

	public void setT_ENTRY_DATE(Date t_ENTRY_DATE) {
		T_ENTRY_DATE = t_ENTRY_DATE;
	}

	public String getT_UPD_USER() {
		return T_UPD_USER;
	}

	public void setT_UPD_USER(String t_UPD_USER) {
		T_UPD_USER = t_UPD_USER;
	}

	public Date getT_UPD_DATE() {
		return T_UPD_DATE;
	}

	public void setT_UPD_DATE(Date t_UPD_DATE) {
		T_UPD_DATE = t_UPD_DATE;
	}

	

}

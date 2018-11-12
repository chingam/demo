package com.example.demo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Table(name = "t02017")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class T02017 implements Serializable {

	private static final long serialVersionUID = 1539040557093520619L;

	@Id
	@Column(name = "t_status_code")
	private String statusCode;
	
	@Column(name = "t_lang1_name")
	private String langName1;
	
	@Column(name = "t_lang2_name")
	private String langName2;
	
	@Column(name = "t_entry_user")
	private String entryUser;
	
	@Column(name = "t_entry_date")
	private Date entryDate;
	
	@Column(name = "t_upd_user")
	private String updateUser;
	
	@Column(name = "t_upd_date")
	private Date updateDate;

}

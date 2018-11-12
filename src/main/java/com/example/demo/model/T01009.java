package com.example.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "t01009")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class T01009 extends CommonEntity implements Serializable {

	private static final long serialVersionUID = 8472532406483618939L;

	@Id
	@Column(name = "t_emp_code")
	private String employeeCode;

	@Column(name = "t_site_code")
	private String siteCode;

	@Column(name = "t_first_lang1_name")
	private String firstLangName1;

	@Column(name = "t_first_lang2_name")
	private String firstLangName2;

	@Column(name = "t_family_lang1_name")
	private String familyLangName1;

	@Column(name = "t_family_lang2_name")
	private String familyLangName2;

	@Column(name = "t_father_lang1_name")
	private String fatherLangName1;

	@Column(name = "t_father_lang2_name")
	private String fatherLangName2;

	@Column(name = "t_gfather_lang1_name")
	private String gfatherLangName1;

	@Column(name = "t_gfather_lang2_name")
	private String gfatherLangName2;

	@Column(name = "t_title_no")
	private String titleNo;

	@Column(name = "t_role_code")
	private String roleCode;

	@Column(name = "t_login_name")
	private String loginName;

	@Column(name = "t_pwd")
	private String password;

	@Column(name = "t_user_lang")
	private String userLang;

	@Column(name = "t_mobile_no")
	private String mobileNo;

	@Column(name = "t_email_id")
	private String emailId;

	@Column(name = "t_active_flag")
	private String activeFlag;

	@Transient
	private String jobTileLan1;
	@Transient
	private String jobTileLan2;

	@Transient
	private String zoneCode;

}

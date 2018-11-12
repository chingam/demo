package com.example.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "t01199")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class T01199 extends CommonEntity implements Serializable {

	private static final long serialVersionUID = -8699362275742438802L;

	@Id
	@Column(name = "t_link_label_id")
	private Integer linkLabelID;

	@Column(name = "t_lang2_name")
	private String langName2;

	@Column(name = "t_lang1_name")
	private String langName1;

	@Column(name = "t_link_text")
	private String linkText;

	@Column(name = "t_link_seperation")
	private Integer linkSeperation;

	@Column(name = "t_role_code")
	private String roleCode;

	@Column(name = "t_inactive_flag")
	private String inactiveFlag;
}

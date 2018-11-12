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
@Table(name = "t01003")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class T01003 extends CommonEntity implements Serializable {

	private static final long serialVersionUID = -953143919926275355L;

	@Id
	@Column(name = "t_form_code")
	private String formCode;

	@Column(name = "t_lang1_name")
	private String langName1;

	@Column(name = "t_lang2_name")
	private String langName2;

	@Column(name = "t_version_no")
	private String version;

	@Column(name = "t_report_flag")
	private String reportFlag;

}

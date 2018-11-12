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
@Table(name = "t01008")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class T01008 extends CommonEntity implements Serializable {

	private static final long serialVersionUID = 7833549723341827925L;

	@Id
	@Column(name = "t_form_code")
	private String formCode;
	
	@Transient
	private String langName1;
	
	@Transient
	private String langName2;

	@Column(name = "t_role_code")
	private String roleCode;

	@Column(name = "t_can_opn")
	private String canOpen;

	@Column(name = "t_can_ins")
	private String canInsert;

	@Column(name = "t_can_upd")
	private String canUpdate;

	@Column(name = "t_can_del")
	private String canDelete;

	@Column(name = "t_can_qry")
	private String canQuery;

}

package com.example.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "t91007")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PracticeSetting extends CommonEntity implements Serializable {

	private static final long serialVersionUID = -5171672287182557683L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "t_practice_id")
	private String practiceId;

	@Column(name = "t_practice_code")
	private String practiceCode;

	@Column(name = "t_patient_no")
	private String patientNo;

	@Column(name = "t_lang1_name")
	private String langName1;

	@Column(name = "t_lang2_name")
	private String langName2;

}

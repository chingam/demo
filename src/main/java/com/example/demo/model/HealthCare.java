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
@Table(name = "t91008")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HealthCare extends CommonEntity implements Serializable {

	private static final long serialVersionUID = 4727929950175568172L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "t_practice_id")
	private String healthCareId;

	@Column(name = "t_practice_code")
	private String healthCareCode;

	@Column(name = "t_patient_no")
	private String patientNo;

	@Column(name = "t_lang1_name")
	private String langName1;

	@Column(name = "t_lang2_name")
	private String langName2;

}

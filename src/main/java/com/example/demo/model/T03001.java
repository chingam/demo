package com.example.demo.model;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "t03001")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class T03001 extends CommonEntity implements Serializable{

	private static final long serialVersionUID = 7088626601142936923L;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "t_patient_no")
	private String patientNo;
	
	@Column(name = "t_birthdate")
	private Date birthDate;
	@Column(name = "t_birthplace")
	private String birthPlace;
	@Column(name = "t_idissuedate")
	private Date idIssueDate;
	@Column(name = "idissueplace")
	private String idIssuePlace;
	@Column(name = "t_maritalstatus")
	private String maritalStatus;
	@Column(name = "t_motherpatientno")
	private String motherPatientNo;
	@Column(name = "t_family_name_native")
	private String familyNameNative;
	@Column(name = "t_family_name")
	private String familyName;
	@Column(name = "t_father_name_native")
	private String fatherNameNative;
	@Column(name = "t_father_name")
	private String fatherName;
	@Column(name = "t_grandfather_name_native")
	private String grandfatherNameNative;
	@Column(name = "t_grandfathername")
	private String grandfatherName;
	@Column
	private String firstNameNative;
	@Column
	private String firstName;
	@Column
	private String motherNameNative;
	@Column
	private String motherName;
	@Column
	private String nationalityCode;
	@Column
	private String nationalityId;
	@Column
	private Date passportIssueDate;
	@Column
	private String passportIssuePlace;
	@Column
	private String passportNo;
	@Column
	private String phoneHome;
	@Column
	private String phoneWork;
	@Column
	private String mobileNo;
	@Column
	private String postalCode;
	@Column
	private String addressLineOne;
	@Column
	private String addressLineTwo;
	@Column
	private String religionCode;
	@Column
	private String relationCode;
	@Column
	private String gender;
	@Column
	private String chartNo;
	@Column
	private String city;
	@Column
	private String country;
}

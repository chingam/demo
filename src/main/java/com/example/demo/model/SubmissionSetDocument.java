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
@Table(name = "t91010")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubmissionSetDocument extends CommonEntity implements Serializable{
	private static final long serialVersionUID = 8409561566544372433L;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "t_submission_id")
	private String submissionId;
	
	@Column(name = "t_patient_no")
	private String patientNo;
	
	@Column(name = "t_contenttype_code")
	private String contentTypeCode;
	
	@Column(name = "t_entryuuid")
	private String entryUuid;
	
	@Column(name = "t_sourceid")
	private String sourceId;
	
	@Column(name = "t_submissiontime")
	private Date submissionTime;
	
	@Column(name = "t_titledocument")
	private String titleDocument;
}

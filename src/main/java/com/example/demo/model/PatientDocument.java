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
@Table(name = "t91005")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PatientDocument extends CommonEntity implements Serializable{

	private static final long serialVersionUID = -5694160439761127637L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "t_patient_doc_id")
	private String patientDocId;
	
	@Column(name = "t_patientno")
	private String patientNo;
	
	
	@Column(name = "t_classcode")
	private String classCode; // from Code
	
	@Column(name = "t_creationtime")
	private Date creationTime;
	
	@Column(name = "t_entry_uuid")
	private String entryUuid;
	
	@Column(name = "t_formatcode")
	private String formatCode; // From Code
	
	@Column(name = "t_healthcarefacilitytypecode")
	private String healthcareFacilityTypeCode; // From Code
	
	@Column(name = "t_practicesettingcode")
	private String practiceSettingCode; //From code
	
	@Column(name = "t_typecode")
	private String typeCode; //From code
	
	@Column(name = "t_uri")
	private String uri;
	
	@Column(name = "t_document_id")
	private String documentId;
	
	@Column(name = "t_logical_uuid")
	private String logicalUuid;
	
	@Column(name = "t_mimetype")
	private String mimeType;
	
	@Column(name = "t_tile")
	private String docTitle;
	
	@Column(name = "t_servicestarttime")
	private Date serviceStartTime;
	
	@Column(name = "t_servicestoptime")
	private Date serviceStopTime;
	
}

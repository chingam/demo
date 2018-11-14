package com.example.demo.model;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PatientInfo {
	private String patientId;
	private String title;
	private Date creationDate;
	private String status;
	private Date serviceDate;
	private String homeCommunityId;
	private String repositoryUniqueId;
	private String documentUniqueId;
	private String mimeType;
	private String url;
	private List<MetaDataInfo> metaData;
}

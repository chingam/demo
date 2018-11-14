package com.example.demo.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.model.MetaDataInfo;
import com.example.demo.model.PatientInfo;

@Component
public class RegistryQueryService {

	public PatientInfo getPatientMetaData(String patientNo, String returnType) {
		List<MetaDataInfo> metaData = Arrays.asList(new MetaDataInfo("hash", "d62e7d16cd29abde6d13abc44e69479526978c37"), new MetaDataInfo("size", "2416"), new MetaDataInfo("repositoryUniqueId", "1.3.6.1.4.1.21367.2011.2.3.248"));
		PatientInfo patientInfo = new PatientInfo();
		patientInfo.setPatientId("9fe89bd266ef460^^^&1.3.6.1.4.1.21367.2005.13.20.1000&ISO");
		patientInfo.setTitle("AllergyDoc");
		patientInfo.setCreationDate(new Date());
		patientInfo.setStatus("Approved");
		patientInfo.setServiceDate(new Date());
		patientInfo.setHomeCommunityId("2423232323");
		patientInfo.setRepositoryUniqueId("1.3.6.1.4.1.21367.2011.2.3.248");
		patientInfo.setDocumentUniqueId("1.3.6.1.4.1.12559.11.1.2.2.1.1.3.125974");
		patientInfo.setMimeType("text/xml");
		patientInfo.setUrl("www.medisys.com.sa");
		patientInfo.setMetaData(metaData);
		return patientInfo;
	}

}

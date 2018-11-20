package com.example.demo.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.Address;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.AssigningAuthority;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.Association;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.AssociationLabel;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.AssociationType;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.AvailabilityStatus;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.Code;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.DocumentAvailability;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.DocumentEntry;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.DocumentEntryType;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.Folder;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.Identifiable;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.LocalizedString;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.Name;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.ObjectReference;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.PatientInfo;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.Person;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.SubmissionSet;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.Version;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.XpnName;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.FindDocumentsQuery;
import org.openehealth.ipf.commons.ihe.xds.core.responses.QueryResponse;
import org.openehealth.ipf.commons.ihe.xds.core.responses.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.ApplicationConfig;
import com.example.demo.DateUtil;
import com.example.demo.model.PatientDocument;
import com.example.demo.model.T03001;
import com.example.demo.repo.AssociationRepository;
import com.example.demo.repo.CodeRepository;
import com.example.demo.repo.PatientDocumentRepository;
import com.example.demo.repo.T03001Repository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CreateQueryResponseService {

	@Autowired private ApplicationConfig appConfig;
	@Autowired private T03001Repository patientRepository;
	@Autowired private AssociationRepository associationRepository;
	@Autowired private PatientDocumentRepository patientDocumentRepository;
	@Autowired private CodeRepository codeRepository;

	public QueryResponse createResponse(FindDocumentsQuery findDocumentsQuery) {
		if (findDocumentsQuery.getPatientId() == null || StringUtils.isBlank(findDocumentsQuery.getPatientId().getId())) {
			return sendErrorResponse();
		}
		String patientId = findDocumentsQuery.getPatientId().getId();
		
		QueryResponse response = new QueryResponse();
		response.setDocumentEntries(crateDocumentEntries(patientId));
//		response.setAssociations(createAssociation(patientId));
//		response.setFolders(createFolders(patientId));
//		response.setSubmissionSets(creatieSubmissionSets(patientId));
//		response.setReferences(createObjectReferences(patientId));
		response.setStatus(Status.SUCCESS);
		return response;
	}

	private QueryResponse sendErrorResponse() {
		QueryResponse response = new QueryResponse();
		response.setStatus(Status.FAILURE);
		return response;
	}

	private List<ObjectReference> createObjectReferences(String patientId) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<SubmissionSet> creatieSubmissionSets(String patientId) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<Folder> createFolders(String patientId) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<DocumentEntry> crateDocumentEntries(String patientId) {
		PatientDocument formObj = patientDocumentRepository.findByPatientNo(patientId);
		if (formObj == null) {
			return Collections.emptyList();
		}

		List<DocumentEntry> docList = new ArrayList<>();
		DocumentEntry doc = new DocumentEntry();
		doc.setAvailabilityStatus(AvailabilityStatus.APPROVED);
		
		Code classCode = createCode(formObj.getClassCode());
		if (classCode != null) {
			doc.setClassCode(classCode);
		}
		
		// doc.setComments(LocalizedString.);
		if (formObj.getCreationTime() != null) {
			doc.setCreationTime(DateUtil.format(formObj.getCreationTime(), DateUtil.HL7v2_DATE_FORMAT));
		}
		
		// doc.setCreationTime(Timestamp);
		doc.setDocumentAvailability(DocumentAvailability.ONLINE);
		if (StringUtils.isNotEmpty(formObj.getEntryUuid())) {
			doc.setEntryUuid(formObj.getEntryUuid());
		}
		
		Code formatCode = createCode(formObj.getFormatCode());
		if (formatCode != null) {
			doc.setFormatCode(formatCode);
		}
		
		doc.setHash(genarateHashValue(patientId, formObj.getMimeType()));
		Code healthcareFacilityTypeCode = createCode(formObj.getHealthcareFacilityTypeCode());
		if (healthcareFacilityTypeCode != null) {
			doc.setHealthcareFacilityTypeCode(healthcareFacilityTypeCode);
		}
		doc.setHomeCommunityId(appConfig.getHomeCommunityId());
		doc.setLanguageCode("en-us");
		Person legalAuthenticator = new Person();
		Identifiable identifiable = new Identifiable("1.2.3.4.5..7.8.33.20132", new AssigningAuthority(appConfig.getAssigningauthority()));
		legalAuthenticator.setId(identifiable);
		doc.setLegalAuthenticator(legalAuthenticator);
		if (StringUtils.isNotEmpty(formObj.getLogicalUuid())) {
			doc.setLogicalUuid(formObj.getLogicalUuid());
		}
		if (StringUtils.isNotEmpty(formObj.getMimeType())) {
			doc.setMimeType(formObj.getMimeType());
		}
		
		Identifiable patientObj = new Identifiable(patientId, new AssigningAuthority(appConfig.getAssigningauthority()));
		doc.setPatientId(patientObj);

		Code practiceSettingCode = createCode(formObj.getPracticeSettingCode());
		if (practiceSettingCode != null) {
			doc.setPracticeSettingCode(practiceSettingCode);
		}

		doc.setRepositoryUniqueId(appConfig.getRepositoryId());
		if (formObj.getServiceStartTime() != null) {
			doc.setServiceStartTime(DateUtil.format(formObj.getServiceStartTime(), DateUtil.HL7v2_DATE_FORMAT));
		}
		if (formObj.getServiceStopTime() != null) {
			doc.setServiceStopTime(DateUtil.format(formObj.getServiceStopTime(), DateUtil.HL7v2_DATE_FORMAT));
		}
		
		doc.setSize(generateSize(patientId, formObj.getMimeType()));

		Optional<T03001> paOptional = patientRepository.findById(patientId);
		if (paOptional.isPresent()) {
			T03001 patientOb = paOptional.get();
			PatientInfo sourcePatientInfo = new PatientInfo();
			Name name = new XpnName();
			name.setFamilyName(patientOb.getFamilyNameNative());
			sourcePatientInfo.setName(name);
			sourcePatientInfo.setGender(patientOb.getGender());
			sourcePatientInfo.setDateOfBirth(DateUtil.format(patientOb.getBirthDate(), DateUtil.HL7v2_DATE_FORMAT));
			Address address = new Address();
			address.setCity(StringUtils.isNotEmpty(patientOb.getCity()) ? patientOb.getCity() : "");
			address.setCountry(StringUtils.isNotEmpty(patientOb.getCountry()) ? patientOb.getCountry() : "");
			address.setZipOrPostalCode(StringUtils.isNotEmpty(patientOb.getPostalCode()) ? patientOb.getPostalCode() : "");
			sourcePatientInfo.setAddress(address);
			doc.setSourcePatientInfo(sourcePatientInfo);
		}

		if (StringUtils.isNotEmpty(formObj.getDocTitle())) {
			doc.setTitle(new LocalizedString(formObj.getDocTitle()));
		}
		
		doc.setType(DocumentEntryType.STABLE);
		
		Code typeCode = createCode(formObj.getTypeCode());
		if (typeCode != null) {
			doc.setTypeCode(typeCode);
		}
		
		doc.setUniqueId(patientId);
		if (StringUtils.isNotEmpty(formObj.getUri())) {
			doc.setUri(formObj.getUri());
		}

		Version version = new Version("1");
		doc.setVersion(version);
		docList.add(doc);
		return docList;
	}

	private Long generateSize(String patientId, String mimeType) {
		String fileExtension = "pdf";
		if (StringUtils.isNotEmpty(mimeType) && "text/xml".equals(mimeType)) {
			fileExtension = "xml";
		}
		File file = new File(appConfig.getPath() + File.separator + patientId + "." + fileExtension);
		if (file.exists()) {
			return file.length();
		}
		return null;
	}

	private String genarateHashValue(String patientId, CharSequence mimeType) {
		String fileExtension = "pdf";
		if (StringUtils.isNotEmpty(mimeType) && "text/xml".equals(mimeType)) {
			fileExtension = "xml";
		}

		StringBuilder builder = new StringBuilder();
		File filePath = new File(appConfig.getPath() + File.separator + patientId + "." + fileExtension);
		InputStream in;
		try {
			in = new FileInputStream(filePath);
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			byte[] aa = digest.digest(IOUtils.toByteArray(in));
			for (byte b : aa) {
				String hexString = Integer.toHexString((int) b & 0xff);
				builder.append(hexString.length() == 2 ? hexString : "0" + hexString);
			}
			System.out.println(builder.toString());
		} catch (NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
		}
		return builder.toString();

	}

	private Code createClassCode(String patientId) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<Association> createAssociation(String patientId) {
		List<Association> assList = new ArrayList<>();
		com.example.demo.model.Association assObj = associationRepository.findByPatientNo(patientId);
		if (assObj == null)
			return null;

		Association association = new Association();
		association.setAssociationPropagation(assObj.getAssociationPropagation());
		association.setAssociationType(AssociationType.valueOfOpcode30(assObj.getAssociationType()));
		association.setAvailabilityStatus(AvailabilityStatus.APPROVED);

		Code docCode = createCode(assObj.getDocCode());
		if (docCode != null) {
			association.setDocCode(docCode);
		}
		association.setEntryUuid(assObj.getEntryUuid());
		association.setLabel(AssociationLabel.valueOf(assObj.getLabel()));
		association.setNewStatus(AvailabilityStatus.APPROVED);
		association.setOriginalStatus(AvailabilityStatus.APPROVED);
		association.setPreviousVersion(assObj.getPreviousVersion());
		association.setSourceUuid(assObj.getSourceUuid());
		association.setTargetUuid(assObj.getTargetUuid());
		assList.add(association);
		return assList;
	}

	private Code createCode(String value) {
		com.example.demo.model.Code code = codeRepository.findByCode(value);
		if (code == null)
			return null;
		Code c = new Code();
		LocalizedString l = new LocalizedString();
		c.setCode(code.getCode());
		l.setCharset("");
		l.setLang("");
		l.setValue(code.getDisplayName());
		c.setDisplayName(l);
		c.setSchemeName(code.getSchemeName());
		return c;
	}
}

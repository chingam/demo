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
import org.openehealth.ipf.commons.ihe.xds.core.metadata.Author;
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
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.FindFoldersQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.FindSubmissionSetsQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.GetAllQuery;
import org.openehealth.ipf.commons.ihe.xds.core.responses.ErrorInfo;
import org.openehealth.ipf.commons.ihe.xds.core.responses.QueryResponse;
import org.openehealth.ipf.commons.ihe.xds.core.responses.Severity;
import org.openehealth.ipf.commons.ihe.xds.core.responses.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.ApplicationConfig;
import com.example.demo.DateUtil;
import com.example.demo.model.PatientDocument;
import com.example.demo.model.SubmissionSetDocument;
import com.example.demo.model.T03001;
import com.example.demo.repo.AssociationRepository;
import com.example.demo.repo.CodeRepository;
import com.example.demo.repo.PatientDocumentRepository;
import com.example.demo.repo.SubmissionSetDocumentRepository;
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
	@Autowired private SubmissionSetDocumentRepository submissionSetRepository;

	public QueryResponse createResponse(FindDocumentsQuery findDocumentsQuery) {
		if (findDocumentsQuery.getPatientId() == null || StringUtils.isBlank(findDocumentsQuery.getPatientId().getId())) {
			return sendErrorResponse();
		}
		String patientId = findDocumentsQuery.getPatientId().getId();
		
		QueryResponse response = new QueryResponse();
		response.setStatus(Status.SUCCESS);
		try {
			response.setDocumentEntries(crateDocumentEntries(patientId));
		} catch (Exception e) {
			log.error("Could not create response FindDocumentsQuery {}", e);
			return sendErrorResponse();
		}
//		response.setAssociations(createAssociation(patientId));
//		response.setFolders(createFolders(patientId));
//		response.setSubmissionSets(creatieSubmissionSets(patientId));
//		response.setReferences(createObjectReferences(patientId));
		return response;
	}
	
	public QueryResponse createResponse(FindSubmissionSetsQuery findSubmission) {
		if (findSubmission == null || findSubmission.getPatientId() == null || StringUtils.isBlank(findSubmission.getPatientId().getId())) {
			return sendErrorResponse();
		}
		String patientId = findSubmission.getPatientId().getId();
		QueryResponse response = new QueryResponse();
		response.setStatus(Status.SUCCESS);
		try {
			response.setSubmissionSets(creatieSubmissionSets(patientId));
		} catch (Exception e) {
			log.error("Could not create response FindSubmissionSetsQuery {}", e);
			return sendErrorResponse();
		}
		return response;
		
	}
	public QueryResponse createResponse(GetAllQuery getAllQuery) {
		if (getAllQuery == null || getAllQuery.getPatientId() == null || StringUtils.isBlank(getAllQuery.getPatientId().getId())) {
			return sendErrorResponse();
		}
		String patientId = getAllQuery.getPatientId().getId();
		QueryResponse response = new QueryResponse();
		response.setStatus(Status.SUCCESS);
		try {
			response.setDocumentEntries(crateDocumentEntries(patientId));
			response.setSubmissionSets(creatieSubmissionSets(patientId));
			response.setFolders(createFolders(patientId));
		} catch (Exception e) {
			log.error("Could not create response GetAllQuery {}", e);
			return sendErrorResponse();
		}
		return null;
	}
	
	public QueryResponse createResponse(FindFoldersQuery findFoldersQuery) {
		if (findFoldersQuery == null || findFoldersQuery.getPatientId() == null || StringUtils.isBlank(findFoldersQuery.getPatientId().getId())) {
			return sendErrorResponse();
		}
		String patientId = findFoldersQuery.getPatientId().getId();
		QueryResponse response = new QueryResponse();
		response.setStatus(Status.SUCCESS);
		try {
			response.setFolders(createFolders(patientId));
		} catch (Exception e) {
			log.error("Could not create response FindFoldersQuery {}", e);
			return sendErrorResponse();
		}
		return response;
		
	}

	private QueryResponse sendErrorResponse() {
		QueryResponse response = new QueryResponse();
		response.setStatus(Status.FAILURE);
		List<ErrorInfo> errors = new ArrayList<>();
		ErrorInfo error = new ErrorInfo();
		error.setSeverity(Severity.ERROR);
		errors.add(error);
		error.setCodeContext("error : patient id not found !");
		response.setErrors(errors);
		return response;
	}

	private List<ObjectReference> createObjectReferences(String patientId) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<SubmissionSet> creatieSubmissionSets(String patientId) {
		List<SubmissionSet> sets = new ArrayList<>();
		SubmissionSetDocument submissionSetObj = submissionSetRepository.findByPatientNo(patientId);
		if (submissionSetObj == null) {
			return Collections.emptyList();
		}
		SubmissionSet submissionSet = new SubmissionSet();
		submissionSet.setAuthor(createAuthor(patientId));
		submissionSet.setAvailabilityStatus(AvailabilityStatus.APPROVED);
		
		Code contentTypeCode = createCode(submissionSetObj.getContentTypeCode());
		if (contentTypeCode != null) {
			submissionSet.setContentTypeCode(contentTypeCode);
		}
		if (StringUtils.isNotBlank(submissionSetObj.getEntryUuid())) {
			submissionSet.setEntryUuid(submissionSetObj.getEntryUuid());
		}
		submissionSet.setHomeCommunityId(appConfig.getHomeCommunityId());
		submissionSet.setPatientId(getPatientId(patientId));
		if (StringUtils.isNotBlank(submissionSetObj.getSourceId())) {
			submissionSet.setSourceId(submissionSetObj.getSourceId());
		}
		if (submissionSetObj.getSubmissionTime() != null) {
			submissionSet.setSubmissionTime(DateUtil.format(submissionSetObj.getSubmissionTime(), DateUtil.HL7v2_DATE_FORMAT));
		}
		if (StringUtils.isNotBlank(submissionSetObj.getTitleDocument())) {
			submissionSet.setTitle(new LocalizedString(submissionSetObj.getTitleDocument()));
		}
		submissionSet.setUniqueId(patientId);
		submissionSet.setVersion(new Version("1.0.0"));
		sets.add(submissionSet);
		return sets;
	}

	private Identifiable getPatientId(String patientId) {
		Identifiable identifiable = new Identifiable(patientId, new AssigningAuthority(appConfig.getAssigningauthority()));
		return identifiable;
	}

	private Author createAuthor(String patientId) {
		Author author = new Author();
		author.setAuthorPerson(createAuthorPerson(patientId));
		return author;
	}

	private Person createAuthorPerson(String patientId) {
		Person person = new Person();
		person.setId(createIdentifiable(patientId));
		person.setName(createAuthorName(patientId));
		return person;
	}

	private Name createAuthorName(String patientId) {
		// TODO Implementation
		Name name = new XpnName();
		name.setDegree("MBBS, BMBS, MBChB, MBBCh)");
		name.setFamilyName("GM Salam");
		name.setGivenName("Hossain");
		name.setPrefix("Dr");
		name.setSuffix("Sur");
		return name;
	}

	private Identifiable createIdentifiable(String patientId) {
		Identifiable identifi= new Identifiable();
		identifi.setId(appConfig.getAssigningauthority());
		identifi.setId(patientId);
		return identifi;
	}

	private List<Folder> createFolders(String patientId) {
		List<Folder> list = new ArrayList<>();
		
		Folder folder = new Folder();
		folder.setAvailabilityStatus(AvailabilityStatus.APPROVED);
		folder.setComments(new LocalizedString("folder"));
		folder.setEntryUuid("1.3.4.5.6.44.56");
		folder.setHomeCommunityId(appConfig.getHomeCommunityId());
//		folder.setLastUpdateTime(lastUpdateTime);
		folder.setPatientId(createIdentifiable(patientId));
		folder.setTitle(new LocalizedString("Folder Document"));
		folder.setVersion(new Version("1.0.0"));
		list.add(folder);
		return list;
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

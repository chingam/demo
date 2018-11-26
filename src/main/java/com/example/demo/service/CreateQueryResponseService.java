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
import java.util.stream.Collectors;

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
import org.openehealth.ipf.commons.ihe.xds.core.metadata.Organization;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.PatientInfo;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.Person;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.SubmissionSet;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.Version;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.XpnName;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.FindDocumentsQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.FindFoldersQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.FindSubmissionSetsQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.GetAllQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.GetDocumentsAndAssociationsQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.GetDocumentsQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.GetFoldersQuery;
import org.openehealth.ipf.commons.ihe.xds.core.responses.QueryResponse;
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

	private static final String EN_US = "en-US";
	private static final String UTF_8 = "UTF-8";
	@Autowired private ApplicationConfig appConfig;
	@Autowired private T03001Repository patientRepository;
	@Autowired private AssociationRepository associationRepository;
	@Autowired private PatientDocumentRepository patientDocumentRepository;
	@Autowired private CodeRepository codeRepository;
	@Autowired private SubmissionSetDocumentRepository submissionSetRepository;

	public QueryResponse createResponse(GetDocumentsAndAssociationsQuery getDocumentsQuery) {
		if (getDocumentsQuery == null || (getDocumentsQuery.getUniqueIds() == null && getDocumentsQuery.getUuids() == null)) {
			return sendErrorResponse();
		}
		List<DocumentEntry> docs = new ArrayList<>();
		List<PatientDocument> docments = new ArrayList<>();
		
		if (getDocumentsQuery.getUniqueIds() != null && !getDocumentsQuery.getUniqueIds().isEmpty()) {
			getDocumentsQuery.getUniqueIds().stream().forEach(a -> getPatientDocuments(docments, a));
		}
		
		docs.addAll(docments.stream().map(this::getDocumentEntry).collect(Collectors.toList()));
		
		if (getDocumentsQuery.getUuids() != null && !getDocumentsQuery.getUuids().isEmpty()) {
			docs.addAll(getDocumentsQuery.getUuids().stream().map(this::getObjectRef).map(this::getDocumentEntry).collect(Collectors.toList()));
		}
		
		QueryResponse response = new QueryResponse();
		response.setStatus(Status.SUCCESS);
		response.setDocumentEntries(docs);
		return response;
	}

	private void getPatientDocuments(List<PatientDocument> docments, String a) {
		docments.addAll(patientDocumentRepository.findAllByEntryUuid(a));
	}
	
	public QueryResponse createResponse(FindDocumentsQuery findDocumentsQuery) {
		if (findDocumentsQuery == null || findDocumentsQuery.getPatientId() == null) {
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
		return response;
	}
	
	public QueryResponse createResponse(GetFoldersQuery getFolderQuery) {
		if (getFolderQuery == null || (getFolderQuery.getUniqueIds() == null && getFolderQuery.getUuids() == null)) {
			return sendErrorResponse();
		}
		List<DocumentEntry> docs = new ArrayList<>();
		List<PatientDocument> docments = new ArrayList<>();
		
		if (getFolderQuery.getUniqueIds() != null && !getFolderQuery.getUniqueIds().isEmpty()) {
			getFolderQuery.getUniqueIds().stream().forEach(a -> getPatientDocuments(docments, a));
		}
		docs.addAll(docments.stream().map(this::getDocumentEntry).collect(Collectors.toList()));
		
		if (getFolderQuery.getUuids() != null && !getFolderQuery.getUuids().isEmpty()) {
			List<DocumentEntry> docsEntry = getFolderQuery.getUuids().stream().map(this::getObjectRef).map(this::getDocumentEntry).collect(Collectors.toList());
			docs.addAll(docsEntry);
		}
		QueryResponse response = new QueryResponse();
		response.setStatus(Status.SUCCESS);
		response.setDocumentEntries(docs);
		return response;
	}
	
	public QueryResponse createResponse(GetDocumentsQuery getDocumentQuery) {
		if (getDocumentQuery == null || (getDocumentQuery.getUniqueIds() == null && getDocumentQuery.getUuids() == null)) {
			return sendErrorResponse();
		}
		List<DocumentEntry> docs = new ArrayList<>();
		List<PatientDocument> docments = new ArrayList<>();
		
		if (getDocumentQuery.getUniqueIds() != null && !getDocumentQuery.getUniqueIds().isEmpty()) {
			getDocumentQuery.getUniqueIds().stream().forEach(a -> getPatientDocuments(docments, a));
			docs.addAll(docments.stream().map(this::getDocumentEntry).collect(Collectors.toList()));
		}
		
		if (getDocumentQuery.getUuids() != null && !getDocumentQuery.getUuids().isEmpty()) {
			List<DocumentEntry> docsEntry = getDocumentQuery.getUuids().stream().map(this::getObjectRef).map(this::getDocumentEntry).collect(Collectors.toList());
			docs.addAll(docsEntry);
		}
		QueryResponse response = new QueryResponse();
		response.setStatus(Status.SUCCESS);
		response.setDocumentEntries(docs);
		return response;
	}
	
	public QueryResponse createResponse(FindSubmissionSetsQuery findSubmission) {
		if (findSubmission == null || findSubmission.getPatientId() == null) {
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
		if (getAllQuery == null || getAllQuery.getPatientId() == null) {
			return sendErrorResponse();
		}
		String patientId = getAllQuery.getPatientId().getId();
		QueryResponse response = new QueryResponse();
		response.setStatus(Status.SUCCESS);
		try {
			response.setDocumentEntries(crateDocumentEntries(patientId));
			response.setSubmissionSets(creatieSubmissionSets(patientId));
			response.setAssociations(createAssociation(patientId));
			response.setFolders(createFolders(patientId));
		} catch (Exception e) {
			log.error("Could not create response GetAllQuery {}", e);
			return sendErrorResponse();
		}
		return response;
	}
	
	public QueryResponse createResponse(FindFoldersQuery findFoldersQuery) {
		if (findFoldersQuery == null) {
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
		return response;
	}

	private List<SubmissionSet> creatieSubmissionSets(String patientId) {
		if (StringUtils.isBlank(patientId)) {
			return Collections.emptyList();
		}
		
		SubmissionSetDocument submissionSetObj = submissionSetRepository.findByPatientNo(patientId);
		if (submissionSetObj == null) {
			return Collections.emptyList();
		}
		
		List<SubmissionSet> sets = new ArrayList<>();
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
		return new Identifiable(patientId, new AssigningAuthority(appConfig.getAssigningauthority()));
	}

	private Author createAuthor(String userId) {
		Author author = new Author();
		Organization org = new Organization();
		org.setAssigningAuthority(new AssigningAuthority(appConfig.getAssigningauthority()));
		org.setIdNumber("276");
		org.setOrganizationName("King Abdulaziz Specialist JOUF2");
		author.getAuthorInstitution().add(org);
		author.setAuthorPerson(createAuthorPerson(userId));
		author.getAuthorSpecialty().add(createIdentifiable("3456"));
		return author;
	}

	private Person createAuthorPerson(String userId) {
		Person person = new Person();
		person.setId(createIdentifiable("304"));
		person.setName(createAuthorName(userId));
		return person;
	}

	private Name<?> createAuthorName(String patientId) {
		Name<?> name = new XpnName();
		name.setFamilyName("GM Salam");
		name.setGivenName("Hossain");
		name.setPrefix("Dr");
		name.setSuffix("Sur");
		return name;
	}

	private Identifiable createIdentifiable(String patientId) {
		Identifiable identifi= new Identifiable();
		identifi.setId(patientId);
		return identifi;
	}

	private List<Folder> createFolders(String patientId) {
		List<PatientDocument> patientDocs = patientDocumentRepository.findAllByPatientNo(patientId);
		if (patientDocs == null) {
			return Collections.emptyList();
		}
		return patientDocs.stream().map(this::getFolder).collect(Collectors.toList());
	}

	private Folder getFolder(PatientDocument patientDoc) {
		Folder folder = new Folder();
		folder.setAvailabilityStatus(AvailabilityStatus.APPROVED);
		folder.setComments(new LocalizedString("folder"));
		if (StringUtils.isNotBlank(patientDoc.getEntryUuid())) {
			folder.setEntryUuid(patientDoc.getEntryUuid());
		}
		folder.setHomeCommunityId(appConfig.getHomeCommunityId());
		if (StringUtils.isNotBlank(patientDoc.getPatientNo())) {
			folder.setPatientId(createIdentifiable(patientDoc.getPatientNo()));
		}
		folder.setTitle(new LocalizedString("Document"));
		folder.setVersion(new Version("1.0.0"));
		return folder;
	}

	private List<DocumentEntry> crateDocumentEntries(String patientId) {
		if (StringUtils.isBlank(patientId)) {
			Collections.emptyList();
		}
		
		List<PatientDocument> patientList = patientDocumentRepository.findAllByPatientNo(patientId);
		return patientList.stream().map(this::getDocumentEntry).collect(Collectors.toList());
	}

	private Long generateSize(String patientId, String mimeType) {
		String fileExtension = "pdf";
		if ("text/xml".equals(mimeType) || "application/xml".equals(mimeType)) {
			fileExtension = "xml";
		} else if ("application/zip".equals(mimeType)) {
			fileExtension = "zip";
		}
		File file = new File(appConfig.getPath() + File.separator + patientId + "." + fileExtension);
		if (file.exists()) {
			return file.length();
		}
		return 0L;
	}

	private String genarateHashValue(String patientId, CharSequence mimeType) {
		String fileExtension = "pdf";
		if (StringUtils.isNotEmpty(mimeType) && "text/xml".equals(mimeType)) {
			fileExtension = "xml";
		}

		StringBuilder builder = new StringBuilder();
		File filePath = new File(appConfig.getPath() + File.separator + patientId + "." + fileExtension);
		try (InputStream in = new FileInputStream(filePath)){
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			byte[] aa = digest.digest(IOUtils.toByteArray(in));
			for (byte b : aa) {
				String hexString = Integer.toHexString((int) b & 0xff);
				builder.append(hexString.length() == 2 ? hexString : "0" + hexString);
			}
		} catch (NoSuchAlgorithmException | IOException e) {
			log.error("Could not genarate Hash value", e.getMessage());
		}
		return builder.toString();

	}

	private List<Association> createAssociation(String patientId) {
		return associationRepository.findAllByPatientNo(patientId).stream().map(this::getAssociation).collect(Collectors.toList());
	}

	private Association getAssociation(com.example.demo.model.Association assObj) {
		Association association = new Association();
		if (assObj.getAssociationPropagation() != null) {
			association.setAssociationPropagation(assObj.getAssociationPropagation());
		}
		if (StringUtils.isNotEmpty(assObj.getAssociationType())) {
			association.setAssociationType(AssociationType.valueOfOpcode30(assObj.getAssociationType()));
		}
		
		association.setAvailabilityStatus(AvailabilityStatus.APPROVED);
		Code docCode = createCode(assObj.getDocCode());
		if (docCode != null) {
			association.setDocCode(docCode);
		}
		if (StringUtils.isNotEmpty(assObj.getEntryUuid())) {
			association.setEntryUuid(assObj.getEntryUuid());
		}
		if (StringUtils.isNotEmpty(assObj.getLabel())) {
			association.setLabel(AssociationLabel.valueOf(assObj.getLabel()));
		}
		association.setNewStatus(AvailabilityStatus.APPROVED);
		association.setOriginalStatus(AvailabilityStatus.APPROVED);
		if (StringUtils.isNotEmpty(assObj.getPreviousVersion())) {
			association.setPreviousVersion(assObj.getPreviousVersion());
		}
		if (StringUtils.isNotEmpty(assObj.getSourceUuid())) {
			association.setSourceUuid(assObj.getSourceUuid());
		}
		if (StringUtils.isNotEmpty(assObj.getTargetUuid())) {
			association.setTargetUuid(assObj.getTargetUuid());
		}
		return association;
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
	
	private DocumentEntry getDocumentEntry(PatientDocument formObj) {
		if (formObj == null) {
			return new DocumentEntry();
		}

		DocumentEntry doc = new DocumentEntry();
		doc.setAvailabilityStatus(AvailabilityStatus.APPROVED);
		
		// Financial standing and financial details
		//Education, training and employment experience
		// Religious beliefs.
		// Racial or ethnic origin
		//Sexuality
		//Criminal convictions
		doc.setAuthor(createAuthor("0505"));
		Code confidentialCode = new Code();
		confidentialCode.setCode("031");
		confidentialCode.setDisplayName(new LocalizedString("Physical or mental health", EN_US, UTF_8));
		confidentialCode.setSchemeName("1.2.3.4.5.6.7.8.9");
		doc.getConfidentialityCodes().add(confidentialCode);
		
		Code confidentialCode2 = new Code();
		confidentialCode2.setCode("034");
		confidentialCode2.setDisplayName(new LocalizedString("Social or family circumstances", EN_US, UTF_8));
		confidentialCode2.setSchemeName("1.2.3.4.5.6.7.34");
		doc.getConfidentialityCodes().add(confidentialCode2);
		
		Code classCode = createCode(formObj.getClassCode());
		doc.setClassCode(classCode);
		
		// doc.setComments(LocalizedString.)
		if (formObj.getCreationTime() != null) {
			doc.setCreationTime(DateUtil.format(formObj.getCreationTime(), DateUtil.HL7v2_DATE_FORMAT));
		}
		
		// doc.setCreationTime(Timestamp)
		doc.setDocumentAvailability(DocumentAvailability.ONLINE);
		
		//A globally unique identifier used to manage the entry. 
		doc.setEntryUuid(formObj.getPatientDocId());
		
		/*
		 * This list of codes represents the main clinical acts, such as a colonoscopy or an appendectomy, being documented. 
		 */
		Code eventCode = new Code("456", new LocalizedString("Colonoscopy", EN_US, UTF_8), "1.2.3.4.5.6.7.88");
		doc.getEventCodeList().add(eventCode);
		
		/*
		 * Code globally uniquely specifying the detailed technical format of the document. 
		 */
		Code formatCode = createCode(formObj.getFormatCode());
		doc.setFormatCode(formatCode);
		
		doc.setHash(genarateHashValue(formObj.getPatientNo(), formObj.getMimeType()));
		
		/*
		 * This code represents the type of organizational setting of the clinical encounter during which the documented act occurred
		 */
		Code healthcareFacilityTypeCode = createCode(formObj.getHealthcareFacilityTypeCode());
		doc.setHealthcareFacilityTypeCode(healthcareFacilityTypeCode);
		
		// A globally unique identifier for a community.
		doc.setHomeCommunityId(appConfig.getHomeCommunityId());
		
		Identifiable sourcePatientId = new Identifiable(formObj.getPatientNo(), new AssigningAuthority(appConfig.getAssigningauthority()));
		doc.setSourcePatientId(sourcePatientId);
		
		 //Specifies the human language of character data in the document. 
		doc.setLanguageCode("en-us");
		
		/*
		 * Characterizes a participant who has legally
			authenticated or attested the document within the
			authorInstitution. 
		 */
		Person legalAuthenticator = new Person();
		Identifiable identifiable = new Identifiable("1.2.3.4.5..7.8.33.20132", new AssigningAuthority(appConfig.getAssigningauthority()));
		legalAuthenticator.setId(identifiable);
		doc.setLegalAuthenticator(legalAuthenticator);
		doc.setLogicalUuid(formObj.getLogicalUuid());
		doc.setMimeType(formObj.getMimeType());
		
		// The patientId represents the subject of care of the 
		Identifiable patientObj = new Identifiable(StringUtils.isNotBlank(formObj.getPatientNo()) ? formObj.getPatientNo() : "", new AssigningAuthority(appConfig.getAssigningauthority()));
		doc.setPatientId(patientObj);

		// The code specifying the clinical specialty 
		//where the act that resulted in the document was performed (e.g.,Family Practice, Laboratory, Radiology). 
		Code practiceSettingCode = createCode(formObj.getPracticeSettingCode());
		doc.setPracticeSettingCode(practiceSettingCode);

		//The globally unique identifier of the repository where the document is stored.
		doc.setRepositoryUniqueId(appConfig.getRepositoryId());
		
		// Represents the start time the service being documented took place. 
		if (formObj.getServiceStartTime() != null) {
			doc.setServiceStartTime(DateUtil.format(formObj.getServiceStartTime(), DateUtil.HL7v2_DATE_FORMAT));
		}
		// Represents the stop time the service being documented took place. 
		if (formObj.getServiceStopTime() != null) {
			doc.setServiceStopTime(DateUtil.format(formObj.getServiceStopTime(), DateUtil.HL7v2_DATE_FORMAT));
		}
		
		if (StringUtils.isNotEmpty(formObj.getPatientNo())) {
			doc.setSize(generateSize(formObj.getPatientNo(), formObj.getMimeType()));
		}

		/*
		 * The sourcePatientId represents the subject of care
		 *	medical record Identifier (e.g., Patient Id) in the local
		 *	patient Identifier Domain of the creating entity. 
		 *
		 *This attribute contains demographic information of
		 *the source patient to whose medical record this
		 *document belongs
		 */
		Optional<T03001> paOptional = patientRepository.findById(formObj.getPatientNo());
		if (paOptional.isPresent()) {
			T03001 patientOb = paOptional.get();
			PatientInfo sourcePatientInfo = new PatientInfo();
			Identifiable identi = new Identifiable(patientOb.getPatientNo(), new AssigningAuthority(appConfig.getAssigningauthority()));
			sourcePatientInfo.getIds().add(identi);
			Name<?> name = new XpnName();
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

		// Represents the title of the document.
		doc.setTitle(new LocalizedString(formObj.getDocTitle()));
		
		doc.setType(DocumentEntryType.STABLE);
		
		/*
		 * A low-level classification of documents within a
			classCode that describes class, event, specialty, and setting.
		 */
		Code typeCode = createCode(formObj.getTypeCode());
		doc.setTypeCode(typeCode);
		
		// The globally unique identifier assigned by the document creator to this document. 
		
		doc.setUniqueId(formObj.getEntryUuid());
		
		doc.setUri(formObj.getUri());

		Version version = new Version("1");
		doc.setVersion(version);
		return doc;
	}

	public QueryResponse createResponseWithObjRef(GetDocumentsQuery getDocumentsQuery) {
		if (getDocumentsQuery == null || (getDocumentsQuery.getUniqueIds() == null && getDocumentsQuery.getUuids() == null)) {
			return sendErrorResponse();
		}
		
		QueryResponse response = new QueryResponse();
		List<ObjectReference> docRef = new ArrayList<>();
		List<PatientDocument> docments = new ArrayList<>();
		if (getDocumentsQuery.getUniqueIds() != null && !getDocumentsQuery.getUniqueIds().isEmpty()) {
			getDocumentsQuery.getUniqueIds().stream().forEach(a -> getPatientDocuments(docments, a));
		}
		
		docRef.addAll(docments.stream().map(this::getReferences).collect(Collectors.toList()));
		if (getDocumentsQuery.getUuids() != null && !getDocumentsQuery.getUuids().isEmpty()) {
			List<ObjectReference> docRefer = getDocumentsQuery.getUuids().stream().map(this::getObjectRef).map(this::getReferences).collect(Collectors.toList());
			docRef.addAll(docRefer);
		}
		
		response.setReferences(docRef);
		response.setStatus(Status.SUCCESS);
		return response;
	}
	
	public QueryResponse createResponseWithObjRef(GetFoldersQuery getFolderQuery) {
		if (getFolderQuery == null || (getFolderQuery.getUniqueIds() == null && getFolderQuery.getUuids() == null)) {
			return sendErrorResponse();
		}
		
		QueryResponse response = new QueryResponse();
		List<ObjectReference> docRef = new ArrayList<>();
		List<PatientDocument> docments = new ArrayList<>();
		if (getFolderQuery.getUniqueIds() != null && !getFolderQuery.getUniqueIds().isEmpty()) {
			getFolderQuery.getUniqueIds().stream().forEach(a -> getPatientDocuments(docments, a));
		}
		
		docRef.addAll(docments.stream().map(this::getReferences).collect(Collectors.toList()));
		if (getFolderQuery.getUuids() != null && !getFolderQuery.getUuids().isEmpty()) {
			List<ObjectReference> docRefer = getFolderQuery.getUuids().stream().map(this::getObjectRef).map(this::getReferences).collect(Collectors.toList());
			docRef.addAll(docRefer);
		}
		
		response.setReferences(docRef);
		response.setStatus(Status.SUCCESS);
		return response;
	}
	
	public QueryResponse createResponseWithObjRef(FindFoldersQuery findFolder) {
		if (findFolder == null || findFolder.getPatientId() == null) {
			return sendErrorResponse();
		}
		
		QueryResponse response = new QueryResponse();
		String patientId = findFolder.getPatientId().getId();
		List<PatientDocument> documents = patientDocumentRepository.findAllByPatientNo(patientId);
		response.setReferences(documents.stream().map(this::getReferences).collect(Collectors.toList()));
		response.setStatus(Status.SUCCESS);
		return response;
	}
	
	public QueryResponse createResponseWithObjRef(FindSubmissionSetsQuery findSub) {
		if (findSub == null || (findSub.getPatientId() == null)) {
			return sendErrorResponse();
		}
		QueryResponse response = new QueryResponse();
		String patientId = findSub.getPatientId().getId();
		List<SubmissionSetDocument> submission = submissionSetRepository.findAllByPatientNo(patientId);
		response.setReferences(submission.stream().map(this::getReferences).collect(Collectors.toList()));
		response.setStatus(Status.SUCCESS);
		return response;
	}

	private PatientDocument getObjectRef(String a) {
		Optional<PatientDocument> patientOption = patientDocumentRepository.findById(a);
		if (patientOption.isPresent()) return patientOption.get();
		return new PatientDocument();
	}
	
	public QueryResponse createResponseWithObjRef(GetAllQuery getAll) {
		if (getAll == null || getAll.getPatientId() == null) {
			return sendErrorResponse();
		}
		QueryResponse response = new QueryResponse();
		String patientId = getAll.getPatientId().getId();
		List<PatientDocument> documents = patientDocumentRepository.findAllByPatientNo(patientId);
		List<ObjectReference> objectList = documents.stream().map(this::getReferences).collect(Collectors.toList());
		List<SubmissionSetDocument> submission = submissionSetRepository.findAllByPatientNo(patientId);
		objectList.addAll(submission.stream().map(this::getReferences).collect(Collectors.toList()));
		response.setStatus(Status.SUCCESS);
		return response;
	}
	
	public QueryResponse createResponseWithObjRef(GetDocumentsAndAssociationsQuery getDocumentsQuery) {
		if (getDocumentsQuery == null || (getDocumentsQuery.getUniqueIds() == null && getDocumentsQuery.getUuids() == null)) {
			return sendErrorResponse();
		}
		
		QueryResponse response = new QueryResponse();
		List<PatientDocument> docments = new ArrayList<>();
		List<ObjectReference> docRef = new ArrayList<>();
		if (getDocumentsQuery.getUniqueIds() != null && !getDocumentsQuery.getUniqueIds().isEmpty()) {
			getDocumentsQuery.getUniqueIds().stream().forEach(a -> getPatientDocuments(docments, a));
		}
		
		docRef.addAll(docments.stream().map(this::getReferences).collect(Collectors.toList()));
		if (getDocumentsQuery.getUuids() != null && !getDocumentsQuery.getUuids().isEmpty()) {
			List<ObjectReference> docRefer = getDocumentsQuery.getUuids().stream().map(this::getObjectRef).map(this::getReferences).collect(Collectors.toList());
			docRef.addAll(docRefer);
		}
		response.setReferences(docRef);
		response.setStatus(Status.SUCCESS);
		return response;
	}
	
	public QueryResponse createResponseWithObjRef(FindDocumentsQuery findDocumentsQuery) {
		if (findDocumentsQuery == null || findDocumentsQuery.getPatientId() == null) {
			return sendErrorResponse();
		}
		
		QueryResponse response = new QueryResponse();
		String patientId = findDocumentsQuery.getPatientId().getId();
		List<PatientDocument> documents = patientDocumentRepository.findAllByPatientNo(patientId);
		response.setReferences(documents.stream().map(this::getReferences).collect(Collectors.toList()));
		response.setStatus(Status.SUCCESS);
		return response;
	}

	private ObjectReference getReferences(PatientDocument a) {
		ObjectReference ref = new ObjectReference();
		ref.setId(StringUtils.isNotEmpty(a.getPatientDocId()) ? a.getPatientDocId() : "");
		return ref;
	}
	
	private ObjectReference getReferences(SubmissionSetDocument submission) {
		ObjectReference ref = new ObjectReference();
		ref.setId(submission.getEntryUuid());
		return ref;
	}
}

package com.example.demo.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.commons.lang3.StringUtils;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.AssigningAuthority;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.Author;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.AvailabilityStatus;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.DocumentAvailability;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.DocumentEntry;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.DocumentEntryType;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.Identifiable;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.ObjectReference;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.Organization;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.SubmissionSet;
import org.openehealth.ipf.commons.ihe.xds.core.requests.QueryRegistry;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.FindDocumentsQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.QueryReturnType;
import org.openehealth.ipf.commons.ihe.xds.core.responses.QueryResponse;
import org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.query.AdhocQueryResponse;
import org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.rim.ExtrinsicObjectType;
import org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.rim.RegistryObjectListType;
import org.openehealth.ipf.platform.camel.ihe.xds.core.converters.EbXML30Converters;
import org.openehealth.ipf.platform.camel.ihe.xds.core.converters.XdsRenderingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.RecordNotFoundException;
import com.example.demo.model.MetaDataInfo;
import com.example.demo.model.PatientInfo;
import com.example.demo.model.T130961;
import com.example.demo.repo.T130961Repository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RegistryQueryService {

	@Autowired private T130961Repository patnerConfig;
	
	@EndpointInject(uri = "direct:mokpoint")
	private ProducerTemplate producerTemplate;
	
	public QueryResponse getPatientMetaData(String patientNo, String returnType, String messageType, String patnerId) {
		T130961 patner = patnerConfig.findById(patnerId).orElseThrow(() -> new RecordNotFoundException("Patner id not found"));
		if(StringUtils.isEmpty(patner.getUrl())) {
			log.error("Wrong patner Url");
			throw new BadRequestException("Wrong patner Url");
		}
		
		FindDocumentsQuery query = new FindDocumentsQuery();
		query.setPatientId(new Identifiable(patientNo, new AssigningAuthority("1.3.6.1.4.1.21367.2005.13.20.1000")));
		List<AvailabilityStatus> list = Arrays.asList(AvailabilityStatus.APPROVED, AvailabilityStatus.SUBMITTED);
		query.setStatus(list);
		List<DocumentEntryType> documents = Arrays.asList(DocumentEntryType.STABLE);
		query.setDocumentEntryTypes(documents);
		
		List<DocumentAvailability> documentAvail = Arrays.asList(DocumentAvailability.ONLINE);
		query.setDocumentAvailability(documentAvail);
		query.setMetadataLevel(1);
		
		QueryRegistry queryRegistry=new QueryRegistry(query);
		try {
			QueryReturnType queryType = QueryReturnType.valueOfCode(returnType);
			queryRegistry.setReturnType(queryType);
		} catch (Exception e) {
			log.info("Wrong query return type {}", e.getMessage());
			throw e;
		}
		
		AdhocQueryResponse response = (AdhocQueryResponse) producerTemplate.requestBody("xds-iti18://" + patner.getUrl(), queryRegistry);
		System.out.println(XdsRenderingUtils.renderEbxml(response));
		QueryResponse queryResponse = EbXML30Converters.convertToQueryResponse(response);
		
		return queryResponse;
		
//		queryResponse.getDocumentEntries().iterator().forEachRemaining(f -> {
//			System.out.println("getMimeType :" + f.getMimeType());
//			System.out.println("getHash :" + f.getHash());
//			System.out.println("getEntryUuid :" + f.getEntryUuid());
//			System.out.println("getEntryUuid :" + f.getHomeCommunityId());
//			System.out.println("getEntryUuid :" + f.getLanguageCode());
//			System.out.println("getEntryUuid :" + f.getLogicalUuid());
//			System.out.println("getEntryUuid :" + f.getRepositoryUniqueId());
//			System.out.println("getEntryUuid :" + f.getUniqueId());
//			System.out.println("getEntryUuid :" + f.getUri());
//			System.out.println("getEntryUuid :" + f.getSize());
//			System.out.println("getEntryUuid :" + f.getAvailabilityStatus());
//			System.out.println("getEntryUuid :" + f.getAuthor().getAuthorPerson().getName());
//			
//			f.getAuthor().getAuthorInstitution().forEach(a -> a.getOrganizationName());
//			System.out.println("getEntryUuid :" + f.getAuthor().getAuthorInstitution());
//			
//			System.out.println("getEntryUuid :" + f.getComments().getLang());
//			System.out.println("getEntryUuid :" + f.getComments().getValue());
//			System.out.println("getEntryUuid :" + f.getCreationTime());
//			System.out.println("getEntryUuid :" + f.getServiceStartTime());
//			System.out.println("getEntryUuid :" + f.getServiceStopTime());
//			System.out.println("getEntryUuid :" + f.getSourcePatientId().getId());
//			System.out.println("getEntryUuid :" + f.getSourcePatientInfo().getGender());
//			System.out.println("getEntryUuid :" + f.getSourcePatientInfo().getAddress().getCity());
//			System.out.println("getEntryUuid :" + f.getSourcePatientInfo().getAddress().getCountry());
//			System.out.println("getEntryUuid :" + f.getSourcePatientInfo().getAddress().getStreetAddress());
//			System.out.println("getEntryUuid :" + f.getSourcePatientInfo().getDateOfBirth());
////			System.out.println("getEntryUuid :" + f.getSourcePatientInfo().getIds().forEach(i -> i.getId()));
//			System.out.println("getEntryUuid :" + f.getTitle());
//			System.out.println("getEntryUuid :" + f.getType().getUuid());
////			System.out.println("getEntryUuid :" + f.get);
//		});
//		
//		queryResponse.getDocuments().forEach(d -> {
//			System.out.println("getMimeType : " + d.getDocumentEntry().getMimeType());
//			System.out.println("getHash : " + d.getDocumentEntry().getHash());
//			System.out.println("getEntryUuid : " + d.getDocumentEntry().getEntryUuid());
//			System.out.println("getHomeCommunityId : " + d.getDocumentEntry().getHomeCommunityId());
//			System.out.println("getSize : " + d.getDocumentEntry().getSize());
//		});
		
//		ExtrinsicObjectType aas = new ExtrinsicObjectType();
//		response.getRegistryObjectList().getIdentifiable().iterator().forEachRemaining(a -> {
//			
//			
//			System.out.println("======================");
//			System.out.println("getDeclaredType : "+ a.getDeclaredType());
//			System.out.println("getLocalPart : "+ a.getName().getLocalPart());
//			System.out.println("getNamespaceURI : "+ a.getName().getNamespaceURI());
//			System.out.println("getPrefix : "+ a.getName().getPrefix());
//			System.out.println("getHome : "+ a.getValue().getHome());
//			System.out.println("scope name : "+ a.getScope().getName());
//			
//			
//		});
		
//		response.getResponseSlotList().getSlot().forEach(s -> {
//			
//			System.out.println("slote name : "+ s.getName());
//			System.out.println("getSlotType : "+ s.getSlotType());
//			s.getValueList().getValue().forEach(System.out::println);
//			
//		});
		
		
		
//		List<MetaDataInfo> metaData = Arrays.asList(new MetaDataInfo("hash", "d62e7d16cd29abde6d13abc44e69479526978c37"), new MetaDataInfo("size", "2416"), new MetaDataInfo("repositoryUniqueId", "1.3.6.1.4.1.21367.2011.2.3.248"));
//		PatientInfo patientInfo = new PatientInfo();
//		patientInfo.setPatientId("9fe89bd266ef460^^^&1.3.6.1.4.1.21367.2005.13.20.1000&ISO");
//		patientInfo.setTitle("AllergyDoc");
//		patientInfo.setCreationDate(new Date());
//		patientInfo.setStatus("Approved");
//		patientInfo.setServiceDate(new Date());
//		patientInfo.setHomeCommunityId("2423232323");
//		patientInfo.setRepositoryUniqueId("1.3.6.1.4.1.21367.2011.2.3.248");
//		patientInfo.setDocumentUniqueId("1.3.6.1.4.1.12559.11.1.2.2.1.1.3.125974");
//		patientInfo.setMimeType("text/xml");
//		patientInfo.setUrl("www.medisys.com.sa");
//		patientInfo.setMetaData(metaData);
//		return patientInfo;
	}

}

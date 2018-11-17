package com.example.demo.service;

import java.util.Arrays;
import java.util.Collections;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.commons.lang3.StringUtils;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.AssigningAuthority;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.AssociationType;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.AvailabilityStatus;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.DocumentAvailability;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.DocumentEntryType;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.Identifiable;
import org.openehealth.ipf.commons.ihe.xds.core.requests.QueryRegistry;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.FindDocumentsQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.FindFoldersQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.FindSubmissionSetsQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.GetAllQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.GetAssociationsQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.GetDocumentsAndAssociationsQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.GetDocumentsQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.GetFolderAndContentsQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.GetFoldersForDocumentQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.GetFoldersQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.GetRelatedDocumentsQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.GetSubmissionSetAndContentsQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.GetSubmissionSetsQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.QueryReturnType;
import org.openehealth.ipf.commons.ihe.xds.core.responses.QueryResponse;
import org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.query.AdhocQueryRequest;
import org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.query.AdhocQueryResponse;
import org.openehealth.ipf.platform.camel.ihe.xds.core.converters.EbXML30Converters;
import org.openehealth.ipf.platform.camel.ihe.xds.core.converters.XdsRenderingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.RecordNotFoundException;
import com.example.demo.model.T130961;
import com.example.demo.repo.T130961Repository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RegistryQueryService {

	@Autowired
	private T130961Repository patnerConfig;

	@EndpointInject(uri = "direct:mokpoint")
	private ProducerTemplate producerTemplate;

	public QueryResponse getPatientMetaData(String patientId, String returnType, String messageType, String patnerId) {
		QueryRegistry queryRegistry = null;
		T130961 patner = patnerConfig.findById(patnerId)
				.orElseThrow(() -> new RecordNotFoundException("Patner id not found"));
		if (StringUtils.isEmpty(patner.getUrl())) {
			log.error("Wrong patner Url");
			throw new BadRequestException("Wrong patner Url");
		}
		String assigningAuthority = patner.getAssigningAuthority();
		if ("98".equals(messageType)) {
			queryRegistry = new QueryRegistry(createFindDocumentsQuery(patientId, assigningAuthority, patner.getHomeCommunityId()));
		} else if ("100".equals(messageType)) {
			queryRegistry = new QueryRegistry(createFindFoldersQuery(patientId, assigningAuthority, patner.getHomeCommunityId()));
		} else if ("101".equals(messageType)) {
			queryRegistry = new QueryRegistry(createFindSubmissionSetsQuery(patientId, assigningAuthority, patner.getHomeCommunityId()));
		} else if ("102".equals(messageType)) {
			queryRegistry = new QueryRegistry(createGetAllQuery(patientId, assigningAuthority, patner.getHomeCommunityId()));
		} else if ("103".equals(messageType)) {
			queryRegistry = new QueryRegistry(createGetAssociationsQuery(patnerId, patner.getHomeCommunityId()));
		} else if ("104".equals(messageType)) {
			queryRegistry = new QueryRegistry(createGetDocumentsQuery(patientId, patner.getHomeCommunityId()));
		} else if ("105".equals(messageType)) {
			queryRegistry = new QueryRegistry(createGetDocumentsAndAssociationsQuery(patientId, patner.getHomeCommunityId()));
		} else if ("106".equals(messageType)) {
			queryRegistry = new QueryRegistry(createGetFolderAndContentsQuery(patientId, patner.getHomeCommunityId()));
		} else if ("107".equals(messageType)) {
			queryRegistry = new QueryRegistry(createGetFoldersQuery(patientId, patner.getHomeCommunityId()));
		} else if ("108".equals(messageType)) {
			queryRegistry = new QueryRegistry(createGetFoldersForDocumentQuery(patientId, patner.getHomeCommunityId()));
		} else if ("109".equals(messageType)) {
			queryRegistry = new QueryRegistry(createGetRelatedDocumentsQuery(patientId, patner.getHomeCommunityId()));
		} else if ("110".equals(messageType)) {
			queryRegistry = new QueryRegistry(createGetSubmissionSetAndContentsQuery(patientId, patner.getHomeCommunityId()));
		} else if ("111".equals(messageType)) {
			queryRegistry = new QueryRegistry(createGetSubmissionSetsQuery(patientId, patner.getHomeCommunityId()));
		}

		try {
			QueryReturnType queryType = QueryReturnType.valueOfCode(returnType);
			queryRegistry.setReturnType(queryType);
		} catch (Exception e) {
			log.info("Wrong query return type {}", e.getMessage());
			throw e;
		}

		AdhocQueryRequest ebXML = EbXML30Converters.convert(queryRegistry);
		System.out.println(XdsRenderingUtils.renderEbxml(ebXML));

		AdhocQueryResponse response = (AdhocQueryResponse) producerTemplate.requestBody("xds-iti18://" + patner.getUrl(), queryRegistry);
		System.out.println(XdsRenderingUtils.renderEbxml(response));
		QueryResponse queryResponse = EbXML30Converters.convertToQueryResponse(response);
		return queryResponse;
	}

	public FindDocumentsQuery createFindDocumentsQuery(String patientId, String assigningAuthority, String homeCommunityId) {
		FindDocumentsQuery query = new FindDocumentsQuery();
		if (StringUtils.isNotBlank(homeCommunityId)) {
			query.setHomeCommunityId(homeCommunityId);
		}
		
		
		query.setPatientId(new Identifiable(patientId, new AssigningAuthority(assigningAuthority)));
		query.setStatus(Arrays.asList(AvailabilityStatus.APPROVED, AvailabilityStatus.SUBMITTED));
		query.setDocumentEntryTypes(Collections.singletonList(DocumentEntryType.STABLE));
		query.setDocumentAvailability(Collections.singletonList(DocumentAvailability.ONLINE));
		query.setMetadataLevel(1);
		return query;
	}

	public FindFoldersQuery createFindFoldersQuery(String patientId, String assigningAuthority, String homeCommunityId) {
		FindFoldersQuery query = new FindFoldersQuery();
		if (StringUtils.isNotBlank(homeCommunityId)) {
			query.setHomeCommunityId(homeCommunityId);
		}
		query.setPatientId(new Identifiable(patientId, new AssigningAuthority(assigningAuthority)));
		query.setStatus(Arrays.asList(AvailabilityStatus.APPROVED, AvailabilityStatus.SUBMITTED));
		return query;
	}

	public FindSubmissionSetsQuery createFindSubmissionSetsQuery(String patientId, String assigningAuthority, String homeCommunityId) {
		FindSubmissionSetsQuery query = new FindSubmissionSetsQuery();
		if (StringUtils.isNotBlank(homeCommunityId)) {
			query.setHomeCommunityId(homeCommunityId);
		}
		query.setPatientId(new Identifiable(patientId, new AssigningAuthority(assigningAuthority)));
		query.setStatus(Arrays.asList(AvailabilityStatus.APPROVED, AvailabilityStatus.SUBMITTED));
		return query;
	}

	public GetAllQuery createGetAllQuery(String patientId, String assigningAuthority, String homeCommunityId) {
		GetAllQuery query = new GetAllQuery();
		if (StringUtils.isNotBlank(homeCommunityId)) {
			query.setHomeCommunityId(homeCommunityId);
		}
		query.setPatientId(new Identifiable(patientId, new AssigningAuthority(assigningAuthority)));
		query.setStatusDocuments(Arrays.asList(AvailabilityStatus.APPROVED, AvailabilityStatus.SUBMITTED));
		query.setStatusFolders(Arrays.asList(AvailabilityStatus.APPROVED, AvailabilityStatus.SUBMITTED));
		query.setStatusSubmissionSets(Arrays.asList(AvailabilityStatus.APPROVED, AvailabilityStatus.SUBMITTED));
		query.setDocumentEntryTypes(Collections.singletonList(DocumentEntryType.STABLE));
		return query;
	}

	public GetAssociationsQuery createGetAssociationsQuery(String uuids, String homeCommunityId) {
		// "urn:uuid:1.2.3.4", "urn:uuid:2.3.4.5"
		GetAssociationsQuery query = new GetAssociationsQuery();
		if (StringUtils.isNotBlank(homeCommunityId)) {
			query.setHomeCommunityId(homeCommunityId);
		}
		query.setUuids(Arrays.asList("urn:uuid:" + uuids));
		return query;
	}

	public GetDocumentsQuery createGetDocumentsQuery(String uniqueIds, String homeCommunityId) {
		GetDocumentsQuery query = new GetDocumentsQuery();
		if (StringUtils.isNotBlank(homeCommunityId)) {
			query.setHomeCommunityId(homeCommunityId);
		}
		// query.setUuids(Arrays.asList(uuids));
		query.setUniqueIds(Arrays.asList(uniqueIds));
		return query;
	}

	public GetDocumentsAndAssociationsQuery createGetDocumentsAndAssociationsQuery(String uniqueIds, String homeCommunityId) {
		GetDocumentsAndAssociationsQuery query = new GetDocumentsAndAssociationsQuery();
		// query.setUuids(Arrays.asList("urn:uuid:1.2.3.4",
		// "urn:uuid:2.3.4.5"));
		if (StringUtils.isNotBlank(homeCommunityId)) {
			query.setHomeCommunityId(homeCommunityId);
		}
		query.setUniqueIds(Arrays.asList(uniqueIds));
		return query;
	}

	public GetFolderAndContentsQuery createGetFolderAndContentsQuery(String uniqueId, String homeCommunityId) {
		GetFolderAndContentsQuery query = new GetFolderAndContentsQuery();
		if (StringUtils.isNotBlank(homeCommunityId)) {
			query.setHomeCommunityId(homeCommunityId);
		}
		query.setUniqueId(uniqueId);
		query.setDocumentEntryTypes(Collections.singletonList(DocumentEntryType.STABLE));
		return query;
	}

	public GetFoldersQuery createGetFoldersQuery(String uniqueIds, String homeCommunityId) {
		GetFoldersQuery query = new GetFoldersQuery();
		if (StringUtils.isNotBlank(homeCommunityId)) {
			query.setHomeCommunityId(homeCommunityId);
		}
		
		query.setUniqueIds(Arrays.asList(uniqueIds));
		return query;
	}

	public GetFoldersForDocumentQuery createGetFoldersForDocumentQuery(String uniqueId, String homeCommunityId) {
		GetFoldersForDocumentQuery query = new GetFoldersForDocumentQuery();
		if (StringUtils.isNotBlank(homeCommunityId)) {
			query.setHomeCommunityId(homeCommunityId);
		}
		query.setUniqueId(uniqueId);
		return query;
	}

	public GetRelatedDocumentsQuery createGetRelatedDocumentsQuery(String uniqueId, String homeCommunityId) {
		GetRelatedDocumentsQuery query = new GetRelatedDocumentsQuery();
		if (StringUtils.isNotBlank(homeCommunityId)) {
			query.setHomeCommunityId(homeCommunityId);
		}
		query.setUniqueId(uniqueId);
		query.setAssociationTypes(Arrays.asList(AssociationType.APPEND, AssociationType.TRANSFORM));
		query.setDocumentEntryTypes(Collections.singletonList(DocumentEntryType.STABLE));
		return query;
	}

	public GetSubmissionSetAndContentsQuery createGetSubmissionSetAndContentsQuery(String uniqueId, String homeCommunityId) {
		GetSubmissionSetAndContentsQuery query = new GetSubmissionSetAndContentsQuery();
		if (StringUtils.isNotBlank(homeCommunityId)) {
			query.setHomeCommunityId(homeCommunityId);
		}
		query.setUniqueId(uniqueId);
		query.setDocumentEntryTypes(Collections.singletonList(DocumentEntryType.STABLE));
		return query;
	}

	public GetSubmissionSetsQuery createGetSubmissionSetsQuery(String uuids, String homeCommunityId) {
		GetSubmissionSetsQuery query = new GetSubmissionSetsQuery();
		if (StringUtils.isNotBlank(homeCommunityId)) {
			query.setHomeCommunityId(homeCommunityId);
		}
		query.setUuids(Arrays.asList("urn:uuid:" + uuids));
		return query;
	}
}

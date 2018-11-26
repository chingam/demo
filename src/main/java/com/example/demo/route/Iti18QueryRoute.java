package com.example.demo.route;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.spring.boot.FatJarRouter;
import org.apache.commons.lang3.StringUtils;
import org.openehealth.ipf.commons.ihe.xds.core.requests.QueryRegistry;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.FindDocumentsQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.FindFoldersQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.FindSubmissionSetsQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.GetAllQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.GetAssociationsQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.GetDocumentsQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.GetFoldersForDocumentQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.GetFoldersQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.GetRelatedDocumentsQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.GetSubmissionSetsQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.QueryReturnType;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.QueryType;
import org.openehealth.ipf.commons.ihe.xds.core.responses.ErrorCode;
import org.openehealth.ipf.commons.ihe.xds.core.responses.ErrorInfo;
import org.openehealth.ipf.commons.ihe.xds.core.responses.QueryResponse;
import org.openehealth.ipf.commons.ihe.xds.core.responses.Severity;
import org.openehealth.ipf.commons.ihe.xds.core.responses.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.service.CreateQueryResponseService;

@Component
public class Iti18QueryRoute extends FatJarRouter {

	private final static Logger log = LoggerFactory.getLogger(Iti18QueryRoute.class);

	@Autowired
	private CreateQueryResponseService service;

	@Override
	public void configure() throws Exception {
		from("direct:queryImplementation").log(LoggingLevel.DEBUG, "message Imple.....${body}")
				.process(new Processor() {

					@Override
					public void process(Exchange exchange) throws Exception {
						String recipients = "";
						QueryType quretType = exchange.getIn().getBody(QueryRegistry.class).getQuery().getType();
						if (quretType == QueryType.FIND_DOCUMENTS) {
							recipients = "direct:findDocs";
						}else if (quretType == QueryType.FIND_DOCUMENTS_BY_REFERENCE_ID) {
							recipients = "direct:findDocsByRef";
						} else if (quretType == QueryType.FIND_SUBMISSION_SETS) {
							recipients = "direct:findSets";
						} else if (quretType == QueryType.FIND_FOLDERS) {
							recipients = "direct:findFolders";
						} else if (quretType == QueryType.GET_SUBMISSION_SET_AND_CONTENTS) {
							recipients = "direct:getSetAndContents";
						} else if (quretType == QueryType.GET_DOCUMENTS) {
							recipients = "direct:getDocs";
						} else if (quretType == QueryType.GET_FOLDER_AND_CONTENTS) {
							recipients = "direct:getFolderAndContents";
						} else if (quretType == QueryType.GET_FOLDERS) {
							recipients = "direct:getFolders";
						} else if (quretType == QueryType.GET_SUBMISSION_SETS) {
							recipients = "direct:getSets";
						} else if (quretType == QueryType.GET_ASSOCIATIONS) {
							recipients = "direct:getAssocs";
						} else if (quretType == QueryType.GET_DOCUMENTS_AND_ASSOCIATIONS) {
							recipients = "direct:getDocsAndAssocs";
						} else if (quretType == QueryType.GET_FOLDERS_FOR_DOCUMENT) {
							recipients = "direct:getFoldersForDoc";
						} else if (quretType == QueryType.GET_RELATED_DOCUMENTS) {
							recipients = "direct:getRelatedDocs";
						} else if (quretType == QueryType.GET_ALL) {
							recipients = "direct:getAll";
						} else {
							recipients = "direct:fail";
						}
						exchange.getIn().setHeader("queryMatch", recipients);
					}
				}).recipientList(header("queryMatch"));

		

		from("direct:findSets").process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {
				QueryRegistry request = exchange.getIn().getBody(QueryRegistry.class);
				FindSubmissionSetsQuery findSubmissionsetsQuery = (FindSubmissionSetsQuery) request.getQuery();
				if (QueryReturnType.OBJECT_REF.name().equals(request.getReturnType().name())) {
					QueryResponse response = service.createResponseWithObjRef(findSubmissionsetsQuery);
					exchange.getIn().setBody(response);
					return;
				}
				
				QueryResponse resp = service.createResponse(findSubmissionsetsQuery);
				exchange.getIn().setBody(resp);
			}
		}).log(LoggingLevel.INFO, "findSets End.........");

		from("direct:findFolders").process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {
				QueryRegistry request = exchange.getIn().getBody(QueryRegistry.class);
				FindFoldersQuery findFoldersQuery = (FindFoldersQuery) request.getQuery();
				if (QueryReturnType.OBJECT_REF.name().equals(request.getReturnType().name())) {
					QueryResponse response = service.createResponseWithObjRef(findFoldersQuery);
					exchange.getIn().setBody(response);
					return;
				}
				QueryResponse resp = service.createResponse(findFoldersQuery);
				exchange.getIn().setBody(resp);
			}
		}).log(LoggingLevel.DEBUG, "FindFoldersQuery logic").end();

		from("direct:getFolders").process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {
				QueryRegistry request = exchange.getIn().getBody(QueryRegistry.class);
				GetFoldersQuery getFolderQuery = (GetFoldersQuery) request.getQuery();
				if (QueryReturnType.OBJECT_REF.name().equals(request.getReturnType().name())) {
					QueryResponse response = service.createResponseWithObjRef(getFolderQuery);
					exchange.getIn().setBody(response);
					return;
				}
				
				QueryResponse resp = service.createResponse(getFolderQuery);
				exchange.getIn().setBody(resp);
			}
		}).log(LoggingLevel.DEBUG, "GetFoldersQuery logic").end();

		

		from("direct:getFoldersForDoc").process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {
				QueryRegistry request = exchange.getIn().getBody(QueryRegistry.class);
				GetFoldersForDocumentQuery getFolderQuery = (GetFoldersForDocumentQuery) request.getQuery();
				QueryResponse resp = new QueryResponse();
				if (getFolderQuery == null || (StringUtils.isBlank(getFolderQuery.getUniqueId()) && StringUtils.isBlank(getFolderQuery.getUuid()))) {
					resp.setStatus(Status.FAILURE);
					List<ErrorInfo> errors = new ArrayList<>();
					ErrorInfo error = new ErrorInfo();
					error.setErrorCode(ErrorCode.STORED_QUERY_MISSING_PARAM);
					error.setSeverity(Severity.ERROR);
					errors.add(error);
					error.setCodeContext("Unknown Stored Query query id");
					error.setLocation("Unknown Stored Query query id");
					resp.setErrors(errors);
					exchange.getIn().setBody(resp);
				} else {
					resp.setStatus(Status.SUCCESS);
					exchange.getIn().setBody(resp);
				}
			}
		}).log(LoggingLevel.DEBUG, "GetFoldersForDocumentQuery logic").end();

		from("direct:getRelatedDocs").process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {
				QueryRegistry request = exchange.getIn().getBody(QueryRegistry.class);
				GetRelatedDocumentsQuery getFolderQuery = (GetRelatedDocumentsQuery) request.getQuery();
				QueryResponse resp = new QueryResponse();
				if (getFolderQuery == null || (StringUtils.isBlank(getFolderQuery.getUniqueId()) && StringUtils.isBlank(getFolderQuery.getUuid()))) {
					resp.setStatus(Status.FAILURE);
					List<ErrorInfo> errors = new ArrayList<>();
					ErrorInfo error = new ErrorInfo();
					error.setErrorCode(ErrorCode.STORED_QUERY_MISSING_PARAM);
					error.setSeverity(Severity.ERROR);
					errors.add(error);
					error.setCodeContext("Unknown Stored Query query id");
					error.setLocation("Unknown Stored Query query id");
					resp.setErrors(errors);
					exchange.getIn().setBody(resp);
				} else {
					resp.setStatus(Status.SUCCESS);
					exchange.getIn().setBody(resp);
				}
			}
		}).log(LoggingLevel.DEBUG, "GetRelatedDocumentsQuery logic").end();

		from("direct:getSets").process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {
				QueryRegistry request = exchange.getIn().getBody(QueryRegistry.class);
				GetSubmissionSetsQuery getSubmissionSetsQuery = (GetSubmissionSetsQuery) request.getQuery();
				QueryResponse resp = new QueryResponse();
				if (getSubmissionSetsQuery == null || getSubmissionSetsQuery.getUuids().isEmpty()) {
					resp.setStatus(Status.FAILURE);
					exchange.getIn().setBody(resp);
				} else {
					resp.setStatus(Status.SUCCESS);
					exchange.getIn().setBody(resp);
				}
			}
		}).log(LoggingLevel.DEBUG, "GetSubmissionSetQuery logic").end();

		from("direct:getAssocs").process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {
				QueryRegistry request = exchange.getIn().getBody(QueryRegistry.class);
				GetAssociationsQuery getAssociationsQuery = (GetAssociationsQuery) request.getQuery();
				QueryResponse resp = new QueryResponse();
				if (getAssociationsQuery == null || getAssociationsQuery.getUuids().isEmpty()) {
					resp.setStatus(Status.FAILURE);
					exchange.getIn().setBody(resp);
				} else {
					resp.setStatus(Status.SUCCESS);
					exchange.getIn().setBody(resp);
				}
			}
		}).log(LoggingLevel.DEBUG, "GetAssociationsQuery logic").end();

		from("direct:getFolderAndContents").process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {
				QueryRegistry request = exchange.getIn().getBody(QueryRegistry.class);
				// GetFolderAndContentsQuery getFolderAndContentsQuery =
				// (GetFolderAndContentsQuery) request.getQuery();
				QueryResponse resp = new QueryResponse();
				resp.setStatus(Status.SUCCESS);
				exchange.getIn().setBody(resp);
			}
		}).log(LoggingLevel.DEBUG, "GetFolderAndContentsQuery logic").end();

		from("direct:getSetAndContents").process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {
				QueryRegistry request = exchange.getIn().getBody(QueryRegistry.class);
				// GetFolderAndContentsQuery getFolderAndContentsQuery =
				// (GetFolderAndContentsQuery) request.getQuery();
				QueryResponse resp = new QueryResponse();
				resp.setStatus(Status.SUCCESS);
				exchange.getIn().setBody(resp);
			}
		}).log(LoggingLevel.DEBUG, "GetSubmissionSetAndContentsQuery logic").end();

		from("direct:unableQurery").process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {
				QueryRegistry request = exchange.getIn().getBody(QueryRegistry.class);
				// GetFolderAndContentsQuery getFolderAndContentsQuery =
				// (GetFolderAndContentsQuery) request.getQuery();
				QueryResponse resp = new QueryResponse();
				resp.setStatus(Status.SUCCESS);
				exchange.getIn().setBody(resp);
			}
		}).log(LoggingLevel.DEBUG, "No Found");

	}

}

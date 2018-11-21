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
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.GetFoldersForDocumentQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.GetFoldersQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.GetRelatedDocumentsQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.QueryType;
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
public class Iti18QueryRoute extends FatJarRouter{

	 private final static Logger log = LoggerFactory.getLogger(Iti18QueryRoute.class);
	
	@Autowired private CreateQueryResponseService service;
	@Override
	public void configure() throws Exception {
		from("direct:queryImplementation").log(LoggingLevel.DEBUG, "message Imple.....${body}")
		.process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				String recipients="";
				QueryType quretType=exchange.getIn().getBody(QueryRegistry.class).getQuery().getType();
				if(quretType==QueryType.FIND_DOCUMENTS){
					recipients="direct:findDocs";
				}else if(quretType==QueryType.FIND_SUBMISSION_SETS){
					recipients="direct:findSets";
				}else if(quretType==QueryType.FIND_FOLDERS){
					recipients="direct:findFolders";
				}else if(quretType==QueryType.GET_SUBMISSION_SET_AND_CONTENTS){
					recipients="direct:getSetAndContents";
				}else if(quretType==QueryType.GET_DOCUMENTS){
					recipients="direct:getDocs";
				}else if(quretType==QueryType.GET_FOLDER_AND_CONTENTS){
					recipients="direct:getFolderAndContents";
				}else if(quretType==QueryType.GET_FOLDERS){
					recipients="direct:getFolders";
				}else if(quretType==QueryType.GET_SUBMISSION_SETS){
					recipients="direct:getSets";
				}else if(quretType==QueryType.GET_ASSOCIATIONS){
					recipients="direct:getAssocs";
				}else if(quretType==QueryType.GET_DOCUMENTS_AND_ASSOCIATIONS){
					recipients="direct:getDocsAndAssocs";
				}else if(quretType==QueryType.GET_FOLDERS_FOR_DOCUMENT){
					recipients="direct:getFoldersForDoc";
				}else if(quretType==QueryType.GET_RELATED_DOCUMENTS){
					recipients="direct:getRelatedDocs";
				}else{
					recipients="direct:fail";
				}
				exchange.getIn().setHeader("queryMatch", recipients);
			}
		}).recipientList(header("queryMatch"));
		
		
		from("direct:findDocs").process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				QueryRegistry request=exchange.getIn().getBody(QueryRegistry.class);
				FindDocumentsQuery findDocumentsQuery =(FindDocumentsQuery) request.getQuery();
				
				QueryResponse response = service.createResponse(findDocumentsQuery);
				exchange.getIn().setBody(response);
				
				/*QueryResponse resp=new QueryResponse();
				if (findDocumentsQuery.getPatientId() == null || StringUtils.isBlank(findDocumentsQuery.getPatientId().getId())) {
					resp.setStatus(Status.FAILURE);
					List<ErrorInfo> errors = new ArrayList<>();
					ErrorInfo error = new ErrorInfo();
					error.setSeverity(Severity.ERROR);
					errors.add(error);
					error.setCodeContext("error : patient id not found !");
					resp.setErrors(errors);
					exchange.getIn().setBody(resp);
				}
				resp.setStatus(Status.SUCCESS);*/
				
			}
		}).log(LoggingLevel.INFO, "findSets End.........");
		
		from("direct:findSets").process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				QueryRegistry request=exchange.getIn().getBody(QueryRegistry.class);
				FindSubmissionSetsQuery findSubmissionsetsQuery =(FindSubmissionSetsQuery) request.getQuery();
				QueryResponse resp=service.createResponse(findSubmissionsetsQuery);
				exchange.getIn().setBody(resp);
			}
		}).log(LoggingLevel.INFO, "findSets End.........");
		
		

		
		  from("direct:findFolders").process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				QueryRegistry request=exchange.getIn().getBody(QueryRegistry.class);
				FindFoldersQuery findFoldersQuery = (FindFoldersQuery) request.getQuery();
				QueryResponse resp=service.createResponse(findFoldersQuery);
				exchange.getIn().setBody(resp);
			}
		}).log(LoggingLevel.DEBUG, "FindFoldersQuery logic").end();

		  from("direct:getDocs").process(new Processor() {
				
				@Override
				public void process(Exchange exchange) throws Exception {
					QueryRegistry request=exchange.getIn().getBody(QueryRegistry.class);
					GetAllQuery getAllQuery = (GetAllQuery) request.getQuery();
					QueryResponse resp=service.createResponse(getAllQuery);
					exchange.getIn().setBody(resp);
				}
			}).log(LoggingLevel.DEBUG, "GetDocumentsQuery logic").end();

		  
		  from("direct:getFolders").process(new Processor() {
				
				@Override
				public void process(Exchange exchange) throws Exception {
					QueryRegistry request=exchange.getIn().getBody(QueryRegistry.class);
					GetFoldersQuery getFolderQuery = (GetFoldersQuery) request.getQuery();
					QueryResponse resp = new QueryResponse();
					if (getFolderQuery == null || getFolderQuery.getUuids().isEmpty() || getFolderQuery.getUniqueIds().isEmpty()) {
						resp.setStatus(Status.FAILURE);
						List<ErrorInfo> errors = new ArrayList<>();
						ErrorInfo error = new ErrorInfo();
						error.setSeverity(Severity.ERROR);
						errors.add(error);
						error.setCodeContext("error : query is empty !");
						resp.setErrors(errors);
						exchange.getIn().setBody(resp);
					} else {
						resp.setStatus(Status.SUCCESS);
						exchange.getIn().setBody(resp);
					}
				}
			}).log(LoggingLevel.DEBUG, "GetFoldersQuery logic").end();

		  from("direct:getDocsAndAssocs").to("direct:getDocs").log(LoggingLevel.DEBUG, "GetDocumentsAndAssociationsQuery logic").end();
      
		  from("direct:getFoldersForDoc").process(new Processor() {
				
				@Override
				public void process(Exchange exchange) throws Exception {
					QueryRegistry request=exchange.getIn().getBody(QueryRegistry.class);
					GetFoldersForDocumentQuery getFolderQuery = (GetFoldersForDocumentQuery) request.getQuery();
					QueryResponse resp = new QueryResponse();
					if (getFolderQuery == null || StringUtils.isBlank(getFolderQuery.getUniqueId()) || StringUtils.isBlank(getFolderQuery.getUuid())) {
						resp.setStatus(Status.FAILURE);
						List<ErrorInfo> errors = new ArrayList<>();
						ErrorInfo error = new ErrorInfo();
						error.setSeverity(Severity.ERROR);
						errors.add(error);
						error.setCodeContext("error : query is empty !");
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
					QueryRegistry request=exchange.getIn().getBody(QueryRegistry.class);
					GetRelatedDocumentsQuery getFolderQuery = (GetRelatedDocumentsQuery) request.getQuery();
					QueryResponse resp = new QueryResponse();
					if (getFolderQuery == null || StringUtils.isBlank(getFolderQuery.getUniqueId()) || StringUtils.isBlank(getFolderQuery.getUuid())) {
						resp.setStatus(Status.FAILURE);
						List<ErrorInfo> errors = new ArrayList<>();
						ErrorInfo error = new ErrorInfo();
						error.setSeverity(Severity.ERROR);
						errors.add(error);
						error.setCodeContext("error : query is empty !");
						resp.setErrors(errors);
						exchange.getIn().setBody(resp);
					} else {
						resp.setStatus(Status.SUCCESS);
						exchange.getIn().setBody(resp);
					}
				}
			}).log(LoggingLevel.DEBUG, "GetRelatedDocumentsQuery logic").end();
      
		  from("direct:getSets").log(LoggingLevel.DEBUG, "GetSubmissionSetQuery logic").end();
      
		  from("direct:getAssocs").log(LoggingLevel.DEBUG, "GetAssociationsQuery logic").end();
  
		  from("direct:getFolderAndContents").log(LoggingLevel.DEBUG, "GetFolderAndContentsQuery logic").end();
      
		  from("direct:getSetAndContents").log(LoggingLevel.DEBUG, "GetSubmissionSetAndContentsQuery logic").end();
		
		  from("direct:unableQurery").log(LoggingLevel.DEBUG, "No Found");
		
	}

	
	
}

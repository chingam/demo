package com.example.demo.route;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.spring.boot.FatJarRouter;
import org.openehealth.ipf.commons.ihe.xds.core.requests.QueryRegistry;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.FindSubmissionSetsQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.QueryType;
import org.openehealth.ipf.commons.ihe.xds.core.responses.QueryResponse;
import org.openehealth.ipf.commons.ihe.xds.core.responses.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Iti18QueryRoute extends FatJarRouter{

	 private final static Logger log = LoggerFactory.getLogger(Iti18QueryRoute.class);
	
	
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
		
		
		
		
		
		
		from("direct:findSets").process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				QueryRegistry request=exchange.getIn().getBody(QueryRegistry.class);
				FindSubmissionSetsQuery findSubmissionsetsQuery =(FindSubmissionSetsQuery) request.getQuery();
				
				QueryResponse resp=new QueryResponse();
				resp.setStatus(Status.SUCCESS);
				exchange.getIn().setBody(resp);
			}
		}).log(LoggingLevel.INFO, "findSets End.........");
		
		

		
		  from("direct:findFolders").log(LoggingLevel.DEBUG, "FindFoldersQuery logic").end();

		  from("direct:getDocs").log(LoggingLevel.DEBUG, "GetDocumentsQuery logic").end();

		  from("direct:getFolders").log(LoggingLevel.DEBUG, "GetFoldersQuery logic").end();

		  from("direct:getDocsAndAssocs").to("direct:getDocs").log(LoggingLevel.DEBUG, "GetDocumentsAndAssociationsQuery logic").end();
      
		  from("direct:getFoldersForDoc").log(LoggingLevel.DEBUG, "GetFoldersForDocumentQuery logic").end();
  
		  from("direct:getRelatedDocs").log(LoggingLevel.DEBUG, "GetRelatedDocumentsQuery logic").end();
      
		  from("direct:getSets").log(LoggingLevel.DEBUG, "GetSubmissionSetQuery logic").end();
      
		  from("direct:getAssocs").log(LoggingLevel.DEBUG, "GetAssociationsQuery logic").end();
  
		  from("direct:getFolderAndContents").log(LoggingLevel.DEBUG, "GetFolderAndContentsQuery logic").end();
      
		  from("direct:getSetAndContents").log(LoggingLevel.DEBUG, "GetSubmissionSetAndContentsQuery logic").end();
		
		  from("direct:unableQurery").log(LoggingLevel.DEBUG, "No Found");
		
	}

	
	
}

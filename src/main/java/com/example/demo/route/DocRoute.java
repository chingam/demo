package com.example.demo.route;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.spring.boot.FatJarRouter;
import org.openehealth.ipf.commons.ihe.xds.core.requests.QueryRegistry;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.FindDocumentsByReferenceIdQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.FindDocumentsQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.GetAllQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.GetDocumentsAndAssociationsQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.GetDocumentsQuery;
import org.openehealth.ipf.commons.ihe.xds.core.requests.query.QueryReturnType;
import org.openehealth.ipf.commons.ihe.xds.core.responses.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.service.CreateQueryResponseService;

@Component
public class DocRoute extends FatJarRouter {
	@Autowired
	private CreateQueryResponseService service;

	@Override
	public void configure() throws Exception {
		from("direct:findDocs").process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {
				QueryRegistry request = exchange.getIn().getBody(QueryRegistry.class);
				FindDocumentsQuery findDocumentsQuery = (FindDocumentsQuery) request.getQuery();
				
				if (QueryReturnType.OBJECT_REF.name().equals(request.getReturnType().name())) {
					QueryResponse response = service.createResponseWithObjRef(findDocumentsQuery);
					exchange.getIn().setBody(response);
					return;
				}
				QueryResponse response = service.createResponse(findDocumentsQuery);
				exchange.getIn().setBody(response);
			}
		}).log(LoggingLevel.INFO, "findSets End.........").end();

		from("direct:getDocsAndAssocs").process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {
				QueryRegistry request = exchange.getIn().getBody(QueryRegistry.class);
				GetDocumentsAndAssociationsQuery getDocumentsQuery = (GetDocumentsAndAssociationsQuery) request.getQuery();
				
				if (QueryReturnType.OBJECT_REF.name().equals(request.getReturnType().name())) {
					QueryResponse response = service.createResponseWithObjRef(getDocumentsQuery);
					exchange.getIn().setBody(response);
					return;
				} 
				QueryResponse resp = service.createResponse(getDocumentsQuery);
				exchange.getIn().setBody(resp);
			
			}
		}).log(LoggingLevel.DEBUG, "GetDocumentsAndAssociationsQuery logic").end();

		
		from("direct:getDocs").process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {
				QueryRegistry request = exchange.getIn().getBody(QueryRegistry.class);
				GetDocumentsQuery getDocumentsQuery = (GetDocumentsQuery) request.getQuery();
				
				if (QueryReturnType.OBJECT_REF.name().equals(request.getReturnType().name())) {
					QueryResponse response = service.createResponseWithObjRef(getDocumentsQuery);
					exchange.getIn().setBody(response);
					return;
				}
				QueryResponse resp = service.createResponse(getDocumentsQuery);
				exchange.getIn().setBody(resp);
			}
		}).log(LoggingLevel.DEBUG, "GetDocumentsQuery logic").end();
		
		from("direct:findDocsByRef").process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				QueryRegistry request = exchange.getIn().getBody(QueryRegistry.class);
				FindDocumentsByReferenceIdQuery getDocumentsQuery = (FindDocumentsByReferenceIdQuery) request.getQuery();
				
				if (QueryReturnType.OBJECT_REF.name().equals(request.getReturnType().name())) {
					QueryResponse response = service.createResponseWithObjRef(getDocumentsQuery);
					exchange.getIn().setBody(response);
					return;
				}
				QueryResponse resp = service.createResponse(getDocumentsQuery);
				exchange.getIn().setBody(resp);
			}
		}).log(LoggingLevel.DEBUG, "FindDocumentByReference logic").end();
		
		from("direct:getAll").process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				QueryRegistry request = exchange.getIn().getBody(QueryRegistry.class);
				GetAllQuery getAll = (GetAllQuery) request.getQuery();
				
				if (QueryReturnType.OBJECT_REF.name().equals(request.getReturnType().name())) {
					QueryResponse response = service.createResponseWithObjRef(getAll);
					exchange.getIn().setBody(response);
					return;
				}
				QueryResponse resp = service.createResponse(getAll);
				exchange.getIn().setBody(resp);
			}
		}).log(LoggingLevel.DEBUG, "FindDocumentByReference logic").end();

	}
}

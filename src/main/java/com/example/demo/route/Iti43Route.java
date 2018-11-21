package com.example.demo.route;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.activation.DataHandler;
import javax.activation.DataSource;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.spring.boot.FatJarRouter;
import org.openehealth.ipf.commons.ihe.xds.core.requests.RetrieveDocumentSet;
import org.openehealth.ipf.commons.ihe.xds.core.responses.ErrorInfo;
import org.openehealth.ipf.commons.ihe.xds.core.responses.RetrievedDocument;
import org.openehealth.ipf.commons.ihe.xds.core.responses.RetrievedDocumentSet;
import org.openehealth.ipf.commons.ihe.xds.core.responses.Severity;
import org.openehealth.ipf.commons.ihe.xds.core.responses.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.ApplicationConfig;
import com.sun.istack.ByteArrayDataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Iti43Route extends FatJarRouter {

	@Autowired private ApplicationConfig appConfig;
	
	@Override
	public void configure() throws Exception {

		from("xds-iti43:xds-iti43-medisys").convertBodyTo(RetrieveDocumentSet.class).process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {
				RetrieveDocumentSet retrive = exchange.getIn().getBody(RetrieveDocumentSet.class);
				if (retrive.getDocuments().isEmpty()) {
					RetrievedDocumentSet response = sendErrorResponse();
					exchange.getIn().setBody(response);
					return;
				}

				RetrievedDocumentSet response = new RetrievedDocumentSet();
				List<RetrievedDocument> retrievedDocuments = retrive.getDocuments().stream().map(a -> {
					RetrievedDocument retrievedDocument = new RetrievedDocument();
					retrievedDocument.setRequestData(a);
					Path path = Paths.get(appConfig.getPath() + File.separator + a.getDocumentUniqueId() + ".pdf");
					if (path.toFile().exists()) {
						try {
							DataSource dataSrc = new ByteArrayDataSource(Files.readAllBytes(path), "application/pdf");
							DataHandler dataHandler1 = new DataHandler(dataSrc);
							retrievedDocument.setDataHandler(dataHandler1);
							retrievedDocument.setMimeType("application/pdf");
						} catch (IOException e) {
							log.error("Could not read the file {}", e);
						}
					}
					
					return retrievedDocument;
				}).collect(Collectors.toList());
				response.getDocuments().addAll(retrievedDocuments);
				response.setStatus(Status.SUCCESS);
				exchange.getIn().setBody(response);
			}

			private RetrievedDocumentSet sendErrorResponse() {
				RetrievedDocumentSet response = new RetrievedDocumentSet();
				response.setStatus(Status.FAILURE);
				List<ErrorInfo> errors = new ArrayList<>();
				ErrorInfo error = new ErrorInfo();
				error.setSeverity(Severity.ERROR);
				errors.add(error);
				error.setCodeContext("error : query is empty !");
				response.setErrors(errors);
				return response;
			}
		}).end();
	}
}

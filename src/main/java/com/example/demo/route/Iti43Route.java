package com.example.demo.route;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.activation.DataHandler;
import javax.activation.DataSource;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.spring.boot.FatJarRouter;
import org.apache.commons.io.IOUtils;
import org.openehealth.ipf.commons.ihe.xds.core.ebxml.ebxml30.RetrieveDocumentSetRequestType;
import org.openehealth.ipf.commons.ihe.xds.core.requests.RetrieveDocumentSet;
import org.openehealth.ipf.commons.ihe.xds.core.responses.ErrorInfo;
import org.openehealth.ipf.commons.ihe.xds.core.responses.RetrievedDocument;
import org.openehealth.ipf.commons.ihe.xds.core.responses.RetrievedDocumentSet;
import org.openehealth.ipf.commons.ihe.xds.core.responses.Severity;
import org.openehealth.ipf.commons.ihe.xds.core.responses.Status;
import org.openehealth.ipf.platform.camel.ihe.xds.core.converters.EbXML30Converters;
import org.openehealth.ipf.platform.camel.ihe.xds.core.converters.XdsRenderingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.ApplicationConfig;
import com.example.demo.DateUtil;
import com.example.demo.model.PatientDocument;
import com.example.demo.model.ServerLog;
import com.example.demo.repo.PatientDocumentRepository;
import com.example.demo.repo.ServerLogRepository;
import com.sun.istack.ByteArrayDataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Iti43Route extends FatJarRouter {

	@Autowired private ApplicationConfig appConfig;
	@Autowired private ServerLogRepository serverRepo;
	@Autowired private PatientDocumentRepository repository;
	
	@Override
	public void configure() throws Exception {

		from("xds-iti43:xds-iti43-retrieve").convertBodyTo(RetrieveDocumentSet.class).process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {
				RetrieveDocumentSet retrive = exchange.getIn().getBody(RetrieveDocumentSet.class);
				
				String messageId = DateUtil.format(new Date(), DateUtil.HL7v2_DATE_FORMAT);
				RetrieveDocumentSetRequestType ebXML = EbXML30Converters.convert(retrive);
				String msg = XdsRenderingUtils.renderEbxml(ebXML);
				String url = appConfig.getPath() + File.separator + messageId + ".xml";
				InputStream in = new ByteArrayInputStream(msg.getBytes(StandardCharsets.UTF_8));
				OutputStream os = new FileOutputStream(new File(url));
				IOUtils.copy(in, os);
				IOUtils.closeQuietly(os);
				IOUtils.closeQuietly(in);
				ServerLog server = new ServerLog();
				server.setMessageId(messageId);
				server.setDate(new Date());
				server.setTransactionName("Retrieve document set");
				server.setType("INCOMMING");
				serverRepo.save(server);	
				
				if (retrive.getDocuments().isEmpty()) {
					RetrievedDocumentSet response = sendErrorResponse();
					exchange.getIn().setBody(response);
					return;
				}

				RetrievedDocumentSet response = new RetrievedDocumentSet();
				List<RetrievedDocument> retrievedDocuments = retrive.getDocuments().stream().map(a -> {
					RetrievedDocument retrievedDocument = new RetrievedDocument();
					retrievedDocument.setRequestData(a);
					Optional<PatientDocument> patientDoc = repository.findAllByEntryUuid(a.getDocumentUniqueId()).stream().findFirst();
					PatientDocument document = null;
					if (patientDoc.isPresent()) {
						document = patientDoc.get();
						Path path = Paths.get(appConfig.getPath() + File.separator + document.getFileName());
						if (path.toFile().exists()) {
							try {
								DataSource dataSrc = new ByteArrayDataSource(Files.readAllBytes(path), document.getMimeType().getDescription());
								DataHandler dataHandler1 = new DataHandler(dataSrc);
								retrievedDocument.setDataHandler(dataHandler1);
								retrievedDocument.setMimeType(document.getMimeType().getDescription());
							} catch (IOException e) {
								log.error("Could not read the file {}", e);
							}
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

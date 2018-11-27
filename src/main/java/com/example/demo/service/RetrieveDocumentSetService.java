package com.example.demo.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

import javax.activation.DataHandler;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.openehealth.ipf.commons.ihe.xds.core.ebxml.ebxml30.RetrieveDocumentSetRequestType;
import org.openehealth.ipf.commons.ihe.xds.core.ebxml.ebxml30.RetrieveDocumentSetResponseType;
import org.openehealth.ipf.commons.ihe.xds.core.requests.RetrieveDocument;
import org.openehealth.ipf.commons.ihe.xds.core.requests.RetrieveDocumentSet;
import org.openehealth.ipf.platform.camel.ihe.xds.core.converters.EbXML30Converters;
import org.openehealth.ipf.platform.camel.ihe.xds.core.converters.XdsRenderingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.ApplicationConfig;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.RecordNotFoundException;
import com.example.demo.model.ServerLog;
import com.example.demo.model.T130961;
import com.example.demo.repo.ServerLogRepository;
import com.example.demo.repo.T130961Repository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RetrieveDocumentSetService {

	@EndpointInject(uri = "direct:mokpoint")
	private ProducerTemplate producerTemplate;
	
	@Autowired private ApplicationConfig appConfig;
	@Autowired private ServerLogRepository serverRepo;

	@Autowired
	private T130961Repository patnerConfig;

	public RetrieveDocumentSetResponseType retrieveDocument(String documentUniqueId, String homeCommunityId, String repositoryUniqueId, String patnerId) {
		T130961 patner = patnerConfig.findById(patnerId).orElseThrow(() -> new RecordNotFoundException("Patner id not found"));
		if (StringUtils.isEmpty(patner.getUrl())) {
			log.error("Wrong patner Url");
			throw new BadRequestException("Wrong patner Url");
		}

		RetrieveDocumentSet retrieve = new RetrieveDocumentSet();
		RetrieveDocument doc1 = new RetrieveDocument();
		doc1.setDocumentUniqueId(documentUniqueId);
		doc1.setHomeCommunityId(homeCommunityId);
		doc1.setRepositoryUniqueId(repositoryUniqueId);
		retrieve.getDocuments().add(doc1);
		
		RetrieveDocumentSetRequestType ebXML = EbXML30Converters.convert(retrieve);
		String msg = XdsRenderingUtils.renderEbxml(ebXML);
		final String uuid = UUID.randomUUID().toString().replace("-", "");
		log(msg, uuid,"OUTGOING");

		RetrieveDocumentSetResponseType response = (RetrieveDocumentSetResponseType) producerTemplate.requestBody("xds-iti43://" + patner.getUrl(), retrieve);
		String msgres = XdsRenderingUtils.renderEbxml(response);
		final String uuidres = UUID.randomUUID().toString().replace("-", "");
		log(msgres, uuidres,"INCOMING");
		
		response.getDocumentResponse().forEach(a -> {
			DataHandler dataHandler = a.getDocument();
			if (dataHandler != null && dataHandler.getDataSource() != null) {
				String contentType = dataHandler.getDataSource().getContentType();
				log.warn("content Type >>" + contentType);
				try {
					String extension = ".pdf";
					if ("text/xml".equals(contentType)) {
						extension = ".xml";
					} else if("image/jpeg".equals(contentType)) {
						extension = ".jpeg";
					} else if ("text/plain".equals(contentType)) {
						extension = ".text";
					}
					String path = appConfig.getPath() + File.separator + a.getDocumentUniqueId() + extension;
					InputStream in = dataHandler.getDataSource().getInputStream();
					OutputStream os = new FileOutputStream(new File(path));
					IOUtils.copy(in, os);
					IOUtils.closeQuietly(os);
					IOUtils.closeQuietly(in);
				} catch (IOException e) {
					log.error("Content could not save {}", e.getMessage());
				}
			}
		});
		return response;
	}
	
	private void log(String msg, String messageId, String type) {
		String path = appConfig.getPath() + File.separator + messageId + ".xml";
		InputStream in = new ByteArrayInputStream(msg.getBytes(StandardCharsets.UTF_8));
		try (OutputStream os = new FileOutputStream(new File(path))){
			IOUtils.copy(in, os);
			IOUtils.closeQuietly(os);
			IOUtils.closeQuietly(in);
			ServerLog server = new ServerLog();
			server.setMessageId(messageId);
			server.setDate(new Date());
			server.setTransactionName("Rerieve Set a Document");
			server.setType(type);
			serverRepo.save(server);
		} catch (FileNotFoundException e) {
			log.error("File found ", e);
		} catch (IOException e) {
			log.error("File could not write ", e);
		}
	}
	
}

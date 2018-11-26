package com.example.demo.route;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.spring.boot.FatJarRouter;
import org.apache.commons.io.IOUtils;
import org.openehealth.ipf.commons.ihe.xds.core.requests.QueryRegistry;
import org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.query.AdhocQueryRequest;
import org.openehealth.ipf.platform.camel.ihe.xds.core.converters.EbXML30Converters;
import org.openehealth.ipf.platform.camel.ihe.xds.core.converters.XdsRenderingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.ApplicationConfig;
import com.example.demo.DateUtil;
import com.example.demo.model.ServerLog;
import com.example.demo.repo.ServerLogRepository;

@Component
public class Iti18Route extends FatJarRouter {
	@Autowired private ServerLogRepository serverRepo;
	@Autowired private ApplicationConfig appConfig;
	@Override
	public void configure() throws Exception {

		from("xds-iti18:xds-iti18-query").process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {/*
				QueryResponse queryResponse=new QueryResponse(Status.SUCCESS);
				exchange.getIn().setBody(queryResponse);
			*/
				String messageId = DateUtil.format(new Date(), DateUtil.HL7v2_DATE_FORMAT);
				QueryRegistry quretType=exchange.getIn().getBody(QueryRegistry.class);
				AdhocQueryRequest ebXML = EbXML30Converters.convert(quretType);
				String msg = XdsRenderingUtils.renderEbxml(ebXML);
				
				String path = appConfig.getPath() + File.separator + messageId + ".xml";
				InputStream in = new ByteArrayInputStream(msg.getBytes(StandardCharsets.UTF_8));
				OutputStream os = new FileOutputStream(new File(path));
				IOUtils.copy(in, os);
				IOUtils.closeQuietly(os);
				IOUtils.closeQuietly(in);
				
				ServerLog server = new ServerLog();
				server.setMessageId(messageId);
				server.setDate(new Date());
				server.setTransactionName("Registry store query");
				server.setType("INCOMMING");
				serverRepo.save(server);	
			}
		})
//		.log(LoggingLevel.INFO, "${body}")
		
		.to("direct:queryImplementation").end();
		
		from("xds-iti41:xds-document").log("test").end();
	}
}

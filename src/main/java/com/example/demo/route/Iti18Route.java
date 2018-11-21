package com.example.demo.route;


import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.spring.boot.FatJarRouter;
import org.openehealth.ipf.commons.ihe.xds.core.responses.QueryResponse;
import org.openehealth.ipf.commons.ihe.xds.core.responses.Status;
import org.springframework.stereotype.Component;

@Component
public class Iti18Route extends FatJarRouter {
	
	@Override
	public void configure() throws Exception {

		from("xds-iti18:xds-iti18-medisys").process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {/*
				QueryResponse queryResponse=new QueryResponse(Status.SUCCESS);
				exchange.getIn().setBody(queryResponse);
			*/}
		})
//		.log(LoggingLevel.INFO, "${body}")
		.to("direct:queryImplementation").end();
		
		from("xds-iti41:xds-document").log("test").end();
	}
}

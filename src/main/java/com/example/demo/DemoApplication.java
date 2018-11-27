package com.example.demo;

import java.io.File;
import java.util.Date;
import java.util.Locale;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.openehealth.ipf.commons.ihe.ws.cxf.payload.OutPayloadLoggerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@SpringBootApplication
@ImportResource({ "classpath:META-INF/cxf/cxf.xml", "classpath:context.xml" })
public class DemoApplication {
	@Autowired private ApplicationConfig appConfig;
	
	@Bean
	public ServletRegistrationBean<CXFServlet> servletRegistrationBean(ApplicationContext context) {
		return new ServletRegistrationBean<>(new CXFServlet(), "/xds/service/*");
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		return lci;
	}

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(Locale.US);
		return slr;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	class LocalInterceptor implements WebMvcConfigurer {
		@Override
		public void addInterceptors(InterceptorRegistry registry) {
			registry.addInterceptor(localeChangeInterceptor());
		}
	}
	
	@Bean
	public OutPayloadLoggerInterceptor serverOutLogger() {
		String messageId = DateUtil.format(new Date(), DateUtil.HL7v2_DATE_FORMAT);
		String path = appConfig.getPath() + File.separator + messageId + ".xml";
//		ServerLog server = new ServerLog();
//		server.setMessageId(messageId);
//		server.setDate(new Date());
//		server.setTransactionName("Registry store query");
//		server.setType("OUTGOING");
//		serverRepo.save(server);	
		return new OutPayloadLoggerInterceptor(path );
	}
}

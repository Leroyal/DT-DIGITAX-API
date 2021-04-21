package com.digitax.config;


import org.apache.maven.plugins.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.util.StringUtils;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.ws.wsdl.wsdl11.ProviderBasedWsdl4jDefinition;
import org.springframework.ws.wsdl.wsdl11.provider.InliningXsdSchemaTypesProvider;
import org.springframework.ws.wsdl.wsdl11.provider.SoapProvider;
import org.springframework.ws.wsdl.wsdl11.provider.SuffixBasedMessagesProvider;
import org.springframework.ws.wsdl.wsdl11.provider.SuffixBasedPortTypesProvider;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import com.digitax.endpoint.LoginRequestEndpoint;

import gov.irs.mef.exception.ServiceException;
import gov.irs.mef.exception.ToolkitException;
import gov.irs.mef.services.ServiceEndpoints;

@EnableWs
@Configuration
public class LoginConfig extends WsConfigurerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(LoginConfig.class);

	private static final String NAMESPACE_URI = "https://la.www4.irs.gov/ae_rup_svcs";

	
	
	
	  @Bean 
	  public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) { final
	  MessageDispatcherServlet servlet = new MessageDispatcherServlet();
	  servlet.setApplicationContext(applicationContext);
	  servlet.setTransformWsdlLocations(true);
	  logger.info("hellooooooooooooooooooooooooooooooooooooooooooo"); 
	  return new  ServletRegistrationBean<>(servlet, "/ws/*"); }
	  
	  
	
	  @Bean(name = "Loginres") 
	  public DefaultWsdl11Definition  defaultWsdl11Definition(XsdSchema countriesSchema) 
	  {
	  logger.info("i am in wsdl11definatiuon"); 
	  DefaultWsdl11Definition	  wsdl11Definition = new DefaultWsdl11Definition();
	  
	  wsdl11Definition.setPortTypeName("Login");
	  wsdl11Definition.setLocationUri("/ws/login");
	  wsdl11Definition.setTargetNamespace(NAMESPACE_URI);
	  logger.info("hiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
	  wsdl11Definition.setSchema(countriesSchema);
	  return wsdl11Definition; 
	  }
	  
	  @Bean 
	  public XsdSchema countriesSchema() { 
		  return new SimpleXsdSchema(new   ClassPathResource("LOGIN.xsd")); 
		  }
	  
	 
	/*
	 * public void setTargetNamespace(String targetNamespace) {
	 * delegate.setTargetNamespace(targetNamespace); }
	 * 
	 * public void afterPropertiesSet() throws Exception { if
	 * (!StringUtils.hasText(delegate.getTargetNamespace()) &&
	 * typesProvider.getSchemaCollection() != null &&
	 * typesProvider.getSchemaCollection().getXsdSchemas().length > 0) { XsdSchema
	 * schema = typesProvider.getSchemaCollection().getXsdSchemas()[0];
	 * setTargetNamespace(schema.getTargetNamespace()); } if
	 * (!StringUtils.hasText(loginService) &&
	 * StringUtils.hasText(portTypesProvider.getPortTypeName())) {
	 * soapProvider.setServiceName(portTypesProvider.getPortTypeName() +
	 * "loginService"); } delegate.afterPropertiesSet(); }
	 */
}  

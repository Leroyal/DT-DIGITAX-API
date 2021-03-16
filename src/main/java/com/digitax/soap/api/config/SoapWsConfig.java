package com.digitax.soap.api.config;

import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
@EnableWs
public class SoapWsConfig extends WsConfigurerAdapter  {
	@Bean
	public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext context)
	{
		MessageDispatcherServlet servlet=new MessageDispatcherServlet();
		servlet.setApplicationContext(context);
		servlet.setTransformWsdlLocations(true);
		return new  ServletRegistrationBean<MessageDispatcherServlet>(servlet,"/ws/*");        
	}
	@Bean(name="Service")
	public DefaultWsdl11Definition defaultWsdl11Defination(XsdSchema schema)
	{
		DefaultWsdl11Definition defaultWsdl11Definition=new DefaultWsdl11Definition();
		defaultWsdl11Definition.setPortTypeName("Service");
		defaultWsdl11Definition.setLocationUri("/ws");
		defaultWsdl11Definition.setTargetNamespace("http://www.irs.gov/a2a/mef/MeFMSIServices");
		defaultWsdl11Definition.setSchema(schema);
//		defaultWsdl11Definition.setSchema(schema1);
		return defaultWsdl11Definition;
	}
	@Bean
	public XsdSchema schema()
	{
		return new SimpleXsdSchema(new ClassPathResource("MeFMSIServices.xsd"));
	}

	@Bean
	public XsdSchema schema1()
	{
		return new SimpleXsdSchema(new ClassPathResource("MeFHeader.xsd"));
	}

}

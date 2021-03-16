package com.digitax.soap.service;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;

@WebServiceClient(name = "Login", targetNamespace = "http://www.irs.gov/a2a/mef/MeFMSIServices", wsdlLocation = "http://gov.irs.efile/a2a/services/MeFMSIServices.wsdl")
public class Login_Service extends Service {
	private final static URL LOGIN_WSDL_LOCATION;
    private final static WebServiceException LOGIN_EXCEPTION;
    private final static QName LOGIN_QNAME = new QName("http://www.irs.gov/a2a/mef/MeFMSIServices", "Login");
    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://gov.irs.efile/a2a/services/MeFMSIServices.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        LOGIN_WSDL_LOCATION = url;
        LOGIN_EXCEPTION = e;
    }

    public Login_Service() {
        super(__getWsdlLocation(), LOGIN_QNAME);
    }

    public Login_Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), LOGIN_QNAME, features);
    }

    public Login_Service(URL wsdlLocation) {
        super(wsdlLocation, LOGIN_QNAME);
    }

    public Login_Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, LOGIN_QNAME, features);
    }

    public Login_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public Login_Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    @WebEndpoint(name = "Login")
    public Login getLogin() {
        return super.getPort(new QName("http://www.irs.gov/a2a/mef/MeFMSIServices", "Login"), Login.class);
    }

    
    @WebEndpoint(name = "Login")
    public Login getLogin(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.irs.gov/a2a/mef/MeFMSIServices", "Login"), Login.class, features);
    }

    private static URL __getWsdlLocation() {
        if (LOGIN_EXCEPTION!= null) {
            throw LOGIN_EXCEPTION;
        }
        return LOGIN_WSDL_LOCATION;
    }

}

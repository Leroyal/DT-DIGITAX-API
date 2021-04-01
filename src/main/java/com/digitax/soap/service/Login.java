package com.digitax.soap.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Holder;

import com.digitax.exception.ErrorExceptionDetail;

import gov.irs.a2a.mef.mefheader.MeFHeaderType;
import gov.irs.a2a.mef.mefmsiservices.LoginRequestType;
import gov.irs.a2a.mef.mefmsiservices.LoginResponseType;

@WebService(name = "Login", targetNamespace = "http://www.irs.gov/a2a/mef/MeFMSIServices")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    gov.irs.a2a.mef.mefheader.ObjectFactory.class,
    gov.irs.a2a.mef.mefmsiservices.ObjectFactory.class
})
public interface Login {
	@WebMethod(operationName = "Login", action = "http://www.irs.gov/a2a/mef/Login")
    @WebResult(name = "LoginResponse", targetNamespace = "http://www.irs.gov/a2a/mef/MeFMSIServices.xsd", partName = "LoginResponse")
    public LoginResponseType login(
        @WebParam(name = "MeFHeader", targetNamespace = "http://www.irs.gov/a2a/mef/MeFHeader.xsd", header = true, mode = WebParam.Mode.INOUT, partName = "MeFHeader")
        Holder<MeFHeaderType> meFHeader,
        @WebParam(name = "LoginRequest", targetNamespace = "http://www.irs.gov/a2a/mef/MeFMSIServices.xsd", partName = "Login")
        LoginRequestType login)
        throws ErrorExceptionDetail
    ;

}

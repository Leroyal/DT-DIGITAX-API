package com.digitax.exception;

import javax.xml.ws.WebFault;

import gov.irs.a2a.mef.mefheader.ErrorExceptionDetailType;

@WebFault(name = "ErrorExceptionDetail", targetNamespace = "http://www.irs.gov/a2a/mef/MeFHeader.xsd")
public class ErrorExceptionDetail extends Exception{
	
	private ErrorExceptionDetailType faultInfo;	
	public ErrorExceptionDetail(String message, ErrorExceptionDetailType faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    
    public ErrorExceptionDetail(String message, ErrorExceptionDetailType faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    
    public ErrorExceptionDetailType getFaultInfo() {
        return faultInfo;
    }

}

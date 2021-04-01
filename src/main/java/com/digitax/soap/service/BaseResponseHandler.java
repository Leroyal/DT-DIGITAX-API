package com.digitax.soap.service;

import gov.irs.a2a.mef.mefheader.MeFHeaderType;

public abstract class BaseResponseHandler<R extends Result> implements ResponseHandler<R>{
protected MeFHeaderType responseHeader;
	
	protected BaseResponseHandler (MeFHeaderType responseHeader) {
		this.responseHeader = responseHeader;
	}


}

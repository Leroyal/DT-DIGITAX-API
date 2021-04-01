package com.digitax.soap.service;

import com.digitax.exception.ToolkitException;

public interface ResponseHandler <R extends Result> {
	public Builder<R> handleResponse(ServiceContext context) throws ToolkitException;

}

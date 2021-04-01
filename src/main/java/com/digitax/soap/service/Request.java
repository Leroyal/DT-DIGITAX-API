package com.digitax.soap.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.digitax.exception.ErrorMessages;
import com.digitax.exception.ToolkitException;
import com.digitax.mef.validation.ValidationErrors;
import com.digitax.service.data.MessageID;
import com.digitax.util.LogUtil;

import gov.irs.a2a.mef.mefheader.MeFHeaderType;
import gov.irs.a2a.mef.mefheader.SessionKeyCdType;

public class Request {
	private ValidationErrors validationErrors;
	private String action;
	private MeFHeaderType header;
	private SessionKeyCdType sessionIndicator;
	private static Logger logger = LogUtil.getLogger(Request.class.getName());
	private static final String className = Request.class.getName();

	/**
	 * Constructs a basic Request with MeF Header fields set to default values and
	 * initialized the ValidationErrors to a new empty list.
	 */
	public Request () {
		gov.irs.a2a.mef.mefheader.ObjectFactory headerObjects =
			new gov.irs.a2a.mef.mefheader.ObjectFactory();
		this.header = headerObjects.createMeFHeaderType();
		this.validationErrors = new ValidationErrors();
		this.sessionIndicator = SessionKeyCdType.Y;
	}

	/**
	 * Validates the state of this object.
	 *
	 * @return - Always returns a non-null ValidationErrors object.  If there were
	 * 			 no validation errors, the isEmpty() method of the returned Validation
	 * 			 errors object will evaluate to true.
	 */
	protected ValidationErrors validate() {
		/*Always throw away old errors and reconstruct to keep from duplicate
		 *errors incase of multiple invocations of this method on the same object
		 *in the same invalid state or incase the object state has become valid
		 *since a previous invocation.
		 */
		this.validationErrors = new ValidationErrors();
		return this.validationErrors;
	}

	/**
	 * Grants access to the ValidationErrors associated with the request but without
	 * performing actual validation as via the validate() method.  If this method is
	 * called before the validate() method has been called on an object instance of
	 * this class, the ValidationErrors object returned will be empty.  If called
	 * after the validate() method has been called, it may or may not be empty depending
	 * on the results of validation.  Always returns a non-null object.
	 *
	 * @return - A copy of the ValidationErrors object associated with this object.
	 * 			 Modifying this copy has no effect on the internal state of the original
	 * 			 ServiceRequestContext.
	 */
	public ValidationErrors getValidationErrors() {
		return this.validationErrors;
	}

	void setAction(String action) {
		this.action = action;
	}

	MeFHeaderType buildMeFRequestHeader(ServiceContext aServiceContext)
		throws ToolkitException {

			String methodName = "buildMeFRequestHeader()";
			MessageID msgId = new MessageID(aServiceContext.getEtin());
			DatatypeFactory dtf = null;
			try {
				dtf = DatatypeFactory.newInstance();
			} catch (DatatypeConfigurationException dtcfe) {
				String msg = ErrorMessages.MeFClientSDK000036.getErrorMessage() +
					"; " + className + "; " + methodName + "; " + dtcfe.getMessage();
				ToolkitException tke = new ToolkitException(msg, dtcfe);
				logger.log(Level.SEVERE, msg, tke);
				throw tke;
			}

			XMLGregorianCalendar timeStamp = dtf
				.newXMLGregorianCalendar(msgId.getCreationInstant());
			timeStamp.setFractionalSecond(null);

		synchronized (this.header) {
			this.header.setAction(this.action);
			this.header.setMessageID(msgId.getValue());
			this.header.setMessageTs(timeStamp);
			this.header.setETIN(aServiceContext.getEtin().getValue());
			this.header.setSessionKeyCd(this.sessionIndicator);
			this.header.setTestCd(aServiceContext.getTestCdType());
			this.header.setAppSysID(aServiceContext.getAppSysID());
			this.header.setWSDLVersionNum("10.4");
			this.header.setClientSoftwareTxt(aServiceContext.getClientSoftwareTxt());
		}
		return this.header;
	}

	/*
	 * Will return null if called prior to buildMeFRequestHeader()
	 */
	MeFHeaderType getMeFRequestHeader() {
		return this.header;
	}


	protected String getAuditContent() {
		return "Request data: N/A";
	}

	/**
	 * @return SessionIndicatorType
	 */
	public SessionKeyCdType getSessionIndicator() {
		return this.sessionIndicator;
	}

	/**
	 * @param sessionIndicator
	 */
	public void setSessionIndicator(SessionKeyCdType sessionIndicator) {
		this.sessionIndicator = sessionIndicator;
	}

}

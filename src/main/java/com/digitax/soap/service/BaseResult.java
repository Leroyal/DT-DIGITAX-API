package com.digitax.soap.service;

import javax.xml.datatype.XMLGregorianCalendar;

import gov.irs.a2a.mef.mefheader.MeFHeaderType;
import gov.irs.a2a.mef.mefheader.MeFNotificationResponse;

public abstract class BaseResult<T> implements Result {
	private MeFHeaderType responseHeader;
	private MeFNotificationResponse notificationResponse;
	
	protected BaseResult (BaseResultBuilder<T> myBuilder) {
		this.responseHeader = myBuilder.responseHeader;
		if (this.responseHeader != null) {
			this.notificationResponse = myBuilder.notificationResponse;
		}
	}

	/**
	 * @return The Action value from the response message MeFHeader.
	 */
	//Cannot remove because referenced by AuditImpl.  Making package-private - A.H.
	String getAction() {
		return this.responseHeader == null ? null : this.responseHeader.getAction();
	}

	/**
	 * @return The AppSysID value from the response message MeFHeader.
	 */
	/*public String getAppSysID() {
		return this.responseHeader == null ? null : this.responseHeader.getAppSysID();
	}*/

	/**
	 * @return The ETIN value from the response message MeFHeader.
	 */
	/*public String getETIN() {
		return this.responseHeader == null ? null : this.responseHeader.getETIN();
	}*/

	/**
	 * @return The ID value from the response message MeFHeader.
	 */
	/*public String getId() {
		return this.responseHeader == null ? null : this.responseHeader.getId();
	}*/

	/**
	 * @return The MessageID value from the response message MeFHeader.
	 */
	public String getMessageID() {
		return this.responseHeader == null ? null : this.responseHeader.getMessageID();
	}

	/**
	 * @return The RelatesTo value from the response message MeFHeader.
	 */
	public String getRelatesTo() {
		return this.responseHeader == null ? null : this.responseHeader.getRelatesTo();
	}

	/**
	 * @return The SessionIndicator value from the response message MeFHeader.
	 */
	/*public SessionIndicatorType getSessionIndicator() {
		return this.responseHeader == null ? null : this.responseHeader.getSessionIndicator();
	}*/

	/**
	 * @return The TestIndicator value from the response message MeFHeader.
	 */
	/*public TestIndicatorType getTestIndicator() {
		return this.responseHeader == null ? null : this.responseHeader.getTestIndicator();
	}*/

	/**
	 * @return The Timestamp value from the response message MeFHeader.
	 */
	public XMLGregorianCalendar getTimestamp() {
		return this.responseHeader == null ? null : this.responseHeader.getMessageTs();
	}

	/**
	 * @return The ApplicableDate value from the response message MeFHeader NotificationResponse.
	 */
	/*public XMLGregorianCalendar getApplicableDate() {
		return this.notificationResponse == null ? null : this.notificationResponse.getApplicableDate();
	}*/

	/**
	 * @return The NotificatoinType value from the response message MeFHeader.
	 */
	public MeFNotificationResponse getNotificationResponse() {
		return this.notificationResponse == null ? null : this.notificationResponse;
	}

	/**
	 * @return true if the NotificationResponse field for the response message MeFHeader
	 * 		   is present, false otherwise.
	 */
	public boolean hasNotificationResponse() {
		return this.notificationResponse != null;
	}
	
	protected String getAuditContent() {
		return "Response data: N/A";
	}	

}

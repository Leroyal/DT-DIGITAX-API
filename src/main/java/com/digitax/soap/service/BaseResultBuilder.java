package com.digitax.soap.service;

import gov.irs.a2a.mef.mefheader.MeFHeaderType;
import gov.irs.a2a.mef.mefheader.MeFNotificationResponse;

public abstract class BaseResultBuilder<T> implements Builder<T> {
	protected MeFHeaderType responseHeader;
	protected MeFNotificationResponse notificationResponse;

	protected BaseResultBuilder(MeFHeaderType responseHeader) {
		this.responseHeader = responseHeader;
		if (responseHeader != null) {
			this.notificationResponse = responseHeader.getNotificationResponse();
		}
	}
}

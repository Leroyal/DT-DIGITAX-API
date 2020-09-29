package com.digitax.service;

import com.twilio.rest.api.v2010.account.Message;

public interface SmsService {
	 void sendSMS(String to, String message);
}

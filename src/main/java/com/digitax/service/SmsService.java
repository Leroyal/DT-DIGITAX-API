package com.digitax.service;

public interface SmsService {
	   void sendSMS(String to, String message);

	   void sendOTP(String string);

	   Boolean verifyOTP(String string, String string2);
}

package com.digitax.service.impl;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.digitax.service.SmsService;
import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
import com.twilio.rest.api.v2010.account.Message;

import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;



@SpringBootApplication
public class SmsServiceImpl implements SmsService {
   public static final String ACCOUNT_SID = "AC236c9127a55f932977abc38afc765508";
   public static final String AUTH_TOKEN = "f8cf1130ffab4e651617b1d6f230f563";
   public static final String TWILIO_NUMBER = "+12563740163";
   public static final String TWILIO_SERVICEID = "VAce3f8f4f571c27a619e67e0c28207bfd";

   
   @Override
   public void sendSMS(String to, String textMessage){
      Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
      try {
      Message message = Message.creator(
            new PhoneNumber(to),
            new PhoneNumber(TWILIO_NUMBER),
            textMessage)
            .create();
      System.out.println(message);
      } catch (Exception e) {
   	      System.out.println(e);
          }

   }
   
   public void sendOTP(String to){
	   Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
       Verification verification = Verification.creator(
               TWILIO_SERVICEID,
               to,
               "sms")
           .create();
       System.out.println(verification.getStatus());
   }
   
   
   public Boolean verifyOTP(String otp, String to){
	        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
	        VerificationCheck verificationCheck = VerificationCheck.creator(
	        		TWILIO_SERVICEID,
	                otp)
	            .setTo(to).create();
	        System.out.println(verificationCheck.getValid());
            return verificationCheck.getValid();
	        
	    }

   
}
package com.digitax.service.impl;

import com.digitax.service.SmsService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.twilio.type.PhoneNumber;

@SpringBootApplication
public class SmsServiceImpl implements SmsService {
   public static final String ACCOUNT_SID = "AC0e5593af095bcf9449b3cd2642b60517";
   public static final String AUTH_TOKEN = "04fe96801eb603ea0ca606712a102911";
   public static final String TWILIO_NUMBER = "+12563740163";

   
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
   
}
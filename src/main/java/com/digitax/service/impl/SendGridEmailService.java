package com.digitax.service.impl;

import com.digitax.constants.MailConstants;
import com.digitax.service.EmailService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class SendGridEmailService implements EmailService {
	  @Autowired
	  private SendGrid sendGrid;
	  
	  public String changeEmailSupport(String toEmail, String newEmail, String token, String UserName) {
        Mail mail = new Mail();
		Email fromEmail = new Email();
		fromEmail.setName(MailConstants.emailSubject);
		fromEmail.setEmail(MailConstants.fromEmial);
		mail.setFrom(fromEmail);
		mail.setTemplateId(MailConstants.CHANGE_EMALI_SUPPORT_EMAIL_ID);

		Personalization personalization = new Personalization();
		//This is the value that will be passed to the dynamic template in SendGrid
		 personalization.addDynamicTemplateData("email", newEmail);  
         personalization.addDynamicTemplateData("username", UserName);
         personalization.addDynamicTemplateData("verifyUrl", MailConstants.baseUrl+"verify-email?email_token="+token);
         personalization.addTo(new Email(toEmail));
         mail.addPersonalization(personalization);
        
         sendMail(mail);

	    return "email was successfully send";
	  }
	  
	  public String changePasswordSupport(String toEmail , String UserName) {
		   
		            Mail mail = new Mail();
		    		Email fromEmail = new Email();
		    		fromEmail.setName(MailConstants.emailSubject);
		    		fromEmail.setEmail(MailConstants.fromEmial);
		    		mail.setFrom(fromEmail);
		    		mail.setTemplateId(MailConstants.CHANGE_PASSWORD_SUPPORT_EMAIL_ID);

		    		Personalization personalization = new Personalization();
		    		//This is the value that will be passed to the dynamic template in SendGrid
		    		personalization.addDynamicTemplateData("username", UserName);        
		            personalization.addTo(new Email(toEmail));
		            mail.addPersonalization(personalization);
		    		
		    		
		            sendMail(mail);
		    
		    return "email was successfully send";
		  }
	  @Override
		public String forgotPasswordSupport(String toEmail, String token, String username) {
		  Mail mail = new Mail();
			Email fromEmail = new Email();
			fromEmail.setName(MailConstants.emailSubject);
			fromEmail.setEmail(MailConstants.fromEmial);
			mail.setFrom(fromEmail);
			mail.setTemplateId(MailConstants.FORGOT_PASSWORD_SUPPORT_EMAIL_ID);

			Personalization personalization = new Personalization();
			//This is the value that will be passed to the dynamic template in SendGrid
	         personalization.addDynamicTemplateData("username", username);
	         personalization.addDynamicTemplateData("verifyUrl", MailConstants.baseUrl+"verify-forgot-password?password_token="+token);
	         personalization.addTo(new Email(toEmail));
	         mail.addPersonalization(personalization);
	        
	         sendMail(mail);

		    return "email was successfully send";
		}
	private void sendMail(Mail mail) {
		Request request = new Request();
	    Response response;

	    try {
	      request.setMethod(Method.POST);
	      request.setEndpoint("mail/send");
	      request.setBody(mail.build());
	      System.out.println(request.getBody());
	      response = sendGrid.api(request);

	      System.out.println(response.getStatusCode());
	      System.out.println(response.getBody());
	      System.out.println(response.getHeaders());
	    } catch (IOException ex) {
	      System.out.println(ex.getMessage());
	    }

		
	}

	
	  
    	
}



package com.digitax.service.impl;

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

	  public String changeEmailSupport(String fromEmail, String toEmail, String subjectEmail) {
	    Personalization personalization = new Personalization();
	    Email from = new Email(fromEmail);
	    String subject = subjectEmail;
	    Email to = new Email(toEmail);
	    Content content = new Content("text/html", "{{email}}" + toEmail);
	    
	    personalization.addSubstitution("{{email}}",toEmail);
	    
	    
	    Mail mail = new Mail(from, subject, to, content);
	    
	    mail.addPersonalization(personalization);
	    mail.setReplyTo(new Email(fromEmail));
	    mail.setTemplateId("d-5c2a42d58636413d922bd44cc6ff0be3");

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

	    return "email was successfully send";
	  }
    	
}

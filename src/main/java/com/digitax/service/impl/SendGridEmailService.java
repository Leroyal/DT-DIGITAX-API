package com.digitax.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitax.service.EmailService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

@Service
public class SendGridEmailService implements EmailService {
    private SendGrid sendGridClient;
    @Autowired
    public SendGridEmailService(SendGrid sendGridClient) {
        this.sendGridClient = new SendGrid("SG.mZI40-aMTCmnbmhO4kd1uQ.E53WzKwjLD0B_DuwLaQgiNcWISv4gvzIHuqkpQsFlg");   
    }
    @Override
    public void sendText(String from, String to, String subject, String body) {
        Response response = Example(from, to, subject, new Content("text/plain", body));
        System.out.println("Status Code: " + response.getStatusCode() + ", Body: " + response.getBody() + ", Headers: "
                + response.getHeaders());
    }
    @Override
    public void sendHTML(String from, String to, String subject, String body) {
        Response response = sendEmail(from, to, subject, new Content("text/html", body));
        System.out.println("Status Code: " + response.getStatusCode() + ", Body: " + response.getBody() + ", Headers: "
                + response.getHeaders());
    }
    
    /**##
     * 
     * 
     * @param from
     * @param to
     * @param subject
     * @param content
     * @return
     */
    private Response sendEmail(String from, String to, String subject, Content content) {
    	Mail mail = new Mail(new Email(from), subject, new Email(to), content);
    	mail.personalization.get(0).addSubstitution("{{username}}", "Example Name");
    	mail.personalization.get(0).addSubstitution("{{email}}", "Example Email");
    	mail.setTemplateId("d-edb1041a6aa54c09b5c204dcdfabb528");
    	
        mail.setReplyTo(new Email("jayanta.5056@gmail.com"));
        Request request = new Request();
        //Response response = null;
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            request.addHeader("Content-Type", "application/json");
            request.addHeader("Authorization","Bearer SG.mZI40-aMTCmnbmhO4kd1uQ.E53WzKwjLD0B_DuwLaQgiNcWISv4gvzIHuqkpQsFlg");
            Response response = this.sendGridClient.api(request);
            return response;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
    
    
    private Response Example(String from, String to, String subject, Content content) {
    	  
    	   Mail mail = new Mail(new Email(from), subject, new Email(to), content);

    	    SendGrid sg = new SendGrid(System.getenv("SG.mZI40-aMTCmnbmhO4kd1uQ.E53WzKwjLD0B_DuwLaQgiNcWISv4gvzIHuqkpQsFlg"));
    	    Request request = new Request();
    	    try {
    	      request.setMethod(Method.POST);
    	      request.setEndpoint("mail/send");
    	      request.addHeader("Content-Type", "application/json");
              request.addHeader("Authorization","Bearer SG.mZI40-aMTCmnbmhO4kd1uQ.E53WzKwjLD0B_DuwLaQgiNcWISv4gvzIHuqkpQsFlg");
              
    	      request.setBody(mail.build());
    	      Response response = sg.api(request);
    	      System.out.println(response.getStatusCode());
    	      System.out.println(response.getBody());
    	      System.out.println(response.getHeaders());
    	      return response;
    	    } catch (IOException ex) {
    	    	System.out.println(ex.getMessage());
                return null;
    	    }
    	  }
    	
}
